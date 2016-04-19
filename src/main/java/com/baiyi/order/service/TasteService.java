package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Taste;
import com.baiyi.order.vo.TasteVO;

public interface TasteService extends CommonsService<Taste> {

	public List<Taste> findList(String name, Integer type, int pageNo, int pageSize);

	public List<TasteVO> findVOList(String name, Integer type, int pageNo, int pageSize);

	public int count(String name, Integer type);

}
