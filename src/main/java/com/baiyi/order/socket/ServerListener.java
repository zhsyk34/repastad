package com.baiyi.order.socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import com.baiyi.order.pojo.Detectrecords;
import com.baiyi.order.pojo.Terminal;
import com.baiyi.order.service.IDetectrecordsService;
import com.baiyi.order.service.TerminalService;
import com.baiyi.order.util.BeanUtil;
import com.baiyi.order.util.Log4JFactory;
import com.baiyi.order.util.PropertiesReadAndWrite;

public class ServerListener extends Thread {

	private ServerSocket serverSocket;

	private ServletContext servletContext;

	private ExecutorService pool;

	public static StringBuilder stringBuilder = null;

	private final int POOL_SIZE = 10;

	// 初始化终端连线信息
	public ServerListener(ServletContext servletContext) {
		IDetectrecordsService iDetectrecordsService = (IDetectrecordsService) BeanUtil.ctx.getBean("iDetectrecordsService");
		this.servletContext = servletContext;
		stringBuilder = new StringBuilder();
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TerminalService terminalService = (TerminalService) BeanUtil.ctx.getBean("terminalService");
		List<Terminal> infoList = terminalService.findAll();
		if (infoList != null && !infoList.isEmpty()) {
			for (Terminal info : infoList) {
				Object[] tempObjects = new Object[7];// 1:terminal-id,2:location,3:time,4:status,5:inline,6:addtime
				tempObjects[0] = info.getTerminalId();
				tempObjects[1] = info.getLocation() == null ? "" : info.getLocation();
				Detectrecords record = iDetectrecordsService.findLastRecord(info.getTerminalId());// 最后登录信息
				if (record != null) {
					String time = record.getAddtime().toString();
					tempObjects[2] = time.substring(0, time.length() - 2);
				} else {
					tempObjects[2] = "";
				}
				// tempObjects[3] = BeanUtil.statusMap.get("closeorborke");
				// tempObjects[4] = BeanUtil.statusMap.get("lineout");
				tempObjects[3] = "closeorborke";
				tempObjects[4] = "lineout";
				tempObjects[5] = "";
				tempObjects[6] = sdf.format(new Date());

				InfoMessage.map.put(info.getTerminalId(), tempObjects);
				// 終端超時時間
				// BeanUtil.terminalDetectRebootTimePeriodMap.put(info.getMachineId(),
				// info.getReboottime());
				// //3g檢測時間
				// BeanUtil.detect3GTimePeriodMap.put(info.getMachineId(),
				// info.getDetect3gtime());
				// //是否啟用畫面回傳
				// BeanUtil.screenBackMap.put(info.getMachineId(),
				// info.getScreenBack());
				// if(info.getValid()==1 && info.getScreenBack()==0){
				// //沒有全部啟用畫面回傳
				// BeanUtil.ALLSCREENBACK = 0;
				// }
			}
		}
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(5199);
			// Runtime的availableProcessor()方法返回当前系统的CPU数目.
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
			System.out.println("服務器啟動...");
			while (true) {
				Socket socket = serverSocket.accept();
				String hostname = socket.getInetAddress().getHostName();
				if (stringBuilder.toString().indexOf(hostname) == -1) {
					if (!stringBuilder.toString().equals("")) {
						stringBuilder.append(",");
					}
					stringBuilder.append(hostname);
				}

				pool.execute(new Handler(socket, servletContext));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Handler implements Runnable {

	private Socket socket;

	private ServletContext servletContext;

	private ObjectInputStream ois;

	private ObjectOutputStream oos;

	private IDetectrecordsService iDetectrecordsService;

	private TerminalService terminalService;

	private boolean isStoreIp;// 連接建立后是否保存了終端的IP地址

	// private SaveLogData saveLogData;// 写信息

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Handler(Socket socket, ServletContext servletContext) {
		iDetectrecordsService = (IDetectrecordsService) BeanUtil.ctx.getBean("iDetectrecordsService");
		terminalService = (TerminalService) BeanUtil.ctx.getBean("terminalService");
		try {
			this.socket = socket;
			socket.setReceiveBufferSize(999999);
			this.servletContext = servletContext;
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("server 建立監聽失敗");
			Log4JFactory.instance().error("server 建立監聽失敗", e);
		}
	}

	public void run() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] tempObjects = new Object[7];
		String no = "";
		String station = "";
		String imagpath = "";
		String pathString = servletContext.getRealPath("/");
		String dateString = "";
		boolean checknew = false;
		while (socket != null && socket.isConnected() && !socket.isClosed()) {

			try {
				String time = PropertiesReadAndWrite.readValue(pathString + "WEB-INF" + File.separator + "classes" + File.separator + "init.properties", "detectiontime");
				if (!time.trim().equals("")) {
					socket.setSoTimeout(Integer.parseInt(time.trim()) * 1000 + 2 * 60 * 1000);
				} else {
					socket.setSoTimeout(2 * 60 * 1000);
				}
				checknew = false;

				oos.writeObject(time);
				oos.flush();
				Object info2 = ois.readObject();
				Object[] info = null;
				// 1:terminal-id,2:location,3:time,4:status,5:inline,6:addtime
				if (info2 instanceof Object[]) {
					info = (Object[]) info2;
				} else {
					// Object rebootPeriodObj =
					// BeanUtil.terminalDetectRebootTimePeriodMap.get(no);//終端網絡超時檢測時間
					// int period = -1;
					// if(rebootPeriodObj!=null){
					// period = Integer.parseInt(rebootPeriodObj.toString());
					// }
					oos.writeObject("1||" + time);// +";"+period
					oos.flush();
					info = (Object[]) ois.readObject();
					checknew = true;
				}
				if (info != null && info.length > 0) {
					// 終端IP
					String terminalIP = socket.getInetAddress().getHostAddress();
					System.out.println("server 獲取到終端信息" + info[0] + "," + info[1] + ",IP地址：" + terminalIP);
					no = (String) info[0];
					// String bootTime = null;
					// if(!isStoreIp){
					// TerminalInfo terminalInfo =
					// terminalService.findByMachine(no);
					// if(terminalInfo!=null){
					// terminalInfo.setStopip(terminalIP);
					// this.terminalService.updateTerminalInfo(terminalInfo);
					// bootTime = terminalInfo.getBoottime();
					// }
					// }

					// 地址:location(station)
					if (InfoMessage.map.get(no) != null) {
						station = (String) InfoMessage.map.get(no)[1];// location数据从terminal读取
					}
					String status = (String) info[1];
					if (status.indexOf("終端") != -1) {
						status = status.replace("終端", "");
					}
					/*
					 * 注释测试 if(status.equals("quitorerror")||status.equals(
					 * "programerror")){ status =
					 * BeanUtil.statusMap.get("systemerror"); }
					 * if(status.equals("runok")){ status =
					 * BeanUtil.statusMap.get("runok"); }else if
					 * (status.equals("picerror")){ status =
					 * BeanUtil.statusMap.get("picerror"); } String line =
					 * BeanUtil.statusMap.get("linein"); File file2 = new
					 * File(pathString +"tempimag/"+ imagpath); if
					 * (file2.exists() && file2.isFile() &&
					 * status.equals("systemerror")) { file2.delete(); }
					 */

					// 在线状态
					String line = "linein";
					File file2 = new File(pathString + "tempimag/" + imagpath);
					if (file2.exists() && file2.isFile() && status.equals("systemerror")) {
						file2.delete();
					}
					imagpath = (String) info[2];
					dateString = sdf.format(new Date());
					// 写入信息记录
					Detectrecords detectrecords = new Detectrecords();
					detectrecords.setLine(line);
					detectrecords.setNo(no);
					detectrecords.setStation(station);
					detectrecords.setStatus(status);
					detectrecords.setIp(terminalIP);
					detectrecords.setAddtime(new Timestamp(new Date().getTime()));
					iDetectrecordsService.save(detectrecords);
					tempObjects[0] = no;
					tempObjects[1] = station;
					tempObjects[2] = simpleDateFormat.format(new Date());
					tempObjects[3] = status;
					tempObjects[4] = line;
					tempObjects[5] = "tempimag/" + imagpath;
					tempObjects[6] = dateString;

					InfoMessage.map.put(no, tempObjects);
					// 校正时间
					if (BeanUtil.checkTimeMap.containsKey(no)) {
						System.out.println("發送時間給" + no);
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String datetime = sdf.format(cal.getTime());
						if (checknew)
							oos.writeObject("2||" + datetime);
						else
							oos.writeObject(datetime);
						oos.flush();
						String string = (String) ois.readObject();
						if (string.equals("changetimesuccess")) {
							System.out.println("終端號：" + no + "時間修改成功");
							BeanUtil.checkTimeMap.remove(no);
						}
					} else {
						// System.out.println("沒有時間發送");
						if (checknew)
							oos.writeObject("2||");
						else
							oos.writeObject("");
						oos.flush();
						String string = (String) ois.readObject();
						if (string.equals("changetimesuccess")) {
							System.out.println("終端號：" + no + "時間修改成功");
							BeanUtil.checkTimeMap.remove(no);
						}
					}
					// ===================重啟===================
					if (checknew) {
						if (BeanUtil.rebootMap.containsKey(no)) {
							System.out.println("發送給" + no + "重啟");
							oos.writeObject("3||" + "reboot");

							oos.flush();
							String terminalReply = (String) ois.readObject();
							if (terminalReply.equals("rebootsuccess")) {
								System.out.println("終端號：" + no + "重啟成功");
								BeanUtil.rebootMap.remove(no);
							}
						} else {
							oos.writeObject("3||");
							oos.flush();
							ois.readObject();
						}
					}
					// ===================關閉===================//TODO
					if (checknew) {
						if (BeanUtil.shutDownMap.containsKey(no)) {
							System.out.println("發送給" + no + "關閉");
							oos.writeObject("4||" + "shutdown");
							oos.flush();
							String terminalReply = (String) ois.readObject();
							if (terminalReply.equals("shutdown")) {
								System.out.println("終端號：" + no + "關閉成功");
								BeanUtil.shutDownMap.remove(no);
							}
						} else {
							oos.writeObject("4||");
							oos.flush();
							ois.readObject();
						}
					}

					// ===================啟動遠端===================
					if (checknew) {
						if (BeanUtil.bootTeamViewer.containsKey(no)) {
							System.out.println("發送給" + no + "啟動遠端");
							oos.writeObject("6||" + "bootTeamViewer");

							oos.flush();
							String terminalReply = (String) ois.readObject();
							if (terminalReply.equals("bootsuccess")) {
								System.out.println("終端號：" + no + "啟動遠端成功");
								BeanUtil.bootTeamViewer.remove(no);
							}
						} else {
							oos.writeObject("6||");
							oos.flush();
							ois.readObject();
						}
					}
					// ===================關閉遠端===================
					if (checknew) {
						if (BeanUtil.closeTeamViewer.containsKey(no)) {
							System.out.println("發送給" + no + "關閉遠端程序");
							oos.writeObject("7||" + "closeTeamViewer");

							oos.flush();
							String terminalReply = (String) ois.readObject();
							if (terminalReply.equals("closesuccess")) {
								System.out.println("終端號：" + no + "關閉遠端程序成功");
								BeanUtil.closeTeamViewer.remove(no);
							}
						} else {
							oos.writeObject("7||");
							oos.flush();
							ois.readObject();
						}
					}
				}

			} catch (Exception e) {
				try {
					e.printStackTrace();
					if (!no.equals("")) {
						String[] strings = ServerListener.stringBuilder.toString().split(",");
						/*
						 * String status =
						 * BeanUtil.statusMap.get("closeorborke"); for (String
						 * string : strings) { if
						 * (InetAddress.getByName(string.trim()).isReachable(
						 * 2000)) { status =
						 * BeanUtil.statusMap.get("closeorborke"); break; } }
						 * String line =BeanUtil.statusMap.get("lineout");
						 */
						String status = "closeorborke";
						for (String string : strings) {
							if (InetAddress.getByName(string.trim()).isReachable(2000)) {
								status = "closeorborke";
								break;
							}
						}
						// String line =BeanUtil.statusMap.get("lineout");
						String line = "lineout";
						// 写入信息记录
						Detectrecords detectrecords = new Detectrecords();
						detectrecords.setLine(line);
						detectrecords.setNo(no);
						detectrecords.setStation(station);
						detectrecords.setStatus(status);
						detectrecords.setAddtime(new Timestamp(new Date().getTime()));
						iDetectrecordsService.save(detectrecords);
						tempObjects[0] = no;
						tempObjects[1] = station;
						tempObjects[2] = simpleDateFormat.format(new Date());
						tempObjects[3] = status;
						tempObjects[4] = line;
						tempObjects[5] = "tempimag/" + imagpath;
						tempObjects[6] = dateString;
						InfoMessage.map.put(no, tempObjects);
						System.out.println(">>>>>>>>>終端機斷開<<<<<<<<<<<<<<<");
					}

				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally {
					try {
						// oos.close();
						socket.close();
						socket = null;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	// 从流中读取内容并保存
	private void readAndSave(InputStream is) throws IOException {
		String filename = getFileName(is);
		int file_len = readInteger(is);
		System.out.println("接收文件：" + filename + "，长度：" + file_len);

		readAndSave0(is, "c:/" + filename, file_len);

		System.out.println("文件保存成功（" + file_len + "字节）。");
	}

	private void readAndSave0(InputStream is, String path, int file_len) throws IOException {
		FileOutputStream os = getFileOS(path);
		readAndWrite(is, os, file_len);
		os.close();
	}

	// 边读边写，直到读取 size 个字节
	private void readAndWrite(InputStream is, FileOutputStream os, int size) throws IOException {
		byte[] buffer = new byte[4096];
		int count = 0;
		while (count < size) {
			int n = is.read(buffer);
			// 这里没有考虑 n = -1 的情况
			os.write(buffer, 0, n);
			count += n;
		}
	}

	// 读取文件名
	private String getFileName(InputStream is) throws IOException {
		int name_len = readInteger(is);
		byte[] result = new byte[name_len];
		is.read(result);
		return new String(result);
	}

	// 读取一个数字
	private int readInteger(InputStream is) throws IOException {
		byte[] bytes = new byte[4];
		is.read(bytes);
		return b2i(bytes);
	}

	// 创建文件并返回输出流
	private FileOutputStream getFileOS(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}

		return new FileOutputStream(file);
	}

	public static int b2i(byte[] b) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i] & 0x000000FF) << shift;
		}
		return value;
	}

}
