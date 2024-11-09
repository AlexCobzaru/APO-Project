package org.Proiect.Servicii.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.scrum.domain.project.Project;
import org.scrum.domain.project.ProjectCurrentReleaseView;
import org.scrum.domain.services.IProjectCurrentReleaseConsolidatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProjectCurrentReleaseConsolidatingServiceImpl implements IProjectCurrentReleaseConsolidatingService {
	@Override
	public ProjectCurrentReleaseView getProjectCurrentReleaseViewDataOf(Project project) {
		return new ProjectCurrentReleaseView(
				project.getProjectNo(), project.getName(), 
				project.getCurrentRelease().getReleaseId(), 
				project.getCurrentRelease().getCodeName(), 
				project.getCurrentRelease().getFeatures().size());
	}
	
	@Override
	public List<ProjectCurrentReleaseView> getProjectCurrentReleaseViewDataOf(Collection<Project> projects) {
		 List<ProjectCurrentReleaseView> viewDataList = 
				 projects.stream()
				 	.map(project -> getProjectCurrentReleaseViewDataOf(project))
				 	.collect(Collectors.toList());
		 
		 return viewDataList;
	}

	@Service
	public static class ProjectValidatingServiceImpl implements IProjectValidatingService{
		private static Logger logger = Logger.getLogger(ProjectValidatingServiceImpl.class.getName());

		@Autowired
		private Validator validator;

		//
		@Override
		public Set<String> validate(Project project){
			// .validate(entity), .validateProperty(entity, "propName")
			// .validateValue(ClassName.class, "propName", value)
			Set<ConstraintViolation<Project>> violations = validator.validate(project);

			logger.info("Violations count: " + violations.size());


			return violations.stream()
					.map(violation -> violation.getMessage()
						+ " (" + violation.getInvalidValue()
						+ ") is an invalid value!" )
					.collect(Collectors.toSet());
		}

		@Override
		public void validateWithException(Project project) throws Exception{
			Set<String> violations = validate(project);
			logger.info("Violations count (to generate exception): " + violations.size());

			if (violations.size() > 0) {
				String violationExceptionMessage = violations.stream()
						.map(violation -> "\n>>> JEE.Spring bean validator exception: " + violation)
						.collect(Collectors.joining(", "));
				try {
					validateProjectAggregate(project);
				}catch(Exception ex) {
					violationExceptionMessage += "\n>>> Local validation: "
							+ ex.getMessage();
				}
				// throw new RuntimeException(violationExceptionMessage);
				throw new Exception(violationExceptionMessage);
			}
		}

		public boolean validateProjectAggregate(Project p) {
			logger.info("Validating project aggregate :: " + p);
			//
			if (p == null)
				throw new RuntimeException("Null Project!");
			if (p.getReleases() == null || p.getReleases().size() == 0)
				throw new RuntimeException("Null Releases!");
			if (p.getCurrentRelease() == null)
				throw new RuntimeException("No current release!");
	//		if (p.getCurrentRelease().getFeatures() == null || p.getCurrentRelease().getFeatures().isEmpty())
	//			throw new RuntimeException("Null Features!");
			return true;
		}
	}
}
