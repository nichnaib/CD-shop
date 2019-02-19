
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Point;
import java.awt.Toolkit;

public class PannelloPrincipale extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	public int columnSearch = 0;
	private JTable cd_table;
	public Cart cart = Cart.getCart();
	public String user = null;
	/**
	 * Create the frame.
	 */
	Connection c = null;
	public PannelloPrincipale(String user) {
		
		super("CDShop");
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*per centrare l'app al centro dello schermo
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		int jframeWidth=this.getSize().width;
		int jframeHeight=this.getSize().height;
				

		int locationX=(dim.width-jframeWidth)/2;
		int locationY=(dim.height-jframeHeight)/2;
		this.setLocation(locationX, locationY);*/
		
		if (user != null){
			JLabel lblWelcomeBack = new JLabel("Welcome " + user + "!");
			lblWelcomeBack.setBounds(657, 37, 141, 25);
			contentPane.add(lblWelcomeBack);
			
			JLabel lblEnjoyYourStaying = new JLabel("Enjoy your staying");
			lblEnjoyYourStaying.setBounds(657, 70, 141, 25);
			contentPane.add(lblEnjoyYourStaying);
			
			JButton btnRegistration = new JButton("Logout");
			btnRegistration.setBounds(657, 98, 141, 25);
			contentPane.add(btnRegistration);
			btnRegistration.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					JOptionPane.showMessageDialog(null, "Logout Successfull!");
					dispose();
					PannelloPrincipale pane = new PannelloPrincipale(null);
					pane.setVisible(true);
				}
			});
		} else {
			
			JButton btnRegistration = new JButton("Registration");
			btnRegistration.setBounds(657, 70, 141, 25);
			contentPane.add(btnRegistration);
			btnRegistration.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
					RegisterFrame rf = new RegisterFrame();
					rf.setVisible(true);
				}
			});
			
			JButton btnLogin= new JButton("Login");
			btnLogin.setBounds(657, 37, 141, 25);
			contentPane.add(btnLogin);
			btnLogin.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
					LoginFrame lf = new LoginFrame();
					lf.setVisible(true);
				}
			});
			
		}
		
		txtSearch = new JTextField();
		txtSearch.setBounds(12, 37, 504, 33);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnByBand = new JButton("by Band");
		btnByBand.setBounds(399, 70, 117, 25);
		contentPane.add(btnByBand);
		
		JButton btnSearchByPrice = new JButton("by Price");
		btnSearchByPrice.setBounds(12, 70, 117, 25);
		contentPane.add(btnSearchByPrice);
		
		JButton btnByMusician = new JButton("by Musician");
		btnByMusician.setBounds(141, 70, 117, 25);
		contentPane.add(btnByMusician);
		
		JButton btnByGenre = new JButton("by Genre");
		btnByGenre.setBounds(270, 70, 117, 25);
		contentPane.add(btnByGenre);
		
		
		btnSearchByPrice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				columnSearch = 2;
				btnSearchByPrice.setEnabled(false);
				btnByBand.setEnabled(true);
				btnByMusician.setEnabled(true);
				btnByGenre.setEnabled(true);
			}
		});
		
		btnByMusician.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				columnSearch = 5;
				btnSearchByPrice.setEnabled(true);
				btnByBand.setEnabled(true);
				btnByMusician.setEnabled(false);
				btnByGenre.setEnabled(true);
			}
		});
		
		btnByGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				columnSearch = 6;	
				btnSearchByPrice.setEnabled(true);
				btnByBand.setEnabled(true);
				btnByMusician.setEnabled(true);
				btnByGenre.setEnabled(false);
				}
			});
		
		btnByBand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				columnSearch = 4;	
				btnSearchByPrice.setEnabled(true);
				btnByBand.setEnabled(false);
				btnByMusician.setEnabled(true);
				btnByGenre.setEnabled(true);
				}
			});


		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(528, 37, 117, 61);
		btnSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(cd_table.getModel());
				RowFilter<TableModel, Object> rf = null;
			    try {
			    	if (columnSearch != 0){
			    		rf = RowFilter.regexFilter("(?i)"+txtSearch.getText(), columnSearch);	// testo, colonna
			    		columnSearch = 0;
			    	} else
			    		rf = RowFilter.regexFilter(txtSearch.getText(), columnSearch);	// testo, colonna
			    } catch (java.util.regex.PatternSyntaxException ec) {
			        return;
			    }
			    sorter.setRowFilter(rf);
			    cd_table.setRowSorter(sorter);
			    // disable all buttons
				btnSearchByPrice.setEnabled(true);
				btnByBand.setEnabled(true);
				btnByGenre.setEnabled(true);
				btnByMusician.setEnabled(true);
			}
		});
		contentPane.add(btnSearch);
		
		
		ArrayList<Cd> list = cdList();
		Object[][] data = new Object[list.size()][8];
		for (int i=0;i<list.size();i++){
			data[i][0]=list.get(i).getTitle();
			data[i][1]=list.get(i).getTitleSongs();
			data[i][2]=list.get(i).getPrice();
			data[i][3]=list.get(i).getDate();
			data[i][4]=list.get(i).getBand();
			data[i][5]=list.get(i).getMusicians();
			data[i][6]=list.get(i).getGenre();
			data[i][7]=list.get(i).getCdId();
		}
		
		cd_table = new JTable();
		cd_table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	try{
		        int row = cd_table.rowAtPoint(evt.getPoint());
		        int col = cd_table.columnAtPoint(evt.getPoint());
		        //in base alla riga premuta fa vedere le caratteristiche del prodotto
		        String id =  (String)cd_table.getModel().getValueAt(row, 7);
		        String title = (String)cd_table.getModel().getValueAt(row, 0);
		        String band = (String)cd_table.getModel().getValueAt(row, 4);
		        String price = (String)cd_table.getModel().getValueAt(row, 2);
		        JFrame view = new ViewProduct(id,cart);
		    	}
		    	catch (Exception e){
		    		
		    	}
		    }
		});
		cd_table.setModel(new DefaultTableModel(
			data,
			new String[] {
				"Title", "Title of Songs", "Price", "Date", "Band", "Musicians", "Genre","CdId"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		cd_table.getColumnModel().removeColumn(cd_table.getColumnModel().getColumn(7));
		
		cd_table.setFont(new Font("Dialog", Font.ITALIC, 13));
		cd_table.setBounds(12, 207, 810, 175);
		contentPane.add(cd_table);
		
		
		JButton btnViewCart = new JButton("View Cart");
		btnViewCart.setBounds(657, 135, 141, 25);
		contentPane.add(btnViewCart);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(12, 185, 36, 15);
		contentPane.add(lblTitle);
		
		JLabel lblTitleOfSongs = new JLabel("Title of Songs");
		lblTitleOfSongs.setBounds(126, 185, 104, 15);
		contentPane.add(lblTitleOfSongs);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(245, 185, 36, 15);
		contentPane.add(lblPrice);
		
		JLabel lblBand = new JLabel("Band");
		lblBand.setBounds(476, 185, 44, 15);
		contentPane.add(lblBand);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(359, 185, 44, 15);
		contentPane.add(lblDate);
		
		JLabel lblMusicians = new JLabel("Musicians");
		lblMusicians.setBounds(589, 185, 70, 15);
		contentPane.add(lblMusicians);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(706, 185, 70, 15);
		contentPane.add(lblGenre);
		btnViewCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ViewCart vc = new ViewCart(cart,user);
				vc.setVisible(true);
			}
		});
		
		this.setVisible(true);
	    
	}
	
	public ArrayList<Cd> cdList(){
		ArrayList<Cd> cdsList = new ArrayList<>();

		// connection to database
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cdshop","postgres", "postgres");
			String query = "select * from \"CD\"";
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			Cd cd;
			while(rs.next()){
				double p = (rs.getDouble(2)*100)/100;
				String price = String.valueOf(p);
				cd = new Cd(rs.getString("title"), rs.getString("titleSongs"),rs.getString("photo"),price, rs.getString("date"), rs.getString("band"), rs.getString("description"), rs.getString("genre"), rs.getString("musicians"),rs.getString("numberSongs"),rs.getString("cd_id"));
				cdsList.add(cd);
			}	
		} catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		
		return cdsList;
		
	}
	
}
