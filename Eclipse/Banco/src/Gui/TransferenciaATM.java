package Gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	private int numTarjeta;
	

	
	public TransferenciaATM(int numTarjeta) {
		initGui();
		this.numTarjeta=numTarjeta;
	}
	
	private void initGui(){
		
		
		
		getContentPane().setBackground(new Color(211, 211, 211));
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setLayout(null);
		
		txtDestino = new JTextField();
		txtDestino.setText("N° de la cuenta de ahorro destino");
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
		setBounds(100, 100, 291, 133);
		

	}
	
	private class oyenteTransferir implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteTransferir(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0){
			
			int resp = JOptionPane.showConfirmDialog(null, "¿Confirmar transacción?","Confirmación",JOptionPane.YES_NO_OPTION);
			
			if(resp==0){
				//Si
				realizarTransferencia();
				txtDestino.setText("Destino");
				txtMonto.setText("Monto");
				
				//Si la transaccion fue un exito, entonces
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(miFrame), "La transacción ha sido exitosa." + "\n","Transacción finalizada",JOptionPane.INFORMATION_MESSAGE);
				
			}
			else
				if(resp==1)
					//No
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(miFrame), "La transacción ha sido cancelada." + "\n","Transacción finalizada",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private void realizarTransferencia(){
		
	
		
		try {
			
			int destino= Integer.parseInt(txtDestino.getText());
			int monto = Integer.parseInt(txtMonto.getText());
			
			
			
			 
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Al menos uno de los campos es incorrecto. Sólo se admite números enteros." + "\n","ERROR.",JOptionPane.ERROR_MESSAGE);
			
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
