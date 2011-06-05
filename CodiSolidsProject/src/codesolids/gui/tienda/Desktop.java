/*
 * Created on 25/05/2011
 */
package codesolids.gui.tienda;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileCacheImageOutputStream;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;
import com.minotauro.echo.table.renderer.ImageCellRenderer;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Border.Side;
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
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;

import codesolids.gui.tienda.TestTableModel;
import codesolids.gui.tienda.Item;
import codesolids.gui.academia.Poder;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;
import echopoint.util.ExtentKit;

/**
 * @author Fernando Osuna
 * 
 */

public class Desktop extends ContentPane {
	private HtmlLayout htmlLayout;
	
	private TestTableModel tableDtaModel;
	private TestTableModel tableDtaModelPlayer;
	
	private ETable table;
	private ETable tablePlayer;
	
	private Label name;
	private Label index;
	private Label description;
	
	WindowPane ventana;

	private List<Item> listItemtienda;
	private List<Item> listItemplayer;
	private Personaje player = new Personaje();
	
	public Desktop() {
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
		
		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
		ResourceImageReference w = new ResourceImageReference("Images/Items/pergamino.png");
		htmlLayout.setBackgroundImage(ir);
		ImageReference image = w;
	
		HtmlLayoutData hld;

		hld = new HtmlLayoutData("left");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		hld = new HtmlLayoutData("central");
		
		Row row = new Row();
		Column col2 = new Column();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		col2.add(description);
		col2.setLayoutData(hld);
		htmlLayout.add(col2);
		
		Panel panel = new Panel();
		
		col2.setCellSpacing(new Extent(5));
		description = new Label("Bienvenido a la Tienda de articulos mágicos del comerciante Merlot");
		col2.add(description);
		description = new Label("Aqui  encontrarás  los objetos, piedras  mágicas, espadas, armaduras,");
		col2.add(description);
		description = new Label("pociones que aumentarán tu vida, pociones que te daran mas psinergia,");
		col2.add(description);
		description = new Label("bombas para dañar a tus enemigos y  un taller para refinar tus Items.");
		col2.add(description);
		description = new Label("Merlot es un viejo ambicioso que vende su mercancia a un alto precio ");
		col2.add(description);
		description = new Label("pero si te compra algo lo hace por la mitad de su precio original,   ");
		col2.add(description);
		description = new Label("asi que piensalo muy bien antes de comprar o vender un objeto.");
		col2.add(description);
		row.add(col2);
		
		FillImage imagep = new FillImage(image);
		
		panel.add(row);
		panel.setLayoutData(hld);
		panel.setBackgroundImage(imagep);
		panel.setHeight(new Extent(385));
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
		hld = new HtmlLayoutData("central");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		setInsets(new Insets(5, 5, 5, 5));
		
		Row row = new Row();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		row.add(description);
		description = new Label("");
		row.add(description);
		row.setLayoutData(hld);
		htmlLayout.add(row);
		
//		Grid col = new Grid(1);
//		col.add(ventana);
		
//		add(col);
		
		Grid colW = new Grid(1);
		
		colW.add(initTopRow());		
		
		TableColModel tableColModel = initTableColModel1();
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
	    
	    colW.add(table);
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    colW.add(tableNavigation);
	    
	    colW.add(initFoot());
	    
	    CreateList();
		rowsArrayItems();
		
//		ventana = new WindowPane();
//		ventana.setTitle("Tienda");
//		ventana.add(colW);
//		ventana.setMovable(false);
//		ventana.setResizable(false);
//		ventana.setContentHeight(new Extent(410));
//		ventana.setContentWidth(new Extent(390));
//		ventana.setInsets(new Insets(10,10,10,10));
//		
//		ventana.addWindowPaneListener(new WindowPaneListener() {
//			public void windowPaneClosing(WindowPaneEvent evt) {
//				add(new Label("Aqui me voy al mapa"));
//			}
//		});		

		colW.setLayoutData(hld);
		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
		htmlLayout.setBackgroundImage(ir);
		htmlLayout.add(colW);
		
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
		
		hld = new HtmlLayoutData("central");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		setInsets(new Insets(5, 5, 5, 5));
		
		Row row = new Row();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		row.add(description);
		description = new Label("");
		row.add(description);
		row.setLayoutData(hld);
		htmlLayout.add(row);
		
		Grid colW = new Grid(1);
		
		colW.add(initTopRowVender());
		
		TableColModel tableColModel = initTableColModel2();
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
	    
	    colW.add(tablePlayer);
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModelPlayer);
	    colW.add(tableNavigation);
	    
