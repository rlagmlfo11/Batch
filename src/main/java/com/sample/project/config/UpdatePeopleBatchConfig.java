package com.sample.project.config;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.sample.project.dto.UpdateRepository;

import com.sample.project.entity.People;
import com.sample.project.entity.Update;

@Configuration
@EnableBatchProcessing
public class UpdatePeopleBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private UpdateRepository updateRepository;

	@Bean
	public JpaPagingItemReader<People> reader() {
		JpaPagingItemReader<People> reader = new JpaPagingItemReader<>();
		reader.setQueryString("SELECT p FROM People p");
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setPageSize(10);
		return reader;
	}

	@Bean
	public ItemProcessor<People, People> processor() {
		return new ItemProcessor<People, People>() {

			@Override
			public People process(People person) throws Exception {
				Optional<Update> update = updateRepository.findById(person.getPersonalNumber());

				if (update.isPresent()) {
					Update updateData = update.get();
					if ("2".equals(updateData.getKubun())) {
						return null;
					} else {
						person.setPosition(updateData.getPosition());
						person.setGender(updateData.getGender());

						String[] setupParts = updateData.getSetup().split("\\|");
						if (setupParts.length == 4) {
							person.setSetup1(setupParts[0]);
							person.setSetup2(setupParts[1]);
							person.setSetup3(setupParts[2]);
							person.setSetup4(setupParts[3]);
						}
					}
				}
				return person;
			}
		};
	}

	@Bean
	public FlatFileItemWriter<People> writer() {
		FlatFileItemWriter<People> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("src/main/resources/updatedPeople.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<People>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<People>() {
					{
						setNames(new String[] { "personalNumber", "name", "gender", "job",
								"position", "setup1", "setup2", "setup3", "setup4" });
					}
				});
			}
		});
		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			public void writeHeader(Writer writer) throws IOException {
				writer.write("number,name,gender,job,position,set1,set2,set3,set4");
			}
		});
		return writer;
	}

//	@Bean(name = "UpdateBean4")
//	public Step updatePeopleStep(JpaPagingItemReader<People> reader,
//			ItemProcessor<People, People> processor, FlatFileItemWriter<People> writer) {
//		return stepBuilderFactory.get("updatePeopleStep").<People, People>chunk(10).reader(reader)
//				.processor(processor).writer(writer).build();
//	}
//
//	@Bean(name = "UpdateBean5")
//	public Job updatePeopleJob(Step updatePeopleStep) {
//		return jobBuilderFactory.get("updatePeopleJob").incrementer(new RunIdIncrementer())
//				.flow(updatePeopleStep).end().build();
//	}
	@Bean
	public Step updatePeopleStep(JpaPagingItemReader<People> reader,
			ItemProcessor<People, People> processor, FlatFileItemWriter<People> writer) {
		return stepBuilderFactory.get("updatePeopleStep").<People, People>chunk(10).reader(reader)
				.processor(processor).writer(writer).build();
	}

	@Bean
	public Job updatePeopleJob(@Qualifier("updatePeopleStep") Step updatePeopleStep) {
		return jobBuilderFactory.get("updatePeopleJob").incrementer(new RunIdIncrementer())
				.flow(updatePeopleStep).end().build();
	}

}
