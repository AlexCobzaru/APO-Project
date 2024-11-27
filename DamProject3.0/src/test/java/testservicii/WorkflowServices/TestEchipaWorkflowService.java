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

        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", 1);


        assertNotNull(echipa);
        assertEquals("Echipa Test", echipa.getDenumire());
        assertEquals(1, echipa.getLider().getUserId());
    }

    @Test
    public void testAdaugaMembruInEchipa() {


        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider); // Asigură-te că liderul are un ID


        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());


        Utilizator membru = new Utilizator();
        membru.setNume("Membru Test");
        membru.setTipUtilizator(TipUtilizator.MEMBRUECHIPA);
        membru = utilizatorRepository.save(membru);


        echipaWorkflowService.adaugaMembruInEchipa(echipa.getIdEchipa(), membru.getUserId());


        assertEquals(1, echipaWorkflowService.vizualizeazaMembriiEchipa(echipa.getIdEchipa()).size());
    }

    @Test
    public void testModificaEchipa() {

        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider);


        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());


        echipaWorkflowService.modificaEchipa(echipa.getIdEchipa(), "Echipa Modificata");


        Echipa echipaModificata = echipaWorkflowService.vizualizeazaEchipa(echipa.getIdEchipa());
        assertEquals("Echipa Modificata", echipaModificata.getDenumire());
    }

    @Test
    public void testArhiveazaEchipa() {

        Utilizator lider = new Utilizator();
        lider.setNume("Lider Test");
        lider.setTipUtilizator(TipUtilizator.LIDER);
        lider = utilizatorRepository.save(lider);


        Echipa echipa = echipaWorkflowService.creeazaEchipa("Echipa Test", lider.getUserId());


        echipaWorkflowService.arhiveazaEchipa(echipa.getIdEchipa());


        Echipa echipaArhivata = echipaWorkflowService.vizualizeazaEchipa(echipa.getIdEchipa());
        assertTrue(echipaArhivata.isArhivata());
    }
}
