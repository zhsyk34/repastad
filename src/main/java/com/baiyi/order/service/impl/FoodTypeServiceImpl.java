package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.FoodTypeDao;
import com.baiyi.order.dao.impl.CommonsDaoImpl;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.service.FoodTypeService;

@Service("foodTypeService")
public class FoodTypeServiceImpl extends CommonsDaoImpl<FoodType> implements FoodTypeService {

	private FoodTypeDao foodTypeDao;

	@Resource
	public void setFoodTypeDao(FoodTypeDao foodTypeDao) {
		this.foodTypeDao = foodTypeDao;
	}

	public int count(String name) {
		return foodTypeDao.count(name);
	}

	public List<FoodType> findList(String name, int pageNo, int pageSize) {
		return foodTypeDao.findList(name, (pageNo - 1) * pageSize, pageSize);
	}

}
