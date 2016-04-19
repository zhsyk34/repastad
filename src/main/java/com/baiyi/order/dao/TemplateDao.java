package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Template;
import com.baiyi.order.util.PageBean;

public interface TemplateDao {

	List<Template> findByPage(String name, PageBean pageBean);

	Template findById(int id);

	List<Template> findByFoodId(String column, int id);

	List<Template> findByName(String name);
	
	void save(Template template);

}
