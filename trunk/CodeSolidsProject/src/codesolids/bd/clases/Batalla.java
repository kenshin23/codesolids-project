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
 * 
 * @author Antonio López
 *
 */

@Entity
@Table( name = "t_batalla" )
@Proxy( lazy = false )

public class Batalla {

	private int id;
	
	private String turno;
	
	private boolean inBattle;
	
	private int secuenciaTurno; 
	
	private int escenario;
	
	private Personaje jugadorCreadorRef;
	private Personaje jugadorRetadorRef;
	
	private int vidaCreador;
	private int vidaRetador;
	
	private int psinergiaCreador;
	private int psinergiaRetador;
	
	private int victoria;
	private int derrota;
	
	private List<ChatBatalla> chatBatallaList = new ArrayList<ChatBatalla>();
	
	private String tipoAtaque;
	
	private int tiempoMovimiento;
	private boolean flag;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}

	public boolean getInBattle() {
		return inBattle;
	}

	public void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
	}
	
	public int getSecuenciaTurno() {
		return secuenciaTurno;
	}
	
	public void setSecuenciaTurno(int secuenciaTurno) {
		this.secuenciaTurno = secuenciaTurno;
	}
	
	public int getEscenario() {
		return escenario;
	}

	public void setEscenario(int escenario) {
		this.escenario = escenario;
	}
	
	@ManyToOne
	public Personaje getJugadorCreadorRef() {
		return jugadorCreadorRef;
	}

	public void setJugadorCreadorRef(Personaje jugadorCreadorRef) {
		this.jugadorCreadorRef = jugadorCreadorRef;
	}

	@ManyToOne
	public Personaje getJugadorRetadorRef() {
		return jugadorRetadorRef;
	}

	public void setJugadorRetadorRef(Personaje jugadorRetadorRef) {
		this.jugadorRetadorRef = jugadorRetadorRef;
	}

	public int getVidaCreador() {
		return vidaCreador;
	}

	public void setVidaCreador(int vidaCreador) {
		this.vidaCreador = vidaCreador;
	}

	public int getVidaRetador() {
		return vidaRetador;
	}

	public void setVidaRetador(int vidaRetador) {
		this.vidaRetador = vidaRetador;
	}

	public int getPsinergiaCreador() {
		return psinergiaCreador;
	}

	public void setPsinergiaCreador(int psinergiaCreador) {
		this.psinergiaCreador = psinergiaCreador;
	}

	public int getPsinergiaRetador() {
		return psinergiaRetador;
	}

	public void setPsinergiaRetador(int psinergiaRetador) {
		this.psinergiaRetador = psinergiaRetador;
	}
	
	public int getVictoria() {
		return victoria;
	}

	public void setVictoria(int victoria) {
		this.victoria = victoria;
	}

	public int getDerrota() {
		return derrota;
	}

	public void setDerrota(int derrota) {
		this.derrota = derrota;
	}
	
	@OneToMany(mappedBy = "batallaChatRef", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade({CascadeType.ALL})
	public List<ChatBatalla> getChatBatallaList() {
		return chatBatallaList;
	}
	public void setChatBatallaList(List<ChatBatalla> chatBatallaList) {
		this.chatBatallaList = chatBatallaList;
	}

	public String getTipoAtaque() {
		return tipoAtaque;
	}

	public void setTipoAtaque(String tipoAtaque) {
		this.tipoAtaque = tipoAtaque;
	}

	public int getTiempoMovimiento() {
		return tiempoMovimiento;
	}

	public void setTiempoMovimiento(int tiempoMovimiento) {
		this.tiempoMovimiento = tiempoMovimiento;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
