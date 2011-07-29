package codesolids.bd.clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


/**
 * 
 * @author Jose Luis Pérez
 *
 */

@Entity
@Table(name = "t_mensaje_batalla")
@Proxy(lazy = false)

public class ChatBatalla {

	private int id;
	private String mensaje;
	private String login;

	private Batalla batallaChatRef; 
	
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
		
	
	@ManyToOne
	public Batalla getBatallaChatRef() {
		return batallaChatRef;
	}

	public void setBatallaChatRef(Batalla batallaChatRef) {
		this.batallaChatRef = batallaChatRef;
	}
	
}