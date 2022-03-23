package com.zwk.utils;

import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInit {
	private static Log4jInit log4jInit;

	public Log4jInit() {
		String log4jInitFilePath = this.getClass().getResource("../../../log4j.properties").getPath();
//		String log4jInitFilePath = this.getClass().getClassLoader().getResource("log4j.properties").getPath();
//		String log4jInitFilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("log4j.properties")).getPath();

		// 获取初始化参数得到配置文件在项目中的路径
		if (null == log4jInitFilePath) {
			System.err.println("*** 没有 log4j-properties-location 初始化的文件, 所以使用 BasicConfigurator初始化");
			BasicConfigurator.configure();
		} else {
			System.out.println("log4j正在加载。。。");
			File log4jFile = new File(log4jInitFilePath);
			if (log4jFile.exists()) {
				System.out.println("使用: " + log4jInitFilePath + "初始化日志设置信息");
				PropertyConfigurator.configure(log4jInitFilePath);
				System.out.println("log4j加载成功。。。");
			} else {
				System.err.println("*** " + log4jInitFilePath + " 文件没有找到， 所以使用 BasicConfigurator初始化");
				BasicConfigurator.configure();
			}
		}
	}

	public static Logger getLogger(Object obj) {
		if (null == log4jInit){
			log4jInit = new Log4jInit();
		}
		return Logger.getLogger(obj.getClass());
	}
}
