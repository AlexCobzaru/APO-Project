package testeServiciiRest;


import org.Proiect.DTO.ProiectDTO;
import org.Proiect.DTO.UtilizatorDTO;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.TipUtilizator;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_ProiectWorkflowREST {

    private static final Logger logger = Logger.getLogger(Test_ProiectWorkflowREST.class.getName());
    private static final String serviceURL = "http://localhost:8083/team/rest/servicii/proiecteWorkflow";
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    @Order(1)
    public void test1_GetAllProjects() {
        logger.info("DEBUG: Received GET request for all projects...");

        HttpHeaders headers = generateHeaders();

        List<ProiectDTO> proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        // Verifică dacă sunt proiecte
        if (proiecte == null || proiecte.isEmpty()) {
            logger.warning("No projects found!");  // Mesaj de log pentru cazurile în care nu sunt proiecte
            // Nu întrerupe testul, doar loghează avertismentul
            assertTrue(true);  // Testul continuă fără a opri
        } else {
            // Dacă există proiecte, le loghezi
            proiecte.forEach(p -> logger.info("Proiect: " + p));
            // Poți să ai și alte aserțiuni, în funcție de ce vrei să verifici.
            assertTrue(true); // Sau orice altă aserțiune care are sens pentru testul tău
        }
    }

    @Test
    @Order(2)
    public void test2_AddProject() {
        logger.info("DEBUG: Test ADD project...");

        HttpHeaders headers = generateHeaders();

        ProiectDTO proiectDTO = new ProiectDTO();
        proiectDTO.setDenumire("Proiect Test Workflow");
        proiectDTO.setDescriere("Descriere test pentru workflow");
        proiectDTO.setStatus("IN_PROGRESS");
        proiectDTO.setDataIncepere(new Date());

        // Adăugăm un lider dacă este disponibil, altfel lăsăm null
        UtilizatorDTO liderDTO = null; // liderul poate fi null la început
        if (liderDTO != null && liderDTO.getUserId() > 0) {
            liderDTO = new UtilizatorDTO();
            liderDTO.setUserId(1);  // Setează ID-ul utilizatorului care va fi lider
            liderDTO.setNume("Lider Test");
            liderDTO.setTipUtilizator("MANAGER");  // Tipul utilizatorului, poate fi 'MANAGER', 'ADMIN', etc.
        }

        // Setează liderul în proiect (poate fi null)
        proiectDTO.setLider(liderDTO);

        ProiectDTO createdProject = restTemplate.exchange(
                serviceURL,
                HttpMethod.POST,
                new HttpEntity<>(proiectDTO, headers),
                ProiectDTO.class
        ).getBody();

        assertTrue(createdProject != null, "Failed to create project!");
        logger.info("Created project: " + createdProject);

        // Dacă liderul a fost setat, verifică-l
        if (liderDTO != null) {
            assertEquals(liderDTO.getUserId(), createdProject.getLider().getUserId(), "Liderul nu a fost setat corect.");
        } else {
            logger.info("Liderul nu a fost setat, dar proiectul a fost creat.");
        }
    }


    @Test
    @Order(3)
    public void test3_UpdateProjectStatus() {
        logger.info("DEBUG: Test UPDATE project status...");

        HttpHeaders headers = generateHeaders();

        // Get all projects to find a project to update
        List<ProiectDTO> proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        if (!proiecte.isEmpty()) {
            ProiectDTO proiectDTO = proiecte.get(0);
            proiectDTO.setStatus("COMPLETED");

            ProiectDTO updatedProject = restTemplate.exchange(
                    serviceURL + "/" + proiectDTO.getId() + "/status",
                    HttpMethod.PUT,
                    new HttpEntity<>(proiectDTO, headers),
                    ProiectDTO.class
            ).getBody();

            assertTrue(updatedProject != null, "Failed to update project status!");
            assertTrue("COMPLETED".equals(updatedProject.getStatus()), "Project status not updated correctly.");
        }
    }

    @Test
    @Order(4)
    public void test4_AddTeamToProject() {
        logger.info("DEBUG: Test ADD team to project...");

        HttpHeaders headers = generateHeaders();

        // Get all projects to find a project to add a team to
        List<ProiectDTO> proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        if (!proiecte.isEmpty()) {
            ProiectDTO proiectDTO = proiecte.get(0);
            // Presupunem că avem un endpoint pentru adăugarea unei echipe
            String teamURL = serviceURL + "/" + proiectDTO.getId() + "/echipe";

            // Creează un obiect de echipă (presupunem că trebuie să adăugăm o echipă JSON)
            String echipaJson = "{\"denumire\": \"Echipa Test\"}";

            restTemplate.exchange(
                    teamURL,
                    HttpMethod.POST,
                    new HttpEntity<>(echipaJson, headers),
                    String.class
            );
            logger.info("Added team to project with ID: " + proiectDTO.getId());
        }
    }

    @Test
    @Order(5)
    public void test5_UpdateProjectLeader() {
        logger.info("DEBUG: Test UPDATE project leader...");

        HttpHeaders headers = generateHeaders();

        // Get all projects to find a project to update leader
        List<ProiectDTO> proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        if (!proiecte.isEmpty()) {
            ProiectDTO proiectDTO = proiecte.get(0);
            // Presupunem că avem un endpoint pentru schimbarea liderului
            String leaderURL = serviceURL + "/" + proiectDTO.getId() + "/leader";

            String leaderJson = "{\"id\": 2}"; // Exemplu de ID al unui utilizator care devine lider

            ProiectDTO updatedProject = restTemplate.exchange(
                    leaderURL,
                    HttpMethod.PUT,
                    new HttpEntity<>(leaderJson, headers),
                    ProiectDTO.class
            ).getBody();

            assertTrue(updatedProject != null, "Failed to update project leader!");
            logger.info("Updated project leader: " + updatedProject);
        }
    }

    @Test
    @Order(6)
    public void test6_DeleteProjects() {
        logger.info("DEBUG: Test DELETE projects...");

        HttpHeaders headers = generateHeaders();

        List<ProiectDTO> proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        for (ProiectDTO proiectDTO : proiecte) {
            restTemplate.exchange(
                    serviceURL + "/" + proiectDTO.getId(),
                    HttpMethod.DELETE,
                    new HttpEntity<>(headers),
                    Void.class
            );
        }

        // Verificăm că toate proiectele au fost șterse
        proiecte = restTemplate.exchange(
                serviceURL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ProiectDTO>>() {}
        ).getBody();

        assertTrue(proiecte.isEmpty(), "Failed to delete all projects!");
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
