package com.castle.security.captcha;

import java.awt.Font;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

@Configuration
public class CaptchaConfigBean {

	public static final String CAPTCHA_FILTER_NAME = "captchaFilter";
	
	@Bean(name = CAPTCHA_FILTER_NAME)
	public CaptchaFilter captchaFilter(ObjectMapper objectMapper) {
		return new CaptchaFilter(captchaService(), "captcha", objectMapper, true);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CaptchaService captchaService() {
		GenericManageableCaptchaService captchaService = new GenericManageableCaptchaService(captchaEngine(), 180, 180000, 10);
		return captchaService;
	}

	@Bean
	public CaptchaEngine captchaEngine() {
		GenericCaptchaEngine captchaEngine = new GenericCaptchaEngine(new CaptchaFactory[] { captchaFactory() });
		return captchaEngine;
	}

	@Bean
	public CaptchaFactory captchaFactory() {
		GimpyFactory gimpyFactory = new GimpyFactory(wordGenerator(), wordToImage());
		return gimpyFactory;
	}

	@Bean
	public WordGenerator wordGenerator() {
		return new RandomWordGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	@Bean
	public WordToImage wordToImage() {
		ColorGenerator fontColorGenerator = new RandomRangeColorGenerator(new int[] { 128, 255 }, new int[] { 128, 255 }, new int[] { 128, 255 });
		ColorGenerator backgroundColorGenerator = new RandomRangeColorGenerator(new int[] { 0, 127 }, new int[] { 0, 127 }, new int[] { 0, 127 });
		return new ComposedWordToImage(new RandomFontGenerator(36, 48, new Font[] { new Font("Arial", 0, 1) }), new UniColorBackgroundGenerator(220, 80, backgroundColorGenerator),
				new SimpleTextPaster(4, 4, fontColorGenerator));
	}

	@Bean
	public WebMvcConfigurer captchaWebMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter = new BufferedImageHttpMessageConverter();
				if (converters.size() > 0) {
					converters.add(converters.size() - 1, bufferedImageHttpMessageConverter);
				} else {
					converters.add(bufferedImageHttpMessageConverter);
				}
			}
		};
	}
}
