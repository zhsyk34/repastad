package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.DinnerTable;

public interface DinnerTableDao extends CommonsDao<DinnerTable> {

	public List<DinnerTable> findList(String name, String style, int index, int length);

	public int count(String name, String style);

}
