package kris.java.config;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<PlayerDoc, String> {
	
	@Query("{ 'name' : ?0 }")
	Page<PlayerDoc> findByCustomQuery(String firstname, Pageable pageable);
	
    public Page<PlayerDoc> findByName(String name,  Pageable pageable);
    public Page<PlayerDoc> findByLocation(String location, Pageable pageable);
//    public List<PlayerDoc> findOne(Query query2);
    
    @Query("{ 'name':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findByNameRegex(String name, Pageable pageable);
    
    @Query("{ 'name':{$regex:?0,$options:'i'}}") 
    public List<PlayerDoc> findByNameRegex(String name, Sort sort);
    
    
    
    @Query(value="{ 'name':{$regex:?0,$options:'i'}}", count = true) 
    long count(String name);
}