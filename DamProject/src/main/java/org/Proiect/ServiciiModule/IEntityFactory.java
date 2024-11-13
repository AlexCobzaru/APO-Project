package org.Proiect.ServiciiModule;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;

import java.util.Date;
import java.util.List;

public interface IEntityFactory {
    // Metode pentru entitățile Proiect
    Proiect buildSimpleProject(String projectName);
    Proiect buildProjectWithTasks(String projectName, List<Task> tasks);
    Proiect buildProjectWithTeams(String projectName, List<Echipa> echipe);

    // Metode pentru entitățile Task
    Task buildTask(String denumire, String descriere, Date startDate, Date endDate, Status status);
    Task buildTaskForMember(String denumire, MembruEchipa membru, Date startDate, Date endDate, Status status);

    // Metode pentru entitățile Echipa
    Echipa buildTeam(String denumire, LiderEchipa lider);
    Echipa buildTeamWithMembers(String denumire, LiderEchipa lider, List<MembruEchipa> membri);

    // Transformare din DTO în entități
    Proiect toProjectEntity(Proiect projectDTO);
    Task toTaskEntity(Task taskDTO);
    Echipa toTeamEntity(Echipa teamDTO);

    // Metodă de inițializare
    void initEntities();
}

