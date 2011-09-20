package codesolids.bd.inicializar;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.*;
import codesolids.bd.hibernate.SessionHibernate;

/**
 * @author: Antonio López
 * @Colaborador Fernando Osuna
 * @Colaborador Eduardo Granados
 */

public class InicializarBD {


	public static void main(String[] args) {

		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		PoderesBD initP = new PoderesBD();
		initP.createList();
		
		ItemsBD initI = new ItemsBD();
		initI.createList();
		initI.createListRecetas();
		
		EnemigosBD initE = new EnemigosBD();
		initE.createRegion();
		initE.createList();
		initE.createPoderes();

		NivelBD initN = new NivelBD();
		initN.createList();
		
		session.getTransaction().commit();
		session.close();

		System.err.println("**********************************");
		System.err.println("*   BASE DE DATOS INICIALIZADA   *");
		System.err.println("**********************************");

	}	
}

class PoderesBD
{
	
	private void AsignarDatos (int id,int level,String nombre,int oro,int dano,int tiemporeutilizacion,int tiempoEntrenamiento,int psinergia,String tipo,String dirImage, String descripcion)
	{
				
		Poderes poder = new Poderes();
		
		poder.setId(id);
		poder.setLevel(level);
		poder.setName(nombre);
		poder.setGold(oro);
		poder.setDamage(dano);
		poder.setCooldown(tiemporeutilizacion);
		poder.setTimeTraining(tiempoEntrenamiento);
		poder.setPsinergia(psinergia);
		poder.setTipo(tipo);
		poder.setDirImage(dirImage);
		poder.setDescripcion(descripcion);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Poderes pBean = new Poderes();
		pBean.setLevel(poder.getLevel());
		pBean.setName(poder.getName());
		pBean.setGold(poder.getGold());
		pBean.setDamage(poder.getDamage());
		pBean.setCooldown(poder.getCooldown());
		pBean.setTimeTraining(poder.getTimeTraining());
		pBean.setPsinergia(poder.getPsinergia());
		pBean.setTipo(poder.getTipo());
		pBean.setDirImage(poder.getDirImage());
		pBean.setDescripcion(poder.getDescripcion());
		
		session.save(pBean);
        
		session.getTransaction().commit();
		session.close();
	}
	
