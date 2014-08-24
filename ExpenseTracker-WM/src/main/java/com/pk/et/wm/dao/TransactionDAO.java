package com.pk.et.wm.dao;

import java.util.List;

import com.pk.et.infra.model.User;
import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.ITransactionDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.Transaction;

public interface TransactionDAO extends ETRepository<Transaction, Long>,ITransactionDAO{
	 List<Transaction> getByPortfolio(User user,Long portfolioId);
	 List<Transaction> getByPortfolio(User user,Portfolio portfolio,Equity equity);
}
