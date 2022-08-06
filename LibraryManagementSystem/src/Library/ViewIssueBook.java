package Library;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewIssueBook extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
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
					ViewIssueBook frame = new ViewIssueBook();
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
	public ViewIssueBook() {
		setTitle("View Issued Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 560, 229);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Customer ID", "Book ID", "Customer Name", "Date Of Issue", "Contact", "Pre-pay", "Post-pay", "Security Deposit", "Amount Paid"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(116);
		table.getColumnModel().getColumn(2).setPreferredWidth(105);
		table.getColumnModel().getColumn(3).setPreferredWidth(102);
		table.getColumnModel().getColumn(7).setPreferredWidth(102);
		table.getColumnModel().getColumn(8).setPreferredWidth(88);
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibraryManagement lib = new LibraryManagement();
				lib.setVisible(true);
				dispose();
			}
		});
		back.setBounds(10, 285, 97, 25);
		contentPane.add(back);
		
		try 
		{
			Class.forName(dr);
			con =  (Connection) DriverManager.getConnection(url, user, pass);
			stmt = (Statement) con.createStatement();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String qry = "select * from issuebook;";
			rs = stmt.executeQuery(qry);
			
			while(rs.next())
			{
				String cid = rs.getInt("Customer_Id")+"";
				String bid = rs.getInt("Book_Id")+"";
				String cname = rs.getString("Customer_Name");
				String doi = rs.getString("DO_Issue");
				String cont = rs.getString("Contact");
				String prepay = rs.getString("Prepay");
				String postpay = rs.getString("Postpay");
				String sd = rs.getString("Security_Dep");
				String ap = rs.getString("Amount_Paid");
				model.addRow(new Object [] {cid, bid, cname, doi, cont, prepay, postpay, sd, ap});
			}
		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}
}
