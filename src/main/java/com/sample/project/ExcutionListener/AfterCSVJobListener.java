//package com.sample.project.ExcutionListener;
//
//import org.springframework.batch.core.BatchStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.listener.JobExecutionListenerSupport;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AfterCSVJobListener extends JobExecutionListenerSupport {
//
//	private final JobLauncher jobLauncher;
//	private final Job nextJob; // This will be your blacklist job
//
//	@Autowired
//	public AfterCSVJobListener(JobLauncher jobLauncher, @Qualifier("blackListJob") Job nextJob) {
//		this.jobLauncher = jobLauncher;
//		this.nextJob = nextJob;
//	}
//
//	@Override
//	public void afterJob(JobExecution jobExecution) {
//		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
//			try {
//				JobParameters jobParameters = new JobParametersBuilder()
//						.addLong("time", System.currentTimeMillis()).toJobParameters();
//				jobLauncher.run(nextJob, jobParameters);
//			} catch (JobExecutionException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}