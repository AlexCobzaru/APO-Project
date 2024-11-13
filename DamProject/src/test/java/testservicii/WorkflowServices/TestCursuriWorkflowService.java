package testservicii.WorkflowServices;

import java.util.logging.Logger;

import org.Proiect.ServiciiModule.ManagementCursuri.ICursuriWorkflowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCursuriWorkflowService {

    private static Logger logger = Logger.getLogger(TestCursuriWorkflowService.class.getName());

    @Autowired
    private ICursuriWorkflowService cursuriWorkflowService;

    @Test
    public void test_WorkflowServiceFacade() {
        logger.info("Domain Service implementation instance:: " + cursuriWorkflowService);

        // Planificarea unui curs nou
        Integer cursId = cursuriWorkflowService.planificareCursNou("Java Avansat", "Curs pentru programatori experimentați");
        logger.info("Curs ID generat: " + cursId);

        // Asignarea cursului unui membru
        cursuriWorkflowService.asignareCursLaMembru(cursId, 203);
        logger.info("Cursul a fost asignat membrului.");

        // Evaluarea cursului
        cursuriWorkflowService.evaluareCurs(cursId, 203, 9);
        logger.info("Cursul a fost evaluat.");
    }
}