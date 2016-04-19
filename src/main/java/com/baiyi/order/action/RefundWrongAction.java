package com.baiyi.order.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Admins;
import com.baiyi.order.pojo.RefundWrong;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.RefundWrongService;
import com.baiyi.order.util.Log4JFactory;

@SuppressWarnings("serial")
public class RefundWrongAction extends BasicAction {

	private RefundWrongService refundWrongService;

	private AdminsService adminsService;

	private List<Admins> adminList;

	private RefundWrong refundWrong;

	private List<RefundWrong> refundWrongList;

	// refundWrong 屬性

	private String sno;// 流水號

	private String terminalId;// 終端編號�ն�

	private String orderId;// 訂單編號���

	private int reason;// 錯誤原因:1.機器異常;2.餘額不足��

	private int type;// 錯誤類型:1.找零失敗;2退幣失敗�˱�ʧ��

	private double amount;// 應退金額�˽��

	private String happenTime;// �˱�ʧ��ʱ�� �ն��ϴ���ʽ發生錯誤的時間

	private int isGet;// 是否已領取:1未領取;2已領取 //全部數據

	private int adminId;// ��ȡʱ���ĸ��Աȷ�ϵ操作員編號,未領取爲0

	private Date addTime;// 後臺接收到數據的時間

	// 修改狀態

	private Integer id;

	private int dealStatus;// 修改的數據的處理狀態

	private Date dealNewTime;// 處理時間

	// 查詢時間
	private String beginTime;

	private String endTime;

	// 翻頁

	private int pageNo;

	private int pageSize;

	private int pageCount;

	private int count;

	// return json data
	private Map jsonMap = new HashMap<String, Object>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public List<Admins> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admins> adminList) {
		this.adminList = adminList;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public RefundWrongService getRefundWrongService() {
		return refundWrongService;
	}

	public Date getDealNewTime() {
		return dealNewTime;
	}

	public void setDealNewTime(Date dealNewTime) {
		this.dealNewTime = dealNewTime;
	}

	public int getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(int dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIsGet() {
		return isGet;
	}

	public void setIsGet(int isGet) {
		this.isGet = isGet;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
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

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}

	public RefundWrong getRefundWrong() {
		return refundWrong;
	}

	public void setRefundWrong(RefundWrong refundWrong) {
		this.refundWrong = refundWrong;
	}

	public List<RefundWrong> getRefundWrongList() {
		return refundWrongList;
	}

	public void setRefundWrongList(List<RefundWrong> refundWrongList) {
		this.refundWrongList = refundWrongList;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Resource
	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	};

	@Resource
	public void setRefundWrongService(RefundWrongService refundWrongService) {
		this.refundWrongService = refundWrongService;
	}

	/*------------------------------------*/
	public String mod() {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dealNewTime = new Date();
		String dateStr = format.format(dealNewTime);

		Admins user = (Admins) session.get("user");

		refundWrong = refundWrongService.find(id);
		refundWrong.setIsGet(dealStatus);
		refundWrong.setAdminId(user.getId());
		refundWrong.setDealTime(dealNewTime);
		refundWrongService.update(refundWrong);

		jsonMap.put("result", "success");
		jsonMap.put("dateStr", dateStr);
		jsonMap.put("username", user.getUsername());
		return SUCCESS;
	};

	public String del() {
		return SUCCESS;
	};

	public String add() {
		JSONObject resultObj = new JSONObject();
		refundWrong = refundWrongService.find(sno);
		if (refundWrong != null) {
			resultObj.put("success", "the data is exsit");
		} else {
			try {
				refundWrong = new RefundWrong();
				refundWrong.setTerminalId(terminalId);
				refundWrong.setSno(sno);
				refundWrong.setReason(reason);
				refundWrong.setType(type);
				refundWrong.setOrderId(orderId);
				refundWrong.setHappenTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(happenTime));
				refundWrong.setAmount(amount);
				refundWrongService.save(refundWrong);
				resultObj.put("success", true);
			} catch (Exception e) {
				resultObj.put("success", false);
				e.printStackTrace();
			}
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
	};

	public String list() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		refundWrongList = refundWrongService.findList(terminalId, orderId, sno, beginTime, endTime, isGet, adminId, pageNo, pageSize);

		if (refundWrongList != null && refundWrongList.size() > 0) {
			count = refundWrongService.findList(terminalId, orderId, sno, beginTime, endTime, isGet, adminId, 0, 0).size();
		}

		adminList = adminsService.findList();
		pageCount = (count + pageSize - 1) / pageSize;
		return SUCCESS;
	}
}
