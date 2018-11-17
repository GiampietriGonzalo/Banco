package Gui;

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
	private int numTarjeta;
	private MovimientosATM mov;
	private ConsultarSaldoATM saldos;
	private TransferenciaATM trans;
	private ExtraccionATM ext;
	private Login login;
	private JDesktopPane dkP;

	
	public MenuATM(int codCaja,int numTarjeta,Login login){
		this.codCaja=codCaja;
		this.numTarjeta=numTarjeta;
		initGui(login);	
	}

	private void initGui(Login login){
		
		setTitle("ATM");
		setBounds(new Rectangle(0, 0, 900, 500));
		this.login=login;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		mov= new MovimientosATM(codCaja);
		mov.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mov.setResizable(false);
		mov.setNormalBounds(new Rectangle(-10, -10, 842, 485));
		mov.setMaximizable(true);
		mov.setBounds(7, 10, 820,492);
		mov.setVisible(false);
		mov.setEnabled(false);
		
		saldos= new ConsultarSaldoATM(codCaja);
		saldos.setMaximizable(true);
		saldos.setClosable(true);
		saldos.setLocation(9, 11);
		
		trans= new TransferenciaATM(codCaja);
		trans.setBorder(null);
		trans.setMaximizable(true);
		trans.setClosable(true);
		trans.setLocation(9, 11);
		trans.setVisible(false);
		
		ext= new ExtraccionATM(codCaja,numTarjeta);
		ext.setBorder(null);
		ext.setMaximizable(true);
		ext.setClosable(true);
		ext.setLocation(9,11);
		ext.setVisible(false);
		setBounds(0,0,837, 540);
		
		setFont(new Font("Dialog", Font.BOLD, 16));
		getContentPane().setForeground(Color.DARK_GRAY);
		setForeground(Color.DARK_GRAY);
		
		JMenuBar menuATM = new JMenuBar();
		menuATM.setForeground(Color.DARK_GRAY);
		menuATM.setBackground(new Color(211, 211, 211));
		setJMenuBar(menuATM);
		
		JMenu mnRealizar = new JMenu("Realizar...");
		mnRealizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnRealizar.setForeground(Color.DARK_GRAY);
		mnRealizar.setBackground(new Color(211, 211, 211));
		menuATM.add(mnRealizar);
		
		JMenuItem miSaldo = new JMenuItem("ConsultarSaldo");
		miSaldo.setForeground(Color.DARK_GRAY);
		mnRealizar.add(miSaldo);
		miSaldo.addActionListener(new oyenteSaldo());
		
		JMenuItem miMovimientos = new JMenuItem("Consultar movimientos");
		miMovimientos.setForeground(Color.DARK_GRAY);
		miMovimientos.addActionListener(new oyenteMovimientos());
		mnRealizar.add(miMovimientos);
		
		JMenuItem miTransferencias = new JMenuItem("Realizar transferencia");
		miTransferencias.setForeground(Color.DARK_GRAY);
		miTransferencias.addActionListener(new oyenteTransferencias());
		mnRealizar.add(miTransferencias);
		
		JMenuItem miExtraccion = new JMenuItem("Realizar Extraccion");
		miExtraccion.setForeground(Color.DARK_GRAY);
		miExtraccion.addActionListener(new oyenteExtraccion());
		mnRealizar.add(miExtraccion);
		
		JSeparator separator = new JSeparator();
		mnRealizar.add(separator);
		
		JMenuItem miSesion = new JMenuItem("Cerrar Sesion");
		miSesion.setForeground(Color.DARK_GRAY);
		mnRealizar.add(miSesion);
		getContentPane().setLayout(null);
		miSesion.addActionListener(new oyenteSesion(this));
		contentPane.setLayout(null);
		
		dkP = new JDesktopPane();
		dkP.setOpaque(false);
		dkP.setBounds(new Rectangle(0, 0, 842, 485));
		dkP.setForeground(Color.DARK_GRAY);
		dkP.setBounds(-8, -11, 844, 496);
		dkP.add(mov);
		dkP.add(saldos);
		dkP.add(trans);
		dkP.add(ext);
		getContentPane().add(dkP);
		
	}
	
	
	private class oyenteMovimientos implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			mov.limpiar();
			mov.setVisible(true);
			mov.setEnabled(true);		
			mov.moveToFront();
		} 
	 }
	
	private class oyenteSaldo implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			saldos= new ConsultarSaldoATM(codCaja);
			dkP.add(saldos);
			saldos.setMaximizable(true);
			saldos.setClosable(true);
			saldos.setLocation(9, 11);
			saldos.setVisible(true);
			saldos.setEnabled(true);			
			saldos.moveToFront();
		} 
	 }
	
	private class oyenteTransferencias implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			trans.setVisible(true);
			trans.setEnabled(true);			
			trans.moveToFront();
		} 
	 }
	
	private class oyenteExtraccion implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
		
			ext.setVisible(true);
			ext.setEnabled(true);			
			ext.moveToFront();
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
}
