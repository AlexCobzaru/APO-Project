package org.Proiect.Domain.Proiect;
import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.LiderEchipa;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Proiect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
 private String denumire;
    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "id_lider")
    private LiderEchipa lider;

    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Raport> rapoarte;
    @ManyToMany
    @JoinTable(name = "EchipaProiect",
            joinColumns = @JoinColumn(name = "proiect_id"),
            inverseJoinColumns = @JoinColumn(name = "echipa_id"))
    private List<Echipa> echipe;
}