package org.Proiect.Domain.Proiect;
import org.Proiect.Domain.Angajati.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor

public class Notificare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;
}