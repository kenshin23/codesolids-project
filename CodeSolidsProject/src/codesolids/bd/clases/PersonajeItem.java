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
@Table(name = "t_personajeitem")
@Proxy(lazy = false)
public class PersonajeItem {
	
	private int id;
	private boolean equipado;
	private Personaje personajeRef;
	private Item itemRef;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isEquipado() {
		return equipado;
	}
	
	public void setEquipado(boolean equipado) {
		this.equipado = equipado;
	}
	
	@ManyToOne
	public Personaje getPersonajeRef() {
		return personajeRef;
	}
	
	public void setPersonajeRef(Personaje personajeRef) {
		this.personajeRef = personajeRef;
	}
	
	@ManyToOne
	public Item getItemRef() {
		return itemRef;
	}
	
	public void setItemRef(Item itemRef) {
		this.itemRef = itemRef;
	}
	
}
