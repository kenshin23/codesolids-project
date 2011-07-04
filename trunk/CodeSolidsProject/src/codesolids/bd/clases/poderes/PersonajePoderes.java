package codesolids.bd.clases.poderes;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;






@Entity
@Table(name = "t_personajePoderes")
@Proxy(lazy = false)
public class PersonajePoderes {


	private int id;
	private boolean equipado;
	private Personaje relPersonaje;
	private Poderes relPoderes;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isEquipado() {
		return equipado;
	}

	public void setEquipado(boolean equipado) {
		this.equipado = equipado;
	}
	 @ManyToOne
	public Personaje getRelPersonaje() {
		return relPersonaje;
	}

	public void setRelPersonaje(Personaje relPersonaje) {
		this.relPersonaje = relPersonaje;
	}

	 @ManyToOne
	public Poderes getRelPoderes() {
		return relPoderes;
	}

	public void setRelPoderes(Poderes relPoderes) {
		this.relPoderes = relPoderes;
	}
	
	
	
	
}
