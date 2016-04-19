package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DepositDao;
import com.baiyi.order.pojo.Deposit;
import com.baiyi.order.service.DepositService;

@Service("depositService")
public class DepositServiceImpl extends CommonsServiceImpl<Deposit> implements DepositService {

	private DepositDao depositDao;

	@Resource
	public void setDepositDao(DepositDao depositDao) {
		this.depositDao = depositDao;
	}

	public Deposit find(String name) {
		return depositDao.find(name);
	}

	public List<Deposit> findByCurrency(String language) {
		return depositDao.findByCurrency(language);
	}

}
