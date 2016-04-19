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

public class Admins implements java.io.Serializable {

	// Fields

	private int id;
	private String username;
	private String password;
	private Date addtime;
	private int pageCount;//用戶設置的每頁查詢數量

	/** default constructor */
	public Admins() {
	}

	/** minimal constructor */
	public Admins(Integer id) {
		this.id = id;
	}


	// Property accessors

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean euqals(Object obj){
		if(this.id == ((Admins)obj).id){
			return true;
		}
		else{
			return  false;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}