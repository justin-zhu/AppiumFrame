package com.meitu.utils;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
/**
 * 工具类
 * @author p_xiaogzhu
 *2019年3月28日
 *
 */
public class JustinUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_HH_mm_ss");
	static Logger logger =Logger.getLogger(JustinUtil.class);
	public  static String getLocalTime() {		
		Long millis = System.currentTimeMillis();
		String timeNow = sdf.format(millis);
		return timeNow;
	}
	public static String getRootPath(String folderName)
	{
		String resultPath=System.getProperty("user.dir")+File.separator+"photos"+File.separator+folderName;
		File path = new File(resultPath);
		if(!path.exists())
		{
			path.mkdirs();
		}
		return path.getPath();
	}
	public static ExcelReader readExcel(String path, String sheetName){
		File file = new File(path);
		ExcelReader excelReader;
		try {
			excelReader =  ExcelUtil.getReader(file, sheetName);
			return excelReader;
		} catch (Exception e) {
			logger.info("文件读取异常，请检查"+path+"是否被其它程序占用");		
			return null;
		}		
	}
	/*
	 * 工程目录下的case文件夹
	 */
	public static String getRootPathCase(){
		File file = new File(System.getProperty("user.dir")+"/case/");
		return file.getPath();
	}
	
}
