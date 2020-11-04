package com.example.demo.tasks;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTask implements Callable<Boolean>{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private String taskName;

	public MyTask(String taskName) {
		super();
		this.taskName = taskName;
	}

	@Override
	public Boolean call() throws Exception {
		logger.info("Task: " + taskName + " Thread id: " + Thread.currentThread().getId());
		return true;
	}
	
}
