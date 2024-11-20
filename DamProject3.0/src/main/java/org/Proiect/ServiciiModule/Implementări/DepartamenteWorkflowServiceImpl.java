package org.Proiect.ServiciiModule.Implementări;

import org.Proiect.Domain.Angajati.AppUser;
import org.Proiect.Domain.Angajati.Departament;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.AngajatiRepository;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.DepartamentRepository;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.IDepartamenteWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamenteWorkflowServiceImpl implements IDepartamenteWorkflowService {

    @Autowired
    private DepartamentRepository departamentRepository;

    @Autowired
    private AngajatiRepository angajatiRepository;

    @Override
    public Integer creeazaDepartamentNou(String numeDepartament) {

        if (departamentRepository.existsByNumeDepartament(numeDepartament)) {
            throw new RuntimeException("Departamentul cu numele " + numeDepartament + " există deja.");
        }

        Departament departament = new Departament();
        departament.setNumeDepartament(numeDepartament);

        Departament savedDepartament = departamentRepository.save(departament);
        return savedDepartament.getId();
    }

    @Override
    public void adaugaAngajatDepartament(Integer departamentId, Integer angajatId) {
        Departament departament = departamentRepository.findById(departamentId)
                .orElseThrow(() -> new RuntimeException("Departamentul cu ID-ul " + departamentId + " nu a fost găsit."));

        // Găsim angajatul
        AppUser angajat = angajatiRepository.findById(angajatId)
                .orElseThrow(() -> new RuntimeException("Angajatul cu ID-ul " + angajatId + " nu a fost găsit."));

        // Asociem angajatul cu departamentul
        angajat.setDepartament(departament);

        // Salvăm modificările
        angajatiRepository.save(angajat);
    }

    }

