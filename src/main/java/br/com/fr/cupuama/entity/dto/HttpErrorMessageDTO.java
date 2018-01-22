package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;
import java.util.Date;

public class HttpErrorMessageDTO implements Serializable {

	private static final long serialVersionUID = -6366082363422371684L;

	private Date timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
