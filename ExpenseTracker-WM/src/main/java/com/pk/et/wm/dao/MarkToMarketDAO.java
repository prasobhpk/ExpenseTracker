package com.pk.et.wm.dao;

import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.IMarkToMarketDAO;
import com.pk.et.wm.model.MarkToMarket;

public interface MarkToMarketDAO extends ETRepository<MarkToMarket, Long>,
		IMarkToMarketDAO {
}
