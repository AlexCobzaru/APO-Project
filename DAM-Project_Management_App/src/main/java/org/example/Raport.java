package org.example;
import java.util.List;
import java.util.Date;
public class Raport {
    private int idRaport;
    private TipRaport tip;
    private String denumire;
    private Date dataGenerare;

    // Constructor
    public Raport(int idRaport, TipRaport tip, String denumire, Date dataGenerare) {
        this.idRaport = idRaport;
        this.tip = tip;
        this.denumire = denumire;
        this.dataGenerare = dataGenerare;
    }

    // Getters și Setters
    public int getIdRaport() { return idRaport; }
    public TipRaport getTip() { return tip; }
    public String getDenumire() { return denumire; }
    public Date getDataGenerare() { return dataGenerare; }
}

