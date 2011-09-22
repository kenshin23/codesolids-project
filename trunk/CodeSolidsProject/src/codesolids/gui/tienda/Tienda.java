/*
 * Created on 25/05/2011
 */
package codesolids.gui.tienda;

import java.util.ArrayList;
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
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Item;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.Receta;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.TestTableModel;

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
 * @author Fernando Osuna
 * @Colaborador Eduardo Granados (refineria)
 */

@SuppressWarnings("serial")
public class Tienda extends ContentPane {
	
	private Usuario usuario;
	private Personaje personaje;
	private Item itemBD;
	private Receta recBD;
	private PersonajeItem personajeItem;
	
	private HtmlLayout htmlLayout;
	
	private TestTableModel tableDtaModel;
	private TestTableModel tableDtaModelPlayer;
	private TestTableModel tableDtaModelBuild;
	
	private int cantR = 0;		//cantidad Rojas del pj
	private int cantB = 0;		//cantidad Blancas del pj
	private int cantN = 0;		//cantidad Negras del pj
	
	private ETable table;
	private ETable tablePlayer;
	private ETable tableBuild;
	
	private Label name;
	private Label index;
	private Label description;
	private Label oro;
	private Label imgIt;
	
	WindowPane window;

	private List<Item> listItem;
	
	Button btnBuild5 = new Button();
	Button btnBuild1 = new Button();
    Button btnBuild2 = new Button();
    Button btnBuild3 = new Button();
    Button btnBuild4 = new Button();
	
	public Tienda() {
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		usuario = app.getUsuario();
		personaje = app.getPersonaje();
		initGUI();	
	}

	private void initGUI() {
		add(initMapa());		
	}
	
	private Component initMapa()
	{		
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		

		ImageReference image = ImageReferenceCache.getInstance().getImageReference("Images/Items/pergamino.png");
	
		HtmlLayoutData hld;

		hld = new HtmlLayoutData("head");
		Row row = new Row();
		
		menu(hld, row);		
		htmlLayout.add(row);
		
		hld = new HtmlLayoutData("central");
		
		Panel panel = new Panel();
		Column col2 = new Column();
		row = new Row();
		col2.setCellSpacing(new Extent(5));
		description = new Label("Bienvenido a la Tienda del comerciante Merlot");
		col2.add(description);
		description = new Label("Aqui encontrarás todos los objetos mágicos, armaduras,");
		col2.add(description);
		description = new Label("espadas, pociones que aumentarán tu vida y tu energía,");
		col2.add(description);
		description = new Label("bombas  para  dañar a tus  enemigos y un taller  para ");
		col2.add(description);
		description = new Label("refinar tus objetos.");
		col2.add(description);
		description = new Label("Merlot es un viejo ambicioso que vende su  mercancia");
		col2.add(description);
		description = new Label("a un alto precio, pero si te compra algo lo hace por");
		col2.add(description);
		description = new Label("la mitad de su precio original, asi que piensalo muy");
		col2.add(description);
		description = new Label("bien antes de comprar o vender un objeto");
		col2.add(description);
		col2.setInsets(new Insets(50));
		row.add(col2);
		
		FillImage imagep = new FillImage(image);
		
		panel.add(row);
		panel.setLayoutData(hld);
		panel.setBackgroundImage(imagep);
		panel.setHeight(new Extent(380));
		panel.setWidth(new Extent(460));
		panel.setBorder(new Border(new Extent(10, Extent.PX), new Color(0xd4630c), Border.STYLE_NONE));
		htmlLayout.add(panel);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		session.getTransaction().commit();
		session.close();
		
		return htmlLayout;
	}

	private Component initComprar()
	{		
		try{
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("head");
		Row row = new Row();
		
		menu(hld, row);		
		htmlLayout.add(row);
		
		hld = new HtmlLayoutData("central");
		
		Grid grid = new Grid(1);
		
		grid.add(initTopRow());		
		
		TableColModel tableColModel = initTableColModel(1);
	    TableSelModel tableSelModel = new TableSelModel();

	    tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(6);
	    
	    table = new ETable();
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);

	    
	    table.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    table.setInsets(new Insets(5, 2, 5, 2));
	    
	    grid.add(table);
	    
	    loadBD();
	    
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
		
		hld = new HtmlLayoutData("navigation");
		
	    Column colNav = new Column();
	    colNav.setInsets(new Insets(500, 0, 0, 0));
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    
	    colNav.add(tableNavigation);	    
	    colNav.setLayoutData(hld);
	    htmlLayout.add(colNav);
	    
	    Column col = new Column();
	    col.add(initFoot());
	    
	    hld = new HtmlLayoutData("foot");
	    col.setLayoutData(hld);
		htmlLayout.add(col);
		
		return htmlLayout;
	}
	
