package com.pk.et.wm.service.impl;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.wm.dao.EquityDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.service.IEquityService;

@Service("equityService")
public class EquityService implements IEquityService {
	// @Qualifier("equityDAO")
	@Autowired(required = true)
	private EquityDAO equityDAO;

	public Equity saveCompany(final Equity equity) {
		return this.equityDAO.save(equity);
	}

	public void saveCompanies(final List<Equity> equities) {
		System.out.println(equities);
		for (final Equity equity : equities) {
			try {
				this.equityDAO.save(equity);
			} catch (final PersistenceException e) {
				System.out.println("Could not save " + equity + " Message>>>"
						+ e.getMessage());
			} catch (final Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public List<Equity> search(final String name) {
		return this.equityDAO.findByNameStartingWith(name);
	}

	public Equity getBySymbol(final String symbol) {
		return this.equityDAO.findBySymbol(symbol);
	}
}
