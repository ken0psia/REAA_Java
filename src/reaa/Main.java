package reaa;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import java.time.*;

public class Main {

	public static void main(String[] args) {
		OrganizationAccount orgAccount = new OrganizationAccount(); //creates instance, pulls local csv data, to be a login screen for data tracking
		orgAccount.processCashflow();

		JFrame mainframe = new JFrame("Ripple Effect Accounting (Java)");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setSize(330, 480);
		mainframe.setLocationRelativeTo(null); //centers window on screen
		
		JPanel landingPanel = new JPanel();
		landingPanel.setBackground(java.awt.Color.DARK_GRAY);
		// Use GridBagLayout with two columns for precise alignment
		landingPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel welcomeLabel = new JLabel("Ripple Effect Accounting");
		JLabel incomeTitle = new JLabel("Monthly Income:");
		JLabel incomeValue = new JLabel(String.format("$%.2f", ((OrganizationAccount) orgAccount).getIncome()));
		JLabel profitTitle = new JLabel("Monthly Profit:");
		JLabel profitValue = new JLabel(String.format("$%.2f", ((OrganizationAccount) orgAccount).getProfit()));
		JLabel expensesTitle = new JLabel("Monthly Expenses:");
		JLabel expensesValue = new JLabel(String.format("$%.2f", orgAccount.getExpenses()));
		JButton viewDetailsButton = new JButton("View Details");
		JButton addSaleButton = new JButton("Add Sale");
		JButton addExpenseButton = new JButton("Add Expense");
		
		// Fonts: titles (alphabetical) = 20, numeric dollar values = Arial 28 bold
		Font titleFont = incomeTitle.getFont().deriveFont(Font.PLAIN, 20f);
		incomeTitle.setFont(titleFont);
		expensesTitle.setFont(titleFont);
		profitTitle.setFont(titleFont);
		Font valueFont = new Font("Arial", Font.BOLD, 28);
		incomeValue.setFont(valueFont);
		expensesValue.setFont(valueFont);
		profitValue.setFont(valueFont);
		
		// Color the pairs: profit = green, expenses = red
		incomeTitle.setForeground(Color.GREEN);
		incomeValue.setForeground(Color.GREEN);
		profitTitle.setForeground(Color.BLUE);
		profitValue.setForeground(Color.BLUE);
		expensesTitle.setForeground(Color.RED);
		expensesValue.setForeground(Color.RED);
		
		// Center the topmost welcome label explicitly
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// welcome spans two columns (row 0)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 0, 6, 0);
		landingPanel.add(welcomeLabel, gbc);
		
		// Row 1: profit title (col 0)
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0.0;
		// increase bottom inset so the numeric value sits one line lower
		gbc.insets = new Insets(0, 10, 12, 10);
		landingPanel.add(profitTitle, gbc);
		// Row 2: profit value (col 1) -> placed on the next row, right-aligned
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 10, 6, 10);
		landingPanel.add(profitValue, gbc);
		
		// Row 3: expenses title (col 0)
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(0, 10, 12, 10);
		landingPanel.add(expensesTitle, gbc);
		// Row 4: expenses value (col 1)
		gbc.gridy = 4;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 10, 6, 10);
		landingPanel.add(expensesValue, gbc);
		
		// Row 5: income title (col 0)
		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(0, 10, 12, 10);
		landingPanel.add(incomeTitle, gbc);
		// Row 6: income value (col 1)
		gbc.gridy = 6;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 10, 6, 10);
		landingPanel.add(incomeValue, gbc);
		
		// ---------------- Center filler (span two columns) ----------------
		gbc.gridy = 7; // moved filler down so values don't overlap
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0; // filler takes remaining space
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		landingPanel.add(filler, gbc);
		
		// ---------------- Bottom area (buttons stacked vertically, span two columns) ----------------
		gbc.gridy = 8; // bottom row
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 0.0; // bottom panel uses preferred size
		gbc.insets = new Insets(0, 10, 10, 10);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		// Add vertical glue to slightly space buttons within the bottom area if desired
		bottomPanel.add(Box.createVerticalGlue());
		viewDetailsButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		addSaleButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		addExpenseButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		bottomPanel.add(viewDetailsButton);
		bottomPanel.add(Box.createVerticalStrut(8));
		bottomPanel.add(addSaleButton);
		bottomPanel.add(Box.createVerticalStrut(8));
		bottomPanel.add(addExpenseButton);
		bottomPanel.add(Box.createVerticalGlue());
		// set an initial preferred size for bottomPanel (25% of frame height)
		bottomPanel.setPreferredSize(new Dimension(mainframe.getWidth()-20, (int)(mainframe.getHeight()*0.25)));
		landingPanel.add(bottomPanel, gbc);
		
		// Update bottomPanel preferred size when the frame is resized so it remains ~25% of height
		mainframe.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int h = mainframe.getHeight();
				bottomPanel.setPreferredSize(new Dimension(mainframe.getWidth()-20, (int)(h * 0.25)));
				landingPanel.revalidate();
			}
		});
		
		JPanel salePanel = new JPanel();
		salePanel.setBackground(java.awt.Color.LIGHT_GRAY);
		salePanel.add(new JLabel("Sale Entry Panel - WIP"));
		LocalDate today = LocalDate.now();
		salePanel.add(new JLabel("Date: " + today.toString()));
		JLabel saleAmountLabel = new JLabel("Sale Amount: $");
		JTextField saleAmountField = new JTextField(10);
		JLabel bottleAmountLabel = new JLabel("Bottles Sold: ");
		JTextField bottleAmountField = new JTextField(10);
		salePanel.add(saleAmountLabel);
		salePanel.add(saleAmountField);
		salePanel.add(bottleAmountLabel);
		salePanel.add(bottleAmountField);
		JButton submitSaleButton = new JButton("Submit Sale");
		salePanel.add(submitSaleButton);
		submitSaleButton.addActionListener(e -> {
			// Placeholder action - in real app, would validate and record sale
			if (saleAmountField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(mainframe, "Please fill in sale amount field.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String saleAmountText = saleAmountField.getText();
			String bottleAmountText = bottleAmountField.getText();
			if (bottleAmountField.getText().isEmpty()) {
				bottleAmountText = null;
			}
			System.out.println("Submitted Sale - Amount: $" + saleAmountText + ", Bottles: " + bottleAmountText);
			
			// Clear fields after submission
			saleAmountField.setText("");
			bottleAmountField.setText("");
			// Return to landing panel
			mainframe.getContentPane().removeAll();
			mainframe.getContentPane().add(landingPanel);
			mainframe.revalidate();
			mainframe.repaint();
			
		});
		
		// Action listener for "Add Sale" button to switch to sale entry panel
				addSaleButton.addActionListener(e -> {
					mainframe.getContentPane().removeAll();
					mainframe.getContentPane().add(salePanel);
					mainframe.revalidate();
					mainframe.repaint();
				});
		
		// Add landing panel to frame
		mainframe.getContentPane().add(landingPanel);
		mainframe.revalidate();
		mainframe.setVisible(true);
	}

	public void recordSale(String amount, String bottles) {
		try {
		    Integer.parseInt(bottles);
		} catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(null, "Invalid bottle count. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e) {
		    // bottles input is optional
		}
		double saleAmount = Double.parseDouble(amount);
		int bottleCount = Integer.parseInt(bottles);
		
	}
}