	public void createList()
	{
		AsignarDatos(1,1, "Bola de Fuego", 500, 30, 2, 0, 45, "Fuego","Images/Poderes/Fuego/FireBall.png","Realiza una bola de fuego para golpear a tus enemigos.");
		AsignarDatos(2,1, "Explosión de Hielo", 500, 28, 2, 0, 35, "Hielo","Images/Poderes/Hielo/WaterBurst.png","Genera una explosión de hielo.");
		AsignarDatos(3,1, "Rotura de la Tierra", 500, 26, 2, 0, 33, "Tierra","Images/Poderes/Tierra/EarthSmash.png","Genera un enorme poder sobre el terreno.");
		AsignarDatos(4,1, "Puño del Dragón", 500, 27, 2, 0, 30, "Guerrero","Images/Poderes/Guerrero/DragonFist.png","Realiza un puño estilo dragón al enemigo.");
		
		AsignarDatos(5,2, "Explosión de Fuego", 1000, 39, 3, 0, 58, "Fuego","Images/Poderes/Fuego/FireExplosion.png","Lanza multiples explosiones de fuego.");
		AsignarDatos(6,2, "Bola de Agua", 1000, 36, 3, 0, 44, "Hielo","Images/Poderes/Hielo/WaterBall.png","Realiza una bola de Agua para golpear a tus enemigos.");
		AsignarDatos(7,2, "Puño de Tierra", 1000, 33, 3, 0, 42, "Tierra","Images/Poderes/Tierra/EarthFist.png","Transforma un puño en roca sólida.");
		AsignarDatos(8,2, "Impacto Mortal", 1000, 35, 3, 0, 38, "Guerrero","Images/Poderes/Guerrero/HeadKick.png","Realiza un ataque combinando la espada y el puño.");
		
		AsignarDatos(9,3, "Rueda de Fuego", 1500, 51, 5, 0, 74, "Fuego","Images/Poderes/Fuego/FireSpikeWheel.png","Elabora una rueda de fuego para arrollar a tus enemigos.");
		AsignarDatos(10,3, "Prisión de Hielo", 1500, 46, 5, 0, 56, "Hielo","Images/Poderes/Hielo/WaterPrision.png","Forma una bola enorme de hielo para encarcelar a tus enemigos.");
		AsignarDatos(11,3, "Estragulamiento", 1500, 43, 5, 0, 53, "Tierra","Images/Poderes/Tierra/EarthStrangle.png","Crea una mano de tierra y estrangula a tus enemigos.");
		AsignarDatos(12,3, "Golpe Cañon", 1500, 44, 5, 0, 49, "Guerrero","Images/Poderes/Guerrero/CannonballStrike.png","Un super golpe seguido por una patada rapida.");
		
		AsignarDatos(13,4, "LLama de Fuego", 2100, 66, 7, 0, 94, "Fuego","Images/Poderes/Fuego/FieryFlame.png","Lanza desde la boca flamas de fuego.");
		AsignarDatos(14,4, "Dragón de Agua", 2100, 59, 7, 0, 72, "Hielo","Images/Poderes/Hielo/WaterDragonVortex.png","Realiza un dragón de agua para arrollar a tus enemigos.");
		AsignarDatos(15,4, "Muro de Tierra", 2100, 55, 7, 0, 68, "Tierra","Images/Poderes/Tierra/EarthWall.png","Realiza una enorme pared de tierra sólida.");
		AsignarDatos(16,4, "Patada y Puño Veloz", 2100, 57, 7, 0, 63, "Guerrero","Images/Poderes/Guerrero/SwiftKick.png","Realiza una patada y un puñetazo rapido a tus enemigos.");
		
		AsignarDatos(17,5, "Torbellino de Fuego", 2700, 86, 8, 1, 121, "Fuego","Images/Poderes/Fuego/FireVortex.png","Lanza un torbellino de fuego al enemigo.");
		AsignarDatos(18,5, "Misil de Hielo", 2700, 76, 8, 1, 91, "Hielo","Images/Poderes/Hielo/WaterMissile.png","Lanza un misil de Hielo.");
		AsignarDatos(19,5, "Golem de Barro", 2700, 70, 8, 1, 86, "Tierra","Images/Poderes/Tierra/MudGolem.png","Invoca un Golem de barro combinando tierra y psinergia.");
		AsignarDatos(20,5, "Triple Puñetazo", 2700, 72, 8, 1, 81, "Guerrero","Images/Poderes/Guerrero/ThreeCombatRapid.png","Ataca a tus enemigos con una combinación de puños.");
		
		AsignarDatos(21,6, "Misil de Fuego", 3300, 111, 10, 2, 155, "Fuego","Images/Poderes/Fuego/FireMissile.png","Lanza un misil de fuego.");
		AsignarDatos(22,6, "Cañón de Hielo", 3300, 97, 10, 2, 116, "Hielo","Images/Poderes/Hielo/WaterJetCannon.png","Lanza un cañon de hielo a tus enemigos.");
		AsignarDatos(23,6, "Estacas de Tierra", 3300, 89, 10, 2, 109, "Tierra","Images/Poderes/Tierra/EarthSpike.png","Crea enormes estacas de tierra.");
		AsignarDatos(24,6, "Super Gancho Rapido", 3300, 93, 10, 2, 103, "Guerrero","Images/Poderes/Guerrero/RapidUppercut.png","Golpea con un poderoso gancho que eleva al enemigo.");
		
		AsignarDatos(25,7, "Flecha de Fuego", 4000, 145, 10, 4, 198, "Fuego","Images/Poderes/Fuego/FireArrow.png","Combina una daga y psinergia para crear una flecha de fuego.");
		AsignarDatos(26,7, "Estacas de Hielo", 4000, 124, 10, 4, 147, "Hielo","Images/Poderes/Hielo/WaterImpale.png","Forma espinas de hielo para atacar al enemigo.");
		AsignarDatos(27,7, "Erosión del Suelo", 4000, 114, 10, 4, 138, "Tierra","Images/Poderes/Tierra/EarthErosion.png","Absorbe los minerales del suelo para crear la erosión de la tierra.");
		AsignarDatos(28,7, "Cuatro Puñetazos Mortales", 4000, 119, 10, 4, 132, "Guerrero","Images/Poderes/Guerrero/FourPalm.png","Concentra psinergia en sus manos y ataca al enemigo por cuatro veces.");
		
		AsignarDatos(29,8, "Fuego del Demonio", 4700, 188, 10, 6, 253, "Fuego","Images/Poderes/Fuego/Hellfire.png","Causa daño y quema a tu enemigo.");
		AsignarDatos(30,8, "Renovación del Hielo", 4700, 159, 10, 6, 187, "Hielo","Images/Poderes/Hielo/WaterConvergence.png","Regenera la fuerza del hielo y crea una enorme bola de hielo.");
		AsignarDatos(31,8, "Explosión de Tierra", 4700, 146, 10, 6, 176, "Tierra","Images/Poderes/Tierra/EarthBlast.png","Concentra psinergia en el super puño de tierra.");
		AsignarDatos(32,8, "Mega Patada", 4700, 152, 10, 6, 169, "Guerrero","Images/Poderes/Guerrero/AssaultKick.png","Realiza una patada poderosa dejando al enemigo confundido.");
		
		AsignarDatos(33,9, "Pilar de Fuego", 5400, 245, 10, 8, 324, "Fuego","Images/Poderes/Fuego/PillarofFlame.png","Moldea la psinergia con fuego para crear un pilar de fuego.");
		AsignarDatos(34,9, "Doble Explosión de Hielo", 5400, 203, 10, 8, 237, "Hielo","Images/Poderes/Hielo/DoubleWaterBurst.png","Realiza doble explosión de hielo y daña a tus enemigos.");
		AsignarDatos(35,9, "Explosión de Roca", 5400, 187, 10, 8, 223, "Tierra","Images/Poderes/Tierra/EarthBoulder.png","Absorbe minerales del suelo y crea una roca.");
		AsignarDatos(36,9, "Patada Voladora", 5400, 195, 10, 8, 216, "Guerrero","Images/Poderes/Guerrero/FlashSmash.png","Realiza una fuerte patada mortal que eleva a tus enemigos.");
		
		AsignarDatos(37,10, "Doble Bola de Fuego ", 10400, 318, 10, 10, 415, "Fuego","Images/Poderes/Fuego/DoubleFireball.png","Crea dos bolas de fuego y quema a tu enemigo en ceniza.");
		AsignarDatos(38,10, "Lanza de Hielo", 10400, 260, 10, 10, 301, "Hielo","Images/Poderes/Hielo/WaterSpear.png","Realiza estacas de hielos y atraviesa a tus enemigos.");
		AsignarDatos(39,10, "Doble Rotura de la Tierra", 10400, 240, 10, 10, 284, "Tierra","Images/Poderes/Tierra/DoubleEarthSmash.png","Aplasta a tus enemigos con el poder de la tierra.");
		AsignarDatos(40,10, "Ráfaga Golpe Dragón", 10400, 249, 10, 10, 277, "Guerrero","Images/Poderes/Guerrero/ThousandViolenceStrike.png","Realiza una ráfagas de golpes destructivos sin dudarlo.");
	}
	
}

