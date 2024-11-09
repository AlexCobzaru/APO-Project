package org.Proiect.Servicii.impl;

import org.scrum.domain.project.ProjectAuditView;
import org.scrum.domain.services.IPlanningProjectAuditingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
// Auditing PlanningProjectBusinessWorkflowService :: addFeatureToProject
public class PlanningProjectAuditingServiceImpl implements
		IPlanningProjectAuditingService {
	private static Logger logger = Logger.getLogger(PlanningProjectAuditingServiceImpl.class.getName());
	
	// Business Logic
	@Override
	public void auditProjectFeature(Integer projectId, String featureName) {
		ProjectAuditView projectAuditData = new ProjectAuditView(
				projectId, featureName,
				ProjectAuditView.EFeatureOperation.ADDED);
		//
		logger.info("AUDIT INFO: " + projectAuditData);
	}

	// SDI Bean Component
	//@Component("ProjectDomainService")
	@Service
	public static class ProjectDomainServiceImpl implements IProjectDomainService {

		@Override
		public List<Feature> getProjectFeatures(Project project) {
			List<Feature> projectFeatures = new ArrayList<>();
			for (Release r : project.getReleases())
				projectFeatures.addAll(r.getFeatures());
			return projectFeatures;
		}

		@Override
		public Integer getProjectFeaturesCount(Project project) {
			List<Feature> projectFeatures = getProjectFeatures(project);
			return projectFeatures.size();
		}

		@Override
		public List<Feature> getProjectFeatures(Integer projectID) {
			Project project = entityRepository.getById(projectID);
			return getProjectFeatures(project);
		}

		@Override
		public Integer getProjectFeaturesCount(Integer projectID) {
			Project project = entityRepository.getById(projectID);
			return getProjectFeaturesCount(project);
		}

		// Dependency
		@Autowired
		private IProjectEntityRepository entityRepository;

		//@Override
		public void setProjectEntityRepository(IProjectEntityRepository repository) {
			this.entityRepository = repository;
		}

		public ProjectDomainServiceImpl() {
			super();
		}

		public ProjectDomainServiceImpl(IProjectEntityRepository entityRepository) {
			super();
			this.entityRepository = entityRepository;
		}

		@Override
		public Feature getProjectFeature(Project project, String featureName) {
			List<Feature> projectFeatures = getProjectFeatures(project);
			List<Feature> features = projectFeatures.stream().filter(f -> f.getName().equals(featureName)).collect(Collectors.toList());
			if (features.size() > 0)
				return features.get(0);

			return null;
		}

		public IProjectEntityRepository getEntityRepository() {
			return entityRepository;
		}

	}

	//SDI Bean Component
	@Repository
	@Scope("singleton")
	public static class ProjectEntityRepositoryBase implements IProjectEntityRepository {

		private static Logger logger = Logger.getLogger(ProjectEntityRepositoryBase.class.getName());

		public ProjectEntityRepositoryBase() {
			logger.info(">>> BEAN: ProjectEntityRepositoryCDI instantiated!");
		}
		//
		private Map<Integer, Project> entities = new HashMap<>();
		//
		private Integer nextID = 0;
		@Override
		public Integer getNextID() {
			nextID++;
			return nextID;
		}

		@Override
		public Project getById(Integer id) {
			return entities.get(id);
		}

		@Override
		public Project get(Project sample) {
			return getById(sample.getProjectNo());
		}

		@Override
		public Collection<Project> toCollection() {
			List<Project> projectList = new ArrayList<>();
			projectList.addAll(this.entities.values());
			return projectList;
		}

		@Override
		public Project add(Project entity) {
			if (entity.getProjectNo() == null) {
				nextID++;
				entity.setProjectNo(nextID);
			}
			entities.put(entity.getProjectNo(), entity);
			return entity;
		}

		@Override
		public Collection<Project> addAll(Collection<Project> entities) {
			for(Project entity: entities)
				this.add(entity);
			return entities;
		}

		@Override
		public boolean remove(Project entity) {
			if (this.entities.containsValue(entity)) {
				this.entities.remove(entity.getProjectNo());
				return true;
			}
			return false;
		}

		@Override
		public boolean removeAll(Collection<Project> entities) {
			Boolean flag =  true;
			for(Project entity: entities) {
				if (!this.remove(entity))
					flag = false;
			}

			return flag;
		}

		@Override
		public int size() {
			return this.entities.values().size();
		}

	}
}
