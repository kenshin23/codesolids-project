/*
 * Created on 06/07/2011
 */
package codesolids.gui.perfil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

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
//import nextapp.echo.app.Font;
//import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.Styles1;
import codesolids.util.TestTableModel;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

import codesolids.bd.clases.Invitation;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import codesolids.bd.clases.PersonajesDisponibles;
import codesolids.bd.hibernate.SessionHibernate;

/**
 * 
 * @author Fernando Osuna
 *
 */

@SuppressWarnings("serial")
public class EditarPerfil extends ContentPane{
	private Usuario usuario;
	private Label lblData;
	Panel descrip = new Panel();
	private TestTableModel tableDtaModel;
	List<PersonajesDisponibles> results = new ArrayList<PersonajesDisponibles>();
	
	private HtmlLayout htmlLayout;
	
	public EditarPerfil(Usuario usuario) {		
		this.usuario = usuario;	
	    initGUI();
	}
	
	private void initGUI() {
		add(initEditarPerfil());
	}

	private Component initEditarPerfil(){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");		
		Row menu = new Row();
		
		Button returnButton = new Button();
		returnButton.setText("Perfil");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Ver el perfil");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonPerfilClicked(e);				
			}
		});
		menu.add(returnButton);
		
		returnButton = new Button();
		returnButton.setText("Salir");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al mapa");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonExitClicked(e);				
			}
		});
		menu.add(returnButton);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);
		
		hld = new HtmlLayoutData("button");
		menu = new Row();
	    lblData = new Label("Personajes");
	    lblData.setForeground(Color.WHITE);
	    menu.add(lblData);
	    menu.setCellSpacing(new Extent(250));
	    lblData = new Label("Descripcion");
	    lblData.setForeground(Color.WHITE);
	    menu.add(lblData);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);
		
		hld = new HtmlLayoutData("body");
		
		Row rowCentral = new Row();
		tableDtaModel = new TestTableModel();
		
		Session session = SessionHibernate.getInstance().getSession();
  	    session.beginTransaction();

  	    results = session.createCriteria(PersonajesDisponibles.class).list();
	
  	    for (int i = 0; i < results.size(); i++) {
  	    	PersonajesDisponibles pd = new PersonajesDisponibles();

  	    	pd.setNivel(results.get(i).getNivel());
  	    	pd.setExperiencia(results.get(i).getExperiencia());
  	    	pd.setVida(results.get(i).getVida());
  	    	pd.setPsinergia(results.get(i).getPsinergia());
  	    	pd.setAtaqueBasico(results.get(i).getAtaqueBasico());
  	    	pd.setAtaqueEspecial(results.get(i).getAtaqueEspecial());
  	    	pd.setVelocidad(results.get(i).getVelocidad());
  	    	pd.setDefensa(results.get(i).getDefensa());
  	    	pd.setOro(results.get(i).getOro());
  	    	pd.setDirImage(results.get(i).getDirImage());
  	    	pd.setTipo(results.get(i).getTipo());
  	    	
  	    	tableDtaModel.add(pd);
  	    }
		
  	    session.getTransaction().commit();  	        
  	    session.close();
		
  	    descrip.add(btnSeeClicked(results.get(0)));
		rowCentral.add(descrip);		
		rowCentral.add(createTable(tableDtaModel, initTableColModel()));
		
		rowCentral.setLayoutData(hld);
		htmlLayout.add(rowCentral);
		
		
		return htmlLayout;
	}
	
	public Panel createTable(TestTableModel tableDtaModel, TableColModel initTableColModel) {

		Panel panel = new Panel();
		panel.setInsets(new Insets(50, 0, 50, 0));
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setInsets(new Insets(0, 0, 0, 0));
		col.setCellSpacing(new Extent(10));

		TableColModel tableColModel = initTableColModel;
		TableSelModel tableSelModel = new TableSelModel();
		tableDtaModel.setEditable(true);
		tableDtaModel.setPageSize(10);

		ETable table = new ETable();
		table.setTableDtaModel(tableDtaModel);
		table.setTableColModel(tableColModel);
		table.setTableSelModel(tableSelModel);
		table.setEasyview(false);
		table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
		col.add(table);
		panel.add(col);
		return panel;

	}
	
	private TableColModel initTableColModel() {

		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Object obj = new Object();
	    		PersonajesDisponibles per = (PersonajesDisponibles) element;
	    		obj = per.getTipo();
	    		
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
	    
	    tableColumn.setWidth(new Extent(100));
	    tableColumn.setHeadValue("Personajes");
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(100));
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
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          final PersonajesDisponibles per = (PersonajesDisponibles) tableDtaModel.getElementAt(row);
	          Button ret = new Button("Ver");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Ver Personaje");

        	  ret.addActionListener(new ActionListener() {
    			  public void actionPerformed(ActionEvent e) {
    				  descrip.removeAll();
    				  descrip.add(btnSeeClicked(per));
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

	          final PersonajesDisponibles per = (PersonajesDisponibles) tableDtaModel.getElementAt(row);
	          Button ret = new Button("Crear");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Crear este personaje");

        	  ret.addActionListener(new ActionListener() {
    			  public void actionPerformed(ActionEvent e) {
    				  btnAddClicked(per);
    			  }
    		  });

	          return ret;
	        }
	    });
	
	    return nestedCellRenderer;
	}
	
	public void btnAddClicked(PersonajesDisponibles per){
	    Session session = null;
	    try {
	      session = SessionHibernate.getInstance().getSession();
	      session.beginTransaction();
	      
		  Personaje bean = new Personaje();

		  bean.setLevel(per.getNivel());
		  bean.setHp(per.getVida());
		  bean.setMp(per.getPsinergia());
		  bean.setGold(per.getOro());
		  bean.setTipo(per.getTipo());
		  bean.setXp(per.getExperiencia());
		  bean.setUsuarioRef(usuario);

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
	
	public Panel btnSeeClicked(PersonajesDisponibles per) {

		Panel panel = new Panel();
		panel.setInsets(new Insets(50, 0, 50, 0));
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setInsets(new Insets(0, 0, 0, 0));
		col.setCellSpacing(new Extent(10));
		
		Panel panelImage= new Panel();
		Row rowTab = new Row();
		Column colTab = new Column();
		
	    ResourceImageReference ir = new ResourceImageReference(per.getDirImage());
		
		Label lblImage = new Label(ir);
		panelImage.add(lblImage);
		panelImage.setHeight(new Extent(213));
		panelImage.setWidth(new Extent(200));
		panelImage.setBorder(new Border(new Extent(5, Extent.PX), Color.LIGHTGRAY, Border.STYLE_SOLID));

		lblData = new Label("Datos Generales ");
		lblData.setBackground(Color.LIGHTGRAY);
		colTab.add(lblData);
		lblData = new Label("Tipo " + per.getTipo());
		colTab.add(lblData);
		lblData = new Label("Nivel " + per.getNivel());
		colTab.add(lblData);
		lblData = new Label("XP " + per.getExperiencia());
		colTab.add(lblData);
		lblData = new Label("Oro " + per.getOro());
		colTab.add(lblData);
		lblData = new Label("Atributos Generales");
		lblData.setBackground(Color.LIGHTGRAY);
		colTab.add(lblData);
		lblData = new Label("Vida "+ per.getVida());
		colTab.add(lblData);
		lblData = new Label("Psinergia "+ per.getVida());
		colTab.add(lblData);
		lblData = new Label("Defensa " + per.getDefensa());
		colTab.add(lblData);
		lblData = new Label("Velocidad " + per.getVelocidad());
		colTab.add(lblData);
		lblData = new Label("Ataque Básico " + per.getAtaqueBasico());
		colTab.add(lblData);
		lblData = new Label("Ataque Especial " + per.getAtaqueEspecial());
		colTab.add(lblData);
		colTab.setBackground(Color.WHITE);
		colTab.setBorder( new Border(new Extent(2), Color.LIGHTGRAY, Border.STYLE_SOLID));
		rowTab.add(panelImage);
		rowTab.setCellSpacing(new Extent(15));
		rowTab.add(colTab);
		
		panel.add(rowTab);
		
		return panel;
	}
	
	private void buttonPerfilClicked(ActionEvent e) {
		removeAll();
		add(new PerfilDesktop(usuario));
	}
	
	private void buttonExitClicked(ActionEvent e) {
		removeAll();
		add(new MapaDesktop(usuario));
	}
	
}
