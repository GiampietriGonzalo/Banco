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

public class ListarClientesMorosos extends JInternalFrame {

	private Connection conexionBD = null;
	private JPanel contentPane, tablePane;
	private JTable tablaMorosos;
	private JScrollPane spTable;
	
	public ListarClientesMorosos() {
		
		initGui();
	}
	
	private void initGui() {
		
		setClosable(true);
		setTitle("Clientes morosos");
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

		tablePane= new JPanel();
		tablePane.setForeground(Color.WHITE);
		tablePane.setBackground(Color.DARK_GRAY);
		tablePane.setBounds(10,10,799,285);
		tablePane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tablePane);

		tablaMorosos = new JTable();
		tablaMorosos.setForeground(Color.DARK_GRAY);
		tablaMorosos.setBackground(Color.WHITE);
		tablaMorosos.setBounds(2, 50, 789, 384);
		contentPane.add(tablaMorosos);
		tablaMorosos.setEnabled(false);
		tablaMorosos.setAutoCreateRowSorter(true);

		spTable = new JScrollPane(tablaMorosos);
		spTable.setForeground(Color.WHITE);
		spTable.setBackground(Color.DARK_GRAY);
		spTable.setBounds(0,50,799,400);

		tablePane.add(spTable, BorderLayout.CENTER);
		
		realizarConsulta();
	}

	public void realizarConsulta(){

		int filas=0;
		int i=0;
		String fecha;
		
		try{    
			conectarBD();
			
			Statement stmt = this.conexionBD.createStatement();
			
			String tfQuery="SELECT nro_cliente, tipo_doc, nro_doc, nombre, apellido, nro_prestamo, monto, cant_meses, valor_cuota, COUNT(nro_prestamo) FROM prestamo natural join pago natural join cliente WHERE fecha_pago IS NULL and fecha_venc < CURDATE() GROUP BY nro_prestamo HAVING count(nro_prestamo) > 1;";
			ResultSet rs= stmt.executeQuery(tfQuery);
			ResultSetMetaData md= rs.getMetaData();


			
			TableModel bancoModel;
			
			if(stmt.execute(tfQuery)) {

				Object columnNames[]=new Object[md.getColumnCount()];

				while(i<md.getColumnCount()){
					columnNames[i]= new String(md.getColumnName(i+1));
					i++;
				}

				bancoModel = new DefaultTableModel(columnNames,0);

				tablaMorosos.setModel(bancoModel);

				i=1;
				//Filas i, Columnas j

				rs=stmt.getResultSet();
				
				while (rs.next()){

					((DefaultTableModel) tablaMorosos.getModel()).setRowCount(i);
					for(int j=1;j<md.getColumnCount()+1;j++){

						tablaMorosos.setValueAt(rs.getObject(j),i-1, j-1);   
					}
					i++;
				}

				JTableHeader header = tablaMorosos.getTableHeader();
				tablePane.add(header,BorderLayout.NORTH);
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
}
