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
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import codesolids.gui.tienda.Item;
import codesolids.gui.tienda.ImageReferenceCache;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Fernando Osuna
 * 
 */
@SuppressWarnings("serial")
public class TiendaDesktop extends ContentPane {
	private HtmlLayout htmlLayout;
	
	private Label description;
	
	WindowPane window;

	private List<Item> listItemtienda;
	private Personaje player = new Personaje();
	private int indexTab = 0;
	
	Button btnBuild5 = new Button();
	Button btnBuild1 = new Button();
    Button btnBuild2 = new Button();
    Button btnBuild3 = new Button();
    Button btnBuild4 = new Button();

	
	private String[] imagePath = new String[]{
			"Images/Items/armor.png","Images/Items/sword.png","Images/Items/stone1.png","Images/Items/potion1.png",
			"Images/Items/stone2.png","Images/Items/armor2.png","Images/Items/potion2.png","Images/Items/potion1.png",
			"Images/Items/stone3.png","Images/Items/sword2.png","Images/Items/bomb.png","Images/Items/bomb2.png",
			"Images/Items/bomb3.png","Images/Items/potion2.png","Images/Items/potion1.png"};
	
	public TiendaDesktop() {
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
		Row title = new Row();
		description = new Label("Tienda de Articulos Mágicos");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(40)));
		description.setForeground(Color.WHITE);
		title.add(description);
		title.setLayoutData(hld);
		htmlLayout.add(title);

		hld = new HtmlLayoutData("left");
		Column col = new Column();
		Row foot = new Row();
		
		menu(hld, col, foot);	
		htmlLayout.add(col);
		htmlLayout.add(foot);
		
		hld = new HtmlLayoutData("central");
		
		Row head = new Row();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		Panel panel = new Panel();
		Column col2 = new Column();
		Row row = new Row();
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
		Row title = new Row();
		description = new Label("Tienda de Articulos Mágicos");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(40)));
		description.setForeground(Color.WHITE);
		title.add(description);
		title.setLayoutData(hld);
		htmlLayout.add(title);
		
		hld = new HtmlLayoutData("left");
		Column col = new Column();
		Row foot = new Row();
		
		menu(hld, col, foot);	
		htmlLayout.add(col);
		htmlLayout.add(foot);
		
		hld = new HtmlLayoutData("central");
		Row head = new Row();
		description = new Label("COMPRAR ");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(25)));
		description.setForeground(Color.WHITE);
		head.add(description);
		head.setCellSpacing(new Extent(270));
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setCellSpacing(new Extent(310));
		head.add(exit());
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		Panel panel = new Panel();
		TabPane tabPane = new TabPane();
		
		tabPane.setTabActiveBackground(new Color(228, 228, 228));
	    tabPane.setTabActiveForeground(Color.BLACK);

	    tabPane.setTabInactiveBackground(Color.LIGHTGRAY);
	    tabPane.setTabInactiveForeground(Color.BLACK);

	    tabPane.setBackground(new Color(228, 228, 228));
	    tabPane.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    tabPane.setActiveTabIndex(indexTab);
	    
	    TabPaneLayoutData tpld = new TabPaneLayoutData();
	    tpld.setTitle("Todo");
		
		Grid grid = new Grid(4);
		
		grid.setLayoutData(tpld);
		grid.setHeight(new Extent(300));
		grid.setWidth(new Extent(800));
		grid.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		Row rowTab = new Row();
		Column colTab = new Column();
		//ResourceImageReference ir;		Otra forma de asignar las imagenes
		Label lblImage;
	    Label lblData;

	    listItemtienda = new ArrayList<Item>();
	    CreateList();
		for (int i = 0; i < listItemtienda.size(); i++){

			ImageReferenceCache irc = ImageReferenceCache.getInstance();
		    ImageReference ir = irc.getImageReference(imagePath[i]);
			rowTab = new Row();
			colTab = new Column();
			//ir = new ResourceImageReference(listItemtienda.get(i).getImage());
			lblImage = new Label(ir);
			lblData = new Label(listItemtienda.get(i).getName());
			colTab.add(lblData);
			lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
		    colTab.add(lblData);
		    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
		    colTab.add(lblData);
		    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
		    colTab.add(lblData);
		    Button btnVer = new Button();
		    btnVer.setText("Ver");
		    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnVer.setWidth(new Extent(60));
		    btnVer.setHeight(new Extent(15));
		    btnVer.setStyle(Styles1.DEFAULT_STYLE);
		    btnVer.setToolTipText("Ver");
		    btnVer.setEnabled(true);
		    final Item it = listItemtienda.get(i);
		    btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnSeeClicked(it);				  
				}				
			});
		    
		    colTab.add(btnVer);
		    Button btnBuy = new Button();
		    btnBuy.setText("Comprar");
		    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnBuy.setWidth(new Extent(60));
		    btnBuy.setHeight(new Extent(15));
		    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
		    btnBuy.setToolTipText("Comprar");
		    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
		    	btnBuy.setEnabled(true);
		    }
		    else{
		    	btnBuy.setEnabled(false);
		    }
		    btnBuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					player.setGold(player.getGold() - it.getPrice());
					player.setItems(it);
					indexTab = 0;
					removeAll();
					add(initComprar());
				}
			});
		    colTab.add(btnBuy);
		    rowTab.add(lblImage);
		    rowTab.add(colTab);
		    rowTab.setBackground(Color.WHITE);
		    grid.add(rowTab);
		}
		tabPane.add(grid);
		
		tpld = new TabPaneLayoutData();
	    tpld.setTitle("Armaduras");		
		Grid grid2 = new Grid(2);
		
		grid2.setLayoutData(tpld);
		grid2.setHeight(new Extent(300));
		grid2.setWidth(new Extent(400));
		grid2.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			if(listItemtienda.get(i).getType() == "Armadura"){
				ImageReferenceCache irc = ImageReferenceCache.getInstance();
			    ImageReference ir = irc.getImageReference(imagePath[i]);
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnVer = new Button();
			    btnVer.setText("Ver");
			    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnVer.setWidth(new Extent(60));
			    btnVer.setHeight(new Extent(15));
			    btnVer.setStyle(Styles1.DEFAULT_STYLE);
			    btnVer.setToolTipText("Ver");
			    btnVer.setEnabled(true);
			    btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSeeClicked(it);				  
					}				
				});
			    colTab.add(btnVer);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
						indexTab = 1;
						removeAll();
						add(initComprar());						
					}
				});
			    colTab.add(btnBuy);
			    rowTab.add(lblImage);
			    rowTab.add(colTab);
			    rowTab.setBackground(Color.WHITE);
			    grid2.add(rowTab);
			}
		}
		tabPane.add(grid2);
		
		tpld = new TabPaneLayoutData();
	    tpld.setTitle("Bombas");
		
		grid2 = new Grid(2);
		
		grid2.setLayoutData(tpld);
		grid2.setHeight(new Extent(300));
		grid2.setWidth(new Extent(400));
		grid2.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			if(listItemtienda.get(i).getType() == "Bomba"){
				ImageReferenceCache irc = ImageReferenceCache.getInstance();
			    ImageReference ir = irc.getImageReference(imagePath[i]);
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnVer = new Button();
			    btnVer.setText("Ver");
			    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnVer.setWidth(new Extent(60));
			    btnVer.setHeight(new Extent(15));
			    btnVer.setStyle(Styles1.DEFAULT_STYLE);
			    btnVer.setToolTipText("Ver");
			    btnVer.setEnabled(true);
			    btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSeeClicked(it);				  
					}				
				});
			    colTab.add(btnVer);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
						indexTab = 2;
						removeAll();
						add(initComprar());						
					}
				});
			    colTab.add(btnBuy);
			    rowTab.add(lblImage);
			    rowTab.add(colTab);
			    rowTab.setBackground(Color.WHITE);
			    grid2.add(rowTab);
			}
		}
		tabPane.add(grid2);
		
		tpld = new TabPaneLayoutData();
	    tpld.setTitle("Espadas");
		
		grid2 = new Grid(2);
		
		grid2.setLayoutData(tpld);
		grid2.setHeight(new Extent(300));
		grid2.setWidth(new Extent(400));
		grid2.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			if(listItemtienda.get(i).getType() == "Espada"){
				ImageReferenceCache irc = ImageReferenceCache.getInstance();
			    ImageReference ir = irc.getImageReference(imagePath[i]);
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnVer = new Button();
			    btnVer.setText("Ver");
			    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnVer.setWidth(new Extent(60));
			    btnVer.setHeight(new Extent(15));
			    btnVer.setStyle(Styles1.DEFAULT_STYLE);
			    btnVer.setToolTipText("Ver");
			    btnVer.setEnabled(true);
			    btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSeeClicked(it);				  
					}				
				});
			    colTab.add(btnVer);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
						indexTab = 3;
						removeAll();
						add(initComprar());						
					}
				});
			    colTab.add(btnBuy);
			    rowTab.add(lblImage);
			    rowTab.add(colTab);
			    rowTab.setBackground(Color.WHITE);
			    grid2.add(rowTab);
			}
		}
		tabPane.add(grid2);
		
		tpld = new TabPaneLayoutData();
	    tpld.setTitle("Piedras");
		
		grid2 = new Grid(2);
		
		grid2.setLayoutData(tpld);
		grid2.setHeight(new Extent(300));
		grid2.setWidth(new Extent(400));
		grid2.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			if(listItemtienda.get(i).getType() == "Piedra"){
				ImageReferenceCache irc = ImageReferenceCache.getInstance();
			    ImageReference ir = irc.getImageReference(imagePath[i]);
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnVer = new Button();
			    btnVer.setText("Ver");
			    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnVer.setWidth(new Extent(60));
			    btnVer.setHeight(new Extent(15));
			    btnVer.setStyle(Styles1.DEFAULT_STYLE);
			    btnVer.setToolTipText("Ver");
			    btnVer.setEnabled(true);
			    btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSeeClicked(it);				  
					}				
				});
			    colTab.add(btnVer);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
						indexTab = 4;
						removeAll();
						add(initComprar());						
					}
				});
			    colTab.add(btnBuy);
			    rowTab.add(lblImage);
			    rowTab.add(colTab);
			    rowTab.setBackground(Color.WHITE);
			    grid2.add(rowTab);
			}
		}
		tabPane.add(grid2);
		
		tpld = new TabPaneLayoutData();
	    tpld.setTitle("Pociones");
		
		grid2 = new Grid(2);
		
		grid2.setLayoutData(tpld);
		grid2.setHeight(new Extent(300));
		grid2.setWidth(new Extent(400));
		grid2.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			if(listItemtienda.get(i).getType() == "Pocion"){
				ImageReferenceCache irc = ImageReferenceCache.getInstance();
			    ImageReference ir = irc.getImageReference(imagePath[i]);
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnVer = new Button();
			    btnVer.setText("Ver");
			    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnVer.setWidth(new Extent(60));
			    btnVer.setHeight(new Extent(15));
			    btnVer.setStyle(Styles1.DEFAULT_STYLE);
			    btnVer.setToolTipText("Ver");
			    btnVer.setEnabled(true);
			    btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSeeClicked(it);				  
					}				
				});
			    colTab.add(btnVer);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold() && it.getLevel() <= player.getLevel()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
						indexTab = 5;
						removeAll();
						add(initComprar());						
					}
				});
			    colTab.add(btnBuy);
			    rowTab.add(lblImage);
			    rowTab.add(colTab);
			    rowTab.setBackground(Color.WHITE);
			    grid2.add(rowTab);
			}
		}
		tabPane.add(grid2);
		
		tabPane.setTabCloseEnabled(false);	
		panel.add(tabPane);
		panel.setHeight(new Extent(500));
		panel.setWidth(new Extent(850));		
		
		panel.setLayoutData(hld);
		htmlLayout.add(panel);
		
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
		Row title = new Row();
		description = new Label("Tienda de Articulos Mágicos");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(40)));
		description.setForeground(Color.WHITE);
		title.add(description);
		title.setLayoutData(hld);
		htmlLayout.add(title);
		
		hld = new HtmlLayoutData("left");
		Column col = new Column();
		Row foot = new Row();
		
		menu(hld, col, foot);		
		htmlLayout.add(col);
		htmlLayout.add(foot);
		
		hld = new HtmlLayoutData("central");
		
		Row head = new Row();
		description = new Label("VENDER ");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(25)));
		description.setForeground(Color.WHITE);
		head.add(description);
		head.setCellSpacing(new Extent(285));
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setCellSpacing(new Extent(315));
		head.add(exit());
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		Panel panel = new Panel();
		TabPane tabPane = new TabPane();
		
		tabPane.setTabActiveBackground(new Color(228, 228, 228));
	    tabPane.setTabActiveForeground(Color.BLACK);

	    tabPane.setTabInactiveBackground(Color.LIGHTGRAY);
	    tabPane.setTabInactiveForeground(Color.BLACK);

	    tabPane.setBackground(new Color(228, 228, 228));
	    tabPane.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    
	    TabPaneLayoutData tpld = new TabPaneLayoutData();
	    tpld.setTitle("Todo");
		
		Grid grid = new Grid(4);
		
		grid.setLayoutData(tpld);
		grid.setHeight(new Extent(300));
		grid.setWidth(new Extent(800));
		grid.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		Row rowTab = new Row();
		Column colTab = new Column();
		ResourceImageReference ir;
		Label lblImage;
	    Label lblData;

		for (int i = 0; i < player.getItems().size(); i++){
			rowTab = new Row();
			colTab = new Column();
			ir = new ResourceImageReference(player.getItems().get(i).getImage());
			lblImage = new Label(ir);
			lblData = new Label(player.getItems().get(i).getName());
			colTab.add(lblData);
			lblData = new Label("Tipo "+ player.getItems().get(i).getType());
		    colTab.add(lblData);
		    lblData = new Label("Nivel "+ player.getItems().get(i).getLevel());
		    colTab.add(lblData);
		    lblData = new Label("Precio "+ player.getItems().get(i).getPrice());
		    colTab.add(lblData);
		    final Item it = player.getItems().get(i);
		    Button btnVer = new Button();
		    btnVer.setText("Ver");
		    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnVer.setWidth(new Extent(60));
		    btnVer.setHeight(new Extent(15));
		    btnVer.setStyle(Styles1.DEFAULT_STYLE);
		    btnVer.setToolTipText("Ver");
		    btnVer.setEnabled(true);
		    btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnSeeClicked(it);				  
				}				
			});
		    colTab.add(btnVer);
		    Button btnSell = new Button();
		    btnSell.setText("Vender");
		    btnSell.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnSell.setWidth(new Extent(60));
		    btnSell.setHeight(new Extent(15));
		    btnSell.setStyle(Styles1.DEFAULT_STYLE);
		    btnSell.setToolTipText("Vender");
		    btnSell.setEnabled(true);
		    btnSell.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					player.setGold(player.getGold() + it.getPrice()/2);					
					player.removeItem(player.searchItem(it.getName()));
					removeAll();
					add(initVender());					
				}
			});
		    colTab.add(btnSell);
		    rowTab.add(lblImage);
		    rowTab.add(colTab);
		    rowTab.setBackground(Color.WHITE);
		    grid.add(rowTab);
		}
		tabPane.add(grid);
		
		tabPane.setTabCloseEnabled(false);	
		panel.add(tabPane);
		panel.setHeight(new Extent(400));
		panel.setWidth(new Extent(850));		
		
		panel.setLayoutData(hld);
		htmlLayout.add(panel);
		
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
		Row title = new Row();
		description = new Label("Tienda de Articulos Mágicos");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(40)));
		description.setForeground(Color.WHITE);
		title.add(description);
		title.setLayoutData(hld);
		htmlLayout.add(title);
		
		hld = new HtmlLayoutData("left");
		Column col = new Column();
		Row foot = new Row();
		
		menu(hld, col, foot);		
		htmlLayout.add(col);
		htmlLayout.add(foot);
		
		hld = new HtmlLayoutData("central");
		
		Row head = new Row();
		description = new Label("REFINAR ");
		description.setFont(new Font(Font.HELVETICA, OVERFLOW_AUTO, new Extent(25)));
		description.setForeground(Color.WHITE);
		head.add(description);
		head.setCellSpacing(new Extent(285));
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setCellSpacing(new Extent(305));
		head.add(exit());
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		Panel panel = new Panel();
		TabPane tabPane = new TabPane();
		
		tabPane.setTabActiveBackground(new Color(228, 228, 228));
	    tabPane.setTabActiveForeground(Color.BLACK);

	    tabPane.setTabInactiveBackground(Color.LIGHTGRAY);
	    tabPane.setTabInactiveForeground(Color.BLACK);

	    tabPane.setBackground(new Color(228, 228, 228));
	    tabPane.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    
	    TabPaneLayoutData tpld = new TabPaneLayoutData();
	    tpld.setTitle("Refina tus Objetos");
		
		Grid grid = new Grid(3);
		
		grid.setLayoutData(tpld);
		grid.setHeight(new Extent(300));
		grid.setWidth(new Extent(500));
		grid.setBorder(new Border(new Extent(10), new Color(228, 228, 228), Border.STYLE_DOUBLE));
		Row rowTab = new Row();
		Column colTab = new Column();
		ResourceImageReference ir;
		Label lblImage;
	    Label lblData;
	    final String nameItem1;
	    final String nameItem2;
	    final String nameItem3;
	    final String nameItem4;
	    final String nameItem5;

		Item item = new Item(3, "Armadura Negra", 4600, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
		nameItem1 = item.getName();
		
		ir = new ResourceImageReference(item.getImage());
		lblImage = new Label(ir);
		lblData = new Label(item.getName());
		colTab.add(lblData);
		lblData = new Label("Tipo "+ item.getType());
	    colTab.add(lblData);
	    lblData = new Label("Nivel "+ item.getLevel());
	    colTab.add(lblData);
	    lblData = new Label("Indice "+ item.getIndex());
	    colTab.add(lblData);
	    
	    btnBuild1 = new Button();
		btnBuild1.setText("Fabricar");
		btnBuild1.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild1.setWidth(new Extent(60));
		btnBuild1.setHeight(new Extent(15));
		btnBuild1.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild1.setToolTipText("Armadura + Piedra Negra");
		if(item.getLevel() <= player.getLevel()){
			if(player.contains("Armadura") && player.contains("Piedra Negra"))
				btnBuild1.setEnabled(true);
			else
				btnBuild1.setEnabled(false);
		}else
			btnBuild1.setEnabled(false);
		btnBuild1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem1);
				it = player.searchItem("Armadura");
				player.removeItem(it);
				it = player.searchItem("Piedra Negra");
				player.removeItem(it);				
				it = player.searchItem(nameItem1);
				testButton(it);
			}
		});
		colTab.add(btnBuild1);
	    rowTab.add(lblImage);
	    rowTab.add(colTab);
	    rowTab.setBackground(Color.WHITE);
	    grid.add(rowTab);
	    
	    item = new Item(5, "Espada Roja", 4600, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
		nameItem2 = item.getName();
	    
		rowTab = new Row();
		colTab = new Column();
		
		ir = new ResourceImageReference(item.getImage());
		lblImage = new Label(ir);
		lblData = new Label(item.getName());
		colTab.add(lblData);
		lblData = new Label("Tipo "+ item.getType());
	    colTab.add(lblData);
	    lblData = new Label("Nivel "+ item.getLevel());
	    colTab.add(lblData);
	    lblData = new Label("Indice "+ item.getIndex());
	    colTab.add(lblData);
	    
	    btnBuild2 = new Button();
		btnBuild2.setText("Fabricar");
		btnBuild2.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild2.setWidth(new Extent(60));
		btnBuild2.setHeight(new Extent(15));
		btnBuild2.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild2.setToolTipText("Espada + Piedra Roja");
		if(item.getLevel() <= player.getLevel()){
			if(player.contains("Espada") && player.contains("Piedra Roja"))
				btnBuild2.setEnabled(true);
			else
				btnBuild2.setEnabled(false);
		}else
			btnBuild2.setEnabled(false);
		btnBuild2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem2);
				it = player.searchItem("Espada");
				player.removeItem(it);
				it = player.searchItem("Piedra Roja");
				player.removeItem(it);				
				it = player.searchItem(nameItem2);
				testButton(it);
			}
		});
		colTab.add(btnBuild2);
	    rowTab.add(lblImage);
	    rowTab.add(colTab);
	    rowTab.setBackground(Color.WHITE);
	    grid.add(rowTab);
	    
	    item = new Item(6, "Bomba Blanca", 3500, 30, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb.png");
		nameItem3 = item.getName();
	    
		rowTab = new Row();
		colTab = new Column();
		
		ir = new ResourceImageReference(item.getImage());
		lblImage = new Label(ir);
		lblData = new Label(item.getName());
		colTab.add(lblData);
		lblData = new Label("Tipo "+ item.getType());
	    colTab.add(lblData);
	    lblData = new Label("Nivel "+ item.getLevel());
	    colTab.add(lblData);
	    lblData = new Label("Indice "+ item.getIndex());
	    colTab.add(lblData);
	    
	    btnBuild3 = new Button();
		btnBuild3.setText("Fabricar");
		btnBuild3.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild3.setWidth(new Extent(60));
		btnBuild3.setHeight(new Extent(15));
		btnBuild3.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild3.setToolTipText("Piedra Blanca x 3");		
		if(item.getLevel() <= player.getLevel()){
			if(player.countItem("Piedra Blanca") >= 3)
				btnBuild3.setEnabled(true);
			else
				btnBuild3.setEnabled(false);
		}else
			btnBuild3.setEnabled(false);
		btnBuild3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem3);
				it = player.searchItem("Piedra Blanca");
				player.removeItem(it);
				it = player.searchItem("Piedra Blanca");
				player.removeItem(it);
				it = player.searchItem("Piedra Blanca");
				player.removeItem(it);				
				it = player.searchItem(nameItem3);
				testButton(it);
			}
		});
		colTab.add(btnBuild3);
	    rowTab.add(lblImage);
	    rowTab.add(colTab);
	    rowTab.setBackground(Color.WHITE);
	    grid.add(rowTab);
	    
	    item = new Item(6, "Bomba Negra", 6500, 60, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png");
		nameItem4 = item.getName();
	    
		rowTab = new Row();
		colTab = new Column();
		
		ir = new ResourceImageReference(item.getImage());
		lblImage = new Label(ir);
		lblData = new Label(item.getName());
		colTab.add(lblData);
		lblData = new Label("Tipo "+ item.getType());
	    colTab.add(lblData);
	    lblData = new Label("Nivel "+ item.getLevel());
	    colTab.add(lblData);
	    lblData = new Label("Indice "+ item.getIndex());
	    colTab.add(lblData);
	    
	    btnBuild4 = new Button();
		btnBuild4.setText("Fabricar");
		btnBuild4.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild4.setWidth(new Extent(60));
		btnBuild4.setHeight(new Extent(15));
		btnBuild4.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild4.setToolTipText("Piedra Blanca + Piedra Negra + Piedra Roja");
		if(item.getLevel() <= player.getLevel()){
			if(player.contains("Piedra Blanca") && player.contains("Piedra Roja") && player.contains("Piedra Negra"))
				btnBuild4.setEnabled(true);
			else
				btnBuild4.setEnabled(false);
		}else
			btnBuild4.setEnabled(false);
		btnBuild4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem4);
				it = player.searchItem("Piedra Blanca");
				player.removeItem(it);
				it = player.searchItem("Piedra Roja");
				player.removeItem(it);
				it = player.searchItem("Piedra Negra");
				player.removeItem(it);
				it = player.searchItem(nameItem4);
				testButton(it);				
			}
		});
		colTab.add(btnBuild4);
	    rowTab.add(lblImage);
	    rowTab.add(colTab);
	    rowTab.setBackground(Color.WHITE);
	    grid.add(rowTab);
	    
	    item = new Item(7, "Bomba Roja", 9500, 90, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png");
		nameItem5 = item.getName();
	    
		rowTab = new Row();
		colTab = new Column();
		
		ir = new ResourceImageReference(item.getImage());
		lblImage = new Label(ir);
		lblData = new Label(item.getName());
		colTab.add(lblData);
		lblData = new Label("Tipo "+ item.getType());
	    colTab.add(lblData);
	    lblData = new Label("Nivel "+ item.getLevel());
	    colTab.add(lblData);
	    lblData = new Label("Indice "+ item.getIndex());
	    colTab.add(lblData);
	    
	    btnBuild5 = new Button();
		btnBuild5.setText("Fabricar");
		btnBuild5.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnBuild5.setWidth(new Extent(60));
		btnBuild5.setHeight(new Extent(15));
		btnBuild5.setStyle(Styles1.DEFAULT_STYLE);
		btnBuild5.setToolTipText("Piedra Roja x 3");
		if( item.getLevel() <= player.getLevel()){
			if(player.countItem("Piedra Roja") >= 3)
				btnBuild5.setEnabled(true);
			else
				btnBuild5.setEnabled(false);
		}else
			btnBuild5.setEnabled(false);
		btnBuild5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item it = new Item();
				player.buildItem(nameItem5);
				it = player.searchItem("Piedra Roja");
				player.removeItem(it);
				it = player.searchItem("Piedra Roja");
				player.removeItem(it);
				it = player.searchItem("Piedra Roja");
				player.removeItem(it);				
				it = player.searchItem(nameItem5);
				testButton(it);
			}
		});
		
		
		colTab.add(btnBuild5);
	    rowTab.add(lblImage);
	    rowTab.add(colTab);
	    rowTab.setBackground(Color.WHITE);
	    grid.add(rowTab);
	    
		tabPane.add(grid);
		
		tabPane.setTabCloseEnabled(false);	
		panel.add(tabPane);
		panel.setHeight(new Extent(400));
		panel.setWidth(new Extent(850));		
		
		panel.setLayoutData(hld);
		htmlLayout.add(panel);
		
		return htmlLayout;
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
	
