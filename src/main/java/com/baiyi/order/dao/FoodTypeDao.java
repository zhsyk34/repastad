package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.FoodType;

public interface FoodTypeDao extends CommonsDao<FoodType> {

	public List<FoodType> findList(String name, int index, int length);

	public int count(String name);
}
