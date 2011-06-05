package codesolids.gui.academia;


import java.util.ArrayList;
import java.util.List;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.ImageCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;
import com.minotauro.echo.table.renderer.BaseCellRenderer;


import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;

import codesolids.gui.academia.TestTableModel;
import codesolids.gui.academia.Poder;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

/**
 * @author: Antonio López
 * 
 */

public class Desktop extends ContentPane {

	private HtmlLayout htmlLayout;
	
	private TestTableModel tableDtaModel;
	
	private ETable table;
	
	private Label name;
	private Label damage;
	private Label cooldown;
	private Label psinergia;
	private Label description;
	
	WindowPane ventana;

	private List<Poder> listPoder;
	
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
	
		HtmlLayoutData hld = new HtmlLayoutData("central");
		
		Column col = new Column();
		
		Row row = new Row();
		Button btnAcademia = new Button();
		btnAcademia.setText("Academia");
		btnAcademia.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnAcademia.setWidth(new Extent(100));
		btnAcademia.setHeight(new Extent(15));
		btnAcademia.setStyle(Styles1.DEFAULT_STYLE);
		btnAcademia.setToolTipText("Ir a la Academia");
		
		btnAcademia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(initAcademia());
			}
		});
		
		row.add(btnAcademia);
		col.add(row);
		
		col.setLayoutData(hld);
		
		htmlLayout.add(col);
		
		
		return htmlLayout;
	}
	
	private Component initAcademia()
	{
		
		try{
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("central");
		
		setInsets(new Insets(5, 5, 5, 5));
		
//		Grid col = new Grid(1);
//		col.add(ventana);
		
//		add(col);
		
		Grid colW = new Grid(1);
		
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

	    
	    table.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));
	    table.setInsets(new Insets(5, 2, 5, 2));
	    
	    colW.add(table);
	    
	    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    colW.add(tableNavigation);
	    
	    colW.add(initFoot());
	    
	    CreateList();
		rowsArrayPoderes();
		
