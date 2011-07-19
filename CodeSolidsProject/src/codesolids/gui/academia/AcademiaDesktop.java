package codesolids.gui.academia;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.informagen.echo.app.CapacityBar;

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
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.util.TestTableModel;
import codesolids.util.TimedServerPush;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.bd.clases.Usuario;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.bd.hibernate.SessionHibernate;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.ImageCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

/**
 * @author: Antonio López
 * 
 */

public class AcademiaDesktop extends ContentPane {
	
	private Usuario usuario;

	private TestTableModel tableDtaModel;
	
	private ETable table;
	
	private Label name;
	private Label damage;
	private Label cooldown;
	private Label psinergia;
	private Label description;
	private Label timeTraining;
	private Label labelGold;
	private Label lblSec;
	private Label lblMin;
	private Label lblHour;
	private Label lblTimeHora;
	
	private boolean flag;
	
	private Column colCartel;
	private Row rowProgress;
	
	private List<Poderes> listPoder;
	
	private Personaje player;

	private PersonajePoderes personajePoderes;
	
	private CapacityBar barProgress;
	private List<Number> listNumber;
	
	private Poderes poderBD;
	
	TimedServerPush timedServer;
	
	TaskQueueHandle taskQueue;
	
	private double fraccionBar;
	private double count = 0;
	
	public AcademiaDesktop() {
		
		taskQueue = ApplicationInstance.getActive().createTaskQueue();
		initGUI();
	
	}

	private void initGUI() {						
		add(initAcademia());
	}
	
	private Component initAcademia()
	{		
		
		HtmlLayout retHtmlLayout1;
		HtmlLayout retHtmlLayout2;
		
		try{
			
			retHtmlLayout1 = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
			retHtmlLayout2 = new HtmlLayout(getClass().getResourceAsStream("templateacademy.html"), "UTF-8");
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
			
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("tabla");
		
		Column colW = new Column();
		colW.add(initTopRow());		
		
		TableColModel tableColModel = initTableColModel();
	    TableSelModel tableSelModel = new TableSelModel();

	    tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(6);
	    
	    table = new ETable();
	    
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);

	    
	    table.setBorder(new Border(1, Color.BLACK, Border.STYLE_RIDGE));
	    table.setInsets(new Insets(5, 2, 5, 2));
	    
	    colW.add(table);
	    
	    colW.setLayoutData(hld);
		
	    retHtmlLayout2.add(colW);
		
		hld = new HtmlLayoutData("navegacion");
		
		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    tableNavigation.setAlignment(Alignment.ALIGN_RIGHT);
	    tableNavigation.setForeground(Color.WHITE);
	    tableNavigation.setInsets(new Insets(0, 0, 95, 80));
	    
	    tableNavigation.setLayoutData(hld);
	    retHtmlLayout1.add(tableNavigation);
	    	    	    
	    colW = new Column();
	    colW.add(initFoot());
	    
	    hld = new HtmlLayoutData("descripcion");
	    colW.setLayoutData(hld);
	    
	    retHtmlLayout1.add(colW);
		
	    loadBD();
	    rowsArrayPoderes();
	    
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Personaje> list = session.createCriteria(Personaje.class).list();
		player = list.get(0);
		
		session.getTransaction().commit();
		session.close();

		if ( player.getLearning() )
		{
			Calendar calIni = Calendar.getInstance();
			Calendar calFin = player.getFechaFin();
			
			if( validateDate(calIni, calFin) == true )
			{
				CreateWindow();
				
				session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();

				String queryStr = "SELECT pp FROM Personaje AS pe, PersonajePoderes AS pp WHERE pe.id = pp.personajeRef AND pe.learning = pp.learnProgreso";
				personajePoderes = (PersonajePoderes) session.createQuery(queryStr).uniqueResult();
				
				personajePoderes.setLearnProgreso(false);
				
				session.getTransaction().commit();
				session.close();
				
				session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				list = session.createCriteria(Personaje.class).list();
				player = list.get(0);
				
				player.setLearning(false);
				
				session.getTransaction().commit();
				session.close();
				
				timedServer.end();
				
			}
			else{
							
				long diferenciaMils = (calFin.getTimeInMillis() - calIni.getTimeInMillis());
				
				long segundos = diferenciaMils / 1000;
				long horas = segundos / 3600;
				segundos -= horas * 3600;
				long minutos = segundos / 60;
				segundos -= minutos * 60;
				
				lblHour = new Label();
				lblHour.setText("" + horas);
				lblMin = new Label();
				lblMin.setText("" + minutos);
				lblSec = new Label();
				lblSec.setText("" + segundos);

				session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();

				String queryStr = "SELECT po FROM Personaje AS pe, PersonajePoderes AS pp, Poderes AS po WHERE pe.id = pp.personajeRef AND pp.poderesRef = po.id AND pe.learning = pp.learnProgreso";
				poderBD = (Poderes) session.createQuery(queryStr).uniqueResult();

				session.getTransaction().commit();
				session.close();

				fraccionBar = (1000.0/(poderBD.getTimeTraining()*3600.0));
				
				long timeEntrenado = ((poderBD.getTimeTraining()*3600) - ((horas*3600) + (minutos*60) + (segundos))); 
				
				count = timeEntrenado*fraccionBar;
				
				lblTimeHora = new Label();
				lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());

				rowProgress = barraProgreso();
				
				btnTrainingClicked();
				timedServer.beg();	
			}
			
		}
		
		btnTrainingClicked();
		
		hld = new HtmlLayoutData("central");
		
		retHtmlLayout2.setLayoutData(hld);

		retHtmlLayout1.add(retHtmlLayout2);
		
		return retHtmlLayout1;
	}
	
	private boolean validateDate(Calendar cal1, Calendar cal2)
	{
		if (cal1.compareTo(cal2) >= 0)
		{
			return true;
		}
		else{
			return false;
		}
	}
		
	private Row initTopRow() {
		
		Row row = new Row();
	    row.setCellSpacing(new Extent(300));
		row.setBackground(new Color(25, 54, 65));
	    
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));
	    
	    Button btnAll = new Button("Todos");
	    btnAll.setStyle(Styles1.DEFAULT_STYLE);
	    btnAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnAllClicked();
            }
        });
	    rowBtn.add(btnAll);
	    
	    Button btnFire = new Button("Fuego");
	    btnFire.setStyle(Styles1.DEFAULT_STYLE);
	    btnFire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnFireClicked();
            }
        });
	    rowBtn.add(btnFire);
	    
	    Button btnIce = new Button("Hielo");
	    btnIce.setStyle(Styles1.DEFAULT_STYLE);
	    btnIce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnIceClicked();
            }
        });
        rowBtn.add(btnIce);

	    Button btnEarth = new Button("Tierra");
	    btnEarth.setStyle(Styles1.DEFAULT_STYLE);
	    btnEarth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnEarthClicked();
            }
        });
        rowBtn.add(btnEarth);
        
	    Button btnCombo = new Button("Combo");
	    btnCombo.setStyle(Styles1.DEFAULT_STYLE);
	    btnCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnComboClicked();
            }
        });
        rowBtn.add(btnCombo);        
              
        
        Panel panel = new Panel();
        panel.setWidth(new Extent(150));
        
        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));
        
        ImageReference imgR = new ResourceImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        rowPanel.add(imgI);
        
        Session session = SessionHibernate.getInstance().getSession();
        session.beginTransaction();
        
        List<Personaje> list = session.createCriteria(Personaje.class).list();
        
        player = new Personaje();
        player = list.get(0);
        
        labelGold = new Label(" "  + player.getGold());
        labelGold.setForeground(Color.YELLOW);
        rowPanel.add(labelGold);
   
        session.getTransaction().commit();
        session.close();
        
        panel.add(rowPanel);
        
        rowBtn.add(panel);
        
        row.add(rowBtn);
        
        
        rowBtn = new Row();

        Button btnExit = new Button("Salir");
        btnExit.setStyle(Styles1.DEFAULT_STYLE);
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeAll();
        		add(new MapaDesktop());
        	}
        });
        rowBtn.add(btnExit);
        row.add(rowBtn);
        
	    return row;
	}
	
	private Column initFoot() {
		
		Panel panel = new Panel();

		ImageReference imgR = new ResourceImageReference("Images/Fondos/cartel.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(505));
		panel.setHeight(new Extent(152));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		
		colCartel.add(initDescription());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.setInsets(new Insets(0,0,0,40));
		colCartel.add(panel);
		
		return colCartel;
	}
	
	private Column initDescription()
	{
		colCartel = new Column(); 
		
		colCartel.setInsets(new Insets(20, 10, 20, 40));
		colCartel.setCellSpacing(new Extent(5));
		
		Column col = new Column();
		
		Label titulo = new Label("Bienvenido a la Academia");
		titulo.setTextPosition(Alignment.ALIGN_CENTER);
		titulo.setToolTipText("Weyard Academy");
		col.add(titulo);
		colCartel.add(col);
		
		col = new Column();
		col.setCellSpacing(new Extent(5));
		col.setInsets(new Insets(20, 10, 20, 0));
		
		col.add(new Label("Aqui podrás entrenar tus poderes de Fuego, Hielo, Tierra y Combo" ));
		col.add(new Label("pero tendrás que pagar una tarifa para usar El Templo Programing,"));
		col.add(new Label("el nivel de entrenamiento es muy fuerte así que preparate para "));
		col.add(new Label("entrenar y salir victorioso en tus batallas."));
		
		colCartel.add(col);		
		
		return colCartel;
	}
	
	private Column DscripRow()
	{
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		
		name = new Label();
		col.add(name);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(20));
		
		damage = new Label();
		damage.setForeground(Color.RED);
		row.add(damage);
		cooldown = new Label();
		cooldown.setForeground(Color.ORANGE);
		row.add(cooldown);
		psinergia = new Label();
		psinergia.setForeground(Color.BLUE);
		row.add(psinergia);
		
		col.add(row);


		timeTraining = new Label("");
		timeTraining.setForeground(new Color(128,0,128));
		col.add(timeTraining);
		
		description = new Label();
		col.add(description);
		
		rowProgress = new Row();
		rowProgress.setCellSpacing(new Extent(10));
		rowProgress.setBorder(new Border(3,new Color(25, 54, 65),Border.STYLE_OUTSET));
		rowProgress.setVisible(false);
		
		rowProgress.add(initBar());
		
		lblHour = new Label();
		lblMin = new Label();
		lblSec = new Label();
		lblTimeHora = new Label();
	    lblTimeHora.setForeground(Color.RED);
	    lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
	    rowProgress.add(lblTimeHora);
		
	    col.add(rowProgress);
		
		return col;
	}
	
	private Row barraProgreso()
	{
		Row rowProg = new Row();
		
		rowProg.setCellSpacing(new Extent(10));
		rowProg.setBorder(new Border(3,new Color(25, 54, 65),Border.STYLE_OUTSET));
		rowProg.setVisible(true);
		
		rowProg.add(initBar());
		
		lblTimeHora = new Label();
	    lblTimeHora.setForeground(Color.RED);
	    lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
	    rowProg.add(lblTimeHora);
		
		return rowProg;
	}
	
	private Column DscripRowLearn()
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		
		name = new Label();
		col.add(name);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(20));
		
		damage = new Label();
		damage.setForeground(Color.RED);
		row.add(damage);
		cooldown = new Label();
		cooldown.setForeground(Color.ORANGE);
		row.add(cooldown);
		psinergia = new Label();
		psinergia.setForeground(Color.BLUE);
		row.add(psinergia);
		
		col.add(row);

		timeTraining = new Label("");
		timeTraining.setForeground(new Color(128,0,128));
		col.add(timeTraining);
		
		description = new Label();
		col.add(description);
		
		rowProgress.setVisible(true);
		col.add(rowProgress);
		
		return col;
	}
	
	private Column DscripRowWait()
	{
		Column col = new Column();
		col.setInsets(new Insets(0,101,0,0));
		
		rowProgress.setVisible(true);
		col.add(rowProgress);
		
		return col;
	}
	
	private Component initBar()
	{
		
		barProgress = new CapacityBar(10,250);
		barProgress.setShowTicks(false);
		
		barProgress.setReflectivity(0);
		
		List<Color> listColor = new ArrayList<Color>();
		listColor.add(Color.RED);
		listColor.add(Color.WHITE);
		barProgress.setColors(listColor);
		
		listNumber = new ArrayList<Number>();
		listNumber.add(0); 
		listNumber.add(1000); 
		barProgress.setValues(listNumber);
		
		return barProgress;
	}
	
	private TableColModel initTableColModel() {		
		
		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getLevel();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Level");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getDirImage();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    ImageCellRenderer icr = new ImageCellRenderer();
	    icr.setBackground(new Color(226,211,161));
	    icr.setWidth(new Extent(15));
	    icr.setHeight(new Extent(15));
	    
	    tableColumn.setDataCellRenderer(icr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(15));
	    tableColumn.setHeadValue("");    
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getName();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(150));
	    tableColumn.setHeadValue("Nombre");
	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getGold();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(new Color(251,255,0));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Oro");
	    
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    tableColumn.setDataCellRenderer(initNestedCellRenderer());
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    return tableColModel;
	}
	
	private CellRenderer initNestedCellRenderer() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
	    
		nestedCellRenderer.setBackground(new Color(226,211,161));
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

			
	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          Button ret = new Button("Ver");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Ver");

	          ret.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	              btnVerClicked(row);
	            }
	          });
	          return ret;
	        }
	    });
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          final Button ret = new Button("Entrenar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Entrenar");

	          final WindowPane ventanaCompra = new WindowPane();
	          ventanaCompra.setTitle("Academia - Compra");
	          ventanaCompra.setWidth(new Extent(300));
	          ventanaCompra.setMaximumWidth(new Extent(300));
	          ventanaCompra.setMaximumHeight(new Extent(150));
	          ventanaCompra.setMovable(false);
	          ventanaCompra.setResizable(false);
	          ventanaCompra.setModal(true);
	          ventanaCompra.setStyle(StyleWindow.ACADEMY_STYLE);

	          Button btnAceptar = new Button("Aceptar");
	          btnAceptar.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
	          btnAceptar.setToolTipText("Aceptar");
	          btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
	          btnAceptar.addActionListener(new ActionListener() {
	        	  public void actionPerformed(ActionEvent evt) {
	        		  ventanaCompra.userClose();
	        	  }
	          });

	          Row rowBtn = new Row();
	          rowBtn.setInsets(new Insets(125, 10, 0, 20));
	          rowBtn.add(btnAceptar);

	          Column colPane = new Column();
	          colPane.setInsets(new Insets(5, 5, 5, 0));
	          colPane.setCellSpacing(new Extent(10));
	          final Label lblText = new Label();
	          colPane.add(lblText);

	          colPane.add(rowBtn);
	          ventanaCompra.add(colPane);
	          
	          final Poderes poder = (Poderes) tableDtaModel.getElementAt(row); 
	          
	          if ( (poder.getUso() == false) && (player.getLevel() >= poder.getLevel()) )
	          {
	        	  
	        	  if( poder.getTipo().equals(player.getTipo()) )
	        	  {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {  
	        				  
	        				  Session session = SessionHibernate.getInstance().getSession();
	        		          session.beginTransaction();
	        		          
	        		          Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id",poder.getId()));
	        		          poderBD = (Poderes) criteria.uniqueResult();
	        		          
	        		          session.getTransaction().commit();
	        		          session.close();
	        				  
	    	        		  if( poderBD.getTimeTraining() > 0 )
	    	        		  {
	    	        			  if((player.getGold() >= poderBD.getGold()) && (player.getLevel() >= poderBD.getLevel()) && (player.getLearning() == false))
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  rowProgress.setVisible(true);

	    	        				  lblHour.setText("" + poderBD.getTimeTraining());
	    	        				  lblMin.setText("0");
	    	        				  lblSec.setText("0");
	    	        				  fraccionBar = (1000.0/(poderBD.getTimeTraining()*3600.0)); 

	    	        				  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
	    	   
		    	        			  timedServer.beg(); 
		    	        			  
		        					  session = SessionHibernate.getInstance().getSession();
		        					  session.beginTransaction();
		        					  
		        					  criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poderBD.getId()));
		        					  
		        					  poderBD = (Poderes) criteria.uniqueResult();
		        					  
		        					  poderBD.setUso(true);
		        					  
		        					  List<Personaje> list = session.createCriteria(Personaje.class).list();
		        			
		        					  player = list.get(0);
		        					  player.setLearning(true);

		        					  player.setGold(player.getGold() - poderBD.getGold());
		        					  labelGold.setText("" + player.getGold());
		        					  
		        					  Calendar cal = Calendar.getInstance();
		        					  player.setFechaInicio(cal);

		        					  cal = Calendar.getInstance();
		        					  cal.add(Calendar.HOUR, poderBD.getTimeTraining());
		        					  player.setFechaFin(cal);

		        					  session.getTransaction().commit();
		        					  session.close();	 
		        					  
		        					  ret.setEnabled(false);
		        					  poder.setUso(true);
		        					  
		        					  session = SessionHibernate.getInstance().getSession();
		        					  session.beginTransaction();
		        					 
		        					  list = session.createCriteria(Personaje.class).list();
		        					  player = new Personaje();
		        					  player = list.get(0);
		        					  
		        					  poderBD = new Poderes();
		        					  criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id",poder.getId()));
			        		          poderBD = (Poderes) criteria.uniqueResult();
		        					  
		        					  personajePoderes = new PersonajePoderes();
		        					  
		        					  personajePoderes.setLearnProgreso(true);
		        					  personajePoderes.setPersonajeRef(player);
		        					  personajePoderes.setPoderesRef(poderBD);
		        					  
		        					  player.getPersonajePoderesList().add(personajePoderes);
		        					  poderBD.getPersonajePoderesList().add(personajePoderes);
		        					  
		        					  session.getTransaction().commit();
		        					  session.close();	 
		        	
		        					  flag = false;
		    	        			  
	    	        			  }
	    	        			  else if ( player.getLearning() == true )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  session = SessionHibernate.getInstance().getSession();
	    	        				  session.beginTransaction();

	    	        				  String queryStr = "SELECT po FROM Personaje AS pe, PersonajePoderes AS pp, Poderes AS po WHERE pe.id = pp.personajeRef AND pp.poderesRef = po.id AND pe.learning = pp.learnProgreso";
	    	        				  poderBD = (Poderes) session.createQuery(queryStr).uniqueResult();
	    	        				 
	    	        				  session.getTransaction().commit();
	    	        				  session.close();
	    	        				  
	    	        				  lblText.setText("Usted esta entrenando " + poderBD.getName());
	    	        				  
	    	        				  add(ventanaCompra);  
	    	        			  }
	    	        			  else if ( player.getGold() < poderBD.getGold() )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted no tiene oro suficiente para entrenar el poder " + poderBD.getName());
		        					  add(ventanaCompra);
	    	        			  }
	    	        			  
	    	        		  }
	    	        		  else
	    	        		  {
	    	        			  if((player.getGold() >= poderBD.getGold()) && (player.getLevel() >= poderBD.getLevel()) && (player.getLearning() == false))
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted compro " + poderBD.getName());
	    	        				  add(ventanaCompra);
	    	        			
		        					  session = SessionHibernate.getInstance().getSession();
		        					  session.beginTransaction();
		        					  
		        					  criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poderBD.getId()));
		        					  
		        					  poderBD = (Poderes) criteria.uniqueResult();
		        					  
		        					  poderBD.setUso(true);
		        					  
		        					  session.getTransaction().commit();
		        					  session.close();	 
		        					  
		        					  poder.setUso(true);
		    	        			  ret.setEnabled(false);
	    	    
		    	        			  session = SessionHibernate.getInstance().getSession();
		        					  session.beginTransaction();
		        					 
		        					  List<Personaje> list = session.createCriteria(Personaje.class).list();
		        					  player = new Personaje();
		        					  player = list.get(0);
		        					
		        					  player.setGold(player.getGold() - poder.getGold());
		        					  labelGold.setText("" + player.getGold());
		        					  
		        					  
		        					  poderBD = new Poderes();
		        					  criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id",poder.getId()));
			        		          poderBD = (Poderes) criteria.uniqueResult();
		        					  
		        					  personajePoderes = new PersonajePoderes();
		        					  
		        					  personajePoderes.setLearnProgreso(false);
		        					  personajePoderes.setPersonajeRef(player);
		        					  personajePoderes.setPoderesRef(poderBD);
		        					  
		        					  player.getPersonajePoderesList().add(personajePoderes);
		        					  poderBD.getPersonajePoderesList().add(personajePoderes);
		        					  
		        					  session.getTransaction().commit();
		        					  session.close();	 
		        				
		    	        			  
	    	        			  }
	    	        			  else if(  player.getLearning() == true )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  session = SessionHibernate.getInstance().getSession();
	    	        				  session.beginTransaction();

	    	        				  String queryStr = "SELECT po FROM Personaje AS pe, PersonajePoderes AS pp, Poderes AS po WHERE pe.id = pp.personajeRef AND pp.poderesRef = po.id AND pe.learning = pp.learnProgreso";
	    	        				  poderBD = (Poderes) session.createQuery(queryStr).uniqueResult();
	    	        				 
	    	        				  session.getTransaction().commit();
	    	        				  session.close();
	    	        				  
	    	        				  lblText.setText("Usted esta entrenando " + poderBD.getName());
	    	        				  
	    	        				  add(ventanaCompra);  
	    	        			  }
	    	        			  else if(player.getGold() < poderBD.getGold())
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted no tiene oro suficiente para entrenar el poder " + poderBD.getName());
	    	        				  add(ventanaCompra);
	    	        				  
	    	        			  }
	    	        		  }
	        				  
	        			  }
	        		  });
	        		  
	        			  btnTrainingClicked();       			  
	        	  }
	        	  else{
	        		  ret.setEnabled(false);  
	        	  }
	          }
	          else{
	        	  ret.setEnabled(false);
	          }
	          return ret;
			}
		});
	
	    return nestedCellRenderer;
	}
	
	private void btnTrainingClicked() {
		
		ApplicationInstance app = ApplicationInstance.getActive();
		
		timedServer = new TimedServerPush(1000, app, taskQueue, new Runnable() {
			  @Override
			  public void run() {
				  if (player.getLearning() == true )
				  {
					  if( ((Integer.parseInt(lblSec.getText())) == 0 ) && ((Integer.parseInt(lblMin.getText())) == 0 ) && ((Integer.parseInt(lblHour.getText())) == 0 ) && (flag == false))
					  {

						  listNumber = new ArrayList<Number>();
						  listNumber.add(1000);
						  listNumber.add(0);
						  barProgress.setValues(listNumber);

						  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());

						  CreateWindow();

						  flag = true;
						  count = 0;
						  fraccionBar = 0;
						  rowProgress.setVisible(false);

						  timedServer.end();
						  
						  Session session = SessionHibernate.getInstance().getSession();
						  session.beginTransaction();

						  String queryStr = "SELECT pp FROM Personaje AS pe, PersonajePoderes AS pp WHERE pe.id = pp.personajeRef AND pe.learning = pp.learnProgreso";
						  personajePoderes = (PersonajePoderes) session.createQuery(queryStr).uniqueResult();

						  personajePoderes.setLearnProgreso(false);

						  session.getTransaction().commit();
						  session.close();

						  session = SessionHibernate.getInstance().getSession();
						  session.beginTransaction();
						  
						  session = SessionHibernate.getInstance().getSession();
						  session.beginTransaction();
						  
						  List<Personaje> list = session.createCriteria(Personaje.class).list();
    					  player = new Personaje();
    					  player = list.get(0);
    					  
    					  player.setLearning(false);
    					  
    					  session.getTransaction().commit();
    					  session.close();	
						  
					  }
					  else
					  {

						  if( (Integer.parseInt(lblSec.getText())) > 0 )
						  {
							  lblSec.setText("" + (Integer.parseInt(lblSec.getText()) - 1));
							  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
						  }
						  else
						  {
							  if( (Integer.parseInt(lblMin.getText())) > 0 )
							  {
								  lblMin.setText("" + (Integer.parseInt(lblMin.getText()) - 1));
								  lblSec.setText("59");
								  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
							  }
							  else
							  {
								  if( (Integer.parseInt(lblHour.getText())) > 0 )
								  {
									  lblHour.setText("" + (Integer.parseInt(lblHour.getText()) - 1));
									  lblMin.setText("59");
									  lblSec.setText("59");
									  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());
								  }
							  }	
						  }

						  listNumber = new ArrayList<Number>();
						  count = count + fraccionBar;
						  listNumber.add(count);
						  listNumber.add(1000-count);
						  barProgress.setValues(listNumber);
						  
					  }
				  }
			  }
		  });

	}
	
	private void CreateWindow()
	{
		final WindowPane ventanaCompra = new WindowPane();
		ventanaCompra.setTitle("Academia - Compra");
		ventanaCompra.setWidth(new Extent(300));
		ventanaCompra.setMaximumWidth(new Extent(300));
		ventanaCompra.setMaximumHeight(new Extent(150));
		ventanaCompra.setMovable(false);
		ventanaCompra.setResizable(false);
		ventanaCompra.setModal(true);
		ventanaCompra.setStyle(StyleWindow.ACADEMY_STYLE);

		Button btnAceptar = new Button("Aceptar");
		btnAceptar.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnAceptar.setToolTipText("Aceptar");
		btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ventanaCompra.userClose();
			}
		});

		Row rowBtn = new Row();
		rowBtn.setInsets(new Insets(125, 10, 0, 20));
		rowBtn.add(btnAceptar);

		Column colPane = new Column();
		colPane.setInsets(new Insets(5, 5, 5, 0));
		colPane.setCellSpacing(new Extent(10));
		Label lblText = new Label();
		lblText.setText("Termino su entrenamieto, has aprendido una nueva habilidad");
		colPane.add(lblText);

		colPane.add(rowBtn);
		
		ventanaCompra.add(colPane);

		add(ventanaCompra);
		  
	}
	
	private void loadBD()
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Poderes> list = session.createCriteria(Poderes.class).addOrder(Order.asc("id")).list();
		
		for(Poderes obj : list)
		{
			Poderes poder = new Poderes();
			
			poder.setId(obj.getId());
			poder.setLevel(obj.getLevel());
			poder.setName(obj.getName());
			poder.setGold(obj.getGold());
			poder.setDamage(obj.getDamage());
			poder.setCooldown(obj.getCooldown());
			poder.setTimeTraining(obj.getTimeTraining());
			poder.setPsinergia(obj.getPsinergia());
			poder.setTipo(obj.getTipo());
			poder.setUso(obj.getUso());
			poder.setDirImage(obj.getDirImage());
						
			tableDtaModel.add(poder);

		}
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	private void btnVerClicked(int row) 
	{
		Poderes poder = (Poderes) tableDtaModel.getElementAt(row);

		if (poder.getTipo().equals("Fuego"))
			infoPowerFire(poder);
		else if(poder.getTipo().equals("Hielo"))
			infoPowerIce(poder);
		else if(poder.getTipo().equals("Tierra"))
			infoPowerEarth(poder);
		else
			infoPowerCombo(poder);
		
	}
	
	private void btnAllClicked() {
			
		if ( player.getLearning() == true )
		{
			colCartel.removeAll();
			colCartel.add(DscripRowWait());
		}
		else
		{
			colCartel.removeAll();
		}
			
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();
		
	}
	
	private void btnFireClicked() {
		
		if ( player.getLearning() == true )
		{
			colCartel.removeAll();
			colCartel.add(DscripRowWait());
		}
		else
		{
			colCartel.removeAll();
		}
		
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("tipo", "Fuego")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();
	
	}
	
	private void btnIceClicked() {
		
		if ( player.getLearning() == true )
		{
			colCartel.removeAll();
			colCartel.add(DscripRowWait());
		}
		else
		{
			colCartel.removeAll();
		}
		
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("tipo", "Hielo")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();

	}
	
	private void btnEarthClicked() {
		
		if ( player.getLearning() == true )
		{
			colCartel.removeAll();
			colCartel.add(DscripRowWait());
		}
		else
		{
			colCartel.removeAll();
		}
		
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("tipo", "Tierra")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();

	}
	
	private void btnComboClicked()
	{

		if ( player.getLearning() == true )
		{
			colCartel.removeAll();
			colCartel.add(DscripRowWait());
		}
		else
		{
			colCartel.removeAll();
		}
		
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("tipo", "Combo")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();
		
	}
	
	private void rowsArrayPoderes(){
		int row = tableDtaModel.getTotalRows();
		
		listPoder = new ArrayList<Poderes>();
		Poderes poder;
		
		for(int i = 0; i < row; i++)
		{
			poder = (Poderes) tableDtaModel.getElementAt(i);
			listPoder.add(poder);
		}
	}
	
	private void infoPowerFire(Poderes poder)
	{

		colCartel.removeAll();

		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poder.getId()));
		poderBD = (Poderes) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if (poderBD.getId() == poder.getId())
		{
			name.setText("" + poderBD.getName());
			damage.setText("Daño: " + poderBD.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poderBD.getCooldown());
			psinergia.setText("Psinergia: " + poderBD.getPsinergia());
			if(poderBD.getTimeTraining() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poderBD.getTimeTraining() + " Horas");
			description.setText(poderBD.getDescripcion());
			
		}

	}
	
	private void infoPowerIce(Poderes poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poder.getId()));
		poderBD = (Poderes) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if (poderBD.getId() == poder.getId())
		{
			name.setText("" + poderBD.getName());
			damage.setText("Daño: " + poderBD.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poderBD.getCooldown());
			psinergia.setText("Psinergia: " + poderBD.getPsinergia());
			if(poderBD.getTimeTraining() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poderBD.getTimeTraining() + " Horas");
			description.setText(poderBD.getDescripcion());
			
		}
		
	}

	private void infoPowerEarth(Poderes poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poder.getId()));
		poderBD = (Poderes) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if (poderBD.getId() == poder.getId())
		{
			name.setText("" + poderBD.getName());
			damage.setText("Daño: " + poderBD.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poderBD.getCooldown());
			psinergia.setText("Psinergia: " + poderBD.getPsinergia());
			if(poderBD.getTimeTraining() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poderBD.getTimeTraining() + " Horas");
			description.setText(poderBD.getDescripcion());
			
		}
		
	}
	
	private void infoPowerCombo(Poderes poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Poderes.class).add(Restrictions.eq("id", poder.getId()));
		poderBD = (Poderes) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if (poderBD.getId() == poder.getId())
		{
			name.setText("" + poderBD.getName());
			damage.setText("Daño: " + poderBD.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poderBD.getCooldown());
			psinergia.setText("Psinergia: " + poderBD.getPsinergia());
			if(poderBD.getTimeTraining() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poderBD.getTimeTraining() + " Horas");
			description.setText(poderBD.getDescripcion());
			
		}
		
	}

}
