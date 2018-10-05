package DataBase;



import javax.swing.JFrame;

import Gui.VentanaAdmin;
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
	
	public boolean verificarUsuario(String user,String password,JFrame login){
		
		boolean tr=false;
		
	
		switch(user) {
			
			case "ATM":{
				tr=password=="atm";
				break;
			}
			
			case "admin":{
				tr=password=="admin";
				VentanaAdmin admin=new VentanaAdmin();
					
				admin.setVisible(true);
				admin.setEnabled(true);
					
				login.dispose();
					
				break;
			}
			
			case "empleado":{
				tr=password=="empleado";
				break;		
			}
		}
	
		return tr;
	}
	
	
	
	
}
