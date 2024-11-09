package services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;


/*
 * DOMAIN Service Tests
 * Strategy: Simple-Facade
 */

//JUnit.5
@SpringBootTest
public class Test1_SimpleDomainService {
	private static Logger logger = Logger.getLogger(Test1_SimpleDomainService.class.getName());

	@Autowired
	private IProjectDomainService service;
	
	@Test
	public void test() {
		logger.info("Service implementation object :: " + service);
		logger.info("Service implementation class :: " + service.getClass().getName());
		//
		Integer featureCount = service.getProjectFeaturesCount(1);
		assertTrue(featureCount > 0, "Features not counting...");
		logger.info("Feature count autowired xml:: " + featureCount);
	}
}

/*
* https://www.logicbig.com/tutorials/spring-framework/spring-core/integration-testing.html
*/