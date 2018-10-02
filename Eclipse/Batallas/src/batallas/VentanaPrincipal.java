package batallas;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



@SuppressWarnings("serial")
public class VentanaPrincipal extends javax.swing.JFrame 
{

   private VentanaBarcos ventanaBarcos;
   private VentanaConsultas ventanaConsultas;
   private JButton btnCerrar;
   private JLabel lblAcercaDe4;
   private JLabel lblAcercaDe3;
   private JLabel lblAcercaDe2;
   private JLabel lblAcercaDe1;
   private VentanaAbmBatallas ventanaAbmBatallas;
   private JMenu mnuAcercaDe;
   private JDialog dlgAcercaDe;

   private JDesktopPane jDesktopPane1;
   private JMenuBar jMenuBar1;
   private JMenuItem mniSalir;
   private JSeparator jSeparator1;
   private JMenuItem mniBarcos;
   private JMenuItem mniConsultas;
   private JMenuItem mniAbmBatallas;
   private JMenu mnuEjemplos;

   /**
   * Auto-generated main method to display this JFrame
   */
   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            VentanaPrincipal inst = new VentanaPrincipal();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
         }
      });
   }
   
   public VentanaPrincipal() 
   {
      super();

      initGUI();

      this.ventanaBarcos = new VentanaBarcos();
      ventanaBarcos.setLocation(0, -12);
      this.ventanaBarcos.setVisible(false);
      this.jDesktopPane1.add(this.ventanaBarcos);
      
      this.ventanaConsultas = new VentanaConsultas();
      this.ventanaConsultas.setVisible(false);
      this.jDesktopPane1.add(this.ventanaConsultas);

      this.ventanaAbmBatallas = new VentanaAbmBatallas();
      this.ventanaAbmBatallas.setVisible(false);
      this.jDesktopPane1.add(this.ventanaAbmBatallas);
   }
   
   private void initGUI() 
   {
      try 
      {
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      } 
      catch(Exception e) 
      {
         e.printStackTrace();
      }
      
      try {
         {
            this.setTitle("Java y MySQL");
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         }
         {
            jDesktopPane1 = new JDesktopPane();
            getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
            jDesktopPane1.setPreferredSize(new java.awt.Dimension(800, 600));
         }
         {
            dlgAcercaDe = new JDialog(this);
            GroupLayout dlgAcercaDeLayout = new GroupLayout((JComponent)dlgAcercaDe.getContentPane());
            dlgAcercaDe.getContentPane().setLayout(dlgAcercaDeLayout);
            dlgAcercaDe.setTitle("Acerca de...");
            dlgAcercaDe.setMinimumSize(new java.awt.Dimension(394, 202));
            dlgAcercaDe.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
            dlgAcercaDe.setUndecorated(true);
            {
               lblAcercaDe1 = new JLabel();
               lblAcercaDe1.setFont(new java.awt.Font("Dialog", 1, 14));
               lblAcercaDe1.setText("Ejemplo de conexión a MySQL mediante JDBC");
               lblAcercaDe1.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               btnCerrar = new JButton();
               btnCerrar.setText("Cerrar");
               btnCerrar.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     btnCerrarActionPerformed(evt);
                  }
               });
            }
            {
               lblAcercaDe2 = new JLabel();
               lblAcercaDe2.setText("Versión: 0.2");
               lblAcercaDe2.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               lblAcercaDe3 = new JLabel();
               lblAcercaDe3.setText("<html><center>Bases de Datos<br/>Elementos de Bases de Datos<br/>Teoría y Diseño de Bases de Datos<br/>");
               lblAcercaDe3.setHorizontalTextPosition(SwingConstants.LEADING);
               lblAcercaDe3.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               lblAcercaDe4 = new JLabel();
               lblAcercaDe4.setText("<html><center>Dpto. de Ciencias e Ingeniería de la Computación<br/>Universidad Nacional del Sur");
               lblAcercaDe4.setHorizontalAlignment(SwingConstants.CENTER);
            }
            dlgAcercaDeLayout.setHorizontalGroup(dlgAcercaDeLayout.createSequentialGroup()
               .addContainerGap(62, 62)
               .addGroup(dlgAcercaDeLayout.createParallelGroup()
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe3, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe4, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addGap(127)
                       .addGroup(dlgAcercaDeLayout.createParallelGroup()
                           .addGroup(GroupLayout.Alignment.LEADING, dlgAcercaDeLayout.createSequentialGroup()
                               .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE))
                           .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                               .addComponent(lblAcercaDe2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                               .addGap(0, 0, Short.MAX_VALUE)))
                       .addGap(132)))
               .addContainerGap(212, 212));
            dlgAcercaDeLayout.setVerticalGroup(dlgAcercaDeLayout.createSequentialGroup()
               .addContainerGap()
               .addComponent(lblAcercaDe1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
               .addGap(22)
               .addComponent(lblAcercaDe2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
               .addComponent(lblAcercaDe3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
               .addGap(20)
               .addComponent(lblAcercaDe4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
               .addGap(24)
               .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addContainerGap(670, Short.MAX_VALUE));
         }
         {
            jMenuBar1 = new JMenuBar();
            setJMenuBar(jMenuBar1);
            {
               mnuEjemplos = new JMenu();
               jMenuBar1.add(mnuEjemplos);
               mnuEjemplos.setText("Ejemplos");
               {
                  mniBarcos = new JMenuItem();
                  mnuEjemplos.add(mniBarcos);
                  mniBarcos.setText("Búsqueda de Barcos (Utilizando JTable)");
                  mniBarcos.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniBarcosActionPerformed(evt);
                     }
                  });
               }
               {
                  mniConsultas = new JMenuItem();
                  mnuEjemplos.add(mniConsultas);
                  mniConsultas.setText("Consultas (Utilizando DBTable)");
                  mniConsultas.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniConsultasActionPerformed(evt);
                     }
                  });
               }
               {
                  mniAbmBatallas = new JMenuItem();
                  mnuEjemplos.add(mniAbmBatallas);
                  mniAbmBatallas.setText("ABM de Batallas (Utilizando DBTable)");
                  mniAbmBatallas.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniAbmBatallasActionPerformed(evt);
                     }
                  });
               }
               {
                  jSeparator1 = new JSeparator();
                  mnuEjemplos.add(jSeparator1);
               }
               {
                  mniSalir = new JMenuItem();
                  mnuEjemplos.add(mniSalir);
                  mniSalir.setText("Salir");
                  mniSalir.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent evt) {
                        mniSalirActionPerformed(evt);
                     }
                  });
               }
            }
            {
               mnuAcercaDe = new JMenu();
               jMenuBar1.add(mnuAcercaDe);
               mnuAcercaDe.setText("Acerca de...");
               mnuAcercaDe.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent evt) {
                     mnuAcercaDeMouseClicked(evt);
                  }
               });
            }
         }
         this.setSize(800, 600);
         pack();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   private void mniSalirActionPerformed(ActionEvent evt) 
   {
      this.dispose();
   }
   
   private void mniBarcosActionPerformed(ActionEvent evt) 
   {
      try
      {
         this.ventanaBarcos.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaBarcos.setVisible(true);      
   }
   
   private void mniConsultasActionPerformed(ActionEvent evt) 
   {
      try
      {
         this.ventanaConsultas.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaConsultas.setVisible(true);      
   }
   
   private void mniAbmBatallasActionPerformed(ActionEvent evt) 
   {
      try
      {
         this.ventanaAbmBatallas.setMaximum(true);
      }
      catch (PropertyVetoException e) {}
      this.ventanaAbmBatallas.setVisible(true);      
   }
   
   private void mnuAcercaDeMouseClicked(MouseEvent evt) 
   {
      dlgAcercaDe.setMinimumSize(new java.awt.Dimension(400, 280));
      centrarDialogo(dlgAcercaDe);
      dlgAcercaDe.setVisible(true);
   }

   private void btnCerrarActionPerformed(ActionEvent evt) 
   {
      dlgAcercaDe.setVisible(false);
   }
   
   private void centrarDialogo(javax.swing.JDialog p_dialogo)
   {
     p_dialogo.setLocationRelativeTo(this);
     p_dialogo.setLocationByPlatform(false);
     int desplzX = (int) ((this.getSize().getWidth() / 2.0) - (p_dialogo.getSize().getWidth() / 2.0));
     int desplzY = (int) ((this.getSize().getHeight() / 2.0) - (p_dialogo.getSize().getHeight() / 2.0));
     p_dialogo.setLocation(new Point((int) this.getLocationOnScreen().getX() + desplzX,
                                     (int) this.getLocationOnScreen().getY() + desplzY));

   }   
   

}
