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

public class CrearPrestamo extends JInternalFrame {
	
	private Connection conexionBD = null;
	private JPanel contentPane, tablePane;
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
		
		setClosable(true);
		setTitle("Crear prestamo");
		setResizable(false);

		setForeground(Color.DARK_GRAY);
		setBackground(new Color(211, 211, 211));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 70, 835, 527);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBackground(new Color(211, 211, 211));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(false);
		this.setEnabled(false);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.DARK_GRAY);
		btnConsultar.setBackground(Color.LIGHT_GRAY);
		btnConsultar.setBounds(318, 34, 142, 23);
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
		etTipo.setForeground(Color.DARK_GRAY);
		etTipo.setBackground(Color.WHITE);
		etTipo.setBounds(10, 10, 142, 10);
		etTipo.setText("Tipo documento");
		contentPane.add(etTipo);
		
		tfTipo = new JTextField(10);
		tfTipo.setForeground(Color.DARK_GRAY);
		tfTipo.setBackground(Color.WHITE);
		tfTipo.setBounds(10, 20, 142, 50);
		contentPane.add(tfTipo);

		etNum = new JLabel();
		etNum.setForeground(Color.DARK_GRAY);
		etNum.setBackground(Color.WHITE);
		etNum.setBounds(160, 10, 142, 10);
		etNum.setText("N�mero documento");
		contentPane.add(etNum);
		
		tfNum = new JTextField(10);
		tfNum.setForeground(Color.DARK_GRAY);
		tfNum.setBackground(Color.WHITE);
		tfNum.setBounds(160, 20, 142, 50);
		contentPane.add(tfNum);
		
		etMonto = new JLabel();
		etMonto.setForeground(Color.DARK_GRAY);
		etMonto.setBackground(Color.WHITE);
		etMonto.setBounds(318, 100, 142, 20);
		etMonto.setText("Monto prestamo");
		contentPane.add(etMonto);
		etMonto.setVisible(false);
		
		tfMonto = new JTextField(10);
		tfMonto.setForeground(Color.DARK_GRAY);
		tfMonto.setBackground(Color.WHITE);
		tfMonto.setBounds(318, 120, 142, 50);
		contentPane.add(tfMonto);
		tfMonto.setVisible(false);
		
		etMeses = new JLabel();
		etMeses.setForeground(Color.DARK_GRAY);
		etMeses.setBackground(Color.WHITE);
		etMeses.setBounds(318, 199, 142, 20);
		etMeses.setText("Meses prestamo");
		contentPane.add(etMeses);
		etMeses.setVisible(false);
		
		tfMeses = new JTextField(10);
		tfMeses.setForeground(Color.DARK_GRAY);
		tfMeses.setBackground(Color.WHITE);
		tfMeses.setBounds(318, 220, 142, 50);
		contentPane.add(tfMeses);
		tfMeses.setVisible(false);
		
		btnCrear = new JButton("Crear Prestamo");
		btnCrear.setForeground(Color.DARK_GRAY);
		btnCrear.setBackground(Color.LIGHT_GRAY);
		btnCrear.setBounds(318, 281, 142, 23);
		contentPane.add(btnCrear);
		btnCrear.addActionListener(new oyenteCrear(this));
		btnCrear.setVisible(false);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.DARK_GRAY);
		btnCancelar.setBackground(Color.LIGHT_GRAY);
		btnCancelar.setBounds(318, 315, 142, 23);
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
		
		String query;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			query = "SELECT tipo_doc, nro_doc \r\n" + 
					"FROM Cliente \r\n" + 
					"WHERE tipo_doc='"+ tfTipo.getText().toString() +"' and nro_doc='" + tfNum.getText().toString()+"';";

			ResultSet rs= stmt.executeQuery(query);
			
			if(!rs.next())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"No se encontro un cliente asociado al tipo y n�mero de documento ingresados\n","No se puede crear prestamo",JOptionPane.ERROR_MESSAGE);
			else {
				
				
				query= "SELECT nro_prestamo FROM prestamo natural join cliente natural join pago WHERE fecha_pago IS NULL and nro_doc = "+tfNum.getText().toString()+";";
				
				rs = stmt.executeQuery(query);
				
				if(!rs.next())
					valido = true;
				else { 
					//EL CLIENTE TIENE UN PRESTAMO, SE CONTROLA SI EL PRESTAMO ESTA ACTIVO, ES DECIR, SI HAY CUOTAS NO PAGAS.
				
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"ERROR: El cliente ya tiene un pr�stamo activo\n","No se puede crear prestamo",JOptionPane.ERROR_MESSAGE);
				
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
		return valido;
	}
	
	private boolean crearPrestamo() {
		
		int numeroC;
		int monto, periodo;
		Double tasa, interes, valorCuota;
		boolean exito=false;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			periodo= Integer.parseInt(tfMeses.getText().toString());
			monto = Integer.parseInt(tfMonto.getText().toString());
			
			String tfQuery = "SELECT tasa FROM tasa_prestamo WHERE '"+monto+"'BETWEEN monto_inf and monto_sup and periodo='"+periodo+"';";
			
			
			ResultSet rs= stmt.executeQuery(tfQuery);

			
			if(!rs.next())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"ERROR: Los datos ingresados no son validos\n","No se puede crear prestamo",JOptionPane.ERROR_MESSAGE);
			else{
				
			
				tfQuery = "";
				
				numeroC = Integer.parseInt(tfNum.getText());
				
				tasa = Double.parseDouble(rs.getString("tasa"));
				
				interes = (monto*tasa*periodo)/1200;
				valorCuota = (monto+interes)/periodo;
				
				java.util.Date dete = new Date();
				String fehca = Fechas.convertirDateAStringDB(dete);
				
				
				tfQuery = "INSERT INTO prestamo(fecha, cant_meses, monto, tasa_interes, interes, valor_cuota, legajo, nro_cliente)\r\n" + 
						"VALUES ('"+fehca+"','"+periodo+"','"+monto+"',"+tasa+","+interes+",'"+valorCuota+"','"+legajo+"','"+numeroC+"');";
				
				
				stmt.execute(tfQuery);
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El prestamo fue creado exitosamente\n","Prestamo creado",JOptionPane.INFORMATION_MESSAGE);
				exito=true;
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
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), ex.getMessage() + "ERROR: No se pudo crear el pr�stamo\n","Error al ejecutar la consulta.",JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException ex){
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "ERROR: S�lo se admiten n�meros positivos" + "\n","Prestamo abortado",JOptionPane.ERROR_MESSAGE);
		}
		
		
		return exito;
	}	
	
	private void conectarBD(){

		try{
			String driver ="com.mysql.cj.jdbc.Driver";
			String servidor = "localhost:3306";
			String baseDatos = "banco";
			String usuario = "empleado";
			String clave = "empleado";
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
				etMonto.setVisible(true);
				etMeses.setVisible(true);
				tfMonto.setVisible(true);
				tfMeses.setVisible(true);
				btnCrear.setVisible(true);
				btnCancelar.setVisible(true);
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
			if(crearPrestamo()) {
				etMonto.setVisible(false);
				etMeses.setVisible(false);
				tfMonto.setVisible(false);
				tfMeses.setVisible(false);
				btnCrear.setVisible(false);
				btnCancelar.setVisible(false);
				tfMeses.setText("");
				tfMonto.setText("");
				tfNum.setEnabled(true);
				tfTipo.setEnabled(true);
				tfNum.setText("");
				tfTipo.setText("");
				btnConsultar.setEnabled(true);
			}
		}
	}
	
	private class oyenteCancelar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteCancelar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			etMonto.setVisible(false);
			etMeses.setVisible(false);
			tfMonto.setVisible(false);
			tfMeses.setVisible(false);
			btnCrear.setVisible(false);
			btnCancelar.setVisible(false);
			tfMeses.setText("");
			tfMonto.setText("");
			tfNum.setEnabled(true);
			tfTipo.setEnabled(true);
			tfNum.setText("");
			tfTipo.setText("");
			btnConsultar.setEnabled(true);
		}
	}
}
