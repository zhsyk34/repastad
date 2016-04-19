package com.baiyi.order.vo;

import java.util.Date;

public class FoodStatus {

	private int id;

	private int foodId;

	private String foodName;

	private String path;

	private int terminalId;

	private String terminalNo;

	private String location;

	private Date begin;

	private Date end;

	public FoodStatus() {
	}

	public FoodStatus(int id, int foodId, String foodName, String path, int terminalId, String terminalNo, String location, Date begin, Date end) {
		this.id = id;
		this.foodId = foodId;
		this.foodName = foodName;
		this.path = path;
		this.terminalId = terminalId;
		this.terminalNo = terminalNo;
		this.location = location;
		this.begin = begin;
		this.end = end;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
