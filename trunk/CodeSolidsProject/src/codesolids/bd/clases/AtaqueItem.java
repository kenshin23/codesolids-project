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
@Table(name = "t_ataqueitem")
@Proxy(lazy = false)
public class AtaqueItem {
	
	private int id;
	private Ataque ataqueRef;
//	private Item itemRef;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	public Ataque getAtaqueRef() {
		return ataqueRef;
	}
	public void setAtaqueRef(Ataque ataqueRef) {
		this.ataqueRef = ataqueRef;
	}
	
//	@ManyToOne
//	public Item getItemRef() {
//		return itemRef;
//	}
//	public void setItemRef(Item itemRef) {
//		this.itemRef = itemRef;
//	}
	
	

}
