package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

public class MenuATM extends JFrame {

	private JPanel contentPane;
	private int codCaja;
	private MovimientosATM mov;
	private Login login;

	
	public MenuATM(int codCaja,Login login){
		
		initGui(login);
		
	}
	
	private class oyenteMovimientos implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			mov.setVisible(true);
			mov.setEnabled(true);			
		} 
	 }
	
	private class oyenteSesion implements ActionListener{
	
		private MenuATM menu;
		
		public oyenteSesion(MenuATM menu){
			this.menu=menu;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			menu.setEnabled(false);
			menu.setVisible(false);
			login.setVisible(true);
			login.setEnabled(true);
			
		}				
	}

	
	private void initGui(Login login){
		
		setTitle("ATM");
		setBounds(new Rectangle(0, 0, 900, 500));
		this.login=login;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		mov= new MovimientosATM(codCaja);
		mov.setResizable(true);
		mov.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		mov.setMaximizable(true);
		mov.setBounds(0, 0, 846, 492);
		mov.setVisible(false);
		mov.setEnabled(false);
		
		this.codCaja=codCaja;
		
		setBounds(0,0,837, 540);
		
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.WHITE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setBackground(Color.DARK_GRAY);
		
		JMenuBar menuATM = new JMenuBar();
		menuATM.setForeground(Color.WHITE);
		menuATM.setBackground(Color.DARK_GRAY);
		setJMenuBar(menuATM);
		
		JMenu mnRealizar = new JMenu("Realizar...");
		mnRealizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnRealizar.setForeground(Color.WHITE);
		mnRealizar.setBackground(Color.DARK_GRAY);
		menuATM.add(mnRealizar);
		
		JMenuItem miSaldo = new JMenuItem("ConsultarSaldo");
		miSaldo.setForeground(Color.WHITE);
		miSaldo.setBackground(Color.DARK_GRAY);
		mnRealizar.add(miSaldo);
		
		JMenuItem miMovimientos = new JMenuItem("Consultar movimientos");
		miMovimientos.setForeground(Color.WHITE);
		miMovimientos.setBackground(Color.DARK_GRAY);
		miMovimientos.addActionListener(new oyenteMovimientos());
		mnRealizar.add(miMovimientos);
		
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
		dkP.add(mov);
		getContentPane().add(dkP);
	
		
		
	}
}
