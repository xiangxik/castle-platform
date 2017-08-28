package com.castle.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfigBean {

	@Value("${mail.smtp.auth?:true}")
	private String mailSmtpAuth;

	@Value("${mail.smtp.timeout?:25000}")
	private String mailSmtpTimeout;

	@Value("${mail.smtp.starttls.enable?:false}")
	private String mailSmtpStartTlsEnabled;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSender sender = new JavaMailSenderImpl();
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", mailSmtpAuth);
		properties.setProperty("mail.smtp.timeout", mailSmtpTimeout);
		properties.setProperty("mail.smtp.starttls.enable", mailSmtpStartTlsEnabled);
		((JavaMailSenderImpl) sender).setJavaMailProperties(properties);
		return sender;
	}
}
