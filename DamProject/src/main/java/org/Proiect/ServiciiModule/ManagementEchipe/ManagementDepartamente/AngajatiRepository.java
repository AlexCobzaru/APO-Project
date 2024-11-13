package org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente;

import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.ManagerProiect;
import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.Angajati.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AngajatiRepository extends JpaRepository<AppUser, Integer> {

    // Căutare angajați după departament
    List<AppUser> findByDepartamentId(Integer departamentId);

    // Căutare lideri de echipă
    @Query("SELECT l FROM LiderEchipa l")
    List<LiderEchipa> findAllLideri();

    // Căutare membri de echipă
    @Query("SELECT m FROM MembruEchipa m")
    List<MembruEchipa> findAllMembri();

    // Adăugăm o metodă pentru a găsi un membru echipa specific după ID
    @Query("SELECT m FROM MembruEchipa m WHERE m.id = :id")
    Optional<MembruEchipa> findMembruEchipaById(@Param("id") Long id);

    // Căutare manageri de proiect
    @Query("SELECT mp FROM ManagerProiect mp")
    List<ManagerProiect> findAllManageriProiect();
}
