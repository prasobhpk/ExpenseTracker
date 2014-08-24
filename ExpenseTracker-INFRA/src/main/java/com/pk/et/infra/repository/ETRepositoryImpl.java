package com.pk.et.infra.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.Extensible;
import com.pk.et.infra.model.Extension;
import com.pk.et.infra.model.ExtensionValue;

public class ETRepositoryImpl<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements ETRepository<T, ID> {
	private final EntityManager entityManager;
	private final JpaEntityInformation<T, ?> entityInformation;

	public ETRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation,
			final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = entityInformation;
	}

	public ETRepositoryImpl(final Class<T> domainClass,
			final EntityManager entityManager) {
		this(JpaEntityInformationSupport
				.getMetadata(domainClass, entityManager), entityManager);
	}
	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		if (this.entityInformation.isNew(entity)) {
			if (entity instanceof Extensible) {
				final Extensible obj = (Extensible) entity;
				Extension extension = null;
				for (final String attr : obj.getAttributes().keySet()) {
					extension = processExtensions(attr, obj.getClass()
							.getSimpleName());
					if (extension != null) {
						final ExtensionValue extVal = new ExtensionValue();
						extVal.setExtension(extension);
						extVal.setValue("" + obj.getAttributes().get(attr));
						obj.getExtensions().put(extension, extVal);
					}
				}
			}
			this.entityManager.persist(entity);
			return entity;
		} else {
			// check whether the object is extensible
			if (entity instanceof Extensible) {
				final Extensible obj = (Extensible) entity;
				Extension extension = null;
				for (final String attr : obj.getAttributes().keySet()) {
					extension = processExtensions(attr, obj.getClass()
							.getSimpleName());
					if (extension != null) {
						ExtensionValue extVal = null;
						// get the current value
						extVal = getExtensionValue(extension, entity);
						if (extVal == null) {
							extVal = new ExtensionValue();
							extVal.setExtension(extension);
							extVal.setValue("" + obj.getAttributes().get(attr));
						} else {
							extVal.setValue("" + obj.getAttributes().get(attr));
						}
						obj.getExtensions().put(extension, extVal);
					}
				}
			}
			return this.entityManager.merge(entity);
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @param params
	 *            list of attributes to be fetched using join
	 * @return A single entity
	 */
	public T findUniqueByCriteria(final Map<String, ?> paramMap,
			final String... params) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<T> cr = cb.createQuery(this.entityInformation
					.getJavaType());
			final Root<T> root = cr.from(this.entityInformation.getJavaType());
			cr.select(root);
			if (paramMap != null) {
				final List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (final String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (final String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}
			for (final String param : params) {
				root.fetch(param);
			}
			final TypedQuery<T> query = this.entityManager.createQuery(cr);
			return query.getSingleResult();
		} catch (final Exception e) {
			e.printStackTrace();
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
	public List<T> findByCriteria(final Map<String, ?> paramMap,
			final String... params) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<T> cr = cb.createQuery(this.entityInformation
					.getJavaType());
			final Root<T> root = cr.from(this.entityInformation.getJavaType());
			cr.select(root);
			final List<Predicate> predicates = new ArrayList<Predicate>();
			Predicate predicate = null;
			for (final String key : paramMap.keySet()) {
				List<String> objGraphs = Arrays.asList(key.split("\\."));
				Path<Object> path = null;
				if (objGraphs.size() > 0) {
					path = root.get(objGraphs.get(0));
					objGraphs = objGraphs.subList(1, objGraphs.size());
				}
				for (final String objGraph : objGraphs) {
					path = path.get(objGraph);
				}
				predicate = cb.equal(path, paramMap.get(key));
				predicates.add(predicate);
			}
			cr.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			for (final String param : params) {
				root.fetch(param);
			}
			final TypedQuery<T> query = this.entityManager.createQuery(cr);
			return query.getResultList();
		} catch (final Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @return A single entity
	 */
	public T findUniqueByCriteria(final Map<String, ?> paramMap) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<T> cr = cb.createQuery(this.entityInformation
					.getJavaType());
			final Root<T> root = cr.from(this.entityInformation.getJavaType());
			cr.select(root);
			final List<Predicate> predicates = new ArrayList<Predicate>();
			Predicate predicate = null;
			for (final String key : paramMap.keySet()) {
				List<String> objGraphs = Arrays.asList(key.split("\\."));
				Path<Object> path = null;
				if (objGraphs.size() > 0) {
					path = root.get(objGraphs.get(0));
					objGraphs = objGraphs.subList(1, objGraphs.size());
				}
				for (final String objGraph : objGraphs) {
					path = path.get(objGraph);
				}
				predicate = cb.equal(path, paramMap.get(key));
				predicates.add(predicate);
			}
			cr.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			final TypedQuery<T> query = this.entityManager.createQuery(cr);
			return query.getSingleResult();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param paramMap
	 *            Key value pairs of attributes and their values
	 * @return A list entities
	 */
	public List<T> findByCriteria(final Map<String, ?> paramMap) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<T> cr = cb.createQuery(this.entityInformation
					.getJavaType());
			final Root<T> root = cr.from(this.entityInformation.getJavaType());
			cr.select(root);
			if (paramMap != null) {
				final List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (final String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (final String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}

			final TypedQuery<T> query = this.entityManager.createQuery(cr);
			return query.getResultList();
		} catch (final Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public int findCountByCriteria(final Map<String, ?> paramMap) {
		int count = 0;
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<Long> cr = cb.createQuery(Long.class);
			final Root<T> root = cr.from(this.entityInformation.getJavaType());
			cr.select(cb.countDistinct(root));
			if (paramMap != null) {
				final List<Predicate> predicates = new ArrayList<Predicate>();
				Predicate predicate = null;
				for (final String key : paramMap.keySet()) {
					List<String> objGraphs = Arrays.asList(key.split("\\."));
					Path<Object> path = null;
					if (objGraphs.size() > 0) {
						path = root.get(objGraphs.get(0));
						objGraphs = objGraphs.subList(1, objGraphs.size());
					}
					for (final String objGraph : objGraphs) {
						path = path.get(objGraph);
					}
					predicate = cb.equal(path, paramMap.get(key));
					predicates.add(predicate);
				}
				cr.where(cb.and(predicates.toArray(new Predicate[predicates
						.size()])));
			}

			final TypedQuery<Long> query = this.entityManager.createQuery(cr);
			count = query.getSingleResult().intValue();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	public T getReference(final ID id) {
		return this.entityManager.getReference(
				this.entityInformation.getJavaType(), id);
	};

	/***
	 * 
	 * @param attribute
	 * @param entity
	 * @return
	 */
	private Extension processExtensions(final String attribute,
			final String entity) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<Extension> cr = cb.createQuery(Extension.class);
			final Root<Extension> root = cr.from(Extension.class);
			cr.select(root);
			final Predicate predicate1 = cb.equal(root.get("entity"), entity);
			final Predicate predicate2 = cb.equal(root.get("attributeName"),
					attribute);
			cr.where(cb.and(predicate1, predicate2));
			final TypedQuery<Extension> query = this.entityManager
					.createQuery(cr);
			return query.getSingleResult();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private ExtensionValue getExtensionValue(final Extension extension,
			final T entity) {
		try {
			// String entityName = entityInformation.getEntityName();
			final String idAttributeName = this.entityInformation
					.getIdAttribute().getName();
			@SuppressWarnings("unchecked")
			final ID id = (ID) this.entityInformation.getId(entity);
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<ExtensionValue> cr = cb
					.createQuery(ExtensionValue.class);
			@SuppressWarnings("unchecked")
			final Root<T> root = cr.from((Class<T>) entity.getClass());
			final Join<T, ExtensionValue> extns = root.join("extensions");
			cr.select(extns);
			final Predicate predicate1 = cb.equal(
					extns.get("extension").get("id"), extension.getId());
			final Predicate predicate2 = cb
					.equal(root.get(idAttributeName), id);
			cr.where(cb.and(predicate1, predicate2));
			final TypedQuery<ExtensionValue> query = this.entityManager
					.createQuery(cr);
			return query.getSingleResult();
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
