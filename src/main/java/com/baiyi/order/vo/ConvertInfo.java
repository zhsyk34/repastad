package com.baiyi.order.vo;

/**
 * 存储要转换的视频信息
 * @author Administrator
 *
 */
public class ConvertInfo {

	private int id;
	
	private String path;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ConvertInfo(int id,String path) {
		this.id = id;
		this.path = path;
	}
	
	
	
}
