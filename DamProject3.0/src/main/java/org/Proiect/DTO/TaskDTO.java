package org.Proiect.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Deadline nu poate fi null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    public TaskDTO(int idTask, String denumire, String descriere, Status status, LocalDate deadline, UtilizatorDTO membru) {
        this.idTask = idTask;
        this.denumire = denumire;
        this.descriere = descriere;
        this.status = status;
        this.deadline = deadline;
        this.membru = membru;
    }


}
