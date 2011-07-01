package codesolids.bd.inicializar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Poderes;
import codesolids.bd.hibernate.SessionHibernate;

/**
 * @author: Antonio López
 * 
 */

public class InicializarBD {


	public static void main(String[] args) {

		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Personaje player = new Personaje();
		player.setTipo("Fuego");
		player.setGold(10000);
		player.setLevel(8);

		session.save(player);

		PoderesBD initP = new PoderesBD();
		initP.createList();

		session.getTransaction().commit();
		session.close();

		System.err.println("**********************************");
		System.err.println("**	BASE DE DATOS INICIALIZADA	**");
		System.err.println("**********************************");

	}	
}

class PoderesBD
{
	
	private void AsignarDatos (int id,int level,String nombre,int oro,int dano,int tiemporeutilizacion,int tiempoEntrenamiento,int psinergia,String tipo,boolean uso,String dirImage, String descripcion)
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
		poder.setUso(uso);
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
		pBean.setUso(poder.getUso());
		pBean.setDirImage(poder.getDirImage());
		pBean.setDescripcion(poder.getDescripcion());
		
		session.save(pBean);
        
		session.getTransaction().commit();
		session.close();
	}
	
	public void createList()
	{
		AsignarDatos(1,1, "Bola de Fuego", 500, 30, 2, 0, 45, "Fuego",false,"Images/Poderes/Fuego/FireBall.png","Realiza una bola de fuego para golpear a tus enemigos.");
		AsignarDatos(2,1, "Explosión de Hielo", 500, 28, 2, 0, 35, "Hielo",false,"Images/Poderes/Hielo/WaterBurst.png","Genera una explosión de hielo.");
		AsignarDatos(3,1, "Rotura de la Tierra", 500, 26, 2, 0, 33, "Tierra",false,"Images/Poderes/Tierra/EarthSmash.png","Genera un enorme poder sobre el terreno.");
		AsignarDatos(4,1, "Puño del Dragón", 500, 27, 2, 0, 30, "Combo",false,"Images/Poderes/Combo/DragonFist.png","Realiza un puño estilo dragón al enemigo.");
		
		AsignarDatos(5,2, "Explosión de Fuego", 1000, 39, 3, 0, 58, "Fuego",false,"Images/Poderes/Fuego/FireExplosion.png","Lanza multiples explosiones de fuego.");
		AsignarDatos(6,2, "Bola de Agua", 1000, 36, 3, 0, 44, "Hielo",false,"Images/Poderes/Hielo/WaterBall.png","Realiza una bola de Agua para golpear a tus enemigos.");
		AsignarDatos(7,2, "Puño de Tierra", 1000, 33, 3, 0, 42, "Tierra",false,"Images/Poderes/Tierra/EarthFist.png","Transforma un puño en roca sólida.");
		AsignarDatos(8,2, "Impacto Mortal", 1000, 35, 3, 0, 38, "Combo",false,"Images/Poderes/Combo/HeadKick.png","Realiza un ataque combinando la espada y el puño.");
		
		AsignarDatos(9,3, "Rueda de Fuego", 1500, 51, 5, 0, 74, "Fuego",false,"Images/Poderes/Fuego/FireSpikeWheel.png","Elabora una rueda de fuego para arrollar a tus enemigos.");
		AsignarDatos(10,3, "Prisión de Hielo", 1500, 46, 5, 0, 56, "Hielo",false,"Images/Poderes/Hielo/WaterPrision.png","Forma una bola enorme de hielo para encarcelar a tus enemigos.");
		AsignarDatos(11,3, "Estragulamiento", 1500, 43, 5, 0, 53, "Tierra",false,"Images/Poderes/Tierra/EarthStrangle.png","Crea una mano de tierra y estrangula a tus enemigos.");
		AsignarDatos(12,3, "Golpe Cañon", 1500, 44, 5, 0, 49, "Combo",false,"Images/Poderes/Combo/CannonballStrike.png","Un super golpe seguido por una patada rapida.");
		
		AsignarDatos(13,4, "LLama de Fuego", 2100, 66, 7, 0, 94, "Fuego",false,"Images/Poderes/Fuego/FieryFlame.png","Lanza desde la boca flamas de fuego.");
		AsignarDatos(14,4, "Dragón de Agua", 2100, 59, 7, 0, 72, "Hielo",false,"Images/Poderes/Hielo/WaterDragonVortex.png","Realiza un dragón de agua para arrollar a tus enemigos.");
		AsignarDatos(15,4, "Muro de Tierra", 2100, 55, 7, 0, 68, "Tierra",false,"Images/Poderes/Tierra/EarthWall.png","Realiza una enorme pared de tierra sólida.");
		AsignarDatos(16,4, "Patada y Puño Veloz", 2100, 57, 7, 0, 63, "Combo",false,"Images/Poderes/Combo/SwiftKick.png","Realiza una patada y un puñetazo rapido a tus enemigos.");
		
		AsignarDatos(17,5, "Torbellino de Fuego", 2700, 86, 8, 1, 121, "Fuego",false,"Images/Poderes/Fuego/FireVortex.png","Lanza un torbellino de fuego al enemigo.");
		AsignarDatos(18,5, "Misil de Hielo", 2700, 76, 8, 1, 91, "Hielo",false,"Images/Poderes/Hielo/WaterMissile.png","Lanza un misil de Hielo.");
		AsignarDatos(19,5, "Golem de Barro", 2700, 70, 8, 1, 86, "Tierra",false,"Images/Poderes/Tierra/MudGolem.png","Invoca un Golem de barro combinando tierra y psinergia.");
		AsignarDatos(20,5, "Triple Puñetazo", 2700, 72, 8, 1, 81, "Combo",false,"Images/Poderes/Combo/ThreeCombatRapid.png","Ataca a tus enemigos con una combinación de puños.");
		
		AsignarDatos(21,6, "Misil de Fuego", 3300, 111, 10, 2, 155, "Fuego",false,"Images/Poderes/Fuego/FireMissile.png","Lanza un misil de fuego.");
		AsignarDatos(22,6, "Cañón de Hielo", 3300, 97, 10, 2, 116, "Hielo",false,"Images/Poderes/Hielo/WaterJetCannon.png","Lanza un cañon de hielo a tus enemigos.");
		AsignarDatos(23,6, "Estacas de Tierra", 3300, 89, 10, 2, 109, "Tierra",false,"Images/Poderes/Tierra/EarthSpike.png","Crea enormes estacas de tierra.");
		AsignarDatos(24,6, "Super Gancho Rapido", 3300, 93, 10, 2, 103, "Combo",false,"Images/Poderes/Combo/RapidUppercut.png","Golpea con un poderoso gancho que eleva al enemigo.");
		
		AsignarDatos(25,7, "Flecha de Fuego", 4000, 145, 10, 4, 198, "Fuego",false,"Images/Poderes/Fuego/FireArrow.png","Combina una daga y psinergia para crear una flecha de fuego.");
		AsignarDatos(26,7, "Estacas de Hielo", 4000, 124, 10, 4, 147, "Hielo",false,"Images/Poderes/Hielo/WaterImpale.png","Forma espinas de hielo para atacar al enemigo.");
		AsignarDatos(27,7, "Erosión del Suelo", 4000, 114, 10, 4, 138, "Tierra",false,"Images/Poderes/Tierra/EarthErosion.png","Absorbe los minerales del suelo para crear la erosión de la tierra.");
		AsignarDatos(28,7, "Cuatro Puñetazos Mortales", 4000, 119, 10, 4, 132, "Combo",false,"Images/Poderes/Combo/FourPalm.png","Concentra psinergia en sus manos y ataca al enemigo por cuatro veces.");
		
		AsignarDatos(29,8, "Fuego del Demonio", 4700, 188, 10, 6, 253, "Fuego",false,"Images/Poderes/Fuego/Hellfire.png","Causa daño y quema a tu enemigo.");
		AsignarDatos(30,8, "Renovación del Hielo", 4700, 159, 10, 6, 187, "Hielo",false,"Images/Poderes/Hielo/WaterConvergence.png","Regenera la fuerza del hielo y crea una enorme bola de hielo.");
		AsignarDatos(31,8, "Explosión de Tierra", 4700, 146, 10, 6, 176, "Tierra",false,"Images/Poderes/Tierra/EarthBlast.png","Concentra psinergia en el super puño de tierra.");
		AsignarDatos(32,8, "Mega Patada", 4700, 152, 10, 6, 169, "Combo",false,"Images/Poderes/Combo/AssaultKick.png","Realiza una patada poderosa dejando al enemigo confundido.");
		
		AsignarDatos(33,9, "Pilar de Fuego", 5400, 245, 10, 8, 324, "Fuego",false,"Images/Poderes/Fuego/PillarofFlame.png","Moldea la psinergia con fuego para crear un pilar de fuego.");
		AsignarDatos(34,9, "Doble Explosión de Hielo", 5400, 203, 10, 8, 237, "Hielo",false,"Images/Poderes/Hielo/DoubleWaterBurst.png","Realiza doble explosión de hielo y daña a tus enemigos.");
		AsignarDatos(35,9, "Explosión de Roca", 5400, 187, 10, 8, 223, "Tierra",false,"Images/Poderes/Tierra/EarthBoulder.png","Absorbe minerales del suelo y crea una roca.");
		AsignarDatos(36,9, "Patada Voladora", 5400, 195, 10, 8, 216, "Combo",false,"Images/Poderes/Combo/FlashSmash.png","Realiza una fuerte patada mortal que eleva a tus enemigos.");
		
		AsignarDatos(37,10, "Doble Bola de Fuego ", 10400, 318, 10, 10, 415, "Fuego",false,"Images/Poderes/Fuego/DoubleFireball.png","Crea dos bolas de fuego y quema a tu enemigo en ceniza.");
		AsignarDatos(38,10, "Lanza de Hielo", 10400, 260, 10, 10, 301, "Hielo",false,"Images/Poderes/Hielo/WaterSpear.png","Realiza estacas de hielos y atraviesa a tus enemigos.");
		AsignarDatos(39,10, "Doble Rotura de la Tierra", 10400, 240, 10, 10, 284, "Tierra",false,"Images/Poderes/Tierra/DoubleEarthSmash.png","Aplasta a tus enemigos con el poder de la tierra.");
		AsignarDatos(40,10, "Ráfaga Golpe Dragón", 10400, 249, 10, 10, 277, "Combo",false,"Images/Poderes/Combo/ThousandViolenceStrike.png","Realiza una ráfagas de golpes destructivos sin dudarlo.");
	}
	
}

