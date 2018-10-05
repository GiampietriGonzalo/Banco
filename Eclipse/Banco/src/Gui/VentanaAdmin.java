package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;


public class VentanaAdmin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Connection conexionBD = null;
	private JButton btnConsultar;
	private JButton btnBorrar;
	public JTextField tfQuery;

	public VentanaAdmin() {
		
		initGui();
		
	}

	
	private void initGui() {
		
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 635);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(false);
		this.setEnabled(false);
		
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setForeground(Color.WHITE);
		btnBorrar.setBackground(Color.DARK_GRAY);
		btnBorrar.setBounds(725, 164, 89, 23);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(new oyenteBorrar());
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(Color.DARK_GRAY);
		btnConsultar.setBounds(609, 164, 89, 23);
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(new oyenteConsultar(this));
		
		table = new JTable();
		table.setForeground(Color.WHITE);
		table.setBackground(Color.DARK_GRAY);
		table.setBounds(5, 198, 809, 384);
		contentPane.add(table);
		
		tfQuery = new JTextField(10);
		tfQuery.setBounds(10, 11, 688, 142);
		contentPane.add(tfQuery);
		
	}
	
	
	 private void conectarBD(){
		 
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
	   
	 private void refrescarTabla(){
		   
		 int filas=0;
		 int i=0;
		 try{    
			   
			 if(tfQuery.getText().isEmpty())
				 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL es vacía\n","Consulta vacía",JOptionPane.ERROR_MESSAGE);
			 else{
				 conectarBD();
				 Statement stmt = this.conexionBD.createStatement();
			   
				 ResultSet rs= stmt.executeQuery(tfQuery.getText());
				 ResultSetMetaData md= rs.getMetaData();
				   
				 //Se cuenta la cantidad de filas de la consulta
				 while(rs.next()){
					 filas++;
				 }
			   
				 rs=stmt.executeQuery(tfQuery.getText());
				 TableModel bancoModel;
				 Object columnNames[]=new Object[md.getColumnCount()];
				   
				 while(i<md.getColumnCount()){
					 columnNames[i]=md.getColumnName(i);
					 bancoModel = new DefaultTableModel(columnNames,filas);
					 
				 }
		       
		       
				 for(int j=0;j<filas;j++){
					 i=0;
					 while (rs.next()){
						 table.setValueAt(rs.getObject(i), j, i);       
						 i++;
					 }
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
	   
	 private class oyenteConsultar implements ActionListener{

		 private JFrame miFrame;
		 
		 public oyenteConsultar(JFrame miFrame) {
			 this.miFrame=miFrame;
		 }

		 public void actionPerformed(ActionEvent arg0) {

			 refrescarTabla();

		 }

	 }

	 private class oyenteBorrar implements ActionListener{

		 public void actionPerformed(ActionEvent arg) {

			 tfQuery.setText("");

		 }

	 }
}
