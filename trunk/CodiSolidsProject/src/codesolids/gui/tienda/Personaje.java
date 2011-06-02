package codesolids.gui.tienda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import codesolids.gui.academia.Poder;
import codesolids.gui.tienda.Item;

/**
 * @author Fernando Osuna
 * @author Antonio López
 */

public class Personaje {

	protected String login;
	protected int level;
	protected int gold;
	protected int xp;
	protected int hp;
	protected int cp;
	protected String tipo;
	protected String dirImage;
	protected List<Poder> poderes;
	protected List<Item> items;
	
	
	public Personaje()
	{
		this.gold = 20000;
		this.items = new ArrayList<Item>();
	}
	
	public Personaje(String login, int level, int gold, int xp,int hp, int cp, String tipo, String dirImage, //
						List<Poder> poderes, List<Item> items){
		
		this.login = login;
		this.level = level;
		this.gold = gold;
		this.xp = xp;
		this.hp = hp;
		this.cp = cp;
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
	
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	
	public int getHp()
	{
		return hp;
	}
	
	public void setCp(int cp)
	{
		this.cp = cp;
	}
	
	public int getCp()
	{
		return cp;
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
		if(name == "Armor Black"){
			Item it1 = new Item(3, "Armor Black", 4600, 30, "Armadura",false,"Images/Items/armor.jpeg");
			this.items.add(it1);
		}
		if(name == "Sword Red"){
			Item it2 = new Item(5, "Sword Red", 4600, 30, "Espada",false,"Images/Items/sword2.jpeg");
			this.items.add(it2);
		}
		if(name == "White Bomb"){
			Item it3 = new Item(5, "White Bomb", 3100, 35, "Bomba",false,"Images/Items/bomb.jpeg");
			this.items.add(it3);
		}
		if(name == "Black Bomb"){
			Item it4 = new Item(5, "Black Bomb", 6100, 60, "Bomba",false,"Images/Items/bomb.jpeg");
			this.items.add(it4);
		}
		if(name == "Red Bomb"){
			Item it5 = new Item(5, "Red Bomb", 9100, 90, "Bomba",false,"Images/Items/bomb.jpeg");
			this.items.add(it5);
		}		
	}
	
	public List<Item> getItems(){
		return this.items;
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
