package org.Proiect.Domain.Dezvoltare;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Proiect.Domain.Angajati.Utilizator;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class UtilizatorCurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_utilizator", nullable = false)
    private Utilizator utilizator;

    @ManyToOne
    @JoinColumn(name = "id_curs", nullable = false)
    private Curs curs;

    // Informații suplimentare
    private boolean completat;
    private int progres; // Exprimat în procente
    private Date dataInrolare;
    private Date dataFinalizare;

}
