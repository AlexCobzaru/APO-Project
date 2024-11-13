package org.Proiect.ServiciiModule.ManagementEchipe;

import org.Proiect.Domain.Angajati.Echipa;

public interface IEchipeValidatingService {
    void validateWithException(Echipa echipa) throws Exception;
}
