package org.Proiect.ServiciiModule.ManagementCursuri;

public interface ICursuriWorkflowService {
    Integer planificareCursNou(String numeCurs, String descriereCurs);

    void asignareCursLaMembru(Integer cursId, Integer membruId);

    void evaluareCurs(Integer cursId, Integer utilizatorId, int scor);

    //CursSummaryView obtineSumarCurs(Integer cursId);
}
