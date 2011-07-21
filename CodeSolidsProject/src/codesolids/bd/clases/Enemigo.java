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
 * @author Antonio López
 */

@Entity
@Table(name = "t_enemigos")
@Proxy(lazy = false)
public class Enemigo {
	private int id;
	private String nombre;
	private int nivel;
	private int vida;
	private int psinergia;
	private int velocidad;
	private int oro;
	private String region;
	private String dirImage;
	
	private List<PoderEnemigo> poderEnemigoList = new ArrayList<PoderEnemigo>();
	
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

	public int getNivel() {
		return nivel;
	}
	
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public int getVida() {
		return vida;
	}
	
	public void setVida(int vida) {
		this.vida = vida;
	}
	
	public int getPsinergia() {
		return psinergia;
	}
	
	public void setPsinergia(int psinergia) {
		this.psinergia = psinergia;
	}
	
	public int getVelocidad() {
		return velocidad;
	}
	
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public int getOro() {
		return oro;
	}
	
	public void setOro(int oro) {
		this.oro = oro;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getDirImage() {
		return dirImage;
	}
	
	public void setDirImage(String dirImage) {
		this.dirImage = dirImage;
	}

	@OneToMany(mappedBy = "enemigoRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PoderEnemigo> getPoderEnemigoList() {
		return poderEnemigoList;
	}

	public void setPoderEnemigoList(List<PoderEnemigo> poderEnemigoList) {
		this.poderEnemigoList = poderEnemigoList;
	}	

}
