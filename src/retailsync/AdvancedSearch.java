package ecommerce;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.*;

public class AdvancedSearch extends JFrame {

	public AdvancedSearch() {

		setTitle("Advanced Search");

		setSize(400, 400); // Reduced frame size for demonstration

		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		setLocationRelativeTo(null);

		// Create buttons

		JButton sortButton = new JButton("Sort");

		JButton priceRangeButton = new JButton("Price Range");

		// Set preferred size for buttons

		sortButton.setPreferredSize(new Dimension(250, 40));

		priceRangeButton.setPreferredSize(new Dimension(250, 40));

		sortButton.setFocusable(false);

		priceRangeButton.setFocusable(false);

		// Set button positions using layout manager

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 400, 20)); // 2 rows, 1 column

		buttonPanel.add(sortButton);

		buttonPanel.add(priceRangeButton);

		// Add button panel to the frame

		add(buttonPanel, BorderLayout.CENTER);

		// Add action listener for sort button

		sortButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				// Open a menu for sorting options

				String[] options = { "Price Low to High", "Price High to Low" };

				int selectedOption = JOptionPane.showOptionDialog(AdvancedSearch.this,

						"Select Sorting Order", "Sort Options", JOptionPane.DEFAULT_OPTION,

						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				if (selectedOption == 0) {

					// Sorting by price low to high

					// Add your sorting logic here

					UserPage.refreshScreen(0, 0 , 0);

					JOptionPane.showMessageDialog(AdvancedSearch.this,

							"Sorting by Price Low to High");

				} else if (selectedOption == 1) {

					// Sorting by price high to low

					// Add your sorting logic here

					UserPage.refreshScreen(1, 0, 0);

					JOptionPane.showMessageDialog(AdvancedSearch.this,

							"Sorting by Price High to Low");

				}

			}

		});

		// Add action listener for price range button

		priceRangeButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				// Ask for price range input

				String minPriceStr = JOptionPane.showInputDialog(AdvancedSearch.this,

						"Enter Minimum Price:");

				String maxPriceStr = JOptionPane.showInputDialog(AdvancedSearch.this,

						"Enter Maximum Price:");

				try {

					double minPrice = Double.parseDouble(minPriceStr);

					double maxPrice = Double.parseDouble(maxPriceStr);

					// Check if minPrice is smaller than maxPrice

					if (minPrice < maxPrice) {

						// Add your price range logic here
						UserPage.refreshScreen(2, (int)minPrice, (int)maxPrice);
						JOptionPane.showMessageDialog(AdvancedSearch.this,

								"Price Range Selected: " + minPrice + " to " + maxPrice);

					} else {

						JOptionPane.showMessageDialog(AdvancedSearch.this,

								"Minimum Price should be smaller than Maximum Price.",

								"Error", JOptionPane.ERROR_MESSAGE);

					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(AdvancedSearch.this,

							"Invalid input. Please enter valid numbers for price range.",

							"Error", JOptionPane.ERROR_MESSAGE);

				}

			}

		});

		// Display the frame

		setVisible(true);

	}

}