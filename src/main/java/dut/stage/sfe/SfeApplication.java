package dut.stage.sfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
@EnableJpaRepositories
public class SfeApplication {
	public static void main(String[] args) {

		SpringApplication.run(SfeApplication.class, args);
		System.out.println("version: " + SpringVersion.getVersion());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String user2 = "Aaa@eope77";
		String password = "@Test1234!";
		String newadminpass = "Heelo@ope78";
		System.out.println(encoder.encode(password));

	}
}
