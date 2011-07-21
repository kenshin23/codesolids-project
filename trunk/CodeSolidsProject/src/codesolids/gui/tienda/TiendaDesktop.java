/*
 * Created on 25/05/2011
 */
package codesolids.gui.tienda;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Item;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
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
 * 
 */

@SuppressWarnings("serial")
public class TiendaDesktop extends ContentPane {
	
	private Usuario usuario;
	private Personaje personaje;
	private Item itemBD;
	private PersonajeItem personajeItem;
	
	private HtmlLayout htmlLayout;
	
	private TestTableModel tableDtaModel;
	private TestTableModel tableDtaModelPlayer;
	private TestTableModel tableDtaModelBuild;
	
	private ETable table;
	private ETable tablePlayer;
	private ETable tableBuild;
	
	private Label name;
	private Label index;
	private Label description;
	private Label oro;
	
	WindowPane window;

	private List<Item> listItem;
	private int indexTab = 0;
	
	Button btnBuild5 = new Button();
	Button btnBuild1 = new Button();
    Button btnBuild2 = new Button();
    Button btnBuild3 = new Button();
    Button btnBuild4 = new Button();

	
	private String[] imagePath = new String[]{
			"Images/Fondos/cartel.png","Images/Items/armor.png","Images/Items/sword.png","Images/Items/stone1.png",
			"Images/Items/potion1.png","Images/Items/stone2.png","Images/Items/armor2.png","Images/Items/potion2.png",
			"Images/Items/potion1.png","Images/Items/stone3.png","Images/Items/sword2.png","Images/Items/bomb.png",
			"Images/Items/bomb2.png","Images/Items/bomb3.png","Images/Items/potion2.png","Images/Items/potion1.png"};
	
	public TiendaDesktop() {
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
//		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
//		htmlLayout.setBackgroundImage(ir);
		ResourceImageReference w = new ResourceImageReference("Images/Items/pergamino.png");
		
		ImageReference image = w;
	
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
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    grid.add(tableNavigation);
	    
	    loadBD();
	    
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
	    
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
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModelPlayer);
	    grid.add(tableNavigation);
	    
	    loadItemPlayer();
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
		
	    Column col = new Column();
	    col.add(initFoot());
	    
	    hld = new HtmlLayoutData("foot");
	    col.setLayoutData(hld);
		htmlLayout.add(col);
		
		return htmlLayout;
	}
	
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
	    
	    if(tipo == 1)
	    	tableColumn.setDataCellRenderer(initNestedCellRenderer());
	    else if(tipo == 2)
	    	tableColumn.setDataCellRenderer(initNestedCellRendererSell());
//	    else if(tipo == 3)
//	    	tableColumn.setDataCellRenderer(initNestedCellRendererBuild());
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
	          final Button ret = new Button("Comprar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
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
	          
	          Button ret = new Button("Ver");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
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
	          
	          final Button ret = new Button("Vender");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
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
	        					  if(list.get(i).getItemRef().getId() == item.getId()){
	        						  session.delete(list.get(i));
	        						  i = list.size();
	        					  }
	        					  i++;
	        				  }

	        				  personaje.setGold(personaje.getGold() + item.getPrice()/2);
	        				  oro.setText(""+personaje.getGold());
	      					  
        					  session.getTransaction().commit();
        					  session.close();
	        				  
	        				  item.setUso(true);	        				  
