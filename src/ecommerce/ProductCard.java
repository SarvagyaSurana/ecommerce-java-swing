package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProductCard extends JPanel {
	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel priceLabel;
	private JLabel categoryLabel;
	private JLabel discountLabel;
	private JButton addToCartButton;
	
	public ProductCard(String name, String description, double price, int discount, String category, int id) {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder()); // Add border for visual separation
		setPreferredSize(new Dimension(250, 150));

		nameLabel = new JLabel(name);
		descriptionLabel = new JLabel(description);
		categoryLabel = new JLabel(String.format("%s", category));
		priceLabel = new JLabel(String.format("$%.2f", price));
		discountLabel = new JLabel(String.format("%d", discount));
		
		JPanel infoPanel = new JPanel(new GridLayout(3, 1));
		infoPanel.add(nameLabel);
		infoPanel.add(descriptionLabel);
		infoPanel.add(categoryLabel);
		infoPanel.add(priceLabel);
		infoPanel.add(discountLabel);

		addToCartButton = new JButton("Add to Cart");
		addToCartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Add logic to add product to cart
				try {
					//INSERT INTO table_name (column1, column2, column3, ...)
					//VALUES (value1, value2, value3, ...);
					GlobalVariables.statement.executeUpdate(String.format("CALL AddToCart(%d,%d,1, @isValid, @addedSuccessfully);",GlobalVariables.userID, id));
					JOptionPane.showMessageDialog(null, "Product added to cart: " + name);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		add(infoPanel, BorderLayout.CENTER);
		add(addToCartButton, BorderLayout.SOUTH);
	}
}
