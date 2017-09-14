package pl.training.cloud.configuration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//export ENCRYPT_KEY=SYMMETRICKEY
@SpringBootApplication
public class ConfigurationServer {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServer.class);
    }

}
