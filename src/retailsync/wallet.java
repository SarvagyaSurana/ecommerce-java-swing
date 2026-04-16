package ecommerce;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class wallet extends JFrame {
	private double balance = 0.0; // Initial balance

	private JLabel balanceLabel;

	public wallet() {
		try {
			ResultSet resultSet = GlobalVariables.statement.executeQuery(
					String.format("Select wallet_amount from user where id = %d", GlobalVariables.userID));
			if (resultSet.next()) {
				balance = resultSet.getInt("wallet_amount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Wallet Balance");
		setSize(400, 400); // Reduced frame size for demonstration
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		// Initialize components
		balanceLabel = new JLabel("Balance: $" + balance);
		JButton addButton = new JButton("ADD");
		addButton.setFocusable(false);
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.setFocusable(false);
		// Set preferred size for buttons
		addButton.setPreferredSize(new Dimension(100, 40));
		withdrawButton.setPreferredSize(new Dimension(100, 40));

		// Add action listeners to buttons
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddMenu();
			}
		});

		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showWithdrawMenu();
			}
		});

		// Create layout and add components
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 500, 20));
		panel.setPreferredSize(new Dimension(100, 40));
		panel.add(balanceLabel);
		panel.add(addButton);
		panel.add(withdrawButton);

		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void showAddMenu() {
		JPopupMenu menu = new JPopupMenu();
		JTextField amountField = new JTextField();
		menu.setPreferredSize(new Dimension(250, 75));
		JButton addAmountButton = new JButton("Add");
		addAmountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double amount = Double.parseDouble(amountField.getText());
					balance += amount;
					updateBalanceLabel();
					menu.setVisible(false);
					try {
						System.out.println(balance);
						GlobalVariables.statement.executeUpdate(String.format(
								"Update user set wallet_amount = %.2f where id = %d", balance, GlobalVariables.userID));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
				}
			}
		});
		menu.add(new JLabel("Enter amount to add:"));
		menu.add(amountField);
		menu.add(addAmountButton);
		menu.show(this, ((getWidth() / 2) - 125), ((getHeight() / 2)));
	}

	private void showWithdrawMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.setPreferredSize(new Dimension(250, 75));
		JTextField amountField = new JTextField();
		JButton withdrawAmountButton = new JButton("Withdraw");
		withdrawAmountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double amount = Double.parseDouble(amountField.getText());
					if (amount <= balance) {
						balance -= amount;
						updateBalanceLabel();

						menu.setVisible(false);
						try {
							System.out.println(balance);
							GlobalVariables.statement
									.executeUpdate(String.format("Update user set wallet_amount = %.2f where id = %d",
											balance, GlobalVariables.userID));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Insufficient balance.");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
				}
			}
		});
		menu.add(new JLabel("Enter amount to withdraw:"));
		menu.add(amountField);
		menu.add(withdrawAmountButton);
		menu.show(this, ((getWidth() / 2) - 125), ((getHeight() / 2)));
	}

	private void updateBalanceLabel() {
		balanceLabel.setText("Balance: $" + balance);
	}

}
