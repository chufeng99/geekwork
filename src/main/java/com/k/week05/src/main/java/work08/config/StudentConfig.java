package work08.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import work08.properties.StudentProperties;

@Configuration
@EnableConfigurationProperties(StudentProperties.class)
public class StudentConfig {
}
