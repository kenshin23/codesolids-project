package codesolids.gui.arena;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import codesolids.bd.clases.Batalla;
import codesolids.bd.clases.Invitacion;
import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.batalla.Desktop;
import codesolids.gui.batalla.DesktopGif;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.TestTableModel;
import codesolids.util.TimedServerPush;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Fernando Osuna
 * 
 */

@SuppressWarnings("serial")
public class PreArena extends ContentPane{
	private Personaje personaje;
	private Label lblData;
	private int actual = 0;
	private TestTableModel tableDtaModelPersonaje;
	private TestTableModel tableDtaModelInvitacion;
	List<Personaje> results = new ArrayList<Personaje>();
	List<Invitacion> resultsI = new ArrayList<Invitacion>();

	private HtmlLayout htmlLayout;

	TaskQueueHandle taskQueue;
	TimedServerPush inviteServerPush;
	//Aqui
	public PreArena() {
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		taskQueue = ApplicationInstance.getActive().createTaskQueue();
		personaje = app.getPersonaje();
		initGUI();
	}

	private void initGUI() {
		add(initPreArena());
	}

	public Component initPreArena(){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("prearena.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");		
		Row menu = new Row();

		Button returnButton = new Button();
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
        returnButton.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/regresar.png")));
        returnButton.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/regresarMouseOver.png"))));
        returnButton.setRolloverEnabled(true);
        returnButton.setHeight(new Extent(27));
        returnButton.setWidth(new Extent(103));
		returnButton.setToolTipText("Regresar al Mapa Principal");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				inviteServerPush.end();
				buttonExitClicked(e);				
			}
		});
		menu.add(returnButton);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);

		hld = new HtmlLayoutData("descrip");
		menu = new Row();
		lblData = new Label("Usuarios en linea");
		lblData.setForeground(Color.WHITE);
		menu.add(lblData);
		menu.setCellSpacing(new Extent(250));
		lblData = new Label("Invitaciones recibidas");
		lblData.setForeground(Color.WHITE);
		menu.add(lblData);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);

		hld = new HtmlLayoutData("central");

		Row rowCentral = new Row();
		tableDtaModelPersonaje = new TestTableModel();
		tableDtaModelInvitacion = new TestTableModel();

		ApplicationInstance app = ApplicationInstance.getActive();
		inviteServerPush = new TimedServerPush(1000, app, taskQueue, new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run(){
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				String queryStr;
				Query query;
				Calendar cal = Calendar.getInstance(); // La fecha actual
				//			  	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());

				if(actual == 0){
					queryStr = "UPDATE t_personaje SET arena = :date WHERE id = :idPlayer";
					query = session.createSQLQuery(queryStr);
					query.setCalendar("date", cal);
					query.setInteger("idPlayer", personaje.getId());
					query.executeUpdate();
					actual = 1;
				}
				actual++;
				if (actual == 29){
					actual = 0;
				}

				cal.add(Calendar.MINUTE, -1); // La fecha actual menos un minuto

				queryStr = "FROM Personaje WHERE arena >= :oneMinuteAgo ORDER BY id ASC";
				query = session.createQuery(queryStr);
				query.setCalendar("oneMinuteAgo", cal);

				List<Object> resultQuery =  query.list();

				createListTable(resultQuery);
				consultEstado();

				session.getTransaction().commit();			  	        
				session.close();
			}
		});

		activate();

		rowCentral.add(createTable(tableDtaModelPersonaje, initTableColModel(1)));
		rowCentral.add(createTable(tableDtaModelInvitacion, initTableColModel(2)));
		rowCentral.setLayoutData(hld);
		htmlLayout.add(rowCentral);

		return htmlLayout;		
	}

	public void createListTable(List<Object> resultQuery) {
		Iterator<Object> iter = resultQuery.iterator();
		if (!iter.hasNext()) {
			return;
		}
		results.clear();
		tableDtaModelPersonaje.clear();
		while (iter.hasNext()) {  	        
			Personaje per = (Personaje) iter.next();
			results.add(per);  	            
		}

		for (int i = 0; i < results.size(); i++) {
			tableDtaModelPersonaje.add(results.get(i));
		}
		consultInvitations();    		
	}

	public Panel createTable(TestTableModel tableDtaModel, TableColModel initTableColModel) {

		Panel panel = new Panel();
		panel.setInsets(new Insets(100, 0, 100, 0));
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setInsets(new Insets(0, 0, 0, 0));
		col.setCellSpacing(new Extent(10));

		TableColModel tableColModel = initTableColModel;
		TableSelModel tableSelModel = new TableSelModel();
		tableDtaModel.setEditable(true);
		tableDtaModel.setPageSize(14);

		ETable table = new ETable();
		table.setTableDtaModel(tableDtaModel);
		table.setTableColModel(tableColModel);
		table.setTableSelModel(tableSelModel);
		table.setEasyview(false);
		table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
		col.add(table);

		Row row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);

		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
		tableNavigation.setForeground(Color.WHITE);
		row.add(tableNavigation);

		col.add(row);
		panel.add(col);
		return panel;

	}

	private TableColModel initTableColModel(final int tipo) {

		TableColModel tableColModel = new TableColModel();
		TableColumn tableColumn;
		LabelCellRenderer lcr;

		tableColumn = new TableColumn(){      
			@Override
			public Object getValue(ETable table, Object element) {
				Object obj = new Object();
				if(tipo == 1){
					Personaje per = (Personaje) element;
					obj = per.getUsuarioRef().getLogin();
				}
				else if(tipo == 2){
					Invitacion iv = (Invitacion) element;
					obj = iv.getPersonajeGeneratesRef().getUsuarioRef().getLogin();
				}
				return obj;
			}
		};

		lcr = new LabelCellRenderer();
		lcr.setBackground(new Color(87, 205, 211));
		lcr.setForeground(Color.WHITE);
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		tableColumn.setHeadCellRenderer(lcr);

		lcr = new LabelCellRenderer();
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));

		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn.setWidth(new Extent(50));
		tableColumn.setHeadValue("Usuario");

		tableColumn = new TableColumn(){      
			@Override
			public Object getValue(ETable table, Object element) {
				Object obj = new Object();
				if(tipo == 1){
					Personaje per = (Personaje) element;
					obj = per.getTipo();
				}
				else if(tipo == 2){
					Invitacion iv = (Invitacion) element;
					obj = iv.getPersonajeGeneratesRef().getTipo();
				}
				return obj;
			}
		};

		lcr = new LabelCellRenderer();
		lcr.setBackground(new Color(87, 205, 211));
		lcr.setForeground(Color.WHITE);
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		tableColumn.setHeadCellRenderer(lcr);

		lcr = new LabelCellRenderer();
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));

		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn.setWidth(new Extent(50));
		tableColumn.setHeadValue("Tipo");

		tableColumn = new TableColumn(){      
			@Override
			public Object getValue(ETable table, Object element) {
				Object obj = new Object();
				if(tipo == 1){
					Personaje per = (Personaje) element;
					obj = per.getLevel();
				}
				else if(tipo == 2){
					Invitacion iv = (Invitacion) element;
					obj = iv.getPersonajeGeneratesRef().getLevel();
				}
				return obj;
			}
		};

		lcr = new LabelCellRenderer();
		lcr.setBackground(new Color(87, 205, 211));
		lcr.setForeground(Color.WHITE);
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		tableColumn.setHeadCellRenderer(lcr);

		lcr = new LabelCellRenderer();
		lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));

		tableColumn.setDataCellRenderer(lcr);
		tableColModel.getTableColumnList().add(tableColumn);

		tableColumn.setWidth(new Extent(50));
		tableColumn.setHeadValue("Nivel");

		tableColumn = new TableColumn();
		tableColumn.setWidth(new Extent(100));
		tableColumn.setHeadValue("");

		lcr = new LabelCellRenderer();
		lcr.setBackground(new Color(87, 205, 211));
		tableColumn.setHeadCellRenderer(lcr);

		if(tipo == 1)
			tableColumn.setDataCellRenderer(initNestedCellRenderer1());
		else if(tipo == 2)
			tableColumn.setDataCellRenderer(initNestedCellRenderer2());
		tableColModel.getTableColumnList().add(tableColumn);

		return tableColModel;

	}

	private CellRenderer initNestedCellRenderer1() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				Personaje per = (Personaje) tableDtaModelPersonaje.getElementAt(row);
				
				Button ret = new Button();
		        ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/invitar.png")));
		        ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/invitarMouseOver.png"))));
		        ret.setDisabledBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/invitarDes.png"))));
		        ret.setRolloverEnabled(true);
		        ret.setHeight(new Extent(30));
		        ret.setWidth(new Extent(100));
				ret.setEnabled(editable);
				ret.setToolTipText("Invitar");
				final Invitacion invitation = new Invitacion();
				invitation.setPersonajeGeneratesRef(personaje);
				invitation.setPersonajeReceivesRef(per);
				if (personaje.getUsuarioRef().getId() != per.getUsuarioRef().getId() )
				{
					ret.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							btnInviteClicked(invitation);
						}
					});
				}

				else{
					ret.setVisible(false);	        	  
				}
				consultBtn(ret, invitation);
				return ret;
			}
		});
		
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          final Personaje per = (Personaje) tableDtaModelPersonaje.getElementAt(row);
	          Button ret = new Button();
	          ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/estd.png");
	          ret.setIcon(imgR);
	          ret.setToolTipText("Estadisticas");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
        	  ret.addActionListener(new ActionListener() {
    			  public void actionPerformed(ActionEvent e) {
    				  btnEstadisticaClicked(per);
    			  }
    		  });

	          return ret;
	        }
	    });

		return nestedCellRenderer;
	}

	private CellRenderer initNestedCellRenderer2() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				final Invitacion inv = (Invitacion) tableDtaModelInvitacion.getElementAt(row);
				Button ret = new Button();
		        ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/acept.png")));
		        ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/aceptMouseOver.png"))));
		        ret.setRolloverEnabled(true);
		        ret.setHeight(new Extent(24));
		        ret.setWidth(new Extent(49));
				ret.setEnabled(editable);
				ret.setToolTipText("Aceptar");

				if (personaje.getUsuarioRef() != inv.getPersonajeGeneratesRef().getUsuarioRef() )
				{	  
					ret.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							BtnClicked(row);
						}	        		  
						private void BtnClicked(int row) {	        				
							inviteServerPush.end();

							//Va a la Batalla!!
							Session session = SessionHibernate.getInstance().getSession();
							session.beginTransaction();

							Batalla battle = new Batalla();

							Personaje pJuagadorCreate = (Personaje) session.load(Personaje.class, inv.getPersonajeGeneratesRef().getId()); 
							Personaje pJuagadorRetador = (Personaje) session.load(Personaje.class, inv.getPersonajeReceivesRef().getId());

							battle.setJugadorCreadorRef(pJuagadorCreate);
							pJuagadorCreate.getCreadores().add(battle);

							battle.setJugadorRetadorRef(pJuagadorRetador);
							pJuagadorRetador.getRetadores().add(battle);

							if( pJuagadorCreate.getSpeed() > pJuagadorRetador.getSpeed() )
								battle.setTurno("Creador");
							else if( pJuagadorCreate.getSpeed() < pJuagadorRetador.getSpeed() )
								battle.setTurno("Retador");
							else
							{
								int numeroAleatorio = (int) (Math.random()*2 + 0);
								if( numeroAleatorio == 0 )
									battle.setTurno("Creador");
								else
									battle.setTurno("Retador");
							}

							battle.setInBattle(true);
							battle.setSecuenciaTurno(1);

							battle.setVidaCreador(pJuagadorCreate.getHp());
							battle.setVidaRetador(pJuagadorRetador.getHp());
							battle.setPsinergiaCreador(pJuagadorCreate.getMp());
							battle.setPsinergiaRetador(pJuagadorRetador.getMp());

							inv.setEstado(true);

							int numeroAleatorio = (int) (Math.random()*3 + 0);	
							
							battle.setEscenario(numeroAleatorio);

							battle.setTiempoMovimiento(0);
							
							session.save(battle);

							session.getTransaction().commit();
							session.close();
							
							UpdateInvitation(inv);
							UpdateOutOfArena();
							consultEstado();
							removeAll();
							
							add(new DesktopGif());
//							add(new Desktop());
						}
					});	        	  
				}
				else{
					ret.setVisible(false);	        	  
				}
				return ret;
			}
		});

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				final Invitacion inv = (Invitacion) tableDtaModelInvitacion.getElementAt(row);
				Button ret = new Button();
		        ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/rech.png")));
		        ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/rechMouseOver.png"))));
		        ret.setRolloverEnabled(true);
		        ret.setHeight(new Extent(24));
		        ret.setWidth(new Extent(49));
				ret.setEnabled(editable);
				ret.setToolTipText("Rechazar");	          
				ret.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnDeleteClicked(inv);
					}
				});	        	  
				return ret;
			}
		});	
		return nestedCellRenderer;
	}
	
	private void btnEstadisticaClicked(Personaje per) {
		
		final WindowPane ventana = new WindowPane();
		ventana.setTitle("Estadisticas del Jugador");
		ventana.setStyle(StyleWindow.DEFAULT_STYLE);
		ventana.setWidth(new Extent(450));
		ventana.setHeight(new Extent(350));
		ventana.setMovable(false);
		ventana.setResizable(false);
		ventana.setModal(false);
		
		add(ventana);
		
		TabPane tabPane = new TabPane();
		ventana.add(tabPane);
		
		tabPane.setTabActiveBackground(new Color(226,211,161));
		tabPane.setTabActiveForeground(Color.BLACK);
		tabPane.setTabActiveFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		
		tabPane.setTabInactiveBackground(new Color(228,228,228));
		tabPane.setTabInactiveForeground(Color.BLACK);
		tabPane.setTabInactiveFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		
		tabPane.setBackground(new Color(226,211,161));
		tabPane.setBorder(new Border(1,Color.BLACK,Border.STYLE_SOLID));
		
		tabPane.setTabWidth(new Extent(180));
		tabPane.setTabHeight(new Extent(20));
		
		tabPane.setTabAlignment(Alignment.ALIGN_CENTER);

		TabPaneLayoutData tpld = new TabPaneLayoutData();
		tpld.setTitle("General");
		
		Column col = new Column();
		col.setLayoutData(tpld);
		col.add(initColumnInfoEstadisticas(per));
		
		tabPane.add(col);
		
		tpld = new TabPaneLayoutData();
		tpld.setTitle("Ultimas 10");
		
		col = new Column();
		col.setLayoutData(tpld);
		col.add(initColumnUltimasEstadisticas(per));
		
		tabPane.add(col);
	}
	
	private Column initColumnInfoEstadisticas(Personaje per){
		Column col = new Column();
		
		col.setInsets(new Insets(10, 10, 10, 10));
		col.setCellSpacing(new Extent(15));
		
		Grid grid = new Grid(2);
		grid.setInsets(new Insets(0, 2, 50, 2));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
  		String queryStr = "FROM Batalla WHERE jugadorcreadorref_id = :idPlayer OR jugadorretadorref_id = :idPlayer ORDER BY id DESC";
  		Query query = session.createQuery(queryStr);
  		query.setInteger("idPlayer", per.getId());
  		
  		List<Batalla> list = query.list();
  		
  		int victorias = 0;
  		int derrotas = 0;
  		for(int i = 0 ; i<list.size(); i++){
  			if(list.get(i).getVictoria() == per.getId())
  				victorias++;
  			else
  				derrotas++;
  		}
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png");
		
		Label lbl = new Label("Batallas totales :", imgR);
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label(""+list.size());
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label("Victorias :", imgR);
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label(""+victorias);
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label("Derrotas :", imgR);
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label(""+derrotas);
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		session.getTransaction().commit();
	    session.close();
		col.add(grid);
		
		return col;
	}
	
	private Column initColumnUltimasEstadisticas(Personaje per){
		Column col = new Column();
		
		col.setInsets(new Insets(10, 10, 10, 10));
		col.setCellSpacing(new Extent(15));
		
		Grid grid = new Grid(3);
		grid.setInsets(new Insets(0, 2, 50, 2));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
  		String queryStr = "FROM Batalla WHERE jugadorcreadorref_id = :idPlayer OR jugadorretadorref_id = :idPlayer ORDER BY id DESC";
  		Query query = session.createQuery(queryStr);
  		query.setInteger("idPlayer", per.getId());
  		
  		List<Batalla> list = query.list();
  		
  		int victorias = 0;
  		int derrotas = 0;
  		for(int i = 0 ; i<list.size(); i++){
  			if(list.get(i).getVictoria() == per.getId())
  				victorias++;
  			else
  				derrotas++;
  		}
		
		Label lbl = new Label("Creador ");
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label("Retador ");
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		lbl = new Label("Resultado ");
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lbl);
		
		int tam = 10;
		if(list.size() < tam)
			tam = list.size();
		
		for(int i = 0; i<tam; i++){
			lbl = new Label(""+list.get(i).getJugadorCreadorRef().getUsuarioRef().getLogin());
			lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
			grid.add(lbl);
			lbl = new Label(""+list.get(i).getJugadorRetadorRef().getUsuarioRef().getLogin());
			lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
			grid.add(lbl);
			if(list.get(i).getVictoria() == per.getId())
				lbl = new Label("Victoria");
			else
				lbl = new Label("Derrota");
			lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
			grid.add(lbl);

		}		
		
		session.getTransaction().commit();
	    session.close();
		col.add(grid);
		
		return col;
	}

	private void activate(){
		inviteServerPush.beg();

	}

	private void UpdateInvitation(Invitacion inv){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		inv.setEstado(true);
		session.update(inv);

		session.getTransaction().commit();
		session.close();

	}
	
	private void consultEstado(){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		List<Invitacion> listA = session.createCriteria(Invitacion.class).list();

		for( int i = 0; i < listA.size(); i++){
			if(listA.get(i).getPersonajeGeneratesRef().getId() == personaje.getId()){
				if(listA.get(i).isEstado() == true){
					inviteServerPush.end();
					UpdateOutOfArena();
					btnDeleteClicked(listA.get(i));
					removeAll();
					
					add(new DesktopGif());
//					add(new Desktop());
				}
			}
		}

		session.getTransaction().commit();
		session.close();
	}

	private void consultBtn(Button btn, Invitacion invitation){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		List<Invitacion> listA = session.createCriteria(Invitacion.class).list();

		for( int i = 0; i < listA.size(); i++){
			if(listA.get(i).getPersonajeGeneratesRef().getId() == invitation.getPersonajeGeneratesRef().getId() && //
					listA.get(i).getPersonajeReceivesRef().getId() == invitation.getPersonajeReceivesRef().getId()	){
				btn.setEnabled(false);
			}
			if(listA.get(i).getPersonajeGeneratesRef().getId() == invitation.getPersonajeReceivesRef().getId() && //
					listA.get(i).getPersonajeReceivesRef().getId() == invitation.getPersonajeGeneratesRef().getId()	){
				btn.setEnabled(false);
			}
		}

		session.getTransaction().commit();
		session.close();
	}

	private void consultInvitations(){

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		personaje = (Personaje) session.load(Personaje.class, personaje.getId());

		Invitacion obj = new Invitacion();
		obj.setPersonajeReceivesRef(personaje);
		List<Invitacion> list = session.createCriteria(Invitacion.class).add(Example.create(obj)).list();

		session.getTransaction().commit();
		session.close();

		resultsI.clear();
		tableDtaModelInvitacion.clear();
		for( int i = 0; i < list.size(); i++){
			if(personaje.getId() == list.get(i).getPersonajeReceivesRef().getId()){
				Invitacion iv = new Invitacion();
				iv.setId(list.get(i).getId());
				iv.setPersonajeGeneratesRef(list.get(i).getPersonajeGeneratesRef());
				iv.setPersonajeReceivesRef(list.get(i).getPersonajeReceivesRef());
				resultsI.add(iv);
			}
		}
		for( int i = 0; i < resultsI.size(); i++){
			tableDtaModelInvitacion.add(resultsI.get(i));
		}
	}

	private void btnInviteClicked(Invitacion invitation) {  		
		Session session = null;
		try {
			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			Invitacion bean = new Invitacion();
			bean.setPersonajeGeneratesRef(invitation.getPersonajeGeneratesRef());
			bean.setPersonajeReceivesRef(invitation.getPersonajeReceivesRef());

			session.save(bean);
		} finally {

			if (session != null) {
				if (session.getTransaction() != null) {
					session.getTransaction().commit();
				}
				session.close();
			}
		}
	}

	private void btnDeleteClicked(Invitacion invitation) {  		
		Session session = null;
		try {
			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			session.delete(invitation);		  

		} finally {

			if (session != null) {
				if (session.getTransaction() != null) {
					session.getTransaction().commit();
				}
				session.close();
			}
		}
	}

	private void UpdateOutOfArena() {

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		String queryStr;
		Query query;
		Calendar cal = Calendar.getInstance(); // La fecha actual
		cal.add(Calendar.MINUTE, -5);
		//  	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());

		queryStr = "UPDATE t_personaje SET arena = :date WHERE id = :idPlayer";
		query = session.createSQLQuery(queryStr);
		query.setCalendar("date", cal);
		query.setInteger("idPlayer", personaje.getId());
		query.executeUpdate();

		personaje = (Personaje) session.load(Personaje.class, personaje.getId());

		Invitacion obj = new Invitacion();
		obj.setPersonajeReceivesRef(personaje);

		List<Invitacion> list = session.createCriteria(Invitacion.class).list();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getPersonajeGeneratesRef() == personaje && list.get(i).isEstado() == false){
				session.delete(list.get(i));
			}
			if(list.get(i).getPersonajeReceivesRef() == personaje && list.get(i).isEstado() == false){
				session.delete(list.get(i));
			}
		}

		session.getTransaction().commit();			  	        
		session.close();
	}

	private void buttonExitClicked(ActionEvent e) {	
		UpdateOutOfArena();
		removeAll();
		add(new MapaDesktop());
	}
}