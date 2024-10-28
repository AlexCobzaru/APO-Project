package org.example;
import java.util.List;
import java.util.Date;
public class Task {
    private int idTask;
    private int idProiect;
    private String denumire;
    private String descriere;
    private Date dataIncepere;
    private Date dataFinalizare;
    private Status status;

    public Task(int idTask, int idProiect, java.lang.String denumire, java.lang.String descriere, Date dataIncepere, Date dataFinalizare, Status status) {
        this.idTask = idTask;
        this.idProiect = idProiect;
        this.denumire = denumire;
        this.descriere = descriere;
        this.dataIncepere = dataIncepere;
        this.dataFinalizare = dataFinalizare;
        this.status = status;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public int getIdProiect() {
        return idProiect;
    }

    public void setIdProiect(int idProiect) {
        this.idProiect = idProiect;
    }

    public java.lang.String getDenumire() {
        return denumire;
    }

    public void setDenumire(java.lang.String denumire) {
        this.denumire = denumire;
    }

    public java.lang.String getDescriere() {
        return descriere;
    }

    public void setDescriere(java.lang.String descriere) {
        this.descriere = descriere;
    }

    public Date getDataIncepere() {
        return dataIncepere;
    }

    public void setDataIncepere(Date dataIncepere) {
        this.dataIncepere = dataIncepere;
    }

    public Date getDataFinalizare() {
        return dataFinalizare;
    }

    public void setDataFinalizare(Date dataFinalizare) {
        this.dataFinalizare = dataFinalizare;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
