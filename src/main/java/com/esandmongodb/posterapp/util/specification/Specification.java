package com.esandmongodb.posterapp.util.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.esandmongodb.posterapp.model.SearchCriteria;

public class Specification {

	public Query toQuery(List<SearchCriteria> searchCriterias) {
		Query query = new Query();
		List<Criteria> specCriteria = new ArrayList<Criteria>();
		for (SearchCriteria searchCriteria : searchCriterias) {

			if (searchCriteria.getOperation().equalsIgnoreCase(":")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey()).is(searchCriteria.getValue().toString()));

			}
			if (searchCriteria.getOperation().equalsIgnoreCase("%")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey())
						.regex(".*" + searchCriteria.getValue().toString() + ".*"));

			}
			if (searchCriteria.getOperation().equalsIgnoreCase(">")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey())
						.gt( searchCriteria.getValue()));

			}
			if (searchCriteria.getOperation().equalsIgnoreCase("<")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey())
						.lt( searchCriteria.getValue()));

			}
			
			if (searchCriteria.getOperation().equalsIgnoreCase("in")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey())
						.in( searchCriteria.getValue()));
				
			}
			if (searchCriteria.getOperation().equalsIgnoreCase("<>")) {

				specCriteria.add(Criteria.where(searchCriteria.getKey()).lt(searchCriteria.getValue()).gt(searchCriteria.getSecondValue()));
				
			}
		
		}
		return query.addCriteria(new Criteria().andOperator(specCriteria.toArray(new Criteria[specCriteria.size()])));

	}
}
