package org.app;

import org.example.LiderEchipa;
import org.example.Proiect;
import org.example.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProiectTest {

    private Proiect proiect;

    @Before
    public void setUp() {
        proiect = new Proiect();
    }

    @Test
    public void testSetAndGetLider() {
        LiderEchipa lider = new LiderEchipa();
        lider.setNume("Ion Popescu");

        proiect.setLider(lider);
        assertEquals("Ion Popescu", proiect.getLider().getNume());
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setDenumire("Task Test");

        proiect.getTasks().add(task);
        assertEquals("Task Test", proiect.getTasks().get(0).getDenumire());
        assertEquals(1, proiect.getTasks().size());
    }
}
