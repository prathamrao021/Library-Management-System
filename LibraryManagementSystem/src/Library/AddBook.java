package Library;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class AddBook extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField author;
	private JTextField publisher;
	private JTextField date;
	private JTextField price;
	private JTextField id;
	private JTextField quantity;
	
	static Connection con;
	static String url="jdbc:mysql://localhost:3306/LMS";
	static String user = "root";
	static String pass="0000";
	static String dr="com.mysql.jdbc.Driver";
	static Statement stmt;
	static ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 AddBook frame = new AddBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void loadata()
	{
		try
		{
			Class.forName(dr);
			con =  (Connection) DriverManager.getConnection(url, user, pass);
			stmt = (Statement) con.createStatement();
			String qry = "select * from addbook;";
			rs = stmt.executeQuery(qry);
			
			while(rs.next())
			{
				int bookid = rs.getInt("Book_Id");
				bookid++;
				id.setText(bookid + "");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

	/**
	 * Create the frame.
	 */
	public AddBook() {
		setTitle("Add Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(12, 58, 56, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(12, 87, 56, 16);
		contentPane.add(lblAuthor);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setBounds(12, 116, 56, 16);
		contentPane.add(lblPublisher);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(12, 145, 56, 16);
		contentPane.add(lblDate);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(12, 174, 56, 16);
		contentPane.add(lblPrice);
		
		name = new JTextField();
		name.setBounds(90, 55, 385, 22);
		contentPane.add(name);
		name.setColumns(10);
		
		author = new JTextField();
		author.setColumns(10);
		author.setBounds(89, 84, 386, 22);
		contentPane.add(author);
		
		publisher = new JTextField();
		publisher.setColumns(10);
		publisher.setBounds(89, 113, 385, 22);
		contentPane.add(publisher);
		
		date = new JTextField();
		date.setColumns(10);
		date.setBounds(89, 142, 386, 22);
		contentPane.add(date);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(89, 171, 386, 22);
		contentPane.add(price);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LibraryManagement lib = new LibraryManagement();
				lib.setVisible(true);
			}
		});
		back.setBounds(12, 243, 97, 25);
		contentPane.add(back);
		
		JButton add = new JButton("Add Book");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Class.forName(dr);
					con =  (Connection) DriverManager.getConnection(url, user, pass);
					String qry = "insert into addbook values ("+id.getText()+", '"+name.getText()+"', '"+author.getText()+"', '"+publisher.getText()+"', '"+date.getText()+"', "+price.getText()+", "+quantity.getText()+")";
					stmt = (Statement) con.createStatement();
					stmt.executeUpdate(qry);
					JOptionPane.showMessageDialog(null, "Insert Successfully");
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				System.out.println("Name:"+ name.getText());
				System.out.println("Author:"+ author.getText());
				System.out.println("Publisher:"+ publisher.getText());
				System.out.println("Date:"+ date.getText());
				System.out.println("Quantity:"+ price.getText());
			}
		});
		add.setBounds(427, 243, 97, 25);
		contentPane.add(add);
		
		JLabel lblBookid = new JLabel("Book-Id");
		lblBookid.setBounds(12, 29, 56, 16);
		contentPane.add(lblBookid);
		
		id = new JTextField();
		id.setEditable(false);
		id.setColumns(10);
		id.setBounds(90, 26, 385, 22);
		contentPane.add(id);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		lblQuantity_1.setBounds(12, 203, 56, 16);
		contentPane.add(lblQuantity_1);
		
		quantity = new JTextField();
		quantity.setColumns(10);
		quantity.setBounds(89, 200, 386, 22);
		contentPane.add(quantity);
		
		loadata();
	}
}
