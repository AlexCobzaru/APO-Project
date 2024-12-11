package org.Proiect.DTO;

import lombok.Data;

@Data
public class TaskDTO {
    private int idTask;
    private String denumire;
    private String descriere;
    private String status;
    private UtilizatorDTO membru;
}