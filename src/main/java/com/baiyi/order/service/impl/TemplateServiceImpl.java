package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DownRecordDao;
import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.TemplateDao;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.service.TemplateService;
import com.baiyi.order.util.PageBean;
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

	@Resource
	private TemplateDao templateDao;
	@Resource
	private GenericDao genericDao;
	@Resource
	private DownRecordDao downRecordDao;

	public List<Template> findByPageBean(String name, PageBean pageBean) {
		return this.templateDao.findByPage(name, pageBean);
	}

	public Template findById(int id) {
		return this.templateDao.findById(id);
	}

	public void saveTemplate(int id, int row, int column, String name, String type, String size, String banner, String marquee, String cakeId, int titleImg, String selectPart, String video, String picture, String picTime, String effect, int adminId) {
		Template t = new Template();
		t.setAddtime(new Date());
		t.setAdminId(adminId);
		if (id > 0) {
			t = templateDao.findById(id);
		}
		t.setName(name);
		t.setBanner(banner);
		t.setMarquee(marquee);
		t.setCakeId(cakeId);
		t.setTitleImg(titleImg);
		t.setSelectPart(selectPart);
		t.setVideo(video);
		t.setPicture(picture);
		t.setPicTime(picTime);
		t.setEffect(effect);
		t.setType(type);
		t.setSize(size);
		t.setRow(row);
		t.setCol(column);
		genericDao.saveOrUpdate(t);
		if (id > 0) {
			List<DownRecord> list = downRecordDao.findBySearch(id, null, DownRecord.UNDELETE, DownRecord.ISDOWN);
			for (DownRecord d : list) {
				d.setIsDown(DownRecord.UNDOWN);
				genericDao.update(d);
			}
		}
	}

	public void deleteById(int id) {
		this.genericDao.deleteByParam("delete from Template as a where a.id=" + id);
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public void setDownRecordDao(DownRecordDao downRecordDao) {
		this.downRecordDao = downRecordDao;
	}

	public List<Template> findByFoodId(String column, int id) {
		return templateDao.findByFoodId(column, id);
	}

	public List<Template> findByName(String name) {
		return templateDao.findByName(name);
	}

	public void saveTemplate(Template template) {
		templateDao.save(template);
	}

}
