package testservicii.WorkflowServices;

import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestEntityManagerJPA {

    @Autowired
    private TestEntityManager testEntityManager;  // Aici injectezi TestEntityManager

    @Test
    void testEntityManager() {
        // Verificăm că TestEntityManager este disponibil
        assertNotNull(testEntityManager);

        // Creăm și persistăm o entitate LiderEchipa
        LiderEchipa liderEchipa = new LiderEchipa();
        liderEchipa.setNume("Ion Popescu");
        liderEchipa = testEntityManager.persistFlushFind(liderEchipa);  // Persistăm și obținem înapoi entitatea

        // Creăm și persistăm o entitate Proiect
        Proiect proiect = new Proiect();
        proiect.setDenumire("Proiect Test");
        proiect.setLider(liderEchipa);  // Asociem liderul cu proiectul
        proiect = testEntityManager.persistFlushFind(proiect);  // Persistăm și obținem înapoi entitatea

        // Creăm și persistăm o entitate Task
        Task task = new Task();
        task.setDenumire("Task Test");
        task.setDescriere("Descriere task test");
        task.setDataIncepere(new java.util.Date());
        task.setDataFinalizare(new java.util.Date());
        task.setStatus(Status.IN_EXECUTIE);  // Statusul este un enum
        task.setDeadline(LocalDate.now().plusDays(5));  // Dead-line peste 5 zile
        task.setProiect(proiect);  // Asociem task-ul cu proiectul
        task = testEntityManager.persistFlushFind(task);  // Persistăm și obținem înapoi entitatea

        // Verificăm că proiectul a fost salvat și că există în baza de date
        Proiect foundProiect = testEntityManager.find(Proiect.class, proiect.getId());
        assertNotNull(foundProiect);
        assertEquals("Proiect Test", foundProiect.getDenumire());

        // Verificăm că task-ul a fost salvat și că este asociat corect cu proiectul
        Task foundTask = testEntityManager.find(Task.class, task.getTaskUserId());
        assertNotNull(foundTask);
        assertEquals("Task Test", foundTask.getDenumire());
        assertEquals(task.getProiect().getId(), foundTask.getProiect().getId());

        // Verificăm că proiectul găsit are task-ul asociat
        assertTrue(foundProiect.getTasks().size() > 0);
        assertTrue(foundProiect.getTasks().contains(foundTask));  // Verificăm dacă task-ul este asociat corect cu proiectul

        // Verificăm lista de proiecte
        List<Proiect> proiecteList = testEntityManager.getEntityManager()
                .createQuery("SELECT p FROM Proiect p", Proiect.class)
                .getResultList();
        assertTrue(proiecteList.size() > 0);  // Verificăm că există cel puțin un proiect în baza de date
    }
}
