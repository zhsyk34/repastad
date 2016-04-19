package com.baiyi.order.pojo;

import java.util.Date;

public class DownRecord {

	public static final int ISDOWN = 1;// 终端用户已下载

	public static final int UNDOWN = 0;// 终端用户未下载

	public static final int ISDELETE = 1;// 终端用户已下载

	public static final int UNDELETE = 0;// 终端用户未下载

	private int id;

	private int templateId;// 下載模板id

	private String terminalId;// 下載終端編號

	private int isDown;// 是否下載

	private int isDelete;// 是否下載

	private Date addtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public int getIsDown() {
		return isDown;
	}

	public void setIsDown(int isDown) {
		this.isDown = isDown;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}