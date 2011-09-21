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
 * @author Antonio López
 *
 */

@Entity
@Table( name = "t_buzon" )
@Proxy(lazy = false)

public class Mensaje {

	private int id;
	private boolean leido;
	private Calendar dateSend;
	
	private Personaje personajeSendRef;
	private Personaje personajeReceivesRef;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@Temporal( TemporalType.DATE )
	public Calendar getDateSend() {
		return dateSend;
	}

	public void setDateSend(Calendar dateSend) {
		this.dateSend = dateSend;
	}

	@ManyToOne
	public Personaje getPersonajeSendRef() {
		return personajeSendRef;
	}

	public void setPersonajeSendRef(Personaje personajeSendRef) {
		this.personajeSendRef = personajeSendRef;
	}

	@ManyToOne
	public Personaje getPersonajeReceivesRef() {
		return personajeReceivesRef;
	}

	public void setPersonajeReceivesRef(Personaje personajeReceivesRef) {
		this.personajeReceivesRef = personajeReceivesRef;
	}
	
}
