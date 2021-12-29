package org.catalysts.commengage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ServerApplication {
    private Environment environment;

    @Autowired
	public ServerApplication(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost("myHost");
//        javaMailSender.setPort(25);
//
//        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.debug", "false");
        return properties;
    }

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
