/**
 * Entrypoint of the application.
 * Creates the Springapplication server.
 */
package nl.zwemmernet.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AbnAmroAssessmentApplication {
    public static void main(String[] args) {
            SpringApplication.run(AbnAmroAssessmentApplication.class, args);
    }
}