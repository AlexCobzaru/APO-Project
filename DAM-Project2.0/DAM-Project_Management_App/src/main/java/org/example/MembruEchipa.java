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

public class MembruEchipa extends User {
    private String type;
    @OneToMany(mappedBy = "membru", cascade = CascadeType.ALL)
    private List<Task> tasks;
}


