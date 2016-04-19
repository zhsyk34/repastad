package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.OrderRule;

public interface OrderRuleService extends CommonsService<OrderRule> {

	public List<OrderRule> findList(int pageNo, int pageSize);

	public OrderRule findById(int id);

	public OrderRule findByUsed(int used);

	public int count();
}
