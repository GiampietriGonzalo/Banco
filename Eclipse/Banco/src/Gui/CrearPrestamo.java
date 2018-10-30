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

public class CrearPrestamo extends JInternalFrame {
	
	private Connection conexionBD = null;
	private JPanel contentPane, tablePane, prestamoPane;
	private JButton btnCrear, btnConsultar, btnCancelar;
	private JTextField tfTipo, tfNum, tfMonto, tfMeses;
	private JTable tablaDoc;
	private JScrollPane spTable;
	private JLabel etMeses, etMonto, etTipo, etNum;
	private String legajo;
	
	public CrearPrestamo(String l) {
		legajo= l;
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
		btnConsultar.setBounds(350, 10, 89, 23);
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(new oyenteConsultar(this));
		
		tablePane= new JPanel();
		tablePane.setForeground(Color.WHITE);
		tablePane.setBackground(Color.DARK_GRAY);
		tablePane.setBounds(10,100,285,285);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);
		
		tablaDoc = new JTable();
		tablaDoc.setForeground(Color.DARK_GRAY);
		tablaDoc.setBackground(Color.WHITE);
		tablaDoc.setBounds(10, 100, 200, 200);
		contentPane.add(tablaDoc);
		tablaDoc.setEnabled(false);

		spTable = new JScrollPane(tablaDoc);
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
		etNum.setText("N�mero documento");
		contentPane.add(etNum);
		
		tfNum = new JTextField(10);
		tfNum.setForeground(Color.WHITE);
		tfNum.setBackground(Color.DARK_GRAY);
		tfNum.setBounds(160, 20, 142, 50);
		contentPane.add(tfNum);
		
		prestamoPane= new JPanel();
		prestamoPane.setForeground(Color.WHITE);
		prestamoPane.setBackground(Color.DARK_GRAY);
		prestamoPane.setBounds(300,100,285,285);
		prestamoPane.setLayout(new BorderLayout(0, 0));
		prestamoPane.setVisible(false);
		contentPane.add(prestamoPane);
		
		etMonto = new JLabel();
		etMonto.setBounds(400, 100, 142, 20);
		etMonto.setEnabled(false);
		etMonto.setText("Monto prestamo");
		prestamoPane.add(etMonto);
		
		tfMonto = new JTextField(10);
		tfMonto.setForeground(Color.WHITE);
		tfMonto.setBackground(Color.DARK_GRAY);
		tfMonto.setBounds(400, 120, 142, 50);
		prestamoPane.add(tfMonto);
		
		etMeses = new JLabel();
		etMeses.setBounds(400, 200, 142, 20);
		etMeses.setEnabled(false);
		etMeses.setText("Meses prestamo");
		prestamoPane.add(etMeses);
		
		tfMeses = new JTextField(10);
		tfMeses.setForeground(Color.WHITE);
		tfMeses.setBackground(Color.DARK_GRAY);
		tfMeses.setBounds(400, 220, 142, 50);
		prestamoPane.add(tfMeses);
		
		btnCrear = new JButton("Crear Prestamo");
		btnCrear.setForeground(Color.WHITE);
		btnCrear.setBackground(Color.DARK_GRAY);
		btnCrear.setBounds(400, 300, 142, 23);
		prestamoPane.add(btnCrear);
		btnCrear.addActionListener(new oyenteCrear(this));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(Color.DARK_GRAY);
		btnCancelar.setBounds(400, 300, 142, 23);
		prestamoPane.add(btnCancelar);
		btnCrear.addActionListener(new oyenteCancelar(this));
		
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

				JTableHeader header = tablaDoc.getTableHeader();
				tablePane.add(header,BorderLayout.NORTH);
				TableModel bancoModel;
				Object columnNames[]=new Object[md.getColumnCount()];

				while(i<md.getColumnCount()){
					columnNames[i]= new String(md.getColumnName(i+1));
					i++;
				}

				bancoModel = new DefaultTableModel(columnNames,1);

				tablaDoc.setModel(bancoModel);