	private Component initVender()
	{		
		try{
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("head");
		Row row = new Row();
		
		menu(hld, row);		
		htmlLayout.add(row);
		
		hld = new HtmlLayoutData("central");
		
		Grid grid = new Grid(1);
		
		grid.add(initTopRowVender());
		
		TableColModel tableColModel = initTableColModel(2);
	    TableSelModel tableSelModel = new TableSelModel();

	    tableDtaModelPlayer = new TestTableModel();
	    tableDtaModelPlayer.setEditable(true);
	    tableDtaModelPlayer.setPageSize(6);
	    
	    tablePlayer = new ETable();
	    tablePlayer.setTableDtaModel(tableDtaModelPlayer);
	    tablePlayer.setTableColModel(tableColModel);
	    tablePlayer.setTableSelModel(tableSelModel);
	    
	    tablePlayer.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    tablePlayer.setInsets(new Insets(5, 2, 5, 2));
	    
	    grid.add(tablePlayer);
	    
	    loadItemPlayer();
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
		
		hld = new HtmlLayoutData("navigation");
		
	    Column colNav = new Column();
	    colNav.setInsets(new Insets(500, 0, 0, 0));
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModelPlayer);
	    
	    colNav.add(tableNavigation);	    
	    colNav.setLayoutData(hld);
	    htmlLayout.add(colNav);
		
	    Column col = new Column();
	    col.add(initFoot());
	    
	    hld = new HtmlLayoutData("foot");
	    col.setLayoutData(hld);
		htmlLayout.add(col);
		
		return htmlLayout;
	}
	
//**********************************************************************************************************************************
	
	private Component initRefineria()
	{		
		try{
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("head");
		Row row = new Row();
		
		menu(hld, row);		
		htmlLayout.add(row);
		
		hld = new HtmlLayoutData("central");
		
		Grid grid = new Grid(1);
		
		grid.add(initTopRowVender());
		
		TableColModel tableColModel = initTableColModelBuild();
	    TableSelModel tableSelModel = new TableSelModel();

	    tableDtaModelBuild = new TestTableModel();
	    tableDtaModelBuild.setEditable(true);
	    tableDtaModelBuild.setPageSize(6);
	    
	    tableBuild = new ETable();
	    tableBuild.setTableDtaModel(tableDtaModelBuild);
	    tableBuild.setTableColModel(tableColModel);
	    tableBuild.setTableSelModel(tableSelModel);
	    
	    tableBuild.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    tableBuild.setInsets(new Insets(5, 2, 5, 2));
	    
	    grid.add(tableBuild);
	    
	    loadRecipes();
	    
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
		
		hld = new HtmlLayoutData("navigation");
		
	    Column colNav = new Column();
	    colNav.setInsets(new Insets(500, 0, 0, 0));
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModelBuild);
	    
	    colNav.add(tableNavigation);	    
	    colNav.setLayoutData(hld);
	    htmlLayout.add(colNav);
		
	    Column col = new Column();
	    col.add(initFoot());
	    
	    hld = new HtmlLayoutData("foot");
	    col.setLayoutData(hld);
		htmlLayout.add(col);
		cantPiedras();
		
		tableDtaModelBuild.currPageChanged();
		
		return htmlLayout;
	}

	
//**********************************************************************************************************************************	
	
	private TableColModel initTableColModelBuild() {		
		
		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Receta rec = (Receta) element;
	    		return rec.getItemCrear().getName();
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
	    tableColumn.setHeadValue("Item Crear");
	    
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Receta rec = (Receta) element;
	    		return rec.getCantBlancas();
	    		//return cantB;
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
	    tableColumn.setHeadValue("Cantidad Blancas");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Receta rec = (Receta) element;
	    		return rec.getCantNegras();
	    		
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
	    tableColumn.setHeadValue("Cantidad Negras");
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Receta rec = (Receta) element;
	    		return rec.getCantRojas();
	    		

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
	    tableColumn.setHeadValue("Cantidad Rojas");
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Receta rec = (Receta) element;
	    		
	    		return rec.getReagent().getName();
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
	    tableColumn.setHeadValue("Reactivo");
	    
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    
	   	tableColumn.setDataCellRenderer(initNestedCellRendererBuild());
	    
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    return tableColModel;
	}
	
	
	//**************************************************************************************************

