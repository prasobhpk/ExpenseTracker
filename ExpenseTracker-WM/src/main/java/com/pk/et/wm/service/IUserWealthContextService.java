package com.pk.et.wm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Equity;

public interface IUserWealthContextService {
	@Transactional
	void addFavStock(String cmpCode,User user);
	
	@Transactional
	void deleteFavStock(String cmpCode,User user);
	
	@Transactional
	List<Equity> getFavStocks(User user);
}
