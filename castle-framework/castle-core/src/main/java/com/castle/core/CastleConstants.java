package com.castle.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;

/**
 * 基础常量
 * 
 * @author kongxiangxi
 *
 */
public interface CastleConstants {

	String characterEncoding = "UTF-8";

	Charset charset = StandardCharsets.UTF_8;

	TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

	Locale locale = Locale.CHINA;

	String date_formatter_pattern = "yyyy-MM-dd";

	String datetime_formatter_pattern = date_formatter_pattern + " HH:mm:ss";

	int order_config = Ordered.HIGHEST_PRECEDENCE;
	int order_security = -10;
	int order_webapp = 0;

	public static final String REGEX_USERNAME = "^$|^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{4,16}$";
	public static final String REGEX_PASSWORD = "^$|^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
	public static final String REGEX_MAIL = "^$|\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String REGEX_MOBILE = "^$|^[1][0-9]{10}$";

	public static final Pattern PATTERN_USERNAME = Pattern.compile(REGEX_USERNAME);
	public static final Pattern PATTENR_PASSWORD = Pattern.compile(REGEX_PASSWORD);
	public static final Pattern PATTERN_MAIL = Pattern.compile(REGEX_MAIL);
	public static final Pattern PATTENR_MOBILE = Pattern.compile(REGEX_MOBILE);

	static boolean isUsername(String username) {
		return PATTERN_USERNAME.matcher(username).matches();
	}

	static boolean isPassword(String password) {
		return PATTENR_PASSWORD.matcher(password).matches();
	}

	static boolean isEmail(String email) {
		return PATTERN_MAIL.matcher(email).matches();
	}

	static boolean isMobile(String mobile) {
		return PATTENR_MOBILE.matcher(mobile).matches();
	}

}
