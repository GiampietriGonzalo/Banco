package Gui;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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

public class PagarCuotas extends JInternalFrame {
	
	private Connection conexionBD = null;
	private JPanel contentPane, tablePane;
	private JButton btnConsultar, btnPagar, btnCancelar;
	private JTextField tfTipo, tfNum;
	private JTable tabla;
	private JScrollPane spTable;
	private JLabel etTipo, etNum;
	
	public PagarCuotas() {
		initGui();
	}	
	
	private void initGui() {
		
		setTitle("Pagar prestamo");
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

		tablePane= new JPanel();
		tablePane.setForeground(Color.WHITE);
		tablePane.setBackground(Color.DARK_GRAY);
		tablePane.setBounds(10,100,285,285);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);
		
		tabla = new JTable();
		tabla.setForeground(Color.DARK_GRAY);
		tabla.setBackground(Color.WHITE);
		tabla.setBounds(2, 10, 789, 384);
		contentPane.add(tabla);
		tabla.setEnabled(true);

		spTable = new JScrollPane(tabla);
		spTable.setForeground(Color.WHITE);
		spTable.setBackground(Color.DARK_GRAY);
		spTable.setBounds(0,50,799,400);

		tablePane.add(spTable, BorderLayout.CENTER);
		
		etTipo = new JLabel();
		etTipo.setBounds(10, 10, 142, 10);
		etTipo.setEnabled(false);
		etTipo.setText("Tipo documento");
		contentPane.add(etTipo);
		
		tfTipo = new JTextField(10);
		tfTipo.setForeground(Color.WHITE);
		tfTipo.setBackground(Color.DARK_GRAY);
		tfTipo.setBounds(10, 20, 142, 50);
		contentPane.add(tfTipo);

		etNum = new JLabel();
		etNum.setBounds(160, 10, 142, 10);
		etNum.setEnabled(false);
		etNum.setText("Número documento");
		contentPane.add(etNum);
		
		tfNum = new JTextField(10);
		tfNum.setForeground(Color.WHITE);
		tfNum.setBackground(Color.DARK_GRAY);
		tfNum.setBounds(160, 20, 142, 50);
		contentPane.add(tfNum);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setBackground(Color.DARK_GRAY);
		btnConsultar.setBounds(320, 10, 89, 23);
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(new oyenteSeleccionar(this));
				

		btnPagar = new JButton("Pagar");
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setBackground(Color.DARK_GRAY);
		btnPagar.setBounds(320, 40, 89, 23);
		contentPane.add(btnPagar);
		btnPagar.addActionListener(new oyentePagar(this));
		btnPagar.setVisible(false);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(Color.DARK_GRAY);
		btnCancelar.setBounds(320, 70, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new oyenteCancelar(this));
		btnCancelar.setVisible(false);
		