//		ventana = new WindowPane();
//		ventana.setTitle("Academia");
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
		
		htmlLayout.add(colW);
		
		return htmlLayout;
	}
	
	private Row initTopRow() {
		
		setInsets(new Insets(5, 5, 5, 5));
		
		Row row = new Row();
	    row.setCellSpacing(new Extent(150));
		
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
	
	private Column initFoot() {
		setInsets(new Insets(5, 5, 5, 5));
		Column col = new Column(); 
		
		description = new Label("Bienvenido a la Academia! Aqui podras entrenar tus poderes.");
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
		
		damage = new Label();
		row.add(damage);
		cooldown = new Label();
		row.add(cooldown);
		psinergia = new Label();
		row.add(psinergia);
		col.add(row);

		col.add(description);

		return col;
	}
	
	private TableColModel initTableColModel() {		
		
		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poder poder = (Poder) element;
	    		return poder.getLevel();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.LIGHTGRAY);
	    lcr.setForeground(Color.RED);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.BLACK);
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Level");
	    
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poder poder = (Poder) element;
	    		return poder.getImage();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.LIGHTGRAY);
	    lcr.setForeground(Color.RED);
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    ImageCellRenderer icr = new ImageCellRenderer();
	    icr.setBackground(Color.BLACK);
	    icr.setWidth(new Extent(15));
	    icr.setHeight(new Extent(15));
	    
	    tableColumn.setDataCellRenderer(icr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(15));
	    tableColumn.setHeadValue("");    
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poder poder = (Poder) element;
	    		return poder.getName();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(150));
	    tableColumn.setHeadValue("Nombre");
	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.LIGHTGRAY);
	    lcr.setForeground(Color.RED);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.BLACK);
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poder poder = (Poder) element;
	    		return poder.getGold();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.LIGHTGRAY);
	    lcr.setForeground(Color.RED);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.BLACK);
	    lcr.setForeground(Color.YELLOW);
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Oro");
	    
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(Color.LIGHTGRAY);
	    lcr.setForeground(Color.RED);
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

	          Poder poder = (Poder) tableDtaModel.getElementAt(row);
	          
	          Personaje player = new Personaje();
	          player.setType("Fuego");
	          
	          final Button ret = new Button("Comprar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Comprar");


	          if (poder.getUse() == false)
	          {
	        	  if(poder.getType() == player.getType())
	        	  {	  
	        	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  btnBuyClicked(row);
	        			  }
	        		  
	        			  private void btnBuyClicked(int row) {
	        				
	        				  Poder poder = (Poder) tableDtaModel.getElementAt(row);
	        			  
	        				  WindowPane ventanaCompra = new WindowPane();
	        				  ventanaCompra.setTitle("Academia - Compra");
	        				  ventanaCompra.setWidth(new Extent(300));
	        				  ventanaCompra.setMaximumWidth(new Extent(300));
	        				  ventanaCompra.setMaximumHeight(new Extent(100));
	        				  ventanaCompra.setMovable(false);
	        				  ventanaCompra.setResizable(false);
	        				  ventanaCompra.add(new Label("Usted compro " + poder.getName()));
	        				  ventanaCompra.setModal(true);
	        				  add(ventanaCompra);

	        				  poder.setUse(true);	        				  
	        				  ret.setEnabled(false);

	        			  }
	        		  });
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
	
	private void AsignarDatos (int level,String nombre,int oro,int dano,int tiemporeutilizacion,int psinergia,String tipo,boolean uso,String dirImage)
	{
		Poder poder = new Poder();
		
		poder.setLevel(level);
		poder.setName(nombre);
		poder.setGold(oro);
		poder.setDamage(dano);
		poder.setCooldown(tiemporeutilizacion);
		poder.setPsinergia(psinergia);
		poder.setType(tipo);
		poder.setUse(uso);
		poder.setImage(dirImage);
		
		listPoder = new ArrayList<Poder>();
		listPoder.add(poder);
		
		tableDtaModel.add(poder);
	}
	
	private void CreateList()
	{
		AsignarDatos(1, "Fire Ball", 300, 30, 2, 45, "Fuego",false,"Images/Poderes/Fuego/FireBall.png");
		AsignarDatos(1, "Water Burst", 300, 30, 2, 45, "Hielo",false,"Images/Poderes/Hielo/WaterBurst.png");
		AsignarDatos(1, "Earth Smash", 300, 30, 2, 45, "Tierra",false,"Images/Poderes/Tierra/EarthSmash.png");
		AsignarDatos(1, "Fist Dragon", 300, 30, 2, 45, "Combo",false,"Images/Poderes/Combo/DragonFist.png");
		
		AsignarDatos(2, "Fire Explosion", 600, 50, 3, 65, "Fuego",false,"Images/Poderes/Fuego/FireExplosion.png");
		AsignarDatos(2, "Water Ball", 600, 50, 3, 60, "Hielo",false,"Images/Poderes/Hielo/WaterBall.png");
		AsignarDatos(2, "Earth Fist", 600, 50, 3, 60, "Tierra",false,"Images/Poderes/Tierra/EarthFist.png");
		AsignarDatos(2, "Hit Mortal", 600, 50, 3, 60, "Combo",false,"Images/Poderes/Combo/HeadKick.png");
		
		AsignarDatos(3, "Fire Spike Wheel", 900, 70, 5, 85, "Fuego",false,"Images/Poderes/Fuego/FireSpikeWheel.png");
		AsignarDatos(3, "Ice Prison", 900, 70, 5, 85, "Hielo",false,"Images/Poderes/Hielo/WaterPrision.png");
		AsignarDatos(3, "Earth Strangle", 900, 70, 5, 85, "Tierra",false,"Images/Poderes/Tierra/EarthStrangle.png");
		AsignarDatos(3, "Cannonball", 900, 70, 5, 85, "Combo",false,"Images/Poderes/Combo/CannonballStrike.png");
		
		AsignarDatos(4, "Fiery Flame", 1200, 90, 7, 105, "Fuego",false,"Images/Poderes/Fuego/FieryFlame.png");
		AsignarDatos(4, "Water Dragon Vortex", 1200, 90, 7, 105, "Hielo",false,"Images/Poderes/Hielo/WaterDragonVortex.png");
		AsignarDatos(4, "Earth Wall", 1200, 90, 7, 105, "Tierra",false,"Images/Poderes/Tierra/EarthWall.png");
		AsignarDatos(4, "Swift Kick", 1200, 90, 7, 105, "Combo",false,"Images/Poderes/Combo/SwiftKick.png");
		
		AsignarDatos(5, "Fire Vortex", 1500, 110, 8, 125, "Fuego",false,"Images/Poderes/Fuego/FireVortex.png");
		AsignarDatos(5, "Water Missile", 1500, 110, 8, 125, "Hielo",false,"Images/Poderes/Hielo/WaterMissile.png");
		AsignarDatos(5, "Mud Golem", 1500, 110, 8, 125, "Tierra",false,"Images/Poderes/Tierra/MudGolem.png");
		AsignarDatos(5, "Three Rapid Hit", 1500, 110, 8, 125, "Combo",false,"Images/Poderes/Combo/ThreeCombatRapid.png");
		
		AsignarDatos(6, "Fiery Missile", 1800, 130, 10, 145, "Fuego",false,"Images/Poderes/Fuego/FireMissile.png");
		AsignarDatos(6, "Ice Cannon", 1800, 130, 10, 145, "Hielo",false,"Images/Poderes/Hielo/WaterJetCannon.png");
		AsignarDatos(6, "Earth Spike", 1800, 130, 10, 145, "Tierra",false,"Images/Poderes/Tierra/EarthSpike.png");
		AsignarDatos(6, "Rapid Uppercut", 1800, 130, 10, 145, "Combo",false,"Images/Poderes/Combo/RapidUppercut.png");
		
		AsignarDatos(7, "Fiery Arrow", 2100, 150, 10, 165, "Fuego",false,"Images/Poderes/Fuego/FireArrow.png");
		AsignarDatos(7, "Water Impale", 2100, 150, 10, 165, "Hielo",false,"Images/Poderes/Hielo/WaterImpale.png");
		AsignarDatos(7, "Earth Erosion", 2100, 150, 10, 165, "Tierra",false,"Images/Poderes/Tierra/EarthErosion.png");
		AsignarDatos(7, "Palm Four Hit", 2100, 150, 10, 165, "Combo",false,"Images/Poderes/Combo/FourPalm.png");
		
		AsignarDatos(8, "Hellfire", 2400, 170, 10, 185, "Fuego",false,"Images/Poderes/Fuego/Hellfire.png");
		AsignarDatos(8, "Ice Renewal", 2400, 170, 10, 185, "Hielo",false,"Images/Poderes/Hielo/WaterConvergence.png");
		AsignarDatos(8, "Earth Blast", 2400, 170, 10, 185, "Tierra",false,"Images/Poderes/Tierra/EarthBlast.png");
		AsignarDatos(8, "Kick Assault", 2400, 170, 10, 185, "Combo",false,"Images/Poderes/Combo/AssaultKick.png");
		
		AsignarDatos(9, "Pillar of Flame", 2700, 190, 10, 205, "Fuego",false,"Images/Poderes/Fuego/PillarofFlame.png");
		AsignarDatos(9, "Double Water Burst", 2700, 190, 10, 205, "Hielo",false,"Images/Poderes/Hielo/DoubleWaterBurst.png");
		AsignarDatos(9, "Explosion Boulder", 2700, 190, 10, 205, "Tierra",false,"Images/Poderes/Tierra/EarthBoulder.png");
		AsignarDatos(9, "Sky Hit", 2700, 190, 10, 205, "Combo",false,"Images/Poderes/Combo/FlashSmash.png");
		
		AsignarDatos(10, "Double Fireball", 3000, 210, 10, 225, "Fuego",false,"Images/Poderes/Fuego/DoubleFireball.png");
		AsignarDatos(10, "Ice Spear", 3000, 210, 10, 225, "Hielo",false,"Images/Poderes/Hielo/WaterSpear.png");
		AsignarDatos(10, "Double Earth Smash", 3000, 210, 10, 225, "Tierra",false,"Images/Poderes/Tierra/DoubleEarthSmash.png");
		AsignarDatos(10, "Blow Savage", 3000, 210, 10, 225, "Combo",false,"Images/Poderes/Combo/ThousandViolenceStrike.png");
	}
	
	private void btnVerClicked(int row) 
	{
		Poder poder = (Poder) tableDtaModel.getElementAt(row);
		
		if (poder.getType() == "Fuego")
			infoPowerFire(poder);
		else if (poder.getType() == "Hielo")
			infoPowerIce(poder);
		else if (poder.getType() == "Tierra")
			infoPowerEarth(poder);
		else
			infoPowerCombo(poder);
	}
	
	
	private void btnAllClicked() {
		
		name.setText("");
		damage.setText("");
		cooldown.setText("");
		psinergia.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			tableDtaModel.add(listPoder.get(i));					
		}
		
	}
	
	private void btnFireClicked() {
		
		name.setText("");
		damage.setText("");
		cooldown.setText("");
		psinergia.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Fuego")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}		
	}
	
	private void btnIceClicked() {
		
		name.setText("");
		damage.setText("");
		cooldown.setText("");
		psinergia.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Hielo")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}
	}
	
	private void btnEarthClicked() {
		
		name.setText("");
		damage.setText("");
		cooldown.setText("");
		psinergia.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Tierra")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}
	}
	
	private void btnComboClicked()
	{
		name.setText("");
		damage.setText("");
		cooldown.setText("");
		psinergia.setText("");
		description.setText("");
		tableDtaModel.clear();
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Combo")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}
		
	}
	
	private void rowsArrayPoderes(){
		int row = tableDtaModel.getTotalRows();
		
		listPoder = new ArrayList<Poder>();
		Poder poder;
		
		for(int i = 0; i < row; i++)
		{
			poder = (Poder) tableDtaModel.getElementAt(i);
			listPoder.add(poder);
		}
	}
	
	private void infoPowerFire(Poder poder)
	{
		if (poder.getName() == "Fire Ball")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Bola de Fuego!");
		}
		
		if (poder.getName() == "Fire Explosion")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Explisión de Fuego!");
		}
		
		if (poder.getName() == "Fire Spike Wheel")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Rueda de Fuego!");
		}
		
		if (poder.getName() == "Fiery Flame")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Flama de Fuego!");
		}
		
		if (poder.getName() == "Fire Vortex")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Torbellino de Fuego!");
		}
		
		if (poder.getName() == "Fiery Missile")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Misil de Fuego!");
		}
		
		if (poder.getName() == "Fiery Arrow")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Flecha de Fuego!");
		}
		
		if (poder.getName() == "Hellfire")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Fuego del Demonio!");
		}
		
		if (poder.getName() == "Pillar of Flame")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Pilar de Fuego!");
		}
		
		if (poder.getName() == "Double Fireball")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Son Dos Bolas de Fuego!");
		}
		
	}
	
	private void infoPowerIce(Poder poder)
	{
		if (poder.getName() == "Water Burst")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Ráfaga de Agua!");
		}		
		
		if (poder.getName() == "Water Ball")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Bola de Agua!");
		}
		
		if (poder.getName() == "Ice Prison")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Prisión de Hielo!");
		}
		
		if (poder.getName() == "Water Dragon Vortex")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Dragon de Agua!");
		}
		
		if (poder.getName() == "Water Missile")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Misil de Agua!");
		}
		
		if (poder.getName() == "Ice Cannon")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Cañon de Hielo!");
		}
		
		if (poder.getName() == "Ice Prison")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Prisión de Hielo!");
		}
		
		if (poder.getName() == "Water Impale")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Estaca de Agua!");
		}
		
		if (poder.getName() == "Ice Renewal")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Renovación de Hielo!");
		}
		
		if (poder.getName() == "Double Water Burst")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Doble Ráfaga de Agua!");
		}
		
		if (poder.getName() == "Ice Spear")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Lanza de Hielo!");
		}
	}

	private void infoPowerEarth(Poder poder)
	{
		if (poder.getName() == "Earth Smash")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Ataque Tierra!");
		}
		
		if (poder.getName() == "Earth Fist")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Ataque Tierra!");
		}
		
		if (poder.getName() == "Earth Strangle")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Estrangulamiento!");
		}
		
		if (poder.getName() == "Earth Wall")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Pared de Tierra!");
		}
		
		if (poder.getName() == "Mud Golem")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Golem de Barro!");
		}
		
		if (poder.getName() == "Earth Spike")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Pico de Tierra!");
		}
		
		if (poder.getName() == "Earth Erosion")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Erosión de la Tierra!");
		}
		
		if (poder.getName() == "Earth Blast")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Exploción de la Tierra!");
		}
		
		if (poder.getName() == "Explosion Boulder")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Erupción de Roca!");
		}
		
		if (poder.getName() == "Double Earth Smash")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Doble Ataque de Tierra!");
		}
	}
	
	private void infoPowerCombo(Poder poder)
	{
		if (poder.getName() == "Fist Dragon")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Golpe de Dragon!");
		}
		
		if (poder.getName() == "Hit Mortal")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Golpe Mortal!");
		}
		
		if (poder.getName() == "Cannonball")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Ataque Cañon!");
		}
		
		if (poder.getName() == "Swift Kick")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Patada Fuerte!");
		}
		
		if (poder.getName() == "Three Rapid Hit")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Son Tres Golpes Rápido!");
		}
		
		if (poder.getName() == "Rapid Uppercut")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Gancho Rapido!");
		}
		
		if (poder.getName() == "Palm Four Hit")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Son Cuatro Puñetazos!");
		}
		
		if (poder.getName() == "Kick Assault")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es una Embestida!");
		}
		
		if (poder.getName() == "Sky Hit")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Golpe Volador!");
		}
		
		if (poder.getName() == "Blow Savage")
		{
			description.setText("");
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			description.setText("Es un Ataque Salvaje!");
		}
	}
	
}
