package Gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import Logica.Fechas;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.Font;

public class ConsultasAdmin extends JInternalFrame {

	private JPanel contentPane;
	private JTable table;
	private Connection conexionBD = null;
	private JButton btnConsultar;
	private JButton btnLimpiar;
	private JTextField tfQuery;
	private JPanel tablePane;
	private JScrollPane spTable;


	public ConsultasAdmin() {
		setBorder(null);
		setResizable(false);
		setTitle("Consultas");
		initGui();
	}


	private void initGui() {

		setForeground(new Color(211, 211, 211));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 835, 527);
		contentPane = new JPanel();
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(false);
		this.setEnabled(false);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setForeground(Color.DARK_GRAY);
		btnLimpiar.setBackground(new Color(211, 211, 211));
		btnLimpiar.setBounds(720, 86, 89, 23);
		contentPane.add(btnLimpiar);
		btnLimpiar.addActionListener(new oyenteBorrar());

		btnConsultar = new JButton("Consultar");
		btnConsultar.setForeground(Color.DARK_GRAY);
		btnConsultar.setBackground(new Color(211, 211, 211));
		btnConsultar.setBounds(590, 86, 103, 23);
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(new oyenteConsultar(this));

		tablePane= new JPanel();
		tablePane.setForeground(Color.DARK_GRAY);
		tablePane.setBackground(new Color(211, 211, 211));
		tablePane.setBounds(10,121,799,362);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);

		table = new JTable();
		table.setForeground(Color.DARK_GRAY);
		table.setBackground(Color.WHITE);
		table.setBounds(2, 50, 789, 384);
		//contentPane.add(table);
		table.setEnabled(false);

		spTable = new JScrollPane(table);
		spTable.setForeground(Color.DARK_GRAY);
		spTable.setBackground(new Color(211, 211, 211));
		spTable.setBounds(0,50,799,400);

		tablePane.add(spTable, BorderLayout.CENTER);

		tfQuery = new JTextField(10);
		tfQuery.setFont(new Font("Dialog", Font.BOLD, 14));
		tfQuery.setForeground(Color.DARK_GRAY);
		tfQuery.setBounds(10, 11, 799, 63);
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

	private void realizarConsulta(){

		int filas=0;
		int i=0;
		String fecha;
		boolean resConsulta=false;
		Statement stmt;
		
		try{    
			
			if(tfQuery.getText().isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL es vacia\n","Consulta vacia",JOptionPane.ERROR_MESSAGE);
			else{
				conectarBD();
				
				stmt = this.conexionBD.createStatement();

				if(noEsConsulta(tfQuery.getText().toLowerCase())){
					resConsulta= stmt.execute(tfQuery.getText());	
					if(!resConsulta)
						JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL ha sido exitosa\n","Consulta exitosa",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL ha fallado\n","Error en la consulta",JOptionPane.ERROR_MESSAGE);
					
					tfQuery.setText("");
					table.setModel(new DefaultTableModel());
				}
				else{

					//ResultSet rs= stmt.executeQuery(tfQuery.getText());

					ResultSet rs;
					
					if(stmt.execute(tfQuery.getText())){
						rs=stmt.getResultSet();

						ResultSetMetaData md= rs.getMetaData();
						rs=stmt.executeQuery(tfQuery.getText());
						TableModel bancoModel;
						Object columnNames[]=new Object[md.getColumnCount()];

						while(i<md.getColumnCount()){
							columnNames[i]= new String(md.getColumnName(i+1));
							i++;
						}

						bancoModel = new DefaultTableModel(columnNames,1);

						table.setModel(bancoModel);

						i=1;
						//Filas i, Columnas j

						while (rs.next()){

							((DefaultTableModel) table.getModel()).setRowCount(i);
							for(int j=1;j<md.getColumnCount()+1;j++){

								if(((String)columnNames[j-1]).trim().toLowerCase().equals("fecha")){

									fecha= rs.getString(j);
									table.setValueAt(Fechas.acomodarFecha(fecha),i-1,j-1);
								}
								else
									if(((String)columnNames[j-1]).trim().toLowerCase().equals("hora"))
										table.setValueAt(rs.getString(j),i-1,j-1);
									else
										table.setValueAt(rs.getObject(j),i-1, j-1);   
							}
							i++;
						}



						JTableHeader header = table.getTableHeader();
						tablePane.add(header,BorderLayout.NORTH);

						rs.close();
					}
					
					stmt.close();
				}
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

		private JInternalFrame miFrame;

		public oyenteConsultar(JInternalFrame miFrame) {
			this.miFrame=miFrame;
		}

		public void actionPerformed(ActionEvent arg0) {
			realizarConsulta();
		}
		
	}

	private class oyenteBorrar implements ActionListener{

		public void actionPerformed(ActionEvent arg) {

			tfQuery.setText("");
			table.setModel(new DefaultTableModel());

		}

	}
	
	private boolean noEsConsulta(String query){
		
		boolean es=false;
		
		if(query.contains("update") || query.contains("insert") || query.contains("delete") || query.contains("create"))
			es=true;
		
		return es;
	}
}
