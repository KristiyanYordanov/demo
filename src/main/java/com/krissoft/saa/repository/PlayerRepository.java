package com.krissoft.saa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.krissoft.saa.config.PlayerDoc;

public interface PlayerRepository extends MongoRepository<PlayerDoc, String> {
	
	@Query("{ 'name' : ?0 }")
	Page<PlayerDoc> findByCustomQuery(String firstname, Pageable pageable);
	
    public Page<PlayerDoc> findByName(String name,  Pageable pageable);
    public Page<PlayerDoc> findByLocation(String location, Pageable pageable);
//    public List<PlayerDoc> findOne(Query query2);
    
    @Query("{ 'name':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findByNameRegex(String name, Pageable pageable);
    
    @Query("{ 'state':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findByStateRegex(String state, Pageable pageable);
    
    
    @Query("{ 'schoolName':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findBySchoolNameRegex(String schoolName, Pageable pageable);
    
    @Query("{ 'schoolCity':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findBySchoolCityRegex(String schoolCity, Pageable pageable);
    
    @Query("{ 'pos':{$regex:?0,$options:'i'}}") 
    public Page<PlayerDoc> findByPosRegex(String pos, Pageable pageable);
    
    
    @Query("{ 'name':{$regex:?0,$options:'i'}}") 
    public List<PlayerDoc> findByNameRegex(String name, Sort sort);
    //Page<PlayerDoc> findByNameAndSchoolNameRegex(String name, String schoolName,  Pageable pageable);
    @Query("'$and':[{'name':{'$regex':?0,'$options':'i'}},{'schoolName':{'$regex':?0,'$options':'i'}}]")
    public Page<PlayerDoc> findByNameAndSchoolNameRegex(String name, String schoolName,  Pageable pageable);
    
    public Page<PlayerDoc> findByNameLikeAndStateLikeAndSchoolNameLikeAndSchoolCityLikeAndPosLike(String name, String state, String schoolName, String schoolCity, String pos, Pageable pageable);
    
    public Page<PlayerDoc> findByWeight(org.springframework.data.mongodb.core.query.Query query, Pageable pageable);
    
    @Query(value="{ 'name':{$regex:?0,$options:'i'}}", count = true) 
    long count(String name);
}