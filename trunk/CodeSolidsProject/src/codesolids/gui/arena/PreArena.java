package codesolids.gui.arena;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
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
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.button.AbstractButton;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

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

import codesolids.bd.clases.Invitation;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.Styles1;
import codesolids.util.TestTableModel;
import codesolids.util.TimedServerPush;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Fernando Osuna
 * 
 */

@SuppressWarnings("serial")
public class PreArena extends ContentPane{
	private Usuario usuario;
	private Label lblData;
	private int actual = 0;
	private TestTableModel tableDtaModelUsuario;
	private TestTableModel tableDtaModelInvitacion;
	List<Usuario> results = new ArrayList<Usuario>();
	List<Invitation> resultsI = new ArrayList<Invitation>();
	
	private HtmlLayout htmlLayout;
	
	TaskQueueHandle taskQueue;
	TimedServerPush inviteServerPush;
	
	public PreArena(Usuario usuario) {		
		taskQueue = ApplicationInstance.getActive().createTaskQueue();		
		this.usuario = usuario;	
	    initGUI();
	}
	
	private void initGUI() {
		add(initPreArena());
	}
	
	public Component initPreArena(){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("prearena.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");		
		Row menu = new Row();
		
		Button returnButton = new Button();
		returnButton.setText("Salir");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al Mapa Principal");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {			
			inviteServerPush.end();
			buttonExitClicked(e);				
			}
		});
		menu.add(returnButton);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);
		
		hld = new HtmlLayoutData("descrip");
		menu = new Row();
	    lblData = new Label("Usuarios en linea");
	    lblData.setForeground(Color.WHITE);
	    menu.add(lblData);
	    menu.setCellSpacing(new Extent(250));
	    lblData = new Label("Invitaciones recibidas");
	    lblData.setForeground(Color.WHITE);
	    menu.add(lblData);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);
		
		hld = new HtmlLayoutData("central");
		
		Row rowCentral = new Row();
		tableDtaModelUsuario = new TestTableModel();
		tableDtaModelInvitacion= new TestTableModel();

	    ApplicationInstance app = ApplicationInstance.getActive();
	    inviteServerPush = new TimedServerPush(1000, app, taskQueue, new Runnable() {
	    	@SuppressWarnings("unchecked")
			@Override
	    	public void run(){
	    			Session session = SessionHibernate.getInstance().getSession();
			  	    session.beginTransaction();
			  	    String queryStr;
			  	    Query query;
			  		Calendar cal = Calendar.getInstance(); // La fecha actual
			  	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());
			  	    
			  	    if(actual == 0){
				  	    queryStr = "UPDATE t_user SET arena = :date WHERE id = :idUser";
				  	    query = session.createSQLQuery(queryStr);
				  	    query.setCalendar("date", cal);
				  	    query.setInteger("idUser", usuario.getId());
					    query.executeUpdate();
					    actual = 1;
			  	    }
			  	    actual++;
			  	    if (actual == 29){
			  	    	actual = 0;
			  	    }

			  		cal.add(Calendar.MINUTE, -1); // La fecha actual menos un minuto

			  		queryStr = "FROM Usuario WHERE arena >= :oneMinuteAgo";
			  		query = session.createQuery(queryStr);
			  		query.setCalendar("oneMinuteAgo", cal);
			  	    
			  		List<Object> resultQuery =  query.list();
			  		
			  	    createListTable(resultQuery); 			  		
	    		
			  	    session.getTransaction().commit();			  	        
			  	    session.close();
	    	}
	    });
		
	    activate();
	    
	    rowCentral.add(createTable(tableDtaModelUsuario, initTableColModel(1)));
	    rowCentral.add(createTable(tableDtaModelInvitacion, initTableColModel(2)));
		rowCentral.setLayoutData(hld);
		htmlLayout.add(rowCentral);
		
		return htmlLayout;		
	}
	
	public void createListTable(List<Object> resultQuery) {
		Iterator<Object> iter = resultQuery.iterator();
  	    if (!iter.hasNext()) {
  	    	return;
  	    }
  	    results.clear();
  	    tableDtaModelUsuario.clear();
  	    while (iter.hasNext()) {  	        
			Usuario user = (Usuario) iter.next();
			results.add(user);  	            
  	    }
  	    
		for (int i = 0; i < results.size(); i++) {
			tableDtaModelUsuario.add(results.get(i));
		}
		consultInvitations();    		
	}
	
	public Panel createTable(TestTableModel tableDtaModel, TableColModel initTableColModel) {

		Panel panel = new Panel();
		panel.setInsets(new Insets(100, 0, 100, 0));
		panel.setAlignment(Alignment.ALIGN_CENTER);

		Column col = new Column();
		col.setInsets(new Insets(0, 0, 0, 0));
		col.setCellSpacing(new Extent(10));

		TableColModel tableColModel = initTableColModel;
		TableSelModel tableSelModel = new TableSelModel();
		tableDtaModel.setEditable(true);
		tableDtaModel.setPageSize(14);

		ETable table = new ETable();
		table.setTableDtaModel(tableDtaModel);
		table.setTableColModel(tableColModel);
		table.setTableSelModel(tableSelModel);
		table.setEasyview(false);
		table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
		col.add(table);
		
		Row row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);

		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
		tableNavigation.setForeground(Color.WHITE);
		row.add(tableNavigation);

		col.add(row);
		panel.add(col);
		return panel;

	}
	
	private TableColModel initTableColModel(final int tipo) {
		
		TableColModel tableColModel = new TableColModel();
	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Object obj = new Object();
	    		if(tipo == 1){
		    		Usuario user = (Usuario) element;
		    		obj = user.getLogin();
	    		}
	    		else if(tipo == 2){
	    			Invitation iv = (Invitation) element;
	    			obj = iv.getUserGeneratesRef().getLogin();
	    		}
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
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Usuario");
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(100));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    tableColumn.setHeadCellRenderer(lcr);
		
	    if(tipo == 1)
	    	tableColumn.setDataCellRenderer(initNestedCellRenderer1());
	    else if(tipo == 2)
	    	tableColumn.setDataCellRenderer(initNestedCellRenderer2());
	    tableColModel.getTableColumnList().add(tableColumn);
	    
		return tableColModel;

	}
	
	private CellRenderer initNestedCellRenderer1() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          Usuario user = (Usuario) tableDtaModelUsuario.getElementAt(row);
	          Button ret = new Button("Invitar");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Invitar");
			  final Invitation invitation = new Invitation();
			  invitation.setUserGeneratesRef(usuario);
			  invitation.setUserReceivesRef(user);
	          if (usuario.getId() != user.getId() )
	          {
	        	  ret.addActionListener(new ActionListener() {
        			  public void actionPerformed(ActionEvent e) {
        				  btnInviteClicked(invitation);
        			  }
        		  });
	          }
	          else{
	        	  ret.setVisible(false);	        	  
	          }
	          return ret;
	        }
	    });
	
	    return nestedCellRenderer;
	}
	
	private CellRenderer initNestedCellRenderer2() {
		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	            final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          final Invitation inv = (Invitation) tableDtaModelInvitacion.getElementAt(row);
	          Button ret = new Button("Acpt");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Aceptar");

	          if (usuario != inv.getUserGeneratesRef() )
	          {	  
	        		  ret.addActionListener(new ActionListener() {
	        			  public void actionPerformed(ActionEvent e) {
	        				  BtnClicked(row);
	        			  }	        		  
	        			  private void BtnClicked(int row) {	        				
	      	          		inviteServerPush.end();
	      	          		btnDeleteClicked(inv);
	      	          		UpdateOutOfArena();
	    	          		removeAll();
	    	          		add(new ArenaDesktop(usuario));
	        			  }
	        		  });	        	  
	          }
	          else{
	        	  ret.setVisible(false);	        	  
	          }
	          return ret;
	        }
	    });
		
		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override
			public Component getCellRenderer( //
	          final ETable table, final Object value, final int col, final int row) {

	          boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

	          final Invitation inv = (Invitation) tableDtaModelInvitacion.getElementAt(row);
	          Button ret = new Button("Rech");
	          ret.setStyle(Styles1.DEFAULT_STYLE);
	          ret.setEnabled(editable);
	          ret.setToolTipText("Rechazar");	          
	          ret.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent e) {
	        			  btnDeleteClicked(inv);
	        		}
	          });	        	  
	          return ret;
	        }
	    });	
	    return nestedCellRenderer;
	}
	
	private void activate(){
		inviteServerPush.beg();
		
	}
	
	private void consultInvitations(){
		
	    Session session = SessionHibernate.getInstance().getSession();
	    session.beginTransaction();
	      
	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());

	    Invitation obj = new Invitation();
		obj.setUserReceivesRef(usuario);
		List<Invitation> list = session.createCriteria(Invitation.class).add(Example.create(obj)).list();
		  
	    session.getTransaction().commit();
	    session.close();

	    resultsI.clear();
	    tableDtaModelInvitacion.clear();
   	  	for( int i = 0; i < list.size(); i++){
   	  		if(usuario.getLogin() == list.get(i).getUserReceivesRef().getLogin()){
	   			Invitation iv = new Invitation();
	   			iv.setId(list.get(i).getId());
	   			iv.setUserGeneratesRef(list.get(i).getUserGeneratesRef());
	   			iv.setUserReceivesRef(list.get(i).getUserReceivesRef());
				resultsI.add(iv);
   	  		}
   	  	}
   	  	for( int i = 0; i < resultsI.size(); i++){
   			tableDtaModelInvitacion.add(resultsI.get(i));
   	  	}
	}
	
	private void btnInviteClicked(Invitation invitation) {  		
	    Session session = null;
	    try {
	      session = SessionHibernate.getInstance().getSession();
	      session.beginTransaction();
	      
		  Invitation bean = new Invitation();
		  bean.setUserGeneratesRef(invitation.getUserGeneratesRef());
		  bean.setUserReceivesRef(invitation.getUserReceivesRef());

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
	
	private void btnDeleteClicked(Invitation invitation) {  		
	    Session session = null;
	    try {
	      session = SessionHibernate.getInstance().getSession();
	      session.beginTransaction();
	      
	      session.delete(invitation);		  

	    } finally {

	      if (session != null) {
	        if (session.getTransaction() != null) {
	          session.getTransaction().commit();
	        }
	        session.close();
	      }
	    }
    }
	
	private void UpdateOutOfArena() {
		
		Session session = SessionHibernate.getInstance().getSession();
  	    session.beginTransaction();
  	    String queryStr;
  	    Query query;
  		Calendar cal = Calendar.getInstance(); // La fecha actual
  		cal.add(Calendar.MINUTE, -5);
  	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());
  	    
  	    queryStr = "UPDATE t_user SET arena = :date WHERE id = :idUser";
  	    query = session.createSQLQuery(queryStr);
  	    query.setCalendar("date", cal);
  	    query.setInteger("idUser", usuario.getId());
	    query.executeUpdate();
	    
	    Invitation obj = new Invitation();
		obj.setUserReceivesRef(usuario);

		List<Invitation> list = session.createCriteria(Invitation.class).add(Example.create(obj)).list();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getUserGeneratesRef() == usuario ){
				session.delete(list.get(i));
			}
		}

  	    session.getTransaction().commit();			  	        
  	    session.close();
}
	
	private void buttonExitClicked(ActionEvent e) {	
		UpdateOutOfArena();
		removeAll();
		add(new MapaDesktop(usuario));
	}
}