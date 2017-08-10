package com.castle.core;

import java.util.regex.Pattern;

public abstract class CastlePatterns {

	public static final String REGEX_USERNAME = "^$|^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{4,16}$";
	public static final String REGEX_PASSWORD = "^$|^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
	public static final String REGEX_MAIL = "^$|\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String REGEX_MOBILE = "^$|^[1][0-9]{10}$";

	public static final Pattern PATTERN_USERNAME = Pattern.compile(REGEX_USERNAME);
	public static final Pattern PATTENR_PASSWORD = Pattern.compile(REGEX_PASSWORD);
	public static final Pattern PATTERN_MAIL = Pattern.compile(REGEX_MAIL);
	public static final Pattern PATTENR_MOBILE = Pattern.compile(REGEX_MOBILE);

	public static boolean isUsername(String username) {
		return PATTERN_USERNAME.matcher(username).matches();
	}

	public static boolean isEmail(String email) {
		return PATTERN_MAIL.matcher(email).matches();
	}

	public static boolean isMobile(String mobile) {
		return PATTENR_MOBILE.matcher(mobile).matches();
	}

}
