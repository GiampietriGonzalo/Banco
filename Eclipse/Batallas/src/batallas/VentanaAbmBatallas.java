package batallas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import quick.dbtable.*;

@SuppressWarnings("serial")
public class VentanaAbmBatallas extends javax.swing.JInternalFrame 
{
   
   private JPanel pnlInferior;
   private JTextField txtNombre;
   private JFormattedTextField txtFecha;   
   private JLabel lblFecha;
   private JLabel lblNombre;
   private JButton btnGuardar;
   private JButton btnBorrar;
   private JButton btnNuevo;
   private JPanel pnlBotones;
   private JPanel pnlCampos;
   //private JScrollPane scrTabla;
   //private JTable tabla;
   private DBTable tabla; 
   
   protected Connection conexionBD = null;
   protected int seleccionado = -1;
   
   public VentanaAbmBatallas() 
   {
      super();
      initGUI();
   }
   
   private void initGUI() 
   {
      try {
         setPreferredSize(new Dimension(640, 480));
         this.setBounds(0, 0, 640, 480);
         this.setTitle("ABM de Batallas (Utilizando DBTable)");
         this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
         this.setResizable(true);
         this.setClosable(true);
         this.setVisible(true);
         BorderLayout thisLayout = new BorderLayout();
         this.setMaximizable(true);
         getContentPane().setLayout(thisLayout);
         this.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt) {
               thisComponentHidden(evt);
            }
            public void componentShown(ComponentEvent evt) {
               thisComponentShown(evt);
            }
         });
         
                
            //Utilizando DBTable            
            {
            	// crea la tabla  
            	tabla = new DBTable();    
            	
				tabla.setToolTipText("Doble-click o Espacio para seleccionar el registro.");
                tabla.addKeyListener(new KeyAdapter() {
                   public void keyTyped(KeyEvent evt) {
                      tablaKeyTyped(evt);
                   }
                });
                
                tabla.addMouseListener(new MouseAdapter() {
                   public void mouseClicked(MouseEvent evt) {
                      tablaMouseClicked(evt);
                   }
            	});
            	// Agregar la tabla al frame (no necesita JScrollPane como Jtable)
                getContentPane().add(tabla, BorderLayout.CENTER);           
                          
               //setea la tabla solo para lectura, no se puede editar su contenido  
               tabla.setEditable(false); 
            }
         
          //mismo código pero Utilizando JTable
          /*                       
            {
               TableModel BatallasModel =  
                  new DefaultTableModel
                  (
                     new String[][] {},
                     new String[] {"Nombre", "Fecha"}
                  )
                  {
                     Class[] types = new Class[] { java.lang.String.class, java.util.Date.class};
                     boolean[] canEdit = new boolean[] { false, false };

                     public Class getColumnClass(int columnIndex)
                     {
                        return types[columnIndex];
                     }

                     public boolean isCellEditable(int rowIndex, int columnIndex)
                     {
                        return canEdit[columnIndex];
                     }
                  }
               ;
               
               scrTabla = new JScrollPane();
               getContentPane().add(scrTabla, BorderLayout.CENTER);
               scrTabla.setPreferredSize(new java.awt.Dimension(638, 376));
              
               tabla = new JTable();
               scrTabla.setViewportView(tabla);
               tabla.setModel(BatallasModel);
               
               tabla.setToolTipText("Doble-click o Espacio para seleccionar el registro.");
               tabla.addKeyListener(new KeyAdapter() {
                  public void keyTyped(KeyEvent evt) {
                     tablaKeyTyped(evt);
                  }
               });
               
               tabla.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent evt) {
                     tablaMouseClicked(evt);
                  }
               });
            }   
          */
         
         
         {
            pnlInferior = new JPanel();
            BorderLayout jPanel1Layout = new BorderLayout();
            jPanel1Layout.setHgap(3);
            jPanel1Layout.setVgap(3);
            getContentPane().add(pnlInferior, BorderLayout.SOUTH);
            pnlInferior.setLayout(jPanel1Layout);
            pnlInferior.setPreferredSize(new java.awt.Dimension(638, 88));
            {
               pnlCampos = new JPanel();
               GridLayout jPanel2Layout = new GridLayout(2, 2);
               jPanel2Layout.setRows(2);
               jPanel2Layout.setColumns(2);
               jPanel2Layout.setHgap(5);
               jPanel2Layout.setVgap(3);
               pnlInferior.add(pnlCampos, BorderLayout.CENTER);
               pnlCampos.setLayout(jPanel2Layout);
               pnlCampos.setPreferredSize(new java.awt.Dimension(638, 76));
               pnlCampos.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
               {
                  lblNombre = new JLabel();
                  pnlCampos.add(lblNombre);
                  lblNombre.setText("Nombre");
                  lblNombre.setHorizontalAlignment(SwingConstants.TRAILING);
               }
               {
                  txtNombre = new JTextField();
                  pnlCampos.add(txtNombre);
                  txtNombre.setPreferredSize(new java.awt.Dimension(638, 41));
               }
               {
                  lblFecha = new JLabel();
                  pnlCampos.add(lblFecha);
                  lblFecha.setText("Fecha");
                  lblFecha.setHorizontalAlignment(SwingConstants.TRAILING);
               }
               {
                  txtFecha = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
                  pnlCampos.add(txtFecha);
               }
            }
            {
               pnlBotones = new JPanel();
               pnlInferior.add(pnlBotones, BorderLayout.SOUTH);
               pnlBotones.setPreferredSize(new java.awt.Dimension(638, 31));
               {
                  btnNuevo = new JButton();
                  pnlBotones.add(btnNuevo);
                  btnNuevo.setText("Nuevo");
                  btnNuevo.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        btnNuevoActionPerformed(evt);
                     }
                  });
               }
               {
                  btnBorrar = new JButton();
                  pnlBotones.add(btnBorrar);
                  btnBorrar.setText("Borrar");
                  btnBorrar.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        btnBorrarActionPerformed(evt);
                     }
                  });
               }
               {
                  btnGuardar = new JButton();
                  pnlBotones.add(btnGuardar);
                  btnGuardar.setText("Guardar");
                  btnGuardar.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        btnGuardarActionPerformed(evt);
                     }
                  });
               }
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
      this.refrescar();
   }
   
   private void thisComponentHidden(ComponentEvent evt) 
   {
      this.desconectarBD();
   }

   private void tablaMouseClicked(MouseEvent evt) 
   {
      if ((this.tabla.getSelectedRow() != -1) && (evt.getClickCount() == 2))
      {
         this.seleccionarFila();
      }
   }
   
   private void tablaKeyTyped(KeyEvent evt) 
   {
      if ((this.tabla.getSelectedRow() != -1) && (evt.getKeyChar() == ' '))
      {
         this.seleccionarFila();
      }
   }
   
   private void btnNuevoActionPerformed(ActionEvent evt) 
   {
      this.inicializarCampos();
      this.seleccionado = -1;
   }
   
   private void btnBorrarActionPerformed(ActionEvent evt) 
   {
      if (this.seleccionado == -1)
      {
         JOptionPane.showMessageDialog(this,
                                       "No ha seleccionado el registro a borrar.",
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         this.borrarRegistro();
         this.refrescar();
      }
   }
   
   private void btnGuardarActionPerformed(ActionEvent evt) 
   {
      if (this.validarCampos())
      {
         if (this.seleccionado == -1)
         {
            // se inserta un nuevo registro
            this.insertarRegistro();
         }
         else
         {
            // se actualiza el registro actual
            this.actualizarRegistro();
         }
         this.refrescar();
      }
   }

   private void conectarBD()
   {
      if (this.conexionBD == null)
      {
        
    	  try
         {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         }
         catch (Exception ex)
         {
            System.out.println(ex.getMessage());
         }
        
         try
         {
            String servidor = "localhost:3306";
            String baseDatos = "batallas";
            String usuario = "admin_batallas";
            String clave = "pwadmin";
            String uriConexion = "jdbc:mysql://" + servidor + "/" + baseDatos+"?serverTimezone=UTC";
   
            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
         }
         catch (SQLException ex)
         {
            JOptionPane.showMessageDialog(this,
                                          "Se produjo un error al intentar conectarse a la base de datos.\n" + ex.getMessage(),
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

   private void inicializarCampos()
   {
      this.seleccionado = -1;
      this.txtNombre.setText("");
      this.txtFecha.setText("");
   }

   private void refrescar()
   {
      try
      {
         Statement stmt = this.conexionBD.createStatement();

         String sql = "SELECT nombre_batalla, fecha " + 
                      "FROM batallas " +
                      "ORDER BY nombre_batalla";


         ResultSet rs = stmt.executeQuery(sql);
       
                 
        //actualiza el contenido de la tabla con los datos del resul set rs
         tabla.refresh(rs);

        //setea el formato de visualización de la columna "fecha" a dia/mes/año
         tabla.getColumnByDatabaseName("fecha").setDateFormat("dd/MM/YYYY");
         
         tabla.getColumnByDatabaseName("fecha").setMinWidth(80);
         
        //Utilizando JTable 
        /* ((DefaultTableModel) this.tabla.getModel()).setRowCount(0);
          int i = 0;
          while (rs.next())
          {
             ((DefaultTableModel) this.tabla.getModel()).setRowCount(i + 1);
             this.tabla.setValueAt(rs.getString("nombre_batalla"), i, 0);
             this.tabla.setValueAt(rs.getDate("fecha"), i, 1);            
             i++;
          }
        */
         
         rs.close();
         stmt.close();
      }
      catch (SQLException ex)
      {
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
      }
      
      this.inicializarCampos();
   }

   private void seleccionarFila()
   {
      this.seleccionado = this.tabla.getSelectedRow();
      this.txtNombre.setText(this.tabla.getValueAt(this.tabla.getSelectedRow(), 0).toString());
      this.txtFecha.setText(Fechas.convertirDateAString((java.util.Date) this.tabla.getValueAt(this.tabla.getSelectedRow(), 1)));
   }

   private boolean validarCampos()
   {
      String mensajeError = null;
      if (this.txtNombre.getText().isEmpty())
      {
         mensajeError = "Debe ingresar un valor para el campo 'Nombre'.";
      }
      else if (this.txtFecha.getText().isEmpty())
      {
         mensajeError = "Debe ingresar un valor para el campo 'Fecha'.";
      }
      else if (! Fechas.validar(this.txtFecha.getText().trim()))
      {
         mensajeError = "En el campo 'Fecha' debe ingresar un valor con el formato dd/mm/aaaa.";
      }

      if (mensajeError != null)
      {
         JOptionPane.showMessageDialog(this,
                                       mensajeError,
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE);
         return false;
      }
      return true;
   }

   private void insertarRegistro()
   {
      String nombre = this.txtNombre.getText().trim();
      java.sql.Date fecha = Fechas.convertirStringADateSQL(this.txtFecha.getText().trim());
      
      try
      {
         Statement stmt = this.conexionBD.createStatement();
         String sql = "INSERT INTO batallas (nombre_batalla, fecha) " +
                      "VALUES ('" + nombre  + "', '" + fecha + "')";
         stmt.execute(sql);
         stmt.close();
      }
      catch (SQLException ex)
      {
         JOptionPane.showMessageDialog(this,
                                       "Se produjo un error al insertar el nuevo registro.\n" + ex.getMessage(),
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE);
      }
   }

   private void actualizarRegistro()
   {
      String nombreViejo = this.tabla.getValueAt(this.seleccionado, 0).toString();
      String nombreNuevo = this.txtNombre.getText().trim();
      java.sql.Date fecha = Fechas.convertirStringADateSQL(this.txtFecha.getText().trim());
      
      try
      {
         // ejemplo con sentencia prerarada
    	 // se prepara la sentencia sql
         String sql = "UPDATE batallas " +
                      "SET nombre_batalla = ?, fecha = ? " +
                      "WHERE nombre_batalla = ?";
         
         // se crea un sentencia preparada
         PreparedStatement stmt = this.conexionBD.prepareStatement(sql);
         
         // se ligan los parámetros efectivos
         stmt.setString(1, nombreNuevo);
         stmt.setDate(2, fecha);
         stmt.setString(3, nombreViejo);

         stmt.executeUpdate();
         stmt.close();
      }
      catch (SQLException ex)
      {
         JOptionPane.showMessageDialog(this,
                                       "Se produjo un error al actualizar el registro.\n" + ex.getMessage(),
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE);
      }
   }
   
   private void borrarRegistro()
   {
      String nombre = this.tabla.getValueAt(this.seleccionado, 0).toString();

      try
      {
         Statement stmt = this.conexionBD.createStatement();
         String sql = "DELETE FROM batallas " +
                      "WHERE nombre_batalla = '" + nombre  + "'";
         stmt.execute(sql);
         stmt.close();
      }
      catch (SQLException ex)
      {
         JOptionPane.showMessageDialog(this,
                                       "Se produjo un error al insertar el nuevo registro.\n" + ex.getMessage(),
                                       "Error",
                                       JOptionPane.ERROR_MESSAGE);
      }
      
   }
   
}
