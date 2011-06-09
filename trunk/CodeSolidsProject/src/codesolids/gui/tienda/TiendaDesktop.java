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
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import codesolids.gui.tienda.TestTableModel;
import codesolids.gui.tienda.Item;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Fernando Osuna
 * 
 */

public class TiendaDesktop extends ContentPane {
	private HtmlLayout htmlLayout;
	
	private Label name;
	private Label index;
	private Label description;
	
	WindowPane ventana;

	private List<Item> listItemtienda;
	private Personaje player = new Personaje();
	
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
		Column head = new Column();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setLayoutData(hld);
		htmlLayout.add(head);

		hld = new HtmlLayoutData("left");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		hld = new HtmlLayoutData("central");
		
		
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

//		ResourceImageReference fondo = new ResourceImageReference("Images/Items/tienda.jpg");
//		htmlLayout.setBackgroundImage(fondo);
		
		HtmlLayoutData hld;

		hld = new HtmlLayoutData("head");
		Column head = new Column();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		hld = new HtmlLayoutData("left");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		hld = new HtmlLayoutData("central");
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

	    listItemtienda = new ArrayList<Item>();
	    CreateList();
		for (int i = 0; i < listItemtienda.size(); i++){
			rowTab = new Row();
			colTab = new Column();
			ir = new ResourceImageReference(listItemtienda.get(i).getImage());
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
		    if (it.getPrice() <= player.getGold()){
		    	btnBuy.setEnabled(true);
		    }
		    else{
		    	btnBuy.setEnabled(false);
		    }
		    btnBuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					player.setGold(player.getGold() - it.getPrice());
					player.setItems(it);
					removeAll();
					add(initComprar());
//					remove(head);
//					head.removeAll();
//					HtmlLayoutData hld = new HtmlLayoutData("head");
//					description = new Label("GOLD: " + player.getGold());
//					description.setBackground(Color.BLACK);
//					description.setForeground(Color.YELLOW);
//					head.add(description);
//					head.setLayoutData(hld);
//					htmlLayout.add(head);
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
				ir = new ResourceImageReference(listItemtienda.get(i).getImage());
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    lblData = new Label("Descripción ");
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
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
				ir = new ResourceImageReference(listItemtienda.get(i).getImage());
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    lblData = new Label("Descripción ");
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
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
				ir = new ResourceImageReference(listItemtienda.get(i).getImage());
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    lblData = new Label("Descripción ");
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
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
				ir = new ResourceImageReference(listItemtienda.get(i).getImage());
				lblImage = new Label(ir);
				lblData = new Label(listItemtienda.get(i).getName());
				colTab.add(lblData);
				lblData = new Label("Tipo "+ listItemtienda.get(i).getType());
			    colTab.add(lblData);
			    lblData = new Label("Nivel "+ listItemtienda.get(i).getLevel());
			    colTab.add(lblData);
			    lblData = new Label("Precio "+ listItemtienda.get(i).getPrice());
			    colTab.add(lblData);
			    lblData = new Label("Descripción ");
			    colTab.add(lblData);
			    final Item it = listItemtienda.get(i);
			    Button btnBuy = new Button();
			    btnBuy.setText("Comprar");
			    btnBuy.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			    btnBuy.setWidth(new Extent(60));
			    btnBuy.setHeight(new Extent(15));
			    btnBuy.setStyle(Styles1.DEFAULT_STYLE);
			    btnBuy.setToolTipText("Comprar");
			    if (it.getPrice() <= player.getGold()){
			    	btnBuy.setEnabled(true);
			    }
			    else{
			    	btnBuy.setEnabled(false);
			    }
			    btnBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						player.setGold(player.getGold() - it.getPrice());
						player.setItems(it);
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
		panel.setHeight(new Extent(400));
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

//		ResourceImageReference fondo = new ResourceImageReference("Images/Items/tienda.jpg");
//		htmlLayout.setBackgroundImage(fondo);
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");
		Column head = new Column();
		description = new Label("GOLD: " + player.getGold());
		description.setBackground(Color.BLACK);
		description.setForeground(Color.YELLOW);
		head.add(description);
		head.setLayoutData(hld);
		htmlLayout.add(head);
		
		hld = new HtmlLayoutData("left");
		Column col = new Column();
		
		menu(hld, col);		
		htmlLayout.add(col);
		
		hld = new HtmlLayoutData("central");
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
		    Button btnVer = new Button();
		    btnVer.setText("Ver");
		    btnVer.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		    btnVer.setWidth(new Extent(60));
		    btnVer.setHeight(new Extent(15));
		    btnVer.setStyle(Styles1.DEFAULT_STYLE);
		    btnVer.setToolTipText("Ver");
		    btnVer.setEnabled(true);
		    final Item it = player.getItems().get(i);
		    btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {					
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
		
		hld = new HtmlLayoutData("left");
		Column colmenu = new Column();
		
		menu(hld, colmenu);		
		htmlLayout.add(colmenu);
		hld = new HtmlLayoutData("central");
		
		Grid colW = new Grid(1);		
		colW.add(initTopRowVender());
		
		Column col = new Column();
		Row row = new Row();
		description = new Label("REFINA TUS ITEMS ");
		description.setBackground(new Color(228, 228, 228));
		col.add(description);
		description = new Label("");
		description.setBackground(new Color(148, 111, 63));
		col.add(description);
		description = new Label("Item ");
		row.add(description);
		row.setCellSpacing(new Extent(200));
		description = new Label("Acción");
		row.add(description);
		row.setCellSpacing(new Extent(160));
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
		
//		ResourceImageReference ir = new ResourceImageReference("Images/Items/tienda.jpg");
//		htmlLayout.setBackgroundImage(ir);
		
		return htmlLayout;
	}
	
private Row initTopRowVender() {
		
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

		col.add(description);

		return col;
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
		AsignarDatos(1, "Armor", 1500, 30, "Armadura", "Armadura Básica",false,"Images/Items/armor.png");
		AsignarDatos(1, "Sword", 1500, 30, "Espada", "Espada Básica",false,"Images/Items/sword.png");
		
		AsignarDatos(2, "Stone White", 1000, 10, "Piedra", "Piedra Blanca",false,"Images/Items/stone1.png");
		AsignarDatos(2, "Medicine", 800, 40, "Pocion", "Medicina para aumentar la vida",false,"Images/Items/potion1.png");
		
		AsignarDatos(3, "Stone Black", 2500, 25, "Piedra", "Piedra Negra",false,"Images/Items/stone2.png");
		AsignarDatos(3, "Armor Black", 4600, 30, "Armadura", "Armadura Avanzada",false,"Images/Items/armor2.png");
		
		AsignarDatos(4, "Energy", 1500, 10, "Pocion", "Pocion para aumentar la psinergia",false,"Images/Items/potion2.png");
		
		AsignarDatos(5, "Stone Red", 3000, 35, "Piedra", "Piedra Roja",false,"Images/Items/stone3.png");
		AsignarDatos(5, "Sword Red", 4600, 30, "Espada", "Espada Avanzada",false,"Images/Items/sword2.png");
		AsignarDatos(5, "White Bomb", 3100, 35, "Bomba", "Bomba mágica de color blanco",false,"Images/Items/bomb.png");
		AsignarDatos(5, "Black Bomb", 6100, 60, "Bomba", "Bomba mágica de color negro",false,"Images/Items/bomb.png");
		AsignarDatos(5, "Red Bomb", 9100, 90, "Bomba", "Bomba mágica de color rojo",false,"Images/Items/bomb.png");
		
	}
	
	private void btnVerClicked(Item item) 
	{
		WindowPane window = new WindowPane();
		window.setTitle("Tienda - Compra");
		window.setWidth(new Extent(300));
		window.setMaximumWidth(new Extent(300));
		window.setMaximumHeight(new Extent(100));
		window.setMovable(false);
		window.setResizable(false);
		window.add(new Label("Nombre : " + item.getName()));
		window.add(new Label("Indice : " + item.getIndex()));
		window.add(new Label("Descripción : "+ item.getDescription()));
		window.setModal(true);
		htmlLayout.add(window);
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
