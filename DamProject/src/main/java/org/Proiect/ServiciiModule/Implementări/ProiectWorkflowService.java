package org.Proiect.ServiciiModule.Implementări;

import org.Proiect.ServiciiModule.ManagementProiecte.IProiecteWorkflowService;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ProiectWorkflowService implements IProiecteWorkflowService {
    @Override
    public Integer planificareProiectNou(String numeProiect, Date dataIncepere) {
        return 0;
    }

    @Override
    public void asignareManagerProiect(Integer proiectId, Integer managerId) {

    }

    @Override
    public void arhivareProiect(Integer proiectId) {

    }
}
