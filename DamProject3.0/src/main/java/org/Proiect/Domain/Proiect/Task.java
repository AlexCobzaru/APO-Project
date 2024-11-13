package org.Proiect.Domain.Proiect;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.MembruEchipa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.Proiect.Domain.App.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Date;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int taskUserId;
    private String denumire;
    private String descriere;
    private Date dataIncepere;
    private Date dataFinalizare;
    private Status status;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "id_proiect")
    private Proiect proiect;

    @ManyToOne
    @JoinColumn(name = "id_lider")
    private LiderEchipa lider;

    @ManyToOne
    @JoinColumn(name = "membru_id")
    private MembruEchipa membru;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Notificare> notificari;
}
