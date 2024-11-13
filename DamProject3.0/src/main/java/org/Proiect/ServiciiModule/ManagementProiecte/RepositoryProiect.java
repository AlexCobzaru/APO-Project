package org.Proiect.ServiciiModule.ManagementProiecte;
import org.Proiect.Domain.Proiect.Proiect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryProiect extends JpaRepository<Proiect, Long> {

    List<Proiect> findByLiderUserId(Integer liderId);
    @Query("SELECT p FROM Proiect p JOIN p.echipe e WHERE e.idEchipa = :echipaId")
    List<Proiect> findByEchipeIdEchipa(@Param("echipaId") Integer echipaId);
    List<Proiect> findByDenumireContaining(String keyword);

    // Căutare proiecte după numele liderului
    @Query("SELECT p FROM Proiect p WHERE p.lider.nume = :numeLider")
    List<Proiect> findByLiderNume(@Param("numeLider") String numeLider);







}
