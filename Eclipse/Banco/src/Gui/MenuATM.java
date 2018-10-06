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

public class MenuATM extends JFrame {

	private JPanel contentPane;
	private int codCaja;

	
	public MenuATM(int codCaja){
		setResizable(false);
		
		this.codCaja=codCaja;
		
		setBounds(0,0,842, 485);
		
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
		
		JMenuItem miUltimos = new JMenuItem("Consultar \u00FAltimos movimientos");
		miUltimos.setForeground(Color.WHITE);
		miUltimos.setBackground(Color.DARK_GRAY);
		mnRealizar.add(miUltimos);
		
		JMenuItem miPeriodo = new JMenuItem("Consultar Movimientos por per\u00EDodo");
		miPeriodo.setForeground(Color.WHITE);
		miPeriodo.setBackground(Color.DARK_GRAY);
		mnRealizar.add(miPeriodo);
		
		JSeparator separator = new JSeparator();
		mnRealizar.add(separator);
		
		JMenuItem miSesion = new JMenuItem("Cerrar Sesi\u00F3n");
		miSesion.setForeground(Color.WHITE);
		miSesion.setBackground(Color.DARK_GRAY);
		mnRealizar.add(miSesion);
		getContentPane().setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setForeground(Color.WHITE);
		desktopPane.setBackground(Color.DARK_GRAY);
		desktopPane.setBounds(0, 0, 434, 240);
		getContentPane().add(desktopPane);
	
		

	}

	
	private void initGui(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	};
}
