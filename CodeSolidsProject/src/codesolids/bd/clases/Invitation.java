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

	private Usuario userGeneratesRef;
	private Usuario userReceivesRef;
	
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
	@ManyToOne
	public Usuario getUserGeneratesRef() {
		return userGeneratesRef;
	}

	public void setUserGeneratesRef(Usuario userGeneratesRef) {
		this.userGeneratesRef = userGeneratesRef;
	}
	@ManyToOne
	public Usuario getUserReceivesRef() {
		return userReceivesRef;
	}

	public void setUserReceivesRef(Usuario userreceivesRef) {
		this.userReceivesRef = userreceivesRef;
	}

	
}