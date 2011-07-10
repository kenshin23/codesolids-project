package codesolids.gui.perfil;

import java.util.ArrayList;
import java.util.List;

import codesolids.gui.academia.Poder;
import codesolids.gui.tienda.Item;
import codesolids.gui.tienda.Personaje;

/**
 * 
 * @author Fernando Osuna
 *
 */

public class User {
	
	private String login;
	private List<Personaje> players = new ArrayList<Personaje>();
	
	private Personaje player;
	private List<Item> items = new ArrayList<Item>();
	private List<Poder> poderes = new ArrayList<Poder>();
	//private Item item;
	private Poder poder;
	
	public User(){
		poder = new Poder(1,1, "Bola de Fuego", 500, 30, 2, 45, "Fuego",false,"Images/Poderes/Fuego/FireBall.png", 0);
		poderes.add(poder);
		poder = new Poder(5,2, "Explosión de Fuego", 1000, 39, 3, 58, "Fuego",false,"Images/Poderes/Fuego/FireExplosion.png", 0);
		poderes.add(poder);
		poder = new Poder(9,3, "Rueda de Fuego", 1500, 51, 5, 74, "Fuego",false,"Images/Poderes/Fuego/FireSpikeWheel.png", 0);
		poderes.add(poder);
		
		player = new Personaje("player1", 9, 1000, 30000, 5000, 2050, 50, 40, 70, "Fuego", "Images/Personajes/MagoF.png", poderes, items);
		player.buildItem("Armadura Negra");
		player.buildItem("Espada Roja");
		player.buildItem("Bomba Blanca");
		player.buildItem("Bomba Blanca");
		player.buildItem("Bomba Negra");
		
		players.add(player);
		
		items = new ArrayList<Item>();
		poderes = new ArrayList<Poder>();
		
		poder = new Poder(3,1, "Rotura de la Tierra", 500, 26, 2, 33, "Tierra",false,"Images/Poderes/Tierra/EarthSmash.png", 0);
		poderes.add(poder);
		poder = new Poder(31,8, "Explosión de Tierra", 4700, 146, 10, 176, "Tierra",false,"Images/Poderes/Tierra/EarthBlast.png", 6);
		poderes.add(poder);
		
		player = new Personaje("player2", 3, 1100, 25000, 5000, 2000, 50, 40, 60, "Tierra", "Images/Personajes/MagoT.png", poderes, items);
		player.buildItem("Armadura Negra");
		player.buildItem("Bomba Blanca");
		player.buildItem("Bomba Negra");
		player.buildItem("Bomba Negra");
		player.buildItem("Bomba Negra");
		player.buildItem("Bomba Negra");
		
		players.add(player);
	}
	
	public User(String login, List<Personaje> players){
		this.login = login;
		this.players = players;		
	}
	
	public void setLogin( String login){
		this.login = login;
	}
	
	public String getLogin(){
		return login;
	}
	
	public List<Personaje> getPlayers(){
		return this.players;
	}

}
