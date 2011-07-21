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
 * 
 */

@Entity
@Table(name = "t_poderenemigo")
@Proxy(lazy = false)
public class PoderEnemigo {
	
	private int id;
	private String nombre;
	private int indice;			//Daño o indice del ataque
	private Enemigo enemigoRef;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getIndice() {
		return indice;
	}
	
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	@ManyToOne
	public Enemigo getEnemigoRef() {
		return enemigoRef;
	}
	
	public void setEnemigoRef(Enemigo enemigoRef) {
		this.enemigoRef = enemigoRef;
	}	

}
