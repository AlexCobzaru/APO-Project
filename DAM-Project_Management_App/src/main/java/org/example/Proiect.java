package org.example;
import java.util.List;
import java.util.Date;

public class Proiect {
    private int idProiect;
    private int ownerId;
    private String denumire;
    private Date dataIncepere;
    private Date dataFinalizare;
    private Status status;

    public Proiect(int idProiect, int ownerId, String denumire, Date dataIncepere, Date dataFinalizare, Status status) {
        this.idProiect = idProiect;
        this.ownerId = ownerId;
        this.denumire = denumire;
        this.dataIncepere = dataIncepere;
        this.dataFinalizare = dataFinalizare;
        this.status = status;
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