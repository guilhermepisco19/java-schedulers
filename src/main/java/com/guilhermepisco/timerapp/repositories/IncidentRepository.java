package com.guilhermepisco.timerapp.repositories;

import org.springframework.stereotype.Repository;

import com.guilhermepisco.timerapp.domain.Incident;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IncidentRepository  extends JpaRepository<Incident, Integer>{

}
