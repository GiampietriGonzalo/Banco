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
		setTitle("Clientes morosos");
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

	private void realizarConsulta(){

		int filas=0;
		int i=0;
		String fecha;
		
		try{    
			conectarBD();
			
			Statement stmt = this.conexionBD.createStatement();
			
			java.util.Date dete = new Date();
			String fehca = Fechas.convertirDateAStringDB(dete);
			
			String tfQuery ="SELECT c.nro_cliente, c.tipo_doc, c.nro_doc, c.nombre, c.apellido, p.nro_prestamo, p.monto, p.cant_meses, p.valor_cuota, COUNT(nro_pago) as cuotas_atrasadas\r\n" + 
					"FROM Cliente c, Prestamo p, Pago a\r\n" + 
					"WHERE c.nro_cliente=p.nro_cliente and p.nro_prestamo=a.nro_prestamo and a.fecha_pago is NULL and a.fecha_venc<"+ fehca +" \r\n" + 
					"GROUP BY nro_prestamo having COUNT(nro_pago)>=2;";
			
			ResultSet rs= stmt.executeQuery(tfQuery);
			ResultSetMetaData md= rs.getMetaData();


			rs=stmt.executeQuery(tfQuery);
			TableModel bancoModel;
			Object columnNames[]=new Object[md.getColumnCount()];

			while(i<md.getColumnCount()){
				columnNames[i]= new String(md.getColumnName(i+1));
				i++;
			}

			bancoModel = new DefaultTableModel(columnNames,1);

			tablaMorosos.setModel(bancoModel);

			i=1;
			//Filas i, Columnas j

			while (rs.next()){

				((DefaultTableModel) tablaMorosos.getModel()).setRowCount(i);
				for(int j=1;j<md.getColumnCount()+1;j++){

					if(columnNames[j-1].equals("fecha")){

						fecha= Fechas.convertirDateAString(rs.getDate(j));
						tablaMorosos.setValueAt(fecha,i-1,j-1);
					}
					else
						tablaMorosos.setValueAt(rs.getObject(j),i-1, j-1);   
				}
				i++;
			}

			JTableHeader header = tablaMorosos.getTableHeader();
			tablePane.add(header,BorderLayout.NORTH);

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
