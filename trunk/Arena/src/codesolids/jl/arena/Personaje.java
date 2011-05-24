package codesolids.jl.arena;

import java.util.ArrayList;
import java.util.List;

/**
 * @author = Jose Luis Perez M
 *
 * @Colaborador = Antonio Lopez 
 * 
 */

public class Personaje {

	protected String login;
	protected int level;
	protected int gold;
	protected int xp;
	protected int hp;
	protected int cp;		
	protected String dirImage;
	protected List<Poder> poderes;
	
	public Personaje(){
		login = "00";
		level = 0;
		gold = 0;
		xp = 0;
		hp = 0;
		cp = 0;
		dirImage = "";
		poderes = new ArrayList<Poder>();
	}
	
	public Personaje(String login, int level, int gold, int xp,int hp, int cp,String dirImage, List<Poder> poderes){
		
		this.login = login;
		this.level = level;
		this.gold = gold;
		this.xp = xp;
		this.hp = hp;
		this.cp = cp;
		this.dirImage = dirImage;
		this.poderes = poderes;
		
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
	
}
	