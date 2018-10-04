package DataBase;



import quick.dbtable.DBTable;

public class DBManager {

	private static DBManager manager;
	private DBTable table;
	
	private DBManager(){}
	
	public static DBManager getDBManager(){
		
		if(manager==null)
			manager=new DBManager();
		
		return manager;
	}
	
	
	
	
}
