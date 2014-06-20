package com.krissoft.saa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.krissoft.saa.config.PlayerDoc;

public interface PlayerRepository extends MongoRepository<PlayerDoc, String> {
    
}