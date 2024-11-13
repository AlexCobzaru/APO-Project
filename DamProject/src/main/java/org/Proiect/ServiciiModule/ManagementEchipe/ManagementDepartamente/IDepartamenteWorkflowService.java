package org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente;

public interface IDepartamenteWorkflowService {
    Integer creeazaDepartamentNou(String numeDepartament);

    void adaugaAngajatDepartament(Integer departamentId, Integer angajatId);

   // DepartamentSummaryView obtineSumarDepartament(Integer departamentId);
}
