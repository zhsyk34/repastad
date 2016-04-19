package com.baiyi.order.pojo;

import java.sql.Timestamp;

public class Detectrecords implements java.io.Serializable {

	private Integer id;

	private String no;

	private String station;

	private String status;

	private String line;

	private String ip;

	private Timestamp addtime;

	// Constructors

	/** default constructor */
	public Detectrecords() {
	}

	/** full constructor */
	public Detectrecords(String no, String station, String status, String line, Timestamp addtime) {
		this.no = no;
		this.station = station;
		this.status = status;
		this.line = line;
		this.addtime = addtime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}