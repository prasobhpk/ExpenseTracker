package com.pk.et.wm.dao.custom.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.wm.dao.custom.IEquityDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Equity_;
@Repository("equityDAO")
public class EquityDAOImpl extends GenericDAO implements IEquityDAO{

	public List<Equity> findByNameStartingWith(String name) {
		List<Equity> equityList = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Equity> cr = cb.createQuery(Equity.class);
			Root<Equity> root = cr.from(Equity.class);
			cr.select(root);
			Predicate codeRes = cb.like(cb.lower(root.get(Equity_.name)), name.toLowerCase()+"%");
			cr.where(codeRes);
			TypedQuery<Equity> query = em.createQuery(cr);
			//query.setHint("fetchgrp","value" );
			equityList = query.getResultList();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return equityList;
	}

	public Equity findBySymbol(String code) {
		Equity equity = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Equity> cr = cb.createQuery(Equity.class);
			Root<Equity> root = cr.from(Equity.class);
			cr.select(root);
			Predicate codeRes = cb.equal(root.get(Equity_.symbol), code);
			cr.where(codeRes);
			TypedQuery<Equity> query = em.createQuery(cr);
			equity = query.getSingleResult();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return equity;
	}
}
