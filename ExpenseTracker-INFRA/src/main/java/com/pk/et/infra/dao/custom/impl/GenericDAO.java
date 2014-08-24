package com.pk.et.infra.dao.custom.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.jpa.JpaCriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pk.et.infra.util.DatePart;


/***
 * 
 * @author PrasobhP Core data access object implementation
 * @param <T>
 *            Entity type
 * @param <I>
 *            ID type of Entity
 */
@Repository
public abstract class GenericDAO{
	protected final Logger log = LoggerFactory.getLogger(getClass());


	@PersistenceContext
	protected EntityManager em;

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @param params
	 *            list of attributes to be fetched using join
	 * @return A single entity
	 */
	protected <T> T findUniqueByCriteria(Class<T> prototype, Map<String, ?> paramMap, String... params) {
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> cr = cb.createQuery(prototype);
			Root<T> root = cr.from(prototype);
			cr.select(root);
			if (paramMap != null) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}
			for (String param : params) {
				root.fetch(param);
			}
			TypedQuery<T> query = em.createQuery(cr);
			return query.getSingleResult();
		} catch (Exception e) {
			log.debug("Exception : {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @param params
	 *            list of attributes to be fetched using join
	 * @return A list of entities
	 */
	protected <T> List<T> findByCriteria(Class<T> prototype,Map<String, ?> paramMap, String... params) {
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> cr = cb.createQuery(prototype);
			Root<T> root = cr.from(prototype);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			Predicate predicate = null;
			for (String key : paramMap.keySet()) {
				List<String> objGraphs = Arrays.asList(key.split("\\."));
				Path<Object> path = null;
				if (objGraphs.size() > 0) {
					path = root.get(objGraphs.get(0));
					objGraphs = objGraphs.subList(1, objGraphs.size());
				}
				for (String objGraph : objGraphs) {
					path = path.get(objGraph);
				}
				predicate = cb.equal(path, paramMap.get(key));
				predicates.add(predicate);
			}
			cr.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			for (String param : params) {
				root.fetch(param);
			}
			TypedQuery<T> query = em.createQuery(cr);
			return query.getResultList();
		} catch (Exception e) {
			log.debug("Exception : {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @return A single entity
	 */
	protected <T> T findUniqueByCriteria(Class<T> prototype,Map<String, ?> paramMap) {
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> cr = cb.createQuery(prototype);
			Root<T> root = cr.from(prototype);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			Predicate predicate = null;
			for (String key : paramMap.keySet()) {
				List<String> objGraphs = Arrays.asList(key.split("\\."));
				Path<Object> path = null;
				if (objGraphs.size() > 0) {
					path = root.get(objGraphs.get(0));
					objGraphs = objGraphs.subList(1, objGraphs.size());
				}
				for (String objGraph : objGraphs) {
					path = path.get(objGraph);
				}
				predicate = cb.equal(path, paramMap.get(key));
				predicates.add(predicate);
			}
			cr.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			TypedQuery<T> query = em.createQuery(cr);
			return query.getSingleResult();
		} catch (Exception e) {
			log.debug("Exception : {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @return A list entities
	 */
	protected <T> List<T> findByCriteria(Class<T> prototype,Map<String, ?> paramMap) {
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> cr = cb.createQuery(prototype);
			Root<T> root = cr.from(prototype);
			cr.select(root);
			if (paramMap != null) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}

			TypedQuery<T> query = em.createQuery(cr);
			return query.getResultList();
		} catch (Exception e) {
			log.debug("Exception : {}", e.getMessage());
			return Collections.emptyList();
		}
	}
	protected <T> int findCountByCriteria(Class<T> prototype,Map<String, ?> paramMap) {
		int count=0;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Long> cr = cb.createQuery(Long.class);
			Root<T> root = cr.from(prototype);
			cr.select(cb.countDistinct(root));
			if (paramMap != null) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}

			TypedQuery<Long> query = em.createQuery(cr);
			count= query.getSingleResult().intValue();
		} catch (Exception e) {
			log.debug("Exception : {}", e.getMessage());
		}
		return count;
	}
	
	protected <T> T getReference(Class<T> prototype,Serializable entityId) {
		return em.getReference(prototype, entityId);
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return this.em.getCriteriaBuilder();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> Expression<T> getDatePart(CriteriaBuilder cb,Path<Date> datePath,DatePart datePart){
		//JpaCriteriaBuilder interface allows access to EclipseLink(2.4) specific functionality
		JpaCriteriaBuilder cbe = (JpaCriteriaBuilder)cb;
		return cbe.fromExpression(cbe.toExpression(datePath).extract(datePart.name()));
	}

}
