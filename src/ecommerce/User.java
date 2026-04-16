package ecommerce;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class User extends JFrame {
	private JTable table;
	private DefaultTableModel model;
	public static String firstName;
	public static String lastName;
	public static int age;
	public static String sex;
	public static String mobile;
	public static String email;
	public static String password;

	public User() {
		try {
			ResultSet r = GlobalVariables.statement
					.executeQuery(String.format("Select * from `user` where `id` = '%d';", GlobalVariables.userID));
			r.next();
			firstName = r.getString("first_name");
			lastName = r.getString("last_name");
			age = r.getInt("age");
			sex = r.getString("sex");
			mobile = r.getString("mobile");
			email = r.getString("email");
			password = r.getString("password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("User Information");
		setSize(800, 400);
		setLocationRelativeTo(null); // Center the frame on the screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a table model with columns
		String[] columns = { "Field Name", "Data" };
		model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Disable editing by default
			}
		};
		table = new JTable(model);

		// Add user data to the table
		Map<String, String> userData = getUserData(); // Example data, replace this with actual user data
		for (Map.Entry<String, String> entry : userData.entrySet()) {
			model.addRow(new Object[] { entry.getKey(), entry.getValue() });
		}

		// Create edit button
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == 8) {
					new wallet();
				} else if (selectedRow != -1) {
					System.out.print(selectedRow);
					// Open a menu or dialog to edit the selected user's information
					String fieldName = (String) model.getValueAt(selectedRow, 0);
					String currentData = (String) model.getValueAt(selectedRow, 1);
					String newData = JOptionPane.showInputDialog(null, "Enter new data for " + fieldName + ":",
							currentData);
					if (newData != null) {
						model.setValueAt(newData, selectedRow, 1);
						switch (fieldName) {
						case "First Name":
							firstName = newData;
							break;
						case "Last Name":
							lastName = newData;
							break;
						case "Email":
							email = newData;
							break;
						case "Age":
							age = Integer.parseInt(newData);
							break;
						case "Sex":
							sex = newData;
							break;
						case "Mobile Number":
							mobile = newData;
							break;
						case "Password":
							password = newData;
							break;
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a field to edit.");
				}
			}
		});

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GlobalVariables.statement.executeUpdate(String.format(
							"UPDATE user SET `first_name` = '%s', `last_name` = '%s', `age` = '%d', `sex` = '%s', `email` = '%s', `mobile` = '%s', `password` = '%s' WHERE `id` = '%d';",
							firstName, lastName, age, sex, email, mobile, password, GlobalVariables.userID));
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(getParent(), e1.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// Add components to a panel
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.add(editButton, BorderLayout.NORTH);
		panel2.add(updateButton, BorderLayout.SOUTH);
		panel.add(panel2, BorderLayout.SOUTH);

		// Add the panel to the frame
		add(panel);

		setVisible(true);
	}

	private Map<String, String> getUserData() {
		// Example user data, replace this with your actual user data
		Map<String, String> userData = new LinkedHashMap<>();
		userData.put("First Name", firstName);
		userData.put("Last Name", lastName);
		userData.put("Age", "" + age);
		userData.put("Sex", sex);
		userData.put("Mobile Number", mobile);
		userData.put("Email", email);
		userData.put("Password", password);
		return userData;
	}

}
