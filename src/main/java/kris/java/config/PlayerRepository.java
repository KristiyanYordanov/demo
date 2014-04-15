package kris.java.config;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<PlayerDoc, String> {
	
    public List<PlayerDoc> findByName(String name);
    public List<PlayerDoc> findByName(String name, Sort sort);
    public List<PlayerDoc> findByLocation(String location);
//    public List<PlayerDoc> findOne(Query query2);
    
    @Query("{ 'name':{$regex:?0,$options:'i'}}") 
    public List<PlayerDoc> findByNameRegex(String name);
    List<PlayerDoc> findAll(Sort sort);
    

}