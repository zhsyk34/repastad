package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.DinnerTable;

public interface DinnerTableService extends CommonsService<DinnerTable> {

	public List<DinnerTable> findList(String name, String style, int pageNo, int pageSize);

	public int count(String name, String style);

}
