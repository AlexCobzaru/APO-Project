package org.Proiect.ServiciiRest;

import org.Proiect.DTO.TaskDTO;
import org.Proiect.Servicii.ITaskWorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/servicii/taskuri")
public class TaskRESTService {
    private final ITaskWorkflowService taskWorkflowService;

    public TaskRESTService (ITaskWorkflowService taskWorkflowService) {
        this.taskWorkflowService = taskWorkflowService;
    }

    // Endpoint pentru a obține toate task-urile
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskWorkflowService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }


    // Endpoint pentru a crea un task nou
    @PostMapping(value = "/taskuri", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> creeazaTaskNou(@RequestBody TaskDTO taskDTO) {
        Integer taskId = taskWorkflowService.creeazaTaskNou(taskDTO.getDescriere(), null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"message\":\"Task creat cu ID-ul: " + taskId + "\"}");
    }


    // Endpoint pentru a asigna un task unui membru
    @PostMapping("/{taskId}/assign/{membruId}")
    public ResponseEntity<Void> asignareTaskMembru(
            @PathVariable Integer taskId,
            @PathVariable Integer membruId
    ) {
        taskWorkflowService.asignareTaskMembru(taskId, membruId);
        return ResponseEntity.ok().build();
    }

    // Endpoint pentru a modifica un task
    @PutMapping("/{taskId}")
    public ResponseEntity<Void> modificareTask(
            @PathVariable Integer taskId,
            @RequestBody String descriereNoua
    ) {
        taskWorkflowService.modificareTask(taskId, descriereNoua);
        return ResponseEntity.ok().build();
    }

    // Endpoint pentru a șterge un task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> stergereTask(@PathVariable Integer taskId) {
        taskWorkflowService.stergereTask(taskId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pentru a vizualiza un task
    @GetMapping("/{taskId}")
    public ResponseEntity<String> vizualizareTask(@PathVariable Integer taskId) {
        String taskDetails = taskWorkflowService.vizualizareTask(taskId);
        return ResponseEntity.ok(taskDetails);
    }

    // Endpoint pentru a schimba statusul unui task
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Void> schimbareStatusTask(
            @PathVariable Integer taskId,
            @RequestBody String statusNou
    ) {
        taskWorkflowService.schimbareStatusTask(taskId, statusNou);
        return ResponseEntity.ok().build();
    }

    // Endpoint pentru a gestiona notificările la finalizarea task-urilor
    @PostMapping("/{taskId}/notify-leader")
    public ResponseEntity<Void> trimiteNotificareLider(@PathVariable Integer taskId) {
        taskWorkflowService.trimiteNotificareLider(taskId);
        return ResponseEntity.ok().build();
    }

    // Endpoint pentru valorificarea unui task (exemplu extra)
    @PostMapping("/{taskId}/valorifica/{membruId}")
    public ResponseEntity<Void> valorificaTask(
            @PathVariable Integer taskId,
            @PathVariable Integer membruId
    ) {
        taskWorkflowService.valorificaTask(taskId, membruId);
        return ResponseEntity.ok().build();
    }
}
