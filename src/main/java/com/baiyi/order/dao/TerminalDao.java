package com.baiyi.order.dao;

import java.util.List;

import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.util.PageBean;

public interface TerminalDao {

	List<Terminal> findByType(int type);

	List<Terminal> findByPage(int type, String terminalId, PageBean pageBean);

	List<Terminal> find(int type, String terminalId);

	Terminal findById(int id);

	Terminal findByTerminalId(String tid);

	List<Terminal> findAll();
}
