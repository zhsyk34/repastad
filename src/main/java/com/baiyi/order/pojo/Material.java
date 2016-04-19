package com.baiyi.order.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Admins entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Material {

	public static int TYPE_IMAGE = 1;
	public static int TYPE_MOVIE = 2;

	private int id;
	private String name;//食物名稱
	private int type;//類型 1為圖片
	private String path;//路徑
	
	private int isLogo;//login:1,index:2,default:0
	
	private int adminId;//人員
	private Date addtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getIsLogo() {
		return isLogo;
	}

	public void setIsLogo(int isLogo) {
		this.isLogo = isLogo;
	}
	
}