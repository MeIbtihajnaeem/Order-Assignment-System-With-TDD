package com.example.orderAssignmentSystem.view.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.example.orderAssignmentSystem.controller.OrderController;
import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.view.OrderView;

public class OrderSwingView extends JFrame implements OrderView {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField orderIdTextField;
	private JTextField orderDescriptionTextField;
	private DefaultComboBoxModel<Worker> defaultWorkers;
	private JComboBox<CategoryEnum> categoryComboBox;
	private JList<CustomerOrder> orderListLayout;

	private DefaultListModel<CustomerOrder> defaultOrders;

	private JComboBox<OrderStatusEnum> statusComboBox;
	private JComboBox<Worker> workerComboBox;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnWorker;
	private JLabel lblError;

	private OrderController orderController;

	public void setOrderController(OrderController orderController) {
		this.orderController = orderController;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DefaultComboBoxModel<Worker> getDefaultWorkers() {
		return defaultWorkers;
	}

	public DefaultListModel<CustomerOrder> getDefaultOrders() {
		return defaultOrders;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JTextField getOrderIdTextField() {
		return orderIdTextField;
	}

	public JTextField getOrderDescriptionTextField() {
		return orderDescriptionTextField;
	}

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
		defaultWorkers = new DefaultComboBoxModel<Worker>();

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

		btnWorker = new JButton("Manage Worker");
		btnWorker.setName("btnWorker");
		btnWorker.setBackground(Color.gray); // Set button color
		btnWorker.setForeground(Color.black); // Set text color
		GridBagConstraints gbc_btnWorker = new GridBagConstraints();
		gbc_btnWorker.gridwidth = 2;
		gbc_btnWorker.insets = new Insets(0, 0, 5, 10);
		gbc_btnWorker.gridx = 5;
		gbc_btnWorker.gridy = 0;
		contentPane.add(btnWorker, gbc_btnWorker);

		JLabel orderIdLbl = new JLabel("Order Id");
		orderIdLbl.setFont(new Font("Arial", Font.BOLD, 14));
		orderIdLbl.setForeground(Color.BLACK); // Set text color to black
		GridBagConstraints gbc_orderIdLbl = new GridBagConstraints();
		gbc_orderIdLbl.insets = new Insets(0, 0, 5, 5);
		gbc_orderIdLbl.gridx = 0;
		gbc_orderIdLbl.gridy = 1;
		contentPane.add(orderIdLbl, gbc_orderIdLbl);

		orderIdTextField = new JTextField();
		orderIdTextField.setName("orderIdTextField");
		orderIdTextField.setEnabled(true);
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
		orderDescriptionTextField.setName("orderDescriptionTextField");
		orderDescriptionTextField.setEnabled(true);
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

		categoryComboBox = new JComboBox<>();
		categoryComboBox.setSelectedItem(null);
		for (CategoryEnum category : CategoryEnum.values()) {
			categoryComboBox.addItem(category);
		}

		categoryComboBox.setName("categoryComboBox"); // Set name
		categoryComboBox.setEnabled(true);
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

		statusComboBox = new JComboBox<>();
		for (OrderStatusEnum status : OrderStatusEnum.values()) {
			statusComboBox.addItem(status);
		}
		statusComboBox.setName("statusComboBox"); // Set name
		statusComboBox.setEnabled(true);
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

		workerComboBox = new JComboBox<Worker>(defaultWorkers);
		workerComboBox.setName("workerComboBox"); // Set name
		workerComboBox.setEnabled(true);
		GridBagConstraints gbc_workerComboBox = new GridBagConstraints();
		gbc_workerComboBox.gridwidth = 4;
		gbc_workerComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_workerComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_workerComboBox.gridx = 1;
		gbc_workerComboBox.gridy = 5;
		contentPane.add(workerComboBox, gbc_workerComboBox);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
//		panel.setBackground(Color.WHITE); // Set panel background color
		panel.setPreferredSize(new Dimension(200, 50)); // Set panel size
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 7;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		contentPane.add(panel, gbc_panel);

		btnAdd = new JButton("Add");
		btnAdd.setName("btnAdd"); // Set name
		btnAdd.setEnabled(false);
		btnAdd.setBackground(Color.blue); // Set button color
		btnAdd.setForeground(Color.black); // Set text color
		panel.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.setName("btnUpdate"); // Set name
		btnUpdate.setEnabled(false);
		btnUpdate.setBackground(Color.blue); // Set button color
		btnUpdate.setForeground(Color.black); // Set text color
		panel.add(btnUpdate);

		defaultOrders = new DefaultListModel<CustomerOrder>();
		orderListLayout = new JList<>(defaultOrders);

		orderListLayout.setName("OrderList");
		orderListLayout.setBorder(new LineBorder(new Color(0, 0, 0)));
		orderListLayout.setBackground(Color.white); // Set list background color
		orderListLayout.setForeground(Color.black); // Set list background color
		orderListLayout.setPreferredSize(new Dimension(200, 100)); // Set list size
		orderListLayout.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_orderListLayout = new GridBagConstraints();
		gbc_orderListLayout.insets = new Insets(0, 10, 5, 10); // Adjust the left margin here (changed 0 to 10)
		gbc_orderListLayout.gridwidth = 7;
		gbc_orderListLayout.fill = GridBagConstraints.BOTH;
		gbc_orderListLayout.gridx = 0;
		gbc_orderListLayout.gridy = 8;

		contentPane.add(orderListLayout, gbc_orderListLayout);

		btnDelete = new JButton("Delete");
		btnDelete.setName("btnDelete"); // Set name
		btnDelete.setEnabled(false);
		btnDelete.setBackground(Color.red); // Set button color
		btnDelete.setForeground(Color.black); // Set text color
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridwidth = 7;
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 9;
		contentPane.add(btnDelete, gbc_btnDelete);
		lblError = new JLabel(" ");
		lblError.setName("lblError");
		lblError.setForeground(Color.red); // Set error label text color
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 7;
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 10;
		contentPane.add(lblError, gbc_lblError);

		KeyAdapter btnAddUpdateEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateAddButtonState();
			}
		};

