package com.baiyi.order.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.Material;
import com.baiyi.order.service.AdminsService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.ConvertVedioRunnable;
import com.baiyi.order.util.ImgGenerate;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PageBean;
import com.baiyi.order.util.Utility;
import com.baiyi.order.util.ValidateUtil;
import com.baiyi.order.vo.ConvertInfo;

public class MaterialAction extends BasicAction {

	private File[] materialFile;

	private String[] materialFileFileName;

	private String[] fileName;// 表單文件名

	private MaterialService materialService;

	private int id;// 素材ID

	private int materialType;// 素材類型

	private String searchName;// 搜尋名稱

	private String name;// 素材名稱-->修改

	private int searchType;// 搜尋類型

	private int startPage;

	private int maxPage;

	private int pageSize;

	private int rowCount;

	private int adminId;

	private int isLog;

	private String message;

	private AdminsService adminsService;

	private String idList;

	private String searchKey;

	private String sortType;

	private boolean select = false;

	private JSONArray jsonArray;

	/**
	 * 删除素材文件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteMaterial() {
		JSONObject resultObj = new JSONObject();
		try {
			if (id > 0) {
				String webPath = servletContext.getRealPath("/");
				this.materialService.deleteMaterialById(id, webPath);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("SourceMaterailAction deleteMaterial ERROR", e);
		}
		try {
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(resultObj);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("SourceMaterailAction deleteMaterial stream ERROR", e);
		}
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String deleteMaterialByIdList() {
		JSONObject object = new JSONObject();
		try {
			this.idList = (String) Utility.checkParmeter(this.idList);
			if (idList != null) {
				String[] idArray = this.idList.split(",");
				adminId = 1;
				for (int i = 0; i < idArray.length; i++) {
					Integer id = new Integer(idArray[i]);
					Material material = this.materialService.findById(id);
					if (material != null) {
						String webPath = servletContext.getRealPath("/");
						this.materialService.deleteMaterialById(id, webPath);
					}
				}
				object.put("success", true);
			}
		} catch (Exception e) {
			object.put("success", false);
			e.printStackTrace();
			Log4JFactory.instance().error("SourceMaterailAction deleteByIdList ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache", "no-cache");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(object);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			Log4JFactory.instance().error("SourceMaterailAction deleteMaterial stream ERROR", e);
		}
		return SUCCESS;
	}

	public int getAdminId() {
		return adminId;
	}

	public AdminsService getAdminsService() {
		return adminsService;
	}

	public String[] getFileName() {
		return fileName;
	}

	public int getId() {
		return id;
	}

	public String getIdList() {
		return idList;
	}

	public int getIsLog() {
		return isLog;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public File[] getMaterialFile() {
		return materialFile;
	}

	public String[] getMaterialFileFileName() {
		return materialFileFileName;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public int getMaterialType() {
		return materialType;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public String getMessage() {
		return message;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public String getSearchName() {
		return searchName;
	}

	public int getSearchType() {
		return searchType;
	}

	public String getSortType() {
		return sortType;
	}

	public int getStartPage() {
		return startPage;
	}

	public boolean isSelect() {
		return select;
	}

	/**
	 * 查询素材 searchKey,sort=id,sortType=desc,idList
	 */
	public String searchMaterial() {
		try {
			this.searchName = (String) Utility.checkParmeter(this.searchName);
			if (this.pageSize == 0) {
				this.pageSize = admins.getPageCount();
			} else if (this.pageSize != 0 && this.pageSize != admins.getPageCount()) {
				admins.setPageCount(pageSize);
				this.adminsService.updatePageCount(admins.getId(), pageSize);
				session.put("user", admins);
			}
			PageBean pageBean = new PageBean(pageSize);
			if (startPage > 0) {
				pageBean.setStartPage(startPage);
			} else {
				pageBean.setStartPage(1);
			}
			sortType = (String) Utility.checkParmeter(sortType);
			if (sortType == null) {
				sortType = "desc";
			}
			List<Material> materialList = this.materialService.findMaterialByPage(this.searchType, this.searchName, pageBean, sortType, null);
			for (Material m : materialList) {
				String path = m.getPath();
				if (m.getType() == 2) {
					path = path.replace("flv", "jpg");
				}
				m.setPath(path.replace("\\", "/"));
			}
			request.setAttribute("materialList", materialList);
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("forwardPage", "material");
			if (this.message != null) {
				this.message = URLDecoder.decode(this.message, "UTF-8");
			}
			if (select) {
				return "select";
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", this.getText("search.error"));
			Log4JFactory.instance().error("SourceMaterailAction searchMaterialByManage ERROR", e);
		}
		return INPUT;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public void setAdminsService(AdminsService adminsService) {
		this.adminsService = adminsService;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public void setIsLog(int isLog) {
		this.isLog = isLog;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public void setMaterialFile(File[] materialFile) {
		this.materialFile = materialFile;
	}

	public void setMaterialFileFileName(String[] materialFileFileName) {
		this.materialFileFileName = materialFileFileName;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public void setMaterialType(int materialType) {
		this.materialType = materialType;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	/**
	 * 上传素材文件
	 * 
	 * @return
	 */
	public String uploadMaterial() throws Exception {
		int saveResult = -1;
		StringBuilder resultMsg = new StringBuilder();
		StringBuilder uploadMsg = new StringBuilder();
		String webPath = servletContext.getRealPath("/");
		if ((this.fileName == null || this.materialFile == null)) {
			request.setAttribute("message", this.getText("material.add.infoRequired"));
			this.message = URLEncoder.encode(this.getText("material.add.uploadError"), "UTF-8");
			return INPUT;
		}
		if (materialFile != null && materialFile.length > 0) {
			// Tomcat绝对地址
			String destPath = servletContext.getRealPath("/");
			// uri路徑
			StringBuilder relaPath = new StringBuilder();
			// 是否要转换
			boolean isConvert = false;
			// 是否直接生成图片
			boolean isGeneratePic = false;
			try {
				for (int materialIndex = 0; materialIndex < materialFile.length; materialIndex++) {
					isConvert = false;
					isGeneratePic = false;
					if (fileName[materialIndex].trim().length() == 0 || materialFile[materialIndex] == null) {
						continue;
					}
					int fileType = Utility.checkType(materialFileFileName[materialIndex]);
					// 文件类型判断
					switch (fileType) {
					case 1:
						// 图片
						relaPath.append("images").append(File.separator);
						break;
					case 2:
						// 视频
						relaPath.append("vedios").append(File.separator);
						if (materialFileFileName[materialIndex].indexOf("flv") != -1) {
							isConvert = false;
							isGeneratePic = true;
						} else {
							isConvert = true;
						}
						break;
					case 3:
						// 音頻
						relaPath.append("music").append(File.separator);
						break;
					case 0:
						// 不正确的文件类型不上传
						resultMsg.append(materialFileFileName[materialIndex] + this.getText("material.add.fileTypeError"));
						continue;
					default:
						break;
					}
					relaPath.append(Utility.getStrDate(new Date()) + UUID.randomUUID().toString());
					// 复制文件并重命名上傳的文件
					String filesName = materialFileFileName[materialIndex].replace(" ", "");
					String extType = filesName.substring(filesName.lastIndexOf("."));
					File renamedFile = new File(destPath + relaPath + extType.toLowerCase());
					FileUtils.copyFile(materialFile[materialIndex], renamedFile);
					// 保存成功返回materialId
					int adminId = admins.getId();
					saveResult = materialService.saveMaterial(fileType, fileName[materialIndex], relaPath.append(extType).toString(), adminId);
					System.out.println("保存素材路徑：" + relaPath.toString());
					// 加入转换视频列表
					if (isConvert && saveResult > 0) {
						System.out.println("加入视频转换列表");
						ConvertInfo info = new ConvertInfo(saveResult, renamedFile.getAbsolutePath());
						BeanUtil.linkedQueue.add(info);
						// 以單線程方法啟動視頻轉換，而不是用原來的等待一段時間后轉換
						ConvertVedioRunnable convert = new ConvertVedioRunnable(BeanUtil.linkedQueue);
						convert.run();
					} else if (isGeneratePic) {
						// 如果是flv直接截取图片
						Thread ImgGenerateThread = new Thread(new ImgGenerate(renamedFile.getAbsolutePath()));
						ImgGenerateThread.start();
						ImgGenerateThread.join();
					}
					uploadMsg.append(materialFileFileName[materialIndex] + this.getText("material.add.uploadSuccess"));
					relaPath.setLength(0);
				}
			} catch (IOException ioe) {
				request.setAttribute("message", this.getText("material.add.uploadError"));
				this.message = URLEncoder.encode(this.getText("material.add.uploadError"), "UTF-8");
				Log4JFactory.instance().error("SourceMaterailAction uploadMaterial IOERROR", ioe);
				return INPUT;
			} catch (Exception e) {
				if (resultMsg.length() != 0) {
					request.setAttribute("errorMsg", resultMsg);
				}
				request.setAttribute("successMsg", uploadMsg.toString());
				request.setAttribute("message", this.getText("material.add.uploadError"));
				this.message = URLEncoder.encode(this.getText("material.add.uploadError"), "UTF-8");
				// 刪除素材
				this.materialService.deleteMaterialById(saveResult, webPath);
				Log4JFactory.instance().error("SourceMaterailAction uploadMaterial ERROR", e);
				return INPUT;
			}
		}
		if (saveResult > 0 && resultMsg.length() == 0) {
			request.setAttribute("successMsg", uploadMsg.toString());
			request.setAttribute("message", this.getText("material.add.uploadSuccess"));
			this.message = URLEncoder.encode(this.getText("material.add.uploadSuccess"), "UTF-8");
			return SUCCESS;
		} else {
			if (resultMsg.length() != 0) {
				request.setAttribute("errorMsg", resultMsg);
			}
			request.setAttribute("successMsg", uploadMsg.toString());
			request.setAttribute("message", this.getText("material.add.uploadError"));
			this.message = URLEncoder.encode(this.getText("material.add.uploadError"), "UTF-8");
			return INPUT;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String mod() {
		Material m = materialService.findById(id);
		if (m != null && ValidateUtil.isNotEmpty(name)) {
			m.setName(name);
			materialService.save(m);
		}
		return SUCCESS;
	}
}
