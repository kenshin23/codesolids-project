/*
 * Created on 25/05/2011
 */
package codesolids.gui.tienda;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Alignment;
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
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import javax.imageio.stream.FileCacheImageOutputStream;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.ButtonCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;
import com.minotauro.echo.table.renderer.ImageCellRenderer;
import com.sun.org.apache.bcel.internal.generic.NEW;

import codesolids.util.TestTableModel;
import codesolids.gui.tienda.Item;
import codesolids.gui.tienda.ImageReferenceCache;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.bd.clases.Usuario;
import codesolids.gui.style.Styles1;
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

	private List<Item> listItemtienda;
	private List<Item> listItemplayer;
	private List<Item> listItembuild;
	private Personaje player = new Personaje();
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
	
	public TiendaDesktop(Usuario usuario) {
		this.usuario = usuario;
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
	    
	    CreateList(1);
		rowsArrayItems();
	    
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
	    
	    CreateListItem(player.getItems());
		rowsArrayItemsPlayer();
		grid.setLayoutData(hld);
		htmlLayout.add(grid);
		
	    Column col = new Column();
	    col.add(initFoot());
	    
	    hld = new HtmlLayoutData("foot");
	    col.setLayoutData(hld);
		htmlLayout.add(col);
		
		return htmlLayout;
	}
	
	private Component initRefinar()
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
		
		TableColModel tableColModel = initTableColModel(3);
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
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModelBuild);
	    grid.add(tableNavigation);
	    
	    CreateList(3);
		rowsArrayItems2();
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
	    		return item.getImage();
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
	    else if(tipo == 3)
	    	tableColumn.setDataCellRenderer(initNestedCellRendererBuild());
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

	          Item item = (Item) tableDtaModel.getElementAt(row);
	          final Button ret = new Button("Comprar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Comprar");

	          if (item.getPrice() <= player.getGold() && item.getLevel() <= player.getLevel() )
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBuyClicked(row);
	        			  }	        		  
	        			  private void btnBuyClicked(int row) {	        				
	        				  Item item = (Item) tableDtaModel.getElementAt(row);
	        				  
	        				  player.setGold(player.getGold() - item.getPrice());
	        				  player.setItems(item);
	        				  oro = new Label(""+player.getGold());
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initComprar());
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
	              btnVerClicked2(row);
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
	          
	          final Button ret = new Button("Vender");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Vender");

	          if (item.getUse() == false)
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnSellClicked(row);
	        			  }
	        		  
	        			  private void btnSellClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModelPlayer.getElementAt(row);
	        				  tableDtaModelPlayer.del(tableDtaModelPlayer.getElementAt(row));
	        				  player.setGold(player.getGold() + item.getPrice()/2);
	        				  Item it = new Item();
	      					  it = player.searchItem(item.getName());
	      					  player.removeItem(it);
	        				  
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(false);
	        				  
	        				  removeAll();
	        				  add(initVender());
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
	
	private CellRenderer initNestedCellRendererBuild() {
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

	          Item item = (Item) tableDtaModelBuild.getElementAt(row);
	          final Button ret = new Button("Refinar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Refinar Este Item");

	          if (item.getLevel() <= player.getLevel() && item.getName() == "Armadura Negra" && //
	        		  player.contains("Armadura") && player.contains("Piedra Negra"))
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBClicked(row);
	        			  }
	        		  
	        			  private void btnBClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModelBuild.getElementAt(row);
	        				  player.setItems(item);
	        				  Item it;
	        				  it = player.searchItem("Piedra Negra");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Armadura");
	        				  player.removeItem(it);
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initRefinar());
	        			  }
	        		  });	        	  
	          }
	          else if (item.getLevel() <= player.getLevel() && item.getName() == "Espada Roja" && //
	        		  player.contains("Espada") && player.contains("Piedra Roja"))
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBClicked(row);
	        			  }
	        		  
	        			  private void btnBClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModelBuild.getElementAt(row);
	        				  player.setItems(item);
	        				  Item it;
	        				  it = player.searchItem("Piedra Roja");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Espada");
	        				  player.removeItem(it);
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initRefinar());
	        			  }
	        		  });	        	  
	          }
	          else if (item.getLevel() <= player.getLevel() && item.getName() == "Bomba Roja" && //
	        		  player.countItem("Piedra Roja") >= 3)
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBClicked(row);
	        			  }
	        		  
	        			  private void btnBClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModelBuild.getElementAt(row);
	        				  player.setItems(item);
	        				  Item it;
	        				  it = player.searchItem("Piedra Roja");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Roja");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Roja");
	        				  player.removeItem(it);
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initRefinar());
	        			  }
	        		  });	        	  
	          }
	          else if (item.getLevel() <= player.getLevel() && item.getName() == "Bomba Negra" && //
	        		  player.contains("Piedra Negra") && player.contains("Piedra Roja") && player.contains("Piedra Blanca"))
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBClicked(row);
	        			  }
	        		  
	        			  private void btnBClicked(int row) {	        				
	        				  Item item = (Item) tableDtaModelBuild.getElementAt(row);
	        				  player.setItems(item);
	        				  Item it;
	        				  it = player.searchItem("Piedra Negra");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Roja");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Blanca");
	        				  player.removeItem(it);
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initRefinar());
	        			  }
	        		  });	        	  
	          }
	          else if (item.getLevel() <= player.getLevel() && item.getName() == "Bomba Blanca" && //
	        		  player.countItem("Piedra Blanca") >= 3)
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBClicked(row);
	        			  }
	        		  
	        			  private void btnBClicked(int row) {	        				
	        				  Item item = (Item) tableDtaModelBuild.getElementAt(row);
	        				  player.setItems(item);
	        				  Item it;
	        				  it = player.searchItem("Piedra Blanca");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Blanca");
	        				  player.removeItem(it);
	        				  it = player.searchItem("Piedra Blanca");
	        				  player.removeItem(it);
	        				  item.setUse(true);	        				  
	        				  ret.setEnabled(true);
	        				  
	        				  removeAll();
	        				  add(initRefinar());
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
	
	private void btnVerClicked(int row) 
	{
		Item item = (Item) tableDtaModel.getElementAt(row);
		
		if (item.getType() == "Armadura")
			infoItemArmor(item);
		else if (item.getType() == "Espada")
			infoItemSword(item);
		else if (item.getType() == "Pocion")
			infoItemPotion(item);
		else if (item.getType() == "Piedra")
			infoItemStone(item);
		else
			infoItemBomb(item);
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
        
        oro = new Label(" "  + player.getGold());
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
        
        oro = new Label(" "  + player.getGold());
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
	
	public void testButton(Item it){
		if(player.contains("Armadura") && player.contains("Piedra Negra") && it.getLevel() <= player.getLevel()){
			if( it.getName() == "Armadura Negra")
				btnBuild1.setEnabled(true);						
		}else
			btnBuild1.setEnabled(false);
		if(player.contains("Espada") && player.contains("Piedra Roja") && it.getLevel() <= player.getLevel()){
			if(it.getName() == "Espada Roja")
				btnBuild2.setEnabled(true);						
		}else
			btnBuild2.setEnabled(false);
		if(player.countItem("Piedra Blanca") >= 3 && it.getLevel() <= player.getLevel()){
			if(it.getName() == "Bomba Blanca")
				btnBuild3.setEnabled(true);						
		}else
			btnBuild3.setEnabled(false);
		if(player.contains("Piedra Blanca") && player.contains("Piedra Roja") && player.contains("Piedra Negra")
				&& it.getLevel() <= player.getLevel()){
			if(it.getName() == "Bomba Negra")
				btnBuild4.setEnabled(true);						
		}else
			btnBuild4.setEnabled(false);
		if(player.countItem("Piedra Roja") >= 3 && it.getLevel() <= player.getLevel()){
			if(it.getName() == "Bomba Roja")
				btnBuild5.setEnabled(true);						
		}else
			btnBuild5.setEnabled(false);
	}

	private void rowsArrayItems(){
		int row = tableDtaModel.getTotalRows();
		
		listItemtienda = new ArrayList<Item>();
		Item item;
		
		for(int i = 0; i < row; i++)
		{
			item = (Item) tableDtaModel.getElementAt(i);
			listItemtienda.add(item);
		}
	}
	
	private void rowsArrayItems2(){
		int row = tableDtaModelBuild.getTotalRows();
		
		listItembuild = new ArrayList<Item>();
		Item item;
		
		for(int i = 0; i < row; i++)
		{
			item = (Item) tableDtaModelBuild.getElementAt(i);
			listItembuild.add(item);
		}
	}
	
	private void AsignarDatos (int t, int level, String nombre, int precio, int indice, String tipo, String descripcion, boolean uso, String dirImage)
	{
		Item item = new Item();
		
		item.setLevel(level);
		item.setName(nombre);
		item.setPrice(precio);
		item.setIndex(indice);
		item.setType(tipo);
		item.setDescription(descripcion);
		item.setUse(uso);
		item.setImage(dirImage);
		if(t == 1){
			listItemtienda = new ArrayList<Item>();
			listItemtienda.add(item);		
			tableDtaModel.add(item);
		}
		else if(t == 2){
			listItemplayer = new ArrayList<Item>();
			listItemplayer.add(item);			
			tableDtaModelPlayer.add(item);			
		}
		else if(t == 3){
			listItembuild = new ArrayList<Item>();
			listItembuild.add(item);			
			tableDtaModelBuild.add(item);
		}
	}
	
	private void CreateList(int tipo)
	{
		if(tipo == 1){
			AsignarDatos(tipo, 1, "Armadura", 1500, 20, "Armadura", "Armadura Básica",false,"Images/Items/armor.png");
			AsignarDatos(tipo, 1, "Espada", 1500, 20, "Espada", "Espada Básica",false,"Images/Items/sword.png");
			
			AsignarDatos(tipo, 2, "Piedra Blanca", 1000, 10, "Piedra", "Piedra mágica de color Blanco",false,"Images/Items/stone1.png");
			AsignarDatos(tipo, 2, "Medicina20", 800, 20, "Pocion", "Medicina  básica para aumentar la vida",false,"Images/Items/potion1.png");
			
			AsignarDatos(tipo, 3, "Piedra Negra", 2500, 25, "Piedra", "Piedra mágica de color Negro",false,"Images/Items/stone2.png");
			AsignarDatos(tipo, 3, "Armadura Negra", 5000, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
			
			AsignarDatos(tipo, 4, "Energia", 1500, 20, "Pocion", "Pocion para aumentar la psinergia",false,"Images/Items/potion2.png");
			AsignarDatos(tipo, 4, "Medicina40", 1800, 40, "Pocion", "Medicina de media categoría para aumentar la vida",false,"Images/Items/potion1.png");
			
			AsignarDatos(tipo, 5, "Piedra Roja", 3000, 35, "Piedra", "Piedra mágica de color Rojo",false,"Images/Items/stone3.png");
			AsignarDatos(tipo, 5, "Espada Roja", 5000, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
			
			AsignarDatos(tipo, 6, "Bomba Blanca", 3500, 30, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb.png");
			AsignarDatos(tipo, 6, "Bomba Negra", 6500, 60, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png");
			
			AsignarDatos(tipo, 7, "Bomba Roja", 9500, 90, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png");
			AsignarDatos(tipo, 7, "Energia Super", 2500, 40, "Pocion", "Pocion para aumentar mucho más la psinergia",false,"Images/Items/potion2.png");
			
			AsignarDatos(tipo, 9, "Medicina Super", 3000, 80, "Pocion", "Poderosa Medicina para aumentar la vida",false,"Images/Items/potion1.png");
		}
		else if(tipo == 3){
			AsignarDatos(tipo, 3, "Armadura Negra", 5000, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
			AsignarDatos(tipo, 5, "Espada Roja", 5000, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
			
			AsignarDatos(tipo, 6, "Bomba Blanca", 3500, 30, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb.png");
			AsignarDatos(tipo, 6, "Bomba Negra", 6500, 60, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png");
			
			AsignarDatos(tipo, 7, "Bomba Roja", 9500, 90, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png");			
		}
	}
	
	private void CreateListItem(List<Item> it)
	{
			for(int i = 0; i < it.size(); i++)
			{
				AsignarDatos(2, it.get(i).getLevel(), it.get(i).getName(), it.get(i).getPrice(), it.get(i).getIndex(), //
						it.get(i).getType(), it.get(i).getDescription(), false,it.get(i).getImage());
			}
	}
	
	private void btnSeeClicked(Item it) 
	{
		  WindowPane win = new WindowPane();
		  win.setTitle("Descripción");
		  win.setWidth(new Extent(200));
		  win.setMaximumWidth(new Extent(200));
		  win.setMaximumHeight(new Extent(100));
		  win.setMovable(false);
		  win.setResizable(false);
		  Column col = new Column();
		  col.add(new Label("Objeto   : " + it.getName()));
		  col.add(new Label("Detalles : " + it.getDescription()));
		  col.add(new Label("Indice   : " + it.getIndex()));
		  win.add(col);
		  win.setModal(true);
		  add(win);
	}
	
	private void btnVerClicked2(int row) 
	{
		Item item = (Item) tableDtaModelPlayer.getElementAt(row);
		
		if (item.getType() == "Armadura")
			infoItemArmor(item);
		else if (item.getType() == "Espada")
			infoItemSword(item);
		else if (item.getType() == "Pocion")
			infoItemPotion(item);
		else if (item.getType() == "Piedra")
			infoItemStone(item);
		else
			infoItemBomb(item);
	}
	
	private void btnAllClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			tableDtaModel.add(listItemtienda.get(i));					
		}		
	}

	private void btnAllClicked2() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			tableDtaModelPlayer.add(listItemplayer.get(i));					
		}		
	}
	
	private void btnArmorClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			if (listItemtienda.get(i).getType() == "Armadura")
			{
				tableDtaModel.add(listItemtienda.get(i));
			}			
		}		
	}
	
	private void btnArmorClicked2() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();
		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			if (listItemplayer.get(i).getType() == "Armadura")
			{
				tableDtaModelPlayer.add(listItemplayer.get(i));
			}			
		}		
	}
	
	private void btnSwordClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			if (listItemtienda.get(i).getType() == "Espada")
			{
				tableDtaModel.add(listItemtienda.get(i));
			}			
		}
	}
	
	private void btnSwordClicked2() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();
		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			if (listItemplayer.get(i).getType() == "Espada")
			{
				tableDtaModelPlayer.add(listItemplayer.get(i));
			}			
		}
	}
	
	private void btnPotionClicked() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			if (listItemtienda.get(i).getType() == "Pocion")
			{
				tableDtaModel.add(listItemtienda.get(i));
			}			
		}
	}
	
	private void btnPotionClicked2() {
		
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();
		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			if (listItemplayer.get(i).getType() == "Pocion")
			{
				tableDtaModelPlayer.add(listItemplayer.get(i));
			}			
		}
	}
	
	private void btnStoneClicked()
	{
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			if (listItemtienda.get(i).getType() == "Piedra")
			{
				tableDtaModel.add(listItemtienda.get(i));
			}			
		}		
	}
	
	private void btnStoneClicked2()
	{
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();
		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			if (listItemplayer.get(i).getType() == "Piedra")
			{
				tableDtaModelPlayer.add(listItemplayer.get(i));
			}			
		}		
	}
	
	private void btnBombClicked()
	{
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listItemtienda.size(); i++)
		{
			if (listItemtienda.get(i).getType() == "Bomba")
			{
				tableDtaModel.add(listItemtienda.get(i));
			}			
		}		
	}
	
	private void btnBombClicked2()
	{
		name.setText("");
		index.setText("");
		description.setText("");
		tableDtaModelPlayer.clear();
		
		for(int i = 0; i < listItemplayer.size(); i++)
		{
			if (listItemplayer.get(i).getType() == "Bomba")
			{
				tableDtaModelPlayer.add(listItemplayer.get(i));
			}			
		}		
	}
	
	private void rowsArrayItemsPlayer(){
		int row = tableDtaModelPlayer.getTotalRows();
		
		listItemplayer = new ArrayList<Item>();
		Item item;
		
		for(int i = 0; i < row; i++)
		{
			item = (Item) tableDtaModelPlayer.getElementAt(i);
			listItemplayer.add(item);
		}
	}
	
	private void infoItemArmor(Item item)
	{
		if (item.getName() == "Armadura")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Armadura Negra")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
	}
	
	private void infoItemSword(Item item)
	{
		if (item.getName() == "Espada")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Espada Roja")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
	}

	private void infoItemStone(Item item)
	{
		if (item.getName() == "Piedra Blanca")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Piedra Negra")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Piedra Roja")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
	}
	
	private void infoItemPotion(Item item)
	{
		if (item.getName() == "Medicina20")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Medicina40")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Medicina Super")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Energia")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Energia Super")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
	}
	
	private void infoItemBomb(Item item)
	{
		if (item.getName() == "Bomba Blanca")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
		
		if (item.getName() == "Bomba Negra")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}	
		
		if (item.getName() == "Bomba Roja")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText(item.getDescription());
		}
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
		
		Button btnRefinar = new Button();
		btnRefinar.setText("Refinar");
		btnRefinar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnRefinar.setStyle(Styles1.DEFAULT_STYLE);
		btnRefinar.setToolTipText("Fabricar Items compuestos");		
		btnRefinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initRefinar());
			}
		});
		menu.add(btnRefinar);
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
		add(new MapaDesktop(usuario));
	}	
}
