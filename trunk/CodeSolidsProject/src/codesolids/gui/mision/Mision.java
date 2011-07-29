package codesolids.gui.mision;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.informagen.echo.app.CapacityBar;

import codesolids.bd.clases.Enemigo;
import codesolids.bd.clases.Item;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.bd.clases.PoderEnemigo;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.gui.tienda.ImageReferenceCache;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;


/**
 * @author Fernando Osuna
 * @author Jose Luis Perez M
 * @author Antonio Lopez 
 * 
 */
@SuppressWarnings("serial")
public class Mision extends ContentPane{
	private Usuario usuario;
	private Personaje personaje;

	private Enemigo enemigo;
	
	Panel region;
	
	private Column col;
	private Column colA;
	private Label labelCp;
	private Label lblAttack1;
	private Label lblAttack2;
	
	private HtmlLayout htmlLayout;
	
	private Button btnAttack1;
	
	private Button btnHit;
	private Button btnLoad;

	private CapacityBar barraVida1;
	private CapacityBar barraVida2;
	private CapacityBar barraPsinergia;
	private CapacityBar barraXp;

	private List<Number> listNumber;

	private boolean flag=false;
	
	public Mision(){
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();	
		usuario = app.getUsuario();	
		personaje = app.getPersonaje();
	    initGUI();
	}
	
	private void initGUI() {
		add(initRegion());
	}
	
