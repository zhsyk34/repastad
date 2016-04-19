package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.FoodStatus;

public interface FoodStatusDao extends CommonsDao<FoodStatus> {

	public FoodStatus find(Integer foodId, Integer terminalId);

	public List<FoodStatus> findList(String terminalNo);

	public List<FoodStatus> findList(String foodName, String terminalNo, Integer pattern);
}
