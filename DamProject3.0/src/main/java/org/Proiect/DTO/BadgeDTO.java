package org.Proiect.DTO;

import lombok.Data;

@Data
public class BadgeDTO {
    private Integer id;
    private String titlu;
    private String descriere;
    private int dificultate;
    private Integer cursId;
}
