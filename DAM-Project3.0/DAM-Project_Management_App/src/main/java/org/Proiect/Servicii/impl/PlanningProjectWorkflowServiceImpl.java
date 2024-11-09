package org.Proiect.Servicii.impl;

import Servicii.*;
import org.Proiect.Servicii.*;
import org.scrum.domain.project.Project;
import org.scrum.domain.project.ProjectCurrentReleaseView;
import org.scrum.domain.project.Release;
import org.scrum.domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlanningProjectWorkflowServiceImpl
		implements IPlanningProjectWorkflowService {
	// Support Services
	@Autowired
	private IProjectEntityRepository entityRepository;
	
	@Autowired //@Qualifier("ProjectEntityFactoryBase")
	private IProjectEntityFactory entityFactory;
	
	@Autowired
	private IProjectCurrentReleaseConsolidatingService consolidatingProjectService;
	
	@Autowired
	private IProjectValidatingService validatorService;

	@Autowired
	private IPlanningProjectAuditingService auditingService;
	
	// (1) Create new project with default template: projectName, startDate
	@Override
	public Integer planNewProject(String projectName, Date startDate) {
		Project project = entityFactory.buildProjectWith2R(projectName, startDate, 3);
		
		// Invoke Validation Service
        try {
            validatorService.validateWithException(project);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        validatorService.validateProjectAggregate(project);
		//
		project = entityRepository.add(project);
		return project.getProjectNo();
	}
	
	// (2) 
	@Override
	public Integer addFeatureToProject(Integer projectId, String featureName, String featureDescription) {
		Project project = entityRepository.getById(projectId);
		project.getCurrentRelease().addFeature(featureName);
		
		// validation logic to be invoked
		validatorService.validateProjectAggregate(project);
		project = entityRepository.add(project);

		// audit logic to be invoked
		auditingService.auditProjectFeature(project.getProjectNo(), featureName);

		return project.getCurrentRelease().getReleaseId();
	}
	
	// (3) Create new project with default template: projectName, startDate
	@Override
	public Integer planCurrentRelease(Integer projectId, Date publishDate) {
		Project project = entityRepository.getById(projectId);
		Release currentRelease = project.getCurrentRelease();
		
		currentRelease.setPublishDate(publishDate);
		project = entityRepository.add(project);
		//
		return project.getCurrentRelease().getReleaseId();
	}
	
	// (4) Get project summary data: ProjectCurrentReleaseView
	@Override
	public ProjectCurrentReleaseView getProjectSummaryData(Integer projectId) {
		Project project = entityRepository.getById(projectId);
		return consolidatingProjectService.getProjectCurrentReleaseViewDataOf(project);
	}

	/*
	 * Naming template: Action.ing Entity-name Service-type
	 */
	@Service
	public static class ProjectSummarizingServiceImpl implements IProjectSummarizingService {
		@Override
		public Project countingReleases(Project project) {
			Integer releaseCount = (project.getReleases() == null) ? 0 : project.getReleases().size();
			project.setReleaseCount(releaseCount);
			return project;
		}
		@Override
		public Project countingFeatures(Project project) {

			Integer fc = 0;

			//
			for(Release r: project.getReleases())
				for(Feature f: r.getFeatures())
					fc++;
			project.setFeatureCount(fc);

			//

			Long featureCount = (project.getReleases() == null) ? 0 :
				project.getReleases().stream()
					.flatMap(r -> r.getFeatures().stream()).count();
			project.setFeatureCount(featureCount.intValue());
			//

			return project;
		}
	}
}
