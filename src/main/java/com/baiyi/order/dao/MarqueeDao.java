package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.util.PageBean;


public interface MarqueeDao {
	
	void saveMarquee(Marquee marquee);
	
	Marquee findById(int id);
	
	void deleteById(int id);
	
	void updateMarquee(Marquee marquee);
	
	List<Marquee> searchMarquee(String title,String content);
	
	List<Marquee> searchMarqueeByPage(String title,String content,int adminId,PageBean pageBean,String sortType,List<Integer> idList);
	
}
