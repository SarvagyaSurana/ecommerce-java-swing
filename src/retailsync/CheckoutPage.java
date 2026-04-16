package ecommerce;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.swing.JComboBox;
//import javax.swing.JOptionPane;
//import javax.swing.JTextField;

import javax.swing.*;

public class CheckoutPage extends JFrame {

	public CheckoutPage(double cartAmount) {

		String[] paymentOptions = { "Wallet", "Cash on Delivery" };
		JComboBox<String> paymentDropdown = new JComboBox<>(paymentOptions);

		JTextField addressField = new JTextField(20); // Address input field

		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.addActionListener(e -> {
			String selectedOption = (String) paymentDropdown.getSelectedItem();
			String address = addressField.getText();

			if (selectedOption == "Wallet") {
				try {
					ResultSet r = GlobalVariables.statement.executeQuery(String.format(
							"CALL checkout(1, %d, '%s', '', '', '', '', @success);", GlobalVariables.userID, address));
					r = GlobalVariables.statement.executeQuery("Select @success;");
					if (r.next() && r.getInt("@success") != 0) {
							JOptionPane.showMessageDialog(null, "ORDER CONFIRMED!", "Order Confirmation",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "INSUFFICIENT BALANCE!", "Order not confirmed",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}

				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					ResultSet r = GlobalVariables.statement.executeQuery(String.format(
							"CALL checkout(0, %d, '%s', '', '', '', '', @success);", GlobalVariables.userID, address));
					r = GlobalVariables.statement.executeQuery("Select @success;");
							JOptionPane.showMessageDialog(null, "ORDER CONFIRMED!", "Order Confirmation",
									JOptionPane.INFORMATION_MESSAGE);
					}

				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			dispose();

		});

		JPanel panel = new JPanel();
		panel.add(new JLabel("Select Payment Mode:"));
		panel.add(paymentDropdown);
		panel.add(new JLabel("Enter Address:"));
		panel.add(addressField);
		panel.add(checkoutButton);

		setTitle("Checkout Page");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		add(panel);
		setVisible(true);
	}
}
