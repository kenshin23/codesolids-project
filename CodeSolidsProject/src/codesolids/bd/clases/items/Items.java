package codesolids.bd.clases.items;

import java.util.ArrayList;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

/**
 * @author: Erick Oviedo
 * 
*/
@Entity
@Table(name = "Items")
@Proxy(lazy = false)

public class Items {

	protected int id;
	protected int nivel;
	protected String nombre;
	protected int indice;
	protected int precioVent;
	protected String tipo;
	protected boolean mostrarTienda;
	protected String descripcion;
	protected List<PersonajeItems> PersonajeItemsList = new ArrayList<PersonajeItems>(); 
	
	@Idm 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNivel() {
		return nivel;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
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
	
	public int getPrecioVent() {
		return precioVent;
	}
	
	public void setPrecioVent(int precioVent) {
		this.precioVent = precioVent;
	}
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean getMostrarTienda() {
		return mostrarTienda;
	}
	
	public void setMostrarTienda(boolean mostrarTienda) {
		this.mostrarTienda = mostrarTienda;
	}
	
	public void setDescripcion(String descripcion)
	{
	this.descripcion = descripcion;
	}

	public String getDescripcion() 
	{
		return descripcion;
	}

	

	@OneToMany(mappedBy = "relPersonaje", orphanRemoval = true )
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	

	public List<PersonajeItems> getPersonajeItemsList() {
		return PersonajeItemsList;
	}

	public void setPersonajeItemsList(List<PersonajeItems> PersonajeItemsList) {
		this.PersonajeItemsList = PersonajeItemsList;
	}
	
	@OneToMany(mappedBy = "relReceta", orphanRemoval = true )
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	
	public List<Receta> getRecetaList()
		{
			return RecetaList;
		}
	
	public void setRecetaList(List<Receta> RecetaList) {
		this.RecetaList = RecetaList;
	}
	
}