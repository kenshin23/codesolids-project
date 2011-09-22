package codesolids.gui.batalla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
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
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.extras.app.ToolTipContainer;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.criteria.expression.function.AggregationFunction.COUNT;
import org.informagen.echo.app.CapacityBar;

import com.sun.swing.internal.plaf.basic.resources.basic;

import codesolids.bd.clases.Batalla;
import codesolids.bd.clases.ChatBatalla;
import codesolids.bd.clases.Item;
import codesolids.bd.clases.Nivel;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.arena.PreArena;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleButton;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageLevel;
import codesolids.util.TimedServerPush;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Antonio López
 * @Colaborador Eduardo Granado
 *
 */

public class DesktopGif extends ContentPane{
	
	private TextArea txtCharla;
	private TextArea txtMsg;
	private Button btnEnviar;
	
	private Usuario usuario;
	
	private Personaje jugador;
	private Personaje jugadorOponente;
	
	private TaskQueueHandle taskQueue;
	
	private TimedServerPush checkServerPush; // 1  second
	
	private Label lblSec;
	
	private Label labelGold;
	private Label labelXp;
	private Label labelHp;
	private Label labelCp;
	private Label labelOponente;
	private Label labelPoder;
	private Label labelLevel; 
	
	private CapacityBar barraVida1;
	private CapacityBar barraVida2;
	private CapacityBar barraPsinergia;
	private CapacityBar barraXp;
	
	private List<Number> listNumber;
	
	private Button btnAttack;
	
	private Button btnHit;
	private Button btnLoadCp;
	private Button btnItem;
	
	private String turno;
	
	private Column colTimeBotonera;
	private Column colPanel;
	
	private Batalla battle;
	private Nivel nivelExp;
	
	private WindowPane ventanaItem;
	
	private List<Number> listCooldown;

	private Label magoA;
	private Label magoB;
	
	private Row rowM;
	
	private static final int vidaHp = 70;
	private static final int psinergiaMp = 90;
		
	public DesktopGif()
	{
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		usuario = app.getUsuario();
		jugador = app.getPersonaje();
		
		taskQueue = ApplicationInstance.getActive().createTaskQueue();
		
		initGUI();
	}
	
	private void initGUI()
	{
		add(initBattle());		
	}

	private Component initBattle() 
	{
		btnEnviar = new Button("Enviar");
		btnEnviar.setStyle(Styles1.DEFAULT_STYLE);
		btnEnviar.setHeight(new Extent(15));
		
		txtCharla = new TextArea();
		txtCharla.setEditable(false);
		txtCharla.setInsets(new Insets(15, 10, 15, 15));
		txtCharla.setWidth(new Extent(238));
		txtCharla.setHeight(new Extent(65));
		txtCharla.setEnabled(false);
		txtCharla.setDisabledBackground(Color.WHITE);

		txtMsg = new TextArea();
		txtMsg.setMaximumLength(100);
		txtMsg.setWidth(new Extent(400));
		txtMsg.setHeight(new Extent(14));
		
		HtmlLayout retHtmlLayout;
		
		try {
			
			Session session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Batalla.class).add(Restrictions.and(Restrictions.or(Restrictions.eq("jugadorCreadorRef", jugador),Restrictions.eq("jugadorRetadorRef", jugador)), Restrictions.eq("inBattle", true)));
			Batalla batalla = (Batalla) criteria.uniqueResult();
			
			session.getTransaction().commit();
			session.close();
			
			if( batalla.getEscenario() == 0 )
				retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templatebattle1.html"), "UTF-8");
			else if( batalla.getEscenario() == 1 )
				retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templatebattle2.html"), "UTF-8");
			else
				retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templatebattle3.html"), "UTF-8");
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
			
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("batalla");
		
		Column col = new Column();
		
		col.add(initGuiBatalla());
		
		col.setLayoutData(hld);
		
		retHtmlLayout.add(col);
		
		return retHtmlLayout;
	}

	private Column initGuiBatalla() {
		
		Column col = new Column();
		col.setCellSpacing(new Extent(7));

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		jugador = (Personaje) session.load(Personaje.class, jugador.getId());
		
		session.getTransaction().commit();
		session.close();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(300));	
		
		row.add(infoColumn());
		row.add(initChat());
		
		col.add(row);
		
		col.add(marcoAviso());
		
		col.add(statusColumn());
			
		listCooldown = new ArrayList<Number>();
		for(int j = 0; j < 6; j++ )
			listCooldown.add(0);
		
		colTimeBotonera = new Column();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		col.add(colTimeBotonera);
		
		session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		battle = (Batalla) session.load(Batalla.class, battle.getId());

		turno = battle.getTurno();
		
		session.getTransaction().commit();
		session.close();
		
		if( battle.getTurno().equals("Creador") ) 
		{
			if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
				colTimeBotonera.setVisible(true);
		}
		else if( battle.getTurno().equals("Retador") )
		{
			if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
				colTimeBotonera.setVisible(true);
		}
		
