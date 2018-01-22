package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class BasicAPIDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String wsMethodName;
	private String path;
	private String urlMethodAccessType;
	private String contentType;

	public BasicAPIDTO() {
	}

	public BasicAPIDTO(String wsMethodName, String path, String urlMethodAccessType) {
		// TODO Auto-generated constructor stub
		this.wsMethodName = wsMethodName;
		this.path = path;
		this.urlMethodAccessType = urlMethodAccessType;
		this.contentType = "";
	}

	public BasicAPIDTO(String wsMethodName, String path, String urlMethodAccessType, String contentType) {
		// TODO Auto-generated constructor stub
		this.wsMethodName = wsMethodName;
		this.path = path;
		this.urlMethodAccessType = urlMethodAccessType;
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getWsMethodName() {
		return wsMethodName;
	}

	public void setWsMethodName(String wsMethodName) {
		this.wsMethodName = wsMethodName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrlMethodAccessType() {
		return urlMethodAccessType;
	}

	public void setUrlMethodAccessType(String urlMethodAccessType) {
		this.urlMethodAccessType = urlMethodAccessType;
	}

}