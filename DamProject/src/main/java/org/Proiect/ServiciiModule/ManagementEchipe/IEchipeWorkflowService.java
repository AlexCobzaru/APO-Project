package org.Proiect.ServiciiModule.ManagementEchipe;

public interface IEchipeWorkflowService {
    Integer creeazaEchipaNoua(String numeEchipa);

    void adaugaMembruEchipa(Integer echipaId, Integer membruId);

    void arhivareEchipa(Integer echipaId);

   // EchipaSummaryView obtineSumarEchipa(Integer echipaId);
}
