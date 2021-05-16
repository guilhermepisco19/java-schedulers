package com.guilhermepisco.timerapp;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimerAppApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
	    TimerTask task = new TimerTask() {
	        public void run() {
	            System.out.println("Task performed on: " + new Date() + "n" +
	              "Thread's name: " + Thread.currentThread().getName());
	        }
	    };
	    Timer timer = new Timer("Timer");
	    
	    long delay = 1000L;
	    timer.schedule(task, delay);
	}

}
