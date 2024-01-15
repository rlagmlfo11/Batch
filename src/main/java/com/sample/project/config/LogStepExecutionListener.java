package com.sample.project.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogStepExecutionListener extends StepExecutionListenerSupport {

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if ("customerCSVStep".equals(stepExecution.getStepName())) {
			String logFilePath = "src/main/resources/" + stepExecution.getStepName() + "Log.txt";

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, false))) {
				writer.write("STEP_NAME: " + stepExecution.getStepName() + "\n");
				writer.write("START_TIME: " + stepExecution.getStartTime() + "\n");
				writer.write("END_TIME: " + stepExecution.getEndTime() + "\n");
				writer.write("STATUS: " + stepExecution.getStatus() + "\n");
				writer.write("EXIT_CODE: " + stepExecution.getExitStatus().getExitCode() + "\n");
				writer.write("EXIT_MESSAGE: " + stepExecution.getExitStatus().getExitDescription()
						+ "\n");
				writer.write("LAST_UPDATED: " + stepExecution.getLastUpdated() + "\n");
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return stepExecution.getExitStatus();
	}

}