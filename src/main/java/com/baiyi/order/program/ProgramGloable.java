package com.baiyi.order.program;

import java.util.List;

import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.util.BeanUtil;

public class ProgramGloable {

	// 终端用户列表
	// public static List<TerminalInfoBean> userList =
	// Collections.synchronizedList(new ArrayList<TerminalInfoBean>());

	// 在綫終端
	public static final int ISCOMPRESS = 1;// 节目压缩完成

	public static final int UNCOMPRESS = 0; // 节目尚未压缩

	public static final int ISDOWN = 1;// 终端用户已下载

	public static final int UNDOWN = 0;// 终端用户未下载

	public static final int ISUPDATE = 1;// 正在更新

	public static final int UNUPDATE = 0; // 未更新

	public static final int ISTERMINAL = 1;// 是终端用户

	public static final int NOTTERMINAL = 0;// server

	public static final int ISDELETE2 = 2;// 終端刪除，服務端可刪除

	public static final int ISDELETE = 1;// 終端已删除，服務端插播停止播放狀態

	public static final int UNDELETE = 0;// 未删除

	public static final int ISINSERT = 1;// 是插播排程

	public static final int NOTINSERT = 0;// 不是插播排程

	public static final int NOPARAM = -1;// 不是用这个参数

	public static final String NULLPARAM = null;

	public static final int ENABLE = 1;// 啟用參數

	public static final int ISVALID = 1;// 啟用

	public static final int INVALID = 0;// 不啟用

	public static final int ISROTATION = 1;// 是節目輪播

	public static final int NOROTATION = 0;// 不是節目輪播

	public static final int SELFBUILD = 1;// 自建模板

	public static final int ISPROGRAMCHANGE = 1;// 节目已变换

	public static final int NOPROGRAMCHANGE = 0;// 节目未变换

	public static final int ISROTATIONGROUP = 1;// 是节目轮播组合

	public static final int NOROTATIONGROUP = 0;// 不是节目轮播组和

	public static String POST_URL = "";// 这个是外网的版本，有域名的IP地址

	/**
	 * 初始化獲取所有終端名稱
	 * 
	 * @return
	 */
	public static List<Terminal> getAllTerminal() {
		TerminalService ts = (TerminalService) BeanUtil.ctx.getBean("terminalService");
		List<Terminal> infoList = ts.findAll();
		return infoList;
	}

}
