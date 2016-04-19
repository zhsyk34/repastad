package com.baiyi.order.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.baiyi.order.pojo.DownRecord;
import com.baiyi.order.pojo.Food;
import com.baiyi.order.pojo.FoodType;
import com.baiyi.order.pojo.Marquee;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.Taste;
import com.baiyi.order.pojo.Template;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.program.UserDownBean;
import com.baiyi.order.service.DownRecordService;
import com.baiyi.order.service.FoodService;
import com.baiyi.order.service.FoodTypeService;
import com.baiyi.order.service.MarqueeService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.TasteService;
import com.baiyi.order.service.TemplateService;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.Utility;
import com.baiyi.order.util.ValidateUtil;

public class DownRecordAction extends BasicAction {

	private DownRecordService downRecordService;

	private TemplateService templateService;

	private MaterialService materialService;

	private FoodService foodService;

	private TerminalService terminalService;

	private int id;// ID

	private String terminalId;// 終端編號

	private int templateId;// 節目id

	private Long downSize;

	private Long totalSize;

	private String templateIdList;

	private FoodTypeService foodTypeService;

	private MarqueeService marqueeService;

	private TasteService tasteService;

	@Resource
	public void setTerminalService(TerminalService terminalService) {
		this.terminalService = terminalService;
	}

	public Long getDownSize() {
		return downSize;
	}

	public int getId() {
		return id;
	}

	public int getNumber(String num) {
		if (num.equals("four")) {
			return 4;
		} else if (num.equals("eight")) {
			return 8;
		} else if (num.equals("twelve")) {
			return 12;
		} else {
			return 16;
		}
	}

	public int getTemplateId() {
		return templateId;
	}

