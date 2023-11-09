package com.sample.project.config;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.sample.project.dto.PeopleRepository;
import com.sample.project.dto.UpdateRepository;
import com.sample.project.entity.People;
import com.sample.project.entity.Update;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private PeopleRepository peopleRepository;

	@Autowired
	private UpdateRepository updateRepository;

	@Bean
	public JpaPagingItemReader<People> reader() {
		JpaPagingItemReader<People> reader = new JpaPagingItemReader<>();
		reader.setQueryString("SELECT p FROM People p");
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setPageSize(10); // The same as chunk size
		return reader;
	}

	@Bean
	public ItemProcessor<People, People> processor() {
		return new ItemProcessor<People, People>() {
			@Override
			public People process(People person) throws Exception {
				// Look for an update in the Update table
				Optional<Update> update = updateRepository.findById(person.getPersonalNumber());

				if (update.isPresent()) {
					// Apply the update to the person
					person.setPosition(update.get().getPosition());
					// ... apply other updates as needed
				}

				// Return the person with any applied updates
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
								"position" });
					}
				});
			}
		});
		writer.setHeaderCallback(new FlatFileHeaderCallback() {
			public void writeHeader(Writer writer) throws IOException {
				writer.write("PersonalNumber,Name,gender,job,Position");
			}
		});
		return writer;
	}

	@Bean
	public Step updatePeopleStep(JpaPagingItemReader<People> reader,
			ItemProcessor<People, People> processor, FlatFileItemWriter<People> writer) {
		return stepBuilderFactory.get("updatePeopleStep").<People, People>chunk(10).reader(reader)
				.processor(processor).writer(writer).build();
	}

	@Bean
	public Job updatePeopleJob(Step updatePeopleStep) {
		return jobBuilderFactory.get("updatePeopleJob").incrementer(new RunIdIncrementer())
				.flow(updatePeopleStep).end().build();
	}
	
}
