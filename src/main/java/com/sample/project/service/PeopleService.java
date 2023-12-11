package com.sample.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.project.dto.PeopleRepository;
import com.sample.project.entity.People;

@Service
public class PeopleService {

	@Autowired
	private PeopleRepository peopleRepository;

	public List<People> getAllPeople() {
		return peopleRepository.findAll();
	}

	public void registerPeople(People people) {
		peopleRepository.save(people);
	}

	public void updatePeople(People people) {
		peopleRepository.save(people);
	}

	public People getPeopleById(Long id) {
		People getPeopleById = peopleRepository.findById(id).get();
		return getPeopleById;
	}

}
