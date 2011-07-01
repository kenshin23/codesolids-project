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
 * @author: Antonio López
 * 
 * @Colaborador: Jose Perez
 * 
 */


@Entity
@Table(name = "t_poderes")
@Proxy(lazy = false)

public class Poderes {

	protected int id;
	protected int level;
	protected String name;
	protected int gold;
	protected int damage;
	protected int cooldown;
	protected int timeTraining;
	protected int psinergia;
	protected String tipo;
	protected boolean uso;
	protected String dirImage;
	protected String descripcion;
	protected byte[] image;
	protected List<PersonajePoderes> personajePoderesList = new ArrayList<PersonajePoderes>(); 
	
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
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public int getTimeTraining() {
		return timeTraining;
	}
	
	public void setTimeTraining(int timeTraining) {
		this.timeTraining = timeTraining;
	}
	
	public int getPsinergia() {
		return psinergia;
	}
	
	public void setPsinergia(int psinergia) {
		this.psinergia = psinergia;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean getUso() {
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
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@OneToMany(mappedBy = "poderesRef", orphanRemoval = true )
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PersonajePoderes> getPersonajePoderesList() {
		return personajePoderesList;
	}

	public void setPersonajePoderesList(List<PersonajePoderes> personajePoderesList) {
		this.personajePoderesList = personajePoderesList;
	}

	
}
