package org.Proiect.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.Proiect.Domain.App.StatusProiect;

import java.util.Date;

@Data
public class ProiectDTO {
    private int id;
    private String denumire;
    private String descriere;
    private String status;
    private Date dataIncepere;
    private Date dataFinalizare;

    private UtilizatorDTO lider;
}
