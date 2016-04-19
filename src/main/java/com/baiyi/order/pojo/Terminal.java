package com.baiyi.order.pojo;

import java.util.Date;

public class Terminal {

	public static int TYPE_ALL = 0;

	public static int TYPE_COOK = 2;

	public static int TYPE_SHOP = 1;

	private int id;

	private String terminalId;// 終端編號

	private int type;// 類型，1收費端 2廚房端

	private String location;// 位置

	private String version;// 版本

	private String boottime;// 開機時間

	private String shutdowntime;// 關機時間

	private int enableshutdown;// 是否關機

	private int templatePlay;// 使用模板

	private int invoice;// 是否打印發票

	private int adminId;

	private Date addtime;

	private String templateName;

	private String moneyAmount;// 货币存量相关

	private String dinnerTable;// 桌号

	private String teamViewerId;// 终端TV id

	public String getTeamViewerId() {
		return teamViewerId;
	}

	public void setTeamViewerId(String teamViewerId) {
		this.teamViewerId = teamViewerId;
	}

	public String getDinnerTable() {
		return dinnerTable;
	}

	public void setDinnerTable(String dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBoottime() {
		return boottime;
	}

	public void setBoottime(String boottime) {
		this.boottime = boottime;
	}

	public String getShutdowntime() {
		return shutdowntime;
	}

	public void setShutdowntime(String shutdowntime) {
		this.shutdowntime = shutdowntime;
	}

	public int getEnableshutdown() {
		return enableshutdown;
	}

	public void setEnableshutdown(int enableshutdown) {
		this.enableshutdown = enableshutdown;
	}

	public int getTemplatePlay() {
		return templatePlay;
	}

	public void setTemplatePlay(int templatePlay) {
		this.templatePlay = templatePlay;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(String moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

}