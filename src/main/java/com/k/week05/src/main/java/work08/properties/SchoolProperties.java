package work08.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "school")
public class SchoolProperties {
    private KlassProperties klasses;
    private StudentProperties student;
}
