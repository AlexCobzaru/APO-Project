package org.Proiect.Servicii.Implementari;

import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.App.StatusProiect;
import org.Proiect.Domain.App.TipUtilizator;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Raport;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.Domain.Repository.AppUserRepository;
import org.Proiect.Domain.Repository.EchipaRepository;
import org.Proiect.Servicii.IProiecteWorkflowService;

import org.Proiect.Domain.Repository.RepositoryProiect;
import org.Proiect.Domain.Repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProiectWorkflowService implements IProiecteWorkflowService {

    private final RepositoryProiect proiectRepository;
    private final TaskRepository taskRepository;
    private  final EchipaRepository echipaRepository;
    private  final AppUserRepository appUserRepository;

    public ProiectWorkflowService(RepositoryProiect proiectRepository, TaskRepository taskRepository, EchipaRepository echipaRepository, AppUserRepository appUserRepository) {
        this.proiectRepository = proiectRepository;
        this.taskRepository = taskRepository;
        this.echipaRepository=echipaRepository;
        this.appUserRepository=appUserRepository;
    }
    @Override
    @Transactional
    public Proiect creareProiect(String denumire, String descriere, Utilizator lider, List<Echipa> echipe) {
        if (!lider.getTipUtilizator().equals(TipUtilizator.LIDER)) {
            throw new IllegalArgumentException("Liderul trebuie să fie de tip LIDER.");
        }
        Proiect proiect = new Proiect();
        proiect.setDenumire(denumire);
        proiect.setDescriere(descriere);
        proiect.setLider(lider);
        proiect.setStatus(StatusProiect.IN_PROGRESS);
        proiect.setDataIncepere(new Date());

        // Verificare și atașare echipe
        List<Echipa> attachedEchipe = echipe.stream()
                .map(echipa -> {
                    if (echipa.getIdEchipa() != 0) {
                        return echipaRepository.findById(echipa.getIdEchipa())
                                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există: " + echipa.getIdEchipa()));
                    }
                    return echipaRepository.save(echipa); // Salvează echipe noi
                })
                .toList();

        proiect.setEchipe(attachedEchipe);
        attachedEchipe.forEach(e -> e.setProiect(proiect)); // Setare relație inversă

        return proiectRepository.save(proiect);
    }

    @Override
    public Proiect modificaTeamLeader(Integer proiectId, Utilizator nouLider) {
        if (!nouLider.getTipUtilizator().equals(TipUtilizator.LIDER)) {
            throw new IllegalArgumentException("Liderul trebuie să fie de tip LIDER.");
        }
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit!"));

        proiect.setLider(nouLider);
        return proiectRepository.save(proiect);
    }

    @Override
    @Transactional
    public Proiect adaugaEchipaLaProiect(Integer proiectId, Echipa echipa) {
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new IllegalArgumentException("Proiectul nu există: " + proiectId));

        // Atașare sau salvare echipă
        Echipa echipaManaged = echipa.getIdEchipa() != 0
                ? echipaRepository.findById(echipa.getIdEchipa())
                .orElseThrow(() -> new IllegalArgumentException("Echipa nu există: " + echipa.getIdEchipa()))
                : echipaRepository.save(echipa);

        proiect.getEchipe().add(echipaManaged);
        echipaManaged.setProiect(proiect); // Setare relație inversă

        return proiectRepository.save(proiect);
    }
    @Override
    @Transactional
    public Task atribuieTaskMembru(Integer taskId, Utilizator membru) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task-ul nu există: " + taskId));

        Utilizator membruManaged = appUserRepository.findById(membru.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilizatorul nu există: " + membru.getUserId()));

        task.setMembru(membruManaged);
        return taskRepository.save(task);
    }

    @Override
    public Proiect actualizareStatusProiect(Integer proiectId, StatusProiect status) {
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit!"));
        proiect.setStatus(status);
        return proiectRepository.save(proiect);
    }

    @Override
    @Transactional
    public Proiect finalizareProiect(Integer proiectId) {
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new IllegalArgumentException("Proiectul nu există: " + proiectId));

        proiect.setStatus(StatusProiect.COMPLETED);
        proiect.setDataFinalizare(new Date());

        return proiectRepository.save(proiect);
    }

    @Override
    public List<Task> monitorizareTaskuri(Integer proiectId) {
        return taskRepository.findByProiectId(proiectId);
    }

    @Override
    public Raport genereazaRaport(Integer proiectId) {
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit!"));
        Raport raport = new Raport();
        raport.setProiect(proiect);
        raport.setDataGenerare(java.sql.Date.valueOf(java.time.LocalDate.now()));
        raport.setDenumire("Raport pentru proiectul: " + proiect.getDenumire());
        List<Task> taskuri = taskRepository.findByProiectId(proiectId);
        return raport;
    }

    @Override
    public boolean stergeProiect(Integer proiectId) {
        if (!proiectRepository.existsById(proiectId)) {
            throw new RuntimeException("Proiectul nu a fost găsit!");
        }
        proiectRepository.deleteById(proiectId);
        return true;
    }

    @Override
    public Proiect arhiveazaProiect(Integer proiectId) {
        Proiect proiect = proiectRepository.findById(proiectId)
                .orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit!"));
        proiect.setStatus(StatusProiect.ON_HOLD);
        return proiectRepository.save(proiect);
    }

    @Override
    public Proiect getProiectById(Integer proiectId) {
        return proiectRepository.findById(proiectId)
                .orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit!"));
    }

    @Override
    public List<Proiect> getToateProiectele() {
        return proiectRepository.findAll();
    }
}
