package com.zwk.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

public class JDBCUtils {
	private static DataSource dataSource;

	static {
		try {
			// 1.加载配置文件
			Properties properties = new Properties();
			properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
			// 2.获取DataSource
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * 释放资源
	 */
	public static void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取连接池
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
}