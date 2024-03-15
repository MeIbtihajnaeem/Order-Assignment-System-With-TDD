package com.example.orderAssignmentSystem.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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

public class WorkerSwingView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField workerIdTextField;
	private JTextField workerNameTextField;

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

	public WorkerSwingView() {
		setTitle("Worker View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 579, 421);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white); // Set background color
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton btnOrders = new JButton("Manage Order");
		btnOrders.setBackground(Color.gray); // Set button color
		btnOrders.setForeground(Color.white); // Set text color
		GridBagConstraints gbc_btnOrders = new GridBagConstraints();
		gbc_btnOrders.gridwidth = 2;
		gbc_btnOrders.insets = new Insets(0, 0, 5, 0);
		gbc_btnOrders.gridx = 5;
		gbc_btnOrders.gridy = 0;
		contentPane.add(btnOrders, gbc_btnOrders);

		JLabel workerIdLbl = new JLabel("Worker Id");
		workerIdLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_workerIdLbl = new GridBagConstraints();
		gbc_workerIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workerIdLbl.gridx = 0;
		gbc_workerIdLbl.gridy = 1;
		contentPane.add(workerIdLbl, gbc_workerIdLbl);

		workerIdTextField = new JTextField();
		GridBagConstraints gbc_workerIdTextField = new GridBagConstraints();
		gbc_workerIdTextField.gridwidth = 4;
		gbc_workerIdTextField.insets = new Insets(0, 0, 5, 5);
		gbc_workerIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerIdTextField.gridx = 1;
		gbc_workerIdTextField.gridy = 1;
		contentPane.add(workerIdTextField, gbc_workerIdTextField);
		workerIdTextField.setColumns(10);

		JLabel workerNameLbl = new JLabel("Name");
		workerNameLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_workerNameLbl = new GridBagConstraints();
		gbc_workerNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workerNameLbl.gridx = 0;
		gbc_workerNameLbl.gridy = 2;
		contentPane.add(workerNameLbl, gbc_workerNameLbl);

		workerNameTextField = new JTextField();
		GridBagConstraints gbc_workerNameTextField = new GridBagConstraints();
		gbc_workerNameTextField.gridwidth = 4;
		gbc_workerNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_workerNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerNameTextField.gridx = 1;
		gbc_workerNameTextField.gridy = 2;
		contentPane.add(workerNameTextField, gbc_workerNameTextField);
		workerNameTextField.setColumns(10);

		JLabel workerCategoryLbl = new JLabel("Category");
		workerCategoryLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_workerCategoryLbl = new GridBagConstraints();
		gbc_workerCategoryLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workerCategoryLbl.gridx = 0;
		gbc_workerCategoryLbl.gridy = 3;
		contentPane.add(workerCategoryLbl, gbc_workerCategoryLbl);

		JComboBox<String> workerCategoryComboBox = new JComboBox<>();
		GridBagConstraints gbc_workerCategoryComboBox = new GridBagConstraints();
		gbc_workerCategoryComboBox.gridwidth = 4;
		gbc_workerCategoryComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_workerCategoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerCategoryComboBox.gridx = 1;
		gbc_workerCategoryComboBox.gridy = 3;
		contentPane.add(workerCategoryComboBox, gbc_workerCategoryComboBox);

		JLabel statusLbl = new JLabel("Status");
		statusLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_statusLbl = new GridBagConstraints();
		gbc_statusLbl.insets = new Insets(0, 0, 5, 5);
		gbc_statusLbl.gridx = 0;
		gbc_statusLbl.gridy = 4;
		contentPane.add(statusLbl, gbc_statusLbl);

		JComboBox<String> workerStatusComboBox = new JComboBox<>();
		GridBagConstraints gbc_workerStatusComboBox = new GridBagConstraints();
		gbc_workerStatusComboBox.gridwidth = 4;
		gbc_workerStatusComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_workerStatusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerStatusComboBox.gridx = 1;
		gbc_workerStatusComboBox.gridy = 4;
		contentPane.add(workerStatusComboBox, gbc_workerStatusComboBox);

		JLabel workerOrdersLbl = new JLabel("Orders");
		workerOrdersLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_workerOrdersLbl = new GridBagConstraints();
		gbc_workerOrdersLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workerOrdersLbl.gridx = 0;
		gbc_workerOrdersLbl.gridy = 5;
		contentPane.add(workerOrdersLbl, gbc_workerOrdersLbl);

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

		JList<String> workerListLayout = new JList<>();
		workerListLayout.setBorder(new LineBorder(new Color(0, 0, 0)));
		workerListLayout.setBackground(Color.white); // Set list background color
		workerListLayout.setPreferredSize(new Dimension(200, 100)); // Set list size
		GridBagConstraints gbc_workerListLayout = new GridBagConstraints();
		gbc_workerListLayout.insets = new Insets(0, 0, 5, 0);
		gbc_workerListLayout.gridwidth = 7;
		gbc_workerListLayout.fill = GridBagConstraints.BOTH;
		gbc_workerListLayout.gridx = 0;
		gbc_workerListLayout.gridy = 7;
		contentPane.add(workerListLayout, gbc_workerListLayout);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.red); // Set button color
		btnDelete.setForeground(Color.white); // Set text color
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridwidth = 7;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 8;
		contentPane.add(btnDelete, gbc_btnDelete);

		JLabel lblError = new JLabel("Error");
		lblError.setForeground(Color.red); // Set error label text color
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 7;
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 9;
		contentPane.add(lblError, gbc_lblError);
	}
}
