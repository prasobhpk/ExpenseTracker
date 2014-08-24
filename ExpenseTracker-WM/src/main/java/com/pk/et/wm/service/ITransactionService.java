package com.pk.et.wm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Transaction;

public interface ITransactionService {
	@Transactional
	Transaction processTransaction(Transaction transaction, User user) throws Exception;
	
	@Transactional(readOnly=true)
	List<Transaction> getByPortfolio(User user,Long portfolioId);
}
