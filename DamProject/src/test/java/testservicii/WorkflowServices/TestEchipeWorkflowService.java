package testservicii.WorkflowServices;

import java.util.logging.Logger;

import org.Proiect.ServiciiModule.ManagementEchipe.IEchipeWorkflowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class TestEchipeWorkflowService {

    private static Logger logger = Logger.getLogger(TestEchipeWorkflowService.class.getName());

    @Autowired
    private IEchipeWorkflowService echipeWorkflowService;

    @Test
    public void test_WorkflowServiceFacade() {
        logger.info("Domain Service implementation instance:: " + echipeWorkflowService);

        // Crearea unei echipe noi
        Integer echipaId = echipeWorkflowService.creeazaEchipaNoua("Echipa Dev");
        logger.info("Echipa ID generat: " + echipaId);

        // Adăugarea unui membru în echipă
        echipeWorkflowService.adaugaMembruEchipa(echipaId, 101);
        logger.info("Membru adăugat în echipă.");

        // Arhivare echipă
        echipeWorkflowService.arhivareEchipa(echipaId);
        logger.info("Echipa a fost arhivată.");
    }
}