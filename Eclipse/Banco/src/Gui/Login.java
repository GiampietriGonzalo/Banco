package Gui;

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.DBManager;

import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

//Impots para SQL
import java.sql.Types;
//import java.sql.ResultSet;
import java.sql.SQLException;
import quick.dbtable.*;
import javax.swing.JPasswordField;
import java.awt.Font;


public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField tfPassword;
	private DBManager manager;
	private JTextField tfUser;
	
	
	public Login() {
		super();
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setTitle("Banco");
		initGui();
		manager=DBManager.getDBManager();
	}

	
	private void initGui(){
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		tfUser = new JTextField();
		tfUser.setText("Usuario");
		tfUser.setToolTipText("");
		tfUser.setBounds(60, 22, 220, 24);
		contentPane.add(tfUser);
		
		tfPassword = new JPasswordField();
		tfPassword.setEchoChar('*');
		tfPassword.setBounds(61, 58, 220, 19);
		contentPane.add(tfPassword);
		tfPassword.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(211, 211, 211));
		btnSalir.setBounds(60, 95, 94, 25);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new oyenteSalir(this));
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBackground(new Color(211, 211, 211));
		
		btnIngresar.addActionListener(new oyenteIngresar(this));
		
		btnIngresar.setBounds(187, 95, 94, 25);
		contentPane.add(btnIngresar);
	
		
	}

		
	//Listener btnIngresar
	public class oyenteIngresar implements ActionListener{
		
		private Login miFrame;
		
		public oyenteIngresar(Login frame){
			miFrame=frame;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
			manager.verificarUsuario(tfUser.getText(),tfPassword.getText().trim(),miFrame);
			tfPassword.setText("");
		}
		
	}
	
	//Listener btnSalir
	public class oyenteSalir implements ActionListener{
		
		private JFrame miFrame;
		
		public oyenteSalir(JFrame frame){
			miFrame=frame;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			
				//HACER
				
				System.exit(0);
		}	
	}
	
	

	

}
