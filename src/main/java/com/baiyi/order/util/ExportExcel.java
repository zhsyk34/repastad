package com.baiyi.order.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.baiyi.order.vo.OrderContent;
import com.baiyi.order.vo.OrderMessage;

public class ExportExcel {

	public static String decimal(double d) {
		String s = "";
		int temp = (int) d;
		if (d == temp) {
			s += temp;
		} else {
			s += d;
		}
		return s;
	}

	public static void simpleExport(String[] title, List<String[]> list, OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFCellStyle style = workbook.createCellStyle();

		// 樣式 begin
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// sheet.setMargin(HSSFSheet.BottomMargin, 0.7);
		// sheet.setMargin(HSSFSheet.LeftMargin, 0.3);
		// sheet.setMargin(HSSFSheet.RightMargin, 0.3);
		// sheet.setMargin(HSSFSheet.TopMargin, 0.7);
		// 樣式 end

		HSSFRow row = sheet.createRow(0);// 表頭
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		for (int i = 0; i < list.size(); i++) {// 數據
			row = sheet.createRow(i + 1);
			for (int j = 0; j < title.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(list.get(i)[j].toString());
				// cell.setCellStyle(style);
			}
		}
		// sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 2));//合并單元格 行行列列

		workbook.write(out);
		out.close();
		workbook.close();
	}

	public static void orderExport(String[] title, List<OrderMessage> list, OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFCellStyle style1 = workbook.createCellStyle();
		HSSFCellStyle style2 = workbook.createCellStyle();

		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// style.setWrapText(true);// 換行

		HSSFRow row = sheet.createRow(0);// 表頭
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style1);
		}

		for (int i = 0; i < list.size(); i++) {// 數據

			// sheet.autoSizeColumn(i);// 自適應寬度
			OrderMessage message = list.get(i);
			List<OrderContent> contentList = message.getContentList();

			row = sheet.createRow(i + 1);

			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style1);
			cell.setCellValue(message.getShop());

			cell = row.createCell(1);
			cell.setCellStyle(style1);
			cell.setCellValue(message.getCook());

			cell = row.createCell(2);
			cell.setCellStyle(style1);
			cell.setCellValue(message.getId());
			// content
			cell = row.createCell(3);
			cell.setCellStyle(style2);// 換行
			String contentStr = "";
			for (int j = 0; j < contentList.size(); j++) {
				OrderContent content = contentList.get(j);
				contentStr += j + 1 + "." + content.getName() + ":" + decimal(content.getPrice()) + "*" + content.getCount() + "=" + decimal(content.getMoney());
				if (j < contentList.size() - 1) {
					contentStr += "\r\n";
				}

			}
			sheet.autoSizeColumn(3);// 適應寬度
			cell.setCellValue(contentStr);
			//
			cell = row.createCell(4);
			cell.setCellStyle(style1);
			cell.setCellValue(message.getTotal());

			cell = row.createCell(5);
			cell.setCellStyle(style1);
			sheet.autoSizeColumn(5);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cell.setCellValue(sdf.format(message.getClock()));
		}

		workbook.write(out);
		out.close();
		workbook.close();
	}

	// 统计订单
	public static void orderCalExport(String[] title1, String[] title2, List<OrderMessage> list, List<OrderContent> list2, OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet");
		HSSFCellStyle style1 = workbook.createCellStyle();
		HSSFCellStyle style2 = workbook.createCellStyle();

		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFRow row = sheet.createRow(0);// 表頭1
		for (int i = 0; i < title1.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title1[i]);
			cell.setCellStyle(style1);
		}
		row = sheet.createRow(1);// 表頭2
		for (int i = 0; i < title2.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title2[i]);
			cell.setCellStyle(style1);
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 5));

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));

		// data
		int index = 2;
		for (int i = 0; i < list.size(); i++) {// 數據
			OrderMessage message = list.get(i);
			List<OrderContent> contentList = message.getContentList();
			int length = contentList.size();
			for (int j = 0; j < length; j++) {
				row = sheet.createRow(index);

				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style1);
				cell.setCellValue(message.getShop());

				cell = row.createCell(1);
				cell.setCellStyle(style1);
				cell.setCellValue(message.getCook());

				cell = row.createCell(2);
				cell.setCellStyle(style1);
				cell.setCellValue(j + 1);

				cell = row.createCell(3);
				cell.setCellStyle(style1);
				cell.setCellValue(contentList.get(j).getName());

				cell = row.createCell(4);
				cell.setCellStyle(style1);
				cell.setCellValue(contentList.get(j).getCount());

				cell = row.createCell(5);
				cell.setCellStyle(style1);
				cell.setCellValue(contentList.get(j).getMoney());

				cell = row.createCell(6);
				cell.setCellStyle(style1);
				cell.setCellValue(message.getTotal());
				if (j == length - 1) {
					//System.out.println("start:" + (index - length) + " ,end:" + index);
					sheet.addMergedRegion(new CellRangeAddress(index - j, index, 0, 0));
					sheet.addMergedRegion(new CellRangeAddress(index - j, index, 1, 1));
					sheet.addMergedRegion(new CellRangeAddress(index - j, index, 6, 6));
				}
				index++;
			}
		}
		workbook.write(out);
		out.close();
		workbook.close();
	}
}
