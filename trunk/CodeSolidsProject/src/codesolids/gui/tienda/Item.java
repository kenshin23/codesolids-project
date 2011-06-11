package codesolids.gui.tienda;

/**
 * @author Fernando Osuna
 */

public class Item {

	protected int level;
	protected String nombre;
	protected int precio;
	protected int indice;
	protected String tipo;
	protected String descripcion;
	protected boolean uso;
	protected String dirImage;
	
	public Item() {
	
	}
	
	public Item(int level, String nombre, int precio, int indice, String tipo, String descripcion, boolean uso, String dirImage)
	{
		this.level = level;
		this.nombre = nombre;
		this.precio = precio;
		this.indice = indice;
		this.tipo = tipo;
		this.descripcion = descripcion;
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
	
	public void setPrice(int precio){
		this.precio = precio;
	}
	
	public int getPrice(){
		return precio;
	}
	
	public void setIndex(int indice){
		this.indice = indice;
	}
	
	public int getIndex(){
		return indice;
	}
	
	public void setType(String tipo){
		this.tipo = tipo;
	}
	
	public String getType(){
		return tipo;
	}
	
	public String getDescription() {
		return descripcion;
	}

	public void setDescription(String descripcion){
		this.descripcion = descripcion;
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
