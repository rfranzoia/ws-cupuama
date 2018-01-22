package br.com.fr.cupuama.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Data {

	private Data() {
	}

	public static String PATTERN_DATE_DEFAULT = "dd/MM/yyyy";
	public static String PATTERN_TIMESTAMP_DEFAULT = "dd/MM/yyyy HH:mm:ss";
	public static String PATTERN_HMS = "HH:mm:ss";
	
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");
    public static final SimpleDateFormat DATE_FORMAT_LONG_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_BR = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");

	/* Cache de SimpleDate Format */
	private static Map<String, SimpleDateFormat> SD_FORMAT_STORE = new HashMap<>();

	static {
		SD_FORMAT_STORE.put(PATTERN_DATE_DEFAULT, new SimpleDateFormat(PATTERN_DATE_DEFAULT));
		SD_FORMAT_STORE.put(PATTERN_TIMESTAMP_DEFAULT, new SimpleDateFormat(PATTERN_TIMESTAMP_DEFAULT));
		SD_FORMAT_STORE.put(PATTERN_HMS, new SimpleDateFormat(PATTERN_HMS));

	}

	/***
	 * 
	 * @param date
	 *            data que será formatada
	 * @param pattern
	 * @return string com a data formatada conforme o pattern passado como
	 *         parâmetro
	 */

	public static String format(Date date, String pattern) {
		return getSimpleDateFormat(pattern).format(date);
	}

	public static Date parse(String strDate, String pattern) {
		try {
			return getSimpleDateFormat(pattern).parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * 
	 * @param date
	 *            data que será formatada
	 * @param pattern
	 * @return string com a data formatada dd/MM/yyyy
	 */
	public static Date toDate(String strDate) {
		return parse(strDate, PATTERN_DATE_DEFAULT);
	}

	/***
	 * 
	 * @param date
	 *            data que será formatada
	 * @param pattern
	 * @return string com a data formatada dd/MM/yyyy HH:mm:ss
	 */
	public static Date toTimeStamp(String strDate) {
		return parse(strDate, PATTERN_DATE_DEFAULT);
	}

	/***
	 * 
	 * @param date
	 *            data que será formatada
	 * @param pattern
	 * @return string com a data formatada HH:mm:ss
	 */
	public static Date toTime(String strDate) {
		return parse(strDate, PATTERN_HMS);
	}

	public static String formatDate(Date date) {
		return format(date, PATTERN_DATE_DEFAULT);
	}

	public static String formatTimeStamp(Date date) {
		return format(date, PATTERN_TIMESTAMP_DEFAULT);
	}

	public static String formatTime(Date date) {
		return format(date, PATTERN_HMS);
	}

	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return SD_FORMAT_STORE.containsKey(pattern) ? SD_FORMAT_STORE.get(pattern) : new SimpleDateFormat(pattern);
	}

	public static int convertTimeStrToSeconds(String timeStr) {
    	String[] fields = timeStr.split("[:]");
    	
    	int m = 1;
    	int seconds = 0;
    	
    	for (int f = (fields.length - 1); f >= 0; f--) {
    		
    		seconds += Long.valueOf(fields[f]) * m;
    		m =  m * 60;    		
    	}
    	
    	return seconds;
    }
	
	public static String convertSecondsToFormatedDateString(int seconds, boolean isMiliseconds) {

        int hours = 0;
        int minutes = 0;

        if (isMiliseconds) {
            seconds = seconds / 1000;
        }

        if (seconds >= 3600) {
            hours = seconds / 3600;
            seconds = seconds - (hours * 60 * 60);
        }

        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds = seconds - (minutes * 60);
        }

        Calendar cal = Calendar.getInstance(Locale.UK);

        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2016);

        return HOUR_FORMAT.format(cal.getTime());
    }
}