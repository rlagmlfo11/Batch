package com.sample.project.config;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.sample.project.dto.NewCustomerRepository;
import com.sample.project.dto.ResultTableRepository;
import com.sample.project.entity.FirstTable;
import com.sample.project.entity.ResultTable;
import com.sample.project.entity.SecondTable;

@Configuration
@EnableBatchProcessing
public class ResultTableConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public ResultTableRepository resultTableRepository;

	@Autowired
	private DataSource dataSource;

	@Bean(name = "firstTableReader")
	public JdbcCursorItemReader<FirstTable> firstTableReader() {
		JdbcCursorItemReader<FirstTable> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT NAME, GENDER, MAIL, WAGE FROM FIRSTTABLE");
		reader.setRowMapper(new BeanPropertyRowMapper<>(FirstTable.class));
		return reader;
	}

	@Bean(name = "peopleReaderForBlackList")
	public JdbcCursorItemReader<SecondTable> peopleReaderForBlackList() {
		JdbcCursorItemReader<SecondTable> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT GIVENNAME, SEX, PERSONALCONTACT,  PROPERTY FROM SECONDTABLE");
		reader.setRowMapper(new BeanPropertyRowMapper<>(SecondTable.class));
		return reader;
	}

	@Bean(name = "firstTableProcessor")
	public ItemProcessor<FirstTable, ResultTable> firstTableProcessor() {
		return new ItemProcessor<FirstTable, ResultTable>() {
			@Override
			public ResultTable process(FirstTable firstTable) throws Exception {
				ResultTable resultTable = new ResultTable();
				resultTable.setNickName(firstTable.getName());
				resultTable.setOkane(String.valueOf(firstTable.getWage()));
				resultTable.setProxyMail(firstTable.getName());
				resultTable.setSeibetu(firstTable.getGender());

				return resultTable;
			}
		};
	}

	@Bean(name = "secondTableProcess")
	public ItemProcessor<SecondTable, ResultTable> secondTableProcess() {
		return new ItemProcessor<SecondTable, ResultTable>() {
			@Override
			public ResultTable process(SecondTable secondTable) throws Exception {
				ResultTable resultTable = new ResultTable();
				resultTable.setNickName(secondTable.getGivenName());
				resultTable.setOkane(
						String.valueOf(secondTable.getWage()) + " & " + secondTable.getProperty());
				resultTable.setProxyMail(secondTable.getPersonalContact());
				resultTable.setSeibetu(secondTable.getSex());
				return resultTable;
			}
		};
	}

	@Bean(name = "resultTableCsvWriter")
	public FlatFileItemWriter<ResultTable> resultTableCsvWriter() {
		FlatFileItemWriter<ResultTable> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("src/main/resources/ResultTable.csv"));
		writer.setAppendAllowed(true); // Ensure the file is overwritten each time
		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer headerWriter) throws IOException {
				headerWriter.write("NICKNAME,PROXYMAIL,性別,OKANE");
			}
		});
		DelimitedLineAggregator<ResultTable> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<ResultTable> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "nickName", "proxyMail", "seibetu", "okane" });

		lineAggregator.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(lineAggregator);

		return writer;
	}

	@Bean(name = "resultTableDBwriter")
	public RepositoryItemWriter<ResultTable> resultTableDBwriter(NewCustomerRepository repository) {
		RepositoryItemWriter<ResultTable> writer = new RepositoryItemWriter<>();
		writer.setRepository(resultTableRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean(name = "resultTableCompositeItemWriter")
	public CompositeItemWriter<ResultTable> resultTableCompositeItemWriter(
			FlatFileItemWriter<ResultTable> resultTableCsvWriter,
			RepositoryItemWriter<ResultTable> resultTableDBwriter) {
		CompositeItemWriter<ResultTable> writer = new CompositeItemWriter<>();
		writer.setDelegates(Arrays.asList(resultTableCsvWriter, resultTableDBwriter));
		return writer;
	}

	@Bean(name = "firstTableStep")
	public Step firstTableStep(StepBuilderFactory stepBuilderFactory,
			ItemReader<FirstTable> firstTableReader,
			ItemProcessor<FirstTable, ResultTable> firstTableProcessor,
			@Qualifier("resultTableCsvWriter") ItemWriter<ResultTable> resultTableCsvWriter) {
		return stepBuilderFactory.get("firstTableStep").<FirstTable, ResultTable>chunk(10)
				.reader(firstTableReader).processor(firstTableProcessor)
				.writer(resultTableCsvWriter).build();
	}

	@Bean(name = "secondTableStep")
	public Step secondTableStep(StepBuilderFactory stepBuilderFactory,
			ItemReader<SecondTable> secondTableReader,
			ItemProcessor<SecondTable, ResultTable> secondTableProcessor,
			@Qualifier("resultTableCsvWriter") ItemWriter<ResultTable> resultTableCsvWriter) {
		return stepBuilderFactory.get("secondTableStep").<SecondTable, ResultTable>chunk(10)
				.reader(secondTableReader).processor(secondTableProcessor)
				.writer(resultTableCsvWriter).build();
	}

	@Bean(name = "resultTableJob")
	public Job resultTableJob(JobBuilderFactory jobBuilderFactory, Step deleteCsvStep,
			Step firstTableStep, Step secondTableStep) {
		return jobBuilderFactory.get("resultTableJob").incrementer(new RunIdIncrementer())
				.start(deleteCsvStep).next(firstTableStep).next(secondTableStep).build();
	}

	@Bean(name = "deleteCsvStep")
	public Step deleteCsvStep() {
		return stepBuilderFactory.get("deleteCsvStep").tasklet((contribution, chunkContext) -> {
			FileSystemResource resource = new FileSystemResource(
					"src/main/resources/ResultTable.csv");
			if (resource.exists() && resource.isFile()) {
				boolean deleted = resource.getFile().delete();
				if (!deleted) {
					throw new IOException("Could not delete file " + resource.getFilename());
				}
			}
			return RepeatStatus.FINISHED;
		}).build();
	}

}
