package testservicii.WorkflowServices;
import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.App.StatusProiect;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Domain.Repository.EchipaRepository;
import org.Proiect.Domain.Repository.RepositoryProiect;
import org.Proiect.Domain.Repository.TaskRepository;
import org.Proiect.Servicii.ITaskWorkflowService;
import org.Proiect.Servicii.Implementari.TaskWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestTaskWorkflowService {
    @Autowired
    private ITaskWorkflowService taskWorkflowService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private RepositoryProiect repositoryProiect;
    @Autowired
    private EchipaRepository echipaRepository;

    @Test
    void testGestioneazaDepasireDeadline() {
        // Arrange
        Echipa echipa = new Echipa();
        echipa.setDenumire("Echipa Test");
        echipa = echipaRepository.save(echipa);

        Utilizator membru = new Utilizator();
        membru.setNume("Test User");
        membru.setDepasiriDeadline(2); // Sub pragul de 5 depășiri
        membru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        membru.setEchipa(echipa);
        membru = appUserRepository.save(membru);

        Task task = new Task();
        task.setDescriere("Test Task");
        task.setDataFinalizare(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)); // Deadline depășit
        task.setStatus(Status.IN_EXECUTIE);
        task.setMembru(membru);
        task = taskRepository.save(task);

        // Act
        taskWorkflowService.gestioneazaDepasireDeadline(task.getTaskUserId());

        // Assert
        Task updatedTask = taskRepository.findById(task.getTaskUserId()).orElseThrow();
        assertEquals(task.getTaskUserId(), updatedTask.getTaskUserId());
        assertEquals(membru.getUserId(), updatedTask.getMembru().getUserId()); // Membrul rămâne același
    }

    @Test
    void testGestioneazaDepasireDeadline_CuDepasiriMultiple() {
        // Arrange
        Proiect proiect = new Proiect();
        proiect.setDenumire("Test Proiect");
        proiect.setDescriere("Descriere proiect test");
        proiect.setDataIncepere(new Date());
        proiect.setStatus(StatusProiect.IN_PROGRESS);
        proiect = repositoryProiect.save(proiect);

        Echipa echipa = new Echipa();
        echipa.setDenumire("Echipa Test");
        echipa.setProiect(proiect);
        echipa = echipaRepository.save(echipa);

        Utilizator membru = new Utilizator();
        membru.setNume("Test User 2");
        membru.setDepasiriDeadline(6); // Peste pragul de 5 depășiri
        membru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        membru.setEchipa(echipa);
        membru = appUserRepository.save(membru);

        Utilizator altMembru = new Utilizator();
        altMembru.setNume("Alt User");
        altMembru.setDepasiriDeadline(0);
        altMembru.setDisponibil(true);
        altMembru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        altMembru.setEchipa(echipa);
        altMembru = appUserRepository.save(altMembru);

        Task task = new Task();
        task.setDescriere("Test Task 2");
        task.setDataFinalizare(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)); // Deadline depășit
        task.setStatus(Status.IN_EXECUTIE);
        task.setMembru(membru);
        task.setProiect(proiect);
        task = taskRepository.save(task);

        // Act
        taskWorkflowService.gestioneazaDepasireDeadline(task.getTaskUserId());

        // Assert
        Task updatedTask = taskRepository.findById(task.getTaskUserId()).orElseThrow();
        assertNotEquals(membru.getUserId(), updatedTask.getMembru().getUserId()); // Task-ul a fost reatribuit
        assertEquals(altMembru.getUserId(), updatedTask.getMembru().getUserId()); // Task-ul este acum la alt membru
    }

}
