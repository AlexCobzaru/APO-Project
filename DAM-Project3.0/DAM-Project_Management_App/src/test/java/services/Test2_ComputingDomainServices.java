package services;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.scrum.domain.project.Project;
import org.scrum.domain.project.ProjectCurrentReleaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * COMPUTATION Business Service Tests
 * Strategy: Simple-Facade
 * 
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/integration-testing.html
 */

//JUnit.5
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class Test2_ComputingDomainServices {
	private static Logger logger = Logger.getLogger(Test2_ComputingDomainServices.class.getName());
	
	// Support Services
	@Autowired
	private IProjectEntityRepository entityRepository;
	
	// Computing Services
	@Autowired
	private IProjectSummarizingService summarizingProjectdomainService;
	
	@Autowired
	private IProjectCurrentReleaseConsolidatingService consolidatingProjectDomainService;
	
	// Test Business Logic
	@Test @Order(1) // @Disabled
	public void test1_SummarizingProjectDomainService() {
		logger.info("Domain Service implementation instance:: " + summarizingProjectdomainService);
		logger.info("Domain Service implementation class:: " + summarizingProjectdomainService.getClass().getName());
		
		Project project = entityRepository.getById(1);
		assertNotNull(project);
		
		project = summarizingProjectdomainService.countingFeatures(project);
		project = summarizingProjectdomainService.countingReleases(project);
		
		logger.info("Computed project: " + project.getName() 
			+ " featureCount = " + project.getFeatureCount()
			+ " releaseCount = " + project.getReleaseCount());
		
		
		assertNotNull(project.getFeatureCount());
		assertTrue(project.getFeatureCount() > 0, " featureCount not computed!");
	}
	
	//
	@Test @Order(2) // @Disabled
	public void test2_ConsolidatingProjectDomainService() {
		logger.info("Service implementation instance:: " + consolidatingProjectDomainService);
		logger.info("Service implementation class:: " + consolidatingProjectDomainService.getClass().getName());
		// entityFactory.initDomainServiceEntities();
		
		ProjectCurrentReleaseView projectView = consolidatingProjectDomainService
				.getProjectCurrentReleaseViewDataOf(entityRepository.getById(1));
		logger.info("Computed ProjectCurrentReleaseView: " + projectView);

		entityRepository.toCollection().stream().forEach(p -> logger.info(" Project from repository:: " + p));

		List<ProjectCurrentReleaseView> projectViewListData = consolidatingProjectDomainService
				.getProjectCurrentReleaseViewDataOf(entityRepository.toCollection());
		projectViewListData.stream().forEach(p -> logger.info("Computed ProjectCurrentReleaseView:: " + p));
	}
}