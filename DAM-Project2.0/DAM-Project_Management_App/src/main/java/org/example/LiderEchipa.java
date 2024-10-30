package org.example;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LiderEchipa extends User {


    @OneToMany(mappedBy = "lider", cascade = CascadeType.ALL)
    private List<Proiect> proiecte;

    @OneToMany(mappedBy = "lider", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToMany(mappedBy = "lider", cascade = CascadeType.ALL)
    private List<Echipa> echipe;
}

