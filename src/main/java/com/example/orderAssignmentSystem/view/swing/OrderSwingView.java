package com.example.orderAssignmentSystem.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class OrderSwingView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField orderIdTextField;
	private JTextField orderDescriptionTextField;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderSwingView frame = new OrderSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OrderSwingView() {
		setTitle("Order View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 579, 421);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white); // Set background color
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton btnWorker = new JButton("Manage Worker");
		btnWorker.setBackground(Color.gray); // Set button color
		btnWorker.setForeground(Color.white); // Set text color
		GridBagConstraints gbc_btnWorker = new GridBagConstraints();
		gbc_btnWorker.gridwidth = 2;
		gbc_btnWorker.insets = new Insets(0, 0, 5, 10);
		gbc_btnWorker.gridx = 5;
		gbc_btnWorker.gridy = 0;
		contentPane.add(btnWorker, gbc_btnWorker);

		JLabel orderIdLbl = new JLabel("Order Id");
		orderIdLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_orderIdLbl = new GridBagConstraints();
		gbc_orderIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderIdLbl.gridx = 0;
		gbc_orderIdLbl.gridy = 1;
		contentPane.add(orderIdLbl, gbc_orderIdLbl);

		orderIdTextField = new JTextField();
		GridBagConstraints gbc_orderIdTextField = new GridBagConstraints();
		gbc_orderIdTextField.gridwidth = 4;
		gbc_orderIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_orderIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderIdTextField.gridx = 1;
		gbc_orderIdTextField.gridy = 1;
		contentPane.add(orderIdTextField, gbc_orderIdTextField);
		orderIdTextField.setColumns(10);

		JLabel orderDescriptionLbl = new JLabel("Description");
		orderDescriptionLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_orderDescriptionLbl = new GridBagConstraints();
		gbc_orderDescriptionLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderDescriptionLbl.gridx = 0;
		gbc_orderDescriptionLbl.gridy = 2;
		contentPane.add(orderDescriptionLbl, gbc_orderDescriptionLbl);

		orderDescriptionTextField = new JTextField();
		GridBagConstraints gbc_orderDescriptionTextField = new GridBagConstraints();
		gbc_orderDescriptionTextField.gridwidth = 4;
		gbc_orderDescriptionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_orderDescriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderDescriptionTextField.gridx = 1;
		gbc_orderDescriptionTextField.gridy = 2;
		contentPane.add(orderDescriptionTextField, gbc_orderDescriptionTextField);
		orderDescriptionTextField.setColumns(10);

		JLabel orderCategoryLbl = new JLabel("Category");
		orderCategoryLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_orderCategoryLbl = new GridBagConstraints();
		gbc_orderCategoryLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderCategoryLbl.gridx = 0;
		gbc_orderCategoryLbl.gridy = 3;
		contentPane.add(orderCategoryLbl, gbc_orderCategoryLbl);

		JComboBox<String> categoryComboBox = new JComboBox<>();
		GridBagConstraints gbc_categoryComboBox = new GridBagConstraints();
		gbc_categoryComboBox.gridwidth = 4;
		gbc_categoryComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_categoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_categoryComboBox.gridx = 1;
		gbc_categoryComboBox.gridy = 3;
		contentPane.add(categoryComboBox, gbc_categoryComboBox);

		JLabel orderStatusLbl = new JLabel("Status");
		orderStatusLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_orderStatusLbl = new GridBagConstraints();
		gbc_orderStatusLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderStatusLbl.gridx = 0;
		gbc_orderStatusLbl.gridy = 4;
		contentPane.add(orderStatusLbl, gbc_orderStatusLbl);

		JComboBox<String> statusComboBox = new JComboBox<>();
		GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.gridwidth = 4;
		gbc_statusComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusComboBox.gridx = 1;
		gbc_statusComboBox.gridy = 4;
		contentPane.add(statusComboBox, gbc_statusComboBox);

		JLabel orderWorkerLbl = new JLabel("Worker");
		orderWorkerLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_orderWorkerLbl = new GridBagConstraints();
		gbc_orderWorkerLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderWorkerLbl.gridx = 0;
		gbc_orderWorkerLbl.gridy = 5;
		contentPane.add(orderWorkerLbl, gbc_orderWorkerLbl);

		JComboBox<String> workerComboBox = new JComboBox<>();
		GridBagConstraints gbc_workerComboBox = new GridBagConstraints();
		gbc_workerComboBox.gridwidth = 4;
		gbc_workerComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_workerComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerComboBox.gridx = 1;
		gbc_workerComboBox.gridy = 5;
		contentPane.add(workerComboBox, gbc_workerComboBox);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setBackground(UIManager.getColor("Button.light")); // Set panel background color
		panel.setPreferredSize(new Dimension(200, 50)); // Set panel size
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 7;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPane.add(panel, gbc_panel);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.blue); // Set button color
		btnAdd.setForeground(Color.white); // Set text color
		panel.add(btnAdd);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(Color.blue); // Set button color
		btnUpdate.setForeground(Color.white); // Set text color
		panel.add(btnUpdate);

		textField = new JTextField();
		GridBagConstraints searchTextField = new GridBagConstraints();
		searchTextField.gridwidth = 4;
		searchTextField.insets = new Insets(0, 10, 5, 5); // Adjust the left margin here (changed 0 to 10)
		searchTextField.fill = GridBagConstraints.HORIZONTAL;
		searchTextField.gridx = 0;
		searchTextField.gridy = 7;
		contentPane.add(textField, searchTextField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(SystemColor.windowText);
		btnSearch.setBackground(SystemColor.info);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 7;
		contentPane.add(btnSearch, gbc_btnSearch);

		JList<String> orderListLayout = new JList<>();
		orderListLayout.setBorder(new LineBorder(new Color(0, 0, 0)));
		orderListLayout.setBackground(Color.white); // Set list background color
		orderListLayout.setPreferredSize(new Dimension(200, 100)); // Set list size

		GridBagConstraints gbc_orderListLayout = new GridBagConstraints();
		gbc_orderListLayout.insets = new Insets(0, 10, 5, 10); // Adjust the left margin here (changed 0 to 10)
		gbc_orderListLayout.gridwidth = 7;
		gbc_orderListLayout.fill = GridBagConstraints.BOTH;
		gbc_orderListLayout.gridx = 0;
		gbc_orderListLayout.gridy = 8;
		contentPane.add(orderListLayout, gbc_orderListLayout);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.red); // Set button color
		btnDelete.setForeground(Color.white); // Set text color
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridwidth = 7;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 9;
		contentPane.add(btnDelete, gbc_btnDelete);

		JLabel lblError = new JLabel("Error");
		lblError.setForeground(Color.red); // Set error label text color
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 7;
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 10;
		contentPane.add(lblError, gbc_lblError);
	}
}
