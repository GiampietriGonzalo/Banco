package Gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
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
import Logica.Fechas;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JPanel;

public class MovimientosATM extends JInternalFrame {
	
	private JTextField tfDesde;
	private JTextField tfHasta;
	private JButton btnMostrar;
	private JTable tabla;
	private TableModel modelo;
	private JScrollPane spTabla;
	private Connection conexionBD;
	private int codCaja;
	private JPanel panelTabla;
	


	public MovimientosATM(int codCaja){
		setBorder(null);
		setTitle("Movimientos");
		
		this.codCaja=codCaja;
		initGui();
	}

	private void initGui(){
		
		setClosable(true);
		setBounds(0,0, 842, 485);
		getContentPane().setLayout(null);
		
		getContentPane().setForeground(Color.DARK_GRAY);
		setForeground(Color.DARK_GRAY);
		
		ButtonGroup bg= new ButtonGroup();
		
		JRadioButton rbUltimos = new JRadioButton("\u00DAltimos");
		rbUltimos.setForeground(Color.DARK_GRAY);
		rbUltimos.setBounds(273, 15, 86, 23);
		getContentPane().add(rbUltimos);
		
		JRadioButton rbPeriodo = new JRadioButton("Por per\u00EDodo");
		rbPeriodo.setForeground(Color.DARK_GRAY);
		rbPeriodo.setBounds(375, 15, 123, 23);
		getContentPane().add(rbPeriodo);
		
		rbPeriodo.addActionListener(new oyentePeriodo());
		rbUltimos.addActionListener(new oyenteUltimos());
		//rbPeriodo.addMouseMotionListener(l);
		
		bg.add(rbPeriodo);
		bg.add(rbUltimos);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 45, 826, 1);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 93, 826, 1);
		getContentPane().add(separator_1);
		
		tfDesde = new JTextField();
		tfDesde.setEnabled(false);
		tfDesde.setForeground(Color.DARK_GRAY);
		tfDesde.setText("Desde");
		tfDesde.setBounds(260, 59, 86, 23);
		getContentPane().add(tfDesde);
		tfDesde.setColumns(10);
		
		tfHasta = new JTextField();
		tfHasta.setEnabled(false);
		tfHasta.setForeground(Color.DARK_GRAY);
		tfHasta.setText("Hasta");
		tfHasta.setColumns(10);
		tfHasta.setBounds(362, 59, 86, 23);
		getContentPane().add(tfHasta);
		
		btnMostrar = new JButton("Mostar");
		btnMostrar.setEnabled(false);
		btnMostrar.setForeground(Color.DARK_GRAY);
		btnMostrar.setBackground(new Color(211, 211, 211));
		btnMostrar.setBounds(460, 59, 89, 23);
		btnMostrar.addActionListener(new oyenteMostrar(this));
		getContentPane().add(btnMostrar);
		
		panelTabla = new JPanel();
		panelTabla.setForeground(Color.WHITE);
		panelTabla.setBackground(Color.DARK_GRAY);
		panelTabla.setBounds(82, 105, 655, 339);
		getContentPane().add(panelTabla);
		panelTabla.setLayout(new BorderLayout(0, 0));
		
		tabla= new JTable();
		tabla.setEnabled(false);
		tabla.setOpaque(false);
		tabla.setForeground(Color.WHITE);
		tabla.setBackground(Color.DARK_GRAY);
		
		spTabla = new JScrollPane(tabla);
		spTabla.setOpaque(false);
		panelTabla.add(spTabla);
		spTabla.setForeground(Color.WHITE);
		spTabla.setBackground(Color.DARK_GRAY);
		
		spTabla.setEnabled(false);
		spTabla.setVisible(true);
		
	}

	
	private void consultar(String query){
		
		int ultimas=15;
		int i=0;
		int saldo;
		String tipo;

		try{    
			if(query.isEmpty())
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),"La consulta SQL es vacia\n","Consulta vacia",JOptionPane.ERROR_MESSAGE);
			else{

				conectarBD();
				Statement stmt = this.conexionBD.createStatement();
				ResultSet rs= stmt.executeQuery(query);
				ResultSetMetaData md= rs.getMetaData();
				rs=stmt.executeQuery(query);
				Object columnNames[]=new Object[md.getColumnCount()];

				while(i<md.getColumnCount()){
					columnNames[i]= new String(md.getColumnName(i+1));
					i++;
				}

				modelo = new DefaultTableModel(columnNames,1);
				tabla.setModel(modelo);

				i=1; //i=Filas j=Columnas
				String fecha;
				while (rs.next() && i<ultimas+1){

					((DefaultTableModel) tabla.getModel()).setRowCount(i);
					for(int j=1;j<md.getColumnCount();j++){

						tabla.setValueAt(rs.getObject(j),i-1, j-1);

						if(j==1){
							fecha= Fechas.convertirDateAString(rs.getDate(1));
							tabla.setValueAt(fecha,i-1,0);
						}

						if(j==3){

							tipo=rs.getString(j);

							if(!tipo.equals("Deposito"))
								saldo= -rs.getInt(4);
							else
								saldo= rs.getInt(4);

							tabla.setValueAt(saldo,i-1, 3);
						}
					}

					i++;
				}
				JTableHeader header = tabla.getTableHeader();
				panelTabla.add(header,BorderLayout.NORTH);

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
	
	private class oyenteUltimos implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0){
			
			String query="SELECT fecha,hora,tipo,monto FROM trans_cajas_ahorro WHERE cod_caja="+codCaja+" ORDER BY fecha DESC, hora DESC";
			spTabla.setEnabled(true);
			spTabla.setVisible(true);
			
			tfHasta.setEnabled(false);
			tfDesde.setEnabled(false);
			btnMostrar.setEnabled(false);
			
			tfHasta.setText("Hasta");
			tfDesde.setText("Desde");
			
			consultar(query);
			
		}

	}
	
	
	private class oyentePeriodo implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			spTabla.setEnabled(true);
			spTabla.setVisible(true);

			tfHasta.setEnabled(true);
			tfDesde.setEnabled(true);
			btnMostrar.setEnabled(true);
			
			tabla.setModel(new DefaultTableModel());


		}

	}
	
	private class oyenteMostrar implements ActionListener{

		private JInternalFrame miFrame;
		
		public oyenteMostrar(JInternalFrame miFrame){
			this.miFrame=miFrame;
		}
		
		public void actionPerformed(ActionEvent arg0){
			
			String query;
			Date hasta;
			Date desde;
			
			if(Fechas.validar(tfDesde.getText()) && Fechas.validar(tfHasta.getText())){
				
				desde= Fechas.convertirStringADateSQL(tfDesde.getText());
				hasta= Fechas.convertirStringADateSQL(tfHasta.getText());
				query="SELECT fecha, hora, tipo, monto FROM tarjeta NATURAL JOIN trans_cajas_ahorro AS T WHERE T.cod_caja="+codCaja+" AND fecha BETWEEN '"+Fechas.convertirDateAStringDB(desde)+"' AND '"+Fechas.convertirDateAStringDB(hasta)+"' ORDER BY fecha DESC, hora DESC";

				consultar(query);
			}
				
			else
				JOptionPane.showMessageDialog(miFrame,"Al menos una de las fechas es incorrecta.\n" ,"Error-Fechas",JOptionPane.ERROR_MESSAGE);
			
			
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

			//establece una conexion con la  B.D. "banco"    
			conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);

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
