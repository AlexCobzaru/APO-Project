package org.Proiect.Angajati;

import jakarta.persistence.*;
import lombok.*;
import org.Proiect.App.Proiect;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor

public class Echipa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int idEchipa;
    private String denumire;

    @OneToMany(mappedBy = "echipa", cascade = CascadeType.ALL)
    private List<MembruEchipa> membri;

    @ManyToMany(mappedBy = "echipe")
    private List<Proiect> proiecte;

    @ManyToOne
    @JoinColumn(name = "id_lider")
    private LiderEchipa lider;


}
