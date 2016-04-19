package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DownRecordDao;
import com.baiyi.order.dao.FoodDao;
import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.TemplateDao;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.util.PageBean;

@Service("foodService")
public class FoodServiceImpl implements FoodService {

	@Resource
	private FoodDao foodDao;
	@Resource
	private GenericDao genericDao;
	@Resource
	private TemplateDao templateDao;
	@Resource
	private DownRecordDao downRecordDao;

	public List<Food> findByPageBean(int type, String name, PageBean pageBean) {
		return this.foodDao.findByPage(type, name, pageBean);
	}

	public Food findById(int id) {
		return this.foodDao.findById(id);
	}

	public void saveFood(int id, String name, String shortname, String alias, int type, float price, int mid, String introduce, int adminId, String taste, String necessary) {
		Food food = new Food();
		food.setAddtime(new Date());
		food.setAdminId(adminId);
		if (id > 0) {
			food = foodDao.findById(id);
		}
		food.setName(name);
		food.setShortname(shortname);
		food.setAlias(alias);
		food.setType(type);
		food.setPrice(price);
		food.setMaterialId(mid);
		food.setIntroduce(introduce);
		food.setTaste(taste);
		food.setNecessary(necessary);
		genericDao.saveOrUpdate(food);
		if (id > 0) {
			List<Template> list = templateDao.findByFoodId("cakeId", id);
			if (list != null) {
				for (Template t : list) {
					if (t.getCakeId().equals(id + "") || t.getCakeId().startsWith(id + ",") || t.getCakeId().endsWith("," + id) || t.getCakeId().contains("," + id + ",")) {
						List<DownRecord> dlist = downRecordDao.findBySearch(t.getId(), null, DownRecord.UNDELETE, DownRecord.ISDOWN);
						for (DownRecord d : dlist) {
							d.setIsDown(DownRecord.UNDOWN);
							genericDao.update(d);
						}
					}
				}
			}
		}
	}

	public void deleteById(int id) {
		this.genericDao.deleteByParam("delete from Food as a where a.id=" + id);
	}

	public List<Food> getAllFood() {
		return this.foodDao.getAllFood();
	}

	public void setFoodDao(FoodDao foodDao) {
		this.foodDao = foodDao;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public void setDownRecordDao(DownRecordDao downRecordDao) {
		this.downRecordDao = downRecordDao;
	}

	public List<Food> search(String name, int start, int length) {
		return foodDao.search(name, start, length);
	}

}
