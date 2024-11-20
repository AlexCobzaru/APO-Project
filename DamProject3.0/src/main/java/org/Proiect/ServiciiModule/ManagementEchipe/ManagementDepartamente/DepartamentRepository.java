package org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente;

import org.Proiect.Domain.Angajati.Departament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Integer> {
    Optional<Departament> findByNumeDepartament(String numeDepartament);

    List<Departament> findByManagerProiect_Id(Integer userId);
    boolean existsByNumeDepartament(String numeDepartament);

}
