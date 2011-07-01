package codesolids.bd.clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


/**
 * @author Fernando Osuna
 */
//DONE
@Entity
@Table(name = "t_invitation")
@Proxy(lazy = false)
public class Invitation{

	private int id;

	private String generates;
	private String receives;
	
	private Usuario usuarioRef;
	
	public Invitation() {
		// Empty
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGenerates() {
		return generates;
	}
	public void setGenerates(String generates) {
		this.generates = generates;
	}
	public String getReceives() {
		return receives;
	}
	public void setReceives(String receives) {
		this.receives = receives;
	}


	@ManyToOne
	public Usuario getUsuarioRef() {
		return usuarioRef;
	}

	public void setUsuarioRef(Usuario usuarioRef) {
		this.usuarioRef = usuarioRef;
	}
	
}
