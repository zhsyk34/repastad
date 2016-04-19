package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.OrderRuleDao;
import com.baiyi.order.dao.impl.CommonsDaoImpl;
import com.baiyi.order.pojo.OrderRule;
import com.baiyi.order.service.OrderRuleService;

@Service("orderRuleService")
public class OrderRuleServiceImpl extends CommonsDaoImpl<OrderRule> implements OrderRuleService {

	private OrderRuleDao orderRuleDao;

	@Resource
	public void setOrderRuleDao(OrderRuleDao orderRuleDao) {
		this.orderRuleDao = orderRuleDao;
	}

	public int count() {
		return orderRuleDao.count();
	}

	public List<OrderRule> findList(int pageNo, int pageSize) {
		return orderRuleDao.findList((pageNo - 1) * pageSize, pageSize);
	}

	public OrderRule findById(int id) {
		return orderRuleDao.findById(id);
	}

	public OrderRule findByUsed(int used) {
		return orderRuleDao.findByUsed(used);
	}

}
