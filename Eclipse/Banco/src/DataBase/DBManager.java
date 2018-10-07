package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Gui.ConsultasAdmin;
import Gui.Login;
import Gui.MenuATM;
import Gui.MenuAdmin;
import quick.dbtable.DBTable;

public class DBManager {

	private static DBManager manager;
	private Connection conexionBD;
	
	private DBManager(){}
	
	public static DBManager getDBManager(){
		
		if(manager==null)
			manager=new DBManager();
		
		return manager;
	}
	
	public void verificarUsuario(String user,String password,Login login){

		String queryATM;
		String queryEmpleado;
		Statement stmt;
		ResultSet rs;
		boolean incorrecto=true;
		
		if(user.equals("admin")){
			
			if(password.equals("admin")) {
				MenuAdmin admin=new MenuAdmin(login);

				admin.setVisible(true);
				admin.setEnabled(true);
				incorrecto=false;
				login.setVisible(false);
				login.setEnabled(false);
			}
			else
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta \n","Login Error",JOptionPane.ERROR_MESSAGE);

		}
		else{
			try{ 
				//POSIBLE MODIFICACION
				queryEmpleado="SELECT legajo FROM empleado WHERE legajo="+Integer.parseInt(user)+" and password=md5('"+password+"')";

				conectarBD(login);
				stmt = conexionBD.createStatement();
				rs= stmt.executeQuery(queryEmpleado);
				//ResultSetMetaData md= rs.getMetaData();
				
				if(rs.first()){
					//EL USUARIO ES EMPLEADO
					System.out.println("EL USUARIO ES EMPLEADO");
					incorrecto=false;
					//login.dispose();
				}
				else{
					//TODO CONSULTA RANCIA -> PREGUNTA A PABLITO
					//queryATM="SELECT DISTINCT cod_caja FROM tarjeta as T,trans_cajas_ahorro as C WHERE T.nro_tarjeta="+Integer.parseInt(user)+" and T.PIN=md5('"+Integer.parseInt(password)+"') and T.nro_ca=C.nro_ca and T.nro_cliente nad C.nro_cliente";

					//rs=stmt.executeQuery(queryATM);
					//if(rs.first()){
						
						//EL USUARIO ES ATM
						incorrecto=false;
						MenuATM ATM= new MenuATM(123,login);
						ATM.setVisible(true);
						ATM.setEnabled(true);
						
						login.setVisible(false);
						login.setEnabled(false);

					//}
				}
			
			}
			catch (SQLException ex){
				//System.out.println("SQLException: " + ex.getMessage());
				//System.out.println("SQLState: " + ex.getSQLState());
				//System.out.println("VendorError: " + ex.getErrorCode());
				//OptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
			}
			catch(NumberFormatException e){
				
			}
			
		}
		
		if(incorrecto)
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(login),"Usuario o Contraseña incorrecta \n","Login Error",JOptionPane.ERROR_MESSAGE);

		
		
	}
	

	
	private void conectarBD(JFrame login){

		try{
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "banco";
			String usuario = "admin";
			String clave = "admin";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";

			//establece una conexión con la  B.D. "banco"    
			this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
			//table.connectDatabase(driver, uriConexion, usuario, clave);

		}
		catch (SQLException ex){
			JOptionPane.showMessageDialog(login,"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}


	}

	private void desconectarBD(){

		if (this.conexionBD != null){
			try{
				this.conexionBD.close();
				this.conexionBD = null;
			}
			catch (SQLException ex){
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}
	
	
	
	
	
}
