package org.Proiect.ServiciiModule.ManagementProiecte;

import org.Proiect.Domain.Proiect.Proiect;

public interface IProiecteValidatingService {
    void validateWithException(Proiect proiect) throws Exception;
}
