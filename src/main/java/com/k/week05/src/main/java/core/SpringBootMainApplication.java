package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work08.properties.KlassProperties;
import work08.properties.SchoolProperties;
import work08.properties.StudentProperties;

@SpringBootApplication
public class SpringBootMainApplication implements CommandLineRunner {
	@Autowired
	private StudentProperties studentProperties;
	@Autowired
	private KlassProperties klassProperties;
	@Autowired
	private SchoolProperties schoolProperties;


	public static void main(String[] args) {
		System.out.println("...start ...");
		SpringApplication.run(SpringBootMainApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(studentProperties);
		System.out.println(klassProperties);
		System.out.println(schoolProperties);
	}
}
