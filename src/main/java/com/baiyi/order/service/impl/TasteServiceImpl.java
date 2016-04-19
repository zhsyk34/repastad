package com.baiyi.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.TasteDao;
import com.baiyi.order.pojo.Taste;
import com.baiyi.order.service.TasteService;
import com.baiyi.order.vo.TasteVO;

@Service("tasteService")
public class TasteServiceImpl extends CommonsServiceImpl<Taste> implements TasteService {

	private TasteDao tasteDao;

	@Resource
	public void setTasteDao(TasteDao tasteDao) {
		this.tasteDao = tasteDao;
	}

	public int count(String name, Integer type) {
		return tasteDao.count(name, type);
	}

	public List<Taste> findList(String name, Integer type, int pageNo, int pageSize) {
		return tasteDao.findList(name, type, (pageNo - 1) * pageSize, pageSize);
	}

	public List<TasteVO> findVOList(String name, Integer type, int pageNo, int pageSize) {
		return tasteDao.findVOList(name, type, (pageNo - 1) * pageSize, pageSize);
	}

}
