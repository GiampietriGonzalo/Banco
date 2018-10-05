package DataBase;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
	
	public void verificarUsuario(String user,String password,JFrame login){
		
	
		switch(user) {
			
			case "ATM":{
				if(password.equals("atm")){
					
					//TODO IR A FRAME ATM
				}
				else
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta\n","Login Error",JOptionPane.ERROR_MESSAGE);

				break;
			}
			
			case "admin":{
				
				if(password.equals("admin")) {
					VentanaAdmin admin=new VentanaAdmin();

					admin.setVisible(true);
					admin.setEnabled(true);

					login.dispose();
				}
				else
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta \n","Login Error",JOptionPane.ERROR_MESSAGE);

					
					
				break;
			}
			
			case "empleado":{
				if(password.equals("empleado")){
					//TODO IR A FRAME EMPLEADO
					
				}
				else
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta\n","Login Error",JOptionPane.ERROR_MESSAGE);

				break;		
			}
			
			default:JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta\n","Login Error",JOptionPane.ERROR_MESSAGE);

		}

	}
	
	
	
	
}
