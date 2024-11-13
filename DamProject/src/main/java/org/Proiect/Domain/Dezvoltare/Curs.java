package org.Proiect.Domain.Dezvoltare;
import org.Proiect.Domain.Angajati.AppUser;
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
    private String titlu;
    @ManyToMany(mappedBy = "cursuri")
    private List<AppUser> users;

    @OneToMany(mappedBy = "curs", cascade = CascadeType.ALL)
    private List<Badge> badges;
}
