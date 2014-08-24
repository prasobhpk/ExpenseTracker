package com.pk.et.wm.init;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.wm.dao.BrokerageStructureDAO;
import com.pk.et.wm.model.BrokerageStructure;

@Component
public class WmInit {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private BrokerageStructureDAO brokerageStructureDAO;

	@PostConstruct
	@Transactional
	private void init() {
		final Map<String, String> paramMap = new HashMap<String, String>(1);
		paramMap.put("institution", "HDFC");
		try {
			if (this.brokerageStructureDAO.findCountByCriteria(paramMap) == 0) {
				this.log.debug("Setting up the default brokerage for HDFC");
				final BrokerageStructure defaultBrokerage = new BrokerageStructure();
				defaultBrokerage.setBrokerage(new BigDecimal(0.50));
				defaultBrokerage.setInstitution("HDFC");
				defaultBrokerage.setMinBrokerage(new BigDecimal(25));
				defaultBrokerage.setOtherCharges(new BigDecimal(0));
				defaultBrokerage.setSerivceTax(new BigDecimal(10.30));
				defaultBrokerage.setTransactionTax(new BigDecimal(0.13));
				this.brokerageStructureDAO.save(defaultBrokerage);
			}
		} catch (final Exception e) {
			this.log.error("Error :{}", e.getMessage());
		}
	}

}
