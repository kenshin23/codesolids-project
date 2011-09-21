package codesolids.gui.seleccion;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import codesolids.gui.crear.DesktopCreate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.util.TestTableModel;
import codesolids.bd.hibernate.SessionHibernate;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * 
 * @author Antonio López
 *
 */

public class DesktopSelect extends ContentPane{

	private TestTableModel tableDtaModel;
	
	private ETable table;
	
	private Usuario usuario;
	
	public DesktopSelect()
	{
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		usuario = app.getUsuario();
		
		initGUI();
		
	}

	private void initGUI() {
		
		add(initSelect());
	}

	private Component initSelect() {
		
		HtmlLayout retHtmlLayout;
		
		try{
			
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateselect.html"), "UTF-8");
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Column colT = new Column();

		TableColModel tableColModel = initTableColModel();
		TableSelModel tableSelModel = new TableSelModel();

		tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(3);

	    table = new ETable();
	    
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);
	    
	    table.setBorder(new Border(new Extent(1), new Color(87, 205, 211), Border.STYLE_RIDGE));
		
	    colT.add(table);
		
	    loadTableBD();
	    
	    HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("tabla");
		
		colT.setLayoutData(hld);
	    
		retHtmlLayout.add(colT);
		
		return retHtmlLayout;

	}

	private TableColModel initTableColModel() {
		
		TableColModel tableColModel = new TableColModel();
		
		TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		return usuario.getLogin();
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
	    tableColumn.setHeadValue("Nombre");
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getLevel();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(30));
	    tableColumn.setHeadValue("Nivel");
	       
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
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getTipo();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(30));
	    tableColumn.setHeadValue("Tipo");
	    
	    
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

	private CellRenderer initNestedCellRenderer() 
	{

		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();

		nestedCellRenderer.setBackground(new Color(226,211,161));

		nestedCellRenderer.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				Button btnSelect = new Button("Seleccionar");
				btnSelect.setStyle(Styles1.DEFAULT_STYLE);
				btnSelect.setEnabled(editable);
				btnSelect.setToolTipText("Seleccionar");
				
				btnSelect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnSelectClicked(row);
					}
				});

				Personaje pBean = (Personaje) tableDtaModel.getElementAt(row);
				
				if( pBean.getLevel() == 0 )
					btnSelect.setVisible(false);
				else
					btnSelect.setVisible(true);
				
				return btnSelect;
			}
		});

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				Button btnCreate = new Button("Crear Personaje");
				btnCreate.setStyle(Styles1.DEFAULT_STYLE);
				btnCreate.setEnabled(editable);
				btnCreate.setToolTipText("Crear Personaje");
				
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnCreateClicked(row);
					}
				});

				Personaje pBean = (Personaje) tableDtaModel.getElementAt(row);
				
				if( pBean.getLevel() == 0 )
					btnCreate.setVisible(true);
				else
					btnCreate.setVisible(false);
				
				return btnCreate;
			}
		});

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				Button btnDelete = new Button("Eliminar");
				btnDelete.setStyle(Styles1.DEFAULT_STYLE);
				btnDelete.setEnabled(editable);
				btnDelete.setToolTipText("Eliminar");
				
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnDeleteClicked(row);
					}
				});
				
				Personaje pBean = (Personaje) tableDtaModel.getElementAt(row);
				
				if( pBean.getLevel() == 0 )
					btnDelete.setVisible(false);
				else
					btnDelete.setVisible(true);
				
				return btnDelete;
			}
		});		

		return nestedCellRenderer;
	}
	
	private void btnSelectClicked(int row) {
		
		Personaje personaje = (Personaje) tableDtaModel.getElementAt(row);
 		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		app.setPersonaje(personaje);
		
		removeAll();
		add(new MapaDesktop());
	
	}
	
	private void btnCreateClicked(int row) {
		removeAll();
		add(new DesktopCreate());
	}
	
	private void btnDeleteClicked(int row) {
		
		Personaje pBean = (Personaje) tableDtaModel.getElementAt(row);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Personaje.class).add(Restrictions.eq("id", pBean.getId()));
		
		pBean = (Personaje) criteria.uniqueResult();
		
		session.delete(pBean);
		
		session.getTransaction().commit();
		session.close();
		
		removeAll();
		add(new DesktopSelect());
		
	}
	
	private void loadTableBD() 
	{    

		int index = 0;

	    Session session = SessionHibernate.getInstance().getSession();
	    session.beginTransaction();
			    
	    List<Personaje> listP = session.createCriteria(Personaje.class).list();
	    
    	session.getTransaction().commit();
	    session.close();	
	    	    
	    for(int j = 0; j < listP.size(); j++)
	    {
	    	if(listP.get(j).getUsuarioRef().getId() == usuario.getId())
	    	{
	    		tableDtaModel.add(listP.get(j));
	    		index = index + 1;
	    	}
	    }

	    Personaje pBean = new Personaje();
    	
	    for( int i = 0; i < (3 - index); i++ )
	    {   	
	    	tableDtaModel.add(pBean);	
	    }	    
	}

}