				i=1;
				//Filas i, Columnas j

				while (rs.next()){

					((DefaultTableModel) tablaDoc.getModel()).setRowCount(i);
					for(int j=1;j<md.getColumnCount()+1;j++)
						tablaDoc.setValueAt(rs.getObject(j),i-1, j-1);
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
	
	private boolean clienteValidoPrestamo() {
		
		boolean valido = false; 
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			String tipo = '"'+tfTipo.getText().toString()+'"';
			String tfQuery = "SELECT tipo_doc, nro_doc FROM Cliente WHERE tipo_doc="+ tipo +" and nro_doc=" + tfNum.getText().toString();

			ResultSet rs= stmt.executeQuery(tfQuery);
			
			if(!rs.next())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"No se encontr� un cliente asociado al tipo y n�mero de documento ingresados\n","No se puede crear pr�stamo",JOptionPane.ERROR_MESSAGE);
			else {
				
				tfQuery = "SELECT nro_prestamo\r\n" + 
						"FROM Prestamo p, Cliente c\r\n" + 
						"WHERE p.nro_cliente=c.nro_cliente and c.nro_doc="+ tfNum.getText().toString() +";";
				
				rs = stmt.executeQuery(tfQuery);
				
				if(rs.next())
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El cliente correspondiente al tipo y n�mero de documento ingresados ya tiene un pr�stamo activo\n","No se puede crear pr�stamo",JOptionPane.ERROR_MESSAGE);
				else 
					valido = true;
				
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
		return valido;
	}
	
	private void crearPrestamo() {
		
		String monto, periodo, numeroC, tipoC;
		int valorCuota, interes;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			periodo= tfMeses.getText().toString();

			String tfQuery = "SELECT monto_sup, tasa_interes\r\n" + 
					"FROM tasa_prestamo\r\n" + 
					"WHERE periodo="+ periodo +";";
			
			ResultSet rs= stmt.executeQuery(tfQuery);
			
			
			if(!rs.next())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El periodo ingresado no es valido\n","No se puede crear pr�stamo",JOptionPane.ERROR_MESSAGE);
			else {
				
				//verificar si el monto es menor al maximo
				
				monto = tfMonto.getText().toString();
				
				if(Integer.parseInt(monto)<=Integer.parseInt(rs.getString("monto_sup")))
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El monto ingresado no es valido\n","No se puede crear pr�stamo",JOptionPane.ERROR_MESSAGE);
				else {
					
					numeroC = tfNum.getText().toString();
					tipoC = tfTipo.getText().toString();
					
					interes = (Integer.parseInt(monto)*Integer.parseInt(rs.getString("tasa_interes"))*Integer.parseInt(periodo))/1200;
					valorCuota = (Integer.parseInt(monto)+interes)/Integer.parseInt(periodo);
					
					tfQuery = "INSERT INTO prestamo(fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente)\r\n" + 
							"VALUES ("+1+","+periodo+","+monto+","+rs.getString("tasa_interes")+","+interes+","+valorCuota+","+legajo+","+numeroC+");";

					rs= stmt.executeQuery(tfQuery);
					
					ResultSetMetaData md= rs.getMetaData();

					rs=stmt.executeQuery(tfQuery);
					
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
	
	private void conectarBD(){

		try{
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "banco";
			String usuario = "admin";
			String clave = "admin";
			String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";

			//establece una conexi�n con la  B.D. "banco"    
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
			if(clienteValidoPrestamo()) {
				prestamoPane.setVisible(true);
				tfNum.setEnabled(false);
				tfTipo.setEnabled(false);
				btnConsultar.setEnabled(false);
			}
		}
	}
	
	private class oyenteCrear implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteCrear(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			crearPrestamo();
		}
	}
	
	private class oyenteCancelar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteCancelar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			prestamoPane.setVisible(false);
			tfNum.setEnabled(true);
			tfTipo.setEnabled(true);
			btnConsultar.setEnabled(true);
		}
	}
}
