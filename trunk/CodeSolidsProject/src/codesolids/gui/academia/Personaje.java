package codesolids.gui.academia;

import java.util.List;

import codesolids.gui.academia.Poder;

public class Personaje {
	 
	protected String login;
	protected int level;
	protected int gold;
	protected int xp;
	protected int hp;
	protected int cp;
	protected String tipo;
	protected String dirImage;
	protected boolean learning;
	protected List<Poder> poderes;
	
	public Personaje()
	{
		
	}
	
	public Personaje(String login, int level, int gold, int xp,int hp, int cp, String tipo, String dirImage, boolean learning, List<Poder> poderes){
		
		this.login = login;
		this.level = level;
		this.gold = gold;
		this.xp = xp;
		this.hp = hp;
		this.cp = cp;
		this.tipo = tipo;
		this.dirImage = dirImage;
		this.learning = learning;
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
	
	public void setPoderes(List<Poder> poderes){
		this.poderes = poderes;
	}
	
	public List<Poder> getPoderes()
	{
		return poderes;
	}
	
	public boolean getLearning()
	{
		return learning;
	}
	
	public void setLearning(boolean learning)
	{
		this.learning = learning;
	}
	
}

