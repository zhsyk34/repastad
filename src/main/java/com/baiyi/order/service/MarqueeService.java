package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.util.PageBean;


public interface MarqueeService {

	void saveOrUpdate(int id,String color,String size,String title,String direction,String content,String speed,String fontFamily,int colorOrImg,String background,int isRss,int adminId);
	
	void deleteMarqueeById(int id);
	
	Marquee findById(int id);
	
	List<Marquee> findMarquee(String title,String content);
	
	List<Marquee> findMarqueeByPage(String title,String content,int adminId,PageBean pageBean,String sortType,List<Integer> idList);
	
	int deleteByIdList(List<Integer> idList);
	
	//List<Program> reGenerateProgram(int marqueeId,String destPath);

}
