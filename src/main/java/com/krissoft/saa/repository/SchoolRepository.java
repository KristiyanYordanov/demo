package com.krissoft.saa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.krissoft.saa.config.School;

public interface SchoolRepository extends MongoRepository<School, String> {
	
}
