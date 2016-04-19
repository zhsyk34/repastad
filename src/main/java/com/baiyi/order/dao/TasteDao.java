package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Taste;
import com.baiyi.order.vo.TasteVO;

public interface TasteDao extends CommonsDao<Taste> {

	public List<Taste> findList(String name, Integer type, int index, int length);

	public List<TasteVO> findVOList(String name, Integer type, int index, int length);

	public int count(String name, Integer type);
	
}
