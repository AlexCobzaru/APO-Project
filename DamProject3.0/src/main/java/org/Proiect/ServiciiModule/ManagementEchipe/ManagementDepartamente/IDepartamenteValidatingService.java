package org.Proiect.ServiciiModule.ManagementEchipe.ManagementDepartamente;

import org.Proiect.Domain.Angajati.Departament;

public interface IDepartamenteValidatingService {
    void validateWithException(Departament departament) throws Exception;
}