class ItemsBD{
	
	private void AsignarDatos(int id, int level, String nombre, int precio, int indice, String tipo, String descripcion, boolean uso, String dirImage, boolean inShop){
		Item item = new Item();
		
		item.setId(id);
		item.setLevel(level);
		item.setName(nombre);
		item.setPrice(precio);
		item.setIndex(indice);
		item.setTipo(tipo);
		item.setDescripcion(descripcion);
		item.setUso(uso);
		item.setDirImage(dirImage);
		item.setInshop(inShop);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Item iBean = new Item();
		iBean.setLevel(item.getLevel());
		iBean.setName(item.getName());
		iBean.setPrice(item.getPrice());
		iBean.setIndex(item.getIndex());
		iBean.setTipo(item.getTipo());
		iBean.setUso(item.isUso());
		iBean.setDirImage(item.getDirImage());
		iBean.setDescripcion(item.getDescripcion());
		iBean.setInshop(item.isInshop());
		session.save(iBean);
        
		session.getTransaction().commit();
		session.close();		
	}
	
	public void createList(){
		
		AsignarDatos(1, 1, "Armadura", 1500, 5, "Armadura", "Armadura Básica",false,"Images/Items/armor.png", true);
		AsignarDatos(2, 1, "Daga", 300, 10, "Espada", "Puñal pequeño",false,"Images/Items/daga.png", true);
		
		AsignarDatos(3, 4, "Piedra Blanca", 50, 10, "Piedra", "Piedra mágica de color Blanco",false,"Images/Items/stone1.png", true);
		AsignarDatos(4, 2, "Medicina Basica", 800, 20, "Medicina", "Medicina  básica para aumentar la vida",false,"Images/Items/potion1.png", true);
		
		AsignarDatos(5, 3, "Piedra Negra", 75, 25, "Piedra", "Piedra mágica de color Negro",false,"Images/Items/stone2.png", true);
		AsignarDatos(6, 8, "Armadura Negra", 2500, 10, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png", true);
		AsignarDatos(7, 3, "Bomba", 500, 70, "Bomba", "Bomba común",false,"Images/Items/bomb.png", true);
		AsignarDatos(8, 3, "Espada", 1500, 150, "Espada", "Espada Básica",false,"Images/Items/sword.png", true);
		
		AsignarDatos(9, 4, "Energia Media", 900, 20, "Pocion", "Pocion para aumentar la psinergia",false,"Images/Items/potion2.png", true);
		AsignarDatos(10, 4, "Medicina Media", 1260, 40, "Medicina", "Medicina de media categoría para aumentar la vida",false,"Images/Items/potion1.png", true);
		
		AsignarDatos(11, 5, "Piedra Roja", 100, 35, "Piedra", "Piedra mágica de color Rojo",false,"Images/Items/stone3.png", true);
		AsignarDatos(12, 8, "Espada Roja", 3500, 350, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png", true);
		
		AsignarDatos(13, 4, "Bomba Blanca", 700, 100, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb1.png", true);
		AsignarDatos(14, 4, "Bomba Negra", 800, 130, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png", true);
		
		AsignarDatos(15, 7, "Bomba Roja", 1000, 200, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png", true);
		AsignarDatos(16, 7, "Energia Super", 2350, 40, "Pocion", "Pocion para aumentar mucho más la psinergia",false,"Images/Items/potion2.png", true);
		
		AsignarDatos(18, 15, "Armadura Dorada", 3000, 15, "Armadura", "Poderosa armadura hecha de oro y plata",false,"Images/Items/armor3.png", false);
		AsignarDatos(19, 15, "Espada Platino", 3000, 700, "Espada", "Poderosa espada",false,"Images/Items/sword3.png", false);
		
		AsignarDatos(17, 9, "Medicina Super", 2400, 80, "Medicina", "Poderosa Medicina para aumentar la vida",false,"Images/Items/potion1.png", true);
	
	}
	
	private void asignarReceta(int r,int n, int b, String descripcion, String crear, String reagent){
	
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteriaItem; 		
		Item item = new Item();
		Item item2 = new Item();

		Receta receta = new Receta();
	
		receta.setCantRojas(r);
		receta.setCantNegras(n);
		receta.setCantBlancas(b);
		receta.setDescripcion(descripcion);

		criteriaItem = session.createCriteria(Item.class).add(Restrictions.eq("name", crear));
		item = (Item) criteriaItem.uniqueResult();
		criteriaItem = session.createCriteria(Item.class).add(Restrictions.eq("name", reagent));
		item2 = (Item) criteriaItem.uniqueResult();
		
		item.setReceta(receta);
		item2.getIsReagent().add(receta);
		receta.setReagent(item2);
		receta.setItemCrear(item);
		
		session.save(receta);
		session.save(item);
		session.save(item2);
		
		session.getTransaction().commit();
		session.close();
		
	}
	public void createListRecetas(){
		
		asignarReceta(11,15,12,"Para crear Armadura Dorada", "Armadura Dorada", "Armadura Negra");
		asignarReceta(15,13,11,"Para crear Espada Platino", "Espada Platino", "Espada Roja");
		asignarReceta(0,0,3,"Para crear Bomba Blanca", "Bomba Blanca", "Bomba");
		asignarReceta(0,3,0,"Para crear Bomba Negra", "Bomba Negra", "Bomba");
		asignarReceta(3,0,0,"Para crear Bomba Roja", "Bomba Roja", "Bomba");
		asignarReceta(2,2,2,"Para crear Medicina Media", "Medicina Media", "Medicina Basica");
		asignarReceta(5,5,5,"Para crear Energia Super", "Energia Super", "Energia Media");
		asignarReceta(5,5,5,"Para crear Medicina Super", "Medicina Super", "Medicina Media");

	}
	
}

class EnemigosBD{	
	
	private void AsignarRegion(String nombre, String descripcion, String dirImage){
		Region region = new Region();
		
		region.setNombre(nombre);
		region.setDescripcion(descripcion);
		region.setDirImage(dirImage);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Region rBean = new Region();
		
		rBean.setNombre(region.getNombre());
		rBean.setDescripcion(region.getDescripcion());
		rBean.setDirImage(region.getDirImage());
		
		session.save(rBean);
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void createRegion(){
		AsignarRegion("Monte Aleph", "Aqui se encuentran enemigos como elfos y criaturas salvajes: minotauro, ogros, dragones, etc.", "Images/Enemigos/montealeph.png");
		AsignarRegion("Norte Blanco", "Aqui se encuentran enemigos como Osos, Lobos, dragones de hielo", "Images/Enemigos/norteblanco.png");
		AsignarRegion("Gloriosa", "Aqui se encuentran enemigos como enanos guerreros, orcos y personajes de este tipo.", "Images/Enemigos/gloriosa.png");
		AsignarRegion("Pantano Cocona", "Aqui se encuentran enemigos poderosos, demonios y criaturas extrañas", "Images/Enemigos/pantano.png");
		AsignarRegion("Ciudad Programacion", "Esta es la region más poderos de todas, aqui los enemigos son guerreros y hechiceros muy fuertes", "Images/Enemigos/programacion.png");
		
	}
	
	private void AsignarDatos(int id, int level, String nombre, int vida, int xp, int oro, int velocidad, String region, String dirImage){
		Enemigo enemigo = new Enemigo();
		
		enemigo.setId(id);
		enemigo.setNivel(level);
		enemigo.setNombre(nombre);
		enemigo.setVida(vida);
		enemigo.setOro(oro);
		enemigo.setXp(xp);
		enemigo.setVelocidad(velocidad);
		enemigo.setDirImage(dirImage);
		
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Enemigo iBean = new Enemigo();
		Region rBean = new Region();
		
		rBean.setNombre(region);
		Criteria criteria = session.createCriteria(Region.class).add(Restrictions.eq("nombre", rBean.getNombre()));
		rBean = (Region) criteria.uniqueResult();
		
		iBean.setNivel(enemigo.getNivel());
		iBean.setNombre(enemigo.getNombre());
		iBean.setOro(enemigo.getOro());
		iBean.setVida(enemigo.getVida());
		iBean.setDirImage(enemigo.getDirImage());
		iBean.setXp(enemigo.getXp());
		iBean.setVelocidad(enemigo.getVelocidad());
		iBean.setRegionRef(rBean);
		
		session.save(iBean);
        
		session.getTransaction().commit();
		session.close();

	}
	
	public void createList(){
		AsignarDatos(1, 1, "Minotauro", 110, 10, 150, 50, "Monte Aleph", "Images/Enemigos/minotauro.png");
		AsignarDatos(2, 1, "Ogro", 100, 11, 170, 30, "Monte Aleph", "Images/Enemigos/ogro.png");
		AsignarDatos(3, 2, "Dragon Negro", 120, 15, 190, 80, "Monte Aleph", "Images/Enemigos/dragon_negro.png");
		AsignarDatos(4, 3, "Dragon Ojo Negro", 190, 20, 190, 50, "Monte Aleph", "Images/Enemigos/dragon_ojosnegros.png");
		AsignarDatos(8, 4, "Elfo", 350, 40, 410, 120, "Monte Aleph", "Images/Enemigos/elfo.png");
		AsignarDatos(5, 5, "Dragon Gris", 350, 30, 250, 50, "Monte Aleph", "Images/Enemigos/dragon_gris.png");
		AsignarDatos(6, 6, "3 Cabezas", 500, 30, 450, 60, "Monte Aleph", "Images/Enemigos/dragon3cabezas.png");
		
		AsignarDatos(7, 4, "Dragon Blanco", 400, 19, 150, 50, "Norte Blanco", "Images/Enemigos/dragon_hielo.png");
		AsignarDatos(22, 3, "Guerrero", 300, 40, 410, 120, "Norte Blanco", "Images/Enemigos/metal.png");
		AsignarDatos(23, 3, "Lobo Gris", 300, 50, 410, 120, "Norte Blanco", "Images/Enemigos/lobo1.png");
		AsignarDatos(24, 7, "Lobo Negro", 850, 100, 500, 220, "Norte Blanco", "Images/Enemigos/lobo2.png");
		
		AsignarDatos(9, 2, "Enano Arquero", 180, 30, 160, 20, "Gloriosa", "Images/Enemigos/enano1.png");
		AsignarDatos(10, 5, "Enano Dorado", 390, 20, 220, 25, "Gloriosa", "Images/Enemigos/enano2.png");
		AsignarDatos(11, 1, "Enano Gris", 100, 10, 100, 20, "Gloriosa", "Images/Enemigos/enano3.png");
		AsignarDatos(12, 7, "Lider Orco", 800, 100, 550, 80, "Gloriosa", "Images/Enemigos/lider_orco.png");
		AsignarDatos(13, 6, "Orco", 400, 80, 220, 50, "Gloriosa", "Images/Enemigos/orco2.png");
		AsignarDatos(14, 5, "Orco Verde", 100, 60, 200, 10, "Gloriosa", "Images/Enemigos/orco3d.png");
		
		AsignarDatos(16, 5, "Golem", 450, 60, 400, 200, "Pantano Cocona", "Images/Enemigos/golem.png");
		AsignarDatos(15, 4, "Chogall", 390, 10, 150, 100, "Pantano Cocona", "Images/Enemigos/chogall.png");
		AsignarDatos(16, 9, "Demonio", 2000, 60, 400, 200, "Pantano Cocona", "Images/Enemigos/demon.png");
		
		AsignarDatos(17, 1, "Program1", 400, 190, 1000, 200, "Ciudad Programacion", "Images/Enemigos/program1.png");
		AsignarDatos(18, 3, "Program2", 450, 200, 1000, 250, "Ciudad Programacion", "Images/Enemigos/program2.png");
		AsignarDatos(19, 4, "Program3", 1000, 300, 1500, 350, "Ciudad Programacion", "Images/Enemigos/program3.png");
		AsignarDatos(20, 5, "Program4", 2000, 440, 1500, 350, "Ciudad Programacion", "Images/Enemigos/program4.png");
		AsignarDatos(21, 5, "Program5", 3000, 900, 10000, 350, "Ciudad Programacion", "Images/Enemigos/program5.png");
	
	}	
	
	private void AsignarPoder(String id, String nombre, int indice){
		PoderEnemigo pe = new PoderEnemigo();
		
		pe.setNombre(nombre);
		pe.setIndice(indice);
		
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		PoderEnemigo peBean = new PoderEnemigo();
		Enemigo eBean = new Enemigo();
		eBean.setNombre(id);
		
		Criteria criteria = session.createCriteria(Enemigo.class).add(Restrictions.eq("nombre", eBean.getNombre()));
		eBean = (Enemigo) criteria.uniqueResult();
		
		peBean.setNombre(pe.getNombre());
		peBean.setIndice(pe.getIndice());
		
		peBean.setEnemigoRef(eBean);
        
        session.save(peBean);
		session.getTransaction().commit();
		session.close();		
	}
	
	public void createPoderes(){
		AsignarPoder("Minotauro", "Directo", 10);
		AsignarPoder("Minotauro", "Rapido", 20);
		AsignarPoder("Minotauro", "Del Conio", 25);
		
		AsignarPoder("Ogro", "Garrote", 25);
		AsignarPoder("Ogro", "Doble Garrote", 30);
		AsignarPoder("Ogro", "Furia", 20);
		
		AsignarPoder("Dragon Negro", "Aereo", 30);
		AsignarPoder("Dragon Negro", "Mordida", 20);
		AsignarPoder("Dragon Negro", "Golpe de Cola", 40);
		
		AsignarPoder("Dragon OjoNegro", "Directo", 40);
		AsignarPoder("Dragon OjoNegro", "Aliento Dragon", 50);
		AsignarPoder("Dragon OjoNegro", "Dagas", 55);
		
		AsignarPoder("Dragon Gris", "Aereo", 60);
		AsignarPoder("Dragon Gris", "Furia de Fuego", 80);
		AsignarPoder("Dragon Gris", "Infernal", 100);
		
		AsignarPoder("3 Cabezas", "Triple Mordida", 60);
		AsignarPoder("3 Cabezas", "Lluvia de Fuego", 150);
		AsignarPoder("3 Cabezas", "Directo", 300);
		
		AsignarPoder("Dragon Blanco", "Rayo de Hielo", 60);
		AsignarPoder("Dragon Blanco", "Mordida", 70);
		AsignarPoder("Dragon Blanco", "Directo", 80);
		
		AsignarPoder("Lobo Gris", "Rapido", 60);
		AsignarPoder("Lobo Gris", "Furia", 80);
		AsignarPoder("Lobo Gris", "Mordida", 120);
		
		AsignarPoder("Lobo Negro", "Furia", 100);
		AsignarPoder("Lobo Negro", "Oscuro", 250);
		AsignarPoder("Lobo Negro", "Maligno", 500);
		
		AsignarPoder("Elfo", "Golpe Invisible", 60);
		AsignarPoder("Elfo", "Puñal", 80);
		AsignarPoder("Elfo", "Combo", 120);
		
		AsignarPoder("Guerrero", "Fuerte", 60);
		AsignarPoder("Guerrero", "Rapido", 80);
		AsignarPoder("Guerrero", "Combo", 120);
		
		AsignarPoder("Enano Arquero", "Disparo", 40);
		AsignarPoder("Enano Arquero", "Directo", 25);
		AsignarPoder("Enano Arquero", "Rafaga", 35);
		
		AsignarPoder("Enano Dorado", "Directo", 85);
		AsignarPoder("Enano Dorado", "Puñal", 80);
		AsignarPoder("Enano Dorado", "Maldicion", 190);
		
		AsignarPoder("Enano Gris", "Puño", 5);
		AsignarPoder("Enano Gris", "Directo", 15);
		AsignarPoder("Enano Gris", "Directo Doble", 25);
		
		AsignarPoder("Lider Orco", "Maldicion Orco", 300);
		AsignarPoder("Lider Orco", "Garrote", 150);
		AsignarPoder("Lider Orco", "Bomba", 250);
		
		AsignarPoder("Orco", "Directo", 200);
		AsignarPoder("Orco", "Combo", 190);
		AsignarPoder("Orco", "Puñal", 250);
		
		AsignarPoder("Orco Verde", "Furia", 150);
		AsignarPoder("Orco Verde", "Directo", 200);
		AsignarPoder("Orco Verde", "Garrote Doble", 250);
		
		AsignarPoder("Chogall", "Fisura", 200);
		AsignarPoder("Chogall", "Espinas", 190);
		AsignarPoder("Chogall", "Del Conio", 300);
		
		AsignarPoder("Golem", "Fisura", 200);
		AsignarPoder("Golem", "Lava Ardiente", 190);
		AsignarPoder("Golem", "Terremoto", 300);
		
		AsignarPoder("Demonio", "Maligno", 1000);
		AsignarPoder("Demonio", "Invisible", 800);
		AsignarPoder("Demonio", "Directo", 500);
		
		AsignarPoder("Program1", "Java", 100);
		AsignarPoder("Program1", "Echo", 200);
		AsignarPoder("Program1", "Eclipse", 300);		
		
		AsignarPoder("Program2", "Hibernate", 200);
		AsignarPoder("Program2", "Directo", 300);
		AsignarPoder("Program2", "Directo", 500);
		
		AsignarPoder("Program3", "1", 300);
		AsignarPoder("Program3", "2", 300);
		AsignarPoder("Program3", "3", 400);
		
		AsignarPoder("Program4", "1", 1000);
		AsignarPoder("Program4", "2", 2000);
		AsignarPoder("Program4", "3", 3000);
		
		AsignarPoder("Program5", "1", 800);
		AsignarPoder("Program5", "2", 2500);
		AsignarPoder("Program5", "3", 3000);	
	}
}
	
class NivelBD
{

	
	private void AsignarDatos (int level,int exp)
	{

		Nivel nivel = new Nivel();

		nivel.setLevel(level);
		nivel.setCantidadExp(exp);

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		Nivel nBean = new Nivel();
		nBean.setLevel(nivel.getLevel());
		nBean.setCantidadExp(nivel.getCantidadExp());

		session.save(nBean);

		session.getTransaction().commit();
		session.close();
	}

	public void createList()
	{

		AsignarDatos(2,200);
		AsignarDatos(3,400);
		AsignarDatos(4,800);
		AsignarDatos(5,1200);
		AsignarDatos(6,2000);
		AsignarDatos(7,2800);
		AsignarDatos(8,3800);
		AsignarDatos(9,4800);
		AsignarDatos(10,6800);

		AsignarDatos(11,8800);
		AsignarDatos(12,10600);
		AsignarDatos(13,13400);
		AsignarDatos(14,17200);
		AsignarDatos(15,21000);
		AsignarDatos(16,25800);
		AsignarDatos(17,30600);
		AsignarDatos(18,37400);
		AsignarDatos(19,44200);
		AsignarDatos(20,53000);

	}
}


