package br.com.cupuama.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";
	
	public static String getFormattedPeriod(Date date) {
		return PERIOD_DATE_FORMAT.format(date);
	}
}