	public Component initRegion(){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("mision.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("batalla");
		
		Grid grid = new Grid(1);
		Button btnRegion1 = new Button("Monte Aleph");
		btnRegion1.setStyle(Styles1.DEFAULT_STYLE);
		btnRegion1.setToolTipText("Ir a esta region");
		btnRegion1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initEnemigos("Monte Aleph"));
			}
		});
		grid.add(btnRegion1);
		
		Button btnRegion2 = new Button("Norte Blanco");
		btnRegion2.setStyle(Styles1.DEFAULT_STYLE);
		btnRegion2.setToolTipText("Ir a esta region");
		btnRegion2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initEnemigos("Norte Blanco"));
			}
		});
		grid.add(btnRegion2);
		
		Button btnRegion3 = new Button("Gloriosa");
		btnRegion3.setStyle(Styles1.DEFAULT_STYLE);
		btnRegion3.setToolTipText("Ir a esta region");
		btnRegion3.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initEnemigos("Gloriosa"));
			}
		});
		grid.add(btnRegion3);
		
		Button btnRegion4 = new Button("Pantano Cocona");
		btnRegion4.setStyle(Styles1.DEFAULT_STYLE);
		btnRegion4.setToolTipText("Ir a esta region");
		btnRegion4.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initEnemigos("Pantano Cocona"));
			}
		});
		grid.add(btnRegion4);
		
		Button btnRegion5 = new Button("Ciudad Programacion");
		btnRegion5.setStyle(Styles1.DEFAULT_STYLE);
		btnRegion5.setToolTipText("Ir a esta region");
		btnRegion5.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initEnemigos("Ciudad Programacion"));
			}
		});
		grid.add(btnRegion5);
		
		Button returnButton = new Button();		
		returnButton = new Button();
		returnButton.setText("Salir");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al mapa");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonExitClicked(e);				
			}
		});
		
		grid.add(returnButton);
		
		grid.setLayoutData(hld);
		htmlLayout.setBackground(Color.BLACK);
		htmlLayout.add(grid);		
		
		return htmlLayout;
	}
	
	private Component initEnemigos(String nombreR){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("region.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");		
		Row menu = new Row();
		
		Button returnButton = new Button();		
		returnButton = new Button();
		returnButton.setText("Salir");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al mapa");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonExitClicked(e);				
			}
		});
		menu.add(returnButton);
		returnButton = new Button();		
		returnButton = new Button();
		returnButton.setText("Regiones");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar a las regiones");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			removeAll();
			add(initRegion());
			}
		});
		menu.add(returnButton);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);
		
		hld = new HtmlLayoutData("batalla");
		
		Row rowCentral = new Row();
		region = new Panel();
		
		region.add(btnEnemigos(nombreR));
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Enemigos/cartel4.png");
		FillImage imgF = new FillImage(imgR);
		region.setWidth(new Extent(1180));
		region.setHeight(new Extent(480));
		region.setBackgroundImage(imgF);
		region.setInsets(new Insets(20, 20, 0, 0));
		rowCentral.add(region);
		rowCentral.setLayoutData(hld);
		htmlLayout.setBackground(Color.BLACK);
		htmlLayout.add(rowCentral);		
		
		return htmlLayout;
	}
	
	public Grid btnEnemigos( String nombreR){

		Label lbl = new Label();

		Grid grid = new Grid(3);
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Enemigo> list = session.createCriteria(Enemigo.class).add(Restrictions.eq("region", nombreR)).addOrder(Order.asc("nivel")).list();
	    session.getTransaction().commit();
	    session.close();
		for (final Enemigo obj : list) {
			Panel panel = new Panel();
			Row rowTab = new Row();
			rowTab.setInsets(new Insets(20, 35, 20, 20));
			Column colTab = new Column();
			colTab.setCellSpacing(new Extent(4));
			Column col = new Column();
			col.setCellSpacing(new Extent(9));
			
		    ImageReference ir = ImageReferenceCache.getInstance().getImageReference(obj.getDirImage());
	        ImageIcon imgI = new ImageIcon(ir);
	        imgI.setWidth(new Extent(150));
	        imgI.setHeight(new Extent(150));
			
			lbl = new Label("Mision "+list.indexOf(obj));
			colTab.add(lbl);
			lbl = new Label("Region : "+obj.getRegion());
			colTab.add(lbl);
			lbl = new Label("Enemigo : "+obj.getNombre());
			colTab.add(lbl);
			lbl = new Label("Nivel : "+obj.getNivel());
			colTab.add(lbl);
			lbl = new Label("Recompensa : "+obj.getOro());
			colTab.add(lbl);
			Button btnItem = new Button();
			btnItem.setText("Aceptar");
			btnItem.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			btnItem.setHeight(new Extent(15));
			btnItem.setWidth( new Extent(60));
			btnItem.setToolTipText("Hacer esta mision");
			btnItem.setStyle(Styles1.DEFAULT_STYLE);
			btnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
						
				enemigo = (Enemigo) session.load(Enemigo.class, obj.getId());
			    
				session.getTransaction().commit();
			    session.close();
				removeAll();
				add(initBatalla());
				
				}
			});
			colTab.add(btnItem);
			
			rowTab.add(imgI);
			rowTab.setCellSpacing(new Extent(15));
			rowTab.add(colTab);
			panel.add(rowTab);
			grid.add(panel);
		}
		return grid;
	}
	
	public Component initBatalla(){

		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("mision.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		HtmlLayoutData hld;
		hld = new HtmlLayoutData("batalla");

		col = new Column();
		colA = new Column();
		col.setCellSpacing(new Extent(5));
		
		crearPersonaje();

		Column colEstado = new Column();
		Row rowEstado = new Row();

		Panel panelAtaque = new Panel();
		Panel panelEstado = new Panel();
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/p_ataque.png");
		FillImage imgF = new FillImage(imgR);
		panelAtaque.setWidth(new Extent(120));
		panelAtaque.setHeight(new Extent(70));
		panelAtaque.setBackgroundImage(imgF);
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/p_estadoBattle.png");
		imgF = new FillImage(imgR);
		panelEstado.setWidth(new Extent(260));
		panelEstado.setHeight(new Extent(104));
		panelEstado.setBackgroundImage(imgF);
		panelEstado.setInsets(new Insets(10,25,0,0));
		panelAtaque.setInsets(new Insets(40,30,30,30));
		panelEstado.add(colEstado);
		panelAtaque.add(colA);
		panelAtaque.setAlignment(Alignment.ALIGN_CENTER);

		col.add(rowEstado);
		rowEstado.add(panelEstado);
		rowEstado.setCellSpacing(new Extent(150));
		rowEstado.add(panelAtaque);
		colEstado.add(new Label(personaje.getUsuarioRef().getLogin()));

		Row row = new Row();
		row.setCellSpacing(new Extent(50));
		row.add(new Label("Lv. "+ personaje.getLevel()));
		row.add(new Label("Gold "+ personaje.getGold()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("XP"));
		barraXp = crearBarraVida1(Color.GREEN,Color.WHITE,personaje.getXp(), consultXp(personaje.getLevel()) - personaje.getXp());
		row.add(barraXp);
		row.add(new Label(consultXp(personaje.getLevel())+"/"+personaje.getXp()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("HP"));
		barraVida1 = crearBarraVida1(Color.RED,Color.WHITE,personaje.getHp(),0);
		row.add(barraVida1);
		row.add(new Label(personaje.getHp()+"/"+barraVida1.getValues().get(0).intValue()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("MP"));
		barraPsinergia = crearBarraVida1(Color.BLUE,Color.WHITE,personaje.getMp(),0);
		row.add(barraPsinergia);
		labelCp = new Label();
		labelCp.setText(personaje.getMp()+"/"+barraPsinergia.getValues().get(0).intValue());
		row.add(labelCp);
		colEstado.add(row);

		ImageReference mA = new ResourceImageReference(personaje.getDirImage());

		ImageReference mB = new ResourceImageReference(enemigo.getDirImage());

		Label magoa = new Label(mA);
		Label magob = new Label(mB);

		Row rowM = new Row();
		rowM.setCellSpacing(new Extent(200, Extent.PX));
		rowM.add(magoa);
		rowM.add(magob);

		col.add(rowM);

		Row rowA = new Row();
		rowA.setCellSpacing(new Extent(5));
		rowA.add(new Label("Lv "+ personaje.getLevel()));
		rowA.add(barraVida1);

		Row rowB = new Row();
		rowB.setCellSpacing(new Extent(5));
		rowB.add(new Label("Lv "+ enemigo.getNivel()));
		barraVida2 = crearBarraVida1(Color.RED,Color.WHITE,enemigo.getVida(),0);
		rowB.add(barraVida2);

		row = new Row();
		row.setCellSpacing(new Extent(205));
		row.add(rowA);
		row.add(rowB);
		lblAttack1 = new Label();
		lblAttack2 = new Label();
		lblAttack1.setFont(new Font(null, 1 , new Extent(12)));
		
		colA.add(lblAttack1);
		colA.add(lblAttack2);
		colA.setCellSpacing(new Extent(5));

		col.add(row);
		if(personaje.getSpeed() >= enemigo.getVelocidad()){
			
			lblAttack1.setText("Turno");
			lblAttack2.setText(""+personaje.getUsuarioRef().getLogin());

			col.add(CreateButtonSpecial());
			col.add(CreateButtonBasic());
		}
		else{
			int dano = ((int)(Math.random()*(3)));
			PoderEnemigo pe = new PoderEnemigo();
			pe = consultAtaque(dano);
			simular(pe);
			FinalBattle();
			col.add(CreateButtonSpecial());
			col.add(CreateButtonBasic());
		}		

		Button exit = new Button("Exit");
		exit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonExitClicked(e);				
			}
		});		
		
		col.add(exit);		
		col.setLayoutData(hld);
		htmlLayout.add(col);
		htmlLayout.setBackground(Color.BLACK);

		return htmlLayout;
	}
	
	public void crearPersonaje(){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());		
		enemigo = (Enemigo) session.load(Enemigo.class, enemigo.getId());
	    
		session.getTransaction().commit();
	    session.close();
	}
	
	public CapacityBar crearBarraVida1(Color color1, Color color2, int indice1,int indice2){

		CapacityBar barra = new CapacityBar(10, 150); 
		barra.setShowTicks(false);
		barra.setReflectivity(0.1);
		List<Color> listColor = new ArrayList<Color>();
		listColor.add(color1);
		listColor.add(color2);
		barra.setColors(listColor);
		listNumber = new ArrayList<Number>();
		listNumber.add(indice1);
		listNumber.add(indice2); 
		barra.setValues(listNumber);     

		return barra;
	}

	public void FinalBattle(){

		if(barraVida1.getValues().get(0).intValue() <= 0){
			Column colwin = new Column();
			WindowPane windowPane = new WindowPane();
			add(windowPane);
			windowPane.setTitle("Se acabo");
			Label msg = new Label("La Batalla ha terminado.! "+enemigo.getNombre()+" Gano!");
			colwin.add(msg);
			windowPane.setModal(true);
			windowPane.setVisible(true);
			windowPane.setEnabled(true);
			windowPane.setClosable(false);
			windowPane.setMovable(false);
			windowPane.setPositionX(new Extent(500));
		    Button btnClose = new Button();
		    btnClose.setText("Aceptar");
		    btnClose.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnClose.setHeight(new Extent(15));
		    btnClose.setWidth( new Extent(60));
		    btnClose.setStyle(Styles1.DEFAULT_STYLE);
		    btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = false;
				removeAll();
				add(initRegion());
				}
			});
		    colwin.add(btnClose);
		    windowPane.add(colwin);

		}

		if(barraVida2.getValues().get(0).intValue() <= 0){
			int xp = ((int)(Math.random()*(10))) + enemigo.getXp();
			Column colwin = new Column();
			
			WindowPane windowPane = new WindowPane();
			windowPane.setTitle("Misión cumplida");
			add(windowPane);
			Label msg = new Label("La Batalla ha terminado.! " +personaje.getUsuarioRef().getLogin()+" Gano!");
			colwin.add(msg);
			msg = new Label(" +"+enemigo.getOro()+" Oro");
			colwin.add(msg);
			msg = new Label(" +"+xp+" Xp");
			colwin.add(msg);
			
			windowPane.setModal(true);
			windowPane.setVisible(true);
			windowPane.setClosable(false);
			windowPane.setMovable(false);
			windowPane.setPositionX(new Extent(500));
			Session session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();
			
			personaje = (Personaje) session.load(Personaje.class, personaje.getId());
			personaje.setGold( personaje.getGold() + enemigo.getOro());
			int aux = personaje.getXp() + xp;
			if(consultXpLevel(aux) > personaje.getLevel()){
				int aux2 = consultXp(personaje.getLevel());
				personaje.setLevel(consultXpLevel(personaje.getXp() + xp));
				personaje.setXp(aux - aux2);
				personaje.setPuntos(personaje.getPuntos() + 3);
				personaje.setHp(personaje.getHp() + 70);
				personaje.setMp(personaje.getMp() + 90);
				msg = new Label("Ha subido de Nivel.! +" +personaje.getLevel());
				colwin.add(msg);
				msg = new Label("+3 Ptos.!  + 70 Vida.! + 90 Psinergia.!");
				colwin.add(msg);
			}
			session.update(personaje);
		    
			session.getTransaction().commit();
		    session.close();
		    
		    
		    Button btnClose = new Button();
		    btnClose.setText("Aceptar");
		    btnClose.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnClose.setHeight(new Extent(15));
		    btnClose.setWidth( new Extent(60));
		    btnClose.setStyle(Styles1.DEFAULT_STYLE);
		    btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = false;
				removeAll();
				add(initRegion());
				}
			});
		    colwin.add(btnClose);
		    windowPane.add(colwin);
		}
	}
	
	private int consultXpLevel(int num){
		if(num <= 100 && personaje.getLevel() == 1){
			return 1;
		}
		else if(num <=300 && personaje.getLevel() == 2 || personaje.getLevel() == 1){
			return 2;
		}
		
		else if(num <=600 && personaje.getLevel() == 3 || personaje.getLevel() == 2) {
			return 3;
		}
		else if(num <=1000 && personaje.getLevel() == 4 || personaje.getLevel() == 3){
			return 4;
		}
		else if(num <=1500 && personaje.getLevel() == 5 || personaje.getLevel() == 4){
			return 5;
		}
		else if(num <=2000 && personaje.getLevel() == 6 || personaje.getLevel() == 5){
			return 6;
		}
		else if(num <=2500 && personaje.getLevel() == 7 || personaje.getLevel() == 6){
			return 7;
		}
		else if(num <=3000 && personaje.getLevel() == 8 || personaje.getLevel() == 7){
			return 8;
		}
		else if(num <=3500 && personaje.getLevel() == 9 || personaje.getLevel() == 8){
			return 9;
		}
		else{
			return 10;
		}		
	}
	
	private int consultXp(int num){
		if(num == 1){
			return 100;
		}
		else if(num == 2){
			return 300;
		}
		
		else if(num == 3){
			return 600;
		}
		else if(num == 4){
			return 1000;
		}
		else if(num == 5){
			return 1500;
		}
		else if(num == 6){
			return 2000;
		}
		else if(num == 7){
			return 2500;
		}
		else if(num == 8){
			return 3000;
		}
		else if(num == 9){
			return 3500;
		}
		else{
			return num;
		}		
	}

	private Row CreateButtonSpecial()
	{
		Row rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "SELECT personajePoderesList FROM Personaje WHERE personajeref_id = :idPlayer AND equipado = :e";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idPlayer", personaje.getId());
		query.setBoolean("e", true);
		List<Object> listQuery = query.list();			
		
		session.getTransaction().commit();			  	        
		session.close();
		
		createListPoderes(listQuery, rowBotonera);

		rowBotonera.setInsets(new Insets(170,30,20,2));

		return rowBotonera;
	}
	
	private void createListPoderes( List<Object> listQuery, Row rowBotonera){
		Iterator<Object> iter = listQuery.iterator();

  	    if (!iter.hasNext()) {
  	    	for(int i = 0; i<6; i++){
  	    		btnAttack1 = new Button(" X ");
  	    		btnAttack1.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
  	    		btnAttack1.setToolTipText("Null ");
  	    		btnAttack1.setStyle(Styles1.DEFAULT_STYLE);
  				btnAttack1.setHeight(new Extent(33));
  				btnAttack1.setWidth(new Extent(33));
  				btnAttack1.setEnabled(false);
  				btnAttack1.addActionListener(new ActionListener(){
  					public void actionPerformed(ActionEvent e) {
//  						b1Clicked();
  					}
  				});
  				rowBotonera.add(btnAttack1);  				
  	    	} 
  	    	return;
  	    }
  	    while (iter.hasNext()) {  
  	    	final PersonajePoderes p = (PersonajePoderes) iter.next();
			
			ImageReference ir = ImageReferenceCache.getInstance().getImageReference(p.getPoderesRef().getDirImage());
			
			btnAttack1 = new Button();
			btnAttack1.setIcon(ir);
			btnAttack1.setToolTipText("" + p.getPoderesRef().getName() +" Damage: " + p.getPoderesRef().getDamage() + //
					" \nCooldown: " + p.getPoderesRef().getCooldown() +// 
					" \nPsinergia: " + p.getPoderesRef().getPsinergia());
			btnAttack1.setStyle(Styles1.DEFAULT_STYLE);
			btnAttack1.setHeight(new Extent(33));
			btnAttack1.setWidth(new Extent(33));
			
			btnAttack1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					b1Clicked(p.getPoderesRef());
				}
			});		
			rowBotonera.add(btnAttack1);
  	    }
  	    if(listQuery.size() <6){
  	    	for(int i = listQuery.size() ; i<6; i++){
  	    		btnAttack1 = new Button(" X ");
  	    		btnAttack1.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
  	    		btnAttack1.setToolTipText("Null ");
  	    		btnAttack1.setStyle(Styles1.DEFAULT_STYLE);
  				btnAttack1.setHeight(new Extent(33));
  				btnAttack1.setWidth(new Extent(33));
  				btnAttack1.setEnabled(false);
  				btnAttack1.addActionListener(new ActionListener(){
  					public void actionPerformed(ActionEvent e) {
//  						b1Clicked();
  					}
  				});
  				rowBotonera.add(btnAttack1);  				
  	    	}  	    	
  	    }
	}	
	
	private Row CreateButtonBasic()
	{
		Row rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));
	
		btnHit = new Button();
		btnHit.setText("A");
		btnHit.setToolTipText("Ataque Básico ");
		btnHit.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnHit.setStyle(Styles1.DEFAULT_STYLE);
		btnHit.setHeight(new Extent(30));
		btnHit.setWidth(new Extent(30));
		btnHit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnHitClicked();
			}
		});		
		rowBotonera.add(btnHit);
		
		btnLoad = new Button();
		btnLoad.setText("P");
		btnLoad.setToolTipText("Posion ");
		btnLoad.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnLoad.setStyle(Styles1.DEFAULT_STYLE);
		btnLoad.setHeight(new Extent(30));
		btnLoad.setWidth(new Extent(30));
		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				consultItemEnergia();
			}
		});		
		rowBotonera.add(btnLoad);
		
		btnLoad = new Button();
		btnLoad.setText("M");
		btnLoad.setToolTipText("Medicina ");
		btnLoad.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnLoad.setStyle(Styles1.DEFAULT_STYLE);
		btnLoad.setHeight(new Extent(30));
		btnLoad.setWidth(new Extent(30));
		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				consultItemMedicina();
			}
		});		
		rowBotonera.add(btnLoad);
		
		btnLoad = new Button();
		btnLoad.setText("I");
		btnLoad.setToolTipText("Armas ");
		btnLoad.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnLoad.setStyle(Styles1.DEFAULT_STYLE);
		btnLoad.setHeight(new Extent(30));
		btnLoad.setWidth(new Extent(30));
		btnLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				consultItemArmas();
			}
		});		
		rowBotonera.add(btnLoad);
		
		rowBotonera.setInsets(new Insets(255,2,20,20));
		
		return rowBotonera;
	}	

	public void b1Clicked(Poderes poder){

		int dano = ((int)(Math.random()*(3)));
		PoderEnemigo pe = new PoderEnemigo();
		pe = consultAtaque(dano);

		if( (barraVida2.getValues().get(0).intValue() - poder.getDamage() - personaje.getAtaqueEspecial()<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(enemigo.getVida());
			barraVida2.setValues(listNumber);

			flag=true;
		}

		else if( (barraVida2.getValues().get(0).intValue() - poder.getDamage() - personaje.getAtaqueEspecial() > 0) && flag==false ){
			if(barraPsinergia.getValues().get(0).intValue() < poder.getPsinergia() )
			{
				labelCp.setText(personaje.getMp()+"/"+barraPsinergia.getValues().get(0).intValue());
			}

			else{
				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - poder.getDamage() - personaje.getAtaqueEspecial()); 
				listNumber.add( enemigo.getVida() - (barraVida2.getValues().get(0).intValue() - poder.getDamage() - personaje.getAtaqueEspecial()) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(barraPsinergia.getValues().get(0).intValue() - poder.getPsinergia());
				listNumber.add( personaje.getMp() - (barraPsinergia.getValues().get(0).intValue() - poder.getPsinergia()) );
				barraPsinergia.setValues(listNumber);
				
				labelCp.setText(personaje.getMp()+"/"+barraPsinergia.getValues().get(0).intValue());

				simular(pe);
			}
		} 

		if ( (barraVida1.getValues().get(0).intValue() <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(personaje.getHp());
			barraVida1.setValues(listNumber);

			flag=true;
		}
		FinalBattle();
	}
	
	private PoderEnemigo consultAtaque(int ataque){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "FROM PoderEnemigo WHERE enemigoref_id = :idEnemy";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idEnemy", enemigo.getId());
		List<PoderEnemigo> list = query.list();			
		
		session.getTransaction().commit();			  	        
		session.close();
		
		return list.get(ataque);
	}

	private void simular(PoderEnemigo poder){
		FinalBattle();
		listNumber = new ArrayList<Number>();
		listNumber.add(barraVida1.getValues().get(0).intValue() + personaje.getDefensa() - poder.getIndice()); 
		listNumber.add( personaje.getHp() - (barraVida1.getValues().get(0).intValue() + personaje.getDefensa() - poder.getIndice()) );

		barraVida1.setValues(listNumber);
		
		lblAttack1.setText(""+barraVida1.getValues().get(0).intValue()+" "+enemigo.getNombre());
		lblAttack2.setText("Lanzo ataque "+poder.getNombre());
	}
	
	private void btnHitClicked()
	{		
		int dano = ((int)(Math.random()*(3)));
		PoderEnemigo pe = new PoderEnemigo();
		pe = consultAtaque(dano);

		if( (barraVida2.getValues().get(0).intValue() - 20 - personaje.getAtaqueBasico()<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(enemigo.getVida());
			barraVida2.setValues(listNumber);

			flag=true;
		}

		else if( (barraVida2.getValues().get(0).intValue() - 20 - personaje.getAtaqueBasico() > 0) && flag==false ){
				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - 20 - personaje.getAtaqueBasico()); 
				listNumber.add( enemigo.getVida() - (barraVida2.getValues().get(0).intValue() - 20 - personaje.getAtaqueBasico()) );

				barraVida2.setValues(listNumber);
				simular(pe);
		} 

		if ( (barraVida1.getValues().get(0).intValue() <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(personaje.getHp());
			barraVida1.setValues(listNumber);

			flag=true;
		}
		FinalBattle();
	}
	
	private void consultItemEnergia(){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt1 OR name= :nameIt2 AND equipado = :e";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idPlayer", personaje.getId());
		query.setString("nameIt1", "Energia Media");
		query.setString("nameIt2", "Energia Super");
		query.setBoolean("e", true);
		List<PersonajeItem> list = query.list();
		session.getTransaction().commit();			  	        
		session.close();
		
		Column colwin = new Column();
		
		final WindowPane windowPane = new WindowPane();
		windowPane.setTitle("sus posiones");
		add(windowPane);
		
		windowPane.setModal(true);
		windowPane.setVisible(true);
		windowPane.setMaximumWidth(new Extent(200));
		windowPane.setMaximumHeight(new Extent(300));
	    for(final PersonajeItem obj: list){
	    Button btnClose = new Button();
	    btnClose.setText(""+obj.getItemRef().getName());
	    btnClose.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
	    btnClose.setHeight(new Extent(15));
	    btnClose.setWidth( new Extent(100));
	    btnClose.setStyle(Styles1.DEFAULT_STYLE);
	    btnClose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			btnLoadMpClicked(obj);
			windowPane.userClose();
			}
		});
	    colwin.add(btnClose);
	    	
	    }
	    windowPane.add(colwin);
	}
	
	private void btnLoadMpClicked(PersonajeItem obj)
	{				
		int dano = ((int)(Math.random()*(3)));
		PoderEnemigo pe = new PoderEnemigo();
		pe = consultAtaque(dano);		
		
		if((barraPsinergia.getValues().get(0).intValue() + obj.getItemRef().getIndex()) >= personaje.getMp())
		{	
			labelCp.setText(personaje.getMp()+"/"+personaje.getMp());	
			
			listNumber = new ArrayList<Number>();
			listNumber.add(personaje.getMp());
			listNumber.add(0);
			barraPsinergia.setValues(listNumber);
			
			simular(pe);
		}
		else
		{
			int aux = (barraPsinergia.getValues().get(0).intValue() + obj.getItemRef().getIndex());
			
			labelCp.setText(  aux+ "/" + personaje.getMp());
			
			listNumber = new ArrayList<Number>();
			listNumber.add(aux);
			listNumber.add( personaje.getMp() - aux );
			barraPsinergia.setValues(listNumber);
			
			labelCp.setText(personaje.getMp()+"/"+barraPsinergia.getValues().get(0).intValue());
			
			simular(pe);
		}
		if ( (barraVida1.getValues().get(0).intValue() <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(personaje.getHp());
			barraVida1.setValues(listNumber);

			flag=true;
		}
		FinalBattle();
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		session.delete(obj);

		session.getTransaction().commit();			  	        
		session.close();		
	}
	
	private void consultItemMedicina(){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt1 " +
				"OR name= :nameIt2 OR name= :nameIt3 AND equipado = :e";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idPlayer", personaje.getId());
		query.setString("nameIt1", "Medicina Media");
		query.setString("nameIt2", "Medicina Super");
		query.setString("nameIt3", "Medicina Basica");
		query.setBoolean("e", true);
		List<PersonajeItem> list = query.list();
		session.getTransaction().commit();			  	        
		session.close();
		
		Column colwin = new Column();
		
		final WindowPane windowPane = new WindowPane();
		windowPane.setTitle("Medicinas");
		add(windowPane);
		
		windowPane.setModal(true);
		windowPane.setVisible(true);
		windowPane.setMaximumWidth(new Extent(200));
		windowPane.setMaximumHeight(new Extent(300));
	    for(final PersonajeItem obj: list){
	    Button btnClose = new Button();
	    btnClose.setText(""+obj.getItemRef().getName());
	    btnClose.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
	    btnClose.setHeight(new Extent(15));
	    btnClose.setWidth( new Extent(100));
	    btnClose.setStyle(Styles1.DEFAULT_STYLE);
	    btnClose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			btnLoadHpClicked(obj);
			windowPane.userClose();
			}
		});
	    colwin.add(btnClose);
	    	
	    }
	    windowPane.add(colwin);
	}
	
	private void btnLoadHpClicked(PersonajeItem obj)
	{				
		int dano = ((int)(Math.random()*(3)));
		PoderEnemigo pe = new PoderEnemigo();
		pe = consultAtaque(dano);		
		
		if((barraVida1.getValues().get(0).intValue() + obj.getItemRef().getIndex()) >= personaje.getHp())
		{	
			labelCp.setText(personaje.getHp()+"/"+personaje.getHp());	
			
			listNumber = new ArrayList<Number>();
			listNumber.add(personaje.getHp());
			listNumber.add(0);
			barraVida1.setValues(listNumber);
			
			simular(pe);
		}
		else
		{
			int aux = (barraVida1.getValues().get(0).intValue() + obj.getItemRef().getIndex());
			
			labelCp.setText(  aux+ "/" + personaje.getMp());
			
			listNumber = new ArrayList<Number>();
			listNumber.add(aux);
			listNumber.add( personaje.getHp() - aux );
			barraVida1.setValues(listNumber);
			
			labelCp.setText(personaje.getHp()+"/"+barraVida1.getValues().get(0).intValue());
			
			simular(pe);
		}
		if ( (barraVida1.getValues().get(0).intValue() <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(personaje.getHp());
			barraVida1.setValues(listNumber);

			flag=true;
		}
		FinalBattle();
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		session.delete(obj);

		session.getTransaction().commit();			  	        
		session.close();			
	}
	
	private void consultItemArmas(){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND tipo = :tipoE OR tipo = :tipoB AND equipado = :e";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idPlayer", personaje.getId());
		query.setString("tipoE", "Espada");
		query.setString("tipoB", "Bomba");
		query.setBoolean("e", true);
		List<PersonajeItem> list = query.list();
		session.getTransaction().commit();			  	        
		session.close();
		
		Column colwin = new Column();
		
		final WindowPane windowPane = new WindowPane();
		windowPane.setTitle("Sus Armas");
		add(windowPane);
		
		windowPane.setModal(true);
		windowPane.setVisible(true);
		windowPane.setMaximumWidth(new Extent(200));
		windowPane.setMaximumHeight(new Extent(300));
	    for(final PersonajeItem obj: list){
	    Button btnClose = new Button();
	    btnClose.setText(""+obj.getItemRef().getName());
	    btnClose.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
	    btnClose.setHeight(new Extent(15));
	    btnClose.setWidth( new Extent(100));
	    btnClose.setStyle(Styles1.DEFAULT_STYLE);
	    btnClose.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			btnLoadArmorClicked(obj);
			windowPane.userClose();
			}
		});
	    colwin.add(btnClose);
	    	
	    }
	    windowPane.add(colwin);
	}
	
	private void btnLoadArmorClicked(PersonajeItem obj)
	{
		int dano = ((int)(Math.random()*(3)));
		PoderEnemigo pe = new PoderEnemigo();
		pe = consultAtaque(dano);

		if( (barraVida2.getValues().get(0).intValue() - obj.getItemRef().getIndex()<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(enemigo.getVida());
			barraVida2.setValues(listNumber);

			flag=true;
		}
		else if( (barraVida2.getValues().get(0).intValue() - obj.getItemRef().getIndex()>0) && flag==false ){
				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - obj.getItemRef().getIndex()); 
				listNumber.add( enemigo.getVida() - (barraVida2.getValues().get(0).intValue() - obj.getItemRef().getIndex()) );

				barraVida2.setValues(listNumber);

				simular(pe);
			}
		if ( (barraVida1.getValues().get(0).intValue()<=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(personaje.getHp());
			barraVida1.setValues(listNumber);			

			flag=true;
		}
		FinalBattle();
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr = "FROM Item WHERE tipo = :tipoB";
		Query query  = session.createQuery(queryStr);
		query.setString("tipoB", "Bomba");
		List<Item> list = query.list();

		session.getTransaction().commit();			  	        
		session.close();

		session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		for(Item it: list){
			if(obj.getItemRef().getId() == it.getId()){

				session.delete(obj);
			}
		}
		session.getTransaction().commit();			  	        
		session.close();		
	}
	
	private void buttonExitClicked(ActionEvent e) {
		removeAll();
		add(new MapaDesktop());
	}

}
