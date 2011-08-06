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
@Table(name = "t_invitacion")
@Proxy(lazy = false)
public class Invitacion{

	private int id;
	
	private boolean estado;

	private Personaje personajeGeneratesRef;
	private Personaje personajeReceivesRef;
	
	public Invitacion() {
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
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@ManyToOne
	public Personaje getPersonajeGeneratesRef() {
		return personajeGeneratesRef;
	}

	public void setPersonajeGeneratesRef(Personaje personajeGeneratesRef) {
		this.personajeGeneratesRef = personajeGeneratesRef;
	}
	@ManyToOne
	public Personaje getPersonajeReceivesRef() {
		return personajeReceivesRef;
	}

	public void setPersonajeReceivesRef(Personaje personajeReceivesRef) {
		this.personajeReceivesRef = personajeReceivesRef;
	}
	
}