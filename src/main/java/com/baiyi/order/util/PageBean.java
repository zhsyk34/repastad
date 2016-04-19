package com.baiyi.order.util;

public class PageBean {

	private Integer rowCount = 0;

	private Integer startPage;

	private Integer maxPage = 0;

	private Integer pageSize ;

	public PageBean(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

}
