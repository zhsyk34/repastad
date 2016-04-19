package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.util.PageBean;

public interface FoodService {

	List<Food> findByPageBean(int type, String name, PageBean pageBean);

	Food findById(int id);

	void saveFood(int id, String name, String shortname, String alias, int type, float price, int mid, String introduce, int adminid, String taste, String necessary);

	void deleteById(int id);

	List<Food> getAllFood();

	List<Food> search(String name, int start, int length);
}
