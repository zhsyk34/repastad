package com.baiyi.order.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.baiyi.order.pojo.Deposit;
import com.baiyi.order.pojo.Material;
import com.baiyi.order.pojo.SystemConfig;
import com.baiyi.order.service.DepositService;
import com.baiyi.order.service.MaterialService;
import com.baiyi.order.service.SystemConfigService;
import com.baiyi.order.util.DESPlus;

@SuppressWarnings("serial")
public class SystemConfigAction extends BasicAction {

	private SystemConfigService systemConfigService;

	private MaterialService materialService;

	private DepositService depositService;// 貨幣管理

	private int loginId;// 登錄頁logoId

	private int indexId;// 首頁logoId

	private String loginUrl;

	private String indexUrl;

	private int takeId;// 取餐方式 0:不顯示;1:内用;2:外帶

	// private int unifyNo;// 統一編號 0:不顯示;1:顯示

	private int deskId;// 桌位管理 0:不顯示;1:顯示(顯示取餐方式時提供頁面配置)

	private String message;

	/* 面值修改 */
	private int[] depId;

	private int[] depMin;

	private int[] depMax;

	private String pay;

	//
	private int alltype;// 是否顯示所有類型

	private int shoporder;// 是否顯示點餐端訂單

	private int kitchenorder;// 是否顯示廚房端訂單

	private int percent;// 附加比例

	private String name;// 附加名稱

	private int used;// 是否启用附加费

	// 支付配置

	private String wx_title;

	private String wx_mchID;

	private String wx_appID;

	private String wx_key;

	private String zfb_title;

	private String zfb_partner;

	private String zfb_appid;

	private String zfb_privatekey;

	private String zfb_publickey;

	private int zfb_privatekey_status;

	private int zfb_publickey_status;

	private int wx_key_status;

	//
	private Map<String, Deposit> depositMap;

	@Resource
	public void setSystemConfigService(SystemConfigService systemConfigService) {
		this.systemConfigService = systemConfigService;
	}

