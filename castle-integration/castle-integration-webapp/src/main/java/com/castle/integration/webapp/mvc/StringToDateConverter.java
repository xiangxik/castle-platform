package com.castle.integration.webapp.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;

import com.google.common.base.Strings;

public class StringToDateConverter implements Converter<String, Date> {

	public static final String REGEX_VALIDATE_DATE = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
	public static final String REGEX_VALIDATE_DATETIME = "(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})(?::)?([0-5]{1}\\d{1})";
	public static final String REGEX_VALIDATE_DATETIME_NO_SECONDS = "(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})";

	public static final Pattern PATTERN_VALIDATE_DATE = Pattern.compile(REGEX_VALIDATE_DATE);
	public static final Pattern PATTERN_VALIDATE_DATETIME = Pattern.compile(REGEX_VALIDATE_DATETIME);
	public static final Pattern PATTERN_VALIDATE_DATETIME_NO_SECONDS = Pattern.compile(REGEX_VALIDATE_DATETIME_NO_SECONDS);

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_TIME_FORMAT_NO_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date convert(String source) {
		if (Strings.isNullOrEmpty(source)) {
			return null;
		}
		try {
			if (PATTERN_VALIDATE_DATE.matcher(source).matches()) {

				return DATE_FORMAT.parse(source);
			}

			if (PATTERN_VALIDATE_DATETIME.matcher(source).matches()) {
				return DATE_TIME_FORMAT.parse(source);
			}

			if (PATTERN_VALIDATE_DATETIME_NO_SECONDS.matcher(source).matches()) {
				return DATE_TIME_FORMAT_NO_SECONDS.parse(source);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
