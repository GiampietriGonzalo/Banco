package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField TFAccount;
	private JTextField TFPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setBackground(Color.DARK_GRAY);
		setForeground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 201);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox CBUser = new JComboBox();
		CBUser.setToolTipText("");
		CBUser.setForeground(Color.WHITE);
		CBUser.setBackground(Color.DARK_GRAY);
		CBUser.setBounds(82, 12, 220, 24);
		contentPane.add(CBUser);
		
		TFAccount = new JTextField();
		TFAccount.setForeground(Color.WHITE);
		TFAccount.setBackground(Color.DARK_GRAY);
		TFAccount.setBounds(82, 59, 220, 19);
		contentPane.add(TFAccount);
		TFAccount.setColumns(10);
		
		TFPassword = new JTextField();
		TFPassword.setForeground(Color.WHITE);
		TFPassword.setBackground(Color.DARK_GRAY);
		TFPassword.setBounds(82, 101, 220, 19);
		contentPane.add(TFPassword);
		TFPassword.setColumns(10);
		
		JButton BTNSalir = new JButton("Salir");
		BTNSalir.setForeground(Color.WHITE);
		BTNSalir.setBackground(Color.DARK_GRAY);
		BTNSalir.setBounds(81, 138, 94, 25);
		contentPane.add(BTNSalir);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(208, 138, 94, 25);
		contentPane.add(btnNewButton);
	}
}
