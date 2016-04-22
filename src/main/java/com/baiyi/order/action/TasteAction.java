package com.baiyi.order.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.Taste;
import com.baiyi.order.pojo.TasteType;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.TasteService;
import com.baiyi.order.service.TasteTypeService;
import com.baiyi.order.util.ValidateUtil;
import com.baiyi.order.vo.TasteVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class TasteAction extends ActionSupport {

	private TasteService tasteService;

	private TasteTypeService tasteTypeService;

	private FoodService foodService;

	private Taste taste;

	private List<Taste> tasteList;

	private List<TasteType> tasteTypeList;

	private List<TasteVO> tasteVOList;

	private Integer id;

	private Integer[] ids;

	private String name;

	private double price;

	private String searchName;

	private Integer typeId;

	private String typeName;

	private int pageNo;

	private int pageSize;

	private int pageCount;

	private int count;

	private String message;

	/**/
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<TasteType> getTasteTypeList() {
		return tasteTypeList;
	}

	public void setTasteTypeList(List<TasteType> tasteTypeList) {
		this.tasteTypeList = tasteTypeList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
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

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Taste getTaste() {
		return taste;
	}

	public void setTaste(Taste taste) {
		this.taste = taste;
	}

	public List<Taste> getTasteList() {
		return tasteList;
	}

	public void setTasteList(List<Taste> tasteList) {
		this.tasteList = tasteList;
	}

	public List<TasteVO> getTasteVOList() {
		return tasteVOList;
	}

	public void setTasteVOList(List<TasteVO> tasteVOList) {
		this.tasteVOList = tasteVOList;
	}

	@Resource
	public void setTasteService(TasteService tasteService) {
		this.tasteService = tasteService;
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	@Resource
	public void setTasteTypeService(TasteTypeService tasteTypeService) {
		this.tasteTypeService = tasteTypeService;
	}

	/*----------action-------------*/

	public String merge() {
		if (id != null && id > 0) {
			taste = tasteService.find(id);
		} else {
			taste = new Taste();
			pageNo = 1;
		}
		if (ValidateUtil.isStrictNotEmpty(name)) {
			name = name.trim();
			taste.setName(name);
		}
		taste.setPrice(price);
		if (ValidateUtil.isStrictNotEmpty(typeName)) {
			TasteType tasteType = tasteTypeService.find(typeName);
			if (tasteType == null) {
				tasteType = new TasteType();
				tasteType.setName(typeName);
				tasteTypeService.save(tasteType);
			}
			taste.setType(tasteType.getId());
		}
		if (id != null && id > 0) {
			message = "mod";
		} else {
			message = "add";
		}
		tasteService.merge(taste);
		return SUCCESS;
	}

	public String delete() {
		List<Food> foodList = foodService.getAllFood();
		if (ValidateUtil.isNotEmpty(ids)) {
			if (foodList != null && foodList.size() > 0) {
				for (int i = 0; i < foodList.size(); i++) {
					String tastes = foodList.get(i).getTaste();
					if (ValidateUtil.isNotEmpty(tastes)) {
						String[] tasteArr = tastes.split(",");
						for (int j = 0; j < tasteArr.length; j++) {
							int tasteId = Integer.parseInt(tasteArr[j].trim());
							for (int k = 0; k < ids.length; k++) {
								if (tasteId == ids[k]) {
									message = "used";
									return SUCCESS;
								}
							}
						}
					}

				}
			}
			pageNo = 1;
			message = "del";//

		}
		tasteService.delete(ids);
		return SUCCESS;
	}

	public String find() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		tasteVOList = tasteService.findVOList(searchName, typeId, pageNo, pageSize);

		tasteList = tasteService.findList();
		Set<Integer> tasteTypeIdSet = new HashSet<Integer>();// in used
		if (CollectionUtils.isNotEmpty(tasteList)) {
			for (Taste t : tasteList) {
				Integer tasteTypeId = t.getType();
				if (tasteTypeId != null && tasteTypeId > 0) {
					tasteTypeIdSet.add(tasteTypeId);
				}
			}
		}

		tasteTypeList = tasteTypeService.findList();
		if (CollectionUtils.isNotEmpty(tasteTypeList)) {
			for (TasteType tt : tasteTypeList) {
				Integer tasteTypeId = tt.getId();
				if (!tasteTypeIdSet.contains(tasteTypeId)) {
					tasteTypeService.delete(tasteTypeId);
				}
			}
		}

		tasteTypeList = tasteTypeService.findList();
		count = tasteService.count(searchName, typeId);
		return SUCCESS;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
