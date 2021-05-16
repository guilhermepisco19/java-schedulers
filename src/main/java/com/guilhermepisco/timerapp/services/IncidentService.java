package com.guilhermepisco.timerapp.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermepisco.timerapp.domain.Incident;
import com.guilhermepisco.timerapp.repositories.IncidentRepository;
import com.guilhermepisco.timerapp.services.exceptions.ObjectNotFoundException;

@Service
public class IncidentService {

	@Autowired
	private IncidentRepository repo;
	
	public List<Incident> findAll() {
		return repo.findAll();
	}
	
	public Incident findById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Incident not found!"));
	}
	
	public Incident insert(Incident obj) {
		obj.setInstant(new Date());
		obj.setLastUpdate(new Date());
		return repo.save(obj);
	}
	
	public Incident update(Incident obj) {
		Incident incident = findById(obj.getId());
		incident.setLastUpdate(new Date());
		incident.setNextComm(obj.getNextComm());
		return repo.save(incident);
	}
}
