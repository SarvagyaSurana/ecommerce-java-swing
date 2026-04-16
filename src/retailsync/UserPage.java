package ecommerce;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPage implements ActionListener {
	maxFrame m = new maxFrame("ECommerce-App");
	JLabel l = new JLabel("Welcome to User Page!");
	JButton searchButton;
	JButton userAvatarButton;
	static JPanel screen;
	JTextField searchBox;
	public static String searchText = "";

	public static void refreshScreen(int idx, int low, int high) {
		screen.removeAll();
		ResultSet resultSet1;
		ResultSet resultSet2;
		String query;
		String arrIfSearch[] = new String[3];
		String arrIfNOSearch[] = new String[3];
		// 0 for lo to hi, 1 for hi to lo, 2 for price range
		arrIfSearch[0] = String.format("SELECT * FROM item WHERE name LIKE '%%%s%%' order by price asc;", searchText);
		arrIfSearch[1] = String.format("SELECT * FROM item WHERE name LIKE '%%%s%%' order by price desc;", searchText);
		arrIfSearch[2] = String.format("SELECT * FROM item WHERE name LIKE '%%%s%%' and price between %d and %d;",
				searchText, low, high);
		arrIfNOSearch[0] = "SELECT * FROM item order by price asc;";
		arrIfNOSearch[1] = "SELECT * FROM item order by price desc;";
		arrIfNOSearch[2] = String.format("SELECT * FROM item where price between %d and %d;", low, high);
		if (searchText.equals("")) {
			query = arrIfNOSearch[idx];
		} else {
			query = arrIfSearch[idx];
		}
		int count;
		try {
			resultSet1 = GlobalVariables.statement.executeQuery("Select count(*) from item;");
			resultSet1.next();
			count = resultSet1.getInt(1);
			resultSet2 = GlobalVariables.statement.executeQuery(query);
			System.out.println(resultSet2.getFetchSize());
			System.out.println(count);
			// Create a panel for each row
			for (int i = 0; i < count; i += 6) {
				JPanel rowPanel = new JPanel(); // Panel for each row
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 57, 25)); // Set layout for row panel

				// Add six ProductCard components to each row
				for (int j = i; j < i + 6 && resultSet2.next(); j++) {
					rowPanel.add(new ProductCard(resultSet2.getString("name"), resultSet2.getString("description"),
							resultSet2.getDouble("price"), resultSet2.getInt("discount"),
							resultSet2.getString("category"), resultSet2.getInt("id")));
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

	UserPage() {
		JPanel nav = new JPanel(); // Panel for nav
		nav.setBackground(Color.WHITE);
		nav.setPreferredSize(new Dimension(100, 100));
		nav.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Add spacing between components

		JPanel search = new JPanel();
		search.setBackground(Color.WHITE);
		searchButton = new JButton("Search");
		searchBox = new JTextField();
		searchBox.setPreferredSize(new Dimension(300, 40)); // Adjust search box size
		searchButton.setPreferredSize(new Dimension(100, 40)); // Adjust search button size
		searchButton.setFocusable(false);
		searchButton.addActionListener(this);
		search.add(searchBox);
		search.add(searchButton);

		ImageIcon walletIcon = new ImageIcon("src/img/wallet.png");
		JButton walletButton = new JButton();
		walletButton.setText("Wallet");
		Image scaledImage = walletIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		walletButton.setIcon(scaledIcon);
		walletButton.setHorizontalTextPosition(JButton.RIGHT);
		walletButton.setVerticalTextPosition(JButton.CENTER);
		walletButton.setFocusable(false);
		walletButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new wallet();
			}
		});

		ImageIcon shoppingCartImg = new ImageIcon("src/img/shoppingCart.png");
		JButton shoppingCart = new JButton();
		shoppingCart.setText("Shopping Cart");
		Image scaledCartImage = shoppingCartImg.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon scaledCartIcon = new ImageIcon(scaledCartImage);
		shoppingCart.setIcon(scaledCartIcon);
		shoppingCart.setHorizontalTextPosition(JButton.RIGHT);
		shoppingCart.setVerticalTextPosition(JButton.CENTER);
		shoppingCart.setFocusable(false);
		shoppingCart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Cart();
			}
		});

		ImageIcon advSearchImgIcon = new ImageIcon("src/img/search.png");
		JButton advSearchButton = new JButton();
		advSearchButton.setText("Advanced Search");
		Image advSearchImg = advSearchImgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		advSearchImgIcon = new ImageIcon(advSearchImg);
		advSearchButton.setIcon(advSearchImgIcon);
		advSearchButton.setHorizontalTextPosition(JButton.RIGHT);
		advSearchButton.setVerticalTextPosition(JButton.CENTER);
		advSearchButton.setFocusable(false);
		advSearchButton.addActionListener(new ActionListener() {
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
		nav.add(search);
		nav.add(advSearchButton);
		nav.add(walletButton);
		nav.add(shoppingCart);
		nav.add(userAvatarButton);

		m.add(nav, BorderLayout.NORTH);
		m.add(scrollPane, BorderLayout.CENTER);
		ResultSet resultSet1;
		ResultSet resultSet2;
		int count;
		try {
			resultSet1 = GlobalVariables.statement.executeQuery("Select count(*) from item;");
			resultSet1.next();
			count = resultSet1.getInt(1);
			resultSet2 = GlobalVariables.statement.executeQuery("Select * from item;");
			System.out.println(resultSet2.getFetchSize());
			System.out.println(count);
			// Create a panel for each row
			for (int i = 0; i < count; i += 6) {
				JPanel rowPanel = new JPanel(); // Panel for each row
				rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 57, 25)); // Set layout for row panel

				// Add six ProductCard components to each row
				for (int j = i; j < i + 6 && resultSet2.next(); j++) {
					rowPanel.add(new ProductCard(resultSet2.getString("name"), resultSet2.getString("description"),
							resultSet2.getDouble("price"), resultSet2.getInt("discount"),
							resultSet2.getString("category"), resultSet2.getInt("id")));
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
			searchText = searchBox.getText();

			// JDBC here for adding content according to search query;
			ResultSet resultSet1;
			ResultSet resultSet2;
			int count;
			try {
				resultSet1 = GlobalVariables.statement.executeQuery("Select count(*) from item;");
				resultSet1.next();
				count = resultSet1.getInt(1);
				resultSet2 = GlobalVariables.statement
						.executeQuery(String.format("SELECT * FROM item WHERE name LIKE '%%%s%%'", searchText));
				System.out.println(resultSet2.getFetchSize());
				System.out.println(searchButton.getText());
				// Create a panel for each row
				for (int i = 0; i < count; i += 6) {
					System.out.println("test");
					JPanel rowPanel = new JPanel(); // Panel for each row
					rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 57, 25)); // Set layout for row panel

					// Add six ProductCard components to each row
					for (int j = i; j < i + 6 && resultSet2.next(); j++) {
						System.out.println(resultSet2.getString("name"));
						rowPanel.add(new ProductCard(resultSet2.getString("name"), resultSet2.getString("description"),
								resultSet2.getDouble("price"), resultSet2.getInt("discount"),
								resultSet2.getString("category"), resultSet2.getInt("id")));
					}

					// Add row panel to the screen panel
					screen.add(rowPanel);
					screen.revalidate();
					screen.repaint();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == userAvatarButton) {
			JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.setPreferredSize(new Dimension(150, 100));
			JMenuItem profileItem = new JMenuItem("View Profile");
			JMenuItem logoutItem = new JMenuItem("Logout");

			profileItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Perform logout action here

					new User();
					// Close the dropdown menu
					popupMenu.setVisible(false);
				}
			});

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

			popupMenu.add(profileItem);
			popupMenu.add(logoutItem);
			popupMenu.show(userAvatarButton, 0, userAvatarButton.getHeight());
		}
	}
}
