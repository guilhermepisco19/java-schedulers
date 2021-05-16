package com.guilhermepisco.timerapp.services.timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class SchedulerMap {
	
	   private final Map<Integer, List<ScheduledFuture<?>>> scheduledTasks = new IdentityHashMap<>();

	//create an object of SingleObject
	   private static SchedulerMap instance = new SchedulerMap();

	   //make the constructor private so that this class cannot be
	   //instantiated
	   private SchedulerMap(){}

	   //Get the only object available
	   public static SchedulerMap getInstance(){
	      return instance;
	   }
	   
	   public Map<Integer, List<ScheduledFuture<?>>> getSchedulerMap() {
			return scheduledTasks;
	   }
	   
	   public List<ScheduledFuture<?>> getScheduledTasks(Integer incId) {
			return scheduledTasks.get(incId);
	   }
	   
	   public void insertTask(Integer incId, ScheduledFuture<?> future) {
		   if(!scheduledTasks.containsKey(incId)) {
			   scheduledTasks.put(incId, new ArrayList<>());
		   }
		   scheduledTasks.get(incId).add(future);
	   }
	   
	   
}