	    colW.add(initFoot());
	    
	    CreateListItem(player.getItems());
		rowsArrayItemsPlayer();
		colW.setLayoutData(hld);
		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
		htmlLayout.setBackgroundImage(ir);
		htmlLayout.add(colW);
		
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
		
		hld = new HtmlLayoutData("central");
		Column colmenu = new Column();
		
		menu(hld, colmenu);		
		htmlLayout.add(colmenu);
		
		setInsets(new Insets(5, 5, 5, 5));
		
		Grid colW = new Grid(1);		
		colW.add(initTopRowVender());
		
		Column col = new Column();
		Row row = new Row();
		description = new Label("REFINA TUS ITEMS ");
		description.setBackground(new Color(220, 164, 91));
		col.add(description);
		description = new Label("");
		description.setBackground(new Color(148, 111, 63));
		col.add(description);
		description = new Label("Item ");
		row.add(description);
		row.setCellSpacing(new Extent(200));
		description = new Label("Acción");
		row.add(description);
		row.setCellSpacing(new Extent(100));
		col.add(row);
		final String nameItem1;
		final String nameItem2;
		final String nameItem3;
		final String nameItem4;
		final String nameItem5;
		String nameIt = "";
		row = new Row();
		nameIt = "Armor Black";
		nameItem1 = nameIt;
		
		description = new Label(nameItem1);
		row.add(description);
		row.setCellSpacing(new Extent(100));

