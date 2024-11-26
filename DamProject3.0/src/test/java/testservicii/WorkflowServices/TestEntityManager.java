package testservicii.WorkflowServices;

import jakarta.persistence.EntityManager;
import org.Proiect.Domain.App.StatusProiect;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.Status;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestEntityManager {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void testEntityManager() {
        // Verificăm că EntityManager este injectat corect
        assertNotNull(em);

        // Crearea unui Proiect
        Proiect proiect = new Proiect();
        proiect.setDenumire("Proiect Test");
        proiect.setDescriere("Descriere Proiect Test");
        proiect.setStatus(StatusProiect.IN_PROGRESS);  // Asigură-te că setăm statusul
        proiect.setDataIncepere(new Date());  // Setăm data de începere
        em.persist(proiect); // Salvăm proiectul în baza de date

        // Crearea unui Utilizator pentru lider și membru (presupunem că ai o entitate Utilizator)
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        em.persist(lider);  // Salvăm liderul în baza de date

        Utilizator membru = new Utilizator();
        membru.setNume("Membru Test");
        em.persist(membru);  // Salvăm membrul în baza de date

        // Crearea unui Task
        Task task = new Task();
        task.setDenumire("Task Test");
        task.setDescriere("Descriere task");
        task.setDataIncepere(new Date());
        task.setDataFinalizare(new Date());
        task.setStatus(Status.IN_EXECUTIE);  // Setăm statusul task-ului
        task.setDeadline(LocalDate.now().plusDays(2));  // Deadline-ul pentru task
        task.setProiect(proiect);  // Asociem task-ul cu proiectul
        task.setLider(lider);  // Setăm liderul task-ului
        task.setMembru(membru);  // Setăm membrul task-ului

        em.persist(task);  // Salvăm task-ul în baza de date

        // Interogarea pentru a obține toate task-urile
        List<Task> taskList = em.createQuery("SELECT t FROM Task t", Task.class)
                .getResultList();

        // Verificăm că există cel puțin un task în baza de date
        assertTrue(taskList.size() > 0, "Ar trebui să existe cel puțin un task");

        // Afișăm fiecare task
        for (Task t : taskList) {
            System.out.println("Task: " + t);
        }

        // Verificăm că task-ul salvat este prezent
        Task taskSalvat = em.find(Task.class, task.getTaskUserId());
        assertNotNull(taskSalvat, "Task-ul salvat trebuie să existe în baza de date");
        assertEquals(task.getDenumire(), taskSalvat.getDenumire(), "Denumirea task-ului ar trebui să fie aceeași");
        assertEquals(task.getDescriere(), taskSalvat.getDescriere(), "Descrierea task-ului ar trebui să fie aceeași");
    }
}
