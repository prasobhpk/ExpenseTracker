package com.pk.et.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.exp.dao.UserExpenseDAO;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.ConfigurationItemDAO;
import com.pk.et.infra.dao.UserDAO;
import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.User;
import com.pk.et.service.ISignUpService;
import com.pk.et.wm.dao.UserWealthContextDAO;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.UserWealthContext;
@Service("signUpService")
public class SignUpService implements ISignUpService{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserExpenseDAO userExpenseDAO;
	
	@Autowired
	private UserWealthContextDAO userWealthContextDAO;
	
	@Autowired
	private ConfigurationItemDAO configurationItemDAO;
	
	@Autowired
	private ConfigurationDAO configurationDAO;
	
	public boolean signUp(User user) {
		   
			try {
				//Create user
				log.debug("Creating a new user ==>{}",user);
				user=userDAO.save(user);
				
				//Set the default user configurations
				log.debug("Get the default user configuration items");
				List<ConfigurationItem> items = configurationItemDAO.findByConfigType(ConfigType.USER);
				Configuration conf=null;
				for (ConfigurationItem item : items) {
					conf=new Configuration();
					conf.setConfigItem(item);
					conf.setConfigItemValue(item.getDefaultValue());
					conf.setUser(user);
					configurationDAO.save(conf);
				}
				
				log.debug("Default configuratios ==>{} added ",items);
				
				//create user expense context
				log.debug("Create expense context for the user ==>{}",user);
				UserExpense userExpense=new UserExpense(user);
				userExpenseDAO.save(userExpense);
				log.debug("User expense context has been created successfully");
				
				
				//create user wealth context
				log.debug("Create wealth context for the user ==>{}",user);
				UserWealthContext wealthContext=new UserWealthContext(user);
				
				//Add the default portfolio
				log.debug("Add the  default portfolio to the wealth context");
				Portfolio portfolio = new Portfolio();
				portfolio.setPortfolioName("Default");
				wealthContext.addPortfolio(portfolio);
				
				userWealthContextDAO.save(wealthContext);
				log.debug("User wealth context has been created successfully");
				
				log.debug("User ==>{} has been created successfully",user);
				
			} catch (Exception e) {
				log.debug("Error while creating user ==>{} ,error details ==>{}",new Object[]{user,e.getMessage()});
			}
			return true;
	}

}
