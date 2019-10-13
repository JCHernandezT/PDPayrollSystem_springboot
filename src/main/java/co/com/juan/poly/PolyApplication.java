package co.com.juan.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class used by Spring Boot to start the application.
 * 
 * This application is used to support the payroll calculation process of a
 * police department.
 * 
 * @author Juan Camilo Hernández.
 * @see #main(String[])
 *
 */
@SpringBootApplication
public class PolyApplication {

	/**
	 * Method used by Sring Boot to start the application.
	 * 
	 * @param args
	 *            command line of arguments as an array of strings.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PolyApplication.class, args);
	}

}
