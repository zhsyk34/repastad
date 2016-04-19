package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DinnerTableDao;
import com.baiyi.order.dao.impl.CommonsDaoImpl;
import com.baiyi.order.pojo.DinnerTable;
import com.baiyi.order.service.DinnerTableService;

@Service("dinnerTableService")
public class DinnerTableServiceImpl extends CommonsDaoImpl<DinnerTable> implements DinnerTableService {

	private DinnerTableDao dinnerTableDao;

	@Resource
	public void setDinnerTableDao(DinnerTableDao dinnerTableDao) {
		this.dinnerTableDao = dinnerTableDao;
	}

	public int count(String name, String style) {
		return dinnerTableDao.count(name, style);
	}

	public List<DinnerTable> findList(String name, String style, int pageNo, int pageSize) {
		return dinnerTableDao.findList(name, style, (pageNo - 1) * pageSize, pageSize);
	}

}