	@Resource
	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	@Resource
	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	/* action */
	public String setCon() throws Exception {
		// 1.LOGO設置
		// 1.1更新到數據庫
		SystemConfig loginSyscon = systemConfigService.findByName("login");
		SystemConfig indexSyscon = systemConfigService.findByName("index");
		if (loginId > 0) {
			loginSyscon.setValue(Integer.toString(loginId));
		} else {
			loginSyscon.setValue("0");
		}
		if (indexId > 0) {
			indexSyscon.setValue(Integer.toString(indexId));
		} else {
			indexSyscon.setValue("0");
		}

		systemConfigService.mod(loginSyscon);
		systemConfigService.mod(indexSyscon);
		// 1.2更新LOGO到系統環境變量
		if (loginId > 0) {
			Material loginMaterial = materialService.findById(loginId);
			loginUrl = loginMaterial.getPath();
			if (loginUrl != null) {
				loginUrl = loginUrl.replace("\\", "/");
			}
		}
		if (indexId > 0) {
			Material indexMaterial = materialService.findById(indexId);
			indexUrl = indexMaterial.getPath();
			if (indexUrl != null) {
				indexUrl = indexUrl.replace("\\", "/");
			}
		}
		servletContext.setAttribute("loginLogo", loginUrl);
		servletContext.setAttribute("indexLogo", indexUrl);

		// 2.take取餐方式(不顯示;顯示:内用、外帶)
		SystemConfig takeSyscon = systemConfigService.findByName("take");
		String takeValue = Integer.toString(takeId);
		takeSyscon.setValue(takeValue);
		systemConfigService.mod(takeSyscon);
		servletContext.setAttribute("take", takeValue);

		// 3.unifyNo統一編號

		// 4.desk桌位
		SystemConfig deskSysocon = systemConfigService.findByName("desk");
		String deskValue = Integer.toString(deskId);
		if (takeId == 0) {
			deskValue = "0";// 取餐方式不顯示時,桌位也不顯示
		}
		deskSysocon.setValue(deskValue);
		systemConfigService.mod(deskSysocon);
		servletContext.setAttribute("desk", deskValue);

		// 5.deposit
		if (depId != null && depId.length > 0) {
			for (int i = 0; i < depId.length; i++) {
				int did = depId[i];
				int min = depMin[i];
				int max = depMax[i];
				if (did <= 0) {
					continue;
				}
				Deposit deposit = depositService.find(did);
				deposit.setMax(max);
				deposit.setMin(min);
				depositService.update(deposit);
			}
		}
		// 6.pay
		SystemConfig paySyscon = systemConfigService.findByName("pay");
		paySyscon.setValue(pay);
		systemConfigService.mod(paySyscon);

		// 7.alltype
		SystemConfig alltypeSyscon = systemConfigService.findByName("alltype");
		alltypeSyscon.setValue(Integer.toString(alltype));
		systemConfigService.mod(alltypeSyscon);
		// 8.show-order
		SystemConfig shoporderSyscon = systemConfigService.findByName("shoporder");
		shoporderSyscon.setValue(Integer.toString(shoporder));
		systemConfigService.mod(shoporderSyscon);

		SystemConfig kitchenorderSyscon = systemConfigService.findByName("kitchenorder");
		kitchenorderSyscon.setValue(Integer.toString(kitchenorder));
		systemConfigService.mod(kitchenorderSyscon);

		// 9.accessory 附加费
		SystemConfig usedSyscon = systemConfigService.findByName("accessory");
		usedSyscon.setValue(used + "");
		systemConfigService.mod(usedSyscon);

		SystemConfig nameSyscon = systemConfigService.findByName("name");
		nameSyscon.setValue(name);
		systemConfigService.mod(nameSyscon);
		SystemConfig percentSyscon = systemConfigService.findByName("percent");
		percentSyscon.setValue(percent + "");
		systemConfigService.mod(percentSyscon);

		// 10.pay config
		// wechat
		SystemConfig wx_titleConfig = systemConfigService.findByName("wx_title");
		wx_titleConfig.setValue(wx_title);
		systemConfigService.mod(wx_titleConfig);

		SystemConfig wx_mchIDConfig = systemConfigService.findByName("wx_mchID");
		wx_mchIDConfig.setValue(wx_mchID);
		systemConfigService.mod(wx_mchIDConfig);

		SystemConfig wx_appIDConfig = systemConfigService.findByName("wx_appID");
		wx_appIDConfig.setValue(wx_appID);
		systemConfigService.mod(wx_appIDConfig);

		DESPlus deplus = new DESPlus();

		if (StringUtils.isNotBlank(wx_key)) {
			SystemConfig wx_keyConfig = systemConfigService.findByName("wx_key");
			wx_keyConfig.setValue(deplus.encrypt(wx_key));
			systemConfigService.mod(wx_keyConfig);
		}

		// alipay
		SystemConfig zfb_titleConfig = systemConfigService.findByName("zfb_title");
		zfb_titleConfig.setValue(zfb_title);
		systemConfigService.mod(zfb_titleConfig);

		SystemConfig zfb_partnerConfig = systemConfigService.findByName("zfb_partner");
		zfb_partnerConfig.setValue(zfb_partner);
		systemConfigService.mod(zfb_partnerConfig);

		SystemConfig zfb_appidConfig = systemConfigService.findByName("zfb_appid");
		zfb_appidConfig.setValue(zfb_appid);
		systemConfigService.mod(zfb_appidConfig);

		if (StringUtils.isNotBlank(zfb_privatekey)) {
			SystemConfig zfb_privatekeyConfig = systemConfigService.findByName("zfb_privatekey");
			zfb_privatekeyConfig.setValue(deplus.encrypt(zfb_privatekey));
			systemConfigService.mod(zfb_privatekeyConfig);
		}
		if (StringUtils.isNotBlank(zfb_publickey)) {
			SystemConfig zfb_publickeyConfig = systemConfigService.findByName("zfb_publickey");
			zfb_publickeyConfig.setValue(deplus.encrypt(zfb_publickey));
			systemConfigService.mod(zfb_publickeyConfig);
		}

		message = "success";
		return SUCCESS;
	}

