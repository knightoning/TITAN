/*
 * Copyright (C) 2015-2020 yunjiweidian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.yunji.titan.manager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 文件操作工具类
 *
 * @author liuliang
 *
 */
public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	
	/**
	 * @desc 从文件中读取数据(只读取第一列)
	 *
	 * @author liuliang
	 *
	 * @param filePath
	 * @return
	 */
	public static List<String> getDataFromFile(String filePath) {
		if(StringUtils.isBlank(filePath)){
			return null;
		}
		//读取
		List<String> dataList = new ArrayList<String>();
		HSSFWorkbook wookbook = null;
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			wookbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = wookbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rows; i++) {
				HSSFRow sheetRow = sheet.getRow(i);
				if (sheetRow != null) {
					HSSFCell cell0 = sheetRow.getCell(0);
					if (null != cell0) {
						String s = new DataFormatter().formatCellValue(cell0);
						dataList.add(s);
					} else {
						//logger.warn("该列为空->" + cell0);
					}
				} else {
					logger.warn("该行为空,行号->" + i);
				}
			}
		} catch (Exception e) {
			logger.error("读取文件中的数据异常", e);
			return null;
		} finally {
			if (null != wookbook) {
				try {
					wookbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//返回
		return dataList;
	}
}