package testservicii.WorkflowServices;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.Proiect.ServiciiModule.ManagementTaskuri.ITaskWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestTaskWorkflowService {

    private static Logger logger = Logger.getLogger(TestTaskWorkflowService.class.getName());

    @Autowired
    private ITaskWorkflowService taskWorkflowService;

    @Test
    public void test_WorkflowServiceFacade() {
        logger.info("Domain Service implementation instance:: " + taskWorkflowService);

        // Crearea unui task nou
        Integer taskId = taskWorkflowService.creeazaTaskNou("Task de test", LocalDate.now().plusDays(5));
        logger.info("Task ID generat: " + taskId);

        // Asignarea task-ului unui membru
        Integer membruId = 100;
        taskWorkflowService.asignareTaskMembru(taskId, membruId);
        logger.info("Task-ul cu ID-ul " + taskId + " a fost asignat membrului cu ID-ul " + membruId);

        // Modificarea task-ului
        taskWorkflowService.modificareTask(taskId, "Descriere modificată");
        logger.info("Task-ul cu ID-ul " + taskId + " a fost modificat.");
    }
}