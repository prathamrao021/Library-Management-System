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
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import java.awt.List;
import java.awt.Checkbox;
import java.awt.Choice;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class IssueBook extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField id;
	private JTextField cus_id;
	private JTextField cus_name;
	private JTextField doi;
	private JTextField contact;
	private JTextField deposit;
	private JTextField paid;
	
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
					IssueBook frame = new IssueBook();
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
	public IssueBook() {
		setTitle("Issue Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblid = new JLabel("Book-Id");
		lblid.setBounds(12, 33, 101, 16);
		contentPane.add(lblid);
		
		JLabel lblCustomerid = new JLabel("Customer-Id");
		lblCustomerid.setBounds(12, 62, 101, 16);
		contentPane.add(lblCustomerid);
		
		JLabel lblid_1_1 = new JLabel("Customer-Name");
		lblid_1_1.setBounds(12, 91, 101, 16);
		contentPane.add(lblid_1_1);
		
		JLabel lblid_1_2 = new JLabel("Date of Issue");
		lblid_1_2.setBounds(12, 120, 101, 16);
		contentPane.add(lblid_1_2);
		
		JLabel lblid_1_3 = new JLabel("Contact Number");
		lblid_1_3.setBounds(12, 149, 101, 16);
		contentPane.add(lblid_1_3);
		
		JRadioButton prepay = new JRadioButton("Pre-payment");
		
		buttonGroup.add(prepay);
		prepay.setBounds(12, 180, 127, 25);
		contentPane.add(prepay);
		
		JRadioButton postpay = new JRadioButton("Post-payment");
		buttonGroup.add(postpay);
		postpay.setBounds(12, 210, 127, 25);
		contentPane.add(postpay);
		
		id = new JTextField();
		id.setBounds(129, 30, 365, 22);
		contentPane.add(id);
		id.setColumns(10);
		
		cus_id = new JTextField();
		cus_id.setColumns(10);
		cus_id.setBounds(129, 59, 365, 22);
		contentPane.add(cus_id);
		
		cus_name = new JTextField();
		cus_name.setColumns(10);
		cus_name.setBounds(129, 88, 365, 22);
		contentPane.add(cus_name);
		
		doi = new JTextField();
		doi.setColumns(10);
		doi.setBounds(129, 117, 365, 22);
		contentPane.add(doi);
		
		contact = new JTextField();
		contact.setColumns(10);
		contact.setBounds(129, 146, 365, 22);
		contentPane.add(contact);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LibraryManagement lib = new LibraryManagement();
				lib.setVisible(true);
			}
		});
		back.setBounds(12, 342, 97, 25);
		contentPane.add(back);
		
		JButton btnIssueBook = new JButton("Issue Book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					Class.forName(dr);
					con =  (Connection) DriverManager.getConnection(url, user, pass);
					int i= (prepay.isSelected())?1:0;
					int k= (i==1)?0:1;
					
					String a_paid= (postpay.isSelected())?"0":paid.getText();
					String qry = "insert into issuebook values ("+cus_id.getText()+", "+id.getText()+", '"+cus_name.getText()+"', '"+doi.getText()+"', '"+contact.getText()+"', "+i+", "+k+", "+deposit.getText()+", "+a_paid+")";
					String qry1 = "update addbook set Quantity = Quantity - 1 where Book_Id = "+id.getText();
					stmt = (Statement) con.createStatement();
					stmt.executeUpdate(qry);
					stmt.executeUpdate(qry1);
					JOptionPane.showMessageDialog(null, "Insert Successfully");
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		btnIssueBook.setBounds(413, 342, 97, 25);
		contentPane.add(btnIssueBook);
		
		JLabel lbldeposit = new JLabel("Security Deposit");
		lbldeposit.setBounds(12, 254, 101, 16);
		contentPane.add(lbldeposit);
		
		deposit = new JTextField();
		deposit.setToolTipText(".'. full price of the issued book");
		deposit.setColumns(10);
		deposit.setBounds(129, 251, 365, 22);
		contentPane.add(deposit);
		
		JLabel lblpaid = new JLabel("Amount Paid");
		lblpaid.setBounds(12, 286, 101, 16);
		contentPane.add(lblpaid);

		postpay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(postpay.isSelected())
				{
					paid.setVisible(false);
					lblpaid.setVisible(false);
				}
			}
		});
		
		prepay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(prepay.isSelected())
				{
					paid.setVisible(true);
					lblpaid.setVisible(true);
				}
			}
		});
		
		paid = new JTextField();
		paid.setColumns(10);
		paid.setBounds(129, 283, 365, 22);
		contentPane.add(paid);
	}
}
