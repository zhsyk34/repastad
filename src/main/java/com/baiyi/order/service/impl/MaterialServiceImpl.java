package com.baiyi.order.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.MaterialDao;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.util.PageBean;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

	@Resource
	private MaterialDao materialDao;
	@Resource
	private GenericDao genericDao;

	// 搜索素材
	public List<Material> findMaterialByPage(int type, String name, PageBean pageBean, String sortType, List<Integer> idList) {
		return this.materialDao.findByPage(type, name, pageBean, sortType, idList);
	}

	// 查看
	public Material findById(int id) {
		return this.materialDao.findById(id);
	}

	// 保存素材
	public int saveMaterial(int materialType, String materialName, String uriPath, int adminId) {
		Material material = new Material();
		material.setType(materialType);
		material.setName(materialName);
		material.setPath(uriPath);
		material.setAdminId(adminId);
		material.setAddtime(new Date());
		materialDao.saveMaterial(material);
		return material.getId();
	}

	// 刪除素材
	public int deleteMaterialById(int id, String webPath) {
		Material m = materialDao.findById(id);
		if (m != null) {
			// 素材路徑
			this.genericDao.delete(m);
			String materialPath = webPath + m.getPath();
			File file = new File(materialPath);
			if (file.exists()) {
				file.delete();
			}
			if (materialPath.endsWith(".flv")) {
				file = new File(materialPath.replace(".flv", ".jpg"));
				if (file.exists()) {
					file.delete();
				}
			}
			return 1;
		} else {
			return 0;
		}
	}

	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public List<Material> search(String name, int start, int length) {
		return materialDao.search(name, start, length);
	}

	public Material findByLog(int isLogo) {
		return materialDao.findById(isLogo);
	}

	public void updateMaterial(int id, int isLogo) {
		materialDao.updateMaterial(id, isLogo);
	}

	public void setLogo(int id, int isLogo) {
		// -1設置isLogo=1爲0...(刪除自定義LOGO)
		if ((id == -1 || id == -2) && isLogo == 0) {
			isLogo = -id;
			Material om = materialDao.findByLog(isLogo);
			if (om != null) {
				materialDao.updateMaterial(om.getId(), 0);
			}
		}
		if (isLogo == 1 || isLogo == 2) {
			Material om = materialDao.findByLog(isLogo);
			if (om != null) {
				materialDao.updateMaterial(om.getId(), 0);
			}
			materialDao.updateMaterial(id, isLogo);
		}
	}

	public void save(Material m) {
		materialDao.saveMaterial(m);
	}
}
