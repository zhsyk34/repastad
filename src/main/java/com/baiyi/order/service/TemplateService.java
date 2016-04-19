package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Template;
import com.baiyi.order.util.PageBean;

public interface TemplateService {

	List<Template> findByPageBean(String name, PageBean pageBean);

	Template findById(int id);

	void saveTemplate(int id, int row, int column, String name, String type, String size, String banner, String marquee, String cakeId, int titleImg, String selectPart, String video, String picture, String picTime, String effect, int adminid);

	void saveTemplate(Template template);

	void deleteById(int id);

	List<Template> findByFoodId(String column, int id);

	List<Template> findByName(String name);

}
