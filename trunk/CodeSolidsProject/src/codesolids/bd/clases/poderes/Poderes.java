package codesolids.bd.clases.poderes;




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




enum tipoPoderes {fuego,hielo,tierra,combo}

@Entity
@Table(name = "t_poderes")
@Proxy(lazy = false)
public class Poderes {

	private int id;
	private int dano;
	private int costoMp;
	private int costoOro;
	private int nivel;
	private String nombre;
	private tipoPoderes tipo;
	private int cooldown;
	private int tiempoAprender;
	private List<PersonajePoderes> relPersonajePoderes2 = new ArrayList<PersonajePoderes>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDano() {
		return dano;
	}
	public void setDano(int dano) {
		this.dano = dano;
	}
	public int getCostoMp() {
		return costoMp;
	}
	public void setCostoMp(int costoMp) {
		this.costoMp = costoMp;
	}
	public int getCostoOro() {
		return costoOro;
	}
	public void setCostoOro(int costoOro) {
		this.costoOro = costoOro;
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
	public tipoPoderes getTipo() {
		return tipo;
	}
	public void setTipo(tipoPoderes tipo) {
		this.tipo = tipo;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public int getTiempoAprender() {
		return tiempoAprender;
	}
	public void setTiempoAprender(int tiempoAprender) {
		this.tiempoAprender = tiempoAprender;
	}
     
	@OneToMany(mappedBy = "relPoderes", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PersonajePoderes> getRelPersonajePoderes2() {
		return relPersonajePoderes2;
	}
	public void setRelPersonajePoderes2(List<PersonajePoderes> relPersonajePoderes2) {
		this.relPersonajePoderes2 = relPersonajePoderes2;
	}
	
	
	
	
}
