package codesolids.bd.clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Proxy;

/**
 * @author Fernando Osuna
 * 
 */

@Entity
@Table(name = "t_pdisponibles")
@Proxy(lazy = false)
public class PersonajesDisponibles {
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
	private String tipo;
	protected String dirImage;
	
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDirImage() {
		return dirImage;
	}
	public void setDirImage(String dirImage) {
		this.dirImage = dirImage;
	}
}
