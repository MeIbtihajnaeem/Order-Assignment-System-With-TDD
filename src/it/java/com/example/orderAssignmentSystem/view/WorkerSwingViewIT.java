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

import com.example.orderAssignmentSystem.controller.WorkerController;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.repository.postgresql.WorkerDatabaseRepository;
import com.example.orderAssignmentSystem.view.swing.WorkerSwingView;

@RunWith(GUITestRunner.class)
public class WorkerSwingViewIT extends AssertJSwingJUnitTestCase {

	private WorkerRepository workerRepository;

	private WorkerSwingView workerSwingView;

	private WorkerController workerController;

	private FrameFixture window;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private static final String PERSISTENCE_UNIT_NAME = "myPersistenceUnit";

	@Override
	protected void onSetUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		workerRepository = new WorkerDatabaseRepository(entityManager);
		for (Worker worker : workerRepository.findAll()) {
			workerRepository.delete(worker);
		}

		GuiActionRunner.execute(() -> {
			workerSwingView = new WorkerSwingView();
			workerController = new WorkerController(workerRepository, workerSwingView);
			workerSwingView.setWorkerController(workerController);
			return workerSwingView;
		});

		window = new FrameFixture(robot(), workerSwingView);
		window.show();

	}

	@Test
	@GUITest
	public void testAllWorkers() {
		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
		Worker worker2 = new Worker("Alic", CategoryEnum.PLUMBER);
		worker1 = workerRepository.save(worker1);
		worker2 = workerRepository.save(worker2);
		GuiActionRunner.execute(() -> workerController.getAllWorkers());
		assertThat(window.list().contents()).containsExactly(worker1.toString(), worker2.toString());
	}

	@Test
	@GUITest
	public void testAddButtonSuccess() {
		window.textBox("workerNameTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("workerCategoryComboBox").target().getItemAt(0);
		window.comboBox("workerCategoryComboBox").selectItem(category.toString());
		window.button(JButtonMatcher.withName("btnAdd")).click();
		Worker createdWorker = workerRepository.findAll().get(0);
		assertThat(window.list().contents()).containsExactly(createdWorker.toString());
	}

	@Test
	@GUITest
	public void testAddButtonError() {
//		Worker worker1 = new Worker("Jhon", CategoryEnum.PLUMBER);
//		worker1 = workerRepository.save(worker1);
		String name = "Muhammad Ibtihaj Naeem";
		window.textBox("workerNameTextField").enterText(name);
		CategoryEnum category = (CategoryEnum) window.comboBox("workerCategoryComboBox").target().getItemAt(0);
		window.comboBox("workerCategoryComboBox").selectItem(category.toString());
		window.button(JButtonMatcher.withName("btnAdd")).click();

		assertThat(window.list().contents()).isEmpty();
		window.label("lblError").requireText("Worker name cannot be greater than 20: " + new Worker(name, category));
	}

	@Test
	@GUITest
	public void testDeleteButtonSuccess() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		GuiActionRunner.execute(() -> workerController.createNewWorker(worker));
		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(window.list().contents()).isEmpty();

	}

	@Test
	@GUITest
	public void testDeleteButtonError() {
		Worker worker = new Worker("Jhon", CategoryEnum.PLUMBER);
		worker.setWorkerId(1l);
		GuiActionRunner.execute(() -> workerSwingView.getWorkerListModel().addElement(worker));
		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(window.list().contents()).containsExactly(worker.toString());
		window.label("lblError").requireText("No Worker found with id " + worker.getWorkerId() + ": " + null);

	}

}
