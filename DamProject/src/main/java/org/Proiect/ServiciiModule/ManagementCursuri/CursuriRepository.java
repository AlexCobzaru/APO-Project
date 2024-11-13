package org.Proiect.ServiciiModule.ManagementCursuri;

import org.Proiect.Domain.Dezvoltare.Curs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursuriRepository extends JpaRepository<Curs, Long> {

    // Căutare cursuri după utilizator
    List<Curs> findByUsersUserId(Integer userId);

    // Căutare cursuri după titlu
    List<Curs> findByTitluContaining(String titlu);


}