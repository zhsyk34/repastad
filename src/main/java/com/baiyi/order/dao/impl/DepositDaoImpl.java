package com.baiyi.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baiyi.order.dao.DepositDao;
import com.baiyi.order.pojo.Deposit;

@Repository
public class DepositDaoImpl extends CommonsDaoImpl<Deposit> implements DepositDao {

	@SuppressWarnings("unchecked")
	public Deposit find(String name) {
		List<Deposit> list = (List<Deposit>) hibernateTemplate.find("from Deposit as d where d.name = ?", name);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Deposit> findByCurrency(String language) {
		return (List<Deposit>) hibernateTemplate.find("from Deposit as d where d.name like ?", language);
	}

}
