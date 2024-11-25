package testservicii.WorkflowServices;

import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.App.StatusProiect;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Raport;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Domain.Repository.EchipaRepository;
import org.Proiect.Domain.Repository.TaskRepository;
import org.Proiect.Servicii.IProiecteWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.logging.Logger;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestProiecteWorkflowService {

    private static final Logger logger = Logger.getLogger(TestProiecteWorkflowService.class.getName());

    @Autowired
    private IProiecteWorkflowService proiecteWorkflowService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    EchipaRepository echipaRepository;
    @Autowired
    TaskRepository taskRepository;
    @Test
    public void testWorkflowServiceFacade() {
        logger.info("Starting Workflow Test...");

        // 1. Creare lider echipă și echipă
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = appUserRepository.save(lider);
        Echipa echipa = new Echipa();
        echipa.setDenumire("Echipa Test");
        echipa = echipaRepository.save(echipa);

        // 2. Creare proiect
        Proiect proiect = proiecteWorkflowService.creareProiect(
                "Proiect Workflow Test",
                "Descriere Workflow Test",
                lider,
                Collections.singletonList(echipa)
        );
        logger.info("Proiect creat: " + proiect.getDenumire() + " (ID: " + proiect.getId() + ")");

        // 3. Adaugare echipă nouă la proiect
        Echipa echipaNoua = new Echipa();
        echipaNoua.setDenumire("Echipa Nouă");
        echipaNoua = echipaRepository.save(echipaNoua);
        Proiect proiectActualizat = proiecteWorkflowService.adaugaEchipaLaProiect(proiect.getId(), echipaNoua);
        logger.info("Echipa adăugată: " + echipaNoua.getDenumire() + " la proiect: " + proiectActualizat.getDenumire());

        // 4. Creare și persistare membru
        Utilizator membru = new Utilizator();
        membru.setNume("Membru Test");
        membru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        membru = appUserRepository.save(membru);  // Asigură-te că membrul este salvat în baza de date înainte de a-l folosi

        // 5. Creare task și asociere membru
        Task task = new Task();
        task.setDenumire("Task Workflow");
        task.setMembru(membru);  // Asociază membrul salvat la task
        task = taskRepository.save(task);  // Salvează task-ul

        // 6. Atribuire task membru
        Task taskAtribuit = proiecteWorkflowService.atribuieTaskMembru(task.getTaskUserId(), membru);
        logger.info("Task-ul " + taskAtribuit.getDenumire() + " atribuit membrului: " + membru.getNume());
    }
}
