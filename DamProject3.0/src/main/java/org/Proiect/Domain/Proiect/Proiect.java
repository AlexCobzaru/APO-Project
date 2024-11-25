package org.Proiect.Domain.Proiect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.Proiect.Domain.Angajati.Echipa;

import jakarta.persistence.*;
import lombok.*;
import org.Proiect.Domain.Angajati.Utilizator;
import org.Proiect.Domain.App.StatusProiect;

import java.time.LocalDate;
import java.util.Date;
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
    @NotBlank(message = "Denumirea proiectului este obligatorie.")
    @Size(max = 100, message = "Denumirea proiectului nu poate depăși 100 de caractere.")
 private String denumire;
    @Size(max = 255, message = "Descrierea proiectului nu poate depăși 255 de caractere.")
    private String descriere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProiect status;
    @Column(nullable = false)
    private Date dataIncepere;

    private Date dataFinalizare;


    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilizator lider;

    @OneToMany(mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Raport> rapoarte;
    @OneToMany (mappedBy = "proiect", cascade = CascadeType.ALL)
    private List<Echipa> echipe;
}