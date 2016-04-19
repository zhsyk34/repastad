package com.baiyi.order.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Deposit;
import com.baiyi.order.pojo.DinnerTable;
import com.baiyi.order.pojo.FoodStatus;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.pojo.SystemConfig;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.program.ProgramGloable;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.DepositService;
import com.baiyi.order.service.DinnerTableService;
import com.baiyi.order.service.FoodStatusService;
import com.baiyi.order.service.FoodTypeService;
import com.baiyi.order.service.SystemConfigService;
import com.baiyi.order.service.TemplateService;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.socket.InfoMessage;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;
import com.baiyi.order.util.ValidateUtil;
import com.baiyi.order.vo.MoneyDeposit;

public class TerminalAction extends BasicAction {
	@Resource
	private SystemConfigService systemConfigService;

	private DinnerTableService dinnerTableService;

	private List<DinnerTable> dinnerTableList;

	private TerminalService terminalService;

	private TemplateService templateService;

	private DepositService depositService;

	private FoodTypeService foodTypeService;

	private FoodStatusService foodStatusService;

	private Map<String, Object> jsonMap = new HashMap<String, Object>();

	private int id;// ID

	private String startStr;// 編號開頭

	private String terminalId;// 編號開始

	private String terminalIdEnd;// 編號結束

	private int type;

	private String location;

	private int invoice;

	private String searchName;// 搜尋名稱

	private int searchType;// 搜尋名稱

	private int startPage;

	private int maxPage;

	private int pageSize;

	private int adminId;

	private String message;

	private AdminsService adminsService;

	private String idList;

	private String queryline;// 连线状态

	private String serverTime;// 時間

	private String orderType;// 指令類型

	private String orderString;// 指令參數

	private int templateId;

	private String version;

	private String moneyAmount;

	private String dinnerTable;

	private String teamViewerId;;

	// 删除
	@SuppressWarnings("unchecked")
	public String deleteTerminal() {
		JSONObject resultObj = new JSONObject();
		try {
			if (id > 0) {
				this.terminalService.deleteById(id);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("TerminalAction deleteTerminal ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction deleteTerminal stream ERROR", e);
		}
		return SUCCESS;
	}

	// 批量删除
	public String deleteTerminalByIdList() {
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
					this.terminalService.deleteById(id);
					// this.operRecordService.saveOperRecord(0, adminId,
					// TypeBean.MATERIAL,
					// this.getText("material.delete")+material.getM_name());
				}
				object.put("success", true);
			}
		} catch (Exception e) {
			object.put("success", false);
			e.printStackTrace();
			Log4JFactory.instance().error("TerminalAction deleteTerminalByIdList ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction deleteTerminalByIdList stream ERROR", e);
		}
		return SUCCESS;
	}

	public String findTerminal() {
		try {
			Terminal t = this.terminalService.findById(id);
			dinnerTableList = dinnerTableService.findList();
			request.setAttribute("terminal", t);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction findTerminal ERROR", e);
		}
		return INPUT;
	}

	public int getAdminId() {
		return adminId;
	}

	public AdminsService getAdminsService() {
		return adminsService;
	}

	// 廚房端或收費端獲取編號
	@SuppressWarnings("unchecked")
	public String getAllTerminalId() {
		JSONObject resultObj = new JSONObject();
		try {
			List<Terminal> terminalList = terminalService.findAll();
			if (terminalList != null && terminalList.size() > 0) {
				resultObj.put("success", true);
				JSONArray array = new JSONArray();
				for (Terminal t : terminalList) {
					if (type == t.getType()) {
						JSONObject terminalObj = new JSONObject();
						terminalObj.put("id", t.getTerminalId());
						terminalObj.put("location", t.getLocation());
						array.add(terminalObj);
					}
				}
				resultObj.put("terminalArray", array);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("TerminalAction getAllTerminalId ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction getAllTerminalId stream ERROR", e);
		}
		return SUCCESS;
	}

	/**
	 * 獲取終端關機時間
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getBootAndShutdown() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			if (terminalId == null) {
				object.put("success", false);
			} else {
				Terminal terminal = this.terminalService.findTerminalId(terminalId);
				if (terminal != null) {
					int isEnable = terminal.getEnableshutdown();
					if (isEnable == ProgramGloable.ENABLE) {
						object.put("shutdowntime", terminal.getShutdowntime() != null ? terminal.getShutdowntime() : "");// 關機時間
					} else {
						object.put("shutdowntime", "0");// 關機時間
					}
					object.put("boottime", terminal.getBoottime() != null ? terminal.getBoottime() : "");
					object.put("success", true);
				} else {
					object.put("success", false);
				}
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("TerminalAction getShutdownTime ERROR", e);
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
			logger.error("TerminalAction getShutdownTime stream ERROR", e);
		}
		return SUCCESS;
	}

	public String getDinnerTable() {
		return dinnerTable;
	}

	public List<DinnerTable> getDinnerTableList() {
		return dinnerTableList;
	}

	public DinnerTableService getDinnerTableService() {
		return dinnerTableService;
	}

	public int getId() {
		return id;
	}

	public String getIdList() {
		return idList;
	}

	public int getInvoice() {
		return invoice;
	}

	public String getLocation() {
		return location;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public String getMessage() {
		return message;
	}

	public String getMoneyAmount() {
		return moneyAmount;
	}

	public String getOrderString() {
		return orderString;
	}

	public String getOrderType() {
		return orderType;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getQueryline() {
		return queryline;
	}

	public String getSearchName() {
		return searchName;
	}

	public int getSearchType() {
		return searchType;
	}

	// 获得服务器端时间
	@SuppressWarnings("unchecked")
	public String getServerTime() {
		JSONObject resultObj = new JSONObject();
		try {
			// Calendar now = Calendar.getInstance();
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"), Locale.CHINESE);
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int minute = now.get(Calendar.MINUTE);
			int second = now.get(Calendar.SECOND);
			month += 1;
			resultObj.put("year", year);
			resultObj.put("month", month);
			resultObj.put("day", day);
			resultObj.put("hour", hour);
			resultObj.put("minute", minute);
			resultObj.put("second", second);
			resultObj.put("success", true);
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("WebAction getServerTime ERROR", e);
		}
		try {
			response.setContentType("text/xml; charset=UTF-8");
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
			Log4JFactory.instance().error("WebAction getServerTime stream ERROR", e);
		}
		return SUCCESS;
	}

	public int getStartPage() {
		return startPage;
	}

	public String getStartStr() {
		return startStr;
	}

	public int getTemplateId() {
		return templateId;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public String getTerminalIdEnd() {
		return terminalIdEnd;
	}

	// 获取终端配置参数
	public String getTerminalParam() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject resultObj = new JSONObject();

		List<SystemConfig> configs = systemConfigService.findList();
		Map<String, String> configMap = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(configs)) {
			for (SystemConfig config : configs) {
				configMap.put(config.getName(), config.getValue());
			}
		}

		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal t = terminalService.findTerminalId(terminalId);
			if (t == null || t.getType() != 1) {
				resultObj.put("success", false);
			} else {
				// 取餐方式 0:不顯示;1:内用;2:外帶;
				String take = configMap.get("take");
				// 桌號默認顯示方式 0:不顯示;1:顯示
				String desk = configMap.get("desk");
				// 是否显示全部类型选择
				String alltype = configMap.get("alltype");
				// 终端列印
				String shoporder = configMap.get("shoporder");
				String kitchenorder = configMap.get("kitchenorder");

				// JSONObject showorder = new JSONObject();
				// showorder.put("shop", shoporder);
				// showorder.put("kitchen", kitchenorder);
				// 支付方式
				String pay = configMap.get("pay");

				// 附加费name,percent
				int accessoryUsed = Integer.parseInt(configMap.get("accessory"));
				String accessoryName = configMap.get("name");
				int accessoryPercent = Integer.parseInt(configMap.get("percent"));

				JSONObject accessory = new JSONObject();
				accessory.put("used", accessoryUsed);
				accessory.put("name", accessoryName);
				accessory.put("percent", accessoryPercent);

				// 現金存量警戒值
				List<Deposit> depositList = depositService.findList();
				JSONObject JsonDeposit = new JSONObject();
				for (Deposit deposit : depositList) {
					String name = deposit.getName();
					if (!ValidateUtil.isStrictNotEmpty(name)) {
						continue;
					}
					if (name.indexOf("nd100") == -1) {// nd100不需要最大值
						JsonDeposit.put(name + "max", deposit.getMax());
					}
					if (name.indexOf("nv9") == -1) {// nv9不需要最小值
						JsonDeposit.put(name + "min", deposit.getMin());
					}
				}

				// 桌位
				JSONArray tableArray = new JSONArray();
				dinnerTable = t.getDinnerTable();
				if (ValidateUtil.isStrictNotEmpty(dinnerTable)) {
					String[] arr = dinnerTable.split(",");
					if (arr != null && arr.length > 0) {
						for (int i = 0; i < arr.length; i++) {
							int tableId = Integer.parseInt(arr[i].trim());
							DinnerTable dt = dinnerTableService.find(tableId);
							if (dt != null) {
								String tableName = dt.getName();
								tableArray.add(tableName);
							}
						}
					}
				}

				// return json data

				resultObj.put("take", take);
				resultObj.put("desk", desk);
				resultObj.put("alltype", alltype);
				resultObj.put("shoporder", shoporder);
				resultObj.put("accessory", accessory);

				//
				resultObj.put("pay", pay);
				if (StringUtils.isNotBlank(pay)) {
					if (pay.matches(".*,?wechat,?.*")) {
						JSONObject wxconfig = new JSONObject();
						wxconfig.put("title", configMap.get("wx_title"));
						wxconfig.put("machID", configMap.get("wx_mchID"));
						wxconfig.put("appID", configMap.get("wx_appID"));
						wxconfig.put("key", configMap.get("wx_key"));
						resultObj.put("wxconfig", wxconfig);
					}
					if (pay.matches(".*,?alipay,?.*")) {
						JSONObject zfbconfig = new JSONObject();
						zfbconfig.put("title", configMap.get("zfb_title"));
						zfbconfig.put("partner", configMap.get("zfb_partner"));
						zfbconfig.put("appid", configMap.get("zfb_appid"));
						zfbconfig.put("privatekey", configMap.get("zfb_privatekey"));
						zfbconfig.put("publickey", configMap.get("zfb_publickey"));
						resultObj.put("zfbconfig", zfbconfig);
					}
				}

				resultObj.put("deposit", JsonDeposit);

				resultObj.put("table", tableArray);

				resultObj.put("invoice", t.getInvoice() == 1 ? true : false);

				resultObj.put("success", true);
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney stream ERROR", e);
		}
		return null;
	}

	// 终端:餐点类型数据清单
	public String getFoodType() {
		JSONObject resultObj = new JSONObject();
		JSONObject type = new JSONObject();
		try {
			List<FoodType> typeList = foodTypeService.findList();
			if (CollectionUtils.isNotEmpty(typeList)) {
				for (FoodType foodType : typeList) {
					type.put(foodType.getId(), foodType.getTypeName());
				}
			}
			resultObj.put("type", type);

			// 厨房端订单
			String kitchenorder = "0";
			SystemConfig kitchenorderSyscon = systemConfigService.findByName("kitchenorder");
			if (kitchenorderSyscon != null) {
				kitchenorder = kitchenorderSyscon.getValue();
			}
			resultObj.put("kitchenorder", kitchenorder);

			resultObj.put("success", true);
		} catch (Exception e) {
			resultObj.put("success", false);
		}

		PrintWriter out = null;
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			out = response.getWriter();
			out.print(resultObj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	// 餐点活动数据
	public String getPromotion() {
		JSONObject resultObj = new JSONObject();
		JSONObject promotion = new JSONObject();

		List<FoodStatus> list = foodStatusService.findList(terminalId);

		if (CollectionUtils.isNotEmpty(list)) {
			for (FoodStatus foodStatus : list) {
				JSONObject each = new JSONObject();
				each.put("pattern", foodStatus.getPattern());

				each.put("id", foodStatus.getId());
				each.put("begin", foodStatus.getBegin());
				each.put("end", foodStatus.getEnd());
				each.put("count", foodStatus.getCount());
				// gift
				each.put("unit", foodStatus.getUnit());
				//
				each.put("discount", foodStatus.getDiscount());
				promotion.put(foodStatus.getFoodId(), each);
			}
		}
		try {
			resultObj.put("success", true);
			resultObj.put("promotion", promotion);

		} catch (Exception e) {
			resultObj.put("success", false);
		}

		PrintWriter out = null;
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			out = response.getWriter();
			out.print(resultObj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

		return SUCCESS;
	}

	// 獲取使用模板(終端)
	@SuppressWarnings("unchecked")
	public String getTerminalPlay() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal terminal = this.terminalService.findTerminalId(terminalId);
			if (terminal != null) {
				object.put("templateId", terminal.getTemplatePlay());
				object.put("success", true);
			} else {
				object.put("success", false);
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("TerminalAction getTerminalPlay ERROR", e);
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
			logger.error("TerminalAction getTerminalPlay stream ERROR", e);
		}
		return SUCCESS;
	}

	public TerminalService getTerminalService() {
		return terminalService;
	}

	public int getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	// 修改服務端時間
	@SuppressWarnings("unchecked")
	public String modifyServerTime() {
		JSONObject resultObj = new JSONObject();
		try {
			serverTime = (String) Utility.checkParmeter(serverTime);
			if (serverTime != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.parse(serverTime);
				String[] dateTime = serverTime.split(" ");
				Runtime.getRuntime().exec("cmd /c date " + dateTime[0]);
				Runtime.getRuntime().exec("cmd /c time " + dateTime[1]);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("WebAction modifServerTime ERROR", e);
		}
		try {
			response.setContentType("text/xml; charset=UTF-8");
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
			Log4JFactory.instance().error("WebAction getServerTime stream ERROR", e);
		}
		return SUCCESS;
	}

	// 遠程管理
	public String remoteManage() {
		try {
			// pageSize
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			List<Terminal> terminalList = this.terminalService.findAll();
			List<Terminal> filteredList = new ArrayList<Terminal>();// 連綫終端
			if (terminalList != null && terminalList.size() > 0) {
				for (Terminal terminal : terminalList) {
					Object[] infoObj = InfoMessage.map.get(terminal.getTerminalId());
					if (infoObj != null) {
						String status = (String) infoObj[4];
						if (!status.equals("linein")) {
							continue;
						}
						filteredList.add(terminal);
					}
				}
			}

			// filteredList = terminalList;// TODO 測試用
			int totalCount = 0;
			JSONArray array = new JSONArray();
			if (filteredList != null && filteredList.size() > 0) {
				totalCount = filteredList.size();
				if (startPage <= 0) {
					startPage = 1;
				}
				if (totalCount <= 0) {
					maxPage = 1;
				} else {
					maxPage = (totalCount + pageBean.getPageSize() - 1) / pageBean.getPageSize();
				}
				if (startPage > maxPage) {
					startPage = maxPage;
				}

				pageBean.setStartPage(startPage);
				pageBean.setMaxPage(maxPage);
				pageBean.setRowCount(totalCount);
				//
				int startIndex = pageBean.getPageSize() * (pageBean.getStartPage() - 1);
				int endIndex = startIndex + pageBean.getPageSize();
				if (endIndex > totalCount) {
					endIndex = totalCount;
				}

				filteredList = filteredList.subList(startIndex, endIndex);
				for (Terminal terminal : filteredList) {
					if (terminal.getBoottime() != null && terminal.getBoottime().indexOf(";") != -1) {
						terminal.setBoottime(terminal.getBoottime().replace(";", "<br/>"));
					}
					if (terminal.getShutdowntime() != null && terminal.getShutdowntime().indexOf(";") != -1) {
						terminal.setShutdowntime(terminal.getShutdowntime().replace(";", "<br/>"));
					}
					if (terminal.getTemplatePlay() != 0) {
						Template template = templateService.findById(terminal.getTemplatePlay());
						if (template != null) {
							terminal.setTemplateName(template.getName());
						} else {
							terminal.setTemplatePlay(-1);// 找不到節目
						}
					}
				}
			}
			request.setAttribute("lineinList", filteredList);
			request.setAttribute("pageBean", pageBean);
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction searchTerminalDetect ERROR", e);
		}
		return SUCCESS;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String saveTerminal() throws Exception {
		try {
			this.startStr = (String) Utility.checkParmeter(this.startStr);
			this.terminalId = (String) Utility.checkParmeter(this.terminalId);
			this.terminalIdEnd = (String) Utility.checkParmeter(this.terminalIdEnd);
			this.location = (String) Utility.checkParmeter(this.location);
			this.terminalService.saveTerminal(id, startStr, terminalId, terminalIdEnd, type, invoice, location, adminId, dinnerTable, teamViewerId);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("write.error"));
			Log4JFactory.instance().error("TerminalAction saveTerminal Error", e);
		}
		return INPUT;
	}

	public String saveTerminalByPre() {
		dinnerTableList = dinnerTableService.findList();

		return SUCCESS;
	}

	// 保存使用模板
	@SuppressWarnings("unchecked")
	public String saveTerminalPlay() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal terminal = this.terminalService.findTerminalId(terminalId);
			if (terminal != null) {
				terminal.setTemplatePlay(templateId);
				terminalService.updateTerminal(terminal);
				object.put("success", true);
			} else {
				object.put("success", false);
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("TerminalAction saveTerminalPlay ERROR", e);
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
			logger.error("TerminalAction saveTerminalPlay stream ERROR", e);
		}
		return SUCCESS;
	}

	// 查询
	public String searchTerminal() {
		try {
			this.searchName = (String) Utility.checkParmeter(this.searchName);
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			List<Terminal> terminalList = this.terminalService.findByPageBean(searchType, searchName, pageBean);
			String orderversion = "";
			String cookversion = "";
			// 版本
			try {
				File oFile = new File(BeanUtil.path + "downterminal/orderversion.txt");
				File cFile = new File(BeanUtil.path + "downterminal/cookversion.txt");
				FileReader fr = null;
				BufferedReader reader = null;
				if (oFile.exists()) {
					fr = new FileReader(oFile);
					reader = new BufferedReader(fr);
					String line = null;
					while ((line = reader.readLine()) != null) {
						orderversion = line.trim();
					}
					reader.close();
					fr.close();
				}
				if (cFile.exists()) {
					fr = new FileReader(cFile);
					reader = new BufferedReader(fr);
					String line = null;
					while ((line = reader.readLine()) != null) {
						cookversion = line.trim();
					}
					reader.close();
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 各機型存量
			Map<Integer, List<MoneyDeposit>> ndMap = new HashMap<Integer, List<MoneyDeposit>>();
			Map<Integer, List<MoneyDeposit>> nvMap = new HashMap<Integer, List<MoneyDeposit>>();
			Map<Integer, List<MoneyDeposit>> hopMap = new HashMap<Integer, List<MoneyDeposit>>();
			// 系統設置的現金安全存量
			List<Deposit> depositList = depositService.findList();
			Map<String, Deposit> depositMap = new HashMap<String, Deposit>();
			for (Deposit deposit : depositList) {
				depositMap.put(deposit.getName(), deposit);
			}

			try {
				if (terminalList != null && terminalList.size() > 0) {
					for (Terminal t : terminalList) {
						// 終端現金存量
						Map<String, Integer> terminalMoney = new HashMap<String, Integer>();
						String monkeyParam = t.getMoneyAmount();
						if (ValidateUtil.isStrictNotEmpty(monkeyParam)) {
							// 格式：hopper/0.1-100,0.5-10.......;nd100/100-100;nv9/100-100

							String lang = "tw";// TODO幣種
							if (monkeyParam.indexOf("nd100/10-") != -1 || monkeyParam.indexOf("nv9/1-") != -1 || monkeyParam.indexOf("hopper/0.1-") != -1) {
								lang = "cn";
							}
							String[] mpsStr = monkeyParam.split(";");// 根據機型型號分割
							for (String mps : mpsStr) {// 機型型號/參數列表machine/params
								String[] mp = mps.split("/");
								String machine = mp[0] + lang;// 機器型號machine
								String[] ps = mp[1].split(",");
								for (String p : ps) {// params
									String[] fv = p.split("-");// 面值-數量face-value
									String face = fv[0];
									int value = Integer.parseInt(fv[1]);
									terminalMoney.put(machine + face, value);
								}
							}
						}
						List<MoneyDeposit> ndList = new ArrayList<MoneyDeposit>();
						List<MoneyDeposit> nvList = new ArrayList<MoneyDeposit>();
						List<MoneyDeposit> hopList = new ArrayList<MoneyDeposit>();
						for (String s : depositMap.keySet()) {
							if (!terminalMoney.containsKey(s)) {
								continue;
							}
							Deposit d = depositMap.get(s);
							int current = terminalMoney.get(s);
							MoneyDeposit m = new MoneyDeposit();
							String name = d.getName();
							name = name.replace("tw", "");
							name = name.replace("cn", "");
							m.setName(name);
							m.setMin(d.getMin());
							m.setMax(d.getMax());
							m.setCurrent(current);
							if (name.indexOf("nd100") != -1) {
								name = name.replace("nd100", "");
								m.setName(name);
								ndList.add(m);
							} else if (name.indexOf("nv9") != -1) {
								name = name.replace("nv9", "");
								m.setName(name);
								nvList.add(m);

							} else if (name.indexOf("hopper") != -1) {
								name = name.replace("hopper", "");
								m.setName(name);
								hopList.add(m);

							}

						}
						ndMap.put(t.getId(), sortList(ndList));
						nvMap.put(t.getId(), sortList(nvList));
						hopMap.put(t.getId(), sortList(hopList));
					}
				}
			} catch (Exception e) {
			}

			request.setAttribute("ndMap", ndMap);
			request.setAttribute("nvMap", nvMap);
			request.setAttribute("hopMap", hopMap);
			request.setAttribute("terminalList", terminalList);
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("orderversion", orderversion);
			request.setAttribute("cookversion", cookversion);
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction searchTerminal ERROR", e);
		}
		return INPUT;
	}

	// 排序
	private static List<MoneyDeposit> sortList(List<MoneyDeposit> list) {
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				MoneyDeposit data1 = (MoneyDeposit) o1;
				MoneyDeposit data2 = (MoneyDeposit) o2;
				//
				return Integer.parseInt(data1.getName()) - Integer.parseInt(data2.getName());
			}
		});
		return list;
	}

	// 查询監控一览
	public String searchTerminalDetect() {
		JSONObject resultObj = new JSONObject();
		try {
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			List<Terminal> terminalList = this.terminalService.findByPageBean(0, null, pageBean);
			JSONArray array = new JSONArray();
			if (terminalList != null && terminalList.size() > 0) {
				for (Terminal terminal : terminalList) {
					JSONObject tObj = new JSONObject();
					tObj.put("id", terminal.getTerminalId());
					tObj.put("location", terminal.getLocation() == null ? "" : terminal.getLocation());
					Object[] t = InfoMessage.map.get(terminal.getTerminalId());
					if (t != null) {
						tObj.put("time", t[2]);
						tObj.put("status", t[3]);
						tObj.put("online", t[4]);
						tObj.put("picture", t[5]);
					}
					array.add(tObj);
				}
			}
			resultObj.put("success", true);
			resultObj.put("detect", array);
			resultObj.put("startPage", pageBean.getStartPage());
			resultObj.put("pageSize", pageBean.getPageSize());
			resultObj.put("maxPage", pageBean.getMaxPage());
			resultObj.put("rowCount", pageBean.getRowCount());
		} catch (Exception e) {
			resultObj.put("success", false);
			resultObj.put("errorMsg", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction searchTerminalDetect ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction searchTerminalDetect stream ERROR", e);
		}
		return SUCCESS;
	}

	// 查询監控畫面
	public String searchTerminalDetectPage() {
		JSONObject resultObj = new JSONObject();
		try {
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			terminalId = (String) Utility.checkParmeter(this.terminalId);
			queryline = (String) Utility.checkParmeter(this.queryline);
			List<Terminal> terminalList = this.terminalService.findAll();
			List<Terminal> filteredList = new ArrayList<Terminal>();
			if (terminalList != null && terminalList.size() > 0) {
				for (Terminal terminal : terminalList) {
					if (terminalId != null) {// 只看終端機名稱
						if (terminal.getTerminalId().indexOf(terminalId) == -1) {
							continue;
						}
					}
					Object[] infoObj = InfoMessage.map.get(terminal.getTerminalId());
					if (infoObj != null) {
						String status = (String) infoObj[4];
						// 過濾終端機連線狀態
						if (queryline != null) {
							if (!status.equals(queryline)) {
								continue;
							}
						}
						filteredList.add(terminal);
					}
				}
			}
			int totalCount = 0;
			JSONArray array = new JSONArray();
			if (filteredList != null && filteredList.size() > 0) {
				totalCount = filteredList.size();
				int startPage = pageBean.getStartPage();
				maxPage = 0;
				if (totalCount <= 0) {
					startPage = 1;
					maxPage = 1;
				} else {
					if (totalCount % pageBean.getPageSize() == 0) {
						maxPage = totalCount / pageBean.getPageSize();
					} else {
						maxPage = totalCount / pageBean.getPageSize() + 1;
					}
				}
				if (startPage > maxPage) {
					startPage = maxPage;
				}
				if (startPage <= 1) {
					startPage = 1;
				}
				pageBean.setStartPage(startPage);
				pageBean.setMaxPage(maxPage);
				pageBean.setRowCount(totalCount);
				int startIndex = pageBean.getPageSize() * (pageBean.getStartPage() - 1);
				int endIndex = startIndex + pageBean.getPageSize();
				if (endIndex > totalCount) {
					endIndex = totalCount;
				}
				if (startIndex > filteredList.size() - 1 || endIndex > filteredList.size()) {
					startIndex = 0;
					endIndex = endIndex > filteredList.size() ? filteredList.size() : endIndex;
				}
				filteredList = filteredList.subList(startIndex, endIndex);// 傳送要顯示的終端機編號到頁面，頁面根據終端機編號獲取圖像
				for (Terminal terminal : filteredList) {
					JSONObject tObj = new JSONObject();
					tObj.put("id", terminal.getTerminalId());
					tObj.put("type", terminal.getType() == 1 ? "order" : "cook");
					tObj.put("location", terminal.getLocation() == null ? "" : terminal.getLocation());
					Object[] t = InfoMessage.map.get(terminal.getTerminalId());
					if (t != null) {
						tObj.put("time", t[2]);
						tObj.put("status", t[3]);
						tObj.put("online", t[4]);
						tObj.put("picture", t[5]);
					}
					array.add(tObj);
				}
			}
			resultObj.put("success", true);
			resultObj.put("detect", array);
			resultObj.put("startPage", pageBean.getStartPage());
			resultObj.put("pageSize", pageBean.getPageSize());
			resultObj.put("maxPage", pageBean.getMaxPage());
			resultObj.put("rowCount", pageBean.getRowCount());
		} catch (Exception e) {
			resultObj.put("success", false);
			resultObj.put("errorMsg", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction searchTerminalDetect ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction searchTerminalDetect stream ERROR", e);
		}
		return SUCCESS;
	}

	// 操作终端
	public String sendOrder() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			orderType = (String) Utility.checkParmeter(orderType);
			orderString = (String) Utility.checkParmeter(orderString);
			if (terminalId != null) {
				String[] terminalArry = terminalId.split(";");
				for (String id : terminalArry) {
					if (ValidateUtil.isStrictNotEmpty(id)) {
						Terminal t = terminalService.findById(Integer.parseInt(id.trim()));
						if (t != null) {
							if (orderType.equals("timecheck")) {
								BeanUtil.checkTimeMap.put(t.getTerminalId(), true);
							}
							if (orderType.equals("reboot")) {
								BeanUtil.rebootMap.put(t.getTerminalId(), true);
							}
							if (orderType.equals("shutdown")) {
								BeanUtil.shutDownMap.put(t.getTerminalId(), true);
							}

							if (orderType.equals("close")) {//
								BeanUtil.closeTeamViewer.put(t.getTerminalId(), true);
							}
							if (orderType.equals("open")) {//
								BeanUtil.bootTeamViewer.put(t.getTerminalId(), true);
							}

							if (orderType.equals("boottime")) {
								t.setBoottime(orderString);
								terminalService.updateTerminal(t);
							}
							if (orderType.equals("shutdowntime")) {
								t.setShutdowntime(orderString);
								terminalService.updateTerminal(t);
							}
							if (orderType.equals("enableshutdown")) {
								t.setEnableshutdown(Integer.valueOf(orderString));
								terminalService.updateTerminal(t);
							}
						}
					}
				}
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("WebAction checkTerminalTime ERROR", e);
		}
		try {
			response.setContentType("text/xml; charset=UTF-8");
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
			Log4JFactory.instance().error("WebAction checkTerminalTime stream ERROR", e);
		}

		return SUCCESS;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	public void setDinnerTable(String dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	public void setDinnerTableList(List<DinnerTable> dinnerTableList) {
		this.dinnerTableList = dinnerTableList;
	}

	@Resource
	public void setDinnerTableService(DinnerTableService dinnerTableService) {
		this.dinnerTableService = dinnerTableService;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMoneyAmount(String moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setQueryline(String queryline) {
		this.queryline = queryline;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setTerminalIdEnd(String terminalIdEnd) {
		this.terminalIdEnd = terminalIdEnd;
	}

	public void setTerminalService(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String terminalDownOnline() {
		try {
			Template t = templateService.findById(templateId);
			List<Terminal> terminalList = this.terminalService.findAll();
			List<Terminal> filteredList = new ArrayList<Terminal>();
			if (terminalList != null && terminalList.size() > 0) {
				for (Terminal terminal : terminalList) {
					if (terminal.getType() != 1) {
						continue;
					}
					Object[] infoObj = InfoMessage.map.get(terminal.getTerminalId());
					if (infoObj != null) {
						String status = (String) infoObj[4];
						if (!status.equals("linein")) {
							continue;
						}
						filteredList.add(terminal);
					}
				}
			}
			request.setAttribute("lineinList", filteredList);
			request.setAttribute("templateId", t.getId());
			request.setAttribute("templateName", t.getName());
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("TerminalAction terminalDownOnline ERROR", e);
		}
		return SUCCESS;
	}

	// 更新終端現金數量
	public String updateTerminalMoney() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal t = terminalService.findTerminalId(terminalId);
			if (t != null && t.getType() == t.TYPE_SHOP) {
				t.setMoneyAmount(moneyAmount);
				this.terminalService.updateTerminal(t);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney stream ERROR", e);
		}
		return SUCCESS;
	}

	public String updateTerminalVer() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal t = terminalService.findTerminalId(terminalId);
			if (t != null) {
				t.setVersion(version);
				this.terminalService.updateTerminal(t);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("TerminalAction updateTerminalVer ERROR", e);
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
			Log4JFactory.instance().error("TerminalAction updateTerminalVer stream ERROR", e);
		}
		return SUCCESS;
	}

	public String getTeamViewerId() {
		return teamViewerId;
	}

	public void setTeamViewerId(String teamViewerId) {
		this.teamViewerId = teamViewerId;
	}

	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	// 現金警報
	public String cashWarn() {
		JSONObject result = new JSONObject();
		try {
			// 後臺現金存量安全設置
			List<Deposit> depositList = depositService.findList();
			JSONObject safeConfig = new JSONObject();
			for (Deposit deposit : depositList) {
				String name = deposit.getName();
				if (!ValidateUtil.isStrictNotEmpty(name)) {
					continue;
				}
				if (name.indexOf("nd100") == -1) {// nd100不需要最大值
					safeConfig.put(name + "max", deposit.getMax());
				}
				if (name.indexOf("nv9") == -1) {// nv9不需要最小值
					safeConfig.put(name + "min", deposit.getMin());
				}
			}
			// 終端現金存量
			JSONObject stockArray = new JSONObject();
			JSONObject terminalArray = new JSONObject();

			terminalId = (String) Utility.checkParmeter(terminalId);
			List<Terminal> terminalList = terminalService.find(-1, terminalId);
			List<Terminal> inLineList = new ArrayList<Terminal>();// 連綫終端
			if (terminalList != null && terminalList.size() > 0) {// 在綫終端
				for (Terminal terminal : terminalList) {
					if (terminal.getType() != 1) {// 過濾廚房端
						continue;
					}
					Object[] infoObj = InfoMessage.map.get(terminal.getTerminalId());
					if (infoObj != null) {
						String status = (String) infoObj[4];
						if (!status.equals("linein")) {
							continue;
						}
						inLineList.add(terminal);
					}
				}
			}

			for (Terminal terminal : inLineList) {
				JSONObject terminalMoney = new JSONObject();
				String monkeyParam = terminal.getMoneyAmount();
				if (ValidateUtil.isStrictNotEmpty(monkeyParam)) {
					// 格式：hopper/0.1-100,0.5-10.......;nd100/100-100;nv9/100-100
					String lang = "tw";
					if (monkeyParam.indexOf("nd100/10-") != -1 || monkeyParam.indexOf("nv9/1-") != -1 || monkeyParam.indexOf("hopper/0.1-") != -1) {
						lang = "cn";
					}
					String[] mpsStr = monkeyParam.split(";");// 根據機型型號分割
					for (String mps : mpsStr) {// 機型型號/參數列表machine/params
						String[] mp = mps.split("/");
						String machine = mp[0] + lang;// 機器型號machine
						String[] ps = mp[1].split(",");
						for (String p : ps) {// params
							String[] fv = p.split("-");// 面值-數量face-value
							String face = fv[0];
							int value = Integer.parseInt(fv[1]);
							terminalMoney.put(machine + face, value);
						}
					}
				}
				stockArray.put(terminal.getTerminalId(), terminalMoney);
				terminalArray.put(terminal.getTerminalId(), terminal.getLocation());
			}

			result.put("safeConfig", safeConfig);
			result.put("stockArray", stockArray);
			result.put("terminalArray", terminalArray);
			result.put("success", true);

		} catch (Exception e) {
			result.put("success", false);
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney ERROR", e);
		}

		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(result);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("TerminalAction updateTerminalMoney stream ERROR", e);
		}

		return SUCCESS;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	@Resource
	public void setFoodTypeService(FoodTypeService foodTypeService) {
		this.foodTypeService = foodTypeService;
	}

	@Resource
	public void setFoodStatusService(FoodStatusService foodStatusService) {
		this.foodStatusService = foodStatusService;
	}

}
