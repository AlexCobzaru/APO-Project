package org.Proiect.Servicii;

import org.scrum.domain.project.Project;

public interface IProjectSummarizingService {

	Project countingReleases(Project project);

	Project countingFeatures(Project project);
	
	// List<Project> countingReleases(List<Project> projects);
}