package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.util.PageBean;

public interface TerminalService {

	List<Terminal> findByType(int type);

	List<Terminal> findByPageBean(int type, String terminalId, PageBean pageBean);

	Terminal findById(int id);

	void saveTerminal(int id, String startStr, String terminalId, String terminalIdEnd, int type, int invoice, String location, int adminid, String dinnerTable, String teamViewerId);

	void updateTerminal(Terminal t);

	void deleteById(int id);

	List<Terminal> findAll();

	Terminal findTerminalId(String terminalId);

	List<Terminal> find(int type, String terminalId);
}
