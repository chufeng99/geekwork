package work08.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import work08.properties.KlassProperties;

@Configuration
@EnableConfigurationProperties(KlassProperties.class)
public class KlassConfig {
}
