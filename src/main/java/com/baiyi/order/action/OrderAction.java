package com.baiyi.order.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.baiyi.order.pojo.OrderInfo;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.OrderService;
import com.baiyi.order.util.ExportExcel;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;
import com.baiyi.order.vo.OrderContent;
import com.baiyi.order.vo.OrderMessage;

public class OrderAction extends BasicAction {
	private int exportType;// 导出数据类型 1：明细，2：统计

	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	// 排序
	private static List<OrderContent> sortContentList(List<OrderContent> list, final String type) {
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				OrderContent data1 = (OrderContent) o1;
				OrderContent data2 = (OrderContent) o2;
				//
				if ("money".equals(type)) {
					return (int) (data2.getMoney() - data1.getMoney());
				}
				// 默認按照數量排序
				return data2.getCount() - data1.getCount();
			}
		});
		return list;
	}

	// 合并數據
	private static List<OrderContent> mergeList(List<OrderContent> list) {
		List<OrderContent> result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			OrderContent orderContent = list.get(i);
			int id = orderContent.getId();
			boolean exist = false;
			// 已有數據的索引
			int index = -1;
			for (int j = 0; j < result.size(); j++) {
				if (id == result.get(j).getId()) {
					index = j;
					exist = true;
					break;
				}
			}
			if (exist) {
				OrderContent update = new OrderContent();
				OrderContent existContent = result.get(index);
				update.setId(id);
				update.setName(orderContent.getName());
				update.setPrice(orderContent.getPrice());
				// 合并count 和 money字段
				update.setCount(existContent.getCount() + orderContent.getCount());
				update.setMoney(existContent.getMoney() + orderContent.getMoney());
				result.set(index, update);
			} else {
				result.add(orderContent);
			}
		}
		return result;
	}

	private int id;// ID

	private String orderId;// 訂單編號

	private String shopId;// 收費端

	private String cookId;// 廚房端

	private String content;// 消費內容

	private float amount;// 金額

	private float income;// 收款

	private float pay;// 付款

	private String addtime;

	private int startPage;

	private int maxPage;

	private int pageSize;

	private int adminId;

	private String message;

	private AdminsService adminsService;

	private OrderService orderService;

	private String idList;

	private String begindate;

	private String enddate;

	private String sortType;

	// 删除
	@SuppressWarnings("unchecked")
	public String deleteOrder() {
		JSONObject resultObj = new JSONObject();
		try {
			if (id > 0) {
				this.orderService.deleteById(id);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("OrderAction deleteOrder ERROR", e);
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
			Log4JFactory.instance().error("OrderAction deleteOrder stream ERROR", e);
		}
		return SUCCESS;
	}

	// 批量删除
	public String deleteOrderByIdList() {
		JSONObject object = new JSONObject();
		try {
			this.idList = (String) Utility.checkParmeter(this.idList);
			if (idList != null) {
				// List<Integer> idList = new ArrayList<Integer>();
				String[] idArray = this.idList.split(",");
				// int adminId = this.admins.getId();
				adminId = 1;
				for (int i = 0; i < idArray.length; i++) {
					Integer id = new Integer(idArray[i]);
					this.orderService.deleteById(id);
					// this.operRecordService.saveOperRecord(0, adminId,
					// TypeBean.MATERIAL,
					// this.getText("material.delete")+material.getM_name());
				}
				object.put("success", true);
			}
		} catch (Exception e) {
			object.put("success", false);
			e.printStackTrace();
			Log4JFactory.instance().error("OrderRuleAction deleteTemplateByIdList ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(object);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("OrderRuleAction deleteTemplateByIdList stream ERROR", e);
		}
		return SUCCESS;
	}

	public String findOrder() {
		try {
			OrderInfo o = this.orderService.findById(id);
			request.setAttribute("orderRule", 0);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("OrderAction findOrder ERROR", e);
		}
		return INPUT;
	}

	public String getAddtime() {
		return addtime;
	}

	public int getAdminId() {
		return adminId;
	}

	public AdminsService getAdminsService() {
		return adminsService;
	}

	public float getAmount() {
		return amount;
	}

	public String getBegindate() {
		return begindate;
	}

	public String getContent() {
		return content;
	}

	public String getCookId() {
		return cookId;
	}

	public String getEnddate() {
		return enddate;
	}

	public int getId() {
		return id;
	}

	public String getIdList() {
		return idList;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public String getMessage() {
		return message;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getShopId() {
		return shopId;
	}

	public String getSortType() {
		return sortType;
	}

	public int getStartPage() {
		return startPage;
	}

	private String fileName;// 導出文件名稱

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 導出數據
	public String export() {
		return SUCCESS;
	}

	// 返回數據
	public InputStream getInputStream() throws IOException {
		if (1 == exportType) {

			List<OrderInfo> orderList = null;
			List<OrderMessage> list = new ArrayList();

			try {
				if (this.pageSize == 0) {
					this.pageSize = admins.getPageCount();
				} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
					admins.setPageCount(pageSize);
					this.adminsService.updatePageCount(admins.getId(), pageSize);
				}
				PageBean pageBean = new PageBean(pageSize);
				if (startPage > 0) {
					pageBean.setStartPage(startPage);
				} else {
					pageBean.setStartPage(1);
				}
				this.shopId = (String) Utility.checkParmeter(this.shopId);
				this.cookId = (String) Utility.checkParmeter(this.cookId);
				this.begindate = (String) Utility.checkParmeter(this.begindate);
				this.enddate = (String) Utility.checkParmeter(this.enddate);
				if (begindate == null && enddate == null) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					begindate = df.format(new Date());
					enddate = df.format(new Date());
				}
				fileName = "";
				if (shopId != null && shopId.trim().length() > 0) {
					fileName += shopId + "-";
				}
				if (cookId != null && cookId.trim().length() > 0) {
					fileName += cookId;
				}
				fileName += "." + begindate.replace("-", "") + "-" + enddate.replace("-", "");
				fileName += ".xls";

				orderList = orderService.findByPageBean(shopId, cookId, orderId, begindate, enddate, pageBean);

				if (orderList != null && orderList.size() > 0) {
					for (OrderInfo order : orderList) {
						OrderMessage orderMessage = new OrderMessage();
						orderMessage.setId(order.getOrderId());
						orderMessage.setCook(order.getCookId());
						orderMessage.setShop(order.getShopId());
						orderMessage.setTotal(order.getAmount());
						orderMessage.setClock(order.getAddtime());
						orderMessage.setIncome(order.getIncome());
						orderMessage.setPay(order.getPay());
						// 訂單内容
						String content = order.getContent();
						List<OrderContent> contentList = new ArrayList();
						if (content != null && content.length() > 0) {
							String[] foods = content.split("@");
							for (String food : foods) {
								OrderContent orderContent = new OrderContent();
								// 0是id,1是名稱，2是數量，3是類型，4是單價 6金額
								if (food != null && food.length() > 0) {
									String[] data = food.split(";");
									int orderId = Integer.parseInt(data[0]);

									int length = data.length;
									orderContent.setId(length > 0 ? Integer.parseInt(data[0]) : 0);
									orderContent.setName(length > 1 ? data[1] : "");
									orderContent.setCount(length > 2 ? Integer.parseInt(data[2]) : 0);
									orderContent.setPrice(length > 4 ? Double.parseDouble(data[4]) : 0);
									orderContent.setMoney(length > 6 ? Float.parseFloat(data[6]) : 0);

									contentList.add(orderContent);
								}
							}
						}
						orderMessage.setContentList(contentList);
						list.add(orderMessage);
					}
				}
				String[] title = { "收費端", "廚房端", "訂單編號", "訂單内容", "金額", "時間" };
				if ("_cn".equals(this.getText("language"))) {
					title = new String[] { "收费端", "厨房端", "订单编号", "订单内容", "金额", "时间" };
				}

				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ExportExcel.orderExport(title, list, bout);
				ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
				return bin;
			} catch (Exception e) {
				return null;
			}

		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				this.shopId = (String) Utility.checkParmeter(this.shopId);
				this.cookId = (String) Utility.checkParmeter(this.cookId);
				this.begindate = (String) Utility.checkParmeter(this.begindate);
				this.enddate = (String) Utility.checkParmeter(this.enddate);
				this.sortType = (String) Utility.checkParmeter(this.sortType);
				if (begindate == null) {
					begindate = df.format(new Date());
				}
				if (enddate == null) {
					enddate = df.format(new Date());
				}
				fileName = "";
				if (shopId != null && shopId.trim().length() > 0) {
					fileName += shopId + "-";
				}
				if (cookId != null && cookId.trim().length() > 0) {
					fileName += cookId;
				}
				fileName += "  " + begindate.replace("-", "") + "-" + enddate.replace("-", "");
				fileName += ".xls";
				List<OrderInfo> orderList = this.orderService.findByCal(shopId, cookId, begindate, enddate);

				// 根據shop和cook編號合并統計訂單
				Map<String, OrderInfo> mergeMap = new HashMap<String, OrderInfo>();
				if (orderList != null) {
					for (OrderInfo orderInfo : orderList) {
						String key = orderInfo.getCookId() + "-" + orderInfo.getShopId();
						if (!mergeMap.containsKey(key)) {
							mergeMap.put(key, orderInfo);
						} else {
							OrderInfo exist = mergeMap.get(key);
							exist.setContent(orderInfo.getContent() + exist.getContent());
							exist.setAmount(orderInfo.getAmount() + exist.getAmount());
							mergeMap.put(key, exist);
						}
					}
				}
				// 统计数据
				List<OrderMessage> calcList = new ArrayList();
				// 合并總數據
				List<OrderContent> totalList = new ArrayList();
				// 每筆統計訂單
				for (String key : mergeMap.keySet()) {
					OrderInfo orderInfo = mergeMap.get(key);
					OrderMessage orderMessage = new OrderMessage();
					orderMessage.setCook(orderInfo.getCookId());
					orderMessage.setShop(orderInfo.getShopId());
					orderMessage.setTotal(orderInfo.getAmount());
					orderMessage.setIncome(orderInfo.getIncome());
					orderMessage.setPay(orderInfo.getPay());

					// 每筆統計訂單内容
					String content = orderInfo.getContent();
					List<OrderContent> contentList = new ArrayList();
					if (content != null && content.length() > 0) {
						String[] foods = content.split("@");
						for (String food : foods) {
							OrderContent orderContent = new OrderContent();
							// 0是id,1是名稱，2是數量，3是類型，4是單價
							if (food != null && food.length() > 0) {
								String[] data = food.split(";");

								int length = data.length;
								orderContent.setId(length > 0 ? Integer.parseInt(data[0]) : 0);
								orderContent.setName(length > 1 ? data[1] : "");
								orderContent.setCount(length > 2 ? Integer.parseInt(data[2]) : 0);
								orderContent.setPrice(length > 4 ? Double.parseDouble(data[4]) : 0);
								orderContent.setMoney(length > 6 ? Float.parseFloat(data[6]) : 0);

							}
							// 統計數據
							contentList.add(orderContent);
							// 所有數據
							totalList.add(orderContent);
						}
						contentList = sortContentList(mergeList(contentList), sortType);// 統計方法
						orderMessage.setContentList(contentList);
						calcList.add(orderMessage);

					}

				}
				String[] title = { "收費端", "廚房端", "訂單内容統計", "", "", "", "金額統計" };
				String[] title2 = { "", "", "序號", "名稱", "數量", "總價", "" };
				if ("_cn".equals(this.getText("language"))) {
					title = new String[] { "收费端", "厨房端", "订单内容统计", "", "", "", "金额" };
					title2 = new String[] { "", "", "序号", "名称", "数量", "总价", "" };
				}
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ExportExcel.orderCalExport(title, title2, calcList, totalList, bout);
				ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
				return bin;

			} catch (Exception e) {
				request.setAttribute("message", this.getText("search.error"));
				Log4JFactory.instance().error("OrderAction orderCalculate ERROR", e);
				return null;
			}

		}
	}

	// 查询 統計
	public String orderCalculate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.shopId = (String) Utility.checkParmeter(this.shopId);
			this.cookId = (String) Utility.checkParmeter(this.cookId);
			this.begindate = (String) Utility.checkParmeter(this.begindate);
			this.enddate = (String) Utility.checkParmeter(this.enddate);
			this.sortType = (String) Utility.checkParmeter(this.sortType);
			if (begindate == null) {
				begindate = df.format(new Date());
			}
			if (enddate == null) {
				enddate = df.format(new Date());
			}

			List<OrderInfo> orderList = this.orderService.findByCal(shopId, cookId, begindate, enddate);

			// 根據shop和cook編號合并統計訂單
			Map<String, OrderInfo> mergeMap = new HashMap<String, OrderInfo>();
			if (orderList != null) {
				for (OrderInfo orderInfo : orderList) {
					String key = orderInfo.getCookId() + "-" + orderInfo.getShopId();
					if (!mergeMap.containsKey(key)) {
						mergeMap.put(key, orderInfo);
					} else {
						OrderInfo exist = mergeMap.get(key);
						exist.setContent(orderInfo.getContent() + exist.getContent());
						exist.setAmount(orderInfo.getAmount() + exist.getAmount());
						mergeMap.put(key, exist);
					}
				}
			}
			// 统计数据
			List<OrderMessage> calcList = new ArrayList();
			// 合并總數據
			List<OrderContent> totalList = new ArrayList();
			// 每筆統計訂單
			for (String key : mergeMap.keySet()) {
				OrderInfo orderInfo = mergeMap.get(key);
				OrderMessage orderMessage = new OrderMessage();
				orderMessage.setCook(orderInfo.getCookId());
				orderMessage.setShop(orderInfo.getShopId());
				orderMessage.setTotal(orderInfo.getAmount());

				// 每筆統計訂單内容
				String content = orderInfo.getContent();
				List<OrderContent> contentList = new ArrayList();
				if (content != null && content.length() > 0) {
					String[] foods = content.split("@");
					for (String food : foods) {
						OrderContent orderContent = new OrderContent();
						// 0是id,1是名稱，2是數量，3是類型，4是單價,5口味,6總價
						if (food != null && food.length() > 0) {
							String[] data = food.split(";");
							int length = data.length;
							orderContent.setId(length > 0 ? Integer.parseInt(data[0]) : 0);
							String name = length > 1 ? data[1] : "";
							name = name.replaceAll("#s#.*", "");
							orderContent.setName(name);
							orderContent.setCount(length > 2 ? Integer.parseInt(data[2]) : 0);
							orderContent.setPrice(length > 4 ? Double.parseDouble(data[4]) : 0);
							orderContent.setMoney(length > 6 ? Float.parseFloat(data[6]) : 0);
						}
						// 統計數據
						contentList.add(orderContent);
						// 所有數據
						totalList.add(orderContent);
					}
					contentList = sortContentList(mergeList(contentList), sortType);// 統計方法
					orderMessage.setContentList(contentList);
					calcList.add(orderMessage);
				}

			}

			totalList = sortContentList(mergeList(totalList), sortType);
			request.setAttribute("totalList", totalList);
			request.setAttribute("calcList", calcList);

			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("OrderAction orderCalculate ERROR", e);
		}
		return INPUT;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String saveOrder() throws Exception {
		JSONObject resultObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			this.orderId = (String) Utility.checkParmeter(this.orderId);
			this.shopId = (String) Utility.checkParmeter(this.shopId);
			this.cookId = (String) Utility.checkParmeter(this.cookId);
			this.content = (String) Utility.checkParmeter(this.content);
			this.addtime = (String) Utility.checkParmeter(this.addtime);
			Date d = new Date();
			if (addtime != null) {
				try {
					d = sdf.parse(addtime);
				} catch (Exception e) {
				}
			}
			this.orderService.saveOrder(id, orderId, shopId, cookId, content, amount, income, pay, d);
			resultObj.put("success", true);
		} catch (Exception e) {
			resultObj.put("success", false);
			resultObj.put("errorMsg", this.getText("write.error"));
			Log4JFactory.instance().error("OrderAction saveOrder Error", e);
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
			Log4JFactory.instance().error("OrderAction saveOrder stream ERROR", e);
		}
		return SUCCESS;
	}

	public String saveOrderByPre() {
		return SUCCESS;
	}

	// 查询 明細
	public String searchOrder() {
		try {
			List<OrderInfo> orderList = null;
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			this.shopId = (String) Utility.checkParmeter(this.shopId);
			this.cookId = (String) Utility.checkParmeter(this.cookId);
			this.begindate = (String) Utility.checkParmeter(this.begindate);
			this.enddate = (String) Utility.checkParmeter(this.enddate);
			if (begindate == null && enddate == null) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				begindate = df.format(new Date());
				enddate = df.format(new Date());
			}
			request.setAttribute("pageBean", pageBean);

			orderList = orderService.findByPageBean(shopId, cookId, orderId, begindate, enddate, pageBean);
			List<OrderMessage> list = new ArrayList();
			if (orderList != null && orderList.size() > 0) {
				for (OrderInfo order : orderList) {
					OrderMessage orderMessage = new OrderMessage();
					orderMessage.setId(order.getOrderId());
					orderMessage.setCook(order.getCookId());
					orderMessage.setShop(order.getShopId());
					orderMessage.setTotal(order.getAmount());
					orderMessage.setClock(order.getAddtime());
					orderMessage.setIncome(order.getIncome());
					orderMessage.setPay(order.getPay());
					// 訂單内容
					String content = order.getContent();
					List<OrderContent> contentList = new ArrayList();
					if (content != null && content.length() > 0) {
						String[] foods = content.split("@");
						for (String food : foods) {
							OrderContent orderContent = new OrderContent();
							// 0是id,1是名稱，2是數量，3是類型，4是單價，5調味，6金額
							if (food != null && food.length() > 0) {
								String[] data = food.split(";");
								int orderId = Integer.parseInt(data[0]);

								int length = data.length;
								orderContent.setId(length > 0 ? Integer.parseInt(data[0]) : 0);
								String name = length > 1 ? data[1] : "";
								name = name.replaceAll("#s#.*", "");
								orderContent.setName(name);
								orderContent.setCount(length > 2 ? Integer.parseInt(data[2]) : 0);
								orderContent.setPrice(length > 4 ? Double.parseDouble(data[4]) : 0);
								orderContent.setMoney(length > 6 ? Float.parseFloat(data[6]) : 0);
								contentList.add(orderContent);
							}
						}
					}
					orderMessage.setContentList(contentList);
					list.add(orderMessage);
				}
			}
			request.setAttribute("list", list);
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("OrderAction searchOrder ERROR", e);
		}
		return INPUT;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCookId(String cookId) {
		this.cookId = cookId;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public float getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public float getPay() {
		return pay;
	}

	public void setPay(float pay) {
		this.pay = pay;
	}

}
