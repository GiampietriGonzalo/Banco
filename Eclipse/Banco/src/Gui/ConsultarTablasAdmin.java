package Gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class ConsultarTablasAdmin extends JInternalFrame {

	private JList listaTablas;
	private JLabel lblTablas;
	private JLabel lblAtributos;
	private JList listaAtributos;
	private Connection conexionBD=null;
	private String[] toListaTablas,toListaAtributos;
	private JScrollPane spAtr;
	
	public ConsultarTablasAdmin() {
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		setTitle("Consultar Tablas");
		setMaximizable(true);
		setClosable(true);
		setForeground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		getContentPane().setBackground(Color.DARK_GRAY);
		setBounds(0, 0, 835, 527);
		getContentPane().setLayout(null);
		
		toListaTablas= new String[27];
		listaTablas = new JList(toListaTablas);
		listaTablas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		listaTablas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaTablas.setForeground(Color.WHITE);
		listaTablas.setBackground(Color.GRAY);
		listaTablas.setBounds(70, 75, 263, 394);
		//getContentPane().add(listaTablas);
		
		lblTablas = new JLabel("Tablas de la BD");
		lblTablas.setOpaque(true);
		lblTablas.setBackground(Color.BLACK);
		lblTablas.setForeground(Color.WHITE);
		lblTablas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTablas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTablas.setBounds(60, 43, 263, 31);
		getContentPane().add(lblTablas);
		
		lblAtributos = new JLabel("Atributos de la Tabla");
		lblAtributos.setOpaque(true);
		lblAtributos.setBackground(Color.BLACK);
		lblAtributos.setForeground(Color.WHITE);
		lblAtributos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtributos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAtributos.setBounds(493, 43, 263, 31);
		getContentPane().add(lblAtributos);
		
		toListaAtributos= new String[15];
		listaAtributos = new JList(toListaAtributos);
		listaAtributos.setMinimumSize(new Dimension(2, 54));
		listaAtributos.setMaximumSize(new Dimension(2, 54));
		listaAtributos.setToolTipText("");
		listaAtributos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		listaAtributos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaAtributos.setForeground(Color.WHITE);
		listaAtributos.setBackground(Color.GRAY);
		listaAtributos.setBounds(70, 75, 263, 394);

		
		JScrollPane spTablas = new JScrollPane();
		spTablas.setOpaque(false);
		spTablas.setBackground(Color.DARK_GRAY);
		spTablas.setAlignmentY(Component.TOP_ALIGNMENT);
		spTablas.setAlignmentX(Component.LEFT_ALIGNMENT);
		spTablas.setBounds(60, 74, 263, 396);
		spTablas.setColumnHeaderView(listaTablas);
		getContentPane().add(spTablas);
		
		spAtr = new JScrollPane();
		spAtr.setOpaque(false);
		spAtr.setBackground(Color.DARK_GRAY);
		spAtr.setBounds(493, 74, 263, 396);
		spAtr.setColumnHeaderView(listaAtributos);	
		getContentPane().add(spAtr);
		
		spAtr.setViewportView(listaAtributos);
		spTablas.setViewportView(listaTablas);

		
		listaTablas.addListSelectionListener(new oyenteSelectTabla(listaTablas));
		consultarTablas();
		
	
	}
	
	private void conectarBD(){

		try{
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "banco";
			String usuario = "admin";
			String clave = "admin";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";

			//establece una conexion con la  B.D. "banco"    
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
	
	
	private void consultarTablas(){
		
		String query="SHOW TABLES";

		try {
			conectarBD();
			Statement stmt = conexionBD.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			int i=0;
			
			
			while(rs.next()){
				toListaTablas[i]=rs.getString(1);
				i++;
			}
	
		
			rs.close();
			desconectarBD();

		}
		catch (SQLException ex){
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarAtributos(String tabla){
		
		String query="DESCRIBE "+tabla;
		
		
		try {
			conectarBD();
			Statement stmt = conexionBD.createStatement();
			ResultSet rs= stmt.executeQuery(query);
			int i=0;
			
			toListaAtributos= new String[15];
			
			while(rs.next()){
				toListaAtributos[i]=rs.getString(1);
				i++;
			}
	
			listaAtributos.setListData(toListaAtributos);
			rs.close();
			desconectarBD();

		}
		catch (SQLException ex){
			// en caso de error, se muestra la causa en la consola
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public class oyenteSelectTabla implements ListSelectionListener{

		private JList miJList;
		
		public oyenteSelectTabla(JList miJList){
			this.miJList=miJList;
		}
		
		public void valueChanged(ListSelectionEvent event) {
			String tabla = (String) miJList.getSelectedValue();
			consultarAtributos(tabla);
			
		}
		
		
		
	}
}
