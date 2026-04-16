package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductsFrame extends JFrame {
	private JTextField productNameField;
	private JTextField categoryField;
	private JTextField discountField;
	private JTextField priceField;
	private JTextArea descriptionArea;
	private JTextField quantityField;

	public AddProductsFrame() {
		setTitle("Add Products");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));

		JLabel productNameLabel = new JLabel("Product Name:");
		productNameField = new JTextField();
		panel.add(productNameLabel);
		panel.add(productNameField);

		JLabel categoryLabel = new JLabel("Category:");
		categoryField = new JTextField();
		panel.add(categoryLabel);
		panel.add(categoryField);

		JLabel discountLabel = new JLabel("Discount:");
		discountField = new JTextField();
		panel.add(discountLabel);
		panel.add(discountField);

		JLabel priceLabel = new JLabel("Price:");
		priceField = new JTextField();
		panel.add(priceLabel);
		panel.add(priceField);

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(descriptionArea);
		panel.add(descriptionLabel);
		panel.add(scrollPane);

		JLabel quantityLabel = new JLabel("Quantity:");
		quantityField = new JTextField();
		panel.add(quantityLabel);
		panel.add(quantityField);

		JButton addButton = new JButton("Add Product");
		addButton.addActionListener(e -> addProduct());
		panel.add(addButton);

		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addProduct() {
		String productName = productNameField.getText();
		String category = categoryField.getText();
		int discount = Integer.parseInt(discountField.getText());
		double price = Double.parseDouble(priceField.getText());
		String description = descriptionArea.getText();
		int quantity = Integer.parseInt(quantityField.getText());

		try {
			ResultSet r = GlobalVariables.statement.executeQuery(String.format(
					"Call makeNewItem('%s', '%f', '%s', '%d', '%d', '%d', '%s');",
					productName, price, description, quantity, discount, GlobalVariables.userID, category));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(getParent(), e.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
		}
		dispose();
		SellerPage.refreshScreen();
	}
}
