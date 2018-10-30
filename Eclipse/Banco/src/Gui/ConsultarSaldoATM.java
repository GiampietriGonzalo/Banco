package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Logica.Fechas;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;

public class ConsultarSaldoATM extends JInternalFrame {

	private int codCaja;
	private Connection conexionBD = null;
	private JLabel lblSaldo;
	
	public ConsultarSaldoATM(int codCaja){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBorder(null);
		setMaximizable(true);
		setEnabled(false);
		setClosable(true);
		setOpaque(false);
		setForeground(Color.WHITE);
		setTitle("Mi Saldo");
		this.codCaja=codCaja;
		setBounds(100, 100, 280, 118);
		getContentPane().setLayout(null);
		
		JLabel lblInfo = new JLabel("Saldo:");
		lblInfo.setFont(new Font("Dialog", Font.BOLD, 16));
		lblInfo.setBounds(42, 31, 71, 29);
		getContentPane().add(lblInfo);
		
		lblSaldo = new JLabel("");
		lblSaldo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSaldo.setBounds(123, 31, 81, 29);
		getContentPane().add(lblSaldo);
		
		consultarSaldo();
	}
	
	public void consultarSaldo(){
		
		String query="SELECT saldo FROM caja_ahorro WHERE nro_ca="+codCaja;
		int saldo=0;
		
		try{    
			if(query.isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL es vac�a\n","Consulta vac�a",JOptionPane.ERROR_MESSAGE);
			else{

				conectarBD();
				Statement stmt = this.conexionBD.createStatement();
				ResultSet rs= stmt.executeQuery(query);
				ResultSetMetaData md= rs.getMetaData();
				
				if(rs.first()){
					saldo=rs.getInt(1);
					lblSaldo.setText(""+saldo);
				}
				
				rs.close();
				stmt.close();
				desconectarBD();
			}

		}
		catch (SQLException ex){
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	private void conectarBD(){

		try{
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "banco";
			String usuario = "admin";
			String clave = "admin";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";

			//establece una conexi�n con la  B.D. "banco"    
			this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);

		}
		catch (SQLException ex){
			JOptionPane.showMessageDialog(this,"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
