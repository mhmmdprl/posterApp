package com.esandmongodb.posterapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "follower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Follower extends BaseCollection {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String seqName="follewer_sequence";
	@Id
	private Long id;
	
	private String fromAuthorUuid;
	
	private Long toAuthorId;
	
	

}
