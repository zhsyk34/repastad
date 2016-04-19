package com.baiyi.order.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.OrderRule;
import com.baiyi.order.service.OrderRuleService;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.ValidateUtil;

@SuppressWarnings("serial")
public class OrderRuleAction extends BasicAction {

	/*---------------action------------------------*/
	public String save() {
		OrderRule or = new OrderRule();
		if (id > 0) {
			or = orderRuleService.findById(id);
			if (ValidateUtil.isStrictNotEmpty(startNo) && noNumber > 0 && minNumber > 0) {
				or.setStartNo(startNo);
				or.setNoNumber(noNumber);
				or.setMinNumber(minNumber);
			}
			or.setUsed(used);
		} else {
			or.setAddtime(new Date());
			or.setStartNo(startNo);
			or.setNoNumber(noNumber);
			or.setMinNumber(minNumber);
		}

		if (id > 0) {
			message = "mod";
		} else {
			message = "add";
		}

		orderRuleService.merge(or);
		return SUCCESS;
	}

	public String delete() {
		if (ids != null && ids.length > 0) {
			orderRuleService.delete(ids);
			message = "del";
		}
		return SUCCESS;
	}

	public String find() {
		pageNo = pageNo <= 1 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		list = orderRuleService.findList(pageNo, pageSize);
		dataCount = orderRuleService.count();
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getAllOrderRule() {
		JSONObject resultObj = new JSONObject();
		try {
			OrderRule or = orderRuleService.findByUsed(1);
			if (or != null) {
				resultObj.put("foodId", or.getFoodId());
				resultObj.put("startNo", or.getStartNo());
				resultObj.put("noNumber", or.getNoNumber());
				resultObj.put("minNumber", or.getMinNumber());
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("OrderRuleAction deleteOrderRule ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(resultObj);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("OrderRuleAction deleteOrderRule stream ERROR", e);
		}
		return SUCCESS;
	}

	/* pojo */
	private int id;// ID

	private Integer[] ids;// ids

	private int foodId;// 相關食物

	private String startNo;// 編號開頭

	private int noNumber;// 共幾位

	private int minNumber;// 起始值

	private int used;// 是否启用

	private int adminId;

	/* page */
	private int pageNo;

	private int pageSize;

	private int dataCount;

	/* service */
	private OrderRuleService orderRuleService;

	/* return result */
	private List<OrderRule> list;

	private String message;

	private Map<String, Object> jsonMap = new HashMap<String, Object>();

	/*------------getter and setter---------------------*/

	@Resource
	public void setOrderRuleService(OrderRuleService orderRuleService) {
		this.orderRuleService = orderRuleService;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public List<OrderRule> getList() {
		return list;
	}

	public void setList(List<OrderRule> list) {
		this.list = list;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}

	public int getNoNumber() {
		return noNumber;
	}

	public void setNoNumber(int noNumber) {
		this.noNumber = noNumber;
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

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

}
