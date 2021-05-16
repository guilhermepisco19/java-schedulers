package com.guilhermepisco.timerapp.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermepisco.timerapp.domain.Incident;
import com.guilhermepisco.timerapp.services.IncidentService;
import com.guilhermepisco.timerapp.services.SchedulerService;

@RestController
@RequestMapping(value = "/incidents")
public class IncidentResource {

	@Autowired
	private IncidentService service;
	
	@Autowired
	private SchedulerService schedulerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Incident>> findAll(){
		List<Incident> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Incident obj){
		Incident newObj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newObj.getId()).toUri();
		
		schedulerService.scheduleReminderEmails(obj);
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value= "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Incident obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		
		schedulerService.rescheduleReminderEmails(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value= "/{id}/scheduler", method=RequestMethod.DELETE)
	public ResponseEntity<Void> cancelScheduler(@PathVariable Integer id){
		Incident incident = service.findById(id);
		schedulerService.cancelFutureSchedulerTasks(incident);
		
		return ResponseEntity.noContent().build();
	}
	
}
