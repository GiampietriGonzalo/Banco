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

public class PagarCuotas extends JInternalFrame {
	
	private Connection conexionBD = null;
	private JPanel contentPane, tablePane;
	private JButton btnCrear;
	private JTextField tfTipo, tfNum;
	private JTable tablaDoc, tablaCuotas;
	private JScrollPane spTable;
	
	public PagarCuotas() {
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

		btnCrear = new JButton("Consultar");
		btnCrear.setForeground(Color.WHITE);
		btnCrear.setBackground(Color.DARK_GRAY);
		btnCrear.setBounds(320, 10, 89, 23);
		contentPane.add(btnCrear);
		btnCrear.addActionListener(new oyenteSeleccionar(this));
		
		tablePane= new JPanel();
		tablePane.setForeground(Color.WHITE);
		tablePane.setBackground(Color.DARK_GRAY);
		tablePane.setBounds(10,100,285,285);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);
		
		tablaDoc = new JTable();
		tablaDoc.setForeground(Color.DARK_GRAY);
		tablaDoc.setBackground(Color.WHITE);
		tablaDoc.setBounds(2, 10, 789, 384);
		contentPane.add(tablaDoc);
		tablaDoc.setEnabled(false);
		
		tablaCuotas = new JTable();
		tablaCuotas.setForeground(Color.DARK_GRAY);
		tablaCuotas.setBackground(Color.WHITE);
		tablaCuotas.setBounds(300, 100, 285, 285);
		contentPane.add(tablaCuotas);
		tablaCuotas.setEnabled(false);

		spTable = new JScrollPane(tablaDoc);
		spTable.setForeground(Color.WHITE);
		spTable.setBackground(Color.DARK_GRAY);
		spTable.setBounds(0,50,799,400);

		tablePane.add(spTable, BorderLayout.CENTER);
		
		tfTipo = new JTextField(10);
		tfTipo.setForeground(Color.WHITE);
		tfTipo.setBackground(Color.DARK_GRAY);
		tfTipo.setBounds(10, 10, 142, 50);
		tfTipo.addFocusListener(new focusTipo(tfTipo));
		contentPane.add(tfTipo);

		tfNum = new JTextField(10);
		tfNum.setForeground(Color.WHITE);
		tfNum.setBackground(Color.DARK_GRAY);
		tfNum.setBounds(160, 10, 142, 50);
		tfNum.addFocusListener(new focusNum(tfNum));
		contentPane.add(tfNum);
		
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
	
	private void mostrarCuotasCliente() {
		
		int i=0;
		
		try{    

			conectarBD();
			Statement stmt = this.conexionBD.createStatement();
			
			if(tfTipo.getText().isEmpty() || tfNum.getText().isEmpty()) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"Los campos de tipo y número de documento no pueden estar vacíos\n","No se puede seleccionar cliente",JOptionPane.ERROR_MESSAGE);
			}
			else {
				String tipo = '"'+tfTipo.getText().toString()+'"';
				//numero valor fecha vencimieno cuota impaga
				String tfQuery = "SELECT tipo_doc, nro_doc FROM Cliente where tipo_doc="+ tipo +" and nro_doc=" + tfNum.getText().toString();

				ResultSet rs= stmt.executeQuery(tfQuery);
				
				if(!rs.next())
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"El cliente correspondiente al tipo y número de documento ingresados ya tiene un préstamo activo\n","No se puede crear préstamo",JOptionPane.ERROR_MESSAGE);
				else {
					ResultSetMetaData md= rs.getMetaData();

					rs=stmt.executeQuery(tfQuery);

					JTableHeader header = tablaCuotas.getTableHeader();
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

						((DefaultTableModel) tablaCuotas.getModel()).setRowCount(i);
						for(int j=1;j<md.getColumnCount()+1;j++)
							tablaCuotas.setValueAt(rs.getObject(j),i-1, j-1);
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
	
	private class oyenteSeleccionar implements ActionListener{

		private JInternalFrame miFrame;

		public oyenteSeleccionar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			mostrarCuotasCliente();
		}
		
	}
	
	private class focusTipo implements FocusListener{

		private JTextField miTField;
		
		public focusTipo(JTextField miTField) {
			this.miTField=miTField;
			miTField.setText("Tipo documento");
		}
		
		@Override
		public void focusGained(FocusEvent arg0) {
			miTField.setText("");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			miTField.setText(miTField.getText());
		}
	}

	private class focusNum implements FocusListener{

		private JTextField miTField;
		
		public focusNum(JTextField miTField) {
			this.miTField=miTField;
			miTField.setText("Número documento");
		}
		
		@Override
		public void focusGained(FocusEvent arg0) {
			miTField.setText("");
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			miTField.setText(miTField.getText());
		}
	}
}