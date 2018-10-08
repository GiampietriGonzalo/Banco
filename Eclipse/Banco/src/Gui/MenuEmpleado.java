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
	private int legajo;
	private CrearPrestamo crearP;
	private PagarCuotas pagarC;
	private ListarClientesMorosos listarC;

	public MenuEmpleado(Login login) {
		this.login = login;
		initGui();
	}
	
	private void initGui(){
		
		setTitle("Empleado");
		setBounds(new Rectangle(0, 0, 900, 500));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		crearP= new CrearPrestamo();
		crearP.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		crearP.setResizable(true);
		crearP.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		crearP.setMaximizable(true);
		crearP.setBounds(0, 0, 846, 492);
		crearP.setVisible(false);
		crearP.setEnabled(false);
		
		pagarC= new PagarCuotas();
		pagarC.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pagarC.setResizable(true);
		pagarC.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		pagarC.setMaximizable(true);
		pagarC.setBounds(0, 0, 846, 492);
		pagarC.setVisible(false);
		pagarC.setEnabled(false);
		
		listarC= new ListarClientesMorosos();
		listarC.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		listarC.setResizable(true);
		listarC.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		listarC.setMaximizable(true);
		listarC.setBounds(0, 0, 846, 492);
		listarC.setVisible(false);
		listarC.setEnabled(false);
				
		setBounds(0,0,837, 540);
		
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		
		JMenuBar menuEmpleado = new JMenuBar();
		menuEmpleado.setForeground(Color.WHITE);
		menuEmpleado.setBackground(Color.DARK_GRAY);
		setJMenuBar(menuEmpleado);
		
		JMenu mnRealizar = new JMenu("Realizar...");
		mnRealizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnRealizar.setForeground(Color.WHITE);
		mnRealizar.setBackground(Color.DARK_GRAY);
		menuEmpleado.add(mnRealizar);
		
		JMenuItem miCrear = new JMenuItem("Crear prestamo");
		miCrear.setForeground(Color.WHITE);
		miCrear.setBackground(Color.DARK_GRAY);
		miCrear.addActionListener(new oyenteCrear());
		mnRealizar.add(miCrear);
		
		JMenuItem miPagar = new JMenuItem("Pagar cuotas");
		miPagar.setForeground(Color.WHITE);
		miPagar.setBackground(Color.DARK_GRAY);
		miPagar.addActionListener(new oyentePagar());
		mnRealizar.add(miPagar);
		
		JMenuItem miListar = new JMenuItem("Listar clientes morosos");
		miListar.setForeground(Color.WHITE);
		miListar.setBackground(Color.DARK_GRAY);
		miListar.addActionListener(new oyenteListar());
		mnRealizar.add(miListar);
		
		JSeparator separator = new JSeparator();
		mnRealizar.add(separator);
		
		JMenuItem miSesion = new JMenuItem("Cerrar Sesi\u00F3n");
		miSesion.setForeground(Color.WHITE);
		miSesion.setBackground(Color.DARK_GRAY);
		mnRealizar.add(miSesion);
		getContentPane().setLayout(null);
		miSesion.addActionListener(new oyenteSesion(this));
		contentPane.setLayout(null);
		
		JDesktopPane dkP = new JDesktopPane();
		dkP.setOpaque(false);
		dkP.setBounds(new Rectangle(0, 0, 842, 485));
		dkP.setForeground(Color.WHITE);
		dkP.setBackground(Color.DARK_GRAY);
		dkP.setBounds(-8, -11, 844, 496);
		dkP.add(crearP);
		dkP.add(pagarC);
		dkP.add(listarC);
		getContentPane().add(dkP);	
		
	}
	
	private class oyenteCrear implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			crearP.setVisible(true);
			crearP.setEnabled(true);			
		} 
	 }
	
	private class oyentePagar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			pagarC.setVisible(true);
			pagarC.setEnabled(true);			
		} 
	 }
	
	private class oyenteListar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
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
