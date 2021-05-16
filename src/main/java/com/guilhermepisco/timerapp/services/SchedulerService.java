package com.guilhermepisco.timerapp.services;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.guilhermepisco.timerapp.domain.Incident;
import com.guilhermepisco.timerapp.services.timer.ExactReminderTimerTask;
import com.guilhermepisco.timerapp.services.timer.PostReminderTimerTask;
import com.guilhermepisco.timerapp.services.timer.PreReminderTimerTask;
import com.guilhermepisco.timerapp.services.timer.SchedulerMap;

@Service
public class SchedulerService {
	
	@Value("${nextcomm.scheduler.prereminder}")
	private Integer preReminder;
	
	@Value("${nextcomm.scheduler.postreminder}")
	private Integer postReminder;
	
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	public void scheduleReminderEmails(Incident incident) {
		schedulePostReminderEmail(incident);
		schedulePreReminderEmail(incident);
		scheduleExactReminderEmail(incident);
	}
	
	public void rescheduleReminderEmails(Incident incident) {
		cancelFutureSchedulerTasks(incident);
		scheduleReminderEmails(incident);
	}
	
	public void schedulePreReminderEmail(Incident incident) {
		Date startDate = LocalDateTime.fromDateFields(incident.getNextComm()).minusMinutes(preReminder).toDate();
		ScheduledFuture<?> sf = taskScheduler.schedule(new PreReminderTimerTask(incident), startDate);
		SchedulerMap.getInstance().insertTask(incident.getId(), sf);
	}
	
	public void scheduleExactReminderEmail(Incident incident) {
		ScheduledFuture<?> sf = taskScheduler.schedule(new ExactReminderTimerTask(incident), incident.getNextComm());
		SchedulerMap.getInstance().insertTask(incident.getId(), sf);
	}
	
	public void schedulePostReminderEmail(Incident incident) {
		Date startDate = LocalDateTime.fromDateFields(incident.getNextComm()).plusMinutes(postReminder).toDate();
		ScheduledFuture<?> sf = taskScheduler.scheduleAtFixedRate(new PostReminderTimerTask(incident), startDate, postReminder*1000);
		SchedulerMap.getInstance().insertTask(incident.getId(), sf);
	}
	
	public void failSafeScheduleReminderEmails(Incident incident) {
		taskScheduler.scheduleAtFixedRate(new PostReminderTimerTask(incident), incident.getNextComm(), 3000);
		
	}
	
	public void cancelFutureSchedulerTasks(Incident incident) {
		SchedulerMap.getInstance().getScheduledTasks(incident.getId()).forEach(task -> {
			task.cancel(false);
        });
    }
	
}
