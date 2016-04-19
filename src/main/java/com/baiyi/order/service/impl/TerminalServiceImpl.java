package com.baiyi.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiyi.order.dao.GenericDao;
import com.baiyi.order.dao.TerminalDao;
import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.service.IDetectrecordsService;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.socket.InfoMessage;
import com.baiyi.order.util.PageBean;

@Service("terminalService")
public class TerminalServiceImpl implements TerminalService {

	@Resource
	private TerminalDao terminalDao;
	@Resource
	private GenericDao genericDao;
	@Resource
	private IDetectrecordsService iDetectrecordsService;

	public void saveTerminal(int id, String startStr, String terminalId, String terminalIdEnd, int type, int invoice, String location, int adminid, String dinnerTable, String teamViewerId) {
		Terminal t = null;
		if (id > 0) {
			t = terminalDao.findById(id);
			t.setTerminalId(terminalId);
			t.setType(type);
			t.setLocation(location);
			t.setInvoice(invoice);
			t.setDinnerTable(dinnerTable);
			t.setTeamViewerId(teamViewerId);
			genericDao.update(t);
			Object[] tempObjects = InfoMessage.map.get(t.getTerminalId());
			if (tempObjects != null) {
				tempObjects[1] = t.getLocation() == null ? "" : t.getLocation();
			}
			InfoMessage.map.put(t.getTerminalId(), tempObjects);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (terminalIdEnd == null) {
				t = terminalDao.findByTerminalId(terminalId);
				if (t == null) {
					t = new Terminal();
					t.setTerminalId(terminalId);
					t.setType(type);
					t.setLocation(location);
					t.setInvoice(invoice);
					t.setAddtime(new Date());
					t.setAdminId(adminid);
					t.setDinnerTable(dinnerTable);
					t.setTeamViewerId(teamViewerId);
					genericDao.save(t);
					Object[] tempObjects = new Object[7];
					tempObjects[0] = t.getTerminalId();
					tempObjects[1] = t.getLocation() == null ? "" : t.getLocation();
					Detectrecords record = iDetectrecordsService.findLastRecord(t.getTerminalId());
					if (record != null) {
						String time = record.getAddtime().toString();
						tempObjects[2] = time.substring(0, time.length() - 2);
					} else {
						tempObjects[2] = "";
					}
					tempObjects[3] = "closeorborke";
					tempObjects[4] = "lineout";
					tempObjects[5] = "";
					tempObjects[6] = sdf.format(new Date());
					InfoMessage.map.put(t.getTerminalId(), tempObjects);
				}
			} else {
				Integer start = Integer.valueOf(terminalId.replace(startStr, ""));
				Integer end = Integer.valueOf(terminalIdEnd.replace(startStr, ""));
				int length = terminalId.length() - startStr.length();
				for (int startIndex = start; startIndex <= end; startIndex++) {
					String tId = startStr + fillNumber(startIndex, length);
					t = terminalDao.findByTerminalId(tId);
					if (t != null) {
						continue;
					}
					t = new Terminal();
					t.setTerminalId(tId);
					t.setType(type);
					t.setLocation(location);
					t.setInvoice(invoice);
					t.setAddtime(new Date());
					t.setAdminId(adminid);
					genericDao.save(t);
					Object[] tempObjects = new Object[7];
					tempObjects[0] = t.getTerminalId();
					tempObjects[1] = t.getLocation() == null ? "" : t.getLocation();
					Detectrecords record = iDetectrecordsService.findLastRecord(t.getTerminalId());
					if (record != null) {
						String time = record.getAddtime().toString();
						tempObjects[2] = time.substring(0, time.length() - 2);
					} else {
						tempObjects[2] = "";
					}
					tempObjects[3] = "closeorborke";
					tempObjects[4] = "lineout";
					tempObjects[5] = "";
					tempObjects[6] = sdf.format(new Date());
					InfoMessage.map.put(t.getTerminalId(), tempObjects);
				}
			}
		}

	}

	public List<Terminal> findAll() {
		return this.terminalDao.findAll();
	}

	public Terminal findTerminalId(String terminalId) {
		return terminalDao.findByTerminalId(terminalId);
	}

	/**
	 * 填充終端編號
	 * 
	 * @param no
	 *            編號
	 * @param length
	 *            終端編號長度
	 * @return
	 */
	static String fillNumber(int no, int length) {
		String str = new String("" + no);
		if (str.length() < length) {
			for (int i = str.length(); i < length; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	public void updateTerminal(Terminal t) {
		genericDao.update(t);
	}

	public List<Terminal> findByPageBean(int type, String terminalId, PageBean pageBean) {
		return this.terminalDao.findByPage(type, terminalId, pageBean);
	}

	public Terminal findById(int id) {
		return this.terminalDao.findById(id);
	}

	public void deleteById(int id) {
		this.genericDao.deleteByParam("delete from Terminal as a where a.id=" + id);
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public void setTerminalDao(TerminalDao terminalDao) {
		this.terminalDao = terminalDao;
	}

	public List<Terminal> findByType(int type) {
		return terminalDao.findByType(type);
	}

	public List<Terminal> find(int type, String terminalId) {
		return terminalDao.find(type, terminalId);
	}

}