		ApplicationInstance app = ApplicationInstance.getActive();		
		checkServerPush = new TimedServerPush(1000, app, taskQueue, new Runnable() {
			@Override
			public void run() {

				mostrarMensajes();

				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();

				battle = (Batalla) session.load(Batalla.class, battle.getId());				

				session.getTransaction().commit();
				session.close();

				if( battle.getTurno().equals("Creador") ) 
				{
					if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
					{		
						
						if( battle.getTiempoMovimiento() == 0 ){

							colTimeBotonera.setVisible(true);

							if( battle.getVidaCreador() == 0 )
							{

								listNumber = new ArrayList<Number>();
								listNumber.add(0); 
								listNumber.add(150);

								barraVida1.setValues(listNumber);
								labelHp.setText(battle.getVidaCreador() + "/" + jugador.getHp());

								session = SessionHibernate.getInstance().getSession();
								session.beginTransaction();

								jugador = (Personaje) session.load(Personaje.class, jugador.getId());

								jugador.setXp(jugador.getXp());
								jugador.setGold(jugador.getGold());
								jugador.setLevel(jugador.getLevel());

								battle = (Batalla) session.load(Batalla.class, battle.getId());

								battle.setInBattle(false);

								session.getTransaction().commit();
								session.close();

								checkServerPush.end();
								finalBattle();
							}
							else
							{
								listNumber = new ArrayList<Number>();
								listNumber.add(battle.getVidaCreador()); 
								listNumber.add(jugador.getHp() - battle.getVidaCreador());

								barraVida1.setValues(listNumber);

								labelHp.setText(battle.getVidaCreador() + "/" + jugador.getHp());
							}
							timeTurno();
						}else{
							
							session = SessionHibernate.getInstance().getSession();
							session.beginTransaction();
							
							battle = (Batalla) session.load(Batalla.class, battle.getId());
							battle.setTiempoMovimiento(battle.getTiempoMovimiento() - 1);

//							if( flag == false ){
//								simulateMovimiento(battle.getTipoAtaque());
//								flag = true;
//							}else{
//								imageIni();
//							}
							
							if( battle.getFlag() == false ){
								simulateMovimiento(battle.getTipoAtaque());
								battle.setFlag(true);
							}else{
								imageIni();
							}
							
							session.getTransaction().commit();
							session.close();
						}
					}
				}
				else if( battle.getTurno().equals("Retador") )
				{
					if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
					{
						if( battle.getTiempoMovimiento() == 0 ){
							
							colTimeBotonera.setVisible(true);

							if( battle.getVidaRetador() == 0 )
							{	
								listNumber = new ArrayList<Number>();
								listNumber.add(0); 
								listNumber.add(150);

								barraVida1.setValues(listNumber);
								labelHp.setText(battle.getVidaRetador() + "/" + jugador.getHp());

								session = SessionHibernate.getInstance().getSession();
								session.beginTransaction();

								jugador = (Personaje) session.load(Personaje.class, jugador.getId());

								jugador.setXp(jugador.getXp());
								jugador.setGold(jugador.getGold());
								jugador.setLevel(jugador.getLevel());

								battle = (Batalla) session.load(Batalla.class, battle.getId());

								battle.setInBattle(false);

								session.getTransaction().commit();
								session.close();

								checkServerPush.end();
								finalBattle();
							}
							else
							{
								listNumber = new ArrayList<Number>();
								listNumber.add(battle.getVidaRetador()); 
								listNumber.add(jugador.getHp() - battle.getVidaRetador());

								barraVida1.setValues(listNumber);
								labelHp.setText(battle.getVidaRetador() + "/" + jugador.getHp());
							}
							timeTurno();
						}else{
							session = SessionHibernate.getInstance().getSession();
							session.beginTransaction();

							battle = (Batalla) session.load(Batalla.class, battle.getId());
							battle.setTiempoMovimiento(battle.getTiempoMovimiento() - 1);

//							if( flag == false ){
//								simulateMovimiento(battle.getTipoAtaque());
//								flag = true;
//							}else{
//								imageIni();
//							}

							if( battle.getFlag() == false ){
								simulateMovimiento(battle.getTipoAtaque());
								battle.setFlag(true);
							}else{
								imageIni();
							}
							
							session.getTransaction().commit();
							session.close();
						}
					}
				}
			}
		});		
		checkServerPush.beg();
		
