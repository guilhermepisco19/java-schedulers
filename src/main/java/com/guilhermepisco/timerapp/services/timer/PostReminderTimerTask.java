package com.guilhermepisco.timerapp.services.timer;

import java.util.Date;

import com.guilhermepisco.timerapp.domain.Incident;

public class PostReminderTimerTask implements Runnable {

	private Incident incident;
	
	public PostReminderTimerTask(Incident incident) {
		super();
		this.incident = incident;
	}
	
	public Incident getIncident() {
		return incident;
	}

	@Override
	public void run() {
		System.out.println("Post reminder sent at: " 
		          + new Date() + " | Incident: " + incident.getId() + " - " + incident.getShortDescription() );
	}

}
