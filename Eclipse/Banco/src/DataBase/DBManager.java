package DataBase;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import Gui.BoxMessage;
import quick.dbtable.DBTable;

public class DBManager {

	private DBManager manager;
	private DBTable table;
	
	private DBManager(){}
	
	public DBManager gerDBManager(){
		
		if(manager==null)
			manager=new DBManager();
		
		return manager;
	}
	
	private void conectarBD(){
		  
		try{
	       String driver ="com.mysql.cj.jdbc.Driver";
	       String servidor = "localhost:3306";
	       String baseDatos = "banco";
	       String usuario = "admin";
	       String clave = "admin";
	       String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
	   
	       //establece una coneccionn con la  B.D. "batallas"  usando directamante una tabla DBTable    
	       table.connectDatabase(driver, uriConexion, usuario, clave);
	           
		}
	    catch (SQLException ex){
	    	
	    	BoxMessage box=BoxMessage.getBoxMessage("Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage());
	    	box.notificarErrorDB();
	    	//JOptionPane.showMessageDialog(this,"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
	       System.out.println("SQLException: " + ex.getMessage());
	       System.out.println("SQLState: " + ex.getSQLState());
	       System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    catch (ClassNotFoundException e){
	       e.printStackTrace();
	    }
	      
	 }
	
	
	private void desconectarBD(){
	         
		try{
	       table.close();            
	    }
	    catch (SQLException ex){
	       System.out.println("SQLException: " + ex.getMessage());
	       System.out.println("SQLState: " + ex.getSQLState());
	       System.out.println("VendorError: " + ex.getErrorCode());
	    }      
	}
	
	
}
