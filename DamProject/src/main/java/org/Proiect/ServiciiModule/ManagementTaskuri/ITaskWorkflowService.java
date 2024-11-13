package org.Proiect.ServiciiModule.ManagementTaskuri;

import java.time.LocalDate;

public interface ITaskWorkflowService {
    Integer creeazaTaskNou(String descriereTask, LocalDate dataLimita);

    void asignareTaskMembru(Integer taskId, Integer membruId);

    void modificareTask(Integer taskId, String descriereNoua);

   // TaskSummaryView obtineSumarTask(Integer taskId);
}
