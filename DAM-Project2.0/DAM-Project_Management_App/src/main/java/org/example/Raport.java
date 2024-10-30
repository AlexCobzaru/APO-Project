package org.example;
import java.util.List;
import java.util.Date;import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Raport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int idRaport;
    private TipRaport tip;
    private String denumire;
    private Date dataGenerare;
    @ManyToOne
    @JoinColumn(name = "id_proiect")
    private Proiect proiect;

}

