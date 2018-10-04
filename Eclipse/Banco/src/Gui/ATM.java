package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import quick.dbtable.DBTable;

public class ATM extends JFrame {

	private JPanel contentPane;
	private DBTable table;
	
	public ATM() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
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
