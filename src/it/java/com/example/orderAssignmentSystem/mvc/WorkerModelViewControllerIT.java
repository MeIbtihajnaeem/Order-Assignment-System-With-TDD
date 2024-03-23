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

import com.example.orderAssignmentSystem.controller.WorkerController;
import com.example.orderAssignmentSystem.model.Worker;
import com.example.orderAssignmentSystem.model.enums.CategoryEnum;
import com.example.orderAssignmentSystem.repository.WorkerRepository;
import com.example.orderAssignmentSystem.repository.postgresql.WorkerDatabaseRepository;
import com.example.orderAssignmentSystem.view.swing.WorkerSwingView;

@RunWith(GUITestRunner.class)
public class WorkerModelViewControllerIT extends AssertJSwingJUnitTestCase {
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

	@Override
	protected void onTearDown() {
		entityManager.close();
	}

	@Test
	public void testAddWorker() {
		window.textBox("workerNameTextField").enterText("test");
		CategoryEnum category = (CategoryEnum) window.comboBox("workerCategoryComboBox").target().getItemAt(0);
		window.comboBox("workerCategoryComboBox").selectItem(category.toString());
		window.button(JButtonMatcher.withName("btnAdd")).click();
		Worker worker = new Worker("test", category);
		Long workerId = 1l;
		worker.setWorkerId(workerId);
		assertThat(workerRepository.findById(workerId)).isEqualTo(worker);
	}

	@Test
	public void testDeleteWorker() {
		Worker worker = new Worker("test", CategoryEnum.PLUMBER);
		Worker newWorker = workerRepository.save(worker);
		GuiActionRunner.execute(() -> workerController.getAllWorkers());
		window.list().selectItem(0);
		window.button(JButtonMatcher.withName("btnDelete")).click();
		assertThat(workerRepository.findById(newWorker.getWorkerId())).isNull();
	}

}
