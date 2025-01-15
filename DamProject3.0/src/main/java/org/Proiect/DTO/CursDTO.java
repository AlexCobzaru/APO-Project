package org.Proiect.DTO;

import lombok.Data;

@Data
public class CursDTO {
    private Integer id;
    private String titlu;
    private String descriere;
    private int durataOre;
    private UtilizatorDTO adminId;
}