private Button exit() {
	    Button btnExit = new Button("Tienda");
	    btnExit.setStyle(Styles1.DEFAULT_STYLE);
	    btnExit.setToolTipText("Regresar a Principal Tienda");
	    btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              removeAll();
              add(initMapa());
            }
        });        
	    return btnExit;
	}
	
	private void AsignarDatos (int level, String nombre, int precio, int indice, String tipo, String descripcion, boolean uso, String dirImage)
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
		
		listItemtienda.add(item);
	}
	
	private void CreateList()
	{
		AsignarDatos(1, "Armadura", 1500, 20, "Armadura", "Armadura Básica",false,"Images/Items/armor.png");
		AsignarDatos(1, "Espada", 1500, 20, "Espada", "Espada Básica",false,"Images/Items/sword.png");
		
		AsignarDatos(2, "Piedra Blanca", 1000, 10, "Piedra", "Piedra mágica de color Blanco",false,"Images/Items/stone1.png");
		AsignarDatos(2, "Medicina20", 800, 20, "Pocion", "Medicina  básica para aumentar la vida",false,"Images/Items/potion1.png");
		
		AsignarDatos(3, "Piedra Negra", 2500, 25, "Piedra", "Piedra mágica de color Negro",false,"Images/Items/stone2.png");
		AsignarDatos(3, "Armadura Negra", 5000, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
		
		AsignarDatos(4, "Energia", 1500, 20, "Pocion", "Pocion para aumentar la psinergia",false,"Images/Items/potion2.png");
		AsignarDatos(4, "Medicina40", 1800, 40, "Pocion", "Medicina de media categoría para aumentar la vida",false,"Images/Items/potion1.png");
		
		AsignarDatos(5, "Piedra Roja", 3000, 35, "Piedra", "Piedra mágica de color Rojo",false,"Images/Items/stone3.png");
		AsignarDatos(5, "Espada Roja", 5000, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
		
		AsignarDatos(6, "Bomba Blanca", 3500, 30, "Bomba", "Bomba mágica creada con piedras de color blanco",false,"Images/Items/bomb.png");
		AsignarDatos(6, "Bomba Negra", 6500, 60, "Bomba", "Bomba mágica creada con piedras de color negro",false,"Images/Items/bomb2.png");
		
		AsignarDatos(7, "Bomba Roja", 9500, 90, "Bomba", "Bomba mágica creada con piedras de color rojo",false,"Images/Items/bomb3.png");
		AsignarDatos(7, "Energia Super", 2500, 40, "Pocion", "Pocion para aumentar mucho más la psinergia",false,"Images/Items/potion2.png");
		
		AsignarDatos(9, "Medicina Super", 3000, 80, "Pocion", "Poderosa Medicina para aumentar la vida",false,"Images/Items/potion1.png");
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
	
	private void menu(HtmlLayoutData hld , Column col, Row foot){
		hld = new HtmlLayoutData("left");
		Column menu = new Column();
		Button btnComprar = new Button();
		btnComprar.setText("Comprar");
		btnComprar.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnComprar.setWidth(new Extent(55));
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
		btnVender.setWidth(new Extent(55));
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
		btnRefinar.setWidth(new Extent(55));
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
		
		hld = new HtmlLayoutData("foot");
		Button returnButton = new Button();
		returnButton.setText("REGRESAR");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setWidth(new Extent(80));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al Mapa Principal");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button1Clicked(e);				
			}
		});
		foot.add(returnButton);
		foot.setLayoutData(hld);		
	}
	
	private void button1Clicked(ActionEvent e) {		
		removeAll();
		add(new MapaDesktop());
	}	
}