	private void init() {
		Pattern pattern = Pattern.compile("^(login|index|take|desk|alltype|shoporder|kitchenorder|percent|accessory)$");
		String[] keys = { "login", "index", "take", "desk", "pay", "wx_title", "wx_mchID", "wx_appID", "wx_key", "zfb_title", "zfb_partner", "zfb_appid", "zfb_privatekey", "zfb_publickey", "alltype", "shoporder", "kitchenorder", "accessory", "name", "percent" };
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			SystemConfig sc = systemConfigService.findByName(key);
			if (sc == null) {
				sc = new SystemConfig();
				sc.setName(key);
				if (pattern.matcher(key).matches()) {
					sc.setValue("0");
				}
				systemConfigService.save(sc);
			}
		}
	}

	public String getCon() {
		init();
		List<SystemConfig> configs = systemConfigService.findList();
		Map<String, String> configMap = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(configs)) {
			for (SystemConfig config : configs) {
				configMap.put(config.getName(), config.getValue());
			}
		}

		// 1.logo
		loginId = Integer.parseInt(configMap.get("login"));
		indexId = Integer.parseInt(configMap.get("index"));
		if (loginId > 0) {
			Material loginMaterial = materialService.findById(loginId);
			if (loginMaterial != null) {
				loginUrl = loginMaterial.getPath();
			}
			if (loginUrl != null) {
				loginUrl = loginUrl.replace("\\", "/");
			}
		}
		if (indexId > 0) {
			Material indexMaterial = materialService.findById(indexId);
			if (indexMaterial != null) {
				indexUrl = indexMaterial.getPath();
			}
			if (indexUrl != null) {
				indexUrl = indexUrl.replace("\\", "/");
			}
		}

		// 2.take
		takeId = Integer.parseInt(configMap.get("take"));

		// 4.desk
		deskId = Integer.parseInt(configMap.get("desk"));

		// 5.deposit
		List<Deposit> list = depositService.findList();
		depositMap = new HashMap<String, Deposit>();
		for (Deposit deposit : list) {
			depositMap.put(deposit.getName(), deposit);
		}
		// 6.pay
		pay = configMap.get("pay");
		// payconfig
		wx_title = configMap.get("wx_title");
		wx_mchID = configMap.get("wx_mchID");
		wx_appID = configMap.get("wx_appID");

		wx_key = configMap.get("wx_key");
		if (wx_key != null) {
			wx_key_status = 1;
		}

		/*-----alipay---*/
		zfb_title = configMap.get("zfb_title");
		zfb_partner = configMap.get("zfb_partner");
		zfb_appid = configMap.get("zfb_appid");

		zfb_privatekey = configMap.get("zfb_privatekey");
		if (zfb_privatekey != null) {
			zfb_privatekey_status = 1;
		}
		zfb_publickey = configMap.get("zfb_publickey");
		if (zfb_publickey != null) {
			zfb_publickey_status = 1;
		}

		// 7.alltype
		alltype = Integer.parseInt(configMap.get("alltype"));
		// 8.show-order
		shoporder = Integer.parseInt(configMap.get("shoporder"));
		kitchenorder = Integer.parseInt(configMap.get("kitchenorder"));

		// 9.accessory 附加费
		used = Integer.parseInt(configMap.get("accessory"));
		name = configMap.get("name");
		percent = Integer.parseInt(configMap.get("percent"));

		return SUCCESS;
	}

	/* getter and setter */
	public int[] getDepId() {
		return depId;
	}

	public void setDepId(int[] depId) {
		this.depId = depId;
	}

	public int[] getDepMax() {
		return depMax;
	}

	public void setDepMax(int[] depMax) {
		this.depMax = depMax;
	}

	public int[] getDepMin() {
		return depMin;
	}

	public void setDepMin(int[] depMin) {
		this.depMin = depMin;
	}

	public Map<String, Deposit> getDepositMap() {
		return depositMap;
	}

	public void setDepositMap(Map<String, Deposit> depositMap) {
		this.depositMap = depositMap;
	}

	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTakeId() {
		return takeId;
	}

	public void setTakeId(int takeId) {
		this.takeId = takeId;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public int getAlltype() {
		return alltype;
	}

	public void setAlltype(int alltype) {
		this.alltype = alltype;
	}

	public int getKitchenorder() {
		return kitchenorder;
	}

	public void setKitchenorder(int kitchenorder) {
		this.kitchenorder = kitchenorder;
	}

	public int getShoporder() {
		return shoporder;
	}

	public void setShoporder(int shoporder) {
		this.shoporder = shoporder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getWx_appID() {
		return wx_appID;
	}

	public void setWx_appID(String wx_appID) {
		this.wx_appID = wx_appID;
	}

	public String getWx_key() {
		return wx_key;
	}

	public void setWx_key(String wx_key) {
		this.wx_key = wx_key;
	}

	public int getWx_key_status() {
		return wx_key_status;
	}

	public void setWx_key_status(int wx_key_status) {
		this.wx_key_status = wx_key_status;
	}

	public String getWx_mchID() {
		return wx_mchID;
	}

	public void setWx_mchID(String wx_mchID) {
		this.wx_mchID = wx_mchID;
	}

	public String getZfb_appid() {
		return zfb_appid;
	}

	public void setZfb_appid(String zfb_appid) {
		this.zfb_appid = zfb_appid;
	}

	public String getZfb_partner() {
		return zfb_partner;
	}

	public void setZfb_partner(String zfb_partner) {
		this.zfb_partner = zfb_partner;
	}

	public String getZfb_privatekey() {
		return zfb_privatekey;
	}

	public void setZfb_privatekey(String zfb_privatekey) {
		this.zfb_privatekey = zfb_privatekey;
	}

	public int getZfb_privatekey_status() {
		return zfb_privatekey_status;
	}

	public void setZfb_privatekey_status(int zfb_privatekey_status) {
		this.zfb_privatekey_status = zfb_privatekey_status;
	}

	public String getZfb_publickey() {
		return zfb_publickey;
	}

	public void setZfb_publickey(String zfb_publickey) {
		this.zfb_publickey = zfb_publickey;
	}

	public int getZfb_publickey_status() {
		return zfb_publickey_status;
	}

	public void setZfb_publickey_status(int zfb_publickey_status) {
		this.zfb_publickey_status = zfb_publickey_status;
	}

	public DepositService getDepositService() {
		return depositService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public SystemConfigService getSystemConfigService() {
		return systemConfigService;
	}

	public String getWx_title() {
		return wx_title;
	}

	public void setWx_title(String wx_title) {
		this.wx_title = wx_title;
	}

	public String getZfb_title() {
		return zfb_title;
	}

	public void setZfb_title(String zfb_title) {
		this.zfb_title = zfb_title;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

}
