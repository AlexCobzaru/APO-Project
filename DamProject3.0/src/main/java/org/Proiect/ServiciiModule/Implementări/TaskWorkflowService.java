package org.Proiect.ServiciiModule.Implementări;

import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente.AngajatiRepository;
import org.Proiect.ServiciiModule.ManagementTaskuri.ITaskWorkflowService;
import org.Proiect.ServiciiModule.ManagementTaskuri.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public class TaskWorkflowService implements ITaskWorkflowService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AngajatiRepository angajatiRepository;

    @Override
    public Integer creeazaTaskNou(String descriereTask, LocalDate dataLimita) {
        Task task = new Task();
        task.setDescriere(descriereTask);
        task.setDeadline(dataLimita);
        task.setStatus(Status.NOU);
        Task savedTask = taskRepository.save(task);
        return savedTask.getTaskUserId();
    }

    @Override
    public void asignareTaskMembru(Integer taskId, Integer membruId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Task-ul cu ID-ul " + taskId + " nu a fost găsit.");
        }

        // Adăugăm o metodă specifică în AngajatiRepository pentru a găsi MembruEchipa
        Optional<MembruEchipa> optionalMembru = angajatiRepository.findMembruEchipaById(membruId.longValue());
        if (optionalMembru.isEmpty()) {
            throw new RuntimeException("Membrul cu ID-ul " + membruId + " nu a fost găsit.");
        }

        Task task = optionalTask.get();
        MembruEchipa membru = optionalMembru.get();
        task.setMembru(membru);
        taskRepository.save(task);
    }

    @Override
    public void modificareTask(Integer taskId, String descriereNoua) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new RuntimeException("Task-ul cu ID-ul " + taskId + " nu a fost găsit.");
        }

        // Modificăm descrierea task-ului și salvăm modificările
        Task task = optionalTask.get();
        task.setDescriere(descriereNoua);
        taskRepository.save(task);
    }
}
