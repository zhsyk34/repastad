package com.baiyi.order.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.FoodStatus;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.FoodStatusService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.TerminalService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FoodStatusAction extends ActionSupport {

	public String merge() throws ParseException {

		if (foodIds != null && terminalIds != null && foodIds.length == terminalIds.length && terminalIds.length > 0) {
			for (int i = 0; i < foodIds.length; i++) {
				foodId = foodIds[i];
				terminalId = terminalIds[i];

				FoodStatus foodStatus = foodStatusService.find(foodId, terminalId);
				if (foodStatus == null) {
					foodStatus = new FoodStatus();
					foodStatus.setFoodId(foodId);
					foodStatus.setTerminalId(terminalId);
					foodStatus.setSend(0);// 新增 send = 0
				}

				foodStatus.setPattern(pattern);

				switch (pattern) {
				case 1:
					// 打折 促销
					if (count < foodStatus.getSend()) {
						continue;
					}

					foodStatus.setUnit(0);

					foodStatus.setBegin(begin);
					foodStatus.setEnd(end);

					foodStatus.setCount(count);

					foodStatus.setPercent(percent);
					if (percent > 0 && percent < 100) {// 根据折扣
						Food food = foodService.findById(foodId);
						int cheap = (int) Math.ceil(food.getPrice() * percent / 100);
						foodStatus.setDiscount(cheap);
					} else {
						foodStatus.setDiscount(discount);
					}
					break;
				case 2:
					// 赠品
					if (count < foodStatus.getSend()) {
						continue;
					}

					foodStatus.setDiscount(0);
					foodStatus.setPercent(0);

					foodStatus.setBegin(begin);
					foodStatus.setEnd(end);
					foodStatus.setCount(count);

					foodStatus.setUnit(unit);

					break;
				case 3:// 停售
					foodStatus.setUnit(0);
					foodStatus.setDiscount(0);
					foodStatus.setPercent(0);

					if (count > 0) {
						if (count < foodStatus.getSend()) {
							continue;
						}
						foodStatus.setCount(count);
						foodStatus.setBegin(null);
						foodStatus.setEnd(null);
					} else {
						foodStatus.setBegin(begin);
						foodStatus.setEnd(end);
						foodStatus.setCount(0);
						foodStatus.setSend(0);
					}
					break;
				}
				foodStatusService.merge(foodStatus);
			}
		}
		jsonMap.put("result", "success");
		return SUCCESS;
	};

	public String delete() {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				if (id == null || id <= 0) {
					continue;
				}

				foodStatusService.delete(id);
			}
		}
		jsonMap.put("result", "delete");
		return SUCCESS;
	};

	public String find() throws ParseException {

		// 活动结束自动删除
		// Date current = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// List<FoodStatus> originalList = foodStatusService.findList();

		// if (CollectionUtils.isNotEmpty(originalList)) {
		// for (FoodStatus foodStatus : originalList) {
		// // 根据日期删除数据
		// String end = foodStatus.getEnd();
		// if (end != null && end.length() > 0) {
		// Date tEnd = sdf.parse(end);
		// if (tEnd != null && current.after(tEnd)) {
		// foodStatusService.delete(foodStatus.getId());
		// continue;
		// }
		// }
		// // 根据数量删除数据
		// if (foodStatus.getCount() > 0 && foodStatus.getSend() >=
		// foodStatus.getCount()) {
		// foodStatusService.delete(foodStatus.getId());
		// continue;
		// }
		// }
		// }

		list = new ArrayList<FoodStatus>();
		List<FoodStatus> foodStatusList = foodStatusService.findList();// 正在进行活动的数据...
		// 条件查询终端和餐点
		List<Terminal> terminalList = terminalService.find(2, terminalNo);
		List<Food> foodList = foodService.search(foodName, -1, -1);
		if (CollectionUtils.isNotEmpty(foodList) && CollectionUtils.isNotEmpty(terminalList)) {
			for (Terminal terminal : terminalList) {
				Integer terminalId = terminal.getId();
				for (Food food : foodList) {
					Integer foodId = food.getId();
					FoodStatus foodStatus = exist(foodStatusList, foodId, terminalId);// 是否已做活动

					if (pattern < 0 && foodStatus != null) {
						continue;
					}

					if (pattern > 0 && (foodStatus == null || !foodStatus.getPattern().equals(pattern))) {
						continue;
					}

					if (foodStatus == null) {
						foodStatus = new FoodStatus();
						foodStatus.setFoodId(foodId);
						foodStatus.setTerminalId(terminalId);
					}
					// vo-info
					foodStatus.setFoodName(food.getName());
					foodStatus.setPrice(food.getPrice());
					foodStatus.setTerminalNo(terminal.getTerminalId());
					foodStatus.setTerminalLocation(terminal.getLocation());
					Material material = materialService.findById(food.getMaterialId());
					if (material != null) {
						foodStatus.setPath(material.getPath().replace("\\", "/"));
					}
					//
					list.add(foodStatus);
				}
			}
		}

		// page
		if (CollectionUtils.isNotEmpty(list)) {
			dataCount = list.size();
		}
		pageCount = (dataCount + pageSize - 1) / pageSize;
		int fromIndex = (pageNo - 1) * pageSize;
		int toIndex = pageNo * pageSize;
		if (fromIndex > dataCount) {
			fromIndex = dataCount;
		}
		if (toIndex > dataCount) {
			toIndex = dataCount;
		}
		list = list.subList(fromIndex, toIndex);

		jsonMap.put("list", list);
		jsonMap.put("pageNo", pageNo);
		jsonMap.put("pageSize", pageSize);
		jsonMap.put("dataCount", dataCount);

		return SUCCESS;
	};

	// 终端调用----修改送出数量
	public String updateBySend() {
		try {
			if (sendArray == null || sendArray.length() == 0) {
				jsonMap.put("result", "params is null");
				return SUCCESS;
			}
			String[] param = sendArray.split(";");
			String[] ids = param[0].split(",");
			String[] sends = param[1].split(",");

			if (ids.length != sends.length) {
				jsonMap.put("warning", "params error");
				return SUCCESS;
			}
			if (ids.length == 0) {
				jsonMap.put("warning", "params is null");
				return SUCCESS;
			}
			for (int i = 0; i < ids.length; i++) {
				id = Integer.parseInt(ids[i]);
				send = Integer.parseInt(sends[i]);
				if (send < 0) {
					jsonMap.put("warning", "params error in id=" + id + ",send<0");
					continue;
				}

				FoodStatus foodStatus = foodStatusService.find(id);

				if (foodStatus == null) {
					jsonMap.put("warning", "params error in id=" + id + ",can't find the data");
					continue;
				}

				foodStatus.setSend(send);
				foodStatusService.update(foodStatus);
				jsonMap.put("result", "success");
			}
		} catch (Exception exception) {
			jsonMap.put("result", "false");
			exception.printStackTrace();
		}

		return SUCCESS;
	}

	private static FoodStatus exist(List<FoodStatus> list, Integer foodId, Integer terminalId) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (FoodStatus foodStatus : list) {
			if (foodStatus.getFoodId().equals(foodId) && foodStatus.getTerminalId().equals(terminalId)) {
				return foodStatus;
			}
		}
		return null;
	}

	//
	private FoodStatusService foodStatusService;

	private FoodService foodService;

	private TerminalService terminalService;

	private MaterialService materialService;

	//
	private Integer id;

	private Integer foodId;

	private String foodName;

	private Integer terminalId;

	private String terminalNo;

	private Integer pattern = 0;

	private Integer[] ids;

	private Integer[] foodIds;

	private Integer[] terminalIds;

	// 
	private int unit;// 每份贈品数量

	private int count;// 贈品、打折總量

	private int send;// 已完成的贈品、打折數量

	private double discount;// 打折价

	private int percent;// 打折折扣(百分比)

	private String begin;// 停售

	private String end;// 停售

	/**/
	private int pageNo = 1;

	private int pageSize = 10;

	private int pageCount;

	private int dataCount;

	/* 终端数据--修改送出数量 */
	private String sendArray;

	List<FoodStatus> list;

	private Map<String, Object> jsonMap = new HashMap<String, Object>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	public Integer[] getFoodIds() {
		return foodIds;
	}

	public void setFoodIds(Integer[] foodIds) {
		this.foodIds = foodIds;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
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

	public List<FoodStatus> getList() {
		return list;
	}

	public void setList(List<FoodStatus> list) {
		this.list = list;
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

	public Integer getPattern() {
		return pattern;
	}

	public void setPattern(Integer pattern) {
		this.pattern = pattern;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public int getSend() {
		return send;
	}

	public void setSend(int send) {
		this.send = send;
	}

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public Integer[] getTerminalIds() {
		return terminalIds;
	}

	public void setTerminalIds(Integer[] terminalIds) {
		this.terminalIds = terminalIds;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	@Resource
	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	@Resource
	public void setFoodStatusService(FoodStatusService foodStatusService) {
		this.foodStatusService = foodStatusService;
	}

	@Resource
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	@Resource
	public void setTerminalService(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public String getSendArray() {
		return sendArray;
	}

	public void setSendArray(String sendArray) {
		this.sendArray = sendArray;
	}

}
