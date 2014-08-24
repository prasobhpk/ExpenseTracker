package com.pk.et.infra.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ETRepository<T, ID extends Serializable> extends
		JpaRepository<T, ID> {

	T findUniqueByCriteria(Map<String, ?> paramMap, String... params);

	List<T> findByCriteria(Map<String, ?> paramMap, String... params);

	T findUniqueByCriteria(Map<String, ?> paramMap);

	List<T> findByCriteria(Map<String, ?> paramMap);

	int findCountByCriteria(Map<String, ?> paramMap);

	T getReference(ID id);
}
