package com.baiyi.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.TasteTypeDao;
import com.baiyi.order.pojo.TasteType;
import com.baiyi.order.service.TasteTypeService;

@Service("tasteTypeService")
public class TasteTypeServiceImpl extends CommonsServiceImpl<TasteType> implements TasteTypeService {

	private TasteTypeDao tasteTypeDao;

	@Resource
	public void setTasteTypeDao(TasteTypeDao tasteTypeDao) {
		this.tasteTypeDao = tasteTypeDao;
	}

	public TasteType find(String name) {
		return tasteTypeDao.find(name);
	}

}
