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
@Table(name = "Receta")
@Proxy(lazy = false)
public class Receta {

	private int id;
	private int cantRojas;
	private int cantAzules;	
	private int cantVerdes;
	protected String descripcion;
	private Items relItems;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	public void setCantRojas(int cantRojas) {
		this.cantRojas = cantRojas;
	}
	public int getCantRojas() {
		return cantRojas;
	}
	
	public void setCantAzules(int cantAzules) {
		this.id = cantAzulesid;
	}
	public int getCantAzules() {
		return cantAzules;
	}
	
	public void setDescripcion(String descripcion)
	{
	this.descripcion = descripcion;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	 @ManyToOne
	public items getRelItems() {
		return relItems;
	}

	public void setRelItems(items relItems) {
		this.relItems = relItems;
	}
}