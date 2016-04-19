package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Deposit;

public interface DepositDao extends CommonsDao<Deposit> {

	public Deposit find(String name);
	
	public List<Deposit> findByCurrency(String language);
}