	private TableColModel initTableColModel(int tipo) {		
		
		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Item item = (Item) element;
	    		return item.getLevel();
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
	    tableColumn.setHeadValue("Nivel");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Item item = (Item) element;
	    		return item.getDirImage();
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
	    		Item item = (Item) element;
	    		return item.getName();
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
	    		Item item = (Item) element;
	    		return item.getPrice();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Oro");
	    
	    if(tipo == 2){
		    tableColumn = new TableColumn(){      
		    	@Override
		    	public Object getValue(ETable table, Object element) {
		    		Item item = (Item) element;
		    		Session session = SessionHibernate.getInstance().getSession();
		    		session.beginTransaction();
		    		String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt";
		    		Query query  = session.createQuery(queryStr);
		    		query.setInteger("idPlayer", personaje.getId());
		    		query.setString("nameIt", item.getName());
		    		List<PersonajeItem> list = query.list();
		    		session.getTransaction().commit();			  	        
		    		session.close();
		    		return list.size();
		    	}
		    };
		    
		    lcr = new LabelCellRenderer();
		    lcr.setBackground(new Color(87, 205, 211));
		    lcr.setForeground(Color.WHITE);
		    tableColumn.setHeadCellRenderer(lcr);

		    lcr = new LabelCellRenderer();
		    lcr.setBackground(new Color(226,211,161));
		    lcr.setForeground(Color.BLACK);
		    
		    tableColumn.setDataCellRenderer(lcr);
		    tableColModel.getTableColumnList().add(tableColumn);
		    
		    tableColumn.setWidth(new Extent(100));
		    tableColumn.setHeadValue("Cantidad");
	    }
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(100));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    if(tipo == 1)
	    	tableColumn.setDataCellRenderer(initNestedCellRenderer());
	    else if(tipo == 2)
	    	tableColumn.setDataCellRenderer(initNestedCellRendererSell());

	    
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
	          
	          Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/ver2.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/ver2MouseOver.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(23));
	          ret.setWidth(new Extent(35));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Ver");

	          ret.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	              btnVerClicked(row, 1);
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

	          Item item = (Item) tableDtaModel.getElementAt(row);
	          final Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/comprar.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/comprarMouseOver.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(30));
	          ret.setWidth(new Extent(100));
	          ret.setDisabledBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/comprarDes.png"))));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Comprar");

	          if ( consultBD(item) )
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBuyClicked(row);
	        			  }	        		  
	        			  private void btnBuyClicked(int row) {
        					  Session session = SessionHibernate.getInstance().getSession();
        					  session.beginTransaction();
	        				  Item item = (Item) tableDtaModel.getElementAt(row);
	        				  
	        				  
	        				  personaje = (Personaje) session.load(Personaje.class, personaje.getId());
	    
	        				  personaje.setGold(personaje.getGold() - item.getPrice());
	        				  oro.setText(""+personaje.getGold());
	        				  
	        				  personajeItem = new PersonajeItem();
	        				  personajeItem.setPersonajeRef(personaje);
	        				  personajeItem.setItemRef(item);
	        				  personajeItem.setEquipado(false);
	        				  
	        				  itemBD = new Item();
	        				  
	        				  personaje.getPersonajeItemList().add(personajeItem);
	        				  itemBD.getPersonajeItemList().add(personajeItem);	        				  
	        				  
        					  session.getTransaction().commit();
        					  session.close();
	        				  
	        				  ret.setEnabled(consultBD(item));
	        				  tableDtaModel.currPageChanged();
	        				  
	        				  description.setText("Compro "+item.getName()+"!");
	        				  description.setForeground(Color.BLUE);
	        			  }
	        		  });	        	  
	          }
	          else{
	        	  ret.setEnabled(false);	        	  
	          }
	          return ret;
	        }
	    });
	
	    return nestedCellRenderer;
	}
	
	private boolean consultBD(Item item){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		session.getTransaction().commit();
		session.close();
		
		if(item.getPrice() <= personaje.getGold() && item.getLevel() <= personaje.getLevel()){
			return true;
		}
		else return false;
	}
	
	private CellRenderer initNestedCellRendererSell() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
		nestedCellRenderer.setBackground(new Color(226,211,161));
	    
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();
	          
	          Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/ver2.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/ver2MouseOver.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(23));
	          ret.setWidth(new Extent(35));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Ver");

	          ret.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	              btnVerClicked(row, 2);
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

	          Item item = (Item) tableDtaModelPlayer.getElementAt(row);
	          
	          final Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/vender.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/venderMouseOver.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(30));
	          ret.setWidth(new Extent(100));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Vender");

	          if (item.isUso() == false)
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnSellClicked(row);
	        			  }
	        		  
	        			  private void btnSellClicked(int row) {
	        				  int i = 0;
        					  Session session = SessionHibernate.getInstance().getSession();
        					  session.beginTransaction();
        					  
        					  personaje = (Personaje) session.load(Personaje.class, personaje.getId());

	        				  Item item = (Item) tableDtaModelPlayer.getElementAt(row);
	        				  
	        				  List<PersonajeItem> list = session.createCriteria(PersonajeItem.class).addOrder(Order.asc("id")).list();
	        				  while(i<list.size()){
	        					  if(list.get(i).getItemRef().getId() == item.getId() && list.get(i).getPersonajeRef().getId()== personaje.getId()){
	        						  session.delete(list.get(i));
	        						  break;
	        					  }
	        					  i++;
	        				  }

	        				  personaje.setGold(personaje.getGold() + item.getPrice()/2);
	        				  oro.setText(""+personaje.getGold());
	      					  
        					  session.getTransaction().commit();
        					  session.close();
	        				  
	        				 // item.setUso(true);	        				  
//	        				  ret.setEnabled(false);
        					  session = SessionHibernate.getInstance().getSession();
        					  session.beginTransaction();
        					  
        					  String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt";
        					  Query query  = session.createQuery(queryStr);
        					  query.setInteger("idPlayer", personaje.getId());
        					  query.setString("nameIt", item.getName());
        					  List<Object> resultQuery = query.list();
        					  
        					  if(!(resultQuery.size()>0)){
    	        				  tableDtaModelPlayer.del(row);        						  
        					  }
	        				  tableDtaModelPlayer.currPageChanged();
	        				  
	        				  description.setText("Vendio "+item.getName()+"!");
	        				  description.setForeground(Color.BLUE);
	        				  
	        				  session.getTransaction().commit();
        					  session.close();
	        				
	        			  }
	        		  });	        	  
	          }
	          else{
	        	  ret.setEnabled(false);
	          }
	          return ret;
	        }
	    });
	
	    return nestedCellRenderer;
	}

	
	//**********************************************************************************************************************************

	private CellRenderer initNestedCellRendererBuild() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
		nestedCellRenderer.setBackground(new Color(226,211,161));
	    
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();
	          
	          Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/ver2.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/ver2MouseOver.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(23));
	          ret.setWidth(new Extent(35));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Ver");

	          ret.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	              btnVerClicked(row, 3);
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

	          Receta rec = (Receta) tableDtaModelBuild.getElementAt(row);
	          Item item=rec.getReagent();										
	          final Button ret = new Button();
	          ret.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/crear2.png")));
	          ret.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/crear2MouseOver.png"))));
	          ret.setDisabledBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/crearDes.png"))));
	          ret.setRolloverEnabled(true);
	          ret.setHeight(new Extent(23));
	          ret.setWidth(new Extent(40));
	          ret.setEnabled(editable);
	          ret.setToolTipText("Crear Item");

	          if ( consultBDBuild(item,rec.getCantRojas(),rec.getCantBlancas(),rec.getCantNegras()) )
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBuyClicked(row);
	        			  }	        		  
	        			  private void btnBuyClicked(int row) {
        					  Session session = SessionHibernate.getInstance().getSession();
        					  session.beginTransaction();
        					  
	        				  Receta rec = (Receta) tableDtaModelBuild.getElementAt(row);
	        				  Item itemCrear=rec.getItemCrear();
	        				  
	        				  personaje = (Personaje) session.load(Personaje.class, personaje.getId());	      				  
	        				  
	        				  personajeItem = new PersonajeItem();
	        				  personajeItem.setPersonajeRef(personaje);
	        				  personajeItem.setItemRef(itemCrear);
	        				  personajeItem.setEquipado(false);
	        				  
	        				  itemBD = new Item();
	        				  
	        				  personaje.getPersonajeItemList().add(personajeItem);
	        				  itemBD.getPersonajeItemList().add(personajeItem);	        				  
	        				  
        				
	        				  Item itemElim = rec.getReagent();
	        				  
	        				  List<PersonajeItem> list = session.createCriteria(PersonajeItem.class).addOrder(Order.asc("id")).list();
	        				  int i=0;
	        				  while(i<list.size()){
	        					  if(list.get(i).getItemRef().getId() == itemElim.getId() && list.get(i).getPersonajeRef().getId()== personaje.getId()){
	        						  session.delete(list.get(i));
	        						  break;
	        					  }
	        					  i++;
	        				  }

	        				  eliminarPiedra(rec.getCantRojas(),"Piedra Roja");
	        				  eliminarPiedra(rec.getCantBlancas(),"Piedra Blanca");
	        				  eliminarPiedra(rec.getCantNegras(),"Piedra Negra");
	        				  
        					  session.getTransaction().commit();
        					  session.close();
        					  
        					  cantPiedras();
	        				  
        					  if ( consultBDBuild(itemElim,rec.getCantRojas(),rec.getCantBlancas(),rec.getCantNegras()) ){
        						  ret.setEnabled(true);
        						 }
        					  else
        						  ret.setEnabled(false);
        					  tableDtaModelBuild.currPageChanged();
	        			  }
	        		  });	        	  
	          }
	          else{
	        	  ret.setEnabled(false);	        	  
	          }
	          return ret;
	        }
	    });
	
	    return nestedCellRenderer;
	}
	
	private void eliminarPiedra(int cantidad, String nombre){
		

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<PersonajeItem> list = session.createCriteria(PersonajeItem.class).addOrder(Order.asc("id")).list();
		  int i=0;
		  int count=0;
		  while(i<list.size()){
			  
			  if (count ==cantidad){
				  break;
			  }
			  if(list.get(i).getItemRef().getName().equals(nombre) && list.get(i).getPersonajeRef().getId()== personaje.getId()){
				  session.delete(list.get(i));
				  count++;
			  }
			  i++;
		  }
		  session.getTransaction().commit();
		  session.close();
	}
	
	
	private boolean consultBDBuild(Item item,int r,int b, int n){
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		
	
		
		for (PersonajeItem iterator : personaje.getPersonajeItemList()) {
			
				
				if(iterator.getItemRef().getId()==item.getId()){
					
					if(cantR >= r && cantB>=b && cantN >= n){
							session.close();
							return true;
					}
				}
				
		    }
		
		session.close();
		 return false;
	}

	private void cantPiedras(){
		
		cantR=0;
		cantB=0;
		cantN=0;
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		
		
		
		for (PersonajeItem iterator : personaje.getPersonajeItemList()) {
			
				
				if(iterator.getItemRef().getName().equals("Piedra Roja")){
					cantR=cantR+1;
				}
				if(iterator.getItemRef().getName().equals("Piedra Negra")){
					cantN=cantN+1;
				}
				if(iterator.getItemRef().getName().equals("Piedra Blanca")){
					cantB=cantB+1;
				}
				
				
		    }
		
		session.close();
	}
	
	//**********************************************************************************************************************************
	

	private void btnVerClicked(int row, int t) 
	{
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		Item item = new Item();
		Receta rec = new Receta();
		if (t ==1 )
			item = (Item) tableDtaModel.getElementAt(row);
		else if (t ==2 )
			item = (Item) tableDtaModelPlayer.getElementAt(row);
		else if (t==3 )
			 rec= (Receta) tableDtaModelBuild.getElementAt(row);
		
		if(t==3){
			
			Criteria criteria = session.createCriteria(Receta.class).add(Restrictions.eq("id", rec.getId()));
			recBD = (Receta) criteria.uniqueResult();
			
			Criteria criteriaI = session.createCriteria(Item.class).add(Restrictions.eq("id", rec.getItemCrear().getId()));
			itemBD = (Item) criteriaI.uniqueResult();
			
			session.getTransaction().commit();
			session.close();
			
			if (recBD.getId() == rec.getId())
			{	
				imgIt.setVisible(true);
				imgIt.setIcon(ImageReferenceCache.getInstance().getImageReference(itemBD.getDirImage()));
				name.setText("" + itemBD.getName());
				index.setText("Indice: "+ itemBD.getIndex());
				description.setText(recBD.getDescripcion());			
			}
			
		}
		else
		{
			Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("id", item.getId()));
			itemBD = (Item) criteria.uniqueResult();
		
			session.getTransaction().commit();
			session.close();
		
			if (itemBD.getId() == item.getId())
			{
				
				imgIt.setVisible(true);
				imgIt.setIcon(ImageReferenceCache.getInstance().getImageReference(itemBD.getDirImage()));
				name.setText("" + itemBD.getName());
				index.setText("Indice: "+ itemBD.getIndex());
				description.setText(itemBD.getDescripcion());
				description.setForeground(Color.BLACK);
			
			}
		}
	}
	
	private Column initFoot() {		
		
		Panel panel = new Panel();

		FillImage imgF = new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Fondos/cartel.png"));
		
		panel.setWidth(new Extent(505));
		panel.setHeight(new Extent(152));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		
		colCartel.add(DscripRow());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.setInsets(new Insets(0,0,0,40));
		colCartel.add(panel);
		
		return colCartel;
	}
	
	private Column DscripRow()
	{
		Row rowInfo = new Row();
		rowInfo.setCellSpacing(new Extent(15));
		Column colInfo = new Column();
		
		imgIt = new Label();
		
		Column col = new Column();		
		name = new Label();
		colInfo.add(name);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		index = new Label();
		row.add(index);
		colInfo.add(row);
		
		rowInfo.add(imgIt);
		rowInfo.add(colInfo);
		
		col.add(rowInfo);

		description = new Label();
		col.add(description);
		col.setInsets(new Insets(40));
		description.setForeground(Color.BLACK);

		return col;
	}
	
	private Row initTopRow() {
		Row row = new Row();
	    row.setCellSpacing(new Extent(150));
		
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));
	    
	    Button btnAll = new Button();
        btnAll.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/todo.png")));
        btnAll.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/todoMouseOver.png"))));
        btnAll.setRolloverEnabled(true);
        btnAll.setHeight(new Extent(27));
        btnAll.setWidth(new Extent(103));
	    btnAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnAllClicked();
            }
        });
	    rowBtn.add(btnAll);
	    
	    Button btnArmor = new Button();
        btnArmor.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/armaduras.png")));
        btnArmor.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/armadurasMouseOver.png"))));
        btnArmor.setRolloverEnabled(true);
        btnArmor.setHeight(new Extent(27));
        btnArmor.setWidth(new Extent(103));
	    btnArmor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnArmorClicked();
            }
        });
	    rowBtn.add(btnArmor);
	    
	    Button btnSword = new Button();
        btnSword.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/espadas.png")));
        btnSword.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/espadasMouseOver.png"))));
        btnSword.setRolloverEnabled(true);
        btnSword.setHeight(new Extent(27));
        btnSword.setWidth(new Extent(103));
	    btnSword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnSwordClicked();
            }
        });
        rowBtn.add(btnSword);

	    Button btnPotion = new Button();
        btnPotion.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/pociones.png")));
        btnPotion.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/pocionesMouseOver.png"))));
        btnPotion.setRolloverEnabled(true);
        btnPotion.setHeight(new Extent(27));
        btnPotion.setWidth(new Extent(103));
	    btnPotion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnPotionClicked();
            }
        });
        rowBtn.add(btnPotion);
        
	    Button btnStone = new Button();
        btnStone.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/piedras.png")));
        btnStone.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/piedrasMouseOver.png"))));
        btnStone.setRolloverEnabled(true);
        btnStone.setHeight(new Extent(27));
        btnStone.setWidth(new Extent(112));

	    btnStone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnStoneClicked();
            }
        });
        rowBtn.add(btnStone);
        
        Button btnBomb = new Button();
        btnBomb.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/bombas.png")));
        btnBomb.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/bombasMouseOver.png"))));
        btnBomb.setRolloverEnabled(true);
        btnBomb.setHeight(new Extent(27));
        btnBomb.setWidth(new Extent(112));

        btnBomb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnBombClicked();
            }
        });
        rowBtn.add(btnBomb);
        
        Panel panel = new Panel();
        panel.setWidth(new Extent(150));
        
        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));
        
        ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        rowPanel.add(imgI);
        
        oro = new Label(" "  + personaje.getGold());
        oro.setForeground(Color.YELLOW);
        rowPanel.add(oro);
        
        panel.add(rowPanel);
        
        rowBtn.add(panel);
                
        row.add(rowBtn);
        
        rowBtn = new Row();
        
	    Button btnExit = new Button();
        btnExit.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/tienda.png")));
        btnExit.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/tiendaMouseOver.png"))));
        btnExit.setRolloverEnabled(true);
        btnExit.setHeight(new Extent(27));
        btnExit.setWidth(new Extent(94));
	    btnExit.setToolTipText("Regresar a Principal Tienda");
	    btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              removeAll();
              add(initMapa());
            }
        });
        rowBtn.add(btnExit);
        row.add(rowBtn);
        
	    return row;
	}
	
	private Row initTopRowVender() {
		
		Row row = new Row();
	    row.setCellSpacing(new Extent(200));
		
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));
        
        Panel panel = new Panel();
        panel.setWidth(new Extent(150));
        
        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));
        
        ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        rowPanel.add(imgI);
        
        oro = new Label(" "  + personaje.getGold());
        oro.setForeground(Color.YELLOW);
        rowPanel.add(oro);
        
        panel.add(rowPanel);
        
        rowBtn.add(panel);
                
        row.add(rowBtn);
        
        rowBtn = new Row();

	    Button btnExit = new Button();
        btnExit.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/tienda.png")));
        btnExit.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/tiendaMouseOver.png"))));
        btnExit.setRolloverEnabled(true);
        btnExit.setHeight(new Extent(27));
        btnExit.setWidth(new Extent(94));
	    btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              removeAll();
              add(initMapa());
            }
        });
        rowBtn.add(btnExit);
        row.add(rowBtn);
        
	    return row;
	}
	
	private void btnAllClicked() {
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");

		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> list = session.createCriteria(Item.class).add(Restrictions.eq("inshop", true)).addOrder(Order.asc("id")).list();

		for(Item obj : list)
		{			
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();		
	}

	private void btnArmorClicked() {
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> list = session.createCriteria(Item.class).add(Restrictions.eq("inshop", true)).add(Restrictions.eq("tipo", "Armadura")).addOrder(Order.asc("id")).list();

		for(Item obj : list)
		{			
			if(obj.isInshop() == true){
				tableDtaModel.add(obj);
			}			

		}
	    session.getTransaction().commit();
	    session.close();		
	}
	
	private void btnSwordClicked() {		
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> list = session.createCriteria(Item.class).add(Restrictions.eq("inshop", true)).add(Restrictions.eq("tipo", "Espada")).addOrder(Order.asc("id")).list();

		for(Item obj : list)
		{			
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();
	}
	
	private void btnPotionClicked() {
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.or(Restrictions.eq("tipo", "Pocion"), Restrictions.eq("tipo", "Medicina"))).addOrder(Order.asc("level"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}

	    session.getTransaction().commit();
	    session.close();
	}
	
	private void btnStoneClicked(){
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("tipo", "Piedra")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();		
	}
	
	private void btnBombClicked(){
		
		imgIt.setVisible(false);
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("tipo", "Bomba")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();	
	}
	
	private void menu(HtmlLayoutData hld , Row row){
		hld = new HtmlLayoutData("head");
		Row menu = new Row();
		Button btnComprar = new Button();
        btnComprar.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/comprar.png")));
        btnComprar.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/comprarMouseOver.png"))));
        btnComprar.setRolloverEnabled(true);
        btnComprar.setHeight(new Extent(30));
        btnComprar.setWidth(new Extent(100));
		btnComprar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnComprar.setToolTipText("Comprar Items");		
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initComprar());
			}
		});
		
		menu.add(btnComprar);
		menu.setCellSpacing(new Extent(15));
		row.add(menu);
		
		Button btnVender = new Button();

		btnVender.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
        btnVender.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/vender.png")));
        btnVender.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/venderMouseOver.png"))));
        btnVender.setRolloverEnabled(true);
        btnVender.setHeight(new Extent(30));
        btnVender.setWidth(new Extent(100));
		btnVender.setToolTipText("Vender Items");		
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initVender());
			}
		});		
		menu.add(btnVender);
		menu.setCellSpacing(new Extent(15));
		row.add(menu);
		
		menu.setCellSpacing(new Extent(15));
		row.add(menu);
		
		
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		Button btnRefin = new Button();

		btnRefin.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
        btnRefin.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/refineria.png")));
        btnRefin.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/refineriaMouseOver.png"))));
        btnRefin.setRolloverEnabled(true);
        btnRefin.setHeight(new Extent(30));
        btnRefin.setWidth(new Extent(100));
		btnRefin.setToolTipText("Crear Items");		
		btnRefin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initRefineria());
			}
		});		
		menu.add(btnRefin);
		menu.setCellSpacing(new Extent(15));
		row.add(menu);
		
		menu.setCellSpacing(new Extent(15));
		row.add(menu);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		

		Button returnButton = new Button();
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setToolTipText("Regresar al Mapa Principal");
        returnButton.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/salir.png")));
        returnButton.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/salirMouseOver.png"))));
        returnButton.setRolloverEnabled(true);
        returnButton.setHeight(new Extent(30));
        returnButton.setWidth(new Extent(100));
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button1Clicked(e);				
			}
		});
		
		menu.add(returnButton);
		row.add(menu);
		
		row.setLayoutData(hld);		
	}
	
	private void button1Clicked(ActionEvent e) {		
		removeAll();
		add(new MapaDesktop());
	}
	
	private void loadBD()
	{
		listItem = new ArrayList<Item>();
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> list = session.createCriteria(Item.class).add(Restrictions.eq("inshop", true)).addOrder(Order.asc("id")).list();

		for(Item obj : list)
		{
			Item item = new Item();
			
			item.setId(obj.getId());
			item.setLevel(obj.getLevel());
			item.setName(obj.getName());
			item.setPrice(obj.getPrice());
			item.setIndex(obj.getIndex());
			item.setTipo(obj.getTipo());
			item.setDescripcion(obj.getDescripcion());
			item.setUso(obj.isUso());
			item.setDirImage(obj.getDirImage());
			item.setInshop(obj.isInshop());
			
			tableDtaModel.add(obj);
			listItem.add(item);
		}			
		session.getTransaction().commit();
		session.close();		
	}
	
	private void loadItemPlayer()
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> listBD = session.createCriteria(Item.class).addOrder(Order.asc("id")).list();
		session.getTransaction().commit();
		session.close();
		
		session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		for(Item item: listBD){
			Item it = item;
			
			String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt";
			Query query  = session.createQuery(queryStr);
			query.setInteger("idPlayer", personaje.getId());
			query.setString("nameIt", it.getName());
			List<Object> list = query.list();
			
			addItemTable(list);
		}		
			
		session.getTransaction().commit();
		session.close();		
	}
	
	public void addItemTable(List<Object> resultQuery) {
		Iterator<Object> iter = resultQuery.iterator();
  	    if (!iter.hasNext()) {
  	    	return;
  	    }
  	    while (iter.hasNext()) {
			PersonajeItem it = (PersonajeItem) iter.next();
			cargar(it);
			return;
  	    }    		
	}
	
	private void cargar(PersonajeItem obj){
		Item item = new Item();
		item.setId(obj.getItemRef().getId());
		item.setLevel(obj.getItemRef().getLevel());
		item.setName(obj.getItemRef().getName());
		item.setPrice(obj.getItemRef().getPrice());
		item.setIndex(obj.getItemRef().getIndex());
		item.setTipo(obj.getItemRef().getTipo());
		item.setDescripcion(obj.getItemRef().getDescripcion());
		item.setUso(obj.getItemRef().isUso());
		item.setDirImage(obj.getItemRef().getDirImage());
		item.setInshop(obj.getItemRef().isInshop());

		tableDtaModelPlayer.add(item);
	}
	
	private void loadRecipes()
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Receta> list = session.createCriteria(Receta.class).addOrder(Order.asc("id")).list();

		for(Receta obj : list)
		{

			tableDtaModelBuild.add(obj);
			
		}
		session.getTransaction().commit();
		session.close();		
	}
	
}
