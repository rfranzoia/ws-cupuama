package br.com.cupuama.domain.users.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	
	private String login;

	@NotNull(message = "Name cannot be null!")
	private String name;

	private String password;
	
	private String newPassword;
	
	private UserDTO() {
	}

	private UserDTO(String login, String name, String password, String newPassword) {
		this.login = login;
		this.name = name;
		this.password = password;
		this.newPassword = newPassword;
	}

	public static UserDTOBuilder newBuilder() {
		return new UserDTOBuilder();
	}

	@JsonProperty
	public String getLogin() {
		return login;
	}

	@JsonProperty
	public String getName() {
		return name;
	}
	
	@JsonProperty
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public String getNewPassword() {
		return newPassword;
	}
	
	public static class UserDTOBuilder {
		private String login;
		private String name;
		private String password;
		private String newPassword;

		public UserDTOBuilder setLogin(String login) {
			this.login = login;
			return this;
		}
		
		public UserDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public UserDTOBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public UserDTOBuilder setNewPassword(String newPassword) {
			this.newPassword = newPassword;
			return this;
		}
		
		public UserDTO createUserDTO() {
			return new UserDTO(login, name, password, newPassword);
		}
	}
}
