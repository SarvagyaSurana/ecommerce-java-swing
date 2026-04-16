package ecommerce;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Product extends JFrame {
	private JTable table;
	private DefaultTableModel model;
	public static String name;
	public static String description;
	public static String category;
	public static int quantity;
	public static int discount;
	public static double price;
	public static int pID;

	public Product(String n, String desc, String c, int q, int disc, double p, int id) {
		name = n;
		description = desc;
		category = c;
		quantity = q;
		discount = disc;
		price = p;
		pID = id;

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
				if (selectedRow != -1) {
					System.out.print(selectedRow);
					// Open a menu or dialog to edit the selected user's information
					String fieldName = (String) model.getValueAt(selectedRow, 0);
					String currentData = (String) model.getValueAt(selectedRow, 1);
					String newData = JOptionPane.showInputDialog(null, "Enter new data for " + fieldName + ":",
							currentData);
					if (newData != null) {
						model.setValueAt(newData, selectedRow, 1);
						switch (fieldName) {
						case "Name":
							name = newData;
							break;
						case "Description":
							description = newData;
							break;
						case "Category":
							category = newData;
							break;
						case "Price":
							price = Float.parseFloat(newData);
							break;
						case "Quantity":
							quantity = Integer.parseInt(newData);
							break;
						case "Discount":
							discount = Integer.parseInt(newData);
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
							"UPDATE item SET `name` = '%s', `description` = '%s', `category` = '%s', `price` = '%f', `quantity` = '%d', `discount` = '%d' WHERE `id` = '%d';",
							name, description, category, price, quantity, discount, pID));
					dispose();
					SellerPage.refreshScreen();
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
		userData.put("Name", name);
		userData.put("Description", description);
		userData.put("Category", category);
		userData.put("Price", "" + price);
		userData.put("Discount", "" + discount);
		userData.put("Quantity", "" + quantity);
		return userData;
	}

}
