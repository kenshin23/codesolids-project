package codesolids.bd.clases;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Eduardo Granados
 */

//DONE
@Entity
@Table(name = "t_receta")
@Proxy(lazy = false)
public class Receta {
	
	private int id;
	private int cantRojas;
	private int cantNegras;
	private int cantBlancas;
	private String descripcion;
	
	
	private Item itemCrear;
	private Item reagent;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getCantRojas() {
		return cantRojas;
	}

	public void setCantRojas(int cantRojas) {
		this.cantRojas = cantRojas;
	}

	public int getCantNegras() {
		return cantNegras;
	}

	public void setCantNegras(int cantNegras) {
		this.cantNegras = cantNegras;
	}

	public int getCantBlancas() {
		return cantBlancas;
	}

	public void setCantBlancas(int cantBlancas) {
		this.cantBlancas = cantBlancas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToOne
	public Item getItemCrear() {
		return itemCrear;
	}

	public void setItemCrear(Item itemCrear) {
		this.itemCrear = itemCrear;
	}

	
	@ManyToOne
	public Item getReagent() {
		return reagent;
	}

	public void setReagent(Item reagent) {
		this.reagent = reagent;
	}

	
	

	

}