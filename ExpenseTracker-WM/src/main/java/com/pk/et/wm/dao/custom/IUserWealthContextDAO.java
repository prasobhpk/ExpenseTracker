package com.pk.et.wm.dao.custom;

import java.util.List;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Portfolio;

public interface IUserWealthContextDAO {
	List<Equity> getFavStocks(User user);
	List<Portfolio> getPortfolios(User user);
}
