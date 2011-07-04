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





enum tipoPersonaje {magoTierra,magoHielo,magoFuego,guerrero,ladron}

@Entity
@Table(name = "t_personaje")
@Proxy(lazy = false)
public class Personaje {

	private int id;
	private int nivel;
	private int experiencia;
	private int vida;
	private int psinergia;
	private int ataqueBasico;
	private int ataqueEspecial;
	private int velocidad;
	private int defensa;
	private int oro;
	private tipoPersonaje tipo;
	private boolean en_Arena;

	private List<PersonajePoderes> relPersonajePoderes = new ArrayList<PersonajePoderes>();
	
	

	@Id
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
	public int getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
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
	public int getAtaqueBasico() {
		return ataqueBasico;
	}
	public void setAtaqueBasico(int ataqueBasico) {
		this.ataqueBasico = ataqueBasico;
	}
	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}
	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public int getDefensa() {
		return defensa;
	}
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	public int getOro() {
		return oro;
	}
	public void setOro(int oro) {
		this.oro = oro;
	}
	public tipoPersonaje getTipo() {
		return tipo;
	}
	public void setTipo(tipoPersonaje tipo) {
		this.tipo = tipo;
	}
	public boolean isEn_Arena() {
		return en_Arena;
	}
	public void setEn_Arena(boolean en_Arena) {
		this.en_Arena = en_Arena;
	}
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@PrimaryKeyJoinColumn
//	public Usuario getRelUsuario() {
//		return relUsuario;
//	}
//	public void setRelUsuario(Usuario relUsuario) {
//		this.relUsuario = relUsuario;
//	}
	
	
	@OneToMany(mappedBy = "relPersonaje", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PersonajePoderes> getRelPersonajePoderes() {
		return relPersonajePoderes;
	}
	public void setRelPersonajePoderes(List<PersonajePoderes> relPersonajePoderes) {
		this.relPersonajePoderes = relPersonajePoderes;
	}
	
	
	
	
	
}
