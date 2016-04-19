package com.baiyi.order.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.FoodTypeService;
import com.baiyi.order.util.ValidateUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FoodTypeAction extends ActionSupport {

	private FoodTypeService foodTypeService;

	private FoodService foodService;

	private FoodType foodType;

	private Integer id;

	private Integer[] ids;

	private String name;

	private String searchName;

	private List<FoodType> foodTypeList;

	private int pageNo;

	private int pageSize;

	private int count;

	private int pageCount;

	private String message;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public FoodType getFoodType() {
		return foodType;
	}

	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	public List<FoodType> getFoodTypeList() {
		return foodTypeList;
	}

	public void setFoodTypeList(List<FoodType> foodTypeList) {
		this.foodTypeList = foodTypeList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Resource
	public void setFoodTypeService(FoodTypeService foodTypeService) {
		this.foodTypeService = foodTypeService;
	}

	public String save() {
		if (ValidateUtil.isStrictNotEmpty(name)) {
			foodType = new FoodType();
			foodType.setTypeName(name);
			foodType.setAddtime(new Date());
			foodTypeService.save(foodType);
			message = "save";
		}
		return SUCCESS;
	}

	public String delete() {
		List<Food> foodList = foodService.getAllFood();

		if (ValidateUtil.isNotEmpty(ids)) {
			if (foodList != null && foodList.size() > 0) {
				for (int i = 0; i < foodList.size(); i++) {
					int foodId = foodList.get(i).getType();
					for (int j = 0; j < ids.length; j++) {
						if (foodId == ids[j]) {
							message = "used";
							return SUCCESS;
						}
					}
				}
			}

			foodTypeService.delete(ids);
			message = "delsuccess";
		}
		return SUCCESS;
	}

	public String update() {
		foodType = foodTypeService.find(id);
		if (ValidateUtil.isStrictNotEmpty(name)) {
			foodType.setTypeName(name);
			foodTypeService.update(foodType);
		}
		return SUCCESS;
	}

	public String merge() {
		if (id != null && id > 0) {
			foodType = foodTypeService.find(id);
		} else {
			foodType = new FoodType();
		}
		if (ValidateUtil.isStrictNotEmpty(name)) {
			foodType.setTypeName(name);
			foodType.setAddtime(new Date());
		}
		if (id != null && id > 0) {
			message = "mod";
		} else {
			message = "add";
		}
		foodTypeService.merge(foodType);
		return SUCCESS;
	}

	public String find() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		foodTypeList = foodTypeService.findList(searchName, pageNo, pageSize);
		count = foodTypeService.count(searchName);
		pageCount = (count + pageSize - 1) / pageSize;
		return SUCCESS;
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

}
