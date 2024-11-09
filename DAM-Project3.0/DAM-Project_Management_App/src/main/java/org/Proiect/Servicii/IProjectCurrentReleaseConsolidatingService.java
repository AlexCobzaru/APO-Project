package org.Proiect.Servicii;

import org.scrum.domain.project.Project;
import org.scrum.domain.project.ProjectCurrentReleaseView;

import java.util.Collection;
import java.util.List;

public interface IProjectCurrentReleaseConsolidatingService {

	ProjectCurrentReleaseView getProjectCurrentReleaseViewDataOf(Project project);

	List<ProjectCurrentReleaseView> getProjectCurrentReleaseViewDataOf(Collection<Project> projects);

}