package org.Proiect.ServiciiModule;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;

import java.util.List;

public interface IProiectDomainService {
    // Returnează toate taskurile dintr-un proiect
    List<Task> getTasksForProject(int projectId);

    // Returnează numărul total de taskuri dintr-un proiect
    int countTasksForProject(int projectId);

    // Adaugă o echipă la proiect
    Proiect addTeamToProject(int projectId, Echipa echipa);

    // Calculează progresul proiectului în funcție de statusul taskurilor
    double calculateProjectProgress(int projectId);

    // Marchează proiectul ca finalizat
    void completeProject(int projectId);
    // Returnează toate taskurile pentru un membru de echipă
    List<Task> getTasksForMember(int memberId);

    // Schimbă statusul unui task
    void updateTaskStatus(int taskId, Status status);

    // Atribuie un task unui membru al echipei
    void assignTaskToMember(int taskId, int memberId);

    // Verifică dacă un task este depășit
    boolean isTaskOverdue(int taskId);

    // Returnează taskurile în funcție de status
    List<Task> getTasksByStatus(int projectId, Status status);
    // Adaugă un membru la echipă
    void addMemberToTeam(int teamId, MembruEchipa membru);

    // Elimină un membru din echipă
    void removeMemberFromTeam(int teamId, int memberId);

    // Returnează lista membrilor echipei
    List<MembruEchipa> getMembersOfTeam(int teamId);

    // Setează liderul echipei
    void setTeamLeader(int teamId, LiderEchipa lider);

    // Verifică dacă echipa are un lider
    boolean hasLeader(int teamId);

}
