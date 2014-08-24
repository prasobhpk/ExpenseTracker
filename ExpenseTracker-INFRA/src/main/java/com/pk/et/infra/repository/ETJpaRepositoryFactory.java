package com.pk.et.infra.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;

public class ETJpaRepositoryFactory extends JpaRepositoryFactory {
	private final EntityManager entityManager;

	public ETJpaRepositoryFactory(final EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
			final RepositoryMetadata metadata, final EntityManager entityManager) {

		final JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata
				.getDomainType());

		return new ETRepositoryImpl(entityInformation, entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {

		return ETRepositoryImpl.class;
	}
}
