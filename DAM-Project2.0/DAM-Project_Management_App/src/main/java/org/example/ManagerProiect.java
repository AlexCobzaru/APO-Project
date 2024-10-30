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
public class ManagerProiect extends User{
    @OneToMany(mappedBy = "managerProiect", cascade = CascadeType.ALL)
    private List<Departament> departamente;
}
