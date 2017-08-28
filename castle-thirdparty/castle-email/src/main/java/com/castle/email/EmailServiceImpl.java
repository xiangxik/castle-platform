package com.castle.email;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${mail.smtpFromMail?:mail@whenling.com}")
	private String smtpFromMail;

	@Value("${mail.smtpHost?:smtp.exmail.qq.com}")
	private String smtpHost;

	@Value("${mail.smtpPort?:25}")
	private Integer smtpPort;

	@Value("${mail.smtpUsername?:mail@whenling.com}")
	private String smtpUsername;

	@Value("${mail.smtpPassword?:Mail1234}")
	private String smtpPassword;

	private ThreadPoolExecutor pool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

	private void addSendTask(final MimeMessage mimeMessage) {
		try {
			pool.execute(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail, String subject,
			String content, Map<String, Object> model, boolean async) {
		Assert.hasText(smtpFromMail, "发件人不能为空");
		Assert.hasText(smtpHost, "服务主机不能为空");
		Assert.notNull(smtpPort, "端口不能为空");
		Assert.hasText(smtpUsername, "发件人账号不能为空");
		Assert.hasText(smtpPassword, "发件人密码不能为空");
		Assert.hasText(toMail, "收件人不能为空");
		Assert.hasText(subject, "主题不能为空");
		Assert.hasText(content, "内容不能为空");

		((JavaMailSenderImpl) javaMailSender).setHost(smtpHost);
		((JavaMailSenderImpl) javaMailSender).setPort(smtpPort);
		((JavaMailSenderImpl) javaMailSender).setUsername(smtpUsername);
		((JavaMailSenderImpl) javaMailSender).setPassword(smtpPassword);
		((JavaMailSenderImpl) javaMailSender).createMimeMessage();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(" <" + smtpFromMail + ">");
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setText(content, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		if (async) {
			addSendTask(mimeMessage);
		} else {
			javaMailSender.send(mimeMessage);
		}
	}

	@Override
	public void send(String toMail, String subject, String content, Map<String, Object> model, boolean async) {
		send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, content, model, async);
	}

	@Override
	public void send(String toMail, String subject, String content, Map<String, Object> model) {
		send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, content, model, true);
	}

	@Override
	public void send(String toMail, String subject, String content) {
		send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, content, null, true);
	}
}
