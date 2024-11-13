package org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente;

import org.Proiect.Domain.Angajati.Echipa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EchipaRepository extends JpaRepository<Echipa,Integer> {
    // Găsește echipa după ID
    Optional<Echipa> findById(int id);

    // Găsește echipa după denumire
    List<Echipa> findByDenumireContaining(String denumire);

    @Query("SELECT e FROM Echipa e WHERE e.lider.userId = :userId")
    List<Echipa> findByLiderUserId(@Param("userId") int userId);


    // Găsește toate echipele
    List<Echipa> findAll();

    @Query("SELECT e FROM Echipa e JOIN e.membri m GROUP BY e.id HAVING COUNT(m) = :numarMembri")
    List<Echipa> findByNumarMembri(@Param("numarMembri") int numarMembri);

}
