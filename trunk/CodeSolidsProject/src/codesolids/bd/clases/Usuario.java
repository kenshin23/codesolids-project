package codesolids.bd.clases;


import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;



/**
 * 
 * @author Antonio López
 *
 */


@Entity
@Table(name = "t_user")
@Proxy(lazy = false)
public class Usuario {

	private int id;
	private String login;
	private String password;
	private String email;
	private Calendar dateJoin;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal( TemporalType.DATE )
	public Calendar getDateJoin() {
		return dateJoin;
	}

	public void setDateJoin(Calendar dateJoin) {
		this.dateJoin = dateJoin;
	}
	
	
}