//	        				  ret.setEnabled(false);
	        				  tableDtaModelPlayer.del(row);
	        				
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
	
	private void btnVerClicked(int row, int t) 
	{
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		Item item = new Item();
		if (t ==1 )
			item = (Item) tableDtaModel.getElementAt(row);
		else if (t ==2 )
			item = (Item) tableDtaModelPlayer.getElementAt(row);
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("id", item.getId()));
		itemBD = (Item) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if (itemBD.getId() == item.getId())
		{
			name.setText("" + itemBD.getName());
			index.setText("Indice: "+ itemBD.getIndex());
			description.setText(itemBD.getDescripcion());
			
		}

	}
	
	private Column initFoot() {		
		
		Panel panel = new Panel();
		ImageReferenceCache irc = ImageReferenceCache.getInstance();
	    ImageReference ir = irc.getImageReference(imagePath[0]);

//		ImageReference imgR = new ResourceImageReference("Images/Fondos/cartel.png");
		FillImage imgF = new FillImage(ir);
		
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
		Column col = new Column();		
		name = new Label();
		col.add(name);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		index = new Label();
		row.add(index);
		col.add(row);

		description = new Label();
		col.add(description);
		col.setInsets(new Insets(40));

		return col;
	}
	
	private Row initTopRow() {
		Row row = new Row();
	    row.setCellSpacing(new Extent(150));
		
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));
	    
	    Button btnAll = new Button("Todo");
	    btnAll.setStyle(Styles1.DEFAULT_STYLE);
	    btnAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnAllClicked();
            }
        });
	    rowBtn.add(btnAll);
	    
	    Button btnArmor = new Button("Armaduras");
	    btnArmor.setStyle(Styles1.DEFAULT_STYLE);
	    btnArmor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnArmorClicked();
            }
        });
	    rowBtn.add(btnArmor);
	    
	    Button btnSword = new Button("Espadas");
	    btnSword.setStyle(Styles1.DEFAULT_STYLE);
	    btnSword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnSwordClicked();
            }
        });
        rowBtn.add(btnSword);

	    Button btnPotion = new Button("Pociones");
	    btnPotion.setStyle(Styles1.DEFAULT_STYLE);
	    btnPotion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnPotionClicked();
            }
        });
        rowBtn.add(btnPotion);
        
	    Button btnStone = new Button("Piedras Magicas");
	    btnStone.setStyle(Styles1.DEFAULT_STYLE);
	    btnStone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              btnStoneClicked();
            }
        });
        rowBtn.add(btnStone);
        
        Button btnBomb = new Button("Bombas Magicas");
        btnBomb.setStyle(Styles1.DEFAULT_STYLE);
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
        
        ImageReference imgR = new ResourceImageReference("Images/Util/sacomoneda.png");
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
        
	    Button btnExit = new Button("Tienda");
	    btnExit.setStyle(Styles1.DEFAULT_STYLE);
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
	    row.setCellSpacing(new Extent(150));
		
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));
        
        Panel panel = new Panel();
        panel.setWidth(new Extent(150));
        
        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));
        
        ImageReference imgR = new ResourceImageReference("Images/Util/sacomoneda.png");
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

	    Button btnExit = new Button("Tienda");
	    btnExit.setStyle(Styles1.DEFAULT_STYLE);
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
		
		name.setText("");
		index.setText("");
		description.setText("");

		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();		
	}

	private void btnArmorClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("tipo", "Armadura")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();		
	}
	
	private void btnSwordClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("tipo", "Espada")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();
	}
	
	private void btnPotionClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).add(Restrictions.eq("tipo", "Pocion")).addOrder(Order.asc("id"));
		
		for (Object obj : criteria.list()) {
			tableDtaModel.add(obj);
		}
	    session.getTransaction().commit();
	    session.close();
	}
	
	private void btnStoneClicked()
	{
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
	
	private void btnBombClicked()
	{
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
		btnComprar.setText("Comprar");
		btnComprar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnComprar.setStyle(Styles1.DEFAULT_STYLE);
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
		btnVender.setText("Vender");
		btnVender.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnVender.setStyle(Styles1.DEFAULT_STYLE);
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
		
		Button returnButton = new Button();
		returnButton.setText("Salir");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setToolTipText("Regresar al Mapa Principal");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
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
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> list = session.createCriteria(Item.class).addOrder(Order.asc("id")).list();

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

						
			tableDtaModel.add(item);

		}			
		session.getTransaction().commit();
		session.close();		
	}
	
	private void loadItemPlayer()
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		List<PersonajeItem> list = session.createCriteria(PersonajeItem.class).addOrder(Order.asc("id")).list();


		for(PersonajeItem obj : list)
		{
			Item item = new Item();
			if(obj.getPersonajeRef().getId() == personaje.getId()){
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

		}			
		session.getTransaction().commit();
		session.close();		
	}
}
