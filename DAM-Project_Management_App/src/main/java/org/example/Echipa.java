package org.example;

import java.util.List;

public class Echipa {
    private int idEchipa;
    private String denumire;
    private List<User> membri;

    // Constructor
    public Echipa(int idEchipa, String denumire, java.util.List<User> membri) {
        this.idEchipa = idEchipa;
        this.denumire = denumire;
        this.membri = membri;
    }

    // Getters și Setters
    public int getIdEchipa() { return idEchipa; }
    public String getDenumire() { return denumire; }
    public java.util.List<User> getMembri() { return membri; }
}
