package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Material;
import com.baiyi.order.util.PageBean;

public interface MaterialService {

	List<Material> findMaterialByPage(int type, String name, PageBean pageBean, String sortType, List<Integer> idList);

	Material findById(int id);

	int saveMaterial(int materialType, String materialName, String uriPath, int adminId);

	int deleteMaterialById(int id, String webPath);

	List<Material> search(String name, int start, int length);

	Material findByLog(int isLogo);

	void updateMaterial(int id, int isLogo);

	void setLogo(int id, int isLogo);

	void save(Material m);
}
