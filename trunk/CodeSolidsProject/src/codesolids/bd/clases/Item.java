package codesolids.bd.clases;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

/**
 * @author Fernando Osuna
 */

//DONE
@Entity
@Table(name = "t_item")
@Proxy(lazy = false)
public class Item {
	
	private int id;
	private int level;
	private String name;
	private int index;
	private String tipo;
	private int price;
	private String descripcion;
	private boolean inshop;
	protected boolean uso;
	protected String dirImage;
	
	private Ataque ataqueRef;
	
	protected List<PersonajeItem> personajeItemList = new ArrayList<PersonajeItem>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isInshop() {
		return inshop;
	}

	public void setInshop(boolean inshop) {
		this.inshop = inshop;
	}

	@ManyToOne
	public Ataque getAtaqueRef() {
		return ataqueRef;
	}

	public void setAtaqueRef(Ataque ataqueRef) {
		this.ataqueRef = ataqueRef;
	}

	public boolean isUso() {
		return uso;
	}

	public void setUso(boolean uso) {
		this.uso = uso;
	}

	public String getDirImage() {
		return dirImage;
	}

	public void setDirImage(String dirImage) {
		this.dirImage = dirImage;
	}
	
	@OneToMany(mappedBy = "itemRef", orphanRemoval = true )
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PersonajeItem> getPersonajeItemList() {
		return personajeItemList;
	}

	public void setPersonajeItemList(List<PersonajeItem> personajeItemList) {
		this.personajeItemList = personajeItemList;
	}
	
	

}