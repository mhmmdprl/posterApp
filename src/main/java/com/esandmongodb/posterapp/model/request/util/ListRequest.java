package com.esandmongodb.posterapp.model.request.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.esandmongodb.posterapp.model.SearchCriteria;

import lombok.Data;


@Data
public class ListRequest {
	
	private List<SearchCriteria> search = new ArrayList<SearchCriteria>();

	private Pageable sortedByCreatedDate= PageRequest.of(0, 3, Sort.by("createdDate"));

	private Pageable sortedByNameDesc = PageRequest.of(0, 3, Sort.by("name").descending());

	private Pageable sortedByUsernameDescNameAsc = PageRequest.of(0, 5,
			Sort.by("username").descending().and(Sort.by("name")));

}
