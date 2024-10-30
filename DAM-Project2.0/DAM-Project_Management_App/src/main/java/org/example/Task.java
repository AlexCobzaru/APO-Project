package org.example;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Date;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int idTask;
    private String denumire;
    private String descriere;
    private Date dataIncepere;
    private Date dataFinalizare;
    private Status status;

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
