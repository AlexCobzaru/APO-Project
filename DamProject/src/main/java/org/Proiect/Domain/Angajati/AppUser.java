package org.Proiect.Domain.Angajati;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.Proiect.Domain.Proiect.Notificare;
import org.Proiect.Domain.Dezvoltare.Curs;


import java.util.List;
@Entity
@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer userId;
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