		return col;
	}
	
	private Column infoColumn()
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_LEFT);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/p_estadoBattle.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(300));
		panel.setHeight(new Extent(129));
		panel.setBackgroundImage(imgF);
		
		Column col = new Column();
		col.setInsets(new Insets(15,15,15,15));
		col.setCellSpacing(new Extent(2));

		Label lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText(usuario.getLogin());
		col.add(lbl);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(50));
		
		labelLevel = new Label();
		labelLevel.setForeground(Color.YELLOW);
		labelLevel.setText("Lv. "+ jugador.getLevel());
		row.add(labelLevel);
		
        Panel panelGold = new Panel();
        panelGold.setWidth(new Extent(150));

        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));

		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        rowPanel.add(imgI);
        
        labelGold = new Label();
        labelGold.setForeground(Color.YELLOW);
        labelGold.setText(" " + jugador.getGold());
		rowPanel.add(labelGold);
		
        panelGold.add(rowPanel);
        
        row.add(panelGold);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(10));
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("XP");		
		row.add(lbl);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() +1));
		
		session.getTransaction().commit();
		session.close();
		
		barraXp = createBarra(Color.GREEN,Color.WHITE,jugador.getXp(),(nivelExp.getCantidadExp() - jugador.getXp()));
		row.add(barraXp);
		
		labelXp = new Label();
		labelXp.setForeground(Color.WHITE);		
		labelXp.setText(jugador.getXp() + "/" + nivelExp.getCantidadExp());
		row.add(labelXp);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(10));
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("HP");		
		row.add(lbl);
		
		barraVida1 = createBarra(Color.RED,Color.WHITE,jugador.getHp(),0);
		row.add(barraVida1);
		
		labelHp = new Label();
		labelHp.setForeground(Color.WHITE);
		labelHp.setText(barraVida1.getValues().get(0).intValue() + "/" + jugador.getHp());
		row.add(labelHp);
		col.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));

		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("MP");		
		row.add(lbl);
		
		barraPsinergia = createBarra(Color.BLUE,Color.WHITE,jugador.getMp(),0);
		row.add(barraPsinergia);
		
		labelCp = new Label();
		labelCp.setForeground(Color.WHITE);
		labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
		row.add(labelCp);
		col.add(row);
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
		
	}
	
	private Column statusColumn()
	{
		Column colStatus = new Column(); 
		colStatus.setInsets(new Insets(5,5,5,18));

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
				
		Criteria criteria = session.createCriteria(Batalla.class).add(Restrictions.eq("inBattle", true));
		
		List<Batalla> listBattle = criteria.list();
				
		session.getTransaction().commit();
		session.close();
		
		for(int i = 0; i < listBattle.size(); i++ )
		{
			if( jugador.getId() == listBattle.get(i).getJugadorCreadorRef().getId() )
			{
				jugadorOponente = listBattle.get(i).getJugadorRetadorRef();
				battle = listBattle.get(i);
			}
			else if ( jugador.getId() == listBattle.get(i).getJugadorRetadorRef().getId() )
			{
				jugadorOponente = listBattle.get(i).getJugadorCreadorRef();
				battle = listBattle.get(i);
			}
		}
				
		if ( jugadorOponente.getTipo().equals("Tierra") )
		{
		//	jugadorOponente.setDirImage("Images/Personajes/MagoTT.png");
			jugadorOponente.setDirImage("Images/Personajes/Gifs/MGTR.gif");
		}
		else if ( jugadorOponente.getTipo().equals("Fuego") )
		{
		//	jugadorOponente.setDirImage("Images/Personajes/MagoFF.png");
			jugadorOponente.setDirImage("Images/Personajes/Gifs/MGFR.gif");
		}
		else if ( jugadorOponente.getTipo().equals("Hielo") )
		{
		//	jugadorOponente.setDirImage("Images/Personajes/MagoHH.png");
			jugadorOponente.setDirImage("Images/Personajes/Gifs/MGHR.gif");
		}
		else
		{
		//	jugadorOponente.setDirImage("Images/Personajes/GuerreroGG.png");
			jugadorOponente.setDirImage("Images/Personajes/Gifs/MGGR.gif");
		}
			
//		ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
//		Label magoA = new Label(mA);
//		
//		ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
//		Label magoB = new Label(mB);
//		
//		Row rowM = new Row();
//		rowM.setAlignment(Alignment.ALIGN_CENTER);
//		rowM.setCellSpacing(new Extent(500, Extent.PX));
//		rowM.add(magoA);
//		rowM.add(magoB);

		colStatus.add(rowImage());
		
		Row rowA = new Row();

		Row rowB = new Row();
		rowB.setAlignment(Alignment.ALIGN_CENTER);
		rowB.setCellSpacing(new Extent(5));
		rowB.add(new Label("Lv. " + jugadorOponente.getLevel()));
		barraVida2 = createBarra(Color.RED,Color.WHITE,jugadorOponente.getHp(),0);
		rowB.add(barraVida2);

		Row row = new Row();
		row.setCellSpacing(new Extent(690));
		row.add(rowA);
		row.add(rowB);

		colStatus.add(row);
		
		return colStatus;	
	}
	
	private Row rowImage()
	{
		magoA = new Label(ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage()));
	
		magoB = new Label(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
		
		rowM = new Row();
		rowM.setCellSpacing(new Extent(480, Extent.PX));
		rowM.add(magoA);
		rowM.add(magoB);
		
		return rowM;
	}
	
	private Row imageData()
	{
		magoA = new Label();
		magoB = new Label();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(1, Extent.PX));
		row.add(magoA);
		row.add(magoB);
		
		return row;
	}
	
	private CapacityBar createBarra(Color color1, Color color2, int indice1,int indice2){

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
	
	private Column createBotonera()
	{
		
		Column col = new Column();
		col.setCellSpacing(new Extent(2));
		
		Row rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));

		for( int i = 0; i < 6; i++ )
		{
			rowBotonera.add(buttonAtaque(i));
		}
		
		col.add(rowBotonera);

		rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));
	
		btnHit = new Button();
		btnHit.setStyle(StyleButton.BATALLA_STYLE);
		btnHit.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Poderes/Basico/ataque.png"));
		
		btnHit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnHitClicked();
			}
		});		
		rowBotonera.add(btnHit);
		
		btnLoadCp = new Button();
		btnLoadCp.setStyle(StyleButton.BATALLA_STYLE);
		btnLoadCp.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Poderes/Basico/manapoint.png"));
		btnLoadCp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnLoadCpClicked();
			}
		});		
		rowBotonera.add(btnLoadCp);		
		
		btnItem = new Button();
		btnItem.setStyle(StyleButton.BATALLA_STYLE);
		btnItem.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Poderes/Basico/items.png"));
		btnItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnItemClicked();
			}
		});		
		rowBotonera.add(btnItem);		

		col.add(rowBotonera);
		
		return col;
	}
	
	private Column buttonAtaque(final int posicion)
	{
		Column colBtn = new Column();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		jugador = (Personaje) session.load(Personaje.class, jugador.getId());

		battle = (Batalla) session.load(Batalla.class, battle.getId());
		
		String queryStr = "SELECT p FROM PersonajePoderes AS pp, Poderes AS p WHERE pp.equipado = true AND pp.poderesRef = p.id AND pp.personajeRef = " + jugador.getId() + " ORDER BY orderEquipado ASC";
		Query query = session.createQuery(queryStr);
		
		final List<Poderes> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		if( posicion < list.size() )
		{
			btnAttack = new Button();
			btnAttack.setStyle(StyleButton.BATALLA_STYLE);

			ToolTipContainer toolTip = new ToolTipContainer();
			toolTip.add(btnAttack);			
			toolTip.add(toolTipPower(list.get(posicion)));

			colBtn.add(toolTip);

			if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
			{
				if( battle.getPsinergiaCreador() >= list.get(posicion).getPsinergia() )
				{
					if( (listCooldown.get(posicion).intValue() - 1) == battle.getSecuenciaTurno() )
					{
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(list.get(posicion).getDirImage()));
						btnAttack.setEnabled(true);
						listCooldown.add(posicion, 0);
					}
					else if ( listCooldown.get(posicion).intValue() > battle.getSecuenciaTurno() )
					{
						String subStr = list.get(posicion).getDirImage().substring(0,(list.get(posicion).getDirImage().length() - 4));
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(subStr + "Opaca.png"));
						btnAttack.setEnabled(false);
					}
					else
					{
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(list.get(posicion).getDirImage()));
						btnAttack.setEnabled(true);
					}
				}
				else
				{
					String subStr = list.get(posicion).getDirImage().substring(0,(list.get(posicion).getDirImage().length() - 4));
					btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(subStr + "Opaca.png"));
					btnAttack.setEnabled(false);
				}
			}
			else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
			{
				if( battle.getPsinergiaRetador() >= list.get(posicion).getPsinergia() )
				{
					if( listCooldown.get(posicion).intValue() == battle.getSecuenciaTurno() )
					{
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(list.get(posicion).getDirImage()));
						btnAttack.setEnabled(true);
						listCooldown.add(posicion, 0);
					}
					else if ( listCooldown.get(posicion).intValue() > battle.getSecuenciaTurno() )
					{
						String subStr = list.get(posicion).getDirImage().substring(0,(list.get(posicion).getDirImage().length() - 4));
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(subStr + "Opaca.png"));
						btnAttack.setEnabled(false);
					}
					else
					{
						btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(list.get(posicion).getDirImage()));
						btnAttack.setEnabled(true);
					}
				}
				else
				{
					String subStr = list.get(posicion).getDirImage().substring(0,(list.get(posicion).getDirImage().length() - 4));
					btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference(subStr + "Opaca.png"));
					btnAttack.setEnabled(false);
				}
			}
		}
		else
		{
			btnAttack = new Button();
			btnAttack.setStyle(StyleButton.BATALLA_STYLE);
			btnAttack.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Util/vacioAtaque.png"));
			btnAttack.setEnabled(false);
			colBtn.add(btnAttack);
		}

		btnAttack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnAttackClicked(list.get(posicion),posicion);
			}
		});		
		
		return colBtn;
	}

	private Column toolTipPower(Poderes poder)
	{
		
		Column col = new Column();
		col.setBorder(new Border(3, Color.BLACK, Border.STYLE_RIDGE));
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(5, 5, 5, 5));
		col.setBackground(new Color(226,211,161));
		
		Label lbl = new Label();

	    ColumnLayoutData cld;
	    cld = new ColumnLayoutData();
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    lbl.setLayoutData(cld);
		lbl.setText(poder.getName());
		col.add(lbl);
		
		lbl = new Label();
		lbl.setForeground(Color.RED);
		lbl.setText("Daño: " + poder.getDamage());
		col.add(lbl);
		
		lbl = new Label();
		lbl.setForeground(Color.BLUE);
		lbl.setText("Psinergia: " + poder.getPsinergia());
		col.add(lbl);
		
		lbl = new Label();
		lbl.setForeground(Color.ORANGE);
		lbl.setText("Cooldown: " + poder.getCooldown());
		col.add(lbl);
		
		return col;
	}
	
	private void timeTurno()
	{
		if( ( Integer.parseInt(lblSec.getText()) - 1 ) == 0 )
		{

			Session session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			battle = (Batalla) session.load(Batalla.class, battle.getId());

			if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
				battle.setTurno("Retador");
			else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
				battle.setTurno("Creador");

			if( battle.getTurno().equals(turno) )
				battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
			
			battle.setTipoAtaque("Nada");
			battle.setTiempoMovimiento(0);
			
			session.getTransaction().commit();
			session.close();

			colTimeBotonera.removeAll();
			colTimeBotonera.add(panelTimeBotonera());
			colTimeBotonera.setVisible(false);

			lblSec.setText("21");
			lblSec.setForeground(Color.GREEN);
			
			remove(ventanaItem);
			
		}
		else if( Integer.parseInt(lblSec.getText()) < 7 )
		{
			lblSec.setForeground(Color.RED);
			lblSec.setText("" + (Integer.parseInt(lblSec.getText()) - 1));							
		}
		else if( Integer.parseInt(lblSec.getText()) == 17 )
		{
			colPanel.setVisible(false);
			lblSec.setText("" + (Integer.parseInt(lblSec.getText()) - 1));
		}
		else if( Integer.parseInt(lblSec.getText()) > 17 )
		{
			colPanel.setVisible(true);
			lblSec.setText("" + (Integer.parseInt(lblSec.getText()) - 1));
		}
		else
		{
			if( Integer.parseInt(lblSec.getText()) > 0 )
				lblSec.setText("" + (Integer.parseInt(lblSec.getText()) - 1));
		}		
	}
	
	private Column createCount()
	{
		Column col = new Column();

		lblSec = new Label();
		lblSec.setForeground(Color.GREEN);
		lblSec.setFont(new Font(Font.COURIER_NEW,Font.BOLD, null));
		lblSec.setText("20");		
		col.add(lblSec);

		return col;
	}

	private Column panelTimeBotonera()
	{		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setCellSpacing(new Extent(5));
		
		col.add(createCount());
		col.add(createBotonera());
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private void btnAttackClicked(Poderes poder, int posicion) 
	{	
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
	
		battle = (Batalla) session.load(Batalla.class, battle.getId());
		
		Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
		List<PersonajeItem> listItem = criteria.list();		
		
		//Defensa Armadura
		int defensaArmadura = 0;
		
		for( int i = 0; i < listItem.size(); i++ )
		{
			if( listItem.get(i).getItemRef().getTipo().equals("Armadura") && (listItem.get(i).getPersonajeRef().getId() == jugadorOponente.getId()) )
			{
				defensaArmadura = listItem.get(i).getItemRef().getIndex();
			}
		}

		//Ataque Especial
		int damagePoder = (int) ( poder.getDamage() + poder.getDamage()*jugador.getAtaqueEspecial() );
		int defensaOponente = ( jugadorOponente.getDefensa() + defensaArmadura );
		int damage = (int) ( damagePoder - ((damagePoder*defensaOponente)/100) );

		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			if( battle.getVidaRetador() - damage <= 0 )
			{
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);

				battle.setVidaRetador(0);
				battle.setTurno("Retador");
				lblSec.setText("21");
				
				finalBattle();
			}
			else
			{	
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listCooldown.add(posicion, (poder.getCooldown() + battle.getSecuenciaTurno() + 1));
				
				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getVidaRetador() - damage); 
				listNumber.add(jugadorOponente.getHp() - (battle.getVidaRetador() - damage) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getPsinergiaCreador() - poder.getPsinergia());
				listNumber.add( jugador.getMp() - (battle.getPsinergiaCreador() - poder.getPsinergia()) );
				barraPsinergia.setValues(listNumber);

				labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());

				battle.setPsinergiaCreador(battle.getPsinergiaCreador() - poder.getPsinergia());
				battle.setVidaRetador(battle.getVidaRetador() - damage);

				battle.setTurno("Retador");
				lblSec.setText("21");
			}
		}
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			if( battle.getVidaCreador() - damage <= 0 )
			{
				
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);

				battle.setVidaCreador(0);

				battle.setTurno("Creador");
				lblSec.setText("21");
				
				finalBattle();
			}
			else
			{
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					magoA.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFE1.gif"));
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGE1.gif"));
//					magoB.setIcon(ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage()));
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listCooldown.add(posicion, (poder.getCooldown() + battle.getSecuenciaTurno() + 1));
				
				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getVidaCreador() - damage); 
				listNumber.add(jugadorOponente.getHp() - (battle.getVidaCreador() - damage) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getPsinergiaRetador() - poder.getPsinergia());
				listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() - poder.getPsinergia()) );
				barraPsinergia.setValues(listNumber);

				labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());

				battle.setPsinergiaRetador(battle.getPsinergiaRetador() - poder.getPsinergia());
				battle.setVidaCreador(battle.getVidaCreador() - damage);

				battle.setTurno("Creador");
				lblSec.setText("21");
			}
		}
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
		
		battle.setTipoAtaque("Especial");
		battle.setTiempoMovimiento(3);
		battle.setFlag(false);
		
		session.getTransaction().commit();
		session.close();
		
		colTimeBotonera.removeAll();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		
	}

	private void btnHitClicked() 
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
	
		battle = (Batalla) session.load(Batalla.class, battle.getId());
		
		Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
		List<PersonajeItem> listItem = criteria.list();
		
		//Daño del Arma si tiene equipado
		int danoArma = 0;
		//Defensa Armadura
		int defensaArmadura = 0;
		//Ataque Basico
		int damage = 0;
		
		boolean flagArma = false;
		
		for( int i = 0; i < listItem.size(); i++ )
		{
			if( listItem.get(i).getItemRef().getTipo().equals("Espada") && (listItem.get(i).getPersonajeRef().getId() == jugador.getId()) )
			{
				danoArma = listItem.get(i).getItemRef().getIndex();
				flagArma = true;
			}
			if( listItem.get(i).getItemRef().getTipo().equals("Armadura") && (listItem.get(i).getPersonajeRef().getId() == jugadorOponente.getId()) )
				defensaArmadura = listItem.get(i).getItemRef().getIndex();
		}
		
		if( flagArma )
		{
			danoArma = (int) ( danoArma + danoArma*jugador.getAtaqueBasico() );
			int defensaOponente = ( jugadorOponente.getDefensa() + defensaArmadura );
			damage = (int) ( danoArma - ((danoArma*defensaOponente)/100) );
		}
		else
		{
			//Yo le doy 20 por defecto, discutir esto!
			danoArma = (int) ( 20 + 20*jugador.getAtaqueBasico() );
			int defensaOponente = ( jugadorOponente.getDefensa() + defensaArmadura );
			damage = (int) ( danoArma - ((danoArma*defensaOponente)/100) );
		}
		
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			
			battle.setTurno("Retador");
			if( (battle.getVidaRetador() - damage <= 0) )
			{
				
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);
				
				battle.setVidaRetador(0);
				
				finalBattle();
			}
			else
			{
				//Subo 15!! Debe ser algun porcentaje!!
				if( (battle.getPsinergiaCreador() + 15) >= jugador.getMp() )
				{	
					
					if( jugador.getTipo().equals("Fuego") )
					{					
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
					}
					else if( jugador.getTipo().equals("Hielo") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else if( jugador.getTipo().equals("Tierra") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					
					labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					barraPsinergia.setValues(listNumber);
					battle.setPsinergiaCreador(jugador.getMp());
					
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaRetador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaRetador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaRetador(battle.getVidaRetador() - damage);
				}
				else
				{
					if( jugador.getTipo().equals("Fuego") )
					{					
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
					}
					else if( jugador.getTipo().equals("Hielo") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else if( jugador.getTipo().equals("Tierra") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					
					listNumber = new ArrayList<Number>();
					listNumber.add(( battle.getPsinergiaCreador() + 15 ));
					listNumber.add( jugador.getMp() - ( battle.getPsinergiaCreador() + 15 ) );
					barraPsinergia.setValues(listNumber);
					
					battle.setPsinergiaCreador(battle.getPsinergiaCreador() + 15 );
					labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
					
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaRetador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaRetador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaRetador(battle.getVidaRetador() - damage);
				}
			}	
		}
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			battle.setTurno("Creador");
			if( (battle.getVidaCreador() - damage <= 0) )
			{
				if( jugador.getTipo().equals("Fuego") )
				{					
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
				}
				else if( jugador.getTipo().equals("Hielo") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else if( jugador.getTipo().equals("Tierra") )
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				else
				{
					rowM.removeAll();
					rowM.add(imageData());
					
//					ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
					magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//					ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
					magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
				}
				
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);
				
				battle.setVidaCreador(0);
				finalBattle();
			}
			else
			{
				if( (battle.getPsinergiaRetador() + 15) >= jugador.getMp() )
				{	
					if( jugador.getTipo().equals("Fuego") )
					{					
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
					}
					else if( jugador.getTipo().equals("Hielo") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else if( jugador.getTipo().equals("Tierra") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					
					labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					barraPsinergia.setValues(listNumber);
					battle.setPsinergiaRetador(jugador.getMp());
					
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaCreador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaCreador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaCreador(battle.getVidaCreador() - damage);
				}
				else
				{
					if( jugador.getTipo().equals("Fuego") )
					{					
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));			
					}
					else if( jugador.getTipo().equals("Hielo") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else if( jugador.getTipo().equals("Tierra") )
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					else
					{
						rowM.removeAll();
						rowM.add(imageData());
						
//						ImageReference mA = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH1.gif");					
						magoA.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH1.gif"));
//						ImageReference mB = ImageReferenceCache.getInstance().getImageReference(jugadorOponente.getDirImage());
						magoB.setIcon(new ResourceImageReference(jugadorOponente.getDirImage()));
					}
					
					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getPsinergiaRetador() + 15 );
					listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() + 15) );
					barraPsinergia.setValues(listNumber);
					
					battle.setPsinergiaRetador( battle.getPsinergiaRetador() + 15 );
					labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
					
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaCreador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaCreador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaCreador(battle.getVidaCreador() - damage);
				}
			}
		}
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
		
		battle.setTipoAtaque("Basico");
		battle.setTiempoMovimiento(3);
		battle.setFlag(false);
		
		session.getTransaction().commit();
		session.close();
		
		colTimeBotonera.removeAll();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		
		lblSec.setText("21");
	}
	
	private void btnLoadCpClicked() 
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
	
		battle = (Batalla) session.load(Batalla.class, battle.getId());
				
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			battle.setTurno("Retador");
			//Aumento 30, debe ser un porcentaje discutir!
			if( (battle.getPsinergiaCreador() + 30) >= jugador.getMp() )
			{	
				labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
				
				listNumber = new ArrayList<Number>();
				listNumber.add(150);
				listNumber.add(0);
				
				barraPsinergia.setValues(listNumber);
				battle.setPsinergiaCreador(jugador.getMp());
				
			}
			else
			{
				listNumber = new ArrayList<Number>();
				listNumber.add( battle.getPsinergiaCreador() + 30 );
				listNumber.add( jugador.getMp() - (battle.getPsinergiaCreador() + 30) );
				barraPsinergia.setValues(listNumber);
				
				labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
				battle.setPsinergiaCreador(battle.getPsinergiaCreador() + 30);
			}
		}
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			battle.setTurno("Creador");			
			if( (battle.getPsinergiaRetador() + 30) >= jugador.getMp())
			{	
				labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
				
				listNumber = new ArrayList<Number>();
				listNumber.add(150);
				listNumber.add(0);
				
				barraPsinergia.setValues(listNumber);
				battle.setPsinergiaRetador(jugador.getMp());
				
			}
			else
			{
				listNumber = new ArrayList<Number>();
				listNumber.add( battle.getPsinergiaRetador() + 30 );
				listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() + 30) );
				barraPsinergia.setValues(listNumber);
				
				labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
				battle.setPsinergiaRetador(battle.getPsinergiaRetador() + 30);		
			}
		}
		
		battle.setTipoAtaque("CargarMp");
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
		
		session.getTransaction().commit();
		session.close();
	
		colTimeBotonera.removeAll();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		
		lblSec.setText("21");
	}	
	
	private void simulateMovimiento(String tipoAtaque)
	{
		
		if( tipoAtaque.equals("Basico") )
		{
			if( jugadorOponente.getTipo().equals("Fuego") )
			{				
				rowM.removeAll();
				rowM.add(imageData());
				
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFH2.gif"));
			}
			else if( jugadorOponente.getTipo().equals("Hielo") )
			{
				rowM.removeAll();
				rowM.add(imageData());
							
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHH2.gif"));
			}
			else if( jugadorOponente.getTipo().equals("Tierra") )
			{
				rowM.removeAll();
				rowM.add(imageData());

//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTH2.gif"));
			}
			else
			{
				rowM.removeAll();
				rowM.add(imageData());
							
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGH2.gif"));
			}
		}
		else if( tipoAtaque.equals("Especial") )
		{
			if( jugadorOponente.getTipo().equals("Fuego") )
			{				
				rowM.removeAll();
				rowM.add(imageData());
				
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGFE2.gif"));
			}
			else if( jugadorOponente.getTipo().equals("Hielo") )
			{
				rowM.removeAll();
				rowM.add(imageData());
				
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGHE2.gif"));
			}
			else if( jugadorOponente.getTipo().equals("Tierra") )
			{
				rowM.removeAll();
				rowM.add(imageData());
				
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGTE2.gif"));
			}
			else
			{
				rowM.removeAll();
				rowM.add(imageData());
				
//				ImageReference mA = ImageReferenceCache.getInstance().getImageReference(jugador.getDirImage());
				magoA.setIcon(new ResourceImageReference(jugador.getDirImage()));
//				ImageReference mB = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGFH2.gif");				
				magoB.setIcon(new ResourceImageReference("Images/Personajes/Gifs/MGGE2.gif"));
			}
		}
	}
	
	private void imageIni()
	{
		rowM.removeAll();
		rowM.add(rowImage());
	}
	
	private void btnItemClicked() 
	{
		ventanaItem = new WindowPane();
		ventanaItem.setTitle("Items");
		ventanaItem.setStyle(StyleWindow.DEFAULT_STYLE);
		ventanaItem.setWidth(new Extent(380));
		ventanaItem.setHeight(new Extent(170));
		ventanaItem.setMovable(false);
		ventanaItem.setModal(true);
		ventanaItem.setMaximumWidth(new Extent(380));
		ventanaItem.setMinimumHeight(new Extent(170));
		ventanaItem.setResizable(false);
		
		ventanaItem.add(rowItems());
		
		add(ventanaItem);
	}
	
	private Column rowItems()
	{		
		Grid grid = new Grid(5);
		
		GridLayoutData gld;
		gld = new GridLayoutData();
		gld.setAlignment(Alignment.ALIGN_LEFT);
		grid.setLayoutData(gld);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		jugador = (Personaje) session.load(Personaje.class, jugador.getId());
		
		String queryStr = "SELECT new Map(count(*) AS cantidad, it AS item) FROM PersonajeItem AS pi, Item AS it WHERE pi.itemRef = it.id AND personajeref_id = :idPlayer AND pi.equipado = true AND (it.tipo = :tipoIt1 OR it.tipo = :tipoIt2 OR it.tipo = :tipoIt3) GROUP BY it.id ORDER BY it.id";
		Query query = session.createQuery(queryStr);
		query.setInteger("idPlayer", jugador.getId());
		query.setString("tipoIt1", "Pocion");
		query.setString("tipoIt2", "Medicina");
		query.setString("tipoIt3", "Bomba");
		List<Map> listItems = query.list();
		
		for( int i = 0; i < listItems.size(); i++ )
		{
			Map mapa = listItems.get(i);
			
			Item item = new Item();
			item = (Item) mapa.get("item");
			
			Row row = new Row();
			row.setCellSpacing(new Extent(2));
			row.setInsets(new Insets(10, 10, 10, 10));
			
			row.add(createBoton(item));
			row.add(new Label("x" + Integer.parseInt(mapa.get("cantidad").toString()) ));
			grid.add(row);
		}
		
		session.getTransaction().commit();
		session.close();
		
		Column col = new Column();
		col.setInsets(new Insets(5, 5, 5, 5));
		col.add(grid);
		
		return col;
	}
	
	private Column createBoton(final Item item)
	{
		
		Button btnItem = new Button();
		btnItem.setIcon(ImageReferenceCache.getInstance().getImageReference(item.getDirImage()));
		
		btnItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnItemClicked(item);
			}
		});
		
		ToolTipContainer toolTip = new ToolTipContainer();
		toolTip.add(btnItem);			
		toolTip.add(toolTipItem(item));
		
		Column col = new Column();
		col.add(toolTip);
		
		return col;	
	}
	
	private void btnItemClicked(Item item) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		jugador = (Personaje) session.load(Personaje.class, jugador.getId());
		
		battle = (Batalla) session.load(Batalla.class, battle.getId());
		
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			if( item.getTipo().equals("Medicina") )
			{
				int porcentaje = ( (jugador.getHp()*item.getIndex())/100 );

				if( battle.getVidaCreador() + porcentaje >= jugador.getHp() )
				{
					labelHp.setText(jugador.getHp() + "/" + jugador.getHp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					barraVida1.setValues(listNumber);
					battle.setVidaCreador(jugador.getHp());
				}
				else
				{
					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getVidaCreador() + porcentaje );
					listNumber.add( jugador.getHp() - (battle.getVidaCreador() + porcentaje) );
					barraVida1.setValues(listNumber);
					
					labelHp.setText(barraVida1.getValues().get(0).intValue() + "/" + jugador.getHp());

					battle.setVidaCreador(battle.getVidaCreador() + porcentaje);
					battle.setTurno("Retador");

					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));					
				}				
			}
			else if( item.getTipo().equals("Pocion") )
			{
				int porcentaje = ( (jugador.getMp()*item.getIndex())/100 );
				
				if( battle.getPsinergiaCreador() + porcentaje >= jugador.getMp() )
				{
					labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					barraPsinergia.setValues(listNumber);
					battle.setPsinergiaCreador(jugador.getMp());					
				}
				else
				{
					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getPsinergiaCreador() + porcentaje );
					listNumber.add( jugador.getMp() - (battle.getPsinergiaCreador() + porcentaje) );
					barraPsinergia.setValues(listNumber);
					
					labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
					battle.setPsinergiaCreador(battle.getPsinergiaCreador() + porcentaje);	
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
				}
			}
			else if( item.getTipo().equals("Bomba") )
			{
				Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
				List<PersonajeItem> listItem = criteria.list();		
				
				//Defensa Oponente
				int defensa = 0;		
				for( int i = 0; i < listItem.size(); i++ )
				{
					if( listItem.get(i).getItemRef().getTipo().equals("Armadura") && (listItem.get(i).getPersonajeRef().getId() == jugadorOponente.getId()) )
						defensa = listItem.get(i).getItemRef().getIndex();
				}
				
				int damage =  item.getIndex() - ( (item.getIndex()*defensa)/100 );
				
				if( battle.getVidaRetador() - damage <= 0 )
				{
					listNumber = new ArrayList<Number>();
					listNumber.add(0);
					listNumber.add(150);
					barraVida2.setValues(listNumber);

					criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					battle.setVidaRetador(0);
					battle.setTurno("Retador");
					lblSec.setText("21");
					
					finalBattle();
				}
				else
				{	
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaRetador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaRetador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaRetador(battle.getVidaRetador() - damage);
					
					criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
				}
			}
			
			battle.setTurno("Retador");
			lblSec.setText("21");
			
		}
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			if( item.getTipo().equals("Medicina") )
			{
				int porcentaje = ( (jugador.getHp()*item.getIndex())/100 );

				if( battle.getVidaRetador() + porcentaje >= jugador.getHp() )
				{
					labelHp.setText(jugador.getHp() + "/" + jugador.getHp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					barraVida1.setValues(listNumber);
					battle.setVidaRetador(jugador.getHp());
				}
				else
				{
					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getVidaRetador() + porcentaje );
					listNumber.add( jugador.getHp() - (battle.getVidaRetador() + porcentaje) );
					barraVida1.setValues(listNumber);
					
					labelHp.setText(barraVida1.getValues().get(0).intValue() + "/" + jugador.getHp());

					battle.setVidaRetador(battle.getVidaRetador() + porcentaje);
					battle.setTurno("Creador");

					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));					
				}				
			}
			else if( item.getTipo().equals("Pocion") )
			{
				int porcentaje = ( (jugador.getMp()*item.getIndex())/100 );
				
				if( battle.getPsinergiaRetador() + porcentaje >= jugador.getMp() )
				{
					labelCp.setText(jugador.getMp() + "/" + jugador.getMp());	
					
					listNumber = new ArrayList<Number>();
					listNumber.add(150);
					listNumber.add(0);
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					barraPsinergia.setValues(listNumber);
					battle.setPsinergiaRetador(jugador.getMp());					
				}
				else
				{
					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getPsinergiaRetador() + porcentaje );
					listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() + porcentaje) );
					barraPsinergia.setValues(listNumber);
					
					labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + jugador.getMp());
					battle.setPsinergiaRetador(battle.getPsinergiaRetador() + porcentaje);	
					
					Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
				}
			}
			else if( item.getTipo().equals("Bomba") )
			{
				Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
				List<PersonajeItem> listItem = criteria.list();		
				
				//Defensa Oponente
				int defensa = 0;		
				for( int i = 0; i < listItem.size(); i++ )
				{
					if( listItem.get(i).getItemRef().getTipo().equals("Armadura") && (listItem.get(i).getPersonajeRef().getId() == jugadorOponente.getId()) )
						defensa = listItem.get(i).getItemRef().getIndex();
				}
				
				int damage =  item.getIndex() - ( (item.getIndex()*defensa)/100 );
				
				if( battle.getVidaCreador() - damage <= 0 )
				{
					listNumber = new ArrayList<Number>();
					listNumber.add(0);
					listNumber.add(150);
					barraVida2.setValues(listNumber);

					criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
					
					battle.setVidaCreador(0);
					battle.setTurno("Creador");
					lblSec.setText("21");
					
					finalBattle();
				}
				else
				{	
					listNumber = new ArrayList<Number>();
					listNumber.add(battle.getVidaCreador() - damage); 
					listNumber.add(jugadorOponente.getHp() - (battle.getVidaCreador() - damage) );

					barraVida2.setValues(listNumber);
					battle.setVidaCreador(battle.getVidaCreador() - damage);
					
					criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("personajeRef", jugador), Restrictions.eq("itemRef", item)));
					List<PersonajeItem> pItem = criteria.list();
					
					session.delete(pItem.get(0));
				}
			}
			
			battle.setTurno("Creador");
			lblSec.setText("21");
			
		}
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
		
		battle.setTipoAtaque("Items");
		
		session.getTransaction().commit();
		session.close();
		
		ventanaItem.userClose();
		
		colTimeBotonera.removeAll();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		
	}
	
	private Column toolTipItem(Item items)
	{
		
		Column col = new Column();
		col.setBorder(new Border(3, Color.BLACK, Border.STYLE_RIDGE));
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(5, 5, 5, 5));
		col.setBackground(new Color(226,211,161));
		
	    ColumnLayoutData cld;
		
		Label lbl = new Label();
	    cld = new ColumnLayoutData();
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    lbl.setLayoutData(cld);
		lbl.setText(items.getName());
		col.add(lbl);
		
		if( items.getTipo().equals("Pocion") )
		{
			lbl = new Label();
			lbl.setForeground(Color.BLUE);
			lbl.setText("Efecto: " + items.getIndex());
			col.add(lbl);
		}
		else if( items.getTipo().equals("Medicina") )
		{
			lbl = new Label();
			lbl.setForeground(Color.BLUE);
			lbl.setText("Efecto: " + items.getIndex());
			col.add(lbl);
		}
		else if( items.getTipo().equals("Bomba") )
		{
			lbl = new Label();
			lbl.setForeground(Color.RED);
			lbl.setText("Daño: " + items.getIndex());
			col.add(lbl);
		}
		
		return col;
	}
	
	private void finalBattle()
	{
		
		checkServerPush.end();
		
		final WindowPane ventanaFinal = new WindowPane();
		ventanaFinal.setStyle(StyleWindow.ACADEMY_STYLE);
		ventanaFinal.setModal(true);
		ventanaFinal.setMovable(false);
		ventanaFinal.setResizable(false);
		ventanaFinal.setClosable(false);
		ventanaFinal.setWidth(new Extent(400));
		ventanaFinal.setHeight(new Extent(200));
		
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			if( battle.getVidaRetador() == 0 )
			{		
				ventanaFinal.setTitle("VICTORIA");

				int xp = 50;
				int gold = 500;
				
				ventanaFinal.add(validateRecompensa(ventanaFinal, xp, gold, "VICTORIA!"));			
				add(ventanaFinal);
				
				validateLevel(xp, gold);
				
				battle.setVictoria(jugador.getId());
				battle.setDerrota(jugadorOponente.getId());
			}
			if( battle.getVidaCreador() == 0 )
			{	
				ventanaFinal.setTitle("DERROTA");
				
				int xp = 3;
				
				ventanaFinal.add(validateRecompensa(ventanaFinal, xp, 0, "PERDISTE!"));			
				add(ventanaFinal);
				
				validateLevel(xp, 0);
				
				battle.setDerrota(jugador.getId());
				battle.setVictoria(jugadorOponente.getId());
			}
		}		
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			if( battle.getVidaCreador() == 0 )
			{
				ventanaFinal.setTitle("VICTORIA");

				int xp = 50;
				int gold = 500;

				ventanaFinal.add(validateRecompensa(ventanaFinal, xp, gold, "VICTORIA!"));
				add(ventanaFinal);
				
				validateLevel(xp, gold);
				
				battle.setVictoria(jugador.getId());
				battle.setDerrota(jugadorOponente.getId());
			}
			if( battle.getVidaRetador() == 0 )
			{
				ventanaFinal.setTitle("DERROTA");

				int xp = 3;

				ventanaFinal.add(validateRecompensa(ventanaFinal, xp, 0, "PERDISTE!"));				
				add(ventanaFinal);
				
				validateLevel(xp, 0);
				
				battle.setDerrota(jugador.getId());
				battle.setVictoria(jugadorOponente.getId());
			}			
		}
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<ChatBatalla> list =  session.createCriteria(ChatBatalla.class).add(Restrictions.eq("batallaChatRef", battle)).addOrder(Order.asc("id")).list();

		for (ChatBatalla obj : list) {

				session.delete(obj);
		}
		session.getTransaction().commit();
		session.close();
	}
	
	private Column validateRecompensa(final WindowPane ventanaFinal, int xp, int gold,String titulo)
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(15));
		
	    ColumnLayoutData cld;
	    cld = new ColumnLayoutData();
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		col.setLayoutData(cld);
		
		col.add(cartelRecompensa(xp, gold, titulo));

		Button btnAceptar = new Button();
		btnAceptar.setLayoutData(cld);
		btnAceptar.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Util/ok.gif"));

		btnAceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				ventanaFinal.userClose();
				removeAll();
				add(new PreArena());
			}
		});				
		col.add(btnAceptar);
		
		return col;
	}
	
	private Column cartelRecompensa(int xp, int gold, String titulo)
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(15));

		if( titulo.equals("VICTORIA!") )
		{
			Label lbl = new Label();
			lbl.setText(titulo);
			lbl.setFont(new Font(null, 1, new Extent(18)));
			col.add(lbl);
			
			lbl = new Label();
			lbl.setForeground(Color.GREEN);
			lbl.setFont(new Font(null, 1, new Extent(16)));
			lbl.setText("Obtienes de XP: " + xp);
			col.add(lbl);

			lbl = new Label();
			lbl.setForeground(Color.YELLOW);
			lbl.setFont(new Font(null, 1, new Extent(16)));
			lbl.setText("Obtienes Oro: " + gold);
			col.add(lbl);

		}
		else
		{
			Label lbl = new Label();
			lbl.setText("PERDISTE!");
			lbl.setFont(new Font(null, 1, new Extent(18)));
			col.add(lbl);
			
			lbl = new Label();
			lbl.setForeground(Color.GREEN);
			lbl.setFont(new Font(null, 1, new Extent(16)));
			lbl.setText("Obtienes de XP: " + xp);
			col.add(lbl);	
		}
	    
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private void validateLevel(int xp, int gold)
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		jugador = (Personaje) session.load(Personaje.class, jugador.getId());
		
		nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() + 1));
						
		if( jugador.getXp() + xp >= nivelExp.getCantidadExp() )
		{
			jugador.setXp((jugador.getXp() + xp) - nivelExp.getCantidadExp());
			jugador.setLevel(jugador.getLevel() + 1);
			jugador.setGold(jugador.getGold() + gold);			
			
			labelLevel.setText("Lv. "+ jugador.getLevel());
			
			labelHp.setText(barraVida1.getValues().get(0).intValue() + "/" + (jugador.getHp() + vidaHp));
			labelCp.setText(barraPsinergia.getValues().get(0).intValue() + "/" + (jugador.getMp() + psinergiaMp));
			
			MessageLevel msgLevel = new MessageLevel(jugador,vidaHp,psinergiaMp);
			add(msgLevel);
			
		}
		else
		{
			jugador.setXp(jugador.getXp() + xp);
			jugador.setGold(jugador.getGold() + gold);
			
		}
		
		session.getTransaction().commit();
		session.close();
		
		listNumber = new ArrayList<Number>();
		listNumber.add(jugador.getXp() + xp);
		listNumber.add(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
		barraXp.setValues(listNumber);

		labelXp.setText(jugador.getXp() + "/" + nivelExp.getCantidadExp());

		labelGold.setText(" " + jugador.getGold());
		
	}
	
	private Column panelLetrero()
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/panelaviso.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(300));
		panel.setHeight(new Extent(50));
		panel.setBackgroundImage(imgF);
		
		Column col = new Column();
		col.setInsets(new Insets(15,15,15,15));
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		labelOponente = new Label();
		labelOponente.setFont(new Font(null, 1, new Extent(15)));
		row.add(labelOponente);
		
		Label lbl = new Label();
		lbl.setFont(new Font(null, 1, new Extent(15)));
		lbl.setText("ataco");
		row.add(lbl);
		
		labelPoder = new Label();
		labelPoder.setFont(new Font(null, 1, new Extent(15)));
		row.add(labelPoder);
		
		col.add(row);
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column marcoAviso()
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		
		col.add(panelAviso());
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column panelAviso()
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		panel.setWidth(new Extent(300));
		panel.setHeight(new Extent(50));
		
		Column col = new Column();
				
		col.add(colPanel());
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column colPanel()
	{
		colPanel = new Column();
		
		return colPanel;
	}
	
	public Column initChat(){

		Panel panel = new Panel();

		ImageReference w = ImageReferenceCache.getInstance().getImageReference("Images/Fondos/marcochat2.png");	
		ImageReference image = w;
		FillImage imagef = new FillImage(image);

		panel.setWidth(new Extent(350));
		panel.setHeight(new Extent(129));
		panel.setBackgroundImage(imagef);

		Column colp = new Column();
		colp.setCellSpacing(new Extent(5));

		Column colp2 = new Column();
		Row row = new Row();

		row.setCellSpacing(new Extent(20));
		row.add(panelMensajes());
		colp2.add(row);

		colp.add(colp2);

		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(panelTexto());

		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnEnviarClicked();

			}
		});
				
		row.add(panelBtnEnviar());

		colp.add(row);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;

	}
		
	private void btnEnviarClicked() {

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		ChatBatalla chatBatallaBean = new ChatBatalla();

		chatBatallaBean.setMensaje(txtMsg.getText());
		chatBatallaBean.setLogin(usuario.getLogin());
		chatBatallaBean.setBatallaChatRef(battle);

		session.save(chatBatallaBean);

		session.getTransaction().commit();
		session.close();

		txtMsg.setText("");

	}
	
	
	public Component panelMensajes(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(90));
		panel.setWidth(new Extent(330));
		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(40, 18, 5, 5));
		colp.add(txtCharla);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;
	}
	
	public Component panelTexto(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(70));
		panel.setWidth(new Extent(200));

		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(40, 0, 5, 10));
		colp.add(txtMsg);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;

	}
		
	public Component panelBtnEnviar(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(80));
		panel.setWidth(new Extent(60));
		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(5, 5, 5, 5));
		colp.add(btnEnviar);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;

	}
		
	public void mostrarMensajes() {

		txtCharla.setText("");

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		List<ChatBatalla> list =  session.createCriteria(ChatBatalla.class).add(Restrictions.eq("batallaChatRef", battle)).addOrder(Order.asc("id")).list();

		session.getTransaction().commit();
		session.close();

		for (ChatBatalla obj : list)
			txtCharla.setText( obj.getLogin() + ">>" + obj.getMensaje() + "\n" + txtCharla.getText());
	}

}
