package codesolids.gui.academia;


import java.util.ArrayList;
import java.util.List;

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
import nextapp.echo.app.LayoutData;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.gui.academia.TimedServerPush;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.bd.clases.Usuario;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;

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
	
	private Personaje player;

	private List<Poder> listPoder;
	private List<Poder> poderesPlayer;
	
	private CapacityBar barProgress;
	private List<Number> listNumber;
	
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
		
		player = new Personaje();
        player.setType("Fuego");
        player.setGold(10000);
        player.setLevel(8);
		
        poderesPlayer = new ArrayList<Poder>();
        
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
	    
	    CreateList();
		rowsArrayPoderes();
		
		hld = new HtmlLayoutData("central");
		
		retHtmlLayout2.setLayoutData(hld);

		retHtmlLayout1.add(retHtmlLayout2);
		
		return retHtmlLayout1;
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
        
        labelGold = new Label(" "  + player.getGold());
        labelGold.setForeground(Color.YELLOW);
        rowPanel.add(labelGold);
        
        panel.add(rowPanel);
        
        rowBtn.add(panel);
        
        row.add(rowBtn);
        
        
        rowBtn = new Row();

	    Button btnExit = new Button("Salir");
	    btnExit.setStyle(Styles1.DEFAULT_STYLE);
	    btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              removeAll();
              add(new MapaDesktop(usuario));
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
		rowProgress.add(lblTimeHora);
	    
		col.add(rowProgress);
		
		return col;
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
	    		Poder poder = (Poder) element;
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
	    		Poder poder = (Poder) element;
	    		return poder.getImage();
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
	    		Poder poder = (Poder) element;
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
	    		Poder poder = (Poder) element;
	    		return poder.getGold();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.ORANGE);
	    
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

	          final Poder poder = (Poder) tableDtaModel.getElementAt(row);
	          
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
	          
	          if ( (poder.getUse() == false) && (player.getLevel() >= poder.getLevel()) )
	          {
	        	  
	        	  if((poder.getType() == player.getType()) )
	        	  {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {  
	        				  
	    	        		  if( poder.getTrainingTime() > 0 )
	    	        		  {
	    	        			  if((player.getGold() >= poder.getGold()) && (player.getLevel() >= poder.getLevel()) && (player.getLearning() == false))
	    	        			  {
	    	        				  btnVerClicked(row);
		    	        			  rowProgress.setVisible(true);

		    	        			  if(poder.getTrainingTime() == 1){
		        						  lblHour.setText("0");
		        						  lblMin.setText("3");
		        						  lblSec.setText("0");
		        						  fraccionBar = (1000.0/(180.0));
		        					  }
		        					  else
		        					  {
		        						  lblHour.setText("" + poder.getTrainingTime());
		        						  lblMin.setText("0");
		        						  lblSec.setText("0");
		        						  fraccionBar = (1000.0/(poder.getTrainingTime()*3600.0)); 
		        					  }
		    	        			  lblTimeHora.setText(lblHour.getText() + " : " + lblMin.getText() + " : " + lblSec.getText());

		    	        			  timedServer.beg(); 
		    	        			  
		    	        			  player.setGold(player.getGold() - poder.getGold());
		        					  labelGold.setText("" + player.getGold());
		        					  
		        					  ret.setEnabled(false);
		        					  poder.setUse(true);
		        					  player.setLearning(true);
		        					  
		        					  poderesPlayer.add(poder);
		        					  player.setPoderes(poderesPlayer);
		        					  
		        					  flag = false;
		    	        			  
	    	        			  }
	    	        			  else if ( player.getLearning() == true )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted esta entrenando " + poderesPlayer.get(poderesPlayer.size()-1).getName());
	    	        				  add(ventanaCompra);  
	    	        			  }
	    	        			  else if ( player.getGold() < poder.getGold() )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  //ventanaCompra.setMaximumHeight(new Extent(250);
		        					  lblText.setText("Usted no tiene oro suficiente para entrenar el poder " + poder.getName());
		        					  add(ventanaCompra);
	    	        			  }
	    	        			  
	    	        		  }
	    	        		  else
	    	        		  {
	    	        			  if((player.getGold() >= poder.getGold()) && (player.getLevel() >= poder.getLevel()) && (player.getLearning() == false))
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted compro " + poder.getName());
	    	        				  add(ventanaCompra);
	    	        				  
		    	        			  player.setGold(player.getGold() - poder.getGold());
		        					  labelGold.setText("" + player.getGold());
		        					  
		    	        			  poder.setUse(true);	        				  
		        					  ret.setEnabled(false);
	    	        				  
		        					  poderesPlayer.add(poder);
		        					  player.setPoderes(poderesPlayer);
		        					  
		        					  
	    	        			  }
	    	        			  else if(  player.getLearning() == true )
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  lblText.setText("Usted esta entrenando " + poderesPlayer.get(poderesPlayer.size()-1).getName());
	    	        				  add(ventanaCompra);  
	    	        			  }
	    	        			  else if(player.getGold() < poder.getGold())
	    	        			  {
	    	        				  btnVerClicked(row);
	    	        				  
	    	        				  //ventanaCompra.setMaximumHeight(new Extent(250));
	    	        				  lblText.setText("Usted no tiene oro suficiente para entrenar el poder " + poder.getName());
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

						  player.setLearning(false);

						  timedServer.end();
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
	
	private void AsignarDatos (int id,int level,String nombre,int oro,int dano,int tiemporeutilizacion,int tiempoEntrenamiento,int psinergia,String tipo,boolean uso,String dirImage)
	{
		
		Poder poder = new Poder();
		
		poder.setId(id);
		poder.setLevel(level);
		poder.setName(nombre);
		poder.setGold(oro);
		poder.setDamage(dano);
		poder.setCooldown(tiemporeutilizacion);
		poder.setTrainingTime(tiempoEntrenamiento);
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
		AsignarDatos(1,1, "Bola de Fuego", 500, 30, 2, 0, 45, "Fuego",false,"Images/Poderes/Fuego/FireBall.png");
		AsignarDatos(2,1, "Explosión de Hielo", 500, 28, 2, 0, 35, "Hielo",false,"Images/Poderes/Hielo/WaterBurst.png");
		AsignarDatos(3,1, "Rotura de la Tierra", 500, 26, 2, 0, 33, "Tierra",false,"Images/Poderes/Tierra/EarthSmash.png");
		AsignarDatos(4,1, "Puño del Dragón", 500, 27, 2, 0, 30, "Combo",false,"Images/Poderes/Combo/DragonFist.png");
		
		AsignarDatos(5,2, "Explosión de Fuego", 1000, 39, 3, 0, 58, "Fuego",false,"Images/Poderes/Fuego/FireExplosion.png");
		AsignarDatos(6,2, "Bola de Agua", 1000, 36, 3, 0, 44, "Hielo",false,"Images/Poderes/Hielo/WaterBall.png");
		AsignarDatos(7,2, "Puño de Tierra", 1000, 33, 3, 0, 42, "Tierra",false,"Images/Poderes/Tierra/EarthFist.png");
		AsignarDatos(8,2, "Impacto Mortal", 1000, 35, 3, 0, 38, "Combo",false,"Images/Poderes/Combo/HeadKick.png");
		
		AsignarDatos(9,3, "Rueda de Fuego", 1500, 51, 5, 0, 74, "Fuego",false,"Images/Poderes/Fuego/FireSpikeWheel.png");
		AsignarDatos(10,3, "Prisión de Hielo", 1500, 46, 5, 0, 56, "Hielo",false,"Images/Poderes/Hielo/WaterPrision.png");
		AsignarDatos(11,3, "Estragulamiento", 1500, 43, 5, 0, 53, "Tierra",false,"Images/Poderes/Tierra/EarthStrangle.png");
		AsignarDatos(12,3, "Golpe Cañon", 1500, 44, 5, 0, 49, "Combo",false,"Images/Poderes/Combo/CannonballStrike.png");
		
		AsignarDatos(13,4, "LLama de Fuego", 2100, 66, 7, 0, 94, "Fuego",false,"Images/Poderes/Fuego/FieryFlame.png");
		AsignarDatos(14,4, "Dragón de Agua", 2100, 59, 7, 0, 72, "Hielo",false,"Images/Poderes/Hielo/WaterDragonVortex.png");
		AsignarDatos(15,4, "Muro de Tierra", 2100, 55, 7, 0, 68, "Tierra",false,"Images/Poderes/Tierra/EarthWall.png");
		AsignarDatos(16,4, "Patada y Puño Veloz", 2100, 57, 7, 0, 63, "Combo",false,"Images/Poderes/Combo/SwiftKick.png");
		
		AsignarDatos(17,5, "Torbellino de Fuego", 2700, 86, 8, 1, 121, "Fuego",false,"Images/Poderes/Fuego/FireVortex.png");
		AsignarDatos(18,5, "Misil de Hielo", 2700, 76, 8, 1, 91, "Hielo",false,"Images/Poderes/Hielo/WaterMissile.png");
		AsignarDatos(19,5, "Golem de Barro", 2700, 70, 8, 1, 86, "Tierra",false,"Images/Poderes/Tierra/MudGolem.png");
		AsignarDatos(20,5, "Triple Puñetazo", 2700, 72, 8, 1, 81, "Combo",false,"Images/Poderes/Combo/ThreeCombatRapid.png");
		
		AsignarDatos(21,6, "Misil de Fuego", 3300, 111, 10, 2, 155, "Fuego",false,"Images/Poderes/Fuego/FireMissile.png");
		AsignarDatos(22,6, "Cañón de Hielo", 3300, 97, 10, 2, 116, "Hielo",false,"Images/Poderes/Hielo/WaterJetCannon.png");
		AsignarDatos(23,6, "Estacas de Tierra", 3300, 89, 10, 2, 109, "Tierra",false,"Images/Poderes/Tierra/EarthSpike.png");
		AsignarDatos(24,6, "Super Gancho Rapido", 3300, 93, 10, 2, 103, "Combo",false,"Images/Poderes/Combo/RapidUppercut.png");
		
		AsignarDatos(25,7, "Flecha de Fuego", 4000, 145, 10, 4, 198, "Fuego",false,"Images/Poderes/Fuego/FireArrow.png");
		AsignarDatos(26,7, "Estacas de Hielo", 4000, 124, 10, 4, 147, "Hielo",false,"Images/Poderes/Hielo/WaterImpale.png");
		AsignarDatos(27,7, "Erosión del Suelo", 4000, 114, 10, 4, 138, "Tierra",false,"Images/Poderes/Tierra/EarthErosion.png");
		AsignarDatos(28,7, "Cuatro Puñetazos Mortales", 4000, 119, 10, 4, 132, "Combo",false,"Images/Poderes/Combo/FourPalm.png");
		
		AsignarDatos(29,8, "Fuego del Demonio", 4700, 188, 10, 6, 253, "Fuego",false,"Images/Poderes/Fuego/Hellfire.png");
		AsignarDatos(30,8, "Renovación del Hielo", 4700, 159, 10, 6, 187, "Hielo",false,"Images/Poderes/Hielo/WaterConvergence.png");
		AsignarDatos(31,8, "Explosión de Tierra", 4700, 146, 10, 6, 176, "Tierra",false,"Images/Poderes/Tierra/EarthBlast.png");
		AsignarDatos(32,8, "Mega Patada", 4700, 152, 10, 6, 169, "Combo",false,"Images/Poderes/Combo/AssaultKick.png");
		
		AsignarDatos(33,9, "Pilar de Fuego", 5400, 245, 10, 8, 324, "Fuego",false,"Images/Poderes/Fuego/PillarofFlame.png");
		AsignarDatos(34,9, "Doble Explosión de Hielo", 5400, 203, 10, 8, 237, "Hielo",false,"Images/Poderes/Hielo/DoubleWaterBurst.png");
		AsignarDatos(35,9, "Explosión de Roca", 5400, 187, 10, 8, 223, "Tierra",false,"Images/Poderes/Tierra/EarthBoulder.png");
		AsignarDatos(36,9, "Patada Voladora", 5400, 195, 10, 8, 216, "Combo",false,"Images/Poderes/Combo/FlashSmash.png");
		
		AsignarDatos(37,10, "Doble Bola de Fuego ", 10400, 318, 10, 10, 415, "Fuego",false,"Images/Poderes/Fuego/DoubleFireball.png");
		AsignarDatos(38,10, "Lanza de Hielo", 10400, 260, 10, 10, 301, "Hielo",false,"Images/Poderes/Hielo/WaterSpear.png");
		AsignarDatos(39,10, "Doble Rotura de la Tierra", 10400, 240, 10, 10, 284, "Tierra",false,"Images/Poderes/Tierra/DoubleEarthSmash.png");
		AsignarDatos(40,10, "Ráfaga Golpe Dragón", 10400, 249, 10, 10, 277, "Combo",false,"Images/Poderes/Combo/ThousandViolenceStrike.png");
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
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			tableDtaModel.add(listPoder.get(i));					
		}
		
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
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Fuego")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}		
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
		
		for(int i = 0; i < listPoder.size(); i++)
		{
			if (listPoder.get(i).getType() == "Hielo")
			{
				tableDtaModel.add(listPoder.get(i));
			}
			
		}
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

		colCartel.removeAll();

		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		if (poder.getId() == 1)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una bola de fuego para golpear a tus enemigos.");
			
		}
		
		if (poder.getId() == 5)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza multiples explosiones de fuego.");
		}
		
		if (poder.getId() == 9)
		{	
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Elabora una rueda de fuego para arrollar a tus enemigos.");
		}
		
		if (poder.getId() == 13)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza desde la boca flamas de fuego.");
		}
		
		if (poder.getId() == 17)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza un torbellino de fuego al enemigo.");
		}
		
		if (poder.getId() == 21)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza un misil de fuego.");
		}
		
		if (poder.getId() == 25)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Combina una daga y psinergia para crear una flecha de fuego.");
		}
		
		if (poder.getId() == 29)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Causa daño y quema a tu enemigo.");
		}
		
		if (poder.getId() == 33)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Moldea la psinergia con fuego para crear un pilar de fuego.");
		}
		
		if (poder.getId() == 37)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Crea dos bolas de fuego y quema a tu enemigo en ceniza.");
		}
		
	}
	
	private void infoPowerIce(Poder poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		if (poder.getId() == 2)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Genera una explosión de hielo.");
		}		
		
		if (poder.getId() == 6)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una bola de Agua para golpear a tus enemigos.");
		}
		
		if (poder.getId() == 10)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Forma una bola enorme de hielo para encarcelar a tus enemigos.");
		}
		
		if (poder.getId() == 14)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza un dragón de agua para arrollar a tus enemigos.");
		}
		
		if (poder.getId() == 18)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza un misil de Hielo.");
		}
		
		if (poder.getId() == 22)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Lanza un cañon de hielo a tus enemigos.");
		}
		
		if (poder.getId() == 26)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Forma espinas de hielo para atacar al enemigo.");
		}
		
		if (poder.getId() == 30)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Regenera la fuerza del hielo y crea una enorme bola de hielo.");
		}
		
		if (poder.getId() == 34)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza doble explosión de hielo y daña a tus enemigos.");
		}
		
		if (poder.getId() == 38)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza estacas de hielos y atraviesa a tus enemigos.");
		}
	}

	private void infoPowerEarth(Poder poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		if (poder.getId() == 3)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Genera un enorme poder sobre el terreno.");
		}
		
		if (poder.getId() == 7)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Transforma un puño en roca sólida.");
		}
		
		if (poder.getId() == 11)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Crea una mano de tierra y estrangula a tus enemigos.");
		}
		
		if (poder.getId() == 15)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una enorme pared de tierra sólida.");
		}
		
		if (poder.getId() == 19)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Invoca un Golem de barro combinando tierra y psinergia.");
		}
		
		if (poder.getId() == 23)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Crea enormes estacas de tierra.");
		}
		
		if (poder.getId() == 27)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Absorbe los minerales del suelo para crear la erosión de la tierra.");
		}
		
		if (poder.getId() == 31)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Concentra psinergia en el super puño de tierra.");
		}
		
		if (poder.getId() == 35)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Absorbe minerales del suelo y crea una roca.");
		}
		
		if (poder.getId() == 39)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Aplasta a tus enemigos con el poder de la tierra.");
		}
	}
	
	private void infoPowerCombo(Poder poder)
	{

		colCartel.removeAll();
		
		if ( player.getLearning() == true )
			colCartel.add(DscripRowLearn());
		else
			colCartel.add(DscripRow());
		
		if (poder.getId() == 4)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza un puño estilo dragón al enemigo.");
		}
		
		if (poder.getId() == 8)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza un ataque combinando la espada y el puño.");
		}
		
		if (poder.getId() == 12)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Un super golpe seguido por una patada rapida.");
		}
		
		if (poder.getId() == 16)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una patada y un puñetazo rapido a tus enemigos.");
		}
		
		if (poder.getId() == 20)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Ataca a tus enemigos con una combinación de puños.");
		}
		
		if (poder.getId() == 24)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Golpea con un poderoso gancho que eleva al enemigo.");
		}
		
		if (poder.getId() == 28)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Concentra psinergia en sus manos y ataca al enemigo por cuatro veces.");
		}
		
		if (poder.getId() == 32)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una patada poderosa dejando al enemigo confundido.");
		}
		
		if (poder.getId() == 36)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una fuerte patada mortal que eleva a tus enemigos.");
		}
		
		if (poder.getId() == 40)
		{
			name.setText("" + poder.getName());
			damage.setText("Daño: " + poder.getDamage());
			cooldown.setText("Tiempo de Reutilización: "+ poder.getCooldown());
			psinergia.setText("Psinergia: " + poder.getPsinergia());
			if(poder.getTrainingTime() == 0)
				timeTraining.setText("Tiempo de Entrenamiento: ( Instantáneo )");
			else	
				timeTraining.setText("Tiempo de Entrenamiento: " + poder.getTrainingTime() + " Horas");
			description.setText("Realiza una ráfagas de golpes destructivos sin dudarlo.");
		}
	}
	
}
