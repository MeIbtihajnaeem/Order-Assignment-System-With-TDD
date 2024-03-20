package com.example.orderAssignmentSystem.view.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.controller.OrderController;
import com.example.orderAssignmentSystem.model.CustomerOrder;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.model.enums.OrderStatusEnum;

@RunWith(GUITestRunner.class)
public class OrderSwingViewTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;

	private OrderSwingView orderSwingView;
	@Mock
	private OrderController orderController;
	private AutoCloseable closeable;

	@Override
	protected void onSetUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);

		GuiActionRunner.execute(() -> {
			orderSwingView = new OrderSwingView();
			orderSwingView.setOrderController(orderController);
			return orderSwingView;
		});
		window = new FrameFixture(robot(), orderSwingView);
		window.show();
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test
	@GUITest
	public void testControlsInitialStates() {
		window.label(JLabelMatcher.withText("Order Id"));
		window.textBox("orderIdTextField").requireEnabled();

		window.label(JLabelMatcher.withText("Description"));
		window.textBox("orderDescriptionTextField").requireEnabled();

		window.label(JLabelMatcher.withText("Category"));
		window.comboBox("categoryComboBox").requireEnabled();
		window.label(JLabelMatcher.withText("Status"));
		window.comboBox("statusComboBox").requireEnabled();

		window.label(JLabelMatcher.withText("Worker"));
		window.comboBox("workerComboBox").requireEnabled();

		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		window.button(JButtonMatcher.withText("Update")).requireDisabled();

//		window.textBox("textFieldForSearch").requireEnabled();
//		window.button(JButtonMatcher.withText("Search")).requireDisabled();
		window.list("OrderList");
		window.button(JButtonMatcher.withText("Delete")).requireDisabled();
		window.button(JButtonMatcher.withName("btnWorker")).requireEnabled();
		window.label("lblError").requireText(" ");

	}

	// update button id = "1" , description =2 category = plumber status = Pending
	// worker = firstWorker
	@Test
	public void testWhenIdAndDescriptionAreNonEmptyAndCategoryAndStatusAndWorkerNonEmptyThenUpdateButtonShouldBeEnable() {
		window.textBox("orderIdTextField").enterText("1");
		window.textBox("orderDescriptionTextField").enterText("2");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(0);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.comboBox("workerComboBox").selectItem(0);
		window.button(JButtonMatcher.withName("btnUpdate")).requireEnabled();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();
	}
	// add button id = "1" , description =2 category = plumber status = Pending,
	// worker = firstWorker

	@Test
	public void testWhenIdIsEmptyAndDescriptionIsNonEmptyAndCategoryAndStatusNonEmptyThenAddButtonShouldBeEnable() {
		window.textBox("orderDescriptionTextField").enterText("2");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(0);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.comboBox("workerComboBox").selectItem(0);
		window.button(JButtonMatcher.withName("btnAdd")).requireEnabled();
		window.button(JButtonMatcher.withName("btnUpdate")).requireDisabled();

	}

	// add button id = "1" , description =2 category = plumber status = completed,
	// worker = firstWorker

	@Test
	public void testWhenIdIsEmptyAndDescriptionIsNonEmptyAndCategoryNonEmptyAndStatusNotPendingThenAddButtonShouldBeDisable() {
		window.textBox("orderDescriptionTextField").enterText("2");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(1);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.comboBox("workerComboBox").selectItem(0);
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();
		window.button(JButtonMatcher.withName("btnUpdate")).requireDisabled();
	}

	@Test
	public void testWhenEitherIdOrDescriptionOrWorkerAreBlankThenUpdateButtonShouldBeDisabled() {
		JTextComponentFixture idTextBox = window.textBox("orderIdTextField");
		JTextComponentFixture descriptionTextBox = window.textBox("orderDescriptionTextField");
		idTextBox.enterText("1");
		descriptionTextBox.enterText(" ");
		window.comboBox("categoryComboBox").selectItem(1);
		window.comboBox("statusComboBox").selectItem(1);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(null));

		window.button(JButtonMatcher.withText("Update")).requireDisabled();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

		idTextBox.setText("");
		descriptionTextBox.setText("");
		idTextBox.enterText(" ");
		descriptionTextBox.enterText("test");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(0);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.comboBox("workerComboBox").selectItem(0);
		window.button(JButtonMatcher.withText("Update")).requireDisabled();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

	}

	@Test
	public void testWhenEitherIdOrDescriptionOrWorkerAreBlankAndStatusNotPendingThenAddButtonShouldBeDisabled() {
		JTextComponentFixture descriptionTextBox = window.textBox("orderDescriptionTextField");
		descriptionTextBox.enterText("1");
		window.comboBox("categoryComboBox").selectItem(1);
		window.comboBox("statusComboBox").selectItem(1);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(null));

		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();
		window.button(JButtonMatcher.withText("Update")).requireDisabled();

		descriptionTextBox.setText(" ");
		descriptionTextBox.enterText("test");
		window.comboBox("categoryComboBox").selectItem(1);
		window.comboBox("statusComboBox").selectItem(1);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.comboBox("workerComboBox").selectItem(0);
		window.button(JButtonMatcher.withText("Update")).requireDisabled();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

	}

	@Test
	public void testDeleteButtonShouldBeEnabledOnlyWhenAOrderIsSelected() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.COMPLETED,
				worker);

		GuiActionRunner.execute(() -> orderSwingView.getDefaultOrders().addElement(order));
		window.list("OrderList").selectItem(0);
		JButtonFixture deleteButton = window.button(JButtonMatcher.withText("Delete"));
		deleteButton.requireEnabled();
		window.list("OrderList").clearSelection();
		deleteButton.requireDisabled();

	}

	@Test
	public void testsShowAllOrdersAddOrderDescriptionToTheList() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);
		CustomerOrder order2 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required for pipe",
				OrderStatusEnum.COMPLETED, worker);
		GuiActionRunner.execute(() -> orderSwingView.showAllOrders(Arrays.asList(order1, order2)));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(order1.toString(), order2.toString());
	}

	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);
		GuiActionRunner.execute(() -> orderSwingView.showError("error message", order1));
		window.label("lblError").requireText("error message: " + order1);
	}

	@Test
	public void testOrderAddedShouldAddTheOrderToTheListAndResetTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);
		GuiActionRunner.execute(() -> orderSwingView.orderAdded(order1));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(order1.toString());
		window.label("lblError").requireText(" ");
	}

	@Test
	public void testOrderModifiedShouldModifyTheOrderToTheListAndResetTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder previousOrder = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required",
				OrderStatusEnum.PENDING, worker);
		GuiActionRunner.execute(() -> orderSwingView.orderAdded(previousOrder));

		CustomerOrder updatedOrder = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Ac repair required",
				OrderStatusEnum.CANCELED, worker);

		GuiActionRunner.execute(() -> orderSwingView.orderModified(updatedOrder));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(updatedOrder.toString());
		window.label("lblError").requireText(" ");
	}

	@Test
	public void testOrderRemovedShouldRemoveTheOrderToTheListAndResetTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);

		Worker worker2 = new Worker("Bob", CategoryEnum.AC_TECHNICIAN);
		worker.setWorkerId(2l);
		CustomerOrder order2 = new CustomerOrder(2l, CategoryEnum.AC_TECHNICIAN, "Ac repair required",
				OrderStatusEnum.CANCELED, worker2);
		GuiActionRunner.execute(() -> {
			DefaultListModel<CustomerOrder> listOrderModel = orderSwingView.getDefaultOrders();
			listOrderModel.addElement(order1);
			listOrderModel.addElement(order2);
		});

		GuiActionRunner.execute(() -> orderSwingView.orderRemoved(order1));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(order2.toString());
		window.label("lblError").requireText(" ");
	}

	@Test
	public void testAddButtonShouldDelegateToOrderControllerNewOrder() {
		window.textBox("orderDescriptionTextField").enterText("Plumber required");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(0);
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));
		window.button(JButtonMatcher.withText("Add")).click();

		CustomerOrder order1 = new CustomerOrder(CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);
		verify(orderController).createNewOrder(order1);
	}

	@Test
	public void testUpdateButtonShouldDelegateToOrderControllerModifyOrder() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder previousOrder = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required",
				OrderStatusEnum.PENDING, worker);
		GuiActionRunner.execute(() -> orderSwingView.orderAdded(previousOrder));
		window.textBox("orderIdTextField").enterText(1l + "");
		window.textBox("orderDescriptionTextField").enterText("Ac repair required");
		window.comboBox("categoryComboBox").selectItem(0);
		window.comboBox("statusComboBox").selectItem(0);
		GuiActionRunner.execute(() -> orderSwingView.getDefaultWorkers().addElement(worker));

		CustomerOrder updatedOrder = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Ac repair required",
				OrderStatusEnum.PENDING, worker);

		window.button(JButtonMatcher.withName("btnUpdate")).click();
		verify(orderController).modifyOrder(updatedOrder);
	}

	@Test
	public void testDeleteButtonShouldDelegateToOrderControllerDeleteOrder() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		CustomerOrder order1 = new CustomerOrder(1l, CategoryEnum.PLUMBER, "Plumber required", OrderStatusEnum.PENDING,
				worker);

		Worker worker2 = new Worker("Bob", CategoryEnum.AC_TECHNICIAN);
		worker.setWorkerId(2l);
		CustomerOrder order2 = new CustomerOrder(2l, CategoryEnum.AC_TECHNICIAN, "Ac repair required",
				OrderStatusEnum.CANCELED, worker2);

		GuiActionRunner.execute(() -> {
			DefaultListModel<CustomerOrder> listOrderModel = orderSwingView.getDefaultOrders();
			listOrderModel.addElement(order1);
			listOrderModel.addElement(order2);
		});
		window.list("OrderList").selectItem(1);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		verify(orderController).deleteOrders(order2.getOrderId());

	}

}
