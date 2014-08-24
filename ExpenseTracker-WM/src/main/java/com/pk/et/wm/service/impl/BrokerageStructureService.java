package com.pk.et.wm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.wm.dao.BrokerageStructureDAO;
import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.service.IBrokerageStructureService;

@Service("brokerageStructureService")
public class BrokerageStructureService implements IBrokerageStructureService {
	@Autowired
	// @Qualifier("brokerageStructureDAO")
	private BrokerageStructureDAO brokerageStructureDAO;

	public List<BrokerageStructure> getBrokerList() {
		return this.brokerageStructureDAO.findAll();
	}

	public BrokerageStructure addBrokerage(final BrokerageStructure brokerage) {
		return this.brokerageStructureDAO.save(brokerage);
	}

	public BigDecimal calculateBrokerage(final BigDecimal amount,
			BrokerageStructure structure) {

		structure = this.brokerageStructureDAO.findOne(structure.getId());

		BigDecimal totalFee = BigDecimal.ZERO;

		if (structure != null) {

			totalFee = structure.getMinBrokerage();

			if (amount.multiply(

			structure.getBrokerage().divide(new BigDecimal(100)))

			.compareTo(structure.getMinBrokerage()) > 0) {

				totalFee = amount.multiply(structure.getBrokerage().divide(

				new BigDecimal(100)));

			}

			if (structure.getSerivceTax() != null) {

				totalFee = totalFee.add(totalFee.multiply(structure

				.getSerivceTax().divide(new BigDecimal(100))));

			}

			if (structure.getTransactionTax() != null) {

				totalFee = totalFee.add(amount.multiply(structure

				.getTransactionTax().divide(new BigDecimal(100))));

			}

			if (structure.getOtherCharges() != null) {

				totalFee = totalFee.add(structure.getOtherCharges());

			}

		}

		return totalFee;

	}

}
