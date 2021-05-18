package com.esandmongodb.posterapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCollection implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;

	private Character deleted = '0';
	@CreatedDate
	private Date createdDate;
	@CreatedBy
	private Long createdBy;
	@LastModifiedDate
	private Date updatedDate;
	@LastModifiedBy
	private Long updatedBy;

	private Character banStatus = '0';

	protected BaseCollection() {
		this.uuid = UUID.randomUUID().toString();
	}

}
