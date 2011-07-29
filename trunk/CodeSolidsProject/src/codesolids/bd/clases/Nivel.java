package codesolids.bd.clases;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * 
 * @author Antonio López
 *
 */

@Entity
@Table( name = "t_nivel" )
@Proxy( lazy = false )

public class Nivel {

	private int level;
	private int cantidadExp;
	
	@Id
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getCantidadExp() {
		return cantidadExp;
	}
	
	public void setCantidadExp(int cantidadExp) {
		this.cantidadExp = cantidadExp;
	}
}
