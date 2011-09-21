package codesolids.bd.clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
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
 * 
 * @author Antonio López
 *
 */

@Entity
@Table( name = "t_clan" )
@Proxy(lazy = false)

public class Clan {

	private int id;
	private String nameClan;
	private int gold;
	private int reputacion;
	private int limite;
	private int cantPersonaje;
	private Calendar dateJoin;
	
	private Personaje clanMasterRef;
	private List<Personaje> listPersonaje = new ArrayList<Personaje>();
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column( unique = true )
	public String getNameClan() {
		return nameClan;
	}
	
	public void setNameClan(String nameClan) {
		this.nameClan = nameClan;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getReputacion() {
		return reputacion;
	}
	
	public void setReputacion(int reputacion) {
		this.reputacion = reputacion;
	}
	
	public int getLimite() {
		return limite;
	}
	
	public void setLimite(int limite) {
		this.limite = limite;
	}
	
	public int getCantPersonaje() {
		return cantPersonaje;
	}
	
	public void setCantPersonaje(int cantPersonaje) {
		this.cantPersonaje = cantPersonaje;
	}
	
	@Temporal( TemporalType.DATE )
	public Calendar getDateJoin() {
		return dateJoin;
	}
	
	public void setDateJoin(Calendar dateJoin) {
		this.dateJoin = dateJoin;
	}
	
	@ManyToOne
	public Personaje getClanMasterRef() {
		return clanMasterRef;
	}

	public void setClanMasterRef(Personaje clanMasterRef) {
		this.clanMasterRef = clanMasterRef;
	}
	
	@OneToMany(mappedBy = "clanRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<Personaje> getListPersonaje() {
		return listPersonaje;
	}
	
	public void setListPersonaje(List<Personaje> listPersonaje) {
		this.listPersonaje = listPersonaje;
	}
	
}
