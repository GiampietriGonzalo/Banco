package Gui;

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

//Impots para SQL
import java.sql.Types;
//import java.sql.ResultSet;
import java.sql.SQLException;
import quick.dbtable.*;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tfPassword;
	private DBTable table;
	
	
	
	//Crea el frame
	public Login() {
		super();
		initGui();
	}
	
	//Inicializa la interfaz gráfica
	private void initGui(){
		
		String query;
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 170);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		query="SELECT * FROM ";
		JComboBox cbUser = new JComboBox();
		cbUser.addItem(new String("Selecciona usuario"));
		cbUser.addItem(new String("ATM"));
		cbUser.addItem(new String("empleado"));
		cbUser.addItem(new String("admin"));
		cbUser.setToolTipText("");
		cbUser.setForeground(Color.WHITE);
		cbUser.setBackground(Color.DARK_GRAY);
		cbUser.setBounds(60, 22, 220, 24);
		contentPane.add(cbUser);
		
		tfPassword = new JTextField();
		tfPassword.setText("PASSWORD");
		tfPassword.setForeground(Color.WHITE);
		tfPassword.setBackground(Color.DARK_GRAY);
		tfPassword.setBounds(61, 58, 220, 19);
		contentPane.add(tfPassword);
		tfPassword.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.DARK_GRAY);
		btnSalir.setBounds(60, 95, 94, 25);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new oyenteSalir(this));
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBackground(Color.DARK_GRAY);
		btnIngresar.setForeground(Color.WHITE);
		
		btnIngresar.addActionListener(new oyenteIngresar(this));
		
		btnIngresar.setBounds(187, 95, 94, 25);
		contentPane.add(btnIngresar);
	
		
	}
		
	//Listener btnIngresar
	public class oyenteIngresar implements ActionListener{
		
		private JFrame miFrame;
		
		public oyenteIngresar(JFrame frame){
			miFrame=frame;
		}
		
		public void actionPerformed(ActionEvent arg0) {
		
				miFrame.setSize(new Dimension(1100,600));
		}
		
	}
	
	//Listener btnSalir
	public class oyenteSalir implements ActionListener{
		
		private JFrame miFrame;
		
		public oyenteSalir(JFrame frame){
			miFrame=frame;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
				Notificaciones not = new Notificaciones("¿SALIR?");
				not.habilitarAceptarSalir();
				not.habilitarCancelarSalir(miFrame);
				
				not.setVisible(true);
				not.setEnabled(true);
				
				
				miFrame.setVisible(false);
				miFrame.setEnabled(false);
		}
		
	}
	
	
	private void thisComponentShown(ComponentEvent evt){
	      this.conectarBD();
	}
	   
	private void thisComponentHidden(ComponentEvent evt){
	      this.desconectarBD();
	}
	
	
	private void conectarBD(){
	  
		try{
	       String driver ="com.mysql.cj.jdbc.Driver";
	       String servidor = "localhost:3306";
	       String baseDatos = "banco";
	       String usuario = "admin";
	       String clave = "admin";
	       String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
	   
	       //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
	       table.connectDatabase(driver, uriConexion, usuario, clave);
	           
		}
	    catch (SQLException ex){
	       JOptionPane.showMessageDialog(this,"Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
