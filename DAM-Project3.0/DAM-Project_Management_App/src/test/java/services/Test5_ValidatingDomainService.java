 package services;

 import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
 import org.junit.jupiter.api.Order;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.TestMethodOrder;
 import org.scrum.domain.project.Project;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;

 import java.util.Date;
 import java.util.logging.Logger;

/*
 * VALIDATION Service Tests
 * 
 * Strategies
 * - Simple Facade
 * - Event-based from test
 * - Event-based from Workflow service
 * https://www.logicbig.com/tutorials/spring-framework/spring-core/integration-testing.html
 */

//JUnit.5
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class Test5_ValidatingDomainService {
	private static Logger logger = Logger.getLogger(Test5_ValidatingDomainService.class.getName());
	
	// Support Services
	@Autowired
	private IProjectEntityRepository entityRepository;
	
	/*
	 * Validation Flow built in-place 
	 * Validation Service as Simple-Facade
	 */
	@Autowired
	IProjectValidatingService validatorService;
	
	
    @Test @Order(1) //@Disabled
	public void test1_ValidatingProjectDomainService() {
		logger.info("Validation Service object :: " + validatorService);
		logger.info("Validation Service class:: " + validatorService.getClass().getName());
		
		Project project = entityRepository.getById(3);
		//
		project.setName(null);
		project.setStartDate(new Date());
		project.setReleases(null);
		//
		System.out.println("Project to be validated: " + project);
		//
		try {
			validatorService.validateWithException(proiect);
		}catch(Exception ex) {
			logger.severe(ex.getMessage());
			//ex.printStackTrace();
		}
	}
}