		ActionListener btnUpdateEnablerForComboBox = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateAddButtonState();
			}

		};
		ListSelectionListener listListner = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				btnDelete.setEnabled(orderListLayout.getSelectedIndex() != -1);

			}

		};
		orderListLayout.addListSelectionListener(listListner);
		orderIdTextField.addKeyListener(btnAddUpdateEnabler);
		orderDescriptionTextField.addKeyListener(btnAddUpdateEnabler);
		categoryComboBox.addActionListener(btnUpdateEnablerForComboBox);
		workerComboBox.addActionListener(btnUpdateEnablerForComboBox);
		statusComboBox.addActionListener(btnUpdateEnablerForComboBox);
		btnAdd.addActionListener(
				e -> orderController.createNewOrder(new CustomerOrder((CategoryEnum) categoryComboBox.getSelectedItem(),
						orderDescriptionTextField.getText(), (OrderStatusEnum) statusComboBox.getSelectedItem(),
						(Worker) workerComboBox.getSelectedItem())));

		btnUpdate.addActionListener(
				e -> orderController.modifyOrder(new CustomerOrder(Long.parseLong(orderIdTextField.getText()),
						(CategoryEnum) categoryComboBox.getSelectedItem(), orderDescriptionTextField.getText(),
						(OrderStatusEnum) statusComboBox.getSelectedItem(),
						(Worker) workerComboBox.getSelectedItem())));

		btnDelete.addActionListener(e -> orderController.deleteOrders(orderListLayout.getSelectedValue().getOrderId()));
		btnWorker.addActionListener(e -> {
			this.dispose();
			new WorkerSwingView().setVisible(true);
		});
	}

	private void updateAddButtonState() {
		boolean isOrderIdEmpty = orderIdTextField.getText().trim().isEmpty();
		boolean isOrderDescriptionEmpty = orderDescriptionTextField.getText().trim().isEmpty();
		boolean isCategorySelected = categoryComboBox.getSelectedItem() != null;
		boolean isWorkerSelected = workerComboBox.getSelectedItem() != null;
		boolean isStatusSelected = statusComboBox.getSelectedItem() != null;

		btnUpdate.setEnabled(!isOrderIdEmpty && !isOrderDescriptionEmpty && isCategorySelected && isWorkerSelected
				&& isStatusSelected);
//		btnAdd.setEnabled(isOrderIdEmpty && !isOrderDescriptionEmpty && isCategorySelected && isWorkerSelected
//				&& statusComboBox.getSelectedItem() == OrderStatusEnum.PENDING);

		btnAdd.setEnabled(isOrderIdEmpty && !isOrderDescriptionEmpty && isCategorySelected && isWorkerSelected);
	}

	@Override
	public void showAllOrders(List<CustomerOrder> orders) {
		orders.stream().forEach(defaultOrders::addElement);
	}

	@Override
	public void showError(String message, CustomerOrder order) {
		lblError.setText(message + ": " + order);
	}

	@Override
	public void orderAdded(CustomerOrder order) {
		defaultOrders.addElement(order);
		resetErrorLabel();
	}

	@Override
	public void orderModified(CustomerOrder order) {
		for (int i = 0; i < defaultOrders.getSize(); i++) {
			if (defaultOrders.getElementAt(i).getOrderId() == order.getOrderId()) {
				defaultOrders.removeElementAt(i);
				defaultOrders.addElement(order);
				resetErrorLabel();
			}
		}

	}

	@Override
	public void orderRemoved(CustomerOrder order) {
		defaultOrders.removeElement(order);
		resetErrorLabel();
	}

	@Override
	public void showErrorOrderNotFound(String message, CustomerOrder order) {

	}

	private void resetErrorLabel() {
		lblError.setText(" ");

	}

}
