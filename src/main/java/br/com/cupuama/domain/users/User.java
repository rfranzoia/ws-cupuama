package br.com.cupuama.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "user")
public class User extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private String login;
	
	@Column(nullable = false)
	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "Password cannot be null!")
	private String password;
	
	public User() {
	}

	public User(String login, String name, String password) {
		this.login = login;
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.getLogin() != null)
				return false;
		} else if (!login.equals(other.getLogin()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", login=" + login + "]";
	}
	
}
