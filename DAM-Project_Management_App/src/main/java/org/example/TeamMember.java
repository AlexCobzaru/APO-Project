package org.example;
import java.util.List;
import java.util.Date;

public class TeamMember  extends User {
    private String type;

    public TeamMember(int userId, String nume, String email, String type) {
        super(userId, nume, email);
        this.type = type;
    }

    public String getType() { return type; }
}


