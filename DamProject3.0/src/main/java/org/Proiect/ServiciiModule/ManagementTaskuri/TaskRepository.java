package org.Proiect.ServiciiModule.ManagementTaskuri;

import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProiectId(Integer proiectId);
    List<Task> findByTaskUserId(Integer taskId);
    List<Task> findByMembruUserId(Integer membruId);
    List<Task> findByStatus(Status status);
    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.proiect.id = :proiectId")
    List<Task> findByStatusAndProiectId(@Param("status") Status status, @Param("proiectId") Integer proiectId);

}
