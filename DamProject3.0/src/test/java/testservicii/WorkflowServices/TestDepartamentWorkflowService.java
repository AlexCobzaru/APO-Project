package testservicii.WorkflowServices;

import org.Proiect.Domain.Angajati.Departament;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Domain.Repository.DepartamentRepository;
import org.Proiect.Servicii.IDepartamentWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestDepartamentWorkflowService {

    @Autowired
    private IDepartamentWorkflowService departamentWorkflowService;

    @Autowired
    private AppUserRepository utilizatorRepository;

    @Autowired
    private DepartamentRepository departamentRepository;

    @Test
    void testCreeazaDepartament() {
        // Arrange
        Utilizator manager = new Utilizator();
        manager.setNume("Manager Test");
        manager.setTipUtilizator(TipUtilizator.MANAGER);
        manager = utilizatorRepository.save(manager);

        // Act
        Departament departament = departamentWorkflowService.creeazaDepartament("Departament IT", manager.getUserId());

        // Assert
        assertNotNull(departament);
        assertEquals("Departament IT", departament.getNumeDepartament());
        assertEquals(manager.getUserId(), departament.getManagerProiect().getUserId());
    }

    @Test
    void testAdaugaUtilizatorInDepartament() {
        // Arrange
        Departament departament = new Departament();
        departament.setNumeDepartament("Departament HR");
        departament = departamentRepository.save(departament);

        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Test User");
        utilizator = utilizatorRepository.save(utilizator);

        // Act
        departamentWorkflowService.adaugaUtilizatorInDepartament(departament.getId(), utilizator.getUserId(), "MEMBRU");

        // Assert
        Utilizator utilizatorActualizat = utilizatorRepository.findById(utilizator.getUserId()).orElseThrow();
        assertEquals(departament.getId(), utilizatorActualizat.getDepartament().getId());
        assertEquals("MEMBRU", utilizatorActualizat.getRol());
    }

    @Test
    void testModificaDepartament() {
        // Arrange
        Departament departament = new Departament();
        departament.setNumeDepartament("Departament Initial");
        departament = departamentRepository.save(departament);

        // Act
        departamentWorkflowService.modificaDepartament(departament.getId(), "Departament Modificat");

        // Assert
        Departament departamentActualizat = departamentRepository.findById(departament.getId()).orElseThrow();
        assertEquals("Departament Modificat", departamentActualizat.getNumeDepartament());
    }

    @Test
    void testVizualizeazaDepartament() {
        // Arrange
        Departament departament = new Departament();
        departament.setNumeDepartament("Departament Marketing");
        departament = departamentRepository.save(departament);

        // Act
        Departament departamentVizualizat = departamentWorkflowService.vizualizeazaDepartament(departament.getId());

        // Assert
        assertNotNull(departamentVizualizat);
        assertEquals(departament.getId(), departamentVizualizat.getId());
        assertEquals("Departament Marketing", departamentVizualizat.getNumeDepartament());
    }

    @Test
    void testVizualizeazaMembriiDepartament() {
        // Arrange
        Departament departament = new Departament();
        departament.setNumeDepartament("Departament Tehnic");
        departament = departamentRepository.save(departament);

        Utilizator utilizator = new Utilizator();
        utilizator.setNume("John Doe");
        utilizator.setDepartament(departament);
        utilizator = utilizatorRepository.save(utilizator);

        // Act
        List<Utilizator> membri = departamentWorkflowService.vizualizeazaMembriiDepartament(departament.getId());

        // Assert
        assertNotNull(membri);
        assertFalse(membri.isEmpty());
        assertEquals(1, membri.size());
        assertEquals("John Doe", membri.get(0).getNume());
    }
}
