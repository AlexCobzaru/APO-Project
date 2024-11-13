package org.Proiect.ServiciiModule.ManagementProiecte;

import java.util.Date;

public interface IProiecteWorkflowService {
    Integer planificareProiectNou(String numeProiect, Date dataIncepere);

    void asignareManagerProiect(Integer proiectId, Integer managerId);

    void arhivareProiect(Integer proiectId);

    //ProiectSummaryView obtineSumarProiect(Integer proiectId);
}
