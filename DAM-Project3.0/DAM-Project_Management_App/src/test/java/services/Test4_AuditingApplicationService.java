package services;

import org.junit.jupiter.api.Test;
import org.scrum.domain.project.ProjectCurrentReleaseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.logging.Logger;


/*
 * AUDIT Business Service Tests
 * 
 * WORKFLOW Business Service Tests
 * Strategy: AOP
 * 
 */
//JUnit.5
@SpringBootTest
public class Test4_AuditingApplicationService {
	private static Logger logger = Logger.getLogger(Test4_AuditingApplicationService.class.getName());
	/*
	 * Audit Flow built by AppScanConfig BeanFactory class
	 * by AfterReturningAdvice aspect
	 */
	@Autowired
	private IPlanningProjectWorkflowService auditedService;
	
	@Test//@Disabled
	public void test3_AuditWorkflowService() {
		logger.info("Audited Domain Service implementation instance:: " + auditedService);
		logger.info("Audited Domain Service implementation class:: " + auditedService.getClass().getName());
		
		Date tomorow = new Date(new Date().getTime() + 1000*60*60*24*1);
		Integer projectId = auditedService.planNewProject("Planned.Test.Project", tomorow);
		
		auditedService.addFeatureToProject(projectId, "Test.Feature_A", "Planned.Test");
		auditedService.addFeatureToProject(projectId, "Test.Feature_B", "Planned.Test");
		auditedService.addFeatureToProject(projectId, "Test.Feature_C", "Planned.Test");
		Date futureDateOf45Day = new Date(new Date().getTime() + 1000*60*60*24*45);
		auditedService.planCurrentRelease(projectId, futureDateOf45Day);
		ProjectCurrentReleaseView viewData = auditedService.getProjectSummaryData(projectId);
		logger.info(viewData.toString());
		
	}
}
