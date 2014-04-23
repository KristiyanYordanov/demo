package com.krissoft.saa.config;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;

public abstract class BaseMongoObject {
	
	public BaseMongoObject() {
		super();
		this.id = ObjectId.get();
	}

	@Id
	@JsonSerialize(using=ObjectIdSerializer.class)
	private ObjectId id;

}