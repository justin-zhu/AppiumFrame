package com.meitu.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RuntimeUtil;
/**
 * Logcat日志记录类
 * @author p_xiaogzhu
 *2019年3月28日
 *
 */
public class LogcatUtil extends Thread {
	Logger logger =Logger.getLogger(this.getClass());
	private File filepath;    
    private String deviceName;
	@Override
	public void run() {
		logger.info("logcat启动");
		execlogcat();
	}
		
	public LogcatUtil(String deviceName ,File filepath){
		this.deviceName = deviceName;
		this.filepath= filepath;		
	}
	public void execlogcat()  {		
		try {
			//清除缓存
			RuntimeUtil.exec("adb -s "+deviceName+" logcat -c");
			String cmd="adb -s  "+deviceName+"  shell logcat -v time";		
			Process p = Runtime.getRuntime().exec(cmd);			
			// 正确输出流
			InputStream input = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			String line = "";	
			String logPath=filepath.getPath()+File.separator+"logcat.log";
			FileWriter writer = new FileWriter(logPath);			
			while (!interrupted()) {
				line = reader.readLine();
				if(line!=null){
					writer.append(line+"\n");					
				}	
			}	
			logger.info("Logcat结束");
			p.destroy();	
			reader.close();		
			input.close();
			writer.append("finished");			
			// 错误输出流
			InputStream errorInput = p.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput,"UTF-8"));
			String eline = "";			
			while (!interrupted()) {
				eline = errorReader.readLine();
				if(eline != null){					
					writer.append(eline);
				}	
			}				
			p.destroy();			
			errorReader.close();// 此处有依赖关系，先关闭errorReader			
			errorInput.close();			
			writer.append("finished");
			} catch (IOException e) {	
			e.printStackTrace();
		}
	}
}
