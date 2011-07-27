package codesolids.bd.clases.items;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * @author: Erick Oviedo
 * 
*/
@Entity
@Table(name = "personajeitems")
@Proxy(lazy = false)
public class PersonajeItems {


	private int id;
	private boolean equipado;
	private Personaje relPersonaje;
	private Items relItems;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	public Personaje getRelPersonaje() {
		return relPersonaje;
	}

	public void setRelPersonaje(Personaje relPersonaje) {
		this.relPersonaje = relPersonaje;
	}

	 @ManyToOne
	public items getRelItems() {
		return relItems;
	}

	public void setRelItems(items relItems) {
		this.relItems = relItems;
	}
}