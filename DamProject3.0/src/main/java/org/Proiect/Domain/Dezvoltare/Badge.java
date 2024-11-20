package org.Proiect.Domain.Dezvoltare;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String titlu;
    @ManyToOne
    @JoinColumn(name = "id_curs")
    private Curs curs;

}
