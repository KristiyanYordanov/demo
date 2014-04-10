package kris.java.config;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<PlayerDoc, String> {
	
    public PlayerDoc findByName(String name);
    public List<PlayerDoc> findByLocation(String location);

}