package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.FoodStatus;

public interface FoodStatusService {

	public void save(FoodStatus foodStatus);

	public void delete(Integer id);

	public void delete(Integer[] ids);

	public void update(FoodStatus foodStatus);

	public void merge(FoodStatus foodStatus);

	public FoodStatus find(Integer id);

	public FoodStatus find(Integer foodId, Integer terminalId);

	public List<FoodStatus> findList();
	
	public List<FoodStatus> findList(String terminalNo);

	public List<FoodStatus> findList(String foodName, String terminalNo, Integer pattern);

}
