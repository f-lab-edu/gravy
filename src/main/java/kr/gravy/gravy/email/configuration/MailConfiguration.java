package kr.gravy.gravy.email.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    public static final int POSSIBLE_REQUEST_TIME = 180;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 메일 서버 타임아웃 설정 (5초)
        props.put("mail.smtp.connectiontimeout", "5000"); // 연결 타임아웃
        props.put("mail.smtp.timeout", "5000");           // 읽기 타임아웃
        props.put("mail.smtp.writetimeout", "5000");      // 쓰기 타임아웃

        return mailSender;
    }
}
