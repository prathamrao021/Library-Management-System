package Library;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ReturnBook extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField cus_id;
	private JTextField cus_name;
	private JTextField dor;
	private JTextField fees;
	private JTextField atbp;
	private JButton btnNewButton;
	private JButton btnReturnBook;

	static Connection con;
	static String url="jdbc:mysql://localhost:3306/LMS";
	static String user = "root";
	static String pass="0000";
	static String dr="com.mysql.jdbc.Driver";
	static Statement stmt;
	static ResultSet rs;
	private JButton btnNewButton_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnBook frame = new ReturnBook();
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
	public ReturnBook() {
		setTitle("Return Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book-Id");
		lblNewLabel_1.setBounds(12, 42, 94, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Customer-Id");
		lblNewLabel_2.setBounds(12, 71, 94, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Customer-Name");
		lblNewLabel_3.setBounds(12, 100, 94, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Date of Return");
		lblNewLabel_3_1.setBounds(12, 129, 94, 16);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Rental Fees");
		lblNewLabel_3_2.setBounds(12, 157, 94, 16);
		contentPane.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Net Amount");
		lblNewLabel_3_3.setBounds(12, 186, 94, 16);
		contentPane.add(lblNewLabel_3_3);
		
		id = new JTextField();
		id.setBounds(118, 39, 444, 22);
		contentPane.add(id);
		id.setColumns(10);
		
		cus_id = new JTextField();
		cus_id.setBounds(118, 68, 444, 22);
		cus_id.setColumns(10);
		contentPane.add(cus_id);
		
		cus_name = new JTextField();
		cus_name.setBounds(118, 97, 444, 22);
		cus_name.setColumns(10);
		contentPane.add(cus_name);
		
		dor = new JTextField();
		dor.setBounds(118, 126, 360, 22);
		dor.setColumns(10);
		contentPane.add(dor);
		
		fees = new JTextField();
		fees.setEditable(false);
		fees.setBounds(118, 154, 444, 22);
		fees.setColumns(10);
		contentPane.add(fees);
		
		atbp = new JTextField();
		atbp.setEditable(false);
		atbp.setBounds(118, 183, 444, 22);
		atbp.setColumns(10);
		contentPane.add(atbp);
		
		btnNewButton = new JButton("Back");
		btnNewButton.setBounds(12, 263, 97, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibraryManagement lib = new LibraryManagement();
				lib.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnNewButton);
		
		btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Class.forName(dr);
					con =  (Connection) DriverManager.getConnection(url, user, pass);
				
					String qry1 = "update addbook set Quantity = Quantity + 1 where Book_Id = "+id.getText();
					String qry2 = "delete from issuebook where Customer_Id = "+cus_id.getText()+" and Book_Id ="+id.getText();
					String qry3 = "insert into returnbook values ("+id.getText()+", "+cus_id.getText()+", '"+cus_name.getText()+"', '"+dor.getText()+"', '"+fees.getText()+"', "+atbp.getText()+")";
					stmt = (Statement) con.createStatement();
					stmt.executeUpdate(qry1);
					stmt.executeUpdate(qry2);
					stmt.executeUpdate(qry3);
				}
				catch(Exception e1)
				{
					System.out.println(e1);
				}
//				
				JOptionPane.showMessageDialog(null,"Book Returned Succesfully");
			}
		});
		btnReturnBook.setBounds(488, 263, 113, 25);
		contentPane.add(btnReturnBook);
		
		btnNewButton_1 = new JButton("Calculate");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					Class.forName(dr);
					con =  (Connection) DriverManager.getConnection(url, user, pass);
					String qry = "select DO_Issue from issuebook where Customer_Id =  "+cus_id.getText()+" and Book_Id ="+id.getText();
					stmt = (Statement) con.createStatement();
					rs = stmt.executeQuery(qry);
					String doi = null;
					
					if(rs.next())
					{					
						doi = rs.getString("DO_Issue");
						doi.replaceAll("-", "/");
					}
					String DOR = dor.getText();
					DOR.replaceAll("-", "/");
					qry = "select datediff('"+DOR+"', '"+doi+"')";
					rs = stmt.executeQuery(qry);
					int days = 0;
					if(rs.next())
					{
						days = rs.getInt("datediff('"+DOR+"', '"+doi+"')");
					}
					
					String f = days * 10 + "";
					
					fees.setText(f);
					
					qry = "select Amount_Paid from issuebook where Customer_Id = "+cus_id.getText()+" and Book_Id ="+id.getText();
					rs=stmt.executeQuery(qry);
					
					if(rs.next())
					{
						String j = Integer.parseInt(f) - rs.getInt("Amount_Paid")+ "";
						atbp.setText(j);
					}

					JOptionPane.showMessageDialog(null, "Calculated Successfully");
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
		});
		btnNewButton_1.setBounds(477, 125, 86, 25);
		contentPane.add(btnNewButton_1);
		
		
	}
}
