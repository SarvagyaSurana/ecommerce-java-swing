package ecommerce;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ProductCardSeller extends JPanel {
	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel priceLabel;
	private JLabel categoryLabel;
	private JLabel discountLabel;
	private JButton editButton;

    public ProductCardSeller(String name, String description, double price, int discount, String category, int id, int quantity) {
    	
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

        editButton = new JButton("Edit details");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to add product to cart
                new Product(name, description, category, quantity, discount, price, id);
            }
        });
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to add product to cart
                try {
					GlobalVariables.statement.executeUpdate(String.format("Delete from item where `id` = '%d'", id));
					SellerPage.refreshScreen();
                } catch (SQLException e1) {
					// TODO Auto-generated catch block
					
					 JOptionPane.showMessageDialog(getParent(), e1.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
				}
            }
        });

        add(infoPanel, BorderLayout.CENTER);
        JPanel p = new JPanel(new BorderLayout());
        p.add(editButton, BorderLayout.NORTH);  
        p.add(deleteButton, BorderLayout.SOUTH);
        add(p, BorderLayout.SOUTH);
    }
}
