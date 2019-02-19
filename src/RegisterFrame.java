import java.awt.BorderLayout;
import java.sql.*;
import java.util.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame{

	private JPanel contentPane;
	private JTextField usernameText ;
	private JLabel lblName;
	private JLabel lblSurname;
	private JLabel lblFiscalCode;
	private JLabel lblCity;
	private JLabel lblTelephone;
	private JLabel lblCellphone;
	private JTextField passwordText;
	private JTextField nameText;
	private JTextField surnameText;
	private JTextField fiscalCodeText;
	private JTextField cityText;
	private JTextField telephoneText;
	private JTextField cellphoneText;
	private JLabel lblPassword;
	private JLabel lbloptional;
	private JButton btnCancel;
	private JButton btnSubmit;
	private static String[] campi = new String[8];
	static String regex1 = "\\d+";
	static String regex2 = "^[a-zA-Z]+$";
	static String regex3 = "\\d";
	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		super("Registration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 450);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setBounds(29, 35, 95, 15);
		contentPane.add(lblUsername);

		lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(29, 77, 95, 15);
		contentPane.add(lblPassword);
		JLabel lblSuggPassw = new JLabel("* Password must be at least 4 characters");
		lblSuggPassw.setFont(new Font("Arial", Font.ITALIC, 10)); 
		lblSuggPassw.setBounds(39, 104, 320, 12);
		getContentPane().add(lblSuggPassw);

		lblName = new JLabel("Name : ");
		lblName.setBounds(29, 133, 95, 15);
		contentPane.add(lblName);

		lblSurname = new JLabel("Surname  : ");
		lblSurname.setBounds(29, 174, 95, 15);
		contentPane.add(lblSurname);

		lblFiscalCode = new JLabel("Fiscal code : ");
		lblFiscalCode.setBounds(29, 217, 95, 15);
		contentPane.add(lblFiscalCode);

		lblCity = new JLabel("City : ");
		lblCity.setBounds(29, 255, 95, 15);
		contentPane.add(lblCity);

		lblTelephone = new JLabel("Telephone : ");
		lblTelephone.setBounds(29, 300, 95, 15);
		contentPane.add(lblTelephone);

		lblCellphone = new JLabel("Cellphone  : ");
		lblCellphone.setBounds(29, 347, 95, 15);
		contentPane.add(lblCellphone);

		usernameText = new JTextField();
		usernameText.setBounds(114, 30, 159, 25);
		contentPane.add(usernameText);
		usernameText.setColumns(10);


		passwordText = new JPasswordField();
		passwordText.setColumns(10);
		passwordText.setBounds(114, 72, 159, 25);
		contentPane.add(passwordText);


		nameText = new JTextField();
		nameText.setColumns(10);
		nameText.setBounds(114, 128, 159, 25);
		contentPane.add(nameText);


		surnameText = new JTextField();
		surnameText.setColumns(10);
		surnameText.setBounds(114, 169, 159, 25);
		contentPane.add(surnameText);


		fiscalCodeText = new JTextField();
		fiscalCodeText.setColumns(10);
		fiscalCodeText.setBounds(114, 212, 159, 25);
		contentPane.add(fiscalCodeText);


		cityText = new JTextField();
		cityText.setColumns(10);
		cityText.setBounds(114, 250, 159, 25);
		contentPane.add(cityText);


		telephoneText = new JTextField();
		telephoneText.setColumns(10);
		telephoneText.setBounds(114, 295, 159, 25);
		contentPane.add(telephoneText);


		cellphoneText = new JTextField();
		cellphoneText.setColumns(10);
		cellphoneText.setBounds(114, 342, 159, 25);
		contentPane.add(cellphoneText);


		JLabel lblSuggCellphone = new JLabel("* Optional");
		lblSuggCellphone.setFont(new Font("Arial", Font.ITALIC, 10)); 
		lblSuggCellphone.setBounds(39, 367, 320, 19);
		getContentPane().add(lblSuggCellphone);


		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(29, 398, 117, 25);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
				PannelloPrincipale p = new PannelloPrincipale(null);
				p.setVisible(true);
			}
		});


		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(158, 398, 117, 25);
		contentPane.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent s) {
				
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
				String nome=nameText.getText();
				String cognome=surnameText.getText();
				String CF=fiscalCodeText.getText();
				String città=cityText.getText();
				String telefono = telephoneText.getText();
				String cellulare = cellphoneText.getText();

				campi[0] = username;
				campi[1] = password;
				campi[2] = nome;
				campi[3] = cognome;
				campi[4] = CF;
				campi[5] = città;
				campi[6] = telefono;
				campi[7] = cellulare;

				boolean check = false;
				if (campi[0].isEmpty()){
					JOptionPane.showMessageDialog(null,"Invalid username!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}else{
					CheckUsernameExists(campi[0],c);
					check=CheckUsernameExists(campi[0],c);
					if(check){
						JOptionPane.showMessageDialog(null, "Username already exists! Try again!");
						RegisterFrame rf = new RegisterFrame();
						rf.setVisible(true);
						return;
					}
				}

				if( campi[1].length() < 4){
					JOptionPane.showMessageDialog(null,"Invalid password, must be at least 4 characters!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (!campi[2].matches(regex2)){
					JOptionPane.showMessageDialog(null,"Invalid name!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (!campi[3].matches(regex2)){
					JOptionPane.showMessageDialog(null,"Invalid surname!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (campi[4].length() != 16){
					JOptionPane.showMessageDialog(null,"Invalid fiscal code!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (!campi[5].matches(regex2)){
					JOptionPane.showMessageDialog(null,"Invalid city name!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}	
				if (campi[6].isEmpty()){
					JOptionPane.showMessageDialog(null, "You must insert a telephone number!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (!campi[6].matches(regex1) || campi[6].length()!=10){
					JOptionPane.showMessageDialog(null,"Invalid telephone number!");
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
					return;
				}
				if (!campi[7].matches(regex1)){
					System.out.println(cellulare);
					if(!campi[7].isEmpty()){
						JOptionPane.showMessageDialog(null,"Invalid cellphone number!");
						RegisterFrame rf = new RegisterFrame();
						rf.setVisible(true);
						return;
					}	
				}
				try {
					c.setAutoCommit(false);

					String sql;
					if (campi[7].isEmpty())
						sql = "INSERT INTO \"User\" (\"CF\",username,password,nome,cognome,\"città\",telefono) values (?,?,?,?,?,?,?)";
					else if (campi[7].length()==10)
						sql = "INSERT INTO \"User\" (\"CF\",username,password,nome,cognome,\"città\",telefono,cellulare) values (?,?,?,?,?,?,?,?)";
					else {
						JOptionPane.showMessageDialog(null,"Invalid cellphone number!");

						return;
					}
					PreparedStatement ps = c.prepareStatement(sql);
					ps.setString(1, CF);
					ps.setString(2, username);
					ps.setString(3, password);
					ps.setString(4, nome);
					ps.setString(5, cognome);
					ps.setString(6, città);
					ps.setString(7, telefono);
					if (!campi[7].isEmpty()){
						ps.setString(8, cellulare);
					}

					// update database
					ps.executeUpdate();

					c.commit();
					c.close();
				} catch (Exception e) {
					System.err.println( e.getClass().getName()+": "+ e.getMessage() );
					System.exit(0);
				}

				JOptionPane.showMessageDialog(null,"The registration has been succesfully created ");

				PannelloPrincipale prot= new PannelloPrincipale(username);
				prot.setVisible(true);


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


}
