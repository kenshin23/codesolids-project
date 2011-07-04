package codesolids.bd.clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
 * @author Jose Luis Pérez
 *
 */

@Entity
@Table(name = "t_chat")
@Proxy(lazy = false)

public class Chat {

	private int id;
	private String mensaje;
	private String login;
	private Calendar dateMsg;
	
	private List<Usuario> usuarioList = new ArrayList<Usuario>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	@Temporal( TemporalType.DATE )
	public Calendar getDateMsg() {
		return dateMsg;
	}

	public void setDateMsg(Calendar dateMsg) {
		this.dateMsg = dateMsg;
	}
	
	@OneToMany(mappedBy = "chatRef", orphanRemoval = true)
	  @LazyCollection(LazyCollectionOption.TRUE)
	  @Cascade({CascadeType.ALL})
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}
	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}
	
}