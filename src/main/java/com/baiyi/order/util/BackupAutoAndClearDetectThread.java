package com.baiyi.order.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.baiyi.order.service.IDetectrecordsService;

/**
 * 自動備份 and 刪除保存記錄天數外的連線記錄
 * 
 * @author Administrator
 * 
 */
public class BackupAutoAndClearDetectThread extends Thread {

	// BackupRecordService backupRecordService;
	IDetectrecordsService iDetectrecordsService;

	String path;

	int detectholdday;

	public BackupAutoAndClearDetectThread() {
		iDetectrecordsService = (IDetectrecordsService) BeanUtil.ctx.getBean("iDetectrecordsService");
		path = BeanUtil.path + "WEB-INF" + File.separator + "classes" + File.separator + "init.properties";
		detectholdday = 7;

		/*
		 * backupRecordService = (BackupRecordServiceImpl)
		 * BeanUtil.ctx.getBean("backupRecordService"); String backupAuto =
		 * PropertiesReadAndWrite.readValue(path, "backupAuto"); String
		 * backupAutoUrl = PropertiesReadAndWrite.readValue(path,
		 * "backupAutoUrl"); String backupAutoTime =
		 * PropertiesReadAndWrite.readValue(path, "backupAutoTime"); String
		 * backupAutoNumber = PropertiesReadAndWrite.readValue(path,
		 * "backupAutoNumber"); if(backupAuto!=null){
		 * if(backupAuto.equals("yes")){ BeanUtil.backupAuto = true; }else {
		 * BeanUtil.backupAuto = false; } } if(backupAutoUrl!=null &&
		 * backupAutoUrl.length()>0){ BeanUtil.backupAutoUrl = backupAutoUrl; }
		 * if(backupAutoTime!=null && backupAutoTime.length()>0){
		 * BeanUtil.backupAutoTime = Integer.valueOf(backupAutoTime); }
		 * if(backupAutoNumber!=null && backupAutoNumber.length()>0){
		 * BeanUtil.backupAutoNumber = Integer.valueOf(backupAutoNumber); }
		 */
	}

	public void run() {
		while (true) {
			// 刪除保存記錄天數外的連線記錄
			detectClear();
			// 自動備份
			// backupAuto();
			try {
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void detectClear() {
		try {
			Calendar cal = Calendar.getInstance();
			String detectholddayString = PropertiesReadAndWrite.readValue(path, "detectholdday");
			if (detectholddayString != null && detectholddayString.trim().length() != 0) {
				detectholdday = Integer.parseInt(detectholddayString.trim());
			}
			System.out.println("保存記錄天數  " + detectholdday);
			cal.add(Calendar.DATE, -detectholdday);
			iDetectrecordsService.deleteRecord(cal.getTime());
		} catch (Exception e) {
		}
	}

	/*
	 * private void backupAuto(){ try{
	 * System.out.println("autobackup:"+BeanUtil.backupAuto+"\n"+BeanUtil.backupAutoTime+"
	 * day backup one time\n reserve "+BeanUtil.backupAutoNumber+"
	 * share\nlocation："+BeanUtil.backupAutoUrl); if(BeanUtil.backupAuto &&
	 * BeanUtil.backupAutoTime!=0 && BeanUtil.backupAutoNumber!=0 &&
	 * BeanUtil.backupAutoUrl!=null && BeanUtil.backupAutoUrl.length()>0){
	 * DateFormat df = new SimpleDateFormat("yyyyMMdd"); Calendar cal =
	 * Calendar.getInstance(); cal.add(Calendar.DAY_OF_MONTH, 1); String end =
	 * df.format(cal.getTime()); cal.add(Calendar.DAY_OF_MONTH,
	 * -BeanUtil.backupAutoTime); String begin = df.format(cal.getTime()); List<BackupRecord>
	 * list = backupRecordService.findByBackupAuto(df.parse(begin),
	 * df.parse(end)); if(list==null || list.size()==0){ String today =
	 * df.format(new Date()); //開始備份 boolean isbackup =
	 * this.backupRecordService.saveBackupRecord(0,BeanUtil.backupAutoUrl +
	 * today + "/"); if(isbackup){ System.out.println("backup success--"+today); } }
	 * File backupLocation = new File(BeanUtil.backupAutoUrl);
	 * if(backupLocation.exists()){ List<Date> lf = new ArrayList<Date>();
	 * for(File cFile:backupLocation.listFiles()){ if(cFile.isDirectory()){ try{
	 * lf.add(df.parse(cFile.getName())); }catch (Exception e) { } } } //刪除多餘備份
	 * if(!lf.isEmpty() && lf.size()>BeanUtil.backupAutoNumber){
	 * Collections.sort(lf, new Comparator<Date>(){ public int compare(Date d1,
	 * Date d2) { if(d1.after(d2)){ return 1; }else if (d1.equals(d2)){ return
	 * 0; }else { return -1; } } }); String delDir = df.format(lf.get(0));
	 * this.backupRecordService.delBackupData(BeanUtil.backupAutoUrl + delDir +
	 * File.separator); }
	 *  } } }catch (Exception e) { e.printStackTrace(); } }
	 */

	public static void main(String[] args) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		List<Date> lf = new ArrayList<Date>();
		lf.add(df.parse("20130705"));
		lf.add(df.parse("20130630"));
		lf.add(df.parse("20130731"));
		lf.add(df.parse("20130615"));
		if (!lf.isEmpty() && lf.size() > 0) {
			Collections.sort(lf, new Comparator<Date>() {
				public int compare(Date d1, Date d2) {
					if (d1.after(d2)) {
						return 1;
					} else if (d1.equals(d2)) {
						return 0;
					} else {
						return -1;
					}
				}
			});
		}
		System.out.println(lf.get(0));
	}
}
