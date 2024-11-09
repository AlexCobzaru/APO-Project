package org.Proiect.Dezvoltare;
import org.Proiect.Angajati.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Curs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToMany(mappedBy = "cursuri")
    private List<User> users;

    @OneToMany(mappedBy = "curs", cascade = CascadeType.ALL)
    private List<Badge> badges;
}
