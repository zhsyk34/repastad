package com.baiyi.order.pojo;

import java.util.Date;

public class FoodStatus {

	private Integer id;

	private Integer foodId;

	private String foodName;// vo

	private double price;// vo

	private String path;// vo

	private Integer terminalId;

	private String terminalNo;// vo

	private String terminalLocation;// vo

	private Integer pattern;// 促銷方式：-1未活动,0全部,1打折/促销,2贈品,3停售

	private int unit;// 每份贈品数量

	private int count;// 停售 贈品、打折總量

	private int send;// 已完成的贈品、打折數量

	private double discount;// 促銷價

	private int percent;// 折扣(百分比)

	private String begin;

	private String end;

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getTerminalLocation() {
		return terminalLocation;
	}

	public void setTerminalLocation(String terminalLocation) {
		this.terminalLocation = terminalLocation;
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

}
