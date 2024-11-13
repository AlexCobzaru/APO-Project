package org.Proiect.Domain.Angajati;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.Proiect.Domain.Proiect.Task;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class MembruEchipa extends AppUser {
    private String type;
    @OneToMany(mappedBy = "membru", cascade = CascadeType.ALL)
    private List<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "echipa_id")
    private Echipa echipa;
}


