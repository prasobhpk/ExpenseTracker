package com.pk.et.wm.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.wm.dao.BrokerageStructureDAO;
import com.pk.et.wm.dao.EquityDAO;
import com.pk.et.wm.dao.HoldingDAO;
import com.pk.et.wm.dao.PortfolioDAO;
import com.pk.et.wm.dao.TransactionDAO;
import com.pk.et.wm.dao.UserWealthContextDAO;
import com.pk.et.wm.exceptions.WMException;
import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.Transaction;
import com.pk.et.wm.model.TransactionType;
import com.pk.et.wm.model.UserWealthContext;
import com.pk.et.wm.service.ITransactionService;

@Service("transactionService")
public class TransactionService implements ITransactionService {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	// @Qualifier("wealthContextDAO")
	private UserWealthContextDAO wealthContextDAO;

	@Autowired(required = true)
	// @Qualifier("portfolioDAO")
	private PortfolioDAO portfolioDAO;

	@Autowired(required = true)
	// @Qualifier("transactionDAO")
	private TransactionDAO transactionDAO;

	// @Qualifier("equityDAO")
	@Autowired(required = true)
	private EquityDAO equityDAO;

	@Qualifier("holdingDAO")
	@Autowired(required = true)
	private HoldingDAO holdingDAO;

	@Autowired
	// @Qualifier("brokerageStructureDAO")
	private BrokerageStructureDAO brokerageStructureDAO;

	public Transaction processTransaction(final Transaction transaction,
			final User user) throws Exception {
		try {
			final UserWealthContext wealthContext = this.wealthContextDAO
					.findByUserId(user.getId());
			final Portfolio portfolio = this.portfolioDAO
					.getReference(transaction.getPortfolio().getId());
			final Equity equity = this.equityDAO.getReference(transaction
					.getInstrument().getId());
			final BrokerageStructure structure = this.brokerageStructureDAO
					.getReference(transaction.getBrokerageStructure().getId());
			transaction.setPortfolio(portfolio);
			transaction.setInstrument(equity);
			transaction.setBrokerageStructure(structure);

			// Process Holding
			Holding holding = this.holdingDAO.getHolding(user, equity,
					portfolio);
			if (holding == null) {
				holding = new Holding();
				List<Transaction> txns = this.transactionDAO.getByPortfolio(
						user, portfolio, equity);
				if (txns.size() == 0) {
					txns = new ArrayList<Transaction>();
				}
				txns.add(transaction);
				holding.setInstrument(equity);
				holding.setWealthContext(wealthContext);
				holding.setPortfolio(portfolio);
				holding.setBrokerageStructure(structure);
				for (final Transaction txn : txns) {
					holding = updateHoldingDetails(holding, txn);
				}
				this.holdingDAO.save(holding);
			} else {
				holding = updateHoldingDetails(holding, transaction);
			}
			return this.transactionDAO.save(transaction);
		} catch (final WMException e) {
			throw e;
		} catch (final Exception e) {
			throw e;
		}
	}

	public List<Transaction> getByPortfolio(final User user,
			final Long portfolioId) {
		return this.transactionDAO.getByPortfolio(user, portfolioId);
	}

	private Holding updateHoldingDetails(final Holding holding,
			final Transaction txn) throws WMException {

		switch (txn.getType()) {
		case BUY:
			holding.setAmount(holding.getAmount().add(txn.getAmount()));
			holding.setQuantity(holding.getQuantity().add(txn.getQuantity()));
			holding.setTotalAmount(holding.getTotalAmount().add(
					txn.getTotalAmount()));
			break;
		case SELL:
			if (txn.getQuantity().compareTo(holding.getQuantity()) == 1) {
				throw new WMException(
						"Unable to process transaction : transaction quantity shold not be greater than the available quantity");
			}
			holding.setAmount(holding.getAmount().subtract(txn.getAmount()));
			holding.setQuantity(holding.getQuantity().subtract(
					txn.getQuantity()));
			holding.setTotalAmount(holding.getTotalAmount().subtract(
					txn.getTotalAmount()));
			holding.setProfit(holding.getProfit().add(txn.getProfit()));
			break;
		}
		try {

			holding.setBrokerage(holding.getBrokerage().add(txn.getBrokerage()));
			holding.setPrice(holding.getAmount().divide(holding.getQuantity(),
					2, RoundingMode.HALF_UP));
		} catch (final ArithmeticException e) {
			if (txn.getType() == TransactionType.SELL) {
				holding.setPrice(BigDecimal.ZERO);
				holding.setAmount(BigDecimal.ZERO);
			}
		}
		return holding;

	}

}
