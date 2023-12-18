//package com.sample.project.config;
//
//import java.io.IOException;
//import java.io.Writer;
//
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.file.FlatFileHeaderCallback;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
//import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import com.sample.project.entity.BlackList;
//import com.sample.project.entity.Customer;
//import com.sample.project.entity.People;
//
//@Configuration
//@EnableBatchProcessing
//public class BlackListConfig {
//
//	@Autowired
//	public JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	public StepBuilderFactory stepBuilderFactory;
//
//	@Autowired
//	private DataSource dataSource;
//
//	@Bean(name = "customerReaderForBlackList")
//	public JdbcCursorItemReader<Customer> customerReaderForBlackList() {
//		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
//		reader.setDataSource(dataSource);
//		reader.setSql(
//				"SELECT FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, COUNTRY, DOB FROM CUSTOMER");
//		reader.setRowMapper(new BeanPropertyRowMapper<>(Customer.class));
//		return reader;
//	}
//
//	@Bean(name = "peopleReaderForBlackList")
//	public JdbcCursorItemReader<People> peopleReaderForBlackList() {
//		JdbcCursorItemReader<People> reader = new JdbcCursorItemReader<>();
//		reader.setDataSource(dataSource);
//		reader.setSql("SELECT NAME, POSITION FROM PEOPLE");
//		reader.setRowMapper(new BeanPropertyRowMapper<>(People.class));
//		return reader;
//	}
//
//	@Bean(name = "customerProcessor")
//	public ItemProcessor<Customer, BlackList> customerProcessor() {
//		return new ItemProcessor<Customer, BlackList>() {
//			@Override
//			public BlackList process(Customer customer) throws Exception {
//				BlackList blackList = new BlackList();
//				if (customer == null) {
//					System.out.println("nulㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㄹl");
//				} else {
//					blackList.setName(customer.getFirstname() + " " + customer.getLastname());
//					blackList.setLocation(customer.getCountry());
//					blackList.setPersonalCode(customer.getEmail());
//					blackList.setPersonalNumber(customer.getContactNumber());
//					blackList.setBlockedDate(null);
//				}
//				return blackList;
//			}
//		};
//	}
//
//	@Bean(name = "peopleProceessor")
//	public ItemProcessor<People, BlackList> peopleProceessor() {
//		return new ItemProcessor<People, BlackList>() {
//			@Override
//			public BlackList process(People people) throws Exception {
//				BlackList blackList = new BlackList();
//				blackList.setName(people.getName());
//				blackList.setPersonalCode(people.getPosition());
//				blackList.setBlockedDate(null);
//				return blackList;
//			}
//		};
//	}
//
//	@Bean(name = "blackListWriter")
//	public FlatFileItemWriter<BlackList> blackListWriter() {
//		FlatFileItemWriter<BlackList> writer = new FlatFileItemWriter<>();
//		writer.setResource(new FileSystemResource("src/main/resources/BlackList.csv"));
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//			@Override
//			public void writeHeader(Writer headerWriter) throws IOException {
//				headerWriter.write("NAME,LOCATION,PERSONAL_NUMBER,PERSONAL_CODE,BLOCKED_DATE");
//			}
//		});
//		DelimitedLineAggregator<BlackList> lineAggregator = new DelimitedLineAggregator<>();
//		lineAggregator.setDelimiter(",");
//
//		BeanWrapperFieldExtractor<BlackList> fieldExtractor = new BeanWrapperFieldExtractor<>();
//		fieldExtractor.setNames(new String[] { "Name", "Location", "personalNumber", "personalCode",
//				"blockedDate" });
//
//		lineAggregator.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(lineAggregator);
//
//		return writer;
//	}
//
//	@Bean(name = "customerStepforBlackListStep")
//	public Step customerStep(StepBuilderFactory stepBuilderFactory,
//			@Qualifier("customerReaderForBlackList") JdbcCursorItemReader<Customer> customerReader,
//			ItemProcessor<Customer, BlackList> customerProcessor,
//			FlatFileItemWriter<BlackList> blackListWriter) {
//		return stepBuilderFactory.get("customerStepforBlackListStep")
//				.<Customer, BlackList>chunk(1000).reader(customerReader)
//				.processor(customerProcessor).writer(blackListWriter).build();
//	}
//
//	@Bean(name = "peopleStepforBlackListStep")
//	public Step peopleStep(StepBuilderFactory stepBuilderFactory,
//			JdbcCursorItemReader<People> peopleReader,
//			ItemProcessor<People, BlackList> peopleProceessor,
//			FlatFileItemWriter<BlackList> blackListWriter) {
//		return stepBuilderFactory.get("peopleStepforBlackListStep").<People, BlackList>chunk(10)
//				.reader(peopleReader).processor(peopleProceessor).writer(blackListWriter).build();
//	}
//
//	@Bean(name = "blackListJob") // Name of the bean is "nextJob"
//	public Job blackListJob(JobBuilderFactory jobBuilderFactory,
//			@Qualifier("customerStepforBlackListStep") Step customerStep,
//			@Qualifier("peopleStepforBlackListStep") Step peopleStep) {
//		return jobBuilderFactory.get("blackListJob").incrementer(new RunIdIncrementer())
//				.start(customerStep).next(peopleStep).build();
//	}
//
////	@Bean
////	public AfterCSVJobListener afterCSVJobListener(JobLauncher jobLauncher,
////			@Qualifier("blackListJob") Job secondJob) {
////		return new AfterCSVJobListener(jobLauncher, secondJob);
////	}
//
//}