		Button btnBuild1 = new Button();
		btnBuild1.setText("Fabricar");
		btnBuild1.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild1.setWidth(new Extent(60));
		btnBuild1.setHeight(new Extent(15));
		btnBuild1.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild1.setToolTipText("Fabricar este Item");
		btnBuild1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem1);
				it = player.searchItem("Armor");
				player.removeItem(it);
				it = player.searchItem("Stone Black");
				player.removeItem(it);
				removeAll();
				add(initRefinar());
			}
		});
		if(player.contains("Armor") && player.contains("Stone Black")){
			btnBuild1.setEnabled(true);						
		}else
			btnBuild1.setEnabled(false);		
		row.add(btnBuild1);
		row.setCellSpacing(new Extent(100));			
		col.add(row);

		row = new Row();
		nameIt = "Sword Red";
		nameItem2 = nameIt;
		
		description = new Label(nameItem2);
		row.add(description);
		row.setCellSpacing(new Extent(100));
		
		Button btnBuild2 = new Button();
		btnBuild2.setText("Fabricar");
		btnBuild2.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild2.setWidth(new Extent(60));
		btnBuild2.setHeight(new Extent(15));
		btnBuild2.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild2.setToolTipText("Fabricar este Item");
		btnBuild2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem2);
				it = player.searchItem("Sword");
				player.removeItem(it);
				it = player.searchItem("Stone Red");
				player.removeItem(it);
				removeAll();
				add(initRefinar());
			}
		});
		if(player.contains("Sword") && player.contains("Stone Red")){
			btnBuild2.setEnabled(true);						
		}else
			btnBuild2.setEnabled(false);
		row.add(btnBuild2);
		row.setCellSpacing(new Extent(106));
		col.add(row);

		nameIt = "White Bomb";
		nameItem3 = nameIt;

		row = new Row();
		description = new Label(nameItem3);
		row.add(description);
		row.setCellSpacing(new Extent(100));
		
		Button btnBuild3 = new Button();
		btnBuild3.setText("Fabricar");
		btnBuild3.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild3.setWidth(new Extent(60));
		btnBuild3.setHeight(new Extent(15));
		btnBuild3.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild3.setToolTipText("Fabricar este Item");
		btnBuild3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem3);
				it = player.searchItem("Stone White");
				player.removeItem(it);
				it = player.searchItem("Stone White");
				player.removeItem(it);
				it = player.searchItem("Stone White");
				player.removeItem(it);
				removeAll();
				add(initRefinar());
			}
		});
				
		if(player.countItem("Stone White") >= 3){
			btnBuild3.setEnabled(true);						
		}else
			btnBuild3.setEnabled(false);
		
		row.add(btnBuild3);
		row.setCellSpacing(new Extent(100));
		col.add(row);
		
		nameIt = "Black Bomb";
		nameItem4 = nameIt;
		row = new Row();
		
		description = new Label(nameItem4);
		row.add(description);
		row.setCellSpacing(new Extent(100));

		Button btnBuild4 = new Button();
		btnBuild4.setText("Fabricar");
		btnBuild4.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild4.setWidth(new Extent(60));
		btnBuild4.setHeight(new Extent(15));
		btnBuild4.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild4.setToolTipText("Fabricar este Item");
		btnBuild4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem4);
				it = player.searchItem("Stone White");
				player.removeItem(it);
				it = player.searchItem("Stone Red");
				player.removeItem(it);
				it = player.searchItem("Stone Black");
				player.removeItem(it);
				removeAll();
				add(initRefinar());
			}
		});
		
		if(player.contains("Stone White") && player.contains("Stone Red") && player.contains("Stone Black")){
			btnBuild4.setEnabled(true);						
		}else
			btnBuild4.setEnabled(false);
		
		row.add(btnBuild4);
		row.setCellSpacing(new Extent(100));
		col.add(row);

		nameIt = "Red Bomb";
		nameItem5 = nameIt;
		row = new Row();
		
		description = new Label(nameItem5);
		row.add(description);
		row.setCellSpacing(new Extent(100));

		Button btnBuild5 = new Button();
		btnBuild5.setText("Fabricar");
		btnBuild5.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild5.setWidth(new Extent(60));
		btnBuild5.setHeight(new Extent(15));
		btnBuild5.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild5.setToolTipText("Fabricar este Item");
		btnBuild5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem5);
				it = player.searchItem("Stone Red");
				player.removeItem(it);
				it = player.searchItem("Stone Red");
				player.removeItem(it);
				it = player.searchItem("Stone Red");
				player.removeItem(it);
				removeAll();
				add(initRefinar());
			}
		});
		if(player.countItem("Stone Red") >= 3){
			btnBuild5.setEnabled(true);						
		}else
			btnBuild5.setEnabled(false);
		
		row.add(btnBuild5);
		row.setCellSpacing(new Extent(110));
		col.add(row);			
				
		/*Esto es para prueba
		 Item it = new Item();
		it = player.searchItem("Sword");
		player.removeItem(it);
		for( int i = 0 ; i<player.getItems().size(); i++){
			description = new Label(""+player.getItems().get(i).getName());
			col.add(description);
		}
		if(player.contains("Armor")){
			description = new Label("ENCONTRADO");
			col.add(description);
		}*/
		col.setBackground(Color.WHITE);
		colW.add(col);
		colW.setLayoutData(hld);
		htmlLayout.add(colW);
		
		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
		htmlLayout.setBackgroundImage(ir);
		
		return htmlLayout;
	}
	
	private Row initTopRow() {
		
		setInsets(new Insets(5, 5, 5, 5));
		
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
                
        row.add(rowBtn);
        
        rowBtn = new Row();

	    Button btnExit = new Button("Salir");
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
	
private Row initTopRowVender() {
		
		setInsets(new Insets(5, 5, 5, 5));
		
		Row row = new Row();
	    row.setCellSpacing(new Extent(150));
		
		Row rowBtn = new Row();
		rowBtn.setCellSpacing(new Extent(5));

	    Button btnExit = new Button("Salir");
	    btnExit.setStyle(Styles1.DEFAULT_STYLE);
	    btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              removeAll();
              add(initMapa());
            }
        });
        rowBtn.add(btnExit);
        row.add(rowBtn);
        row.setAlignment(Alignment.ALIGN_RIGHT);
        
	    return row;
	}
	
	private Column initFoot() {
		setInsets(new Insets(5, 5, 5, 5));
		Column col = new Column(); 
		
		description = new Label("Puedes ver los detalles de los objetos");
		description.setBackground(Color.WHITE);
		col.add(description);
		
		col.add(DscripRow());
		
		return col;
	}
	
	private Column DscripRow()
	{
		setInsets(new Insets(5, 5, 5, 5));
		
		Column col = new Column();
		
		name = new Label();
		col.add(name);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		index = new Label();
		row.add(index);
		col.add(row);

		col.add(description);

		return col;
	}
	
	private TableColModel initTableColModel1() {		
		
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Level");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Item item = (Item) element;
	    		return item.getImage();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    ImageCellRenderer icr = new ImageCellRenderer();
	    icr.setBackground(Color.WHITE);
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
	    lcr.setForeground(new Color(84, 125, 43));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Precio");
	    
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);
	    

	    tableColumn.setDataCellRenderer(initNestedCellRenderer());
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    return tableColModel;
	}
	
	private CellRenderer initNestedCellRenderer() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
	    
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


	          if (item.getPrice() < player.getGold())
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBuyClicked(row);
	        			  }
	        		  
	        			  private void btnBuyClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModel.getElementAt(row);
	        			  
	        				  WindowPane ventanaCompra = new WindowPane();
	        				  ventanaCompra.setTitle("Tienda - Compra");
	        				  ventanaCompra.setWidth(new Extent(300));
	        				  ventanaCompra.setMaximumWidth(new Extent(300));
	        				  ventanaCompra.setMaximumHeight(new Extent(100));
	        				  ventanaCompra.setMovable(false);
	        				  ventanaCompra.setResizable(false);
	        				  ventanaCompra.add(new Label("Usted compro " + item.getName()));
	        				  ventanaCompra.setModal(true);
	        				  add(ventanaCompra);
	        				  player.setGold(player.getGold() - item.getPrice());
	        				  player.setItems(item);
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
	
