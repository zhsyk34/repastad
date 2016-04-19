package com.baiyi.order.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.baiyi.order.pojo.Material;


public class JDBCUtil {

	public static String username;

	public static String password;

	public static String url;

	private static JDBCUtil jdbcUtil;

	private JDBCUtil() {
		registDriver();
	}

	public static synchronized JDBCUtil getInstance() {
		if (jdbcUtil == null) {
			jdbcUtil = new JDBCUtil();
		}
		return jdbcUtil;
	}

	/**
	 * 注册驱动获得连接
	 * 
	 */
	
	public Connection getConnection() throws SQLException{
		String url = this.url+"&user="+ this.username + "&password=" + this.password;
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}
	
	public void registDriver() {
		try {
			// bt2v68fcmw
			Class.forName("com.mysql.jdbc.Driver");
			String url = this.url+"&user="+ this.username + "&password=" + this.password;
		} catch (ClassNotFoundException e) {
			Log4JFactory.instance().error("JDBC Reigster Driver ERROR");
		} 

	}
	//清空detectrecords記錄
	public void deleteTables(){
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("delete from detectrecords");
			state.close();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	//修復detectrecords
	public void repairTables(){
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		try {
			Statement state = conn.createStatement();
			boolean result = state.execute("repair table detectrecords");
			System.out.println(result);
			state.close();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}

	/**
	 * 读取获得配置信息
	 * 
	 * @return
	 */

	public Object[] getConfigManage() {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];
				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}
				return result;
			}
		};
		QueryRunner run = new QueryRunner();
		Object[] result = null;
		try {
			result = run.query(conn, "SELECT * FROM configmanage", h);
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return result;
	}
	public Object[] getOtherConfig() {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];
				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}
				return result;
			}
		};
		QueryRunner run = new QueryRunner();
		Object[] result = null;
		try {
			result = run.query(conn, "SELECT leftlogo FROM configmanage", h);
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取用户默认设置节目
	 * @param userId
	 * @return
	 */
	public Object[] getUserDefaultProgram(int userId){
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];
				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}
				return result;
			}
		};
		QueryRunner run = new QueryRunner();
		Object[] result = null;
		try {
			result = run.query(conn, "SELECT DefaultProgramId,DefaultName FROM configmanage WHERE userId="+userId, h); 
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 读取素材表并改变转换后的值
	 * 
	 * @return
	 */
	public void modifyMaterial(int id) {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];
				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}
				return result;
			}
		};
		QueryRunner run = new QueryRunner();
		Object[] result = null;
		try {
			// 查询
			result = run.query(conn, "SELECT * FROM material WHERE id =" + id,h);
			// 写入
			String path = (String) result[3];
			path = path.substring(0, path.lastIndexOf(".") + 1) + "flv";
			System.out.println("id:"+id+",path:"+path);
			Object[] updateParams = { path, id };
			int inserts = run.update(conn,
					"UPDATE material SET PATH=? WHERE ID = ?",
					updateParams);
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}

	public Material findMaterialById(int id){
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		QueryRunner qr = new QueryRunner();
		Material material = null;
		try {
			material = (Material) qr.query(conn,
					"SELECT * FROM Material where id = ?", new BeanHandler(
							Material.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return material;
	}
	

	public Object[] findByNamePass(String userName){
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC Get Connection ERROR",e);
		}
		ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
			public Object[] handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				ResultSetMetaData meta = rs.getMetaData();
				int cols = meta.getColumnCount();
				Object[] result = new Object[cols];
				for (int i = 0; i < cols; i++) {
					result[i] = rs.getObject(i + 1);
				}
				return result;
			}
		};
		QueryRunner run = new QueryRunner();
		Object[] result = null;
		try {
			result = run.query(conn, "SELECT id FROM admins WHERE username='"+userName+"'", h);
		} catch (SQLException e) {
			Log4JFactory.instance().error("JDBC queryrunner ERROR",e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		JDBCUtil.username = "root";
		JDBCUtil.password = "root";
		JDBCUtil.url = "jdbc:mysql://localhost:3306/tvset?useUnicode=true&characterEncoding=utf-8";
		String ip = "127.0.0.1";
		JDBCUtil util = JDBCUtil.getInstance();
		// List<Shedule> sheduleList = util.searchShedule(ip);
//		Program p = util.findProgramById(85);
//		Object[] result = util.findByNamePass("root");
		util.repairTables();
		System.out.println();
	}
}
