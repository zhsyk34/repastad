package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.OrderRule;

public interface OrderRuleDao extends CommonsDao<OrderRule> {

	public List<OrderRule> findList(int index, int length);

	public OrderRule findById(int id);

	public OrderRule findByUsed(int used);

	public int count();
}
