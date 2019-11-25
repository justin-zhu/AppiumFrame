package com.meitu.ctrl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
		this.killServer();		
		//String session=" --session-override";
        String cmd = "cmd /k appium" + " -p " + port + " -bp " + (Integer.valueOf(port)+1);
        logger.info("appiumServer init arg:"+ cmd);
        Process process = Runtime.getRuntime().exec(cmd);
        logger.info("Process:"+process);
        appiumMap.put(port, process);        
        InputStream inputStream = process.getInputStream();       
        InputStream errorStream = process.getErrorStream();        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));         
        printMessage(errorStream);
        printMessage(inputStream);
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

	private  void printMessage(InputStream input) {
		new Thread(new Runnable() {
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						logger.debug(line);
					}
				} catch (IOException e) {					
				}finally {
					try {
						reader.close();
						bf.close();
					} catch (IOException e) {						
						e.printStackTrace();
					}					
				}
			}
		}).start();
	}

}
