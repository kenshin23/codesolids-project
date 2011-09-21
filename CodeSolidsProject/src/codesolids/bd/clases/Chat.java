package codesolids.bd.clases;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;


/**
 * 
 * @author Jose Luis Pérez
 *
 */

@Entity
@Table(name = "t_mensaje")
@Proxy(lazy = false)

public class Chat {

	private int id;
	private String mensaje;
	private String login;
	private Calendar dateMsg;
	
	private Usuario usuarioRef;
	
	
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
		
	
	@ManyToOne
	public Usuario getUsuarioRef() {
		return usuarioRef;
	}

	public void setUsuarioRef(Usuario usuarioRef) {
		this.usuarioRef = usuarioRef;
	}
	
}