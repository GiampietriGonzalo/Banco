package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import quick.dbtable.DBTable;

public class VentanaAdmin extends JFrame {

	private JPanel contentPane;
	private DBTable tabla;  

	
	public VentanaAdmin() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
    
        setContentPane(contentPane);
		
		
	}

}
