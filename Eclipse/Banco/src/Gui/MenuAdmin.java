package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Font;

public class MenuAdmin extends JFrame {

	private ConsultasAdmin consultas;
	private ConsultarTablasAdmin consultasTablas;
	private JMenuBar menuBar;
	private JMenuItem JICerrarSesion;
	private JMenuItem JIConsultar;
	private JMenuItem JIConsultarTablas;
	private JMenu menuAccion;
	private JSeparator separator;
	private Login login;


	public MenuAdmin(Login login) {
		getContentPane().setFont(new Font("Dialog", Font.BOLD, 14));
		setFont(new Font("Dialog", Font.BOLD, 16));
		this.login=login;
		initGui();
	}

	private void initGui(){

		getContentPane().setForeground(new Color(64, 64, 64));
		setTitle("Admin Menu");
		setResizable(false);
		
		setForeground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0, 840, 570);


		getContentPane().setLayout(new BorderLayout(0, 0));

		JDesktopPane dpMA = new JDesktopPane();
		dpMA.setForeground(Color.DARK_GRAY);
		dpMA.setEnabled(false);
		dpMA.setAlignmentY(0.0f);
		dpMA.setAlignmentX(0.0f);
		dpMA.setBounds(new Rectangle(0, 0, 835, 527));
		dpMA.setBounds(-10, -10, 835, 527);
		getContentPane().add(dpMA);

		consultas= new ConsultasAdmin();
		consultas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		consultas.setResizable(true);
		consultas.setBorder(null);
		consultas.setAlignmentY(Component.TOP_ALIGNMENT);
		consultas.setAlignmentX(Component.LEFT_ALIGNMENT);
		consultas.getContentPane().setForeground(Color.DARK_GRAY);
		consultas.setForeground(Color.DARK_GRAY);
		consultas.setMaximizable(true);
		consultas.setClosable(true);
		consultas.setBounds(0, 0, 834, 527);
		consultas.setLocation(0, 0);
		consultas.setVisible(false);
		consultas.setEnabled(false);
		dpMA.add(consultas);

		consultasTablas= new ConsultarTablasAdmin();
		consultasTablas.getContentPane().setForeground(Color.DARK_GRAY);
		consultasTablas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		consultasTablas.setBorder(null);
		consultasTablas.setAlignmentY(Component.TOP_ALIGNMENT);
		consultasTablas.setAlignmentX(Component.LEFT_ALIGNMENT);
		consultasTablas.setForeground(Color.DARK_GRAY);
		dpMA.add(consultasTablas);

		menuBar= new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(new Color(211, 211, 211));
		setJMenuBar(menuBar);

		menuAccion = new JMenu("Realizar...");
		menuAccion.setForeground(Color.DARK_GRAY);
		menuAccion.setBackground(new Color(211, 211, 211));
		menuBar.add(menuAccion);
		JIConsultar= new JMenuItem("Realizar Consultas");
		JIConsultar.setForeground(Color.DARK_GRAY);
		menuAccion.add(JIConsultar);
		JIConsultarTablas= new JMenuItem("Consultar Tablas");
		JIConsultarTablas.setForeground(Color.DARK_GRAY);
		menuAccion.add(JIConsultarTablas);

		separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.DARK_GRAY);
		menuAccion.add(separator);
		JICerrarSesion= new JMenuItem("Cerrar Sesion");
		JICerrarSesion.setForeground(Color.DARK_GRAY);
		JICerrarSesion.addActionListener(new oyenteSesion(this));
		menuAccion.add(JICerrarSesion);

		JIConsultarTablas.addActionListener(new oyenteConsultarTablas());
		JIConsultar.addActionListener(new oyenteConsultar());


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private class oyenteConsultar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			consultas.setVisible(true);
			consultas.setEnabled(true);
			consultas.moveToFront();
		} 
	}

	private class oyenteConsultarTablas implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			consultasTablas.consultarTablas();
			consultasTablas.setVisible(true);
			consultasTablas.setEnabled(true);
			consultasTablas.moveToFront();
		} 
	}

	private class oyenteSesion implements ActionListener{
		
		private MenuAdmin menu;
		
		public oyenteSesion(MenuAdmin menu){
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
