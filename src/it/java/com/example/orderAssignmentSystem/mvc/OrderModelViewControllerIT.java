package com.example.orderAssignmentSystem.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

public class OrderModelViewControllerIT extends AssertJSwingJUnitTestCase {

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
	public void testAddOrder() {
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(0);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = (OrderStatusEnum) window.comboBox("statusComboBox").target().getItemAt(0);
		window.comboBox("statusComboBox").selectItem(status.toString());

		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));

		window.button(JButtonMatcher.withName("btnAdd")).click();
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(1l);
		order.setOrderDescription("test");
		order.setCategory(category);
		order.setOrderStatus(status);
		order.setWorker(newWorker);
		assertThat(orderRepository.findById(1l)).isEqualTo(order);
	}

	@Test
	public void testUpdateOrder() {
		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker1);
		CustomerOrder oldOrder = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING,
				newWorker);
		Long orderId = orderRepository.save(oldOrder).getOrderId();
		oldOrder.setOrderId(orderId);

		window.textBox("orderIdTextField").enterText(orderId.toString());
		window.textBox("orderDescriptionTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("categoryComboBox").target().getItemAt(0);
		window.comboBox("categoryComboBox").selectItem(category.toString());

		OrderStatusEnum status = (OrderStatusEnum) window.comboBox("statusComboBox").target().getItemAt(1);
		window.comboBox("statusComboBox").selectItem(status.toString());

		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(newWorker));

		window.button(JButtonMatcher.withName("btnUpdate")).click();
		CustomerOrder order = new CustomerOrder();
		order.setOrderId(1l);
		order.setOrderDescription("test");
		order.setCategory(category);
		order.setOrderStatus(status);
		order.setWorker(newWorker);
		assertThat(orderRepository.findById(1l)).isEqualTo(order);
	}

	@Test
	public void testDeleteOrder() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker = workerRepository.save(worker);
		CustomerOrder order = new CustomerOrder(CategoryEnum.PLUMBER, "description", OrderStatusEnum.PENDING, worker);
		CustomerOrder newOrder = orderRepository.save(order);
		GuiActionRunner.execute(() -> orderController.allOrders());
		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(orderRepository.findById(newOrder.getOrderId())).isNull();
	}

}
