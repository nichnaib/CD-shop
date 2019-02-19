import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ViewCart extends JFrame {

	private JPanel contentPane;
	private JTable cart_table;
	private String consegna,pagamento;
	private ButtonGroup group2, group1;
	
	/**
	 * Create the frame.
	 */
	public ViewCart(Cart cart, String user) {
		super("Your Cart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 427, 570);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cart_table=new JTable();
	
		
		pagamento = "";
		consegna = "";
		
		JLabel lblDeliveryType = new JLabel("Delivery type : ");
		lblDeliveryType.setBounds(12, 261, 130, 15);
		contentPane.add(lblDeliveryType);
		
		JRadioButton rdbtnMail = new JRadioButton("Mail");
		rdbtnMail.setBounds(12, 300, 96, 23);
		contentPane.add(rdbtnMail);
		if(rdbtnMail.isSelected()){
			consegna = "mail";
		}
		
		JRadioButton rdbtnCourier = new JRadioButton("Courier");
		rdbtnCourier.setBounds(135, 300, 149, 23);
		contentPane.add(rdbtnCourier);
		if(rdbtnCourier.isSelected()){
			consegna = "courier";
		}
		
		JRadioButton rdbtnPriorityMail = new JRadioButton("Priority mail");
		rdbtnPriorityMail.setBounds(293, 300, 149, 23);
		contentPane.add(rdbtnPriorityMail);
		if(rdbtnPriorityMail.isSelected()){
			consegna = "priority mail";
		}
		
		
		//raggrupamento bottoni spedizione
		group1=new ButtonGroup();
		group1.add(rdbtnMail);
		group1.add(rdbtnCourier);
		group1.add(rdbtnPriorityMail);
		
		
		JRadioButton rdbtnCreditCard = new JRadioButton("Credit Card");
		rdbtnCreditCard.setBounds(135, 446, 149, 23);
		contentPane.add(rdbtnCreditCard);
		
		JRadioButton rdbtnBancomat = new JRadioButton("Bancomat");
		rdbtnBancomat.setBounds(293, 446, 149, 23);
		contentPane.add(rdbtnBancomat);
		
		JRadioButton rdbtnPaypal_1 = new JRadioButton("Paypal");
		rdbtnPaypal_1.setBounds(12, 446, 107, 23);
		contentPane.add(rdbtnPaypal_1);
			
		
		JLabel lblPaymentType = new JLabel("Payment type : ");
		lblPaymentType.setBounds(12, 408, 123, 15);
		contentPane.add(lblPaymentType);
		
		//raggruppamenti bottoni pagamento
		group2=new ButtonGroup();
		group2.add(rdbtnCreditCard);
		group2.add(rdbtnBancomat);
		group2.add(rdbtnPaypal_1);
		
		JButton btnBuy = new JButton("Buy");
		btnBuy.setBounds(292, 520, 117, 25);
		contentPane.add(btnBuy);
		btnBuy.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				for (Enumeration<AbstractButton> buttons = group2.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		            	pagamento = button.getText();
		            	break;
		            }
		        }
				
				for (Enumeration<AbstractButton> buttons = group1.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		            	consegna = button.getText();
		            	break;
		            }
		        }
				
				
				if (cart.isEmpty()){
					JOptionPane.showMessageDialog(null, "The cart is empty!");
					dispose();
					
				} else if(user == null){
					JOptionPane.showMessageDialog(null, "You must be registered to buy!\nPlease log in or submit");
					dispose();
					
				} else if(consegna == null || consegna == ""){
					JOptionPane.showMessageDialog(null, "You must choose a delivery mode!");
					
				} else if(pagamento == null || pagamento == ""){
					JOptionPane.showMessageDialog(null, "You must choose a paymode!");
					
				} else {
					try {
						Class.forName("org.postgresql.Driver");
						Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cdshop","postgres", "postgres");
						
						String query = "insert into \"Vendita\" (prezzo_tot,mod_pagamento,mod_consegna,ip,cliente,lista_prodotti) values (?,?,?,?,?,?);";
						PreparedStatement ps = c.prepareStatement(query);
						
						ps.setDouble(1, getTotal(c));
						ps.setString(2, pagamento);
						ps.setString(3, consegna);
						ps.setString(4, "190.53.68.1");
						ps.setString(5, user);
						String lista_prodotti = "";
						for(String cd : Cart.getCart().getCds()){
							lista_prodotti+= cd + ",";
						}
						lista_prodotti=lista_prodotti.substring(0,lista_prodotti.length()-1);
						ps.setString(6, lista_prodotti);
						ps.execute();
						
						JOptionPane.showMessageDialog(null, "You successfuly buy!\nThank you for purchasing our product");
						Cart.getCart().clearCart();
						dispose();

					} catch(Exception ee){
						System.err.println( e.getClass().getName()+": "+ ee.getMessage() );
						System.exit(0);
					};
				}
			}
				
		});
		
		
		JLabel lblYourCartContains = new JLabel("Your cart contains :");
		lblYourCartContains.setBounds(12, 12, 183, 15);
		contentPane.add(lblYourCartContains);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(12, 520, 117, 25);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		JButton btnDeleteCart = new JButton("Delete cart");
		btnDeleteCart.setBounds(150, 520, 117, 25);
		contentPane.add(btnDeleteCart);
		btnDeleteCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cart.clearCart();
				dispose();
				ViewCart vc = new ViewCart(cart,user);
				vc.setVisible(true);
			}
		});
		
		JButton btnAddMore = new JButton("Add more");
		btnAddMore.setBounds(12, 224, 117, 25);
		contentPane.add(btnAddMore);
		btnAddMore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});

		Connection c = null;
		List<String> ids = cart.getCds();
		
		ArrayList<Cd> list = new ArrayList<Cd>();
			// connection to database
			try {
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cdshop","postgres", "postgres");
				for(String i : ids){
					String query = "select * from \"CD\" where cd_id = "+i;
					try(Statement st = c.createStatement()){
						ResultSet rs = st.executeQuery(query);
						Cd cd;
						if(rs.next()){
							double p = (rs.getDouble(2)*100)/100;
							String price = String.valueOf(p);
							cd = new Cd(rs.getString("title"), rs.getString("titleSongs"),rs.getString("photo"),price, rs.getString("date"), rs.getString("band"), rs.getString("description"), rs.getString("genre"), rs.getString("musicians"),rs.getString("numberSongs"),rs.getString("cd_id"));
							list.add(cd);
							
						}
					}
				}
				
			} catch (Exception e) {
				System.err.println( e.getClass().getName()+": "+ e.getMessage() );
				System.exit(0);
			}
		
		Object[][] data = new Object[list.size()][4];
		for (int i=0;i<list.size();i++){
			data[i][0]=list.get(i).getTitle();
			data[i][1]=list.get(i).getBand();
			data[i][2]=list.get(i).getGenre();
			data[i][3]=list.get(i).getPrice();
		}
		cart_table=new JTable();
		cart_table.setModel(new DefaultTableModel(
				data,
				new String[] {
					"New column", "New column", "New column", "New column"				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		
		TableModel model = cart_table.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);

		cart_table.setRowSorter(sorter);
		
		cart_table.setFont(new Font("Dialog", Font.ITALIC, 13));
		cart_table.setBounds(12, 56, 397, 158);
		contentPane.add(cart_table);
		
		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setBounds(12, 39, 70, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblBand = new JLabel("Band");
		lblBand.setBounds(111, 39, 70, 15);
		contentPane.add(lblBand);
		
		JLabel lblBand_1 = new JLabel("Price");
		lblBand_1.setBounds(309, 39, 70, 15);
		contentPane.add(lblBand_1);
		
		JLabel lblGenre = new JLabel("Genre");
		lblGenre.setBounds(209, 39, 70, 15);
		contentPane.add(lblGenre);
		}
	
		public double getTotal(Connection c) {
			double tot = 0;
			for(String cd : Cart.getCart().getCds()){
				String query = "select price from \"CD\" where cd_id = "+cd;
				try(Statement st = c.createStatement()){
					ResultSet rs = st.executeQuery(query);
					if(rs.next()){
						tot += rs.getDouble("price");
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return tot;
		}
}
