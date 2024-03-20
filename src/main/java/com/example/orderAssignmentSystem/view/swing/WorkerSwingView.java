package com.example.orderAssignmentSystem.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.example.orderAssignmentSystem.controller.WorkerController;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.view.WorkerView;

public class WorkerSwingView extends JFrame implements WorkerView {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField workerNameTextField;
	private DefaultListModel<Worker> workerListModel;
	private JList<Worker> workerListLayout;
	private JLabel lblError = new JLabel(" ");
	private WorkerController workerController;
	private JButton btnAdd;
	private JComboBox<CategoryEnum> workerCategoryComboBox;

	public void setWorkerController(WorkerController workerController) {
		this.workerController = workerController;
	}

	public DefaultListModel<Worker> getWorkerListModel() {
		return workerListModel;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkerSwingView frame = new WorkerSwingView();
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
		contentPane.setBackground(Color.white); // Set background colo
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
		btnOrders.setName("btnOrders");
		btnOrders.setBackground(Color.gray); // Set button color
		btnOrders.setForeground(Color.black); // Set text color
		GridBagConstraints gbc_btnOrders = new GridBagConstraints();
		gbc_btnOrders.gridwidth = 2;
		gbc_btnOrders.insets = new Insets(0, 0, 5, 0);
		gbc_btnOrders.gridx = 5;
		gbc_btnOrders.gridy = 0;
		contentPane.add(btnOrders, gbc_btnOrders);

		JLabel workerNameLbl = new JLabel("Name");
		workerNameLbl.setFont(new Font("Arial", Font.BOLD, 14)); // Set label font
		GridBagConstraints gbc_workerNameLbl = new GridBagConstraints();
		gbc_workerNameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_workerNameLbl.gridx = 0;
		gbc_workerNameLbl.gridy = 2;
		contentPane.add(workerNameLbl, gbc_workerNameLbl);

		workerNameTextField = new JTextField();
		workerNameTextField.setName("workerNameTextField");

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

		workerCategoryComboBox = new JComboBox<>();
		for (CategoryEnum category : CategoryEnum.values()) {
			workerCategoryComboBox.addItem(category);
		}
		workerCategoryComboBox.setName("workerCategoryComboBox");

		GridBagConstraints gbc_workerCategoryComboBox = new GridBagConstraints();
		gbc_workerCategoryComboBox.gridwidth = 4;
		gbc_workerCategoryComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_workerCategoryComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerCategoryComboBox.gridx = 1;
		gbc_workerCategoryComboBox.gridy = 3;
		contentPane.add(workerCategoryComboBox, gbc_workerCategoryComboBox);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setPreferredSize(new Dimension(200, 50)); // Set panel size
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 7;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPane.add(panel, gbc_panel);

		btnAdd = new JButton("Add");
		btnAdd.setName("btnAdd");
		btnAdd.setEnabled(false);

		btnAdd.setBackground(Color.blue); // Set button color
		btnOrders.setForeground(Color.black); // Set text color
		panel.add(btnAdd);

		workerListModel = new DefaultListModel<Worker>();
		workerListLayout = new JList<>(workerListModel);
		workerListLayout.setName("workerListLayout");

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
		btnDelete.setName("btnDelete");
		btnDelete.setEnabled(false);

		btnDelete.setBackground(Color.red); // Set button color
		btnOrders.setForeground(Color.black); // Set text color
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridwidth = 7;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 8;
		contentPane.add(btnDelete, gbc_btnDelete);

		lblError.setName("lblError");

		lblError.setForeground(Color.red); // Set error label text color
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 7;
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 9;
		contentPane.add(lblError, gbc_lblError);

		KeyListener btnAddEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(!workerNameTextField.getText().trim().isEmpty()
						&& workerCategoryComboBox.getSelectedItem() != null);
				;
			}
		};
		ListSelectionListener listListner = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				btnDelete.setEnabled(workerListLayout.getSelectedIndex() != -1);

			}

		};

		workerListLayout.addListSelectionListener(listListner);
		workerNameTextField.addKeyListener(btnAddEnabler);

		btnAdd.addActionListener(e -> workerController.createNewWorker(
				new Worker(workerNameTextField.getText(), (CategoryEnum) workerCategoryComboBox.getSelectedItem())));

		btnDelete.addActionListener(
				e -> workerController.deleteWorker(workerListLayout.getSelectedValue().getWorkerId()));

	}

	@Override
	public void showAllWorkers(List<Worker> worker) {
		worker.stream().forEach(workerListModel::addElement);

	}

	@Override
	public void showError(String message, Worker worker) {
		lblError.setText(message + ": " + worker);

	}

	@Override
	public void workerAdded(Worker worker) {
		workerListModel.addElement(worker);
		resetErrorLabel();
	}

	@Override
	public void workerRemoved(Worker worker) {
		workerListModel.removeElement(worker);
		resetErrorLabel();
	}

	@Override
	public void showErrorWorkerNotFound(String message, Worker worker) {
		// TODO Auto-generated method stub

	}

	private void resetErrorLabel() {
		lblError.setText(" ");

	}
}
