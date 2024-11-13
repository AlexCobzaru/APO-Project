package org.Proiect.ServiciiModule.Implementări;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.ServiciiModule.IProiectDomainService;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.AngajatiRepository;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.EchipaRepository;
import org.Proiect.ServiciiModule.ManagementProiecte.RepositoryProiect;
import org.Proiect.ServiciiModule.ManagementTaskuri.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProiectDomainService  implements IProiectDomainService {
    private final RepositoryProiect proiectRepository;
    private final AngajatiRepository angajatiRepository;
    private final TaskRepository taskRepository;
    private final EchipaRepository echipaRepository;
    @Autowired
    public ProiectDomainService(RepositoryProiect proiectRepository, AngajatiRepository angajatiRepository, TaskRepository taskRepository, EchipaRepository echipaRepository) {
        this.proiectRepository = proiectRepository;
        this.angajatiRepository = angajatiRepository;
        this.taskRepository=taskRepository;
        this.echipaRepository=echipaRepository;
    }

    @Override
    public List<Task> getTasksForProject(int projectId) {
        return taskRepository.findByProiectId(projectId);
    }

    @Override
    public int countTasksForProject(int projectId) {
        return taskRepository.findByProiectId(projectId).size();
    }

    @Override
    public Proiect addTeamToProject(int projectId, Echipa echipa) {
        Proiect proiect = proiectRepository.findById((long) projectId).orElseThrow(() -> new RuntimeException("Proiectul nu a fost găsit"));

        // Adaugă echipa la proiect (presupunând că ai o metodă în entitatea Proiect pentru a adăuga echipe)
        proiect.getEchipe().add(echipa);  // Dacă ai un set de echipe în Proiect

        return proiectRepository.save(proiect);
    }

    @Override
    public double calculateProjectProgress(int projectId) {
        // Obține toate task-urile pentru proiect
        List<Task> tasks = taskRepository.findByProiectId(projectId);

        // Calculează progresul proiectului în funcție de statusul task-urilor
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(task -> task.getStatus() == Status.FINALIZAT).count();

        // Returnează procentajul de completare
        return totalTasks == 0 ? 0 : (double) completedTasks / totalTasks * 100;
    }

    @Override
    public void completeProject(int projectId) {

        // Obține toate task-urile din proiect
        List<Task> tasks = taskRepository.findByProiectId(projectId);

        // Marchează toate task-urile ca fiind COMPLETED
        tasks.forEach(task -> {
            task.setStatus(Status.FINALIZAT);
            taskRepository.save(task);  // Salvează fiecare task cu statusul actualizat
        });
    }

    @Override
    public List<Task> getTasksForMember(int memberId) {
        return taskRepository.findByMembruUserId(memberId);
    }

    @Override
    public void updateTaskStatus(int taskId, Status status) {
        // Găsește task-ul și schimbă statusul
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task-ul nu a fost găsit"));
        task.setStatus(status);

        // Salvează modificarea în task
        taskRepository.save(task);
    }

    @Override
    public void assignTaskToMember(int taskId, int memberId) {

        // Găsește task-ul și alocă-l unui membru
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task-ul nu a fost găsit"));

        // Găsește membrul (ar trebui să folosești AngajatiRepository pentru a găsi membrul)
        MembruEchipa membru = (MembruEchipa) angajatiRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membru nu a fost găsit"));
        task.setMembru(membru);  // Presupunând că Task are o legătură cu MembruEchipa
        taskRepository.save(task);  // Salvează task-ul cu membrul atribuit
    }


    @Override
    public boolean isTaskOverdue(int taskId) {
        // Găsește task-ul
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task-ul nu a fost găsit"));
        return task.getDeadline().isBefore(java.time.LocalDate.now());
    }

    @Override
    public List<Task> getTasksByStatus(int projectId, Status status) {
        // Găsește toate task-urile cu status-ul dat
        return taskRepository.findByProiectId(projectId).stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void addMemberToTeam(int teamId, MembruEchipa membru) {
        // Găsește echipa folosind findById, care returnează un Optional<Echipa>
        Echipa echipa = echipaRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Echipa nu a fost găsită"));
        // Adaugă membru la echipă
        echipa.getMembri().add(membru);
        // Salvează echipa actualizată
        echipaRepository.save(echipa);
    }

    @Override
    public void removeMemberFromTeam(int teamId, int memberId) {
        Echipa echipa = echipaRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Echipa nu a fost găsită"));
        MembruEchipa membru = angajatiRepository.findById(memberId)
                .filter(MembruEchipa.class::isInstance)  // Verificăm că membrul este de tip MembruEchipa
                .map(MembruEchipa.class::cast)           // Convertim la tipul MembruEchipa
                .orElseThrow(() -> new RuntimeException("Membru nu a fost găsit"));
        echipa.getMembri().remove(membru);  // Eliminăm membrul din echipă
        echipaRepository.save(echipa);  // Salvăm modificările
    }

    @Override
    public List<MembruEchipa> getMembersOfTeam(int teamId) {
        Echipa echipa = echipaRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Echipa nu a fost găsită"));
        return echipa.getMembri();
    }

    @Override
    public void setTeamLeader(int teamId, LiderEchipa lider) {
        Echipa echipa = echipaRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Echipa nu a fost găsită"));
        echipa.setLider(lider);
        echipaRepository.save(echipa);
    }

    @Override
    public boolean hasLeader(int teamId) {
        Echipa echipa = echipaRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Echipa nu a fost găsită"));
        return echipa.getLider() != null;
    }
}
