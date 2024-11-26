package org.Proiect.Servicii;

import org.Proiect.Domain.Dezvoltare.Badge;
import org.Proiect.Domain.Dezvoltare.Curs;
import org.Proiect.Domain.Dezvoltare.UtilizatorCurs;

import java.util.List;
import java.util.Optional;

public interface IDezvoltareWorkflowService {
    // Crearea unui curs (doar pentru Admin)
    Curs creeazaCurs(String titlu, Integer adminId);

    // Asignarea utilizatorilor la curs (Admin)
    void asigneazaUtilizatoriLaCurs(Integer cursId, List<Integer> utilizatorIds);

    // Editarea unui curs (Admin sau ManagerProiect)
    Curs editeazaCurs(Integer cursId, String titlu);

    // Vizualizarea unui curs (Admin, ManagerProiect, sau utilizator înscris)
    Optional<Curs> vizualizeazaCurs(Integer cursId, Integer utilizatorId);

    // Urmărirea progresului tuturor angajaților într-un curs (ManagerProiect)
    List<UtilizatorCurs> urmaresteProgresAngajati(Integer cursId, Integer utilizatorId);

    // Urmărirea progresului propriu într-un curs (Utilizator)
    UtilizatorCurs urmaresteProgresPropriu(Integer cursId, Integer utilizatorId);

    // Sistemul generează un badge la finalizarea unui curs
    Badge genereazaBadgePentruCurs(Integer cursId, Integer utilizatorId);

}
