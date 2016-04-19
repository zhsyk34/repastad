package com.baiyi.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.DownRecordDao;
import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.MarqueeDao;
import com.baiyi.order.dao.TemplateDao;
import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.service.MarqueeService;
import com.baiyi.order.util.PageBean;

@Service("marqueeService")
public class MarqueeServiceImpl implements MarqueeService {

	@Resource
	private MarqueeDao marqueeDao;
	@Resource
	private GenericDao genericDao;
	@Resource
	private TemplateDao templateDao;
	@Resource
	private DownRecordDao downRecordDao;

	public void saveOrUpdate(int id, String color, String size, String title, String direction, String content, String speed, String fontFamily, int colorOrImg, String background, int isRss, int adminId) {
		Marquee marquee = null;
		if (id != 0) {
			marquee = this.marqueeDao.findById(id);
		} else {
			marquee = new Marquee();
			marquee.setAdminid(adminId);
		}
		marquee.setColor(color);
		marquee.setSize(size);
		marquee.setTitle(title);
		marquee.setM_content(content);
		marquee.setDirection(direction);
		marquee.setSpeed(speed);
		marquee.setFontfamily(fontFamily);
		marquee.setColorOrImg(colorOrImg);
		marquee.setBackground(background);
		marquee.setAddTime(new Date());
		marquee.setIsRss(isRss);
		this.marqueeDao.saveMarquee(marquee);
		// 重新下載
		if (id > 0) {
			List<Template> list = templateDao.findByFoodId("marquee", id);
			if (list != null) {
				for (Template t : list) {
					if (t.getMarquee().equals(id + "") || t.getMarquee().startsWith(id + ",") || t.getMarquee().endsWith("," + id) || t.getMarquee().contains("," + id + ",")) {
						List<DownRecord> dlist = downRecordDao.findBySearch(t.getId(), null, DownRecord.UNDELETE, DownRecord.ISDOWN);
						for (DownRecord d : dlist) {
							d.setIsDown(DownRecord.UNDOWN);
							genericDao.update(d);
						}
					}
				}
			}
		}
	}

	// public List<Program> reGenerateProgram(int marqueeId,String destPath) {
	// List<Program> list = new ArrayList<Program>();
	// if(marqueeId>0){
	// Marquee marquee = this.marqueeDao.findById(marqueeId);
	//// 修改跑馬燈后是否需要重新下載節目
	// if(marquee!=null && marquee.getM_content()!=null &&
	// marquee.getM_content().trim().length()>0){
	// //查处需要重新压缩的节目
	// List<Integer> toCompressProgramIdList =
	// this.programDao.findProgramIdByMarquee(marquee.getId());
	// for(Integer programId:toCompressProgramIdList){
	// Program program = this.programDao.findById(programId);
	// if(program!=null){
	// list.add(program);
	// }
	// }
	// }
	// }
	// return list;
	// }

	public Marquee findById(int id) {
		if (id > 0) {
			return this.marqueeDao.findById(id);
		}
		return null;
	}

	public List<Marquee> findMarquee(String title, String content) {
		return this.marqueeDao.searchMarquee(title, content);
	}

	public List<Marquee> findMarqueeByPage(String title, String content, int adminId, PageBean pageBean, String sortType, List<Integer> idList) {
		return this.marqueeDao.searchMarqueeByPage(title, content, adminId, pageBean, sortType, idList);
	}

	public int deleteByIdList(List<Integer> idList) {
		String hql = "delete from Marquee as m where m.id in(:ids)";
		return this.genericDao.deleteByIdList(hql, idList);
	}

	public void deleteMarqueeById(int id) {
		this.marqueeDao.deleteById(id);
	}

}
