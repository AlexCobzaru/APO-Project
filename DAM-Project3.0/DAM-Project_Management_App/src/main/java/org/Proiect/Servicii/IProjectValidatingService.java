package org.Proiect.Servicii;

import org.scrum.domain.project.Project;

import java.util.Set;

public interface IProjectValidatingService {
	//
	Set<String> validate(Project project);
	//
	void validateWithException(Project project) throws Exception;
	//
	boolean validateProjectAggregate(Project p);
}