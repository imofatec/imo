package com.imo.backend.e2e.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;

@TestConfiguration
public class JavaMailSenderMock {

    @Bean
    @Primary
    public JavaMailSender mockJavaMailSender() {
        return Mockito.mock(JavaMailSender.class);
    }
}
