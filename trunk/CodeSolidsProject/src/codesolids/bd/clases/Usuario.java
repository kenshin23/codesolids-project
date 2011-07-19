package codesolids.bd.clases;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
	
	private Chat chatRef;
	
	private List<Personaje> personajeList = new ArrayList<Personaje>();
	
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

	@ManyToOne
	public Chat getChatRef() {
		return chatRef;
	}

	public void setChatRef(Chat chatRef) {
		this.chatRef = chatRef;
	}

	@OneToMany(mappedBy = "usuarioRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<Personaje> getPersonajeList() {
		return personajeList;
	}

	public void setPersonajeList(List<Personaje> personajeList) {
		this.personajeList = personajeList;
	}	 
}