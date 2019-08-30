package com.meitu.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.meitu.entity.TestCaseEntity;

/**
 * 接收EXCEL表格中的operation，将具体操作委托给helper类
 * 
 * @author p_xiaogzhu 2019年3月18日
 *
 */
public class HelperManager {
	private Helper helper;
	private List<Map<String, Object>> resultList;
	boolean operationResult = false;	
	private Logger logger =Logger.getLogger(this.getClass());
	private Object object;
	private String sheetName;
	public HelperManager(Helper helper,String sheetName) {
		this.helper = helper;			
		this.sheetName = sheetName;		
		init();
	}
	public void init()  {
		resultList = new ArrayList<Map<String, Object>>();			
		try {
			//调用sheetName对应的实体类的有参构造，传入helper对象
			Class<?> class1 = Class.forName("com.meitu.domain."+sheetName);
			Constructor<?>  con = class1.getConstructor(Helper.class);
			object =con.newInstance(helper);
		} catch (Exception e) {
			logger.info("反射失败:"+sheetName);			
		}		
	}

	//利用反射执行此方法
	public void method(TestCaseEntity userCase){			
		helper.setMethodName(userCase.getType());
		try {						
			ReflectUtil.invoke(object, userCase.getType().trim());
			operationResult = true;			
		} catch (NullPointerException e) {
			try {
				throw new Exception("方法未指定参数:"+userCase.getType());
			} catch (Exception e1) {				
				e1.printStackTrace();
			}
		} catch (RuntimeException exception) {
			operationResult = false;
			logger.info(exception.getMessage());
		}finally {
			//涵数执行完毕均执行清理操作-确保下面涵数的执行环境在预期状态
			ReflectUtil.invoke(object, "clean");
			addResultToList(userCase);
		}		
	}

	public void addResultToList(TestCaseEntity userCase) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();		
		boolean expectboolean = new Boolean(userCase.getExpect());
		map.put("步骤名称", userCase.getStep());
		map.put("操作名称", userCase.getOperation());
		map.put("操作类型", userCase.getType());
		map.put("传递的参数", userCase.getArg());
		map.put("实际结果", "" + operationResult);
		map.put("期望结果", "" + expectboolean);		
		resultList.add(map);
		helper.sleep(2000);
		try {
			Assert.assertEquals(operationResult, expectboolean, userCase.getStep() + "--------->>执行失败");
		} catch (AssertionError e) {
			helper.snapshot(userCase.getStep());
			logger.info("实际执行结果:" + operationResult + ",期望结果:" + expectboolean);			
		}
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}	
}
