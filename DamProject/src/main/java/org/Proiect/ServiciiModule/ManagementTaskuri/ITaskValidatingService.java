package org.Proiect.ServiciiModule.ManagementTaskuri;

import org.Proiect.Domain.Proiect.Task;

public interface ITaskValidatingService {
    void validateWithException(Task task) throws Exception;}

