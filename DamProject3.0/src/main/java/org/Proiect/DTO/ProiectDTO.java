package org.Proiect.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProiectDTO {
    private int id;

    @JsonProperty("denumire_proiect")
    private String denumire;

    private String descriere;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataIncepere;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataFinalizare;

    @JsonProperty("lider_id")
    private Integer liderId;
}
