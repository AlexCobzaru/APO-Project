package testservicii.WorkflowServices;

import java.util.logging.Logger;

import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.IDepartamenteWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestDepartamenteWorkflowService {

    private static Logger logger = Logger.getLogger(TestDepartamenteWorkflowService.class.getName());

    @Autowired
    private IDepartamenteWorkflowService departamenteWorkflowService;

    @Test
    public void test_WorkflowServiceFacade() {
        logger.info("Domain Service implementation instance:: " + departamenteWorkflowService);

        // Crearea unui departament nou
        Integer departamentId = departamenteWorkflowService.creeazaDepartamentNou("Departament IT");
        logger.info("Departament ID generat: " + departamentId);

        // Adăugarea unui angajat în departament
        departamenteWorkflowService.adaugaAngajatDepartament(departamentId, 202);
        logger.info("Angajat adăugat în departament.");
    }


}