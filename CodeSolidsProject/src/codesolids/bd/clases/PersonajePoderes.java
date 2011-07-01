package codesolids.bd.clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author: Antonio López
 * 
 */

@Entity
@Table(name = "t_personajepoderes")
@Proxy(lazy=false)
public class PersonajePoderes {

	private int id;
	private boolean equipado;
	private boolean learnProgreso;
	private Personaje personajeRef;
	private Poderes poderesRef;
	
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getEquipado() {
		return equipado;
	}
	
	public void setEquipado(boolean equipado) {
		this.equipado = equipado;
	}
	
	public boolean getLearnProgreso() {
		return learnProgreso;
	}
	
	public void setLearnProgreso(boolean learnProgreso) {
		this.learnProgreso = learnProgreso;
	}
	
	@ManyToOne
	public Personaje getPersonajeRef() {
		return personajeRef;
	}
	
	public void setPersonajeRef(Personaje personajeRef) {
		this.personajeRef = personajeRef;
	}
	
	@ManyToOne
	public Poderes getPoderesRef() {
		return poderesRef;
	}
	
	public void setPoderesRef(Poderes poderesRef) {
		this.poderesRef = poderesRef;
	}
	
}
