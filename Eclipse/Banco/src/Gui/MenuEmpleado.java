package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class MenuEmpleado extends JFrame{
	
	private Login login;
	private JPanel contentPane;
	private String legajo;
	private CrearPrestamo crearP;
	private PagarCuotas pagarC;
	private ListarClientesMorosos listarC;
	private JDesktopPane dkP; 

	public MenuEmpleado(Login login, String user) {
		
		this.login = login;
		legajo = user;
		initGui();
	}
	
	private void initGui(){
		
		setResizable(false);
		setTitle("Empleado");
		setBounds(new Rectangle(0, 0, 900, 500));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		crearP= new CrearPrestamo(legajo);
		crearP.setBorder(null);
		
		crearP.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		crearP.setResizable(true);
		crearP.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		crearP.setMaximizable(true);
		crearP.setBounds(5, 10, 837, 492);
		crearP.setVisible(false);
		crearP.setEnabled(false);
		
		pagarC= new PagarCuotas();
		pagarC.setBorder(null);
		pagarC.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pagarC.setResizable(true);
		pagarC.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		pagarC.setMaximizable(true);
		pagarC.setBounds(5, 10, 837, 492);
		pagarC.setVisible(false);
		pagarC.setEnabled(false);
		
		listarC= new ListarClientesMorosos();
		listarC.setBorder(null);
		listarC.setResizable(true);
		listarC.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		listarC.setMaximizable(true);
		listarC.setBounds(5, 10, 837, 490);
		listarC.setVisible(false);
		listarC.setEnabled(false);
		
		
		setBounds(0,0,837, 540);
		
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setForeground(Color.DARK_GRAY);
		setForeground(Color.DARK_GRAY);
		getContentPane().setBackground(new Color(211, 211, 211));
		setBackground(new Color(211, 211, 211));
		
		JMenuBar menuEmpleado = new JMenuBar();
		menuEmpleado.setForeground(Color.DARK_GRAY);
		menuEmpleado.setBackground(Color.WHITE);
		setJMenuBar(menuEmpleado);
		
		JMenu mnRealizar = new JMenu("Realizar...");
		mnRealizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnRealizar.setForeground(Color.DARK_GRAY);
		mnRealizar.setBackground(new Color(211, 211, 211));
		menuEmpleado.add(mnRealizar);
		
		JMenuItem miCrear = new JMenuItem("Crear prestamo");
		miCrear.setForeground(Color.DARK_GRAY);
		miCrear.setBackground(Color.WHITE);
		miCrear.addActionListener(new oyenteCrear());
		mnRealizar.add(miCrear);
		
		JMenuItem miPagar = new JMenuItem("Pagar cuotas");
		miPagar.setForeground(Color.DARK_GRAY);
		miPagar.setBackground(Color.WHITE);
		miPagar.addActionListener(new oyentePagar());
		mnRealizar.add(miPagar);
		
		JMenuItem miListar = new JMenuItem("Listar clientes morosos");
		miListar.setForeground(Color.DARK_GRAY);
		miListar.setBackground(Color.WHITE);
		miListar.addActionListener(new oyenteListar());
		mnRealizar.add(miListar);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		mnRealizar.add(separator);
		
		JMenuItem miSesion = new JMenuItem("Cerrar Sesion");
		miSesion.setForeground(Color.DARK_GRAY);
		miSesion.setBackground(Color.WHITE);
		mnRealizar.add(miSesion);
		getContentPane().setLayout(null);
		miSesion.addActionListener(new oyenteSesion(this));
		contentPane.setLayout(null);
		
		dkP = new JDesktopPane();
		dkP.setOpaque(false);
		dkP.setBounds(new Rectangle(0, 0, 842, 485));
		dkP.setForeground(Color.DARK_GRAY);
		dkP.setBackground(new Color(211, 211, 211));
		dkP.setBounds(-8, -11, 844, 496);
		dkP.add(crearP);
		dkP.add(pagarC);
		dkP.add(listarC);
		getContentPane().add(dkP);	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private class oyenteCrear implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			crearP.setVisible(true);
			crearP.show();
			crearP.moveToFront();
			crearP.setEnabled(true);
			
		} 
	 }
	
	private class oyentePagar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			pagarC.setVisible(true);
			pagarC.show();
			pagarC.moveToFront();
			pagarC.setEnabled(true);			
		} 
	 }
	
	private class oyenteListar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			listarC.realizarConsulta();
			listarC.show();
			listarC.moveToFront();
			
			listarC.setVisible(true);
			listarC.setEnabled(true);			
		} 
	 }
	
	private class oyenteSesion implements ActionListener{
		
		private MenuEmpleado menu;
		
		public oyenteSesion(MenuEmpleado menu){
			this.menu=menu;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			menu.setEnabled(false);
			menu.setVisible(false);
			login.setVisible(true);
			login.setEnabled(true);
			
		}				
	}
}
