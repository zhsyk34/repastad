package com.baiyi.order.pojo;

import java.util.Date;

/**
 * Admins entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderRule {

	private int id;
	private int foodId;//相關食物
	private String startNo;//編號開頭
	private int noNumber;//共幾位
	private int minNumber;//起始值
	private int used;//啟用

	private Date addtime;
	private int adminId;
	
	private String foodName;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public int getNoNumber() {
		return noNumber;
	}

	public void setNoNumber(int noNumber) {
		this.noNumber = noNumber;
	}

	public int getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
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

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}
	
}