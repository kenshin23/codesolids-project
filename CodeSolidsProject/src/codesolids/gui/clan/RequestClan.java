package codesolids.gui.clan;

import java.util.Calendar;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Query;
import org.hibernate.Session;

import codesolids.bd.clases.Clan;
import codesolids.bd.clases.Mensaje;
import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
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
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

/**
 * 
 * @author Antonio Lopez
 *
 */

public class RequestClan extends Column {

	private TestTableModel tableDtaModel;
	
	private ETable table;
	
	private WindowPane ventana;
	
	public RequestClan(WindowPane ventana)
	{
		this.ventana = ventana;
				
		initRequest();
	}

	private void initRequest() {
		
		this.setCellSpacing(new Extent(2));
		
		TableColModel tableColModel = initTableColModel();
		TableSelModel tableSelModel = new TableSelModel();
		
		tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(10);
		
	    table = new ETable();
	    
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);
	    
	    table.setBorder(new Border(new Extent(1), new Color(87, 205, 211), Border.STYLE_RIDGE));
		
	    add(table);
		
		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    tableNavigation.setAlignment(Alignment.ALIGN_RIGHT);
	    tableNavigation.setForeground(Color.WHITE);
		add(tableNavigation);
	    
		loadTableClan();
	}
	
	private TableColModel initTableColModel() {
		
		TableColModel tableColModel = new TableColModel();
		
		TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Clan clan = (Clan) element;
	    		return clan.getNameClan();
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
	    
	    tableColumn.setWidth(new Extent(120));
	    tableColumn.setHeadValue("Nombre Clan");
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Clan clan = (Clan) element;
	    		return clan.getClanMasterRef().getUsuarioRef().getLogin();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(60));
	    tableColumn.setHeadValue("Lider Clan");
	       
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
	    		Clan clan = (Clan) element;
	    		return clan.getCantPersonaje() + " / " + clan.getLimite();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Total Miembro");
	    
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
	    		Clan clan = (Clan) element;
	    		return clan.getReputacion();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(30));
	    tableColumn.setHeadValue("Reputación");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(new Color(128,0,128));
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

				Button btnRequest = new Button("Enviar Solicitud");
				btnRequest.setStyle(Styles1.DEFAULT_STYLE);
				btnRequest.setEnabled(editable);
				btnRequest.setToolTipText("Enviar Solicitud de Vacante");
				
				btnRequest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnRequestClicked(row);
					}
				});
				
				return btnRequest;
			}
		});
		
		return nestedCellRenderer;
	}
	
	private void btnRequestClicked(int row) {
		
		ventana.userClose();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Mensaje mailBox = new Mensaje();
		
		Calendar calSend = Calendar.getInstance();
		mailBox.setDateSend(calSend);
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		Personaje personajeEnv = app.getPersonaje();
		mailBox.setPersonajeSendRef(personajeEnv);
		
		Clan clan = (Clan) tableDtaModel.getElementAt(row); 
		Personaje personajeRec = clan.getClanMasterRef();
		mailBox.setPersonajeReceivesRef(personajeRec);
		
		mailBox.setLeido(false);
		
		session.save(mailBox);
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	private void loadTableClan()
	{
	    
		Session session = SessionHibernate.getInstance().getSession();
	    session.beginTransaction();
			    
	    String queryStr = "SELECT c FROM Clan AS c WHERE c.cantPersonaje < c.limite ORDER BY c.reputacion"; 
	    Query query = session.createQuery(queryStr);
	    List<Clan> clanList = query.list();
	    
    	session.getTransaction().commit();
	    session.close();
	    
	    for( int i = 0; i < clanList.size(); i++  )
	    	tableDtaModel.add(clanList.get(i));
	    	
	}
	
}
