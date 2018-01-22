package br.com.fr.cupuama.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DateParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Date date;

	public DateParam(String dateStr) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

	}

	public Date getDate() {
		return date;
	}
}