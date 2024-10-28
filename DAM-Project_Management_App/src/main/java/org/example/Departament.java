package org.example;
import java.util.List;

public class Departament {
    private int idDepartament;
    private String numeDepartament;
    private List<User> angajati;

    // Constructor
    public Departament(int idDepartament, String numeDepartament, List<User> angajati) {
        this.idDepartament = idDepartament;
        this.numeDepartament = numeDepartament;
        this.angajati = angajati;
    }
    public int getIdDepartament() { return idDepartament; }
    public String getNumeDepartament() { return numeDepartament; }
    public List<User> getAngajati() { return angajati; }

}
