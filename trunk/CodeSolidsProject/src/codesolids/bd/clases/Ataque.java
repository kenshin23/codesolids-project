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
@Table(name = "t_ataque")
@Proxy(lazy = false)
public class Ataque {
	
	private int id;
	private String tipo;
	private String generador;
	private double dano;
	
//	private Poderes poderesRef;
//	private Batalla batallaRef;
	private List<Item> itemList = new ArrayList<Item>();
	
	public Ataque(){
		//Empty
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getGenerador() {
		return generador;
	}

	public void setGenerador(String generador) {
		this.generador = generador;
	}

	public double getDano() {
		return dano;
	}

	public void setDano(double dano) {
		this.dano = dano;
	}
	
	@OneToMany(mappedBy = "ataqueRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
//	@ManyToOne
//	public Poderes getPoderesRef() {
//		return poderesRef;
//	}
//
//	public void setPoderesRef(Poderes poderesRef) {
//		this.poderesRef = poderesRef;
//	}
//	
//	@ManyToOne
//	public Batalla getBatallaRef() {
//		return batallaRef;
//	}
//
//	public void setBatallaRef(Batalla batallaRef) {
//		this.batallaRef = batallaRef;
//	}

	

}
