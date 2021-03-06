package Gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class ExtraccionATM extends JInternalFrame {

	private JTextField txtMonto;
	//private int codCaja;
	private int numTarjeta;
	private JButton btnExtraer;
	private Connection conexionBD = null;

	
	public ExtraccionATM(int codCaja,int numTarjeta) {
	
		initGUI();
		//this.codCaja=codCaja;
		this.numTarjeta=numTarjeta;
	}
	
	private void initGUI(){
		
		setClosable(true);
		setTitle("Extraccion");
		setBorder(null);
		setForeground(new Color(211, 211, 211));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 266, 78);
		getContentPane().setLayout(null);
		
		txtMonto = new JTextField();
		txtMonto.setText("Monto");
		txtMonto.setBounds(12, 12, 114, 27);
		getContentPane().add(txtMonto);
		txtMonto.setColumns(10);
		
		btnExtraer = new JButton("Extraer");
		btnExtraer.setBackground(new Color(211, 211, 211));
		btnExtraer.setBounds(138, 13, 117, 25);
		btnExtraer.addActionListener(new oyenteExtraer(this));
		getContentPane().add(btnExtraer);

	}
	
	private class oyenteExtraer implements ActionListener{
		
		private JInternalFrame miFrame;
		
		public oyenteExtraer(JInternalFrame miFrame){
			this.miFrame=miFrame;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
			int resp = JOptionPane.showConfirmDialog(null, "┐Confirmar Extraccion?","Confirmacion",JOptionPane.YES_NO_OPTION);
			
			if(resp==0)
				//Si
				realizarExtraccion();
			else
				//No
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(miFrame), "La Extraccion ha sido cancelada." + "\n","Transaccion finalizada",JOptionPane.INFORMATION_MESSAGE);
		
			txtMonto.setText("Monto");
			
		} 
	 }

	private void realizarExtraccion(){
		
		String query;
		double monto;
		int atm=100;
		String mensaje="";
		ResultSet rs;
		
		try {
			
			conectarBD();

			monto = Double.parseDouble(txtMonto.getText());

			if(monto>0) {

				query="call extraer("+atm+","+numTarjeta+","+monto+")";

				Statement stmt = conexionBD.createStatement();

				
				rs= stmt.executeQuery(query);
			
				if(rs.next())	
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),rs.getString(1) + "\n","Extraccion Finalizada",JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				mensaje= "ERROR: El monto debe ser mayor a cero";
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), mensaje + "\n","Extraccion Abortada",JOptionPane.ERROR_MESSAGE);
			}
			
			
			desconectarBD();
			 
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "ERROR: Salio se admite numeros enteros." + "\n","Extraccion Abortada",JOptionPane.ERROR_MESSAGE);		
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
			String usuario = "atm";
			String clave = "atm";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";

			//establece una conexion con la  B.D. "banco"    
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

