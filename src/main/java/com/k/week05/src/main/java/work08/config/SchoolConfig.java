package work08.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import work08.properties.SchoolProperties;

@Configuration
@EnableConfigurationProperties(SchoolProperties.class)
public class SchoolConfig {
}
