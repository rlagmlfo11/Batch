//package com.sample.project.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BatchSchedulerConfig {
//
//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	@Qualifier("importCustomerJob") // Replace with your actual job bean name
//	private Job firstJob;
//
//	@Autowired
//	@Qualifier("newCustomerJob") // Replace with your actual job bean name
//	private Job secondJob;
//
//	@Autowired
//	@Qualifier("blackListJob") // Replace with your actual job bean name
//	private Job thirdJob;
//
//	@Scheduled(initialDelay = 10000, fixedRate = 50000) // Example schedule for the first job
//	public void runFirstJob() {
//		try {
//			JobParameters jobParameters = new JobParametersBuilder()
//					.addLong("time", System.currentTimeMillis()).toJobParameters();
//			jobLauncher.run(firstJob, jobParameters);
//		} catch (JobExecutionException e) {
//
//		}
//	}
//
//	// Similar methods for secondJob and thirdJob with different schedules
//	// ...
//
//}