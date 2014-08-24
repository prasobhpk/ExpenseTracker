package com.pk.et.wm.dao;

import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.IBrokerageStructureDAO;
import com.pk.et.wm.model.BrokerageStructure;


public interface BrokerageStructureDAO extends ETRepository<BrokerageStructure, Long>,IBrokerageStructureDAO{

}
