package codesolids.bd.clases;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private boolean activo;
	
	
	private List<Personaje> personajeList = new ArrayList<Personaje>();
	
	private List<Chat> chatList = new ArrayList<Chat>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column( unique = true )
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setDateJoin(Calendar dateJoin) {
		this.dateJoin = dateJoin;
	}

	@OneToMany(mappedBy = "usuarioRef", orphanRemoval = true)
	  @LazyCollection(LazyCollectionOption.TRUE)
	  @Cascade({CascadeType.ALL})
	public List<Chat> getChatList() {
		return chatList;
	}
	public void setChatList(List<Chat> chatList) {
		this.chatList = chatList;
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