package org.Proiect.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UtilizatorCursDTO {
    private Long id;
    private Integer utilizatorId;
    private Integer cursId;
    private boolean completat;
    private int progres;
    private Date dataInrolare;
    private Date dataFinalizare;
}
