package batallas;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import javax.swing.text.MaskFormatter;


@SuppressWarnings("serial")
public class VentanaBarcos extends javax.swing.JInternalFrame 
{
   private JPanel pnlFiltroNombre;
   private JScrollPane scrTabla;
   private JTextField txtNombre;
   private JTable tabla;
   private JLabel lblNombre;
   
   protected Connection conexionBD = null;

   public VentanaBarcos() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try {
         this.setPreferredSize(new java.awt.Dimension(640, 480));
         this.setBounds(0, 0, 640, 480);
         this.setTitle("Búsqueda de Barcos (Utilizando JTable)");
         this.setClosable(true);
         this.setResizable(true);
         this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
         this.setMaximizable(true);
         BorderLayout thisLayout = new BorderLayout();
         this.setVisible(true);
         getContentPane().setLayout(thisLayout);
         this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
         {
            pnlFiltroNombre = new JPanel();
            getContentPane().add(pnlFiltroNombre, BorderLayout.NORTH);
            {
               lblNombre = new JLabel();
               pnlFiltroNombre.add(lblNombre);
               lblNombre.setText("Búsqueda incremental del nombre");
            }
            {
               txtNombre = new JTextField();               
               pnlFiltroNombre.add(txtNombre);
               txtNombre.setColumns(30);
               txtNombre.addCaretListener(new CaretListener() {
                  public void caretUpdate(CaretEvent evt) {
                     txtNombreCaretUpdate(evt);
                  }
               });
            }
         }
         {  //se crea un JScrollPane para poder mostrar la tabla en su interior 
            scrTabla = new JScrollPane();
            getContentPane().add(scrTabla, BorderLayout.CENTER);
            
            {  //modelo de la tabla donde se almacenaran las tuplas 
               TableModel BarcosModel =  // se crea un modelo de tabla BarcosModel 
                  new DefaultTableModel  // extendiendo el modelo DefalutTableModel
                  (
                     new String[][] {},
                     new String[] {"Nombre", "Id", "Capitán"}
                  )
                  {                      // con una clase anónima 
            	     // define la clase java asociada a cada columna de la tabla
            	     Class[] types = new Class[] {java.lang.String.class, 
            	    		 java.lang.Integer.class, java.lang.String.class };
            	    // define si una columna es editable
                     boolean[] canEdit = new boolean[] { false, false, false };
                      
                    // recupera la clase java de cada columna de la tabla
                     public Class getColumnClass(int columnIndex) 
                     {
                        return types[columnIndex];
                     }
                   // determina si una celda es editable
                     public boolean isCellEditable(int rowIndex, int columnIndex) 
                     {
                        return canEdit[columnIndex];
                     }
                  };
               tabla = new JTable(); // Crea una tabla
               scrTabla.setViewportView(tabla); //setea la tabla dentro del JScrollPane srcTabla               
               tabla.setModel(BarcosModel); // setea el modelo de la tabla  
               tabla.setAutoCreateRowSorter(true); // activa el ordenamiento por columnas, para
                                                   // que se ordene al hacer click en una columna
            }
         }
         pack();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void thisComponentShown(ComponentEvent evt) 
   {
      this.conectarBD();
      this.refrescarTabla();
   }
   
   private void thisComponentHidden(ComponentEvent evt) 
   {
      this.desconectarBD();
   }

   private void txtNombreCaretUpdate(CaretEvent evt) 
   {
      this.refrescarTabla();
   }

   private void conectarBD()
   {
      if (this.conexionBD == null)
      { 
         try
         {  // Se carga y registra el driver JDBC de MySQL  
		    // no es necesario para versiones de jdbc posteriores a 4.0 
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         }
         catch (Exception ex)
         {  
            System.out.println(ex.getMessage());
         }
     
         try
         {  //se genera el string que define los datos de la conección 
            String servidor = "localhost:3306";
            String baseDatos = "batallas";
            String usuario = "admin_batallas";
            String clave = "pwadmin";
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos +"?serverTimezone=UTC";
            //se intenta establecer la conección
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
         }
         catch (SQLException ex)
         {
            JOptionPane.showMessageDialog(this,
                                          "Se produjo un error al intentar conectarse a la base de datos.\n" + 
                                           ex.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
      }
   }

   private void desconectarBD()
   {
      if (this.conexionBD != null)
      {
         try
         {
            this.conexionBD.close();
            this.conexionBD = null;
         }
         catch (SQLException ex)
         {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
         }
      }
   }

   private void refrescarTabla()
   {
      try
      {
         // se crea una sentencia o comando jdbc para realizar la consulta 
    	 // a partir de la coneccion establecida (conexionBD)
         Statement stmt = this.conexionBD.createStatement();

         // se prepara el string SQL de la consulta
         String sql = "SELECT nombre_barco, id, capitan " + 
                      "FROM barcos " +
                      "WHERE LOWER(nombre_barco) LIKE '%" + this.txtNombre.getText().trim().toLowerCase() + "%' " +
                      "ORDER BY nombre_barco";

         // se ejecuta la sentencia y se recibe un resultset
         ResultSet rs = stmt.executeQuery(sql);
         // se recorre el resulset y se actualiza la tabla en pantalla
         ((DefaultTableModel) this.tabla.getModel()).setRowCount(0);
         int i = 0;
         while (rs.next())
         {
        	 // agrega una fila al modelo de la tabla
            ((DefaultTableModel) this.tabla.getModel()).setRowCount(i + 1);
            // se agregan a la tabla los datos correspondientes cada celda de la fila recuperada
            this.tabla.setValueAt(rs.getString("nombre_barco"), i, 0);
            this.tabla.setValueAt(rs.getInt("id"), i, 1);            
            this.tabla.setValueAt(rs.getString("capitan"), i, 2);
            i++;
         }
         // se cierran los recursos utilizados 
         rs.close();
         stmt.close();
      }
      catch (SQLException ex)
      {
         // en caso de error, se muestra la causa en la consola
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
      }
   }

}
