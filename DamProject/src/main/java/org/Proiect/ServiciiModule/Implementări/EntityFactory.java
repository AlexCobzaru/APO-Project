package org.Proiect.ServiciiModule.Implementări;

import org.Proiect.Domain.Angajati.Echipa;
import org.Proiect.Domain.Angajati.LiderEchipa;
import org.Proiect.Domain.Angajati.MembruEchipa;
import org.Proiect.Domain.App.Status;
import org.Proiect.Domain.Proiect.Proiect;
import org.Proiect.Domain.Proiect.Task;
import org.Proiect.ServiciiModule.IEntityFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityFactory implements IEntityFactory {
    @Override
    public Proiect buildSimpleProject(String projectName) {
        Proiect proiect = new Proiect();
        proiect.setDenumire(projectName);
        proiect.setTasks(new ArrayList<>());
        proiect.setEchipe(new ArrayList<>());
        proiect.setRapoarte(new ArrayList<>());
        return proiect;
    }

    @Override
    public Proiect buildProjectWithTasks(String projectName, List<Task> tasks) {
        Proiect proiect = buildSimpleProject(projectName);
        proiect.setTasks(tasks);
        return proiect;
    }

    @Override
    public Proiect buildProjectWithTeams(String projectName, List<Echipa> echipe) {
        Proiect proiect = buildSimpleProject(projectName);
        proiect.setEchipe(echipe);
        return proiect;
    }

    @Override
    public Task buildTask(String denumire, String descriere, Date startDate, Date endDate, Status status) {
        Task task = new Task();
        task.setDenumire(denumire);
        task.setDescriere(descriere);
        task.setDataIncepere(startDate);
        task.setDataFinalizare(endDate);
        task.setStatus(status);
        return task;
    }

    @Override
    public Task buildTaskForMember(String denumire, MembruEchipa membru, Date startDate, Date endDate, Status status) {
        Task task = buildTask(denumire, "Descriere", startDate, endDate, status);
        task.setMembru(membru);
        return task;
    }

    @Override
    public Echipa buildTeam(String denumire, LiderEchipa lider) {
        Echipa echipa = new Echipa();
        echipa.setDenumire(denumire);
        echipa.setLider(lider);
        echipa.setMembri(new ArrayList<>());
        return echipa;
    }

    @Override
    public Echipa buildTeamWithMembers(String denumire, LiderEchipa lider, List<MembruEchipa> membri) {
        Echipa echipa = buildTeam(denumire, lider);
        echipa.setMembri(membri);
        return echipa;
    }

    @Override
    public Proiect toProjectEntity(Proiect projectDTO) {
        Proiect proiect = new Proiect();
        proiect.setDenumire(projectDTO.getDenumire());
        proiect.setTasks(projectDTO.getTasks() != null ? new ArrayList<>(projectDTO.getTasks()) : new ArrayList<>());
        proiect.setEchipe(projectDTO.getEchipe() != null ? new ArrayList<>(projectDTO.getEchipe()) : new ArrayList<>());
        return proiect;
    }

    @Override
    public Task toTaskEntity(Task taskDTO) {
        Task task = new Task();
        task.setDenumire(taskDTO.getDenumire());
        task.setDescriere(taskDTO.getDescriere());
        task.setDataIncepere(taskDTO.getDataIncepere());
        task.setDataFinalizare(taskDTO.getDataFinalizare());
        task.setStatus(taskDTO.getStatus());
        task.setMembru(taskDTO.getMembru());
        return task;
    }

    @Override
    public Echipa toTeamEntity(Echipa teamDTO) {
        Echipa echipa = new Echipa();
        echipa.setDenumire(teamDTO.getDenumire());
        echipa.setLider(teamDTO.getLider());
        echipa.setMembri(teamDTO.getMembri() != null ? new ArrayList<>(teamDTO.getMembri()) : new ArrayList<>());
        return echipa;
    }

    @Override
    public void initEntities() {

        // Inițializare a unor valori implicite, dacă este necesar
        System.out.println("Inițializare entități în EntityFactory");
    }
}

