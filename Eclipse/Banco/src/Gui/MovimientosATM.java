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

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class MovimientosATM extends JInternalFrame {
	
	private JTextField tfDesde;
	private JTextField tfHasta;
	private JTable tabla;
	private TableModel modelo;
	private JScrollPane spTabla;
	private Connection conexionBD;
	private int codCaja;
	


	public MovimientosATM(int codCaja){
		this.codCaja=codCaja;
		initGui();
	}

	private void initGui(){
		
		setClosable(true);
		setBounds(100, 100, 842, 485);
		getContentPane().setLayout(null);
		
		ButtonGroup bg= new ButtonGroup();
		
		JRadioButton rbUltimos = new JRadioButton("\u00DAltimos");
		rbUltimos.setBounds(339, 18, 59, 23);
		getContentPane().add(rbUltimos);
		
		JRadioButton rbPeriodo = new JRadioButton("\u00DAltimos");
		rbPeriodo.setBounds(417, 18, 59, 23);
		getContentPane().add(rbPeriodo);
	
		
		bg.add(rbPeriodo);
		bg.add(rbUltimos);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 45, 826, 1);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 93, 826, 1);
		getContentPane().add(separator_1);
		
		tfDesde = new JTextField();
		tfDesde.setText("Desde");
		tfDesde.setBounds(260, 59, 86, 23);
		getContentPane().add(tfDesde);
		tfDesde.setColumns(10);
		
		tfHasta = new JTextField();
		tfHasta.setText("Hasta");
		tfHasta.setColumns(10);
		tfHasta.setBounds(362, 59, 86, 23);
		getContentPane().add(tfHasta);
		
		JButton btnMostar = new JButton("Mostar");
		btnMostar.setBounds(460, 59, 89, 23);
		getContentPane().add(btnMostar);
		
		tabla= new JTable();
		
		spTabla = new JScrollPane(tabla);
		spTabla.setBounds(10, 115, 806, 329);
		getContentPane().add(spTabla);
		
		spTabla.setEnabled(false);
		spTabla.setVisible(true);
		
	}
	
	
	private void consultarUltimos(){
		
		int ultimas=15;
		int i=0;
		String query="SELECT fecha,hora,tipo,monto FROM trans_cajas_ahorro WHERE cod_caja="+codCaja;
		int saldo;
		String tipo;
		
		try{    

			if(query.isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL es vacía\n","Consulta vacía",JOptionPane.ERROR_MESSAGE);
			else{
				
				conectarBD();
				Statement stmt = this.conexionBD.createStatement();

				ResultSet rs= stmt.executeQuery(query);
				ResultSetMetaData md= rs.getMetaData();


				rs=stmt.executeQuery(query);
				TableModel bancoModel;
				Object columnNames[]=new Object[md.getColumnCount()];

				while(i<md.getColumnCount()){
					columnNames[i]= new String(md.getColumnName(i+1));
					i++;
				}

				modelo = new DefaultTableModel(columnNames,1);
				tabla.setModel(modelo);

				//i=Filas
				//j=Columnas
				i=1;

				while (rs.next() && i<ultimas+1){

					((DefaultTableModel) tabla.getModel()).setRowCount(i);
					for(int j=1;j<md.getColumnCount();j++){

						if(j==3){

							tipo=rs.getString(j);

							if(!tipo.equals("Deposito"))
								saldo= -rs.getInt(4);
							else
								saldo= rs.getInt(4);

							tabla.setValueAt(saldo,i-1, 4);
						}
						else
							tabla.setValueAt(rs.getObject(j),i-1, j-1);

					}

					i++;
				}

				JTableHeader header = tabla.getTableHeader();
				spTabla.add(header,BorderLayout.NORTH);

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
	
	private void consultarPorPeriodo(){
		
		
	}
	
	private class oyenteUltimos implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			spTabla.setEnabled(true);
			spTabla.setVisible(true);
			
			consultarUltimos();
			
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
