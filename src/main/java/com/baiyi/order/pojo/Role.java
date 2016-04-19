package com.baiyi.order.pojo;

public class Role {

	private Integer id;

	private String name;

	private String chName;

	private String twName;

	// 初始化角色，永久：1，默认：0
	private int permanent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPermanent() {
		return permanent;
	}

	public void setPermanent(int permanent) {
		this.permanent = permanent;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getTwName() {
		return twName;
	}

	public void setTwName(String twName) {
		this.twName = twName;
	}
}
