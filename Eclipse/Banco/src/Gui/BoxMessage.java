package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Gui.Login.oyenteSalir;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;

public class BoxMessage extends JFrame {

	private static BoxMessage box;
	
	private JPanel cpNotificaciones;
	private String message;
	private JButton btnCancelar,btnAceptar;
	private JLabel lblMessage;
	
	
	private BoxMessage(String message) {
		
		
		message=message;
		
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 336, 173);
		cpNotificaciones = new JPanel();
		cpNotificaciones.setForeground(Color.WHITE);
		cpNotificaciones.setBackground(Color.DARK_GRAY);
		cpNotificaciones.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(cpNotificaciones);
		cpNotificaciones.setLayout(null);
		
		lblMessage = new JLabel();
		lblMessage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblMessage.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setBackground(Color.DARK_GRAY);
		lblMessage.setBounds(10, 0, 300, 88);
		cpNotificaciones.add(lblMessage);
		lblMessage.setText(message);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setVisible(false);
		btnCancelar.setEnabled(false);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(Color.DARK_GRAY);
		btnCancelar.setBounds(49, 99, 96, 25);
		cpNotificaciones.add(btnCancelar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setVisible(false);
		btnAceptar.setEnabled(false);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setBackground(Color.DARK_GRAY);
		btnAceptar.setBounds(172, 99, 96, 25);
		cpNotificaciones.add(btnAceptar);
		
		
	}
	
	//Listener btnSalir
	public class oyenteAceptarSalir implements ActionListener{
			
		public void actionPerformed(ActionEvent arg0) {
				//salir
				System.exit(0);
		}
			
	}
	
	public class oyenteCancelarSalir implements ActionListener{
		
			private JFrame miFrame;
			private JFrame login;
		
		public oyenteCancelarSalir(JFrame miFrame,JFrame login) {
			super();
			this.miFrame=miFrame;
			this.login=login;
			
		}
		
		public void actionPerformed(ActionEvent arg0) {
				//Vuelve a la ventana de login
				login.setVisible(true);
				login.setEnabled(true);
				miFrame.dispose();
		}
			
	}
	
	public void habilitarAceptarSalir() {
		
		//Reconfiguración de los objetos de la interfaz
		cpNotificaciones.setSize(346, cpNotificaciones.getHeight());
		btnCancelar.setAlignmentX(52);
		btnCancelar.setAlignmentY(112);
		btnAceptar.setAlignmentX(193);
		btnAceptar.setAlignmentY(112);
		
		this.setSize(346, 170);
		
		lblMessage.setFont(new Font("Dialog", Font.PLAIN, 19));
		btnAceptar.addActionListener(new oyenteAceptarSalir());
		btnAceptar.setVisible(true);
		btnAceptar.setEnabled(true);
		
	}
	
	public void habilitarCancelarSalir(JFrame login) {
		btnCancelar.addActionListener(new oyenteCancelarSalir(this,login));
		btnCancelar.setVisible(true);
		btnCancelar.setEnabled(true);
		
	}
	
	public static BoxMessage getBoxMessage(String message){
		if(box==null)
			box=new BoxMessage(message);
		
		return box;
	}
	
	public void notificarErrorDB(){
		this.setSize(458, 172);
		//cpNotificaciones.setSize(417, cpNotificaciones.getHeight());
		btnAceptar.setAlignmentX(174);
		btnAceptar.setAlignmentY(99);
		
		btnAceptar.addActionListener(new oyenteAceptarSalir());
		btnAceptar.setVisible(true);
		btnAceptar.setEnabled(true);
		
	}
	
}
