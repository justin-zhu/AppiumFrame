package com.meitu.listener;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;
/**
 * 重写subAppend方法,将日志同时输出至Reporter
 * @author Justin-zhu
 */
public class MyLog4jAppenderListener extends ConsoleAppender{
	
	@Override
	protected void subAppend(LoggingEvent event) {
		this.qw.write(this.layout.format(event));
		//将结果写入报告中
		String message = this.layout.format(event);
		//过滤掉截图相关的日志
		if(!message.contains("screenshot")&&!message.contains("sleep 100ms")) {
			Reporter.log(message);
		}
		
	    if(layout.ignoresThrowable()) {
	      String[] s = event.getThrowableStrRep();
	      if (s != null) {
		int len = s.length;
		for(int i = 0; i < len; i++) {
		  this.qw.write(s[i]);
		  this.qw.write(Layout.LINE_SEP);
		}
	      }
	    }

	    if(shouldFlush(event)) {
	      this.qw.flush();
	    }
	}
}
