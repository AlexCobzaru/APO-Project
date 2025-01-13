package org.Proiect.DTO;

import lombok.Data;
import org.Proiect.Domain.App.Status;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private int idTask;
    private String denumire;
    private String descriere;
    private Status status;
    private UtilizatorDTO membru;
    private LocalDate deadline;
}