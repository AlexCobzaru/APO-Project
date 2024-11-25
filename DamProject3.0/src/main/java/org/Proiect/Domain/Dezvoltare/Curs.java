package org.Proiect.Domain.Dezvoltare;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.Proiect.Domain.Angajati.Utilizator;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Curs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank(message = "Titlul cursului este obligatoriu.")
    @Size(max = 100, message = "Titlul cursului nu poate depăși 100 de caractere.")
    private String titlu;
    @ManyToMany(mappedBy = "cursuri")
    private List<Utilizator> users;

    @OneToMany(mappedBy = "curs", cascade = CascadeType.ALL)
    private List<Badge> badges;
}
