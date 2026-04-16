package ecommerce;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage implements ActionListener {

	maxFrame m = new maxFrame("Login - E-Commerce App");

	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JButton registerButton = new JButton("Register Customer");
	JButton registerSellerButton = new JButton("Register Seller");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("User ID: ");
	JLabel userPasswordLabel = new JLabel("Password: ");
	JLabel messageLabel = new JLabel();
	JLabel registerLabel = new JLabel("Don't have an account? Click Register.");

	JTextField usernameField = new JTextField();
	JLabel registerUsernameLabel = new JLabel("Username:");
	JPasswordField registerPasswordField = new JPasswordField();
	JLabel registerPasswordLabel = new JLabel("Password:");
	JButton registerConfirmButton = new JButton("Register");

	boolean registering = false;

	LoginPage() {
		new GlobalVariables();
		m.setLayout(null);

		userIDLabel.setBounds(50, 100, 75, 25);
		userPasswordLabel.setBounds(50, 150, 75, 25);
		messageLabel.setBounds(125, 250, 250, 35);
		messageLabel.setFont(new Font(null, Font.BOLD, 25));
		userIDField.setBounds(125, 100, 200, 25);
		userPasswordField.setBounds(125, 150, 200, 25);
		loginButton.setBounds(125, 200, 95, 25);
		loginButton.addActionListener(this);
		resetButton.setBounds(225, 200, 95, 25);
		resetButton.addActionListener(this);
		registerButton.setBounds(125, 300, 150, 25);
		registerSellerButton.setBounds(125, 350, 150, 25);
		registerButton.addActionListener(this);
		registerSellerButton.addActionListener(this);
		resetButton.setFocusable(false);
		loginButton.setFocusable(false);
		registerButton.setFocusable(false);
		registerSellerButton.setFocusable(false);

		registerLabel.setBounds(125, 250, 250, 25);

		usernameField.setBounds(125, 300, 200, 25);
		registerUsernameLabel.setBounds(50, 300, 75, 25);
		registerPasswordField.setBounds(125, 350, 200, 25);
		registerPasswordLabel.setBounds(50, 350, 75, 25);
		registerConfirmButton.setBounds(125, 400, 95, 25);
		registerConfirmButton.addActionListener(this);
		registerConfirmButton.setFocusable(false);

		// Initially hide registration fields and labels
		usernameField.setVisible(false);
		registerUsernameLabel.setVisible(false);
		registerPasswordField.setVisible(false);
		registerPasswordLabel.setVisible(false);
		registerConfirmButton.setVisible(false);

		m.add(userIDLabel);
		m.add(userPasswordLabel);
		m.add(messageLabel);
		m.add(userIDField);
		m.add(userPasswordField);
		m.add(loginButton);
		m.add(resetButton);
		m.add(registerButton);
		m.add(registerSellerButton);
		m.add(registerLabel);
		m.add(usernameField);
		m.add(registerUsernameLabel);
		m.add(registerPasswordField);
		m.add(registerPasswordLabel);
		m.add(registerConfirmButton);

		m.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			userIDField.setText("");
			userPasswordField.setText("");
		}

		if (e.getSource() == loginButton) {
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			String loginQuery = String.format("CALL login('%s', '%s', @isSeller, @userID);", userID, password);
			String loginQuery2 = String.format("select @isSeller,@userID;");
			ResultSet resultSet;
			try {
				resultSet = GlobalVariables.statement.executeQuery(loginQuery);
				resultSet = GlobalVariables.statement.executeQuery(loginQuery2);
				if (resultSet.next() && resultSet.getInt("@userID") != 0) {
					GlobalVariables.userID = resultSet.getInt("@userID");
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login Successful!");
					m.dispose();
					if (resultSet.getInt("@isSeller")!= 0) {
						GlobalVariables.userID = resultSet.getInt("@userID");
						new SellerPage();
					} else {
						new UserPage();
					}
				} else {
					System.out.println(resultSet.getInt("@userID"));
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Login Unsuccessful!");
				}
			} catch (SQLException e1) {
				System.err.println("Failed to connect to the database or execute stored procedure!");
				e1.printStackTrace();
			}

			// Add JDBC here to check user and navigate if exists with given credentials

			// new UserPage(); // Commented for testing purposes

		}

		if (e.getSource() == registerButton) {
			new Registration();
		}
		if (e.getSource() == registerSellerButton) {
			new RegistrationSeller();
		}

		if (e.getSource() == registerConfirmButton) {
			String username = usernameField.getText();
			String password = String.valueOf(registerPasswordField.getPassword());

			// Add registration logic here
			messageLabel.setForeground(Color.green);
			messageLabel.setText("Registration Successful!");
			// new UserPage(); // Commented for testing purposes
			new UserPage();
			m.dispose();
		}
	}

	public static void main(String[] args) {
		new LoginPage();
	}
}
