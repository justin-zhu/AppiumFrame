package com.meitu.ctrl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;
/**
 * 服务提供类
 * @author p_xiaogzhu
 *2019年3月25日
 *
 */
public enum AppiumServerCtrl {	
	Instance;
	static Logger logger =Logger.getLogger(AppiumServerCtrl.class);
	HashMap<String, Process> appiumMap = new HashMap<String, Process>();
	public void startServer(String port,String deviceName) throws Exception {
		killServer();
		Process process=null;
		//String session=" --session-override";
        String cmd = "cmd /k appium" + " -p " + port + " -bp " + (Integer.valueOf(port)+1);
        logger.info("appiumServer init arg:"+ cmd);
        process = Runtime.getRuntime().exec(cmd);
        logger.info("Process:"+process);
        appiumMap.put(port, process);        
        InputStream inputStream = process.getInputStream();
        logger.info("inputStream:"+inputStream);
        InputStream errorStream = process.getErrorStream();  
        logger.info("errorStream:"+errorStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));  
        logger.info("inputStream reader:"+errorStream);
        BufferedReader reader2 = new  BufferedReader(new  InputStreamReader(errorStream));  
        logger.info("errorStream reader:"+errorStream);
        new Thread(new Runnable() {			
			@Override
			public void run() {
		        try {
		        	logger.info("errorInputStream 已连接");
		            String line2 = "" ;   
		            while ((line2 = reader2.readLine()) !=null   ) { 
		            	logger.debug(line2);		            	
		            }   
		          } catch (IOException e) {   
		                e.printStackTrace();  
		          }  
			}
		}).start();       
        Thread.sleep(3000);
        String line="";
        while ((line = reader.readLine()) != null) {        	
        	logger.info(line);        	
        }
        try {         	
			process.waitFor();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
        logger.info("stop appium server");  
        inputStream.close();
        reader.close();
        process.destroy();		
	}
	private void stopServer(Process process) {
        if (process != null) {        	
            process.destroy();           
            logger.info("appium process destory successed");
        }
    }
	/**
	 * 根据端口销毁指定的任务线程
	 * @param port
	 */
	public void stopServer(String port) {
        Process process = appiumMap.get(port);
        stopServer(process);
        appiumMap.remove(port);
        logger.info("appium server shutdown portNum:"+port);
    }
	/**
	 * 开启appium服务前的清理
	 * @throws InterruptedException
	 */
	private  void killServer() throws InterruptedException{		
			String cmd2="taskkill /F /IM node.exe";
			try {
				Runtime.getRuntime().exec(cmd2);								
				logger.info("Node.JS server killed");
			} catch (IOException e) {			
				e.printStackTrace();
			}finally{					
				Thread.sleep(4000);				
			}
		
	}

}