	public String getTemplateIdList() {
		return templateIdList;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public Long getTotalSize() {
		return totalSize;
	}

	public void setDownRecordService(DownRecordService downRecordService) {
		this.downRecordService = downRecordService;
	}

	public void setDownSize(Long downSize) {
		this.downSize = downSize;
	}

	public void setFoodService(FoodService foodService) {
		this.foodService = foodService;
	}

	public void setFoodTypeService(FoodTypeService foodTypeService) {
		this.foodTypeService = foodTypeService;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMarqueeService(MarqueeService marqueeService) {
		this.marqueeService = marqueeService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public void setTasteService(TasteService tasteService) {
		this.tasteService = tasteService;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setTemplateIdList(String templateIdList) {
		this.templateIdList = templateIdList;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}

	// 等待下載 (增加记录)
	@SuppressWarnings("unchecked")
	public String saveDownRecord() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			if (terminalId != null) {
				String[] terminalIdArray = terminalId.split(";");
				for (String tid : terminalIdArray) {
					downRecordService.saveDownRecord(id, templateId, tid);// id是無用的
				}
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("write.error"));
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction saveDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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

			Log4JFactory.instance().error("DownRecordAction saveDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 取消下載(删除记录)
	@SuppressWarnings("unchecked")
	public String cancleDownRecord() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			DownRecord d = downRecordService.findByIsDelete(templateId, terminalId, null);
			if (d != null && d.getIsDown() == DownRecord.UNDOWN) {
				if (BeanUtil.downMap.get(terminalId + "-" + templateId) != null) {
					BeanUtil.downMap.get(terminalId + "-" + templateId).setCancle(true);
				}
				downRecordService.deleteById(d.getId());
				resultObj.put("success", true);
				System.out.println("取消下載" + terminalId + "-" + templateId);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("write.error"));
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction saveDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
			Log4JFactory.instance().error("DownRecordAction saveDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 刪除（修改isdelete）
	@SuppressWarnings("unchecked")
	public String deletetTerminalTemplate() {
		JSONObject resultObj = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			templateIdList = (String) Utility.checkParmeter(templateIdList);
			if (templateIdList != null) {
				String[] templateArray = templateIdList.split(",");
				for (String id : templateArray) {
					DownRecord downRecord = this.downRecordService.findByIsDelete(Integer.valueOf(id), terminalId, DownRecord.UNDELETE);
					if (downRecord != null) {
						downRecord.setIsDelete(DownRecord.ISDELETE);
						downRecordService.updateDownRecord(downRecord);
					}
				}
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 刪除（終端獲取）
	@SuppressWarnings("unchecked")
	public String getDeleteDownRecord() {
		JSONObject resultObj = new JSONObject();
		try {
			// 找需要刪除的記錄
			terminalId = (String) Utility.checkParmeter(terminalId);
			List<DownRecord> list = downRecordService.findBySearch(null, terminalId, DownRecord.ISDELETE, null);
			if (list != null && list.size() > 0) {
				JSONArray array = new JSONArray();
				for (DownRecord d : list) {
					array.add(d.getTemplateId());
					downRecordService.deleteById(d.getId());
				}
				resultObj.put("success", true);
				resultObj.put("deleteArray", array);
			} else {
				resultObj.put("success", false);
				resultObj.put("errorMsg", this.getText("delete.error"));
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 下载完成后修改Server端ProgramNote状态为已下载 1(修改下载状态)
	public String updatePorgramNoteState() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			if (terminalId != null) {
				// 更新終端下載節目完成標記
				DownRecord downRecord = this.downRecordService.findByIsDelete(templateId, terminalId, DownRecord.UNDELETE);
				downRecord.setIsDown(downRecord.ISDOWN);
				BeanUtil.downMap.remove(terminalId + "-" + templateId);
				this.downRecordService.updateDownRecord(downRecord);
				object.put("success", true);
			} else {
				object.put("success", false);
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("ProgramNoteAction updatePorgramNoteState ERROR", e);
		}
		try {
			response.setContentType("text/xml; charset=UTF-8");
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
			logger.error("ProgramNoteAction updatePorgramNoteState stream ERROR", e);
		}
		return SUCCESS;
	}

	// 獲取終端已下載模板
	@SuppressWarnings("unchecked")
	public String findTerminalDownTemplate() {
		JSONObject resultObj = new JSONObject();
		try {
			// 找需要下載的記錄
			terminalId = (String) Utility.checkParmeter(terminalId);
			List<DownRecord> list = downRecordService.findBySearch(null, terminalId, DownRecord.UNDELETE, null);
			if (list != null && list.size() > 0) {
				Template t = null;
				// 查找第一個存在的模板
				JSONArray array = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					t = templateService.findById(list.get(i).getTemplateId());
					if (t != null) {
						JSONObject obj = new JSONObject();
						obj.put("id", t.getId());
						obj.put("name", t.getName());
						obj.put("isdown", list.get(i).getIsDown());
						array.add(obj);
					}
				}
				resultObj.put("templateArray", array);
				resultObj.put("success", true);
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 下載模板
	@SuppressWarnings("unchecked")
	public String getTerminalDownTemplate() {
		JSONObject resultObj = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 找需要下載的記錄
			terminalId = (String) Utility.checkParmeter(terminalId);
			Terminal terminal = terminalService.findTerminalId(terminalId);// 終端
			if (terminal == null) {
				return null;
			}
			Integer terminalKey = terminal.getId();
			List<DownRecord> list = downRecordService.findBySearch(null, terminalId, DownRecord.UNDELETE, DownRecord.UNDOWN);
			if (list != null && list.size() > 0) {
				Template t = null;
				// 查找第一個存在的模板
				for (int i = 0; i < list.size(); i++) {
					t = templateService.findById(list.get(i).getTemplateId());
					if (t != null) {// 判斷是否有模板
						break;
					}

				}
				if (t != null) {

					// 模板
					resultObj.put("id", t.getId());
					resultObj.put("titleLogo", "default");
					resultObj.put("type", t.getType());
					resultObj.put("size", t.getSize());

					if (t.getTitleImg() != 0) {
						Material m = materialService.findById(t.getTitleImg());
						if (m != null) {
							// 餐点Logo
							resultObj.put("titleLogo", m.getPath().replace("\\", "/"));
						}
					}
					resultObj.put("banner", "default");
					if (t.getBanner() != null && t.getBanner().length() > 0) {
						Material m = materialService.findById(Integer.valueOf(t.getBanner()));
						if (m != null) {
							// 頂部圖片
							resultObj.put("banner", m.getPath().replace("\\", "/"));
						}
					}
					// 跑馬燈begin
					if (t.getSelectPart() != null && t.getSelectPart().contains("isMarquee")) {
						resultObj.put("isMarquee", true);
						resultObj.put("marquee", null);
						if (t.getMarquee() != null && t.getMarquee().length() != 0) {
							String[] marqueeIdStr = t.getMarquee().split(",");
							JSONObject marquee = new JSONObject();
							String marqueeStr = "";
							boolean isSet = false;
							for (int i = 0; i < marqueeIdStr.length; i++) {
								String marqueeId = marqueeIdStr[i];
								if (marqueeId.trim().length() != 0) {
									Integer id = Integer.valueOf(marqueeId);
									Marquee m = marqueeService.findById(id);
									if (m != null) {
										if (!isSet) {
											marquee.put("size", m.getSize().replace("px", ""));
											marquee.put("speed", m.getSpeed());
											marquee.put("direction", m.getDirection());
											marquee.put("color", m.getColor());
											marquee.put("font", m.getFontfamily());
											marquee.put("background", m.getBackground());
											isSet = true;
										}
										marqueeStr += m.getM_content() + "    ";
									}
								}
							}
							marquee.put("content", marqueeStr);
							resultObj.put("marquee", marquee);
						}
					} else {
						resultObj.put("isMarquee", false);
					}
					if (t.getSelectPart() != null && t.getSelectPart().contains("videoCtrl")) {
						resultObj.put("showCtrl", "videoCtrl");
						resultObj.put("video", null);
						if (t.getVideo() != null && t.getVideo().length() > 0) {
							JSONArray videoArray = new JSONArray();
							String[] videoIds = t.getVideo().split(",");
							for (String videoId : videoIds) {
								if (videoId.trim().length() != 0) {
									Material m = materialService.findById(Integer.valueOf(videoId));
									if (m != null) {
										videoArray.add(m.getPath().replace("\\", "/"));
									}
								}
							}
							resultObj.put("video", videoArray);
						}
					} else if (t.getSelectPart() != null && t.getSelectPart().contains("pictureCtrl")) {
						resultObj.put("showCtrl", "pictureCtrl");
						resultObj.put("picture", null);
						if (t.getPicture() != null && t.getPicture().length() > 0) {
							JSONArray pictureArray = new JSONArray();
							String[] pictureIds = t.getPicture().split(",");
							for (String pictureId : pictureIds) {
								if (pictureId.trim().length() != 0) {
									Material m = materialService.findById(Integer.valueOf(pictureId));
									if (m != null) {
										pictureArray.add(m.getPath().replace("\\", "/"));
									}
								}
							}
							resultObj.put("picture", pictureArray);
						}
						resultObj.put("picTime", t.getPicTime() == null ? "5" : t.getPicTime());
						resultObj.put("effect", t.getEffect() == null ? "Random" : t.getEffect());
					} else {
						resultObj.put("showCtrl", "numberCtrl");
					}
					// 跑馬燈end
					// 餐點:食物 口味 類型
					JSONArray cakeArray = new JSONArray();
					JSONArray typeArray = new JSONArray();
					JSONArray tasteArray = new JSONArray();
					// 過濾 統計 重複數據
					Map<Integer, Integer> typeMap = new LinkedHashMap<Integer, Integer>();
					Map<Integer, Integer> tasteMap = new HashMap<Integer, Integer>();
					// 遍歷模板中的餐點
					String[] cakeIds = t.getCakeId().split(",");
					for (String cakeId : cakeIds) {
						if (cakeId.trim().length() != 0) {
							Integer id = Integer.valueOf(cakeId);
							Food food = foodService.findById(id);

							if (food != null) {
								Integer foodId = food.getId();
								JSONObject cake = new JSONObject();
								cake.put("id", food.getId());
								cake.put("name", food.getName());
								String shortname = food.getShortname();
								if (shortname != null && food.getShortname().trim().length() == 0) {
									shortname = null;
								}
								cake.put("shortname", shortname);
								cake.put("alias", food.getAlias());
								cake.put("necessary", food.getNecessary());
								cake.put("price", food.getPrice());
								cake.put("introduce", food.getIntroduce() == null ? "" : food.getIntroduce());

								// 圖片
								Material m = materialService.findById(food.getMaterialId());
								if (m != null) {
									cake.put("image", m.getPath().replace("\\", "/"));
								}
								// 類型
								Integer typeId = food.getType();
								cake.put("type", typeId);
								// 統計
								if (typeMap.containsKey(typeId)) {
									typeMap.put(typeId, typeMap.get(typeId) + 1);
								} else {
									typeMap.put(typeId, 1);
								}

								// 口味數組
								JSONArray cakeTasteArray = new JSONArray();

								String tasteIds = food.getTaste();
								if (ValidateUtil.isStrictNotEmpty(tasteIds)) {
									String[] tasteIdArray = tasteIds.split(",");
									for (int i = 0; i < tasteIdArray.length; i++) {
										if (tasteIdArray[i].trim().length() > 0) {
											int tasteId = Integer.parseInt(tasteIdArray[i].trim());
											cakeTasteArray.add(tasteId);
											// 統計
											// 已記錄
											if (tasteMap.containsKey(tasteId)) {
												tasteMap.put(tasteId, tasteMap.get(tasteId) + 1);
											} else {
												// 未記錄
												tasteMap.put(tasteId, 1);
												JSONObject tasteJSONObject = new JSONObject();
												Taste taste = tasteService.find(tasteId);
												if (taste != null) {
													tasteJSONObject.put("id", tasteId);
													tasteJSONObject.put("name", taste.getName());
													tasteJSONObject.put("type", taste.getType());
													tasteJSONObject.put("price", taste.getPrice());
													// 添加到返回數據 tasteArray
													tasteArray.add(tasteJSONObject);
												}
											}
										}
									}
								}
								cake.put("taste", cakeTasteArray);
								// 添加到返回數據 cakeArray
								cakeArray.add(cake);
							}
						}
					}

					// 循環結束統計number
					if (typeMap.size() > 0) {
						for (int tid : typeMap.keySet()) {
							FoodType ft = foodTypeService.find(tid);
							if (ft != null) {
								JSONObject type = new JSONObject();
								type.put("id", ft.getId());
								type.put("number", typeMap.get(tid));
								type.put("name", ft.getTypeName());
								typeArray.add(type);
							}
						}
					}
					// 返回數據
					resultObj.put("cakeArray", cakeArray);
					resultObj.put("typeArray", typeArray);
					resultObj.put("tasteArray", tasteArray);
					resultObj.put("success", true);
				} else {
					resultObj.put("success", false);
				}
			} else {
				resultObj.put("success", false);
			}
		} catch (Exception e) {
			resultObj.put("success", false);
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord ERROR", e);
		}
		try {
			response.setContentType("text/json; charset=UTF-8");
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
			Log4JFactory.instance().error("DownRecordAction deleteDownRecord stream ERROR", e);
		}
		return SUCCESS;
	}

	// 查找节目下载的进度
	@SuppressWarnings("unchecked")
	public String findDownProgress() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			if (terminalId != null) {
				String[] tidArray = terminalId.split(";");
				JSONArray array = new JSONArray();
				for (String tid : tidArray) {
					String fileKey = tid + "-" + templateId;
					System.out.println("fileKey:" + fileKey);
					DownRecord downRecord = this.downRecordService.findByIsDelete(templateId, tid, DownRecord.UNDELETE);
					JSONObject down = new JSONObject();
					down.put("terminalId", tid);
					down.put("templateId", templateId);
					if (downRecord != null) {
						down.put("isdown", downRecord.getIsDown() == 1 ? true : false);// 等待下和已下載的節目
						if (BeanUtil.downMap.containsKey(fileKey)) {
							UserDownBean downbean = BeanUtil.downMap.get(fileKey);
							if (downbean != null) {
								System.out.println("fileKeydown:" + fileKey + "down:" + downbean.getDownSize() + "/" + downbean.getTotalSize());
								down.put("down", downbean.getDownSize());
								down.put("total", downbean.getTotalSize());
								down.put("cancle", downbean.isCancle());
							} else {
								down.put("down", 0);// 無下載信息
							}
						} else {
							down.put("down", 0);// 無下載信息
						}
					} else {
						down.put("cancle", true);
					}
					array.add(down);
				}
				object.put("downArray", array);
				object.put("success", true);
			} else {
				object.put("success", false);
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("ProgramNoteAction findDownProgress ERROR", e);
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
			logger.error("ProgramNoteAction findDownProgress stream ERROR", e);
		}
		return SUCCESS;
	}

	/**
	 * 上傳节目下载的进度
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String uploadDownProgress() {
		JSONObject object = new JSONObject();
		try {
			terminalId = (String) Utility.checkParmeter(terminalId);
			if (downSize == null) {
				downSize = 0L;
			}
			if (terminalId != null) {
				String fileKey = terminalId + "-" + templateId;
				DownRecord downRecord = this.downRecordService.findByIsDelete(templateId, terminalId, DownRecord.UNDELETE);
				UserDownBean downbean = BeanUtil.downMap.get(fileKey);
				if (downbean == null && downRecord != null) {
					downbean = new UserDownBean();
					downbean.setTemplateId(templateId);
					downbean.setUsername(terminalId);
					BeanUtil.downMap.put(fileKey, downbean);
				}
				if (downbean != null) {
					downbean.setDownSize(downSize + downbean.getDownSize());
					if (totalSize != null) {
						downbean.setTotalSize(totalSize);
					}
					object.put("cancle", downbean.isCancle());
					System.out.println("fileKey:" + fileKey + "down:" + downbean.getDownSize() + "/" + downbean.getTotalSize());
				}
				if (downRecord == null) {
					BeanUtil.downMap.remove(fileKey);
					object.put("cancle", true);
				}
				object.put("success", true);
			} else {
				object.put("success", false);
			}
		} catch (Exception e) {
			object.put("success", false);
			logger.error("ProgramNoteAction uploadDownProgress ERROR", e);
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
			logger.error("ProgramNoteAction uploadDownProgress stream ERROR", e);
		}
		return SUCCESS;
	}

}
