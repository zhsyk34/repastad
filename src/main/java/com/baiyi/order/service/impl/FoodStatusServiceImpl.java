package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.FoodStatusDao;
import com.baiyi.order.pojo.FoodStatus;
import com.baiyi.order.service.FoodStatusService;

@Service("foodStatusService")
public class FoodStatusServiceImpl implements FoodStatusService {

	@Resource
	private FoodStatusDao foodStatusDao;

	public void delete(Integer id) {
		foodStatusDao.delete(id);
	}

	public void delete(Integer[] ids) {
		foodStatusDao.delete(ids);
	}

	public FoodStatus find(Integer id) {
		return foodStatusDao.find(id);
	}

	public FoodStatus find(Integer foodId, Integer terminalId) {
		return foodStatusDao.find(foodId, terminalId);
	}

	public List<FoodStatus> findList(String foodName, String terminalNo, Integer pattern) {
		return foodStatusDao.findList(foodName, terminalNo, pattern);
	}

	public void merge(FoodStatus foodStatus) {
		foodStatusDao.merge(foodStatus);
	}

	public void save(FoodStatus foodStatus) {
		foodStatusDao.save(foodStatus);
	}

	@Resource
	public void setFoodStatusDao(FoodStatusDao foodStatusDao) {
		this.foodStatusDao = foodStatusDao;
	}

	public void update(FoodStatus foodStatus) {
		foodStatusDao.update(foodStatus);
	}

	public List<FoodStatus> findList() {
		return foodStatusDao.findList();
	}

	public List<FoodStatus> findList(String terminalNo) {
		return foodStatusDao.findList(terminalNo);
	}
}
