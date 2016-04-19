package com.baiyi.order.pojo;

import java.util.Date;

public class Marquee {

	private int id;

	private String title;

	private String color;

	private String size;

	private String direction;

	private String m_content;

	private String speed;

	private String fontfamily;

	private int adminid;

	private Date addTime;

	private int colorOrImg; // 1.背景色，2，背景圖

	private String background;

	private String adminName;// 与dao无关,

	private int isRss = 0;// 0.默認文字，2，rss新聞

	public Marquee() {

	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getM_content() {
		return m_content;
	}

	public void setM_content(String m_content) {
		this.m_content = m_content;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getFontfamily() {
		return fontfamily;
	}

	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}

	public int getColorOrImg() {
		return colorOrImg;
	}

	public void setColorOrImg(int colorOrImg) {
		this.colorOrImg = colorOrImg;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getIsRss() {
		return isRss;
	}

	public void setIsRss(int isRss) {
		this.isRss = isRss;
	}

}
