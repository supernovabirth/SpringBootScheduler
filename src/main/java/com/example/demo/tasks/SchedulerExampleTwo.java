package com.example.demo.tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerExampleTwo {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
	
	@Value(value = "${max.thread.size}")
	private int nThreads;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron = "${cron.schedule.now}")
	public void performScheduledTasks() {
		logger.info("CRON Job started at " + dateFormat.format(new Date())
					+ " Thread pool size: " + nThreads);
		
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
		
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		
		List<String> taskNames = new ArrayList<String>();
		taskNames.add("Test Task 1");
		taskNames.add("Test Task 2");
		
		taskNames.forEach(taskName -> {
			futures.add(completionService.submit(new MyTask(taskName)));
		});
		
	}

}
