package com.sample.project.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sample.project.dto.CustomerRepository;
import com.sample.project.entity.Customer;

@Configuration
@EnableBatchProcessing
public class CustomerCSVtoDBconfig {

	LocalDate currentDate = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	String formattedDate = currentDate.format(formatter);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private CustomerRepository customerRepository;

	@Bean(name = "CustomerReader1")
	public FlatFileItemReader<Customer> reader1() {
		return new FlatFileItemReaderBuilder<Customer>().name("customerItemReader")
				.resource(new ClassPathResource("customer.csv")).delimited()
				.names(new String[] { "id", "firstname", "lastname", "email", "gender",
						"contactNumber", "country", "dob" })
				.linesToSkip(1).fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
					{
						setTargetType(Customer.class);
					}
				}).build();
	}

	@Bean(name = "CustomerProcessor1")
	public ItemProcessor<Customer, Customer> processor1() {
		return new ItemProcessor<Customer, Customer>() {
			@Override
			public Customer process(Customer customer) throws Exception {
				customer.setDate(formattedDate);
				return customer;
			}
		};
	}

	@Bean(name = "CustomerProcessor")
	public CustomerItemProcessor processor() {
		return new CustomerItemProcessor();
	}

	@Bean(name = "CustomerWriter")
	public RepositoryItemWriter<Customer> writer() {
		RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
		writer.setRepository(customerRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean(name = "customerCSVStep")
	public Step customerCSVStep(ItemWriter<Customer> writer) {
		return stepBuilderFactory.get("customerCSVStep").<Customer, Customer>chunk(10)
				.reader(reader1()).processor(processor1()).writer(writer).build();
	}

	@Bean(name = "importCustomerJob")
	public Job importUserJob(JobCompletionNotificationListener listener,
			@Qualifier("customerCSVStep") Step step) {
		return jobBuilderFactory.get("importCustomerJob").incrementer(new RunIdIncrementer())
				.listener(listener).flow(step).next(deleteOldRecordsStep()).end().build();
	}

	public static class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {
		@Override
		public Customer process(final Customer customer) throws Exception {
			return customer;
		}
	}

	@Bean(name = "deleteCustomerStep")
	public Step deleteOldRecordsStep() {
		return stepBuilderFactory.get("deleteOldRecordsStep")
				.tasklet((contribution, chunkContext) -> {
					LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
					customerRepository.deleteOlderThan(oneMonthAgo);
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean(name = "CustomerListener")
	public JobCompletionNotificationListener jobExecutionListener() {
		return new JobCompletionNotificationListener();
	}

	public static class JobCompletionNotificationListener extends JobExecutionListenerSupport {

		@Override
		public void afterJob(JobExecution jobExecution) {
			if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
				System.out.println("JOB FINISHED!");
			}
		}
	}

}
