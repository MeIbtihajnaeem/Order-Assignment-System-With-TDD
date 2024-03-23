package com.example.orderAssignmentSystem.view;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.orderAssignmentSystem.controller.OrderController;
import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;
import com.example.orderAssignmentSystem.repository.OrderRepository;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.repository.postgresql.OrderDatabaseRepository;
import com.example.orderAssignmentSystem.repository.postgresql.WorkerDatabaseRepository;
import com.example.orderAssignmentSystem.view.swing.OrderSwingView;

@RunWith(GUITestRunner.class)
public class OrderSwingViewIT extends AssertJSwingJUnitTestCase {
	private OrderRepository orderRepository;

	private OrderSwingView orderSwingView;

	private WorkerRepository workerRepository;

	private OrderController orderController;

	private FrameFixture window;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";

	@Override
	protected void onSetUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		workerRepository = new WorkerDatabaseRepository(entityManager);
		orderRepository = new OrderDatabaseRepository(entityManager);
		for (Worker worker : workerRepository.findAll()) {
			workerRepository.delete(worker);
		}

		for (CustomerOrder order : orderRepository.findAll()) {
			orderRepository.delete(order);
		}

		GuiActionRunner.execute(() -> {
			orderSwingView = new OrderSwingView();
			orderController = new OrderController(orderRepository, orderSwingView, workerRepository);
			orderSwingView.setOrderController(orderController);
			return orderSwingView;
		});

		window = new FrameFixture(robot(), orderSwingView);
		window.show();

	}

	@Override
	protected void onTearDown() {
		entityManager.close();
	}

	@Test
	@GUITest
	public void testAllOrders() {
		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker worker2 = new Worker("Alic", CategoryEnum.PLUMBER);
		worker1 = workerRepository.save(worker1);
		worker2 = workerRepository.save(worker2);
		CustomerOrder order1 = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING, worker1);
		CustomerOrder orde2 = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING, worker1);
		order1 = orderRepository.save(order1);
		orde2 = orderRepository.save(orde2);
		GuiActionRunner.execute(() -> orderController.allOrders());
		assertThat(window.list().contents()).containsExactly(order1.toString(), orde2.toString());
	}

	@Test
	@GUITest
	public void testAddButtonSuccess() {
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(0);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = (OrderStatusEnum) window.comboBox("statusComboBox").target().getItemAt(0);
		window.comboBox("statusComboBox").selectItem(status.toString());

		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));

		window.button(JButtonMatcher.withName("btnAdd")).click();
		CustomerOrder order = orderRepository.findAll().get(0);
		assertThat(window.list().contents()).containsExactly(order.toString());
	}

	@Test
	@GUITest
	public void testUpdateButtonSuccess() {

		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);
		CustomerOrder oldOrder = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING,
				newWorker);
		Long orderId = orderRepository.save(oldOrder).getOrderId();
		oldOrder.setOrderId(orderId);

		window.textBox("orderIdTextField").enterText(oldOrder.getOrderId().toString());
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(0);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = (OrderStatusEnum) window.comboBox("statusComboBox").target().getItemAt(1);
		window.comboBox("statusComboBox").selectItem(status.toString());

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));
		GuiActionRunner.execute(() -> orderSwingView.getDefaultOrders().addElement(oldOrder));

		window.button(JButtonMatcher.withName("btnUpdate")).click();
		CustomerOrder order = orderRepository.findById(oldOrder.getOrderId());
		assertThat(window.list().contents()).containsExactly(order.toString());
	}

	@Test
	@GUITest
	public void testAddButtonError() {
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(0);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = OrderStatusEnum.COMPLETED;
		window.comboBox("statusComboBox").selectItem(status.toString());

		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));

		window.button(JButtonMatcher.withName("btnAdd")).click();
		assertThat(window.list().contents()).isEmpty();

		window.label("lblError").requireText("Order Status cannot be COMPLETED while creating order: "
				+ new CustomerOrder(category, "test", status, newWorker));
	}

	@Test
	@GUITest
	public void testUpdateButtonError() {

		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);
		CustomerOrder oldOrder = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING,
				newWorker);
		Long orderId = orderRepository.save(oldOrder).getOrderId();
		oldOrder.setOrderId(orderId);

		window.textBox("orderIdTextField").enterText(oldOrder.getOrderId().toString());
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(1);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = (OrderStatusEnum) window.comboBox("statusComboBox").target().getItemAt(1);
		window.comboBox("statusComboBox").selectItem(status.toString());

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));
		GuiActionRunner.execute(() -> orderSwingView.getDefaultOrders().addElement(oldOrder));

		window.button(JButtonMatcher.withName("btnUpdate")).click();

		window.label("lblError").requireText("Worker and order category must change togather: "
				+ new CustomerOrder(category, "test", status, newWorker));
	}

	@Test
	@GUITest
	public void testDeleteButtonSuccess() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker = workerRepository.save(worker);

		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING, worker);
		GuiActionRunner.execute(() -> orderController.createNewOrder(order));
//		order.setOrderId(1l);

		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(window.list().contents()).isEmpty();
	}

	@Test
	@GUITest
	public void testDeleteButtonError() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker = workerRepository.save(worker);

		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING, worker);
		order.setOrderId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultOrders().addElement(order));
		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(window.list().contents()).containsExactly(order.toString());
		window.label("lblError").requireText("No Order found with id " + worker.getWorkerId() + ": " + null);

	}

}
