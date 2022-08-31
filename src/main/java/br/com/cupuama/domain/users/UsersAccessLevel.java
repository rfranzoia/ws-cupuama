package br.com.cupuama.domain.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cupuama.enums.AccessLevel;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "user_access_level")
public class UsersAccessLevel extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;
	
	@Column(name =  "access_level")
	@Enumerated(value = EnumType.STRING)
	private AccessLevel accessLevel;

	@Column(name = "from_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date fromDate;
	
	@Column(name = "to_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date toDate;
	
	public UsersAccessLevel() {
	}

	public UsersAccessLevel(Long id, Users user, AccessLevel accessLevel, Date fromDate, Date toDate) {
		this.id = id;
		this.user = user;
		this.accessLevel = accessLevel;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
