package com.baiyi.order.vo;

import java.util.Date;
import java.util.List;

public class OrderMessage {

	private String id;

	private String cook;

	private String shop;

	private List<OrderContent> contentList;

	private Date clock;

	private float income;// 收款

	private float pay;// 付款

	private float total;

	public Date getClock() {
		return clock;
	}

	public List<OrderContent> getContentList() {
		return contentList;
	}

	public String getCook() {
		return cook;
	}

	public String getId() {
		return id;
	}

	public String getShop() {
		return shop;
	}

	public float getTotal() {
		return total;
	}

	public void setClock(Date clock) {
		this.clock = clock;
	}

	public void setContentList(List<OrderContent> contentList) {
		this.contentList = contentList;
	}

	public void setCook(String cook) {
		this.cook = cook;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setShop(String shop) {
		this.shop = shop;
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

	public void setTotal(float total) {
		this.total = total;
	}

}
