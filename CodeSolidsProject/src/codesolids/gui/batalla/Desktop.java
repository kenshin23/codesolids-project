package codesolids.gui.batalla;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.informagen.echo.app.CapacityBar;

import codesolids.bd.clases.Ataque;
import codesolids.bd.clases.Batalla;
import codesolids.bd.clases.ChatBatalla;
import codesolids.bd.clases.Nivel;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.arena.PreArena;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.TimedServerPush;

import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;
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
import nextapp.echo.extras.app.ToolTipContainer;

/**
 * 
 * @author Antonio López
 * @Colaborador Jose Luis Perez
 *
 */

public class Desktop extends ContentPane{
	
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
	
	private List<Number> listCooldown;
	
	public Desktop()
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
		

		txtMsg = new TextArea();
		txtMsg.setMaximumLength(100);
		txtMsg.setWidth(new Extent(400));
		txtMsg.setHeight(new Extent(14));

		setBackground(Color.WHITE);
		
		HtmlLayout retHtmlLayout;
		
		try {
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templatebattle.html"), "UTF-8");
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
		{
			listCooldown.add(0);
		}
		
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
			{
				colTimeBotonera.setVisible(true);
			}
		}
		else if( battle.getTurno().equals("Retador") )
		{
			if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
			{
				colTimeBotonera.setVisible(true);
			}
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
						colTimeBotonera.setVisible(true);
						
						if( battle.getVidaCreador() == 0 )
						{
							finalBattle();

							session = SessionHibernate.getInstance().getSession();
							session.beginTransaction();
							
							Personaje pJugador = (Personaje) session.load(Personaje.class, jugador.getId());
							
							pJugador.setXp(jugador.getXp());
							pJugador.setGold(jugador.getGold());
							pJugador.setLevel(jugador.getLevel());

							battle = (Batalla) session.load(Batalla.class, battle.getId());

							battle.setInBattle(false);

							session.getTransaction().commit();
							session.close();
							checkServerPush.end();
						}
						else
						{
							listNumber = new ArrayList<Number>();
							listNumber.add(battle.getVidaCreador()); 
							listNumber.add(jugador.getHp() - battle.getVidaCreador());

							barraVida1.setValues(listNumber);

							labelHp.setText(jugador.getHp() + "/" + battle.getVidaCreador());
						}
						timeTurno();
					}
				}
				else if( battle.getTurno().equals("Retador") )
				{
					if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
					{
						colTimeBotonera.setVisible(true);
						
						if( battle.getVidaRetador() == 0 )
						{
							finalBattle();
							
							session = SessionHibernate.getInstance().getSession();
							session.beginTransaction();
							
							Personaje pJugador = (Personaje) session.load(Personaje.class, jugador.getId());
							
							pJugador.setXp(jugador.getXp());
							pJugador.setGold(jugador.getGold());
							pJugador.setLevel(jugador.getLevel());
							
							battle = (Batalla) session.load(Batalla.class, battle.getId());

							battle.setInBattle(false);
							
							session.getTransaction().commit();
							session.close();
							checkServerPush.end();
						}
						else
						{
							listNumber = new ArrayList<Number>();
							listNumber.add(battle.getVidaRetador()); 
							listNumber.add(jugador.getHp() - battle.getVidaRetador());

							barraVida1.setValues(listNumber);
							labelHp.setText(jugador.getHp() + "/" + battle.getVidaRetador());
						}
						timeTurno();
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
		
		ImageReference imgR = new ResourceImageReference("Images/Util/p_estadoBattle.png");
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
		
		lbl = new Label();
		lbl.setForeground(Color.YELLOW);
		lbl.setText("Lv. "+ jugador.getLevel());
		row.add(lbl);
		
        Panel panelGold = new Panel();
        panelGold.setWidth(new Extent(150));

        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));

		imgR = new ResourceImageReference("Images/Util/sacomoneda.png");
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
		labelXp.setText(nivelExp.getCantidadExp() + "/" + jugador.getXp());
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
		labelHp.setText(jugador.getHp() + "/" + barraVida1.getValues().get(0).intValue());
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
		labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
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
			jugadorOponente.setDirImage("Images/Personajes/MagoTT.png");
		}
		else if ( jugadorOponente.getTipo().equals("Fuego") )
		{
			jugadorOponente.setDirImage("Images/Personajes/MagoFF.png");
		}
		else if ( jugadorOponente.getTipo().equals("Hielo") )
		{
			jugadorOponente.setDirImage("Images/Personajes/MagoHH.png");
		}
		else
		{
			jugadorOponente.setDirImage("Images/Personajes/GuerreroGG.png");
		}
			
		ImageReference mA = new ResourceImageReference(jugador.getDirImage());
		Label magoA = new Label(mA);
		
		ImageReference mB = new ResourceImageReference(jugadorOponente.getDirImage());
		Label magoB = new Label(mB);
		
		Row rowM = new Row();
		rowM.setCellSpacing(new Extent(500, Extent.PX));
		rowM.add(magoA);
		rowM.add(magoB);

		colStatus.add(rowM);
		
		Row rowA = new Row();

		Row rowB = new Row();
		rowB.setCellSpacing(new Extent(5));
		rowB.add(new Label("Lv. " + jugadorOponente.getLevel()));
		barraVida2 = createBarra(Color.RED,Color.WHITE,jugadorOponente.getHp(),0);
		rowB.add(barraVida2);

		Row row = new Row();
		row.setCellSpacing(new Extent(630));
		row.add(rowA);
		row.add(rowB);

		colStatus.add(row);
		
		return colStatus;	
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
		btnHit.setStyle(Styles1.DEFAULT_STYLE);
		btnHit.setHeight(new Extent(32));
		btnHit.setWidth(new Extent(32));
		btnHit.setIcon(new ResourceImageReference("Images/Poderes/Basico/ataque.png"));
		
		btnHit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnHitClicked();
			}
		});		
		rowBotonera.add(btnHit);
		
		btnLoadCp = new Button();
		btnLoadCp.setStyle(Styles1.DEFAULT_STYLE);
		btnLoadCp.setHeight(new Extent(32));
		btnLoadCp.setWidth(new Extent(32));
		btnLoadCp.setIcon(new ResourceImageReference("Images/Poderes/Basico/manapoint.png"));
		
		btnLoadCp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnLoadCpClicked();
			}
		});		
		rowBotonera.add(btnLoadCp);		
		
		btnItem = new Button();
		btnItem.setStyle(Styles1.DEFAULT_STYLE);
		btnItem.setHeight(new Extent(32));
		btnItem.setWidth(new Extent(32));
		btnItem.setIcon(new ResourceImageReference("Images/Poderes/Basico/items.png"));
		btnItem.setEnabled(true);
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
			btnAttack.setStyle(Styles1.DEFAULT_STYLE);
			btnAttack.setHeight(new Extent(32));
			btnAttack.setWidth(new Extent(32));
			
			btnAttack.setAlignment(Alignment.ALIGN_CENTER);
			btnAttack.setIcon(new ResourceImageReference(list.get(posicion).getDirImage()));
			
			ToolTipContainer toolTip = new ToolTipContainer();
			toolTip.add(btnAttack);			
			toolTip.add(toolTipPower(list.get(posicion)));
			
			colBtn.add(toolTip);
			
			if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
			{
				if( battle.getPsinergiaCreador() >= list.get(posicion).getPsinergia() )
				{
					if( listCooldown.get(posicion).intValue() == (battle.getSecuenciaTurno() + 1) )
					{
						btnAttack.setEnabled(true);
						listCooldown.add(posicion, 0);
					}
					else if ( listCooldown.get(posicion).intValue() > battle.getSecuenciaTurno() )
						btnAttack.setEnabled(false);
					else
						btnAttack.setEnabled(true);
				}
				else
				{
					btnAttack.setEnabled(false);
				}
			}
			else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
			{
				if( battle.getPsinergiaRetador() >= list.get(posicion).getPsinergia() )
				{
					if( listCooldown.get(posicion).intValue() == (battle.getSecuenciaTurno() + 1) )
					{
						btnAttack.setEnabled(true);
						listCooldown.add(posicion, 0);
					}
					else if ( listCooldown.get(posicion).intValue() > battle.getSecuenciaTurno() )
						btnAttack.setEnabled(false);
					else
						btnAttack.setEnabled(true);
				}
				else
				{
					btnAttack.setEnabled(false);
				}
			}
		}
		else
		{
			btnAttack = new Button();
			btnAttack.setStyle(Styles1.DEFAULT_STYLE);
			btnAttack.setHeight(new Extent(32));
			btnAttack.setWidth(new Extent(32));
			
			btnAttack.setIcon(new ResourceImageReference("Images/Util/vacioAtaque.png"));
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
			
			session.getTransaction().commit();
			session.close();

			colTimeBotonera.removeAll();
			colTimeBotonera.add(panelTimeBotonera());
			colTimeBotonera.setVisible(false);

			lblSec.setText("21");
			lblSec.setForeground(Color.GREEN);

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
		
		//Defensa Oponente
		int defensa = 0;
		
		for( int i = 0; i < listItem.size(); i++ )
		{
			if( listItem.get(i).getItemRef().getTipo().equals("Armadura") && (listItem.get(i).getPersonajeRef().getId() == jugadorOponente.getId()) )
			{
				defensa = listItem.get(i).getItemRef().getIndex();
			}
		}

		//Ataque Especial
		int damage = (int) (poder.getDamage() + poder.getDamage()*jugador.getAtaqueEspecial() - (jugadorOponente.getDefensa() + defensa));

		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			if( battle.getVidaRetador() - damage <= 0 )
			{
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);

				battle.setVidaRetador(0);
				finalBattle();

				battle.setTurno("Retador");
				lblSec.setText("21");
			}
			else
			{	
				listCooldown.add(posicion, (poder.getCooldown() + battle.getSecuenciaTurno() + 1));
				
				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getVidaRetador() - damage); 
				listNumber.add(jugadorOponente.getHp() - (battle.getVidaRetador() - damage) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getPsinergiaCreador() - poder.getPsinergia());
				listNumber.add( jugador.getMp() - (battle.getPsinergiaCreador() - poder.getPsinergia()) );
				barraPsinergia.setValues(listNumber);

				labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());

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
				listNumber = new ArrayList<Number>();
				listNumber.add(0);
				listNumber.add(150);
				barraVida2.setValues(listNumber);

				battle.setVidaCreador(0);
				finalBattle();

				battle.setTurno("Creador");
				lblSec.setText("21");
			}
			else
			{
				listCooldown.add(posicion, (poder.getCooldown() + battle.getSecuenciaTurno() + 1));
				
				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getVidaCreador() - damage); 
				listNumber.add(jugadorOponente.getHp() - (battle.getVidaCreador() - damage) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(battle.getPsinergiaRetador() - poder.getPsinergia());
				listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() - poder.getPsinergia()) );
				barraPsinergia.setValues(listNumber);

				labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());

				battle.setPsinergiaRetador(battle.getPsinergiaRetador() - poder.getPsinergia());
				battle.setVidaCreador(battle.getVidaCreador() - damage);

				battle.setTurno("Creador");
				lblSec.setText("21");
			}
		}
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
		
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
		//Defensa Oponente
		int defensa = 0;
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
			{
				defensa = listItem.get(i).getItemRef().getIndex();
			}
		}
		
		if( flagArma )
		{
			damage = (int) (danoArma + danoArma*jugador.getAtaqueBasico() - (jugadorOponente.getDefensa() + defensa));
		}
		else
		{
			//Yo le doy 20 por defecto, discutir esto!
			damage = (int) (20 + 20*jugador.getAtaqueBasico() - (jugadorOponente.getDefensa() + defensa));
		}
		
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			
			battle.setTurno("Retador");
			if( (battle.getVidaRetador() - damage <= 0) )
			{
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
					listNumber = new ArrayList<Number>();
					listNumber.add(( battle.getPsinergiaCreador() + 15 ));
					listNumber.add( jugador.getMp() - ( battle.getPsinergiaCreador() + 15 ) );
					barraPsinergia.setValues(listNumber);
					
					battle.setPsinergiaCreador(battle.getPsinergiaCreador() + 15 );
					labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
					
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

					listNumber = new ArrayList<Number>();
					listNumber.add( battle.getPsinergiaRetador() + 15 );
					listNumber.add( jugador.getMp() - (battle.getPsinergiaRetador() + 15) );
					barraPsinergia.setValues(listNumber);
					
					battle.setPsinergiaRetador( battle.getPsinergiaRetador() + 15 );
					labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
					
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
				
				labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
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
				
				labelCp.setText(jugador.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
				battle.setPsinergiaRetador(battle.getPsinergiaRetador() + 30);		
			}
		}
		
		if( battle.getTurno().equals(turno) )
			battle.setSecuenciaTurno(battle.getSecuenciaTurno() + 1);
				
		session.getTransaction().commit();
		session.close();
	
		colTimeBotonera.removeAll();
		colTimeBotonera.add(panelTimeBotonera());
		colTimeBotonera.setVisible(false);
		
		lblSec.setText("21");
	}	
	
	private void btnItemClicked() 
	{
		WindowPane ventanaItem = new WindowPane();
		
		add(ventanaItem);
	}
	
	private Column toolTipPower(Poderes poder)
	{
		
		Column col = new Column();
		col.setBorder(new Border(3, Color.BLACK, Border.STYLE_RIDGE));
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(5, 5, 5, 5));
		col.setBackground(new Color(226,211,161));
		
		Label lbl = new Label();
		lbl.setTextPosition(Alignment.ALIGN_CENTER);
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
		col.add(col);
		
		return col;
	}
	
	private void finalBattle()
	{
		
		final WindowPane ventanaFinal = new WindowPane();
		ventanaFinal.setStyle(StyleWindow.ACADEMY_STYLE);
		ventanaFinal.setClosable(false);
				
		if( jugador.getId() == battle.getJugadorCreadorRef().getId() )
		{
			if( battle.getVidaRetador() == 0 )
			{
				ventanaFinal.setTitle("VICTORIA");

				int xp = 50;
				int gold = 500;

				Column col = new Column();
				col.setCellSpacing(new Extent(15));
				
				col.add(cartelRecompensa(xp, gold, "VICTORIA!"));
								
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() + 1));
								
				if( jugador.getXp() + xp > nivelExp.getCantidadExp() )
				{
					jugador.setXp(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
					jugador.setLevel(jugador.getLevel() + 1);
					jugador.setGold(jugador.getGold() + gold);
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

				labelXp.setText(nivelExp.getCantidadExp() + "/" + jugador.getXp());

				jugador.setGold(jugador.getGold() + gold);
				labelGold.setText(" " + jugador.getGold());
	
				Button btnAceptar = new Button("Aceptar");
				btnAceptar.setWidth(new Extent(50));
				btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
				btnAceptar.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent evt) {
						ventanaFinal.userClose();
						removeAll();
						add(new PreArena());
					}
				});				
				col.add(btnAceptar);
				
				ventanaFinal.add(col);
				ventanaFinal.setModal(true);			
	
				add(ventanaFinal);
			}
			if( battle.getVidaCreador() == 0 )
			{
				ventanaFinal.setTitle("DERROTA");
				
				int xp = 3;
				
				Column col = new Column();
				col.setCellSpacing(new Extent(15));
				
				col.add(cartelRecompensa(xp, 0, "PERDISTE!"));
				
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() + 1));
				
				if( jugador.getXp() + xp > nivelExp.getCantidadExp() )
				{
					jugador.setXp(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
					jugador.setLevel(jugador.getLevel() + 1);
				}
				else
				{
					jugador.setXp(jugador.getXp() + xp);
				}

				session.getTransaction().commit();
				session.close();
				
				listNumber = new ArrayList<Number>();
				listNumber.add(jugador.getXp() + xp);
				listNumber.add(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
				barraXp.setValues(listNumber);
				
				labelXp.setText(nivelExp.getCantidadExp() + "/" + jugador.getXp());
				
				Button btnAceptar = new Button("Aceptar");
				btnAceptar.setWidth(new Extent(50));
				btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
				btnAceptar.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent evt) {
						ventanaFinal.userClose();
						removeAll();
						add(new PreArena());
					}
				});
				col.add(btnAceptar);
				
				
				ventanaFinal.add(col);
				ventanaFinal.setModal(true);
				add(ventanaFinal);
			}
		}		
		else if( jugador.getId() == battle.getJugadorRetadorRef().getId() )
		{
			if( battle.getVidaCreador() == 0 )
			{
				ventanaFinal.setTitle("VICTORIA");

				int xp = 50;
				int gold = 500;

				Column col = new Column();
				col.setCellSpacing(new Extent(15));
				
				col.add(cartelRecompensa(xp, gold, "VICTORIA!"));
								
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() + 1));
				
				if( jugador.getXp() + xp > nivelExp.getCantidadExp() )
				{
					jugador.setXp(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
					jugador.setLevel(jugador.getLevel() + 1);
					jugador.setGold(jugador.getGold() + gold);
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
				
				labelXp.setText(nivelExp.getCantidadExp() + "/" + jugador.getXp());

				jugador.setGold(jugador.getGold() + gold);
				labelGold.setText(" " + jugador.getGold());
				
				Button btnAceptar = new Button("Aceptar");
				btnAceptar.setWidth(new Extent(50));
				btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
				btnAceptar.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						ventanaFinal.userClose();
						removeAll();
						add(new PreArena());
					}
				});
				col.add(btnAceptar);

				ventanaFinal.add(col);
				ventanaFinal.setModal(true);
				add(ventanaFinal);
			}
			if( battle.getVidaRetador() == 0 )
			{
				ventanaFinal.setTitle("DERROTA");
				
				int xp = 3;
				
				Column col = new Column();
				col.setCellSpacing(new Extent(15));
				
				col.add(cartelRecompensa(xp, 0, "PERDISTE!"));
								
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				nivelExp = (Nivel) session.load(Nivel.class, (jugador.getLevel() + 1));
				
				if( jugador.getXp() + xp > nivelExp.getCantidadExp() )
				{
					jugador.setXp(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
					jugador.setLevel(jugador.getLevel() + 1);
				}
				else
				{
					jugador.setXp(jugador.getXp() + xp);
				}
				
				session.getTransaction().commit();
				session.close();
				
				listNumber = new ArrayList<Number>();
				listNumber.add(jugador.getXp() + xp);
				listNumber.add(nivelExp.getCantidadExp() - (jugador.getXp() + xp));
				barraXp.setValues(listNumber);
				
				labelXp.setText(nivelExp.getCantidadExp() + "/" + jugador.getXp());
				
				Button btnAceptar = new Button("Aceptar");
				btnAceptar.setWidth(new Extent(50));
				btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
				btnAceptar.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent evt) {
						ventanaFinal.userClose();
						removeAll();
						add(new PreArena());
					}
				});
				col.add(btnAceptar);
				
				ventanaFinal.add(col);
				ventanaFinal.setModal(true);
				add(ventanaFinal);
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
			lbl.setFont(new Font(null, 1, new Extent(12)));
			col.add(lbl);
			
			lbl = new Label();
			lbl.setForeground(Color.GREEN);
			lbl.setFont(new Font(null, 1, new Extent(12)));
			lbl.setText("Obtienes de XP: " + xp);
			col.add(lbl);

			lbl = new Label();
			lbl.setForeground(Color.YELLOW);
			lbl.setFont(new Font(null, 1, new Extent(12)));
			lbl.setText("Obtienes Oro: " + gold);
			col.add(lbl);

		}
		else
		{
			Label lbl = new Label();
			lbl.setText("PERDISTE!");
			lbl.setFont(new Font(null, 1, new Extent(12)));
			col.add(lbl);
			
			lbl = new Label();
			lbl.setForeground(Color.GREEN);
			lbl.setFont(new Font(null, 1, new Extent(12)));
			lbl.setText("Obtienes de XP: " + xp);
			col.add(lbl);	
		}
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column panelLetrero()
	{
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = new ResourceImageReference("Images/Util/panelaviso.png");
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

		ResourceImageReference w = new ResourceImageReference("Images/Fondos/marcochat2.png");	
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

		Criteria criteria;

		List<ChatBatalla> list =  session.createCriteria(ChatBatalla.class).add(Restrictions.eq("batallaChatRef", battle)).addOrder(Order.asc("id")).list();

		session.getTransaction().commit();
		session.close();

		for (ChatBatalla obj : list) {			
				txtCharla.setText( obj.getLogin() + ">>" + obj.getMensaje() + "\n" + txtCharla.getText());			
		}
	}

}
