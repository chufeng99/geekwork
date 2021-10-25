package work08.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "klass")
public class KlassProperties {
    private List<StudentProperties> students;
}
