package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Deposit;

public interface DepositService extends CommonsService<Deposit> {

	public Deposit find(String name);

	public List<Deposit> findByCurrency(String language);
}
