package com.example.orderAssignmentSystem.view.swing;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.orderAssignmentSystem.controller.WorkerController;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;

@RunWith(GUITestRunner.class)
public class WorkerSwingViewTest extends AssertJSwingJUnitTestCase {
	private FrameFixture window;

	private WorkerSwingView workerSwingView;
	@Mock
	private WorkerController workerController;
	private AutoCloseable closeable;

	@Override
	protected void onSetUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);

		GuiActionRunner.execute(() -> {
			workerSwingView = new WorkerSwingView();
			workerSwingView.setWorkerController(workerController);
			return workerSwingView;
		});
		window = new FrameFixture(robot(), workerSwingView);
		window.show();
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test
	@GUITest
	public void testControlsInitialStates() {

		window.label(JLabelMatcher.withText("Name"));
		window.textBox("workerNameTextField").requireEnabled();

		window.label(JLabelMatcher.withText("Category"));
		window.comboBox("workerCategoryComboBox").requireEnabled();

		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

		window.list("workerListLayout");
		window.button(JButtonMatcher.withText("Delete")).requireDisabled();

		window.button(JButtonMatcher.withName("btnOrders")).requireEnabled();
		window.label("lblError").requireText(" ");

	}

	@Test
	public void testWhenNameIsNonEmptyAndCategoryAndOrdersNonEmptyThenAddButtonShouldBeEnable() {
		window.textBox("workerNameTextField").enterText("2");
		window.comboBox("workerCategoryComboBox").selectItem(0);
		window.button(JButtonMatcher.withName("btnAdd")).requireEnabled();
	}

	@Test
	public void testWhenNameIsEmptyAndCategoryIsNonEmptyThenAddButtonShouldBeDisable() {
		window.textBox("workerNameTextField").enterText(" ");
		window.comboBox("workerCategoryComboBox").selectItem(0);
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();
	}

	@Test
	public void testWhenEitherNameOrCategoryAreBlankThenAddButtonShouldBeDisabled() {
		JTextComponentFixture name = window.textBox("workerNameTextField");
		name.enterText(" ");
		JComboBoxFixture combo = window.comboBox("workerCategoryComboBox");
		combo.clearSelection();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

		name.setText("123");
		combo.clearSelection();
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();

		name.setText(" ");
		combo.selectItem(0);
		window.button(JButtonMatcher.withName("btnAdd")).requireDisabled();
	}

	@Test
	public void testDeleteButtonShouldBeEnabledOnlyWhenAWorkerIsSelected() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);

		GuiActionRunner.execute(() -> workerSwingView.getWorkerListModel().addElement(worker));
		window.list("workerListLayout").selectItem(0);
		JButtonFixture deleteButton = window.button(JButtonMatcher.withName("btnDelete"));
		deleteButton.requireEnabled();
		window.list("workerListLayout").clearSelection();
		deleteButton.requireDisabled();

	}

	@Test
	public void testsShowAllOrdersAddWorkerDescriptionToTheList() {
		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker1.setWorkerId(1l);

		Worker worker2 = new Worker("Bob", CategoryEnum.AC_TECHNICIAN);
		worker2.setWorkerId(2l);

		GuiActionRunner.execute(() -> workerSwingView.showAllWorkers(Arrays.asList(worker1, worker2)));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(worker1.toString(), worker2.toString());
	}

	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);

		GuiActionRunner.execute(() -> workerSwingView.showError("error message", worker));
		window.label("lblError").requireText("error message: " + worker);
	}

	@Test
	public void testWorkerAddedShouldAddTheWorkerToTheListAndResetTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);

		GuiActionRunner.execute(() -> workerSwingView.workerAdded(worker));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(worker.toString());
		window.label("lblError").requireText(" ");
	}

	@Test
	public void testWorkerRemovedShouldRemoveTheWorkerToTheListAndResetTheErrorLabel() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);

		Worker worker2 = new Worker("Bob", CategoryEnum.AC_TECHNICIAN);
		worker.setWorkerId(2l);

		GuiActionRunner.execute(() -> {
			DefaultListModel<Worker> listOrderModel = workerSwingView.getWorkerListModel();
			listOrderModel.addElement(worker);
			listOrderModel.addElement(worker2);
		});

		GuiActionRunner.execute(() -> workerSwingView.workerRemoved(worker));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(worker2.toString());
		window.label("lblError").requireText(" ");
	}

	@Test
	public void testAddButtonShouldDelegateToWorkerControllerNewWorker() {
		window.textBox("workerNameTextField").enterText("Jhon");
		window.comboBox("workerCategoryComboBox").selectItem(0);

		window.button(JButtonMatcher.withName("btnAdd")).click();
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);

		verify(workerController).createNewWorker(worker);
	}

	@Test
	public void testDeleteButtonShouldDelegateToOrderControllerDeleteOrder() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);

		Worker worker2 = new Worker("Bob", CategoryEnum.AC_TECHNICIAN);
		worker2.setWorkerId(2l);

		GuiActionRunner.execute(() -> {
			DefaultListModel<Worker> listOrderModel = workerSwingView.getWorkerListModel();
			listOrderModel.addElement(worker);
			listOrderModel.addElement(worker2);
		});
		window.list("workerListLayout").selectItem(1);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		verify(workerController).deleteWorker(worker2.getWorkerId());

	}

}
