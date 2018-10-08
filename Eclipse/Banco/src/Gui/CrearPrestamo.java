package Gui;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import Logica.Fechas;

public class CrearPrestamo extends JInternalFrame {
	
	private Connection conexionBD = null;
	private int numeroDoc;
	private String tipoDoc;
	private JPanel contentPane, tablePane;
	private JButton btnConsultar;
	private JTextField tfTipo, tfNum;
	private JTable table;
	private JScrollPane spTable;
	
	public CrearPrestamo() {
		initGui();
	}	
	
	private void initGui() {
		setTitle("Crear prestamo");
		setResizable(false);

		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 527);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(false);
		this.setEnabled(false);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(Color.DARK_GRAY);
		btnConsultar.setBounds(604, 164, 89, 23);
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(new oyenteConsultar(this));

		tablePane= new JPanel();
		tablePane.setForeground(Color.WHITE);
		tablePane.setBackground(Color.DARK_GRAY);
		tablePane.setBounds(10,198,799,285);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);

		table = new JTable();
		table.setForeground(Color.DARK_GRAY);
		table.setBackground(Color.WHITE);
		table.setBounds(2, 50, 789, 384);
		//contentPane.add(table);
		table.setEnabled(false);

		spTable = new JScrollPane(table);
		spTable.setForeground(Color.WHITE);
		spTable.setBackground(Color.DARK_GRAY);
		spTable.setBounds(0,50,799,400);

		tablePane.add(spTable, BorderLayout.CENTER);

		tfTipo = new JTextField(10);
		tfTipo.setForeground(Color.WHITE);
		tfTipo.setBackground(Color.DARK_GRAY);
		tfTipo.setBounds(10, 11, 799, 142);
		contentPane.add(tfTipo);		

		tfNum = new JTextField(10);
		tfNum.setForeground(Color.WHITE);
		tfNum.setBackground(Color.DARK_GRAY);
		tfNum.setBounds(10, 11, 799, 142);
		contentPane.add(tfNum);
	}

	private void realizarConsulta(){

		int filas=0;
		int i=0;
		String fecha;
		
		try{    

			if(tfTipo.getText().isEmpty() || tfNum.getText().isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"Los campos de tipo y número no pueden estar vacíos\n","Campo vacío",JOptionPane.ERROR_MESSAGE);
			else{
				conectarBD();
				Statement stmt = this.conexionBD.createStatement();
				String tfQuery = "SELECT tipo_doc, nro_doc FROM Cliente WHERE tipo_doc=" + tfTipo +" and nro_doc=" + tfNum;

				ResultSet rs= stmt.executeQuery(tfQuery);
				
				if(!rs.next())
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El cliente correspondiente al tipo y número de documento ingresados ya tiene un préstamo activo\n","No se puede crear préstamo",JOptionPane.ERROR_MESSAGE);
				else {
					ResultSetMetaData md= rs.getMetaData();

					rs=stmt.executeQuery(tfQuery);
					
					

					table.setModel(bancoModel);

					JTableHeader header = table.getTableHeader();
					tablePane.add(header,BorderLayout.NORTH);
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

			//establece una conexión con la  B.D. "banco"    
			conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
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
	
	private class oyenteConsultar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteConsultar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			realizarConsulta();
		}
		
	}
	
}
