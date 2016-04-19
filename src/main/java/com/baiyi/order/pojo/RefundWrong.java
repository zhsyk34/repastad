package com.baiyi.order.pojo;

import java.util.Date;

//退幣異常記錄
public class RefundWrong {

	private Integer id;

	private String sno;// 序列號

	// 上傳數據
	private String terminalId;// 終端編號�ն�

	private String orderId;// 訂單編號���

	private int reason;// 錯誤原因:1.機器異常;2.餘額不足��

	private int type;// 錯誤類型:1.找零失敗;2退幣失敗�˱�ʧ��

	private double amount;// 應退金額�˽��

	private Date happenTime;// �˱�ʧ��ʱ�� �ն��ϴ���ʽ發生錯誤的時間

	// 操作確認��ȡ��ȷ��
	private int isGet;// 是否已領取:0未領取;1已領取.默認0

	private Date dealTime;// 處理時間ȷ����Ǯʱ��

	private int adminId;// ��ȡʱ���ĸ��Աȷ�ϵ操作員編號,未領取爲0

	// 自動生成�Զ����
	private Date addTime;// 後臺接收到數據的時間

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Date getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(Date happenTime) {
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

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

}
