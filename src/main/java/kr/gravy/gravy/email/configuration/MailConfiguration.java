package kr.gravy.gravy.email.configuration;

import kr.gravy.gravy.configuration.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfiguration {

    public static final int POSSIBLE_REQUEST_TIME = 180;

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.host());
        mailSender.setPort(mailProperties.port());
        mailSender.setUsername(mailProperties.username());
        mailSender.setPassword(mailProperties.password());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProperties.transport().protocol());
        props.put("mail.smtp.auth", String.valueOf(mailProperties.smtp().auth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(mailProperties.smtp().starttls().enable()));

        // Duration을 밀리초로 변환하여 JavaMail Properties에 적용
        props.put("mail.smtp.connectiontimeout", String.valueOf(mailProperties.smtp().connectiontimeout().toMillis()));
        props.put("mail.smtp.timeout", String.valueOf(mailProperties.smtp().timeout().toMillis()));
        props.put("mail.smtp.writetimeout", String.valueOf(mailProperties.smtp().writetimeout().toMillis()));

        return mailSender;
    }
}
