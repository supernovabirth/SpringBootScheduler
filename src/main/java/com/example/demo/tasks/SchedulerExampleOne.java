package com.example.demo.tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
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
public class SchedulerExampleOne {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
	
	@Value(value = "${max.thread.size}")
	private int nThreads;
	
	private static final long EXECUTION_TIME = 5000L;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Scheduled(cron = "*10 * * * * *")
	public void performScheduledTasks() {
		logger.info("CRON Job started at " + dateFormat.format(new Date())
					+ " Thread pool size: " + nThreads);
		
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
		
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		
		futures.add(completionService.submit(new Callable<Boolean>() {
			
			@Override
			public Boolean call() throws Exception {
				Thread.sleep(EXECUTION_TIME);
				logger.info("Task A. Thread id: " + Thread.currentThread().getId());
				return null;
			}
		
		}));
		
		futures.add(completionService.submit(new Callable<Boolean>() {
			
			@Override
			public Boolean call() throws Exception {
				Thread.sleep(EXECUTION_TIME);
				logger.info("Task B. Thread id: " + Thread.currentThread().getId());
			
				return null;
			}
		
			
		}));
	}

}
