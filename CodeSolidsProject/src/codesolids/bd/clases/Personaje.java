package codesolids.bd.clases;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

/**
 * @author: Antonio López
 * 
 */

@Entity
@Table(name = "t_personaje")
@Proxy(lazy = false)

public class Personaje {

	protected int id;
	protected int level;
	protected int gold;
	protected int xp;
	protected int hp;
	protected int mp;
	protected String tipo;
	protected String dirImage;
	protected boolean learning;
	protected Calendar fechaInicio;
	protected Calendar fechaFin;
	protected List<PersonajePoderes> personajePoderesList = new ArrayList<PersonajePoderes>();	
	
	private List<Invitation> invGeneratesList = new ArrayList<Invitation>();
	private List<Invitation> invReceivesList = new ArrayList<Invitation>();
	
	protected Usuario usuarioRef;
	private Timestamp arena;
	
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
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getXp() {
		return xp;
	}
	
	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getMp() {
		return mp;
	}
	
	public void setMp(int mp) {
		this.mp = mp;
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
	
	public boolean getLearning() {
		return learning;
	}
	
	public void setLearning(boolean learning) {
		this.learning = learning;
	}
	
	@Temporal( TemporalType.TIMESTAMP )
	public Calendar getFechaInicio() {
		return fechaInicio;
	}
	
	
	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal( TemporalType.TIMESTAMP )
	public Calendar getFechaFin() {
		return fechaFin;
	}
	
	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	@OneToMany(mappedBy = "personajeRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<PersonajePoderes> getPersonajePoderesList() {
		return personajePoderesList;
	}

	public void setPersonajePoderesList(List<PersonajePoderes> personajePoderesList) {
		this.personajePoderesList = personajePoderesList;
	}

	@ManyToOne
	public Usuario getUsuarioRef() {
		return usuarioRef;
	}

	public void setUsuarioRef(Usuario usuarioRef) {
		this.usuarioRef = usuarioRef;
	}
	
	public Timestamp getArena() {
		return arena;
	}

	public void setArena(Timestamp arena) {
		this.arena = arena;
	}
	
	@OneToMany(mappedBy = "personajeGeneratesRef", orphanRemoval = true)
	  @LazyCollection(LazyCollectionOption.TRUE)
	  @Cascade({CascadeType.ALL})
	  public List<Invitation> getInvGeneratesList() {
		return invGeneratesList;
	  }

	  public void setInvGeneratesList(List<Invitation> invGeneratesList) {
		this.invGeneratesList = invGeneratesList;
	  }
	  
	  @OneToMany(mappedBy = "personajeReceivesRef", orphanRemoval = true)
	  @LazyCollection(LazyCollectionOption.TRUE)
	  @Cascade({CascadeType.ALL})
	  public List<Invitation> getInvReceivesList() {
		return invReceivesList;
	  }

	  public void setInvReceivesList(List<Invitation> invReceivesList) {
		this.invReceivesList = invReceivesList;
	  }
	
}

