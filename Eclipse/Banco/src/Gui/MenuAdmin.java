package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

public class MenuAdmin extends JFrame {
	
	private ConsultasAdmin consultas;
	private ConsultarTablasAdmin consultasTablas;
	private JMenuBar menuBar;
	private JMenuItem JICerrarSesion;
	private JMenuItem JIConsultar;
	private JMenuItem JIConsultarTablas;
	private JMenu menuAccion;
	private JSeparator separator;
	
	
	public MenuAdmin() {
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setTitle("Admin Menu");
		setResizable(false);
		initGui();
	}
	
	private void initGui(){
		
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0, 840, 570);
			
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JDesktopPane dpMA = new JDesktopPane();
		dpMA.setForeground(Color.WHITE);
		dpMA.setBackground(Color.DARK_GRAY);
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
		consultas.getContentPane().setForeground(Color.WHITE);
		consultas.setForeground(Color.WHITE);
		consultas.getContentPane().setBackground(Color.DARK_GRAY);
		consultas.setBackground(Color.DARK_GRAY);
		consultas.setMaximizable(true);
		//consultas.setMaximum(true);
		//consultas.setClosed(true);
		consultas.setClosable(true);
		consultas.setBounds(0, 0, 834, 527);
		consultas.setLocation(0, 0);
		consultas.setVisible(false);
		consultas.setEnabled(false);
		dpMA.add(consultas);
		
		consultasTablas= new ConsultarTablasAdmin();
		consultasTablas.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		consultasTablas.setBorder(null);
		consultasTablas.setAlignmentY(Component.TOP_ALIGNMENT);
		consultasTablas.setAlignmentX(Component.LEFT_ALIGNMENT);
		consultasTablas.setForeground(Color.WHITE);
		dpMA.add(consultasTablas);
		
		menuBar= new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(Color.DARK_GRAY);
		setJMenuBar(menuBar);
		
		menuAccion = new JMenu("Realizar...");
		menuAccion.setForeground(Color.WHITE);
		menuAccion.setBackground(Color.DARK_GRAY);
		menuBar.add(menuAccion);
		JIConsultar= new JMenuItem("Realizar Consultas");
		JIConsultar.setForeground(Color.WHITE);
		JIConsultar.setBackground(Color.DARK_GRAY);
		menuAccion.add(JIConsultar);
		JIConsultarTablas= new JMenuItem("Consultar Tablas");
		JIConsultarTablas.setForeground(Color.WHITE);
		JIConsultarTablas.setBackground(Color.DARK_GRAY);
		menuAccion.add(JIConsultarTablas);
		
		separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.DARK_GRAY);
		menuAccion.add(separator);
		JICerrarSesion= new JMenuItem("Cerrar Sesión");
		JICerrarSesion.setForeground(Color.WHITE);
		JICerrarSesion.setBackground(Color.DARK_GRAY);
		menuAccion.add(JICerrarSesion);
		
		JIConsultarTablas.addActionListener(new oyenteConsultarTablas());
		JIConsultar.addActionListener(new oyenteConsultar());
		
		
		
	}
	
	 private class oyenteConsultar implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			consultas.setVisible(true);
			consultas.setEnabled(true);			
		} 
	 }
	 
	 private class oyenteConsultarTablas implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				consultasTablas.setVisible(true);
				consultas.setEnabled(true);			
			} 
		 }
	
	
}
