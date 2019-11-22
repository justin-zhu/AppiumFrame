package com.meitu.entity;
/**
 * 测试数据的entity
 * @author p_xiaogzhu
 *2019年3月28日
 *
 */
public class TestCaseEntity {
	String step,type,arg,text,expect;

	public String getStep() {
		return step;
	}

	public String getType() {
		return type;
	}

	public String getArg() {
		return arg;
	}

	public String getText() {
		return text;
	}
	
	public void setStep(String step) {
		this.step = step;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public void setText(String text) {
		this.text = text;
	}	

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}	
}
