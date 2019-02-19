import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernameText;
	private JTextField passwordText;
	private static String[] campi = new String[2];
	static String regex = "^[a-zA-Z]+$";
	
	/**
	 * Create the frame.
	 */
	
	public LoginFrame() {
		super("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 270, 170);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(12, 34, 159, 15);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(12, 80, 159, 15);
		contentPane.add(lblPassword);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(12, 125, 117, 25);
		contentPane.add(btnCancel);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(141, 125, 117, 25);
		contentPane.add(btnSubmit);
		
		usernameText = new JTextField();
		usernameText.setBounds(98, 29, 159, 25);
		contentPane.add(usernameText);
		usernameText.setColumns(10);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(98, 75, 159, 25);
		contentPane.add(passwordText);
		passwordText.setColumns(10);
		
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				PannelloPrincipale p = new PannelloPrincipale(null);
				p.setVisible(true);
			}
		});

		
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent s) {
				// TODO Auto-generated method stub
				dispose();
				Connection c = null;

				
				// connection to database
				try {
					Class.forName("org.postgresql.Driver");
					c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cdshop","postgres", "postgres");
				} catch (Exception e) {
					System.err.println( e.getClass().getName()+": "+ e.getMessage() );
					System.exit(0);
				}
				
				String username=usernameText.getText();
				String password=passwordText.getText();
				
				campi[0] = username;
				campi[1] = password;
				boolean check=false;
				if (campi[0].isEmpty()){
					JOptionPane.showMessageDialog(null,"You must insert an username!");
					LoginFrame lf = new LoginFrame();
					lf.setVisible(true);
					return;
				}else{
					CheckUsernameExists(campi[0],c);

					if (CheckUsernameExists(campi[0],c)) {
						check=CheckPassword(campi[0],campi[1],c);
						
					}
				}

				if (check){
					JOptionPane.showMessageDialog(null, "Login successfull, authenticated!");
					PannelloPrincipale prot = new PannelloPrincipale(username);
					prot.setVisible(true);
				} 
				else {
					JOptionPane.showMessageDialog(null,"Login failed, invalid username or password!");
					LoginFrame lf = new LoginFrame();
					lf.setVisible(true);

				}
			}
		});
		
	}

	public boolean CheckUsernameExists(String username, Connection c) {

		boolean usernameExists = false;
		
		try {
			PreparedStatement st = c.prepareStatement("select username from \"User\"");
			ResultSet r1 = st.executeQuery();
			while(r1.next()){
				String db_username = r1.getObject(1).toString();
				if(db_username.equals(username)){
					usernameExists = true;
					break;
				}
			}
		}
		catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		}
		return usernameExists;
	}
	
	public boolean CheckPassword(String username, String password, Connection c){
		
		boolean correctUser = false;
		try{
			String str ="'";
			String user = username.concat(str);
			String user2 = str.concat(user);
			PreparedStatement st = c.prepareStatement("select password from \"User\" where username = "+user2);
			ResultSet r1 = st.executeQuery();
			while(r1.next()){
				String db_password = r1.getObject(1).toString();
				if(db_password.equals(password)){
					correctUser = true;
					break;
				}
			}
		} catch (SQLException e){
			System.out.println("SQL Exception: " + e.toString());
		}
		return correctUser;
	}
	
	
	
	
	
}
