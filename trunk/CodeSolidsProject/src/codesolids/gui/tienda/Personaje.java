package codesolids.gui.tienda;

import java.util.ArrayList;
import java.util.List;

import codesolids.gui.academia.Poder;
import codesolids.gui.tienda.Item;

/**
 * 
 * @author Antonio López
 * 
 * @author Fernando Osuna
 * 
 */

public class Personaje {

	protected String login;
	protected int level;
	protected int gold;
	protected int xp;
	protected int life;
	protected int energy;
	protected int force;
	protected int defense;
	protected int speed;
	protected String tipo;
	protected String dirImage;
	protected List<Poder> poderes;
	protected List<Item> items;
	
	
	public Personaje()
	{
		this.level = 5;
		this.gold = 30000;
		this.items = new ArrayList<Item>();
	}
	
	public Personaje(String login, int level, int xp, int gold, int life, int energy, int force, int defense, int speed, String tipo, String dirImage, //
						List<Poder> poderes, List<Item> items){
		
		this.login = login;
		this.level = level;
		this.gold = gold;
		this.xp = xp;
		this.life = life;
		this.energy = energy;
		this.force = force;
		this.defense = defense;
		this.speed = speed;
		this.tipo = tipo;
		this.dirImage = dirImage;
		this.poderes = poderes;
		this.items = items;
		
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public String getLogin()
	{
		return login;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setGold(int gold)
	{
		this.gold = gold;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public void setXp(int xp)
	{
		this.xp = xp;
	}
	
	public int getXp()
	{
		return xp;
	}
	
	public void setLife(int life)
	{
		this.life = life;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public void setEnergy(int energy)
	{
		this.energy = energy;
	}
	
	public int getEnergy()
	{
		return life;
	}
	
	public void setForce(int force)
	{
		this.force = force;
	}
	
	public int getForce()
	{
		return force;
	}
	
	public void setDefense(int defense)
	{
		this.defense = defense;
	}
	
	public int getDefense()
	{
		return defense;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}

	public void setType(String tipo)
	{
		this.tipo = tipo;
	}

	public String getType()
	{
		return tipo;
	}
	
	public void setImage(String dirImage)
	{
		this.dirImage = dirImage;
	}

	public String getImage()
	{
		return dirImage;
	}
	
	public void setPoderes(Poder poder){
		
		this.poderes.add(poder);
		
	}
	
	public void setItems(Item item){
		
		this.items.add(item);
		
	}
	
	public void buildItem(String name){
		if(name == "Armadura Negra"){
			Item it1 = new Item(3, "Armadura Negra", 5000, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
			this.items.add(it1);
		}
		if(name == "Espada Roja"){
			Item it2 = new Item(5, "Espada Roja", 5000, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
			this.items.add(it2);
		}
		if(name == "Bomba Blanca"){
			Item it3 = new Item(6, "Bomba Blanca", 3500, 30, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb.png");
			this.items.add(it3);
		}
		if(name == "Bomba Negra"){
			Item it4 = new Item(6, "Bomba Negra", 6500, 60, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png");
			this.items.add(it4);
		}
		if(name == "Bomba Roja"){
			Item it5 = new Item(7, "Bomba Roja", 9500, 90, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png");
			this.items.add(it5);
		}		
	}
	
	public List<Item> getItems(){
		return this.items;
	}
	
	public List<Poder> getPowers(){
		return this.poderes;
	}
	
	public void revomeItems(){
		
		this.items = new ArrayList<Item>();
		
	}
	
	public void removeItem(Item item){
		if(this.items.contains(item)){
			this.items.remove(this.items.indexOf(item));
		}
	}
	
	public Item searchItem(String name){
		Item item = new Item();
		for(int i = 0; i<this.items.size(); i++){
			if(this.items.get(i).getName() == name){
				item = this.items.get(i);
			}				
		}
		return item;
	}
	
	public boolean contains(String name){
		boolean bool = false;
		for(int i = 0; i<this.items.size(); i++){
			if(this.items.get(i).getName() == name){
				bool = true;
				i = this.items.size();
			}				
		}
		return bool;		
	}
	
	public int countItem(String name){
		int counter = 0;
		for(int i = 0; i<this.items.size(); i++){
			if(this.items.get(i).getName() == name){
				counter++;
			}				
		}
		return counter;
	}
}
