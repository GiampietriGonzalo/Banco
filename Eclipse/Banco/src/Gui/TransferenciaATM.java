package Gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class TransferenciaATM extends JInternalFrame {
	
	private JTextField txtDestino;
	private JTextField txtMonto;
	private Connection conexionBD = null;
	private JButton btnTransferir;
	private int codCaja;
	

	
	public TransferenciaATM(int codCaja) {
		setBorder(null);
		initGui();
		this.codCaja=codCaja;
	}
	
	private void initGui(){
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setLayout(null);
		
		txtDestino = new JTextField();
		txtDestino.setText("N∞ de la cuenta de ahorro destino");
		txtDestino.setBounds(12, 12, 243, 30);
		getContentPane().add(txtDestino);
		txtDestino.setColumns(10);
		
		txtMonto = new JTextField();
		txtMonto.setText("Monto");
		txtMonto.setBounds(12, 54, 114, 30);
		getContentPane().add(txtMonto);
		txtMonto.setColumns(10);
		
		btnTransferir = new JButton("Transferir");
		btnTransferir.setBackground(new Color(211, 211, 211));
		btnTransferir.setBounds(138, 54, 117, 30);
		btnTransferir.addActionListener(new oyenteTransferir(this));
		getContentPane().add(btnTransferir);
		setForeground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Transferencia");
		setBounds(100, 100, 274, 119);
		

	}
	
	private class oyenteTransferir implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteTransferir(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0){
			
			int resp = JOptionPane.showConfirmDialog(null, "øConfirmar transacciÛn?","ConfirmaciÛn",JOptionPane.YES_NO_OPTION);
			
			if(resp==0)
				//Si
				realizarTransferencia();
			else
				//No
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(miFrame), "La transacci√≥n ha sido cancelada." + "\n","Transacci√≥n finalizada",JOptionPane.INFORMATION_MESSAGE);
		
			txtDestino.setText("Destino");
			txtMonto.setText("Monto");
		}
		
	}
	
	private void realizarTransferencia(){
		
		String query;
		int destino;
		double monto;
		int atm=100;
		String mensaje="";
		
		try {
			
			conectarBD();
			
			destino= Integer.parseInt(txtDestino.getText());
			monto = Double.parseDouble(txtMonto.getText());
		
			if(monto>0) {

				query="call transferir("+atm+","+monto+","+codCaja+","+destino+")";

				Statement stmt = conexionBD.createStatement();


				ResultSet rs= stmt.executeQuery(query);

				if(rs.next()){
					mensaje=rs.getString(1);
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), mensaje + "\n","Transferencia Finalizada.",JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			else{
				mensaje="ERROR: El monto debe ser mayor a cero";
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), mensaje + "\n","Transferencia Abortada.",JOptionPane.ERROR_MESSAGE);	
			}
			
			
			desconectarBD();
			 
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Al menos uno de los campos es incorrecto. S√≥lo se admite n√∫meros enteros." + "\n","Transferencia Abortada",JOptionPane.ERROR_MESSAGE);
			
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
