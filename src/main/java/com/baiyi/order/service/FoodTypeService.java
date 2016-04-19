package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.FoodType;

public interface FoodTypeService extends CommonsService<FoodType> {

	public List<FoodType> findList(String name, int pageNo, int pageSize);

	public int count(String name);

}