		mostrarDocumentos();
	}

	private void mostrarDocumentos(){

		int i=0;
		
		

		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			String tfQuery = "SELECT tipo_doc, nro_doc FROM Cliente";

			ResultSet rs= stmt.executeQuery(tfQuery);
			
			if(!rs.next())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"No hay clientes registrados en la base de datos\n","No se encontraron clientes",JOptionPane.ERROR_MESSAGE);
			else {
				ResultSetMetaData md= rs.getMetaData();

				rs=stmt.executeQuery(tfQuery);

				JTableHeader header = tabla.getTableHeader();
				tablePane.add(header,BorderLayout.NORTH);
				TableModel bancoModel;
				Object columnNames[]=new Object[md.getColumnCount()];

				while(i<md.getColumnCount()){
					columnNames[i]= new String(md.getColumnName(i+1));
					i++;
				}

				bancoModel = new DefaultTableModel(columnNames,1){
					
					public boolean isCellEditable(int rowIndex, int columnIndex) {
					    return false;  //
					}
				};

				tabla.setModel(bancoModel);

				i=1;
				//Filas i, Columnas j

				while (rs.next()){

					((DefaultTableModel) tabla.getModel()).setRowCount(i);
					for(int j=1;j<md.getColumnCount()+1;j++)
						tabla.setValueAt(rs.getObject(j),i-1, j-1);
					i++;
				}


			}
			rs.close();
			stmt.close();
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
	
	private void mostrarCuotasCliente() {
		
		int i=0;
		String tipo, numero;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			if(tfTipo.getText().isEmpty() || tfNum.getText().isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"Los campos de tipo y número de documento no pueden estar vacíos\n","No se puede seleccionar cliente",JOptionPane.ERROR_MESSAGE);
			else{
				
				tipo = tfTipo.getText().toString();
				numero = tfNum.getText().toString();
				
				String tfQuery = "SELECT tipo_doc, nro_doc FROM Cliente where tipo_doc='"+ tipo +"' and nro_doc='" + numero +"';";

				ResultSet rs= stmt.executeQuery(tfQuery);
				
				if(!rs.next()) 
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El tipo y numero de documento ingresados no corresponden a un cliente de la base de datos\n","Error",JOptionPane.ERROR_MESSAGE);
				else {
					
					btnPagar.setVisible(true);
					btnCancelar.setVisible(true);
					btnConsultar.setVisible(false);
					tfTipo.setEnabled(false);
					tfNum.setEnabled(false);

					rs=stmt.executeQuery(tfQuery);
					
					tfQuery = "SELECT a.nro_pago as NroPago, p.nro_prestamo as NroPrestamo, p.valor_cuota as ValorCuota, a.fecha_venc as FechaVenc\r\n" + 
							"FROM Prestamo p, Pago a\r\n" + 
							"WHERE p.nro_prestamo=a.nro_prestamo and a.fecha_pago is NULL and p.nro_cliente='"+ numero +"';";
					
					rs=stmt.executeQuery(tfQuery);

					ResultSetMetaData md= rs.getMetaData();
					
					JTableHeader header = tabla.getTableHeader();
					tablePane.add(header,BorderLayout.NORTH);
					TableModel bancoModel;
					Object columnNames[]=new Object[md.getColumnCount()];

					while(i<md.getColumnCount()){
						columnNames[i]= new String(md.getColumnName(i+1));
						i++;
					}

					bancoModel = new DefaultTableModel(columnNames,1){
						
						public boolean isCellEditable(int rowIndex, int columnIndex) {
						    return false;  //
						}
					};

					tabla.setModel(bancoModel);
					
					
					i=1;
					//Filas i, Columnas j

					while (rs.next()){

						((DefaultTableModel) tabla.getModel()).setRowCount(i);
						for(int j=1;j<md.getColumnCount()+1;j++)
							tabla.setValueAt(rs.getObject(j),i-1, j-1);
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
	
	private void pagarCuota() {
		
		int i=0;
		int fila;
		String nroPago, nroPres;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			fila = tabla.getSelectedRow();
			if(fila==-1)
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"Debe seleccionar una cuota para pagar\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
			else {
				
				nroPago = tabla.getValueAt(fila, 0).toString();
				nroPres = tabla.getValueAt(fila, 1).toString();
				
				String tfQuery = "\r\n" + 
						"UPDATE pago\r\n" + 
						"SET fecha_pago=CURDATE()\r\n" + 
						"WHERE nro_pago='"+ nroPago +"' and nro_prestamo='"+nroPres+"';";
				
				stmt.execute(tfQuery);
				
				mostrarCuotasCliente();
				
			}
			stmt.close();
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
	
	private class oyentePagar implements ActionListener{

		private JInternalFrame miFrame;

		public oyentePagar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			pagarCuota();
		}
		
	}
	
	private class oyenteCancelar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteCancelar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			mostrarCuotasCliente();
			btnPagar.setVisible(false);
			btnCancelar.setVisible(false);
			btnConsultar.setVisible(true);
			tfTipo.setEnabled(true);
			tfNum.setEnabled(true);
			mostrarDocumentos();
		}
		
	}
	
	private class oyenteSeleccionar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteSeleccionar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			mostrarCuotasCliente();
			
		}
		
	}
}