private TableColModel initTableColModel2() {		
		
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Level");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Item item = (Item) element;
	    		return item.getImage();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    ImageCellRenderer icr = new ImageCellRenderer();
	    icr.setBackground(Color.WHITE);
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
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
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.WHITE);
	    lcr.setForeground(new Color(84, 125, 43));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Precio");	    
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(148, 111, 63));
	    lcr.setForeground(Color.BLACK);
	    tableColumn.setHeadCellRenderer(lcr);	    

	    tableColumn.setDataCellRenderer(initNestedCellRendererSell());
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    return tableColModel;
	}
	
	private NestedCellRenderer initNestedCellRendererSell() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
	    
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
	        				  btnBuyClicked(row);
	        			  }
	        		  
	        			  private void btnBuyClicked(int row) {
	        				
	        				  Item item = (Item) tableDtaModelPlayer.getElementAt(row);
	        				  tableDtaModelPlayer.del(tableDtaModelPlayer.getElementAt(row));
	        			  
	        				  WindowPane ventanaCompra = new WindowPane();
	        				  ventanaCompra.setTitle("Tienda - Compra");
	        				  ventanaCompra.setWidth(new Extent(300));
	        				  ventanaCompra.setMaximumWidth(new Extent(300));
	        				  ventanaCompra.setMaximumHeight(new Extent(100));
	        				  ventanaCompra.setMovable(false);
	        				  ventanaCompra.setResizable(false);
	        				  ventanaCompra.add(new Label("Usted compro " + item.getName()));
	        				  ventanaCompra.setModal(true);
	        				  add(ventanaCompra);
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
	
	private void AsignarDatos (int level, String nombre, int precio, int indice, String tipo, boolean uso, String dirImage)
	{
		Item item = new Item();
		
		item.setLevel(level);
		item.setName(nombre);
		item.setPrice(precio);
		item.setIndex(indice);
		item.setType(tipo);
		item.setUse(uso);
		item.setImage(dirImage);
		
		listItemtienda = new ArrayList<Item>();
		listItemtienda.add(item);
		
		tableDtaModel.add(item);
	}
	
	private void AsignarDatosItem (int level, String nombre, int precio, int indice, String tipo, boolean uso, String dirImage)
	{
		Item item = new Item();		
		item.setLevel(level);
		item.setName(nombre);
		item.setPrice(precio);
		item.setIndex(indice);
		item.setType(tipo);
		item.setUse(uso);
		item.setImage(dirImage);
		
		listItemplayer = new ArrayList<Item>();
		listItemplayer.add(item);
		
		tableDtaModelPlayer.add(item);
	}
	
	private void CreateList()
	{
		
		AsignarDatos(1, "Armor", 1500, 30, "Armadura",false,"Images/Items/armor.jpeg");
		AsignarDatos(1, "Sword", 1500, 30, "Espada",false,"Images/Items/sword.jpeg");
		
		AsignarDatos(2, "Stone White", 1000, 10, "Piedra",false,"Images/Items/stone.jpeg");
		AsignarDatos(2, "Medicine", 800, 40, "Pocion",false,"Images/Items/potion1.jpeg");
		
		AsignarDatos(3, "Stone Black", 2500, 25, "Piedra",false,"Images/Items/stone.jpeg");
		AsignarDatos(3, "Armor Black", 4600, 30, "Armadura",false,"Images/Items/armor.jpeg");
		
		AsignarDatos(4, "Energy", 1500, 10, "Pocion",false,"Images/Items/potion2.jpeg");
		
		AsignarDatos(5, "Stone Red", 3000, 35, "Piedra",false,"Images/Items/stone.jpeg");
		AsignarDatos(5, "Sword Red", 4600, 30, "Espada",false,"Images/Items/sword2.jpeg");
		AsignarDatos(5, "White Bomb", 3100, 35, "Bomba",false,"Images/Items/bomb.jpeg");
		AsignarDatos(5, "Black Bomb", 6100, 60, "Bomba",false,"Images/Items/bomb.jpeg");
		AsignarDatos(5, "Red Bomb", 9100, 90, "Bomba",false,"Images/Items/bomb.jpeg");
		
	}
	
	private void CreateListItem(List<Item> it)
	{
			for(int i = 0; i < it.size(); i++)
			{
				AsignarDatosItem(it.get(i).getLevel(), it.get(i).getName(), it.get(i).getPrice(), it.get(i).getIndex(), //
						it.get(i).getType(),false,it.get(i).getImage());
			}
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
		if (item.getName() == "Armor")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Armadura Básica");
		}
		
		if (item.getName() == "Armor Black")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Armadura Avanzada");
		}
		name.setBackground(Color.WHITE);
		index.setBackground(Color.WHITE);
	}
	
	private void infoItemSword(Item item)
	{
		if (item.getName() == "Sword")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Espada Básica");
		}
		
		if (item.getName() == "Sword Red")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Espada Avanzada");
		}
		name.setBackground(Color.WHITE);
		index.setBackground(Color.WHITE);
	}

	private void infoItemStone(Item item)
	{
		if (item.getName() == "Stone White")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Piedra Blanca");
		}
		
		if (item.getName() == "Stone Black")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Piedra Negra");
		}
		
		if (item.getName() == "Stone Red")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Piedra Roja");
		}
		name.setBackground(Color.WHITE);
		index.setBackground(Color.WHITE);
	}
	
	private void infoItemPotion(Item item)
	{
		if (item.getName() == "Medicine")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Medicina para aumentar la vida");
		}
		
		if (item.getName() == "Energy")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Pocion para aumentar la psinergia");
		}
		name.setBackground(Color.WHITE);
		index.setBackground(Color.WHITE);
	}
	
	private void infoItemBomb(Item item)
	{
		if (item.getName() == "White Bomb")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Bomba mágica de color blanco");
		}
		
		if (item.getName() == "Black Bomb")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Bomba mágica de color negro");
		}	
		
		if (item.getName() == "Red Bomb")
		{
			description.setText("");
			name.setText("" + item.getName());
			index.setText("Indice: " + item.getIndex());
			description.setText("Bomba mágica de color rojo");
		}
		name.setBackground(Color.WHITE);
		index.setBackground(Color.WHITE);
	}
	
	private void menu(HtmlLayoutData hld , Column col){
		hld = new HtmlLayoutData("left");
		Column menu = new Column();
		Button btnComprar = new Button();
		btnComprar.setText("Comprar");
		btnComprar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnComprar.setWidth(new Extent(60));
		btnComprar.setHeight(new Extent(15));
		btnComprar.setStyle(Styles1.DEFAULT_STYLE);
		btnComprar.setToolTipText("Comprar Items");		
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initComprar());
			}
		});
		
		menu.add(btnComprar);
		col.add(menu);
		
		Button btnVender = new Button();
		btnVender.setText("Vender");
		btnVender.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnVender.setWidth(new Extent(60));
		btnVender.setHeight(new Extent(15));
		btnVender.setStyle(Styles1.DEFAULT_STYLE);
		btnVender.setToolTipText("Vender Items");		
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initVender());
			}
		});		
		menu.add(btnVender);
		col.add(menu);
		
		Button btnRefinar = new Button();
		btnRefinar.setText("Refinar");
		btnRefinar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnRefinar.setWidth(new Extent(60));
		btnRefinar.setHeight(new Extent(15));
		btnRefinar.setStyle(Styles1.DEFAULT_STYLE);
		btnRefinar.setToolTipText("Fabricar Items compuestos");
		
		btnRefinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initRefinar());
			}
		});		
		menu.add(btnRefinar);
		col.add(menu);		
		col.setLayoutData(hld);
	}
	
}
