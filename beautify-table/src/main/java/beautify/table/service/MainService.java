package beautify.table.service;

import java.util.List;
import java.util.Scanner;

import beautify.table.dao.DButil;
import beautify.table.pojo.Table;

public class MainService {
	
	public static void main(String[] args) {
		
			Scanner sc=new Scanner(System.in);//这一块可以改表名
			
			String tableName=sc.nextLine();
			
			DButil db=new DButil();
			
			//创建新表并获取新的表名
			String newTableName=db.createNewTable(tableName);
			
			//获取原表数据
			List<Table> oldList=db.getAllTable(tableName);
			
			//处理原表数据得到一个新表的数据
			List<Table> newList=db.batchSub(oldList);
			
			//向新表批量插入处理后的数据
			db.batchInserts(newList, newTableName);
			
			System.out.println(tableName+"表执行成功！");
			
		
	}

}
