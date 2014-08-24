package com.pk.et.infra.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import com.pk.et.infra.dao.custom.IUserDAO;
import com.pk.et.infra.model.User;

public class UserDAOImpl extends GenericDAO implements IUserDAO{

	public boolean changePassowrd(Long userId, String oldPassword,
			String newPassword) {
		boolean status = false;
		User user = em.find(User.class,userId);
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			em.merge(user);
			status = true;
		}
		return status;
	}

	public boolean userExists(String userName) {
		Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", userName);
		return findCountByCriteria(User.class,paramMap)>0;
	}
	
//	public static void main(String[] args) {
//		EntityManagerFactory emf=Persistence.createEntityManagerFactory("ExpenseTracker-INFRA");
//		EntityManager em=emf.createEntityManager();
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		JpaCriteriaBuilder cbe = (JpaCriteriaBuilder)cb;
//		CriteriaQuery<Integer> cr = cb.createQuery(Integer.class);
//		Root<User> root=cr.from(User.class);
//		//Expression<Integer> yearExp=cb.function("EXTRACT", Integer.class,cb.literal("YEAR"),cb.currentDate());
//		cr.select(cbe.fromExpression(cbe.toExpression(cb.currentDate()).extract("YEAR")));
//		TypedQuery<Integer> query = em.createQuery(cr);
//		System.out.println(query.getResultList().get(0));
//	}

}
