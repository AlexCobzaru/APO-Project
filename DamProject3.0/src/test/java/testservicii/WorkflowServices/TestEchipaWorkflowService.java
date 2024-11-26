package testservicii.WorkflowServices;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Servicii.IEchipaWorkflowService;
import org.Proiect.SpringBootDomain_AplicatieDAM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = SpringBootDomain_AplicatieDAM.class)
public class TestEchipaWorkflowService {

    @Autowired
    private IEchipaWorkflowService echipaWorkflowService;
@Autowired
private AppUserRepository utilizatorRepository;
    @Test
    public void testCreeazaEchipa() {
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        utilizatorRepository.save(lider);
        // Creăm o echipă
        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", 1);

        // Verificăm că echipa a fost creată cu succes
        assertNotNull(echipa);
        assertEquals("Echipa Test", echipa.getDenumire());
        assertEquals(1, echipa.getLider().getUserId());
    }

    @Test
    public void testAdaugaMembruInEchipa() {

        // Creează și salvează liderul
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider); // Asigură-te că liderul are un ID

        // Creează echipa
        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());

        // Creează și salvează membrul
        Utilizator membru = new Utilizator();
        membru.setNume("Membru Test");
        membru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        membru = utilizatorRepository.save(membru);

        // Adaugă membrul în echipă
        echipaWorkflowService.adaugaMembruInEchipa(echipa.getIdEchipa(), membru.getUserId());

        // Verifică că membrul a fost adăugat
        assertEquals(1, echipaWorkflowService.vizualizeazaMembriiEchipa(echipa.getIdEchipa()).size());
    }

    @Test
    public void testModificaEchipa() {
        // Creează și salvează liderul
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider);

        // Creează echipa
        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());

        // Modifică numele echipei
        echipaWorkflowService.modificaEchipa(echipa.getIdEchipa(), "Echipa Modificata");

        // Verifică că numele a fost modificat
        Echipa echipaModificata = echipaWorkflowService.vizualizeazaEchipa(echipa.getIdEchipa());
        assertEquals("Echipa Modificata", echipaModificata.getDenumire());
    }

    @Test
    public void testArhiveazaEchipa() {
        // Creează și salvează liderul
        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider);

        // Creează echipa
        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());

        // Arhivează echipa
        echipaWorkflowService.arhiveazaEchipa(echipa.getIdEchipa());

        // Verifică că echipa este arhivată
        Echipa echipaArhivata = echipaWorkflowService.vizualizeazaEchipa(echipa.getIdEchipa());
        assertTrue(echipaArhivata.isArhivata());
    }
}
