package com.managersystem.sisclinica.api.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class Ordenacao {

	public void ordenacao(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<?> criteria,
			Root<?> root) {
		Sort sort = pageable.getSort();
		if (sort != null && sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			if (order.isAscending()) {
				criteria.orderBy(builder.asc(root.get(property)));  
			} else {					
				criteria.orderBy(builder.desc(root.get(property)));  
			}
		}
	}
	
}
