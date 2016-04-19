package com.baiyi.order.vo;

public class TasteVO {

	private int id;

	private String name;

	private Integer typeId;

	private String typeName;

	private double price;

	public TasteVO() {
	}

	public TasteVO(int id, String name, Integer typeId, String typeName, double price) {
		super();
		this.id = id;
		this.name = name;
		this.typeId = typeId;
		this.typeName = typeName;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
