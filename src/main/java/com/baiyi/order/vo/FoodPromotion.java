package com.baiyi.order.vo;

import java.util.Date;

public class FoodPromotion {

	private Integer id;

	private Integer foodId;

	private String foodName;

	private String path;

	private Integer terminalId;

	private String terminalNo;

	private String location;

	private Integer pattern;// 促銷方式：1贈品,2打折

	private int unit;// 贈品規模

	private int total;// 贈品總數

	private int send;// 剩餘贈品數

	private double originalPrice;// 原價

	private double cheapPrice;// 促銷價

	private int percent;// 折扣

	private Date begin;

	private Date end;

	public FoodPromotion() {
	}

	public FoodPromotion(Integer id, Integer foodId, String foodName, String path, Integer terminalId, String terminalNo, String location, Integer pattern, int unit, int total, int send, double originalPrice, double cheapPrice, int percent, Date begin, Date end) {
		this.id = id;
		this.foodId = foodId;
		this.foodName = foodName;
		this.path = path;
		this.terminalId = terminalId;
		this.terminalNo = terminalNo;
		this.location = location;
		this.pattern = pattern;
		this.unit = unit;
		this.total = total;
		this.send = send;
		this.originalPrice = originalPrice;
		this.cheapPrice = cheapPrice;
		this.percent = percent;
		this.begin = begin;
		this.end = end;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public double getCheapPrice() {
		return cheapPrice;
	}

	public void setCheapPrice(double cheapPrice) {
		this.cheapPrice = cheapPrice;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
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

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getSend() {
		return send;
	}

	public void setSend(int send) {
		this.send = send;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
