package com.pk.et.infra.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class ETRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<T, S, ID> {

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(
			final EntityManager entityManager) {
		return new ETJpaRepositoryFactory(entityManager);
	}

}
