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
	Logger logger =Logger.getLogger(this.getClass());
	HashMap<String, Process> appiumMap = new HashMap<String, Process>();
	public void startServer(String port,String deviceName) throws Exception {
		killServer();
		Process process=null;
		//String session=" --session-override";
        String cmd = "cmd /k appium" + " -p " + port + " -bp " + (Integer.valueOf(port)+1)+" -U "+deviceName ;
        logger.info("AppiumServer init arg:"+ cmd);
        process = Runtime.getRuntime().exec(cmd);
        appiumMap.put(port, process);        
        InputStream inputStream = process.getInputStream();
        InputStream is2 = process.getErrorStream();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));       
        BufferedReader reader2 = new  BufferedReader(new  InputStreamReader(is2));   
        new Thread(new Runnable() {			
			@Override
			public void run() {
		        try {   
		            String line2 = "" ;   
		            while ((line2 = reader2.readLine()) !=null   ) { 
		            	logger.debug(line2);
		            	if(line2.contains("shell pm list packages com.tencent.southpole.appstore' exited with code 2")) {
		            		throw new RuntimeException("adb shell pm list 运行失败");
		            	}
		            }   
		          } catch (IOException e) {   
		                e.printStackTrace();  
		          }  
			}
		}).start();
        String line;
        while ((line = reader.readLine()) != null) {        	
        	logger.debug(line);
        	if(line.contains("Welcome"))
        	{
        		logger.info("Appium Init Successed!");
        	}
        }
        try {        	
			process.waitFor();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
        logger.info("Stop Appium Server");  
        inputStream.close();
        reader.close();
        process.destroy();		
	}
	private void stopServer(Process process) {
        if (process != null) {        	
            process.destroy();           
            logger.info("Appium Process Destory Successed");
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
        logger.info("Appium Server Shutdown PortNum:"+port);
    }
	/**
	 * 开启appium服务前的清理
	 * @throws InterruptedException
	 */
	private  void killServer() throws InterruptedException{		
			String cmd2="taskkill /F /IM node.exe";
			try {
				Runtime.getRuntime().exec(cmd2);								
				logger.info("Node.JS Server Killed");
			} catch (IOException e) {			
				e.printStackTrace();
			}finally{					
				Thread.sleep(4000);				
			}
		
	}

}
