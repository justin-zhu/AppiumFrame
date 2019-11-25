package com.meitu.entity;
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
	@Override
	public String toString() {
		return "DriverEntity [port=" + port + ", udid=" + udid + ", path=" + path + ", appPackage=" + appPackage
				+ ", appActivity=" + appActivity + ", version=" + version + "]";
	}
	
	
}
