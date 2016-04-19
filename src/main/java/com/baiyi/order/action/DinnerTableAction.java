package com.baiyi.order.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baiyi.order.pojo.DinnerTable;
import com.baiyi.order.service.DinnerTableService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DinnerTableAction extends ActionSupport {

	private DinnerTableService dinnerTableService;

	private DinnerTable dinnerTable;

	private List<DinnerTable> dinnerTableList;

	private Integer id;

	private Integer[] ids;

	private String searchName;

	private String name;

	private String style;

	private int pageNo;

	private int pageSize;

	private int pageCount;

	private int count;

	private int begin;

	private int end;

	private int batch;

	private String message;

	private Map jsonMap = new HashMap<String, Object>();

	@Resource
	public void setDinnerTableService(DinnerTableService dinnerTableService) {
		this.dinnerTableService = dinnerTableService;
	}

	/* action method */
	public String pre() {
		if (id != null && id > 0) {
			dinnerTable = dinnerTableService.find(id);
		}
		return SUCCESS;
	}

	public String add() {
		dinnerTable = new DinnerTable();
		dinnerTable.setName(name);
		dinnerTable.setAddTime(new Date());
		dinnerTableService.save(dinnerTable);
		return SUCCESS;
	}

	public String delete() {
		if (ids != null && ids.length > 0) {
			dinnerTableService.delete(ids);
		}
		message = "delete";
		return SUCCESS;
	}

	public String update() {
		dinnerTable = dinnerTableService.find(id);
		dinnerTable.setName(name);
		dinnerTableService.update(dinnerTable);
		message = "success";
		return SUCCESS;
	}

	public String merge() {
		if (id != null && id > 0) {
			dinnerTable = dinnerTableService.find(id);
			dinnerTable.setName(name);
			dinnerTableService.update(dinnerTable);
			message = "modify";
		} else {
			if (batch > 0) {
				for (int i = begin; i <= end; i++) {
					dinnerTable = new DinnerTable();
					dinnerTable.setName(name + i);
					dinnerTable.setAddTime(new Date());
					dinnerTableService.save(dinnerTable);
					message = "add";
				}
			} else {
				dinnerTable = new DinnerTable();
				dinnerTable.setName(name);
				dinnerTable.setAddTime(new Date());
				dinnerTableService.save(dinnerTable);
				message = "add";
			}
		}

		return SUCCESS;
	}

	public String find() {
		pageNo = pageNo <= 0 ? 1 : pageNo;
		pageSize = pageSize <= 0 ? 10 : pageSize;
		dinnerTableList = dinnerTableService.findList(searchName, style, pageNo, pageSize);
		count = dinnerTableService.count(searchName, style);
		pageCount = (count + pageSize - 1) / pageSize;
		return SUCCESS;
	}

	/* getter and setter */
	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public DinnerTable getDinnerTable() {
		return dinnerTable;
	}

	public void setDinnerTable(DinnerTable dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	public List<DinnerTable> getDinnerTableList() {
		return dinnerTableList;
	}

	public void setDinnerTableList(List<DinnerTable> dinnerTableList) {
		this.dinnerTableList = dinnerTableList;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
