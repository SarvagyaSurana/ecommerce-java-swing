package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistrationSeller extends JFrame {
    private JTextField emailField, firstNameField, lastNameField, ageField, mobileField,
            storeAddress1Field, storeAddress2Field, storeCityField, storeCountryField, storePincodeField;
    private JPasswordField passwordField;
    private JComboBox<String> sexComboBox;

    public RegistrationSeller() {
        setTitle("Registration");
        setSize(400, 400);
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
        String[] sexOptions = {"M", "F"};
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

        // New fields for store address details
        JLabel storeAddress1Label = new JLabel("Store Address 1:");
        storeAddress1Field = new JTextField();
        panel.add(storeAddress1Label);
        panel.add(storeAddress1Field);

        JLabel storeAddress2Label = new JLabel("Store Address 2:");
        storeAddress2Field = new JTextField();
        panel.add(storeAddress2Label);
        panel.add(storeAddress2Field);

        JLabel storeCityLabel = new JLabel("Store City:");
        storeCityField = new JTextField();
        panel.add(storeCityLabel);
        panel.add(storeCityField);

        JLabel storeCountryLabel = new JLabel("Store Country:");
        storeCountryField = new JTextField();
        panel.add(storeCountryLabel);
        panel.add(storeCountryField);

        JLabel storePincodeLabel = new JLabel("Store Pincode:");
        storePincodeField = new JTextField();
        panel.add(storePincodeLabel);
        panel.add(storePincodeField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve input values
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String sex = (String) sexComboBox.getSelectedItem();
                int age = Integer.parseInt(ageField.getText());
                String mobile = mobileField.getText();
                String storeAddress1 = storeAddress1Field.getText();
                String storeAddress2 = storeAddress2Field.getText();
                String storeCity = storeCityField.getText();
                String storeCountry = storeCountryField.getText();
                String storePincode = storePincodeField.getText();

                // Call the registration procedure
                String sql = String.format(
                        "CALL register('%s','%s','%s','%s',%d,'%s','%s',1,'%s','%s','%s','%s','%s')",
                        email, password, mobile, firstName, age, lastName, sex, storeAddress1,
                        storeAddress2, storeCity, storeCountry, storePincode);

                try {
                    GlobalVariables.statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(RegistrationSeller.this, "Registration successful!");
                    dispose(); // Close the registration window after successful registration
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(RegistrationSeller.this, "Error: " + ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(registerButton);

        getContentPane().add(panel);

        setVisible(true);
    }
}
