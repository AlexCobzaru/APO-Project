package services;

import org.junit.jupiter.api.Test;
import org.scrum.domain.project.ProjectCurrentReleaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.logging.Logger;

/*
 * WORKFLOW Business Service Tests
 * Strategy: Simple-Facade
 * 
 */
//JUnit.5
@SpringBootTest
public class Test3_WorkflowService {
	private static Logger logger = Logger.getLogger(Test3_WorkflowService.class.getName());
	
	/* Simple-Facade Business Service
	 * Injection from scanning 
	 * @Service("PlanningProjectWorkflowService")
	 * Bean-implementation class
	 */
	@Autowired
	private IPlanningProjectWorkflowService workflowService;
	
	@Test
	public void test1_WorkflowServiceFacade() {
		logger.info("Domain Service implementation instance:: " + workflowService);
		logger.info("Domain Service implementation class:: " 
					+ workflowService.getClass().getName());
		
		Date tomorow = new Date(new Date().getTime() + 1000*60*60*24*1);
		// 1.
		Integer projectId = workflowService.planNewProject("Planned.Test.Project", tomorow);
		// 2...
		workflowService.addFeatureToProject(projectId, "Test.Feature_A", "Planned.Test");
		workflowService.addFeatureToProject(projectId, "Test.Feature_B", "Planned.Test");
		workflowService.addFeatureToProject(projectId, "Test.Feature_C", "Planned.Test");
		// 3.
		Date futureDateOf45Day = new Date(new Date().getTime() + 1000*60*60*24*45);
		workflowService.planCurrentRelease(projectId, futureDateOf45Day);
		// 4.
		ProjectCurrentReleaseView viewData = workflowService.getProjectSummaryData(projectId);
		// 
		assertEquals(3, viewData.getReleaseFeatureCount());
		
		logger.info(viewData.toString());	
	}
}
