package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Registration extends JFrame {
	private JTextField emailField, firstNameField, lastNameField, ageField, mobileField;
	private JPasswordField passwordField;
	private JComboBox<String> sexComboBox;

	public Registration() {
		setTitle("Registration");
		setSize(400, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 5, 5));

		JLabel emailLabel = new JLabel("Email:");
		emailField = new JTextField();
		panel.add(emailLabel);
		panel.add(emailField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		panel.add(passwordLabel);
		panel.add(passwordField);

		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameField = new JTextField();
		panel.add(firstNameLabel);
		panel.add(firstNameField);

		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameField = new JTextField();
		panel.add(lastNameLabel);
		panel.add(lastNameField);

		JLabel sexLabel = new JLabel("Sex:");
		String[] sexOptions = { "M", "F" };
		sexComboBox = new JComboBox<>(sexOptions);
		panel.add(sexLabel);
		panel.add(sexComboBox);

		JLabel ageLabel = new JLabel("Age:");
		ageField = new JTextField();
		panel.add(ageLabel);
		panel.add(ageField);

		JLabel mobileLabel = new JLabel("Mobile Number:");
		mobileField = new JTextField();
		panel.add(mobileLabel);
		panel.add(mobileField);

		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve input values

				String sql = String.format("Call register('%s','%s','%s', '%s', '%d', '%s', '%s', 0, '','','','','');",
						emailField.getText(), new String(passwordField.getPassword()), mobileField.getText(),
						firstNameField.getText(), Integer.parseInt(ageField.getText()), lastNameField.getText(),
						(String) sexComboBox.getSelectedItem());

				try {
					GlobalVariables.statement.executeUpdate(sql);
					JOptionPane.showMessageDialog(Registration.this, "Registration successful!");
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(getParent(), e1.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);

				}

				// You can perform further actions here, like saving the data to a database
			}
		});
		panel.add(registerButton);

		getContentPane().add(panel);

		setVisible(true);
	}
}
