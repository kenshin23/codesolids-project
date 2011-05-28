package codesolids.gui.academia;

/**
 * @author: Antonio López
 * 
 */

public class Poder {

	protected int level;
	protected String nombre;
	protected int oro;
	protected int dano;
	protected int tiempoReutilizacion;
	protected int psinergia;
	protected String tipo;
	protected boolean uso;
	protected String dirImage;
	
	public Poder() {
	
	}
	
	public Poder(int level,String nombre,int oro,int dano,int tiempoReutilizacion,int psinergia,String tipo,boolean uso,String dirImage)
	{
		this.level = level;
		this.nombre = nombre;
		this.oro = oro;
		this.dano = dano;
		this.tiempoReutilizacion = tiempoReutilizacion;
		this.psinergia = psinergia;
		this.tipo = tipo;
		this.uso = uso;
		this.dirImage = dirImage;

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public void setName(String nombre){
		this.nombre = nombre;
	}
	
	public String getName() {
		return nombre;
	}
	
	public void setGold(int oro){
		this.oro = oro;
	}
	
	public int getGold(){
		return oro;
	}
	
	public void setDamage(int dano){
		this.dano = dano;
	}
	
	public int getDamage(){
		return dano;
	}
	
	public void setCooldown(int tiempoReutilizacion){
		this.tiempoReutilizacion = tiempoReutilizacion;
	}
	
	public int getCooldown(){
		return tiempoReutilizacion;
	}
	
	public void setPsinergia(int psinergia){
		this.psinergia = psinergia;
	}
	
	public int getPsinergia(){
		return psinergia;
	}
	
	public void setType(String tipo){
		this.tipo = tipo;
	}
	
	public String getType(){
		return tipo;
	}
	
	public void setUse(boolean uso){
		this.uso = uso;
	}
	
	public boolean getUse(){
		return uso;
	}
	
	public void setImage(String dirImage){
		this.dirImage = dirImage;
	}
	
	public String getImage(){
		return dirImage;
	}
	
}
