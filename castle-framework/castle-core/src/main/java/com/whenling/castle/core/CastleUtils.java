package com.whenling.castle.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;

public class CastleUtils {

	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}

	public static Date toDate(String dateString) {
		if (Strings.isNullOrEmpty(dateString)) {
			return null;
		}
		try {
			return DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatYearMonth(Date date) {
		if (date == null) {
			return "";
		}
		return YEAR_MONTH_FORMAT.format(date);
	}

	public static Date toYearMonth(String dateString) {
		if (Strings.isNullOrEmpty(dateString)) {
			return null;
		}
		try {
			return YEAR_MONTH_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIME_FORMAT.format(date);
	}
}
