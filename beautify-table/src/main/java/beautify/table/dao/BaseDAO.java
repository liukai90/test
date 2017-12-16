package beautify.table.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class BaseDAO {
	
	private static BasicDataSource dataSource=null;
	//静态块初始化数据库
	static{
		
		Properties prop=new Properties();
		
		try {
			
			//通过类路径加载数据库配置参数
			prop.load(BaseDAO.class.getClassLoader().getResourceAsStream("resource/db.properties"));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取不到数据库文件！");
		}
		
		try {
			
			//获取配置文件的数据库参数
			String driverName=prop.getProperty("jdbc.driver");
			
			String url=prop.getProperty("jdbc.url");
			
			String username=prop.getProperty("jdbc.username");
			
			String password=prop.getProperty("jdbc.password");
			
			//将参数设置到连接池中
			dataSource=new BasicDataSource();
			
			dataSource.setDriverClassName(driverName);
			
			dataSource.setUrl(url);
			
			dataSource.setUsername(username);
			
			dataSource.setPassword(password);
			
			dataSource.setMaxActive(8);
			
			dataSource.setMaxIdle(2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println("创建连接池失败！");
			
		}
		
		
	}
	
	//向外界提供获取数据库连接的方法
	public static Connection getConnection() throws Exception{
		
		return dataSource.getConnection();
	}
	
	//将数据连接归还给连接池
	public static void closeConnection(Connection con){
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
