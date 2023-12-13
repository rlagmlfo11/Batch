package com.sample.project.config;

import java.io.IOException;
import java.io.Writer;

import javax.sql.DataSource;

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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.project.entity.Customer;
import com.sample.project.entity.NewCustomer;

// 데이터베이스에있는 테이블과 csv의 항목명이 다를경우. wage도뺌

@Configuration
@EnableBatchProcessing
public class NewCustomerConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcCursorItemReader<Customer> newCustomerReader() {
		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql(
				"SELECT firstname, lastname, email, contactNumber, country, dob FROM CUSTOMER");
		reader.setRowMapper(new BeanPropertyRowMapper<>(Customer.class));
		return reader;
	}

	@Bean
	public ItemProcessor<Customer, NewCustomer> newCustomerProcessor() {
		return new ItemProcessor<Customer, NewCustomer>() {
			@Override
			public NewCustomer process(Customer customer) throws Exception {
				NewCustomer newCustomer = new NewCustomer();
				if (customer.getCountry().equals("France")) {
					newCustomer.setBirthday(customer.getDob());
					newCustomer.setEmail(customer.getEmail());
					newCustomer.setFullName(customer.getFirstname() + " " + customer.getLastname());
					newCustomer.setNation(customer.getCountry());
					newCustomer.setPhoneNumber(customer.getContactNumber());
					return newCustomer;
				}
				return null;
			}
		};
	}

	@Bean
	public FlatFileItemWriter<NewCustomer> newCustomerWriter() {
		FlatFileItemWriter<NewCustomer> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("src/main/resources/NewCustomer.csv"));

		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer headerWriter) throws IOException {
				headerWriter.write("FullName,Birthday,Nation,PhoneNumber,Email");
			}
		});
		DelimitedLineAggregator<NewCustomer> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<NewCustomer> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(
				new String[] { "fullName", "birthday", "nation", "phoneNumber", "email" });

		lineAggregator.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(lineAggregator);

		return writer;
	}

	@Bean
	public Step newCustomerStep(StepBuilderFactory stepBuilderFactory, ItemReader<Customer> reader,
			ItemProcessor<Customer, NewCustomer> processor, ItemWriter<NewCustomer> writer) {
		return stepBuilderFactory.get("newCustomerStep").<Customer, NewCustomer>chunk(10)
				.reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	public Job newCustomerJob(JobBuilderFactory jobBuilderFactory, Step newCustomerStep) {
		return jobBuilderFactory.get("newCustomerJob").incrementer(new RunIdIncrementer())
				.flow(newCustomerStep).end().build();
	}
}
