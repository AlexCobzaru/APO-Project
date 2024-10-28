package org.example;

public class TaskSystem {
    private int userId;
    private int proiectId;
    private RolUtilizator rol;

    public TaskSystem(int userId, int proiectId, RolUtilizator rol) {
        this.userId = userId;
        this.proiectId = proiectId;
        this.rol = rol;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProiectId() {
        return proiectId;
    }

    public void setProiectId(int proiectId) {
        this.proiectId = proiectId;
    }

    public RolUtilizator getRol() {
        return rol;
    }

    public void setRol(RolUtilizator rol) {
        this.rol = rol;
    }
}
