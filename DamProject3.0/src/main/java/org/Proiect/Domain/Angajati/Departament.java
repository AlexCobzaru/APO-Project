package org.Proiect.Domain.Angajati;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor


public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String numeDepartament;


    @ManyToOne
    @JoinColumn(name = "id_manager_proiect")
    private ManagerProiect managerProiect;;

    @OneToMany(mappedBy = "departament", cascade = CascadeType.ALL)
    private List<AppUser> users;

}
