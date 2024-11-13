package org.Proiect.Domain.Proiect;
import java.util.Date;import jakarta.persistence.*;
import lombok.*;
import org.Proiect.Domain.App.TipRaport;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

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

