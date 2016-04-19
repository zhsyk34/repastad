package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.util.PageBean;

public interface FoodDao {

	List<Food> findByPage(int type, String name, PageBean pageBean);

	Food findById(int id);

	List<Food> getAllFood();

	List<Food> search(String name, int start, int length);
}
