package testservicii.WorkflowServices;

import org.Proiect.ServiciiModule.ManagementProiecte.IProiecteWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.logging.Logger;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestProiecteWorkflowService {

    private static final Logger logger = Logger.getLogger(TestProiecteWorkflowService.class.getName());

    @Autowired
    private IProiecteWorkflowService proiecteWorkflowService;

    @Test
    public void test_WorkflowServiceFacade() {
        // Verificăm dacă instanța este injectată corect
        if (proiecteWorkflowService == null) {
            logger.severe("Serviciul proiecteWorkflowService nu a fost injectat corect!");
            throw new RuntimeException("Dependency injection failed");
        }

        logger.info("Instanță de IProiecteWorkflowService: " + proiecteWorkflowService);

        // Planificare nou proiect
        Integer projectId = proiecteWorkflowService.planificareProiectNou("Proiect Test", new Date());
        logger.info("Proiect ID generat: " + projectId);

        // Validare dacă proiectul a fost creat
        if (projectId == null) {
            logger.severe("Eroare la planificarea proiectului!");
            throw new RuntimeException("Project creation failed");
        }

        // Asignare manager proiect
        proiecteWorkflowService.asignareManagerProiect(projectId, 200);
        logger.info("Manager asignat proiectului cu ID: " + projectId);

        // Arhivare proiect
        proiecteWorkflowService.arhivareProiect(projectId);
        logger.info("Proiectul cu ID " + projectId + " a fost arhivat.");
    }
}
