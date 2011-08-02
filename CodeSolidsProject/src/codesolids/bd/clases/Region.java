package codesolids.bd.clases;

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
 * @author Fernando Osuna
 * 
 */

@Entity
@Table(name = "t_region")
@Proxy(lazy = false)
public class Region {
	private int id;
	private String nombre;
	private String descripcion;
	private String dirImage;
	
	private List<Enemigo> enemigoList = new ArrayList<Enemigo>();
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDirImage() {
		return dirImage;
	}

	public void setDirImage(String dirImage) {
		this.dirImage = dirImage;
	}

	@OneToMany(mappedBy = "regionRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<Enemigo> getEnemigoList() {
		return enemigoList;
	}

	public void setEnemigoList(List<Enemigo> enemigoList) {
		this.enemigoList = enemigoList;
	}
	
	

}
