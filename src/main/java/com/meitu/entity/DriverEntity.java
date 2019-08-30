package com.meitu.entity;

import java.util.List;
/**
 * 交由DriverCtrl调用
 * @author p_xiaogzhu
 *2019年3月28日
 *
 */
public class DriverEntity {
	private String port;
	private String udid;
	private String path;
	private String appPackage;
	private String appActivity;
	private String version;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	private List<String> sheetNameList;	
	
	public List<String> getSheetNameList() {
		return sheetNameList;
	}
	public void setSheetNameList(List<String> sheetNameList) {
		this.sheetNameList = sheetNameList;
	}
	public String getPort() {
		return port;
	}
	public String getUdid() {
		return udid;
	}
	public String getPath() {
		return path;
	}
	public String getAppPackage() {
		return appPackage;
	}
	public String getAppActivity() {
		return appActivity;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}
	public void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}

	
}
