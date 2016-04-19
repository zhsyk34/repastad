package com.baiyi.order.pojo;

import java.util.Date;

public class Template {

	private int id;

	private String name;

	private String banner;

	private String marquee;

	private String cakeId;

	private int titleImg;

	private String selectPart;// 選擇廣告內容

	private String video;

	private String picture;

	private String picTime;

	private String effect;

	private int adminId;

	private Date addtime;

	private String cakePath;

	private String titleImgPath;

	private String bannerPath;

	private String marqueeStr;

	private String videoStr;

	private String pictureStr;

	private String type;

	private String size;

	private int row;

	private int col;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCakeId() {
		return cakeId;
	}

	public void setCakeId(String cakeId) {
		this.cakeId = cakeId;
	}

	public String getCakePath() {
		return cakePath;
	}

	public void setCakePath(String cakePath) {
		this.cakePath = cakePath;
	}

	public int getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(int titleImg) {
		this.titleImg = titleImg;
	}

	public String getTitleImgPath() {
		return titleImgPath;
	}

	public void setTitleImgPath(String titleImgPath) {
		this.titleImgPath = titleImgPath;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public String getBannerPath() {
		return bannerPath;
	}

	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}

	public String getMarqueeStr() {
		return marqueeStr;
	}

	public void setMarqueeStr(String marqueeStr) {
		this.marqueeStr = marqueeStr;
	}

	public String getSelectPart() {
		return selectPart;
	}

	public void setSelectPart(String selectPart) {
		this.selectPart = selectPart;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideoStr() {
		return videoStr;
	}

	public void setVideoStr(String videoStr) {
		this.videoStr = videoStr;
	}

	public String getPictureStr() {
		return pictureStr;
	}

	public void setPictureStr(String pictureStr) {
		this.pictureStr = pictureStr;
	}

	public String getPicTime() {
		return picTime;
	}

	public void setPicTime(String picTime) {
		this.picTime = picTime;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}