package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerPage implements ActionListener {
	public static maxFrame m = new maxFrame("User HomePage - ECommerce App");
	JLabel l = new JLabel("Welcome to User Page!");
	JButton searchButton;
	JButton userAvatarButton;
	static JPanel screen;

	public static void refreshScreen() {
		screen.removeAll();
		try {
			ResultSet resultSet1 = GlobalVariables.statement.executeQuery("Select count(*) from item;");
			resultSet1.next();
			int count = resultSet1.getInt(1);
			ResultSet resultSet2 = GlobalVariables.statement
					.executeQuery(String.format("Select * from item where seller_id = %s ", GlobalVariables.userID));
			System.out.println(resultSet2.getFetchSize());
			System.out.println(count);
			// Create a panel for each row
			for (int i = 0; i < count; i += 6) {
				JPanel rowPanel = new JPanel(); // Panel for each row
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 57, 25)); // Set layout for row panel

				// Add six ProductCardSeller components to each row
				for (int j = i; j < i + 6 && resultSet2.next(); j++) {
					rowPanel.add(new ProductCardSeller(resultSet2.getString("name"),
							resultSet2.getString("description"), resultSet2.getDouble("price"),
							resultSet2.getInt("discount"), resultSet2.getString("category"), resultSet2.getInt("id"), resultSet2.getInt("quantity")));
				}

				// Add row panel to the screen panel
				screen.add(rowPanel);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		screen.revalidate();
		screen.repaint();
	}

	SellerPage() {
		JPanel nav = new JPanel(); // Panel for nav
		nav.setBackground(Color.WHITE);
		nav.setPreferredSize(new Dimension(100, 100));
		nav.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Add spacing between components

		ImageIcon addImg = new ImageIcon("src/img/add.png");
		JButton addButton = new JButton();
		addButton.setText("Add Products");
		Image scaledAddImg = addImg.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledAddIcon = new ImageIcon(scaledAddImg);
		addButton.setIcon(scaledAddIcon);
		addButton.setHorizontalTextPosition(JButton.RIGHT);
		addButton.setVerticalTextPosition(JButton.CENTER);
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddProductsFrame();
			}
		});

		ImageIcon couponImg = new ImageIcon("src/img/coupon.png");
		JButton couponButton = new JButton();
		couponButton.setText("Manage Coupons");
		Image scaledCouponImg = couponImg.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledCartIcon = new ImageIcon(scaledCouponImg);
		couponButton.setIcon(scaledCartIcon);
		couponButton.setHorizontalTextPosition(JButton.RIGHT);
		couponButton.setVerticalTextPosition(JButton.CENTER);
		couponButton.setFocusable(false);
		couponButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Cart();
			}
		});

		ImageIcon manageInventoryImgIcon = new ImageIcon("src/img/manage.png");
		JButton manageInventoryButton = new JButton();
		manageInventoryButton.setText("Manage Inventory");
		Image manageInventoryImg = manageInventoryImgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		manageInventoryImgIcon = new ImageIcon(manageInventoryImg);
		manageInventoryButton.setIcon(manageInventoryImgIcon);
		manageInventoryButton.setHorizontalTextPosition(JButton.RIGHT);
		manageInventoryButton.setVerticalTextPosition(JButton.CENTER);
		manageInventoryButton.setFocusable(false);
		manageInventoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdvancedSearch();
			}
		});

		ImageIcon originalIcon = new ImageIcon("src/img/logo.jpg");
		Image scaledAppImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon scaledAppIcon = new ImageIcon(scaledAppImage);
		JLabel app = new JLabel();
		app.setIcon(scaledAppIcon);
		ResultSet r;
		String s = "Welcome";

		try {
			r = GlobalVariables.statement
					.executeQuery(String.format("Select first_name from user where id = %d ", GlobalVariables.userID));
			r.next();
			s = r.getString("first_name");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userAvatarButton = new JButton(String.format("Welcome %s", s));
		userAvatarButton.setPreferredSize(new Dimension(150, 40)); // Adjust userAvatarButton size
		userAvatarButton.addActionListener(this);
		userAvatarButton.setFocusable(false);

		screen = new JPanel(); // Panel for main screen
		screen.setBackground(Color.LIGHT_GRAY); // Set screen background color

		// Change layout of screen panel to BoxLayout with Y_AXIS alignment
		screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));

		// Add screen panel to JScrollPane
		JScrollPane scrollPane = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Set preferred size of JScrollPane

		nav.add(app);
		// nav.add(search);
		// nav.add(manageInventoryButton);
		nav.add(addButton);
		//nav.add(couponButton);
		nav.add(userAvatarButton);

		m.add(nav, BorderLayout.NORTH);
		m.add(scrollPane, BorderLayout.CENTER);

		// Create a panel for each row
		try {
			ResultSet resultSet1 = GlobalVariables.statement.executeQuery("Select count(*) from item;");
			resultSet1.next();
			int count = resultSet1.getInt(1);
			ResultSet resultSet2 = GlobalVariables.statement
					.executeQuery(String.format("Select * from item where seller_id = '%d';", GlobalVariables.userID));
			System.out.println(resultSet2.getFetchSize());
			System.out.println(count);
			// Create a panel for each row
			for (int i = 0; i < count; i += 6) {
				JPanel rowPanel = new JPanel(); // Panel for each row
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 57, 25)); // Set layout for row panel

				// Add six ProductCardSeller components to each row
				for (int j = i; j < i + 6 && resultSet2.next(); j++) {
					rowPanel.add(new ProductCardSeller(resultSet2.getString("name"),
							resultSet2.getString("description"), resultSet2.getDouble("price"),
							resultSet2.getInt("discount"), resultSet2.getString("category"), resultSet2.getInt("id"), resultSet2.getInt("quantity")));
				}

				// Add row panel to the screen panel
				screen.add(rowPanel);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		m.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			screen.removeAll();

			// JDBC here for adding content according to search query;

			screen.revalidate();
			screen.repaint();
		}
		if (e.getSource() == userAvatarButton) {
			JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.setPreferredSize(new Dimension(150, 100));
			JMenuItem profileItem = new JMenuItem("View Profile");
			JMenuItem shoppingCart = new JMenuItem("Shopping Cart");
			JMenuItem logoutItem = new JMenuItem("Logout");
			logoutItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						GlobalVariables.statement.close();
						GlobalVariables.connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Perform logout action here
					m.dispose();
					new LoginPage();
					JOptionPane.showMessageDialog(null, "Logged out!");
					// Close the dropdown menu
					popupMenu.setVisible(false);
				}
			});
			profileItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Perform logout action here
					new User();
				}
			});

			popupMenu.add(profileItem);
			popupMenu.add(logoutItem);
			popupMenu.show(userAvatarButton, 0, userAvatarButton.getHeight());
		}
	}
}
