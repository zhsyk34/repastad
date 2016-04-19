package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Material;
import com.baiyi.order.util.PageBean;


public interface MaterialDao {
	
	List<Material> findByPage(int type,String name, PageBean pageBean,String sortType,List<Integer> idList);
	
	List<Material> search(String name, int start,int length);
	
	Material findById(int id);
	
	void saveMaterial(Material m);
	
	Material findByLog(int isLogo);
	
	void updateMaterial(int id,int isLogo);
}
