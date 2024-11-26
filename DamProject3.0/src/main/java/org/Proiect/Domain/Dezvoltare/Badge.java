package org.Proiect.Domain.Dezvoltare;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "Titlul badge-ului este obligatoriu.")
    private String titlu;

    @ManyToOne
    @JoinColumn(name = "id_curs", nullable = false)
    private Curs curs;

    // Opțional: descriere pentru badge
    private String descriere;

    // Exemplu de câmp suplimentar pentru dificultatea badge-ului
    private int dificultate; // 1 = ușor, 5 = foarte greu

}
