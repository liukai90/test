package beautify.table.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beautify.table.pojo.Table;

public class DButil {
	
	//获取旧表的所有数据
	public List<Table> getAllTable(String tableName){
		
		List<Table> list=new ArrayList<Table>();
		
		String sql="SELECT * FROM "+tableName;
		
		Connection con=null;
		
		Statement stat=null;
		
		ResultSet rs=null;
		
		try {
			
			con=BaseDAO.getConnection();
			
			stat=con.createStatement();
			
			rs=stat.executeQuery(sql);
			
			while(rs.next()){
				
				Table tb=new Table();
				
				tb.setSubj(rs.getString(1));
				
				tb.setProp(rs.getString(2));
				
				tb.setObj(rs.getString(3));
				
				list.add(tb);
				
			}
			
			System.out.println("获取表数据成功！");
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("获取表格失败");
		}finally {
			
			BaseDAO.closeConnection(con);
		}
		return null;
		
	}
	
	//创建一个新表
	public String createNewTable(String tableName){
		
		String sql="CREATE TABLE "+"new_"+tableName+"(id  int(11) NOT NULL AUTO_INCREMENT ,"+
					"subj  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"prop varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"obj  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,"+
					"PRIMARY KEY (`id`)"+
					")";
		Connection con=null;
		
		PreparedStatement ps=null;
		
		try {
			con=BaseDAO.getConnection();
			
			ps=con.prepareStatement(sql);
			
			ps.executeUpdate();
			
			System.out.println("建表成功！");
			
			return "new_"+tableName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("建表失败！");
		}finally {
			
			BaseDAO.closeConnection(con);
		}
		
		return null;
		
	}
	
	//批量截取字符串
	public List<Table> batchSub(List<Table> oldList){
		
		List<Table> newList=new ArrayList<Table>();
		
		for(Table tb:oldList){
			
			tb.setSubj(DButil.subString(tb.getSubj()));
			
			tb.setProp(DButil.subString(tb.getProp()));
			
			tb.setObj(DButil.stringFormat(DButil.subString(tb.getObj())));
			
			newList.add(tb);
		}
		
		return newList;
	}
	
	//批量向新表里插入处理后的数据
	public void batchInserts(List<Table> newList,String newTableName){
		
		String sql="INSERT INTO "+newTableName+
				"(subj, prop, obj) "+"VALUES(?, ?, ?)";
		
		Connection con=null;
		
		PreparedStatement ps=null;
		
		try {
			con=BaseDAO.getConnection();
			
			con.setAutoCommit(false);
			
			ps=con.prepareStatement(sql);
			
			for(Table tb:newList){
				
				ps.setString(1, tb.getSubj());
				
				ps.setString(2, tb.getProp());
				
				ps.setString(3, tb.getObj());
				
				ps.addBatch();
			}
			
			ps.executeBatch();
			
			con.commit();
			
			System.out.println("批量插入数据成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("批量插入失败！");
			
		}finally {
			
			BaseDAO.closeConnection(con);
		}
		
		
		
	}
	
	//自定义截取单个字符串
	public static String subString(String str){
		
		if(str.lastIndexOf("#")>=0){
			
			return str.substring(str.lastIndexOf("#")+1);
			
		}else{
			
			return str.substring(str.lastIndexOf("/")+1);
		}
		
		
	}
	
	//去掉最后一列的冒号和数据类型前缀
	public static String stringFormat(String str){
		
		String [] typeTable={"integer","float","double","string"};
		
		StringBuffer sb=new StringBuffer(str);
		
		sb.deleteCharAt(sb.lastIndexOf(":"));
		
		for(int i=0;i<typeTable.length;i++){
			
			if(sb.indexOf(typeTable[i])>=0){
				
				sb.delete(sb.indexOf(typeTable[i]),
						sb.indexOf(typeTable[i])+typeTable[i].length());
			}
		}
		return sb.toString();
	}

}
