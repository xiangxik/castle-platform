package com.whenling.castle.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfigBean {

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.timeout", "25000");
		properties.setProperty("mail.smtp.starttls.enable", "false");
		sender.setJavaMailProperties(properties);
//		sender.setHost("smtp.exmail.qq.com");
//		sender.setUsername("mail@whenling.com");
//		sender.setPassword("Mail1234");
//		sender.setDefaultEncoding("utf-8");
		return sender;
	}
}
