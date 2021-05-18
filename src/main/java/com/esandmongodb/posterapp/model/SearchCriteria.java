package com.esandmongodb.posterapp.model;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class SearchCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String key;
    private String operation;
    private Object value;
    private Object secondValue;
}
