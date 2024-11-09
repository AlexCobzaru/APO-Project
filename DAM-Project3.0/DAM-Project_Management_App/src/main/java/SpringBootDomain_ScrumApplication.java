import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.logging.Logger;

@SpringBootApplication
public class SpringBootDomain_ScrumApplication extends SpringBootServletInitializer
{
	private static Logger logger = Logger.getLogger(SpringBootDomain_ScrumApplication.class.getName());
	
	public static void main(String[] args) {
		logger.info("Loading ... :: SpringBoot - ScrumApplication Default Settings ...  ");
		SpringApplication.run(SpringBootDomain_ScrumApplication.class, args);
	}
}