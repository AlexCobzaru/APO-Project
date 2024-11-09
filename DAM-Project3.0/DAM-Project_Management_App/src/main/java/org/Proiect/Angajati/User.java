package org.Proiect.Angajati;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.Proiect.Dezvoltare.Curs;
import Departament;
import Notificare;

import java.util.List;
@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int userId;
    private String nume;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notificare> notificari;

    @ManyToMany
    @JoinTable(name = "UserCurs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "curs_id"))
    private List<Curs> cursuri;
    @ManyToOne
    @JoinColumn(name = "id_departament")
    private Departament departament;

}
