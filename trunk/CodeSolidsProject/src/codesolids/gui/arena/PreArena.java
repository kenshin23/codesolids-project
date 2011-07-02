package codesolids.gui.arena;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Invitation;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.Styles1;
import codesolids.util.TimedServerPush;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Fernando Osuna
 * 
 */


public class PreArena extends ContentPane{
	private Usuario usuario;
	private Label lblData;
	List<Usuario> results;
	List<Invitation> resultsI;
	private Button invite;
	private Column invitations;
	private Column colUser;
	private Column colButtons;
	private Row row;
	private int it;				//iterator
	
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
		returnButton.setText("REGRESAR");
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
		
		Grid grid = new Grid(3);
		invitations = new Column();
	    colUser = new Column();
	    colButtons = new Column();
	    
	    grid.add(colUser);
	    grid.add(colButtons);
	    grid.add(invitations);
	    
	    UpdateInOfArena();
	    
	    ApplicationInstance app = ApplicationInstance.getActive();
	    inviteServerPush = new TimedServerPush(1000, app, taskQueue, new Runnable() {
	    	@Override
	    	public void run(){
	    		Session session = null;
	    		try {
	    			session = SessionHibernate.getInstance().getSession();
			  	    session.beginTransaction();
			  	      
			  	    usuario = (Usuario) session.load(Usuario.class, usuario.getId());

			  	    Usuario userObj = new Usuario();
			  		userObj.setArena(1);

			  		// FROM UserObj WHERE arena = 1;
			  		results = //
			  		session.createCriteria(Usuario.class).add(Example.create(userObj)).list();
	    		} finally {
	    			if (session != null) {
			  	        if (session.getTransaction() != null) {
			  	          session.getTransaction().commit();
			  	        }
			  	        session.close();
			  	      }
	    		}
	    		colUser.removeAll();
	    		colButtons.removeAll();
	    		
	    		for (int i = 0; i < results.size(); i++) {
	    			row = new Row();

			    	lblData = new Label(results.get(i).getLogin());
			    	lblData.setForeground(Color.WHITE);
			    	colUser.add(lblData);
			    	colUser.setCellSpacing(new Extent(25));
			    	invite = new Button("Invitar");
			    	invite.setStyle(Styles1.DEFAULT_STYLE);
			    	final Invitation invitation = new Invitation();
			    	invitation.setUserGeneratesRef(usuario);
			    	invitation.setUserReceivesRef(results.get(i));
			    	invite.setToolTipText("Luchar");
			    	invite.addActionListener(new ActionListener() {
			          public void actionPerformed(ActionEvent evt) {
			            btnInviteClicked(invitation);
			          }

			        });
			    	if(usuario.getLogin() == results.get(i).getLogin()){
			    		invite.setEnabled(false);
			    		invite.setFocusedForeground(Color.BLUE);
			    	}
			    	colButtons.add(invite);
	    			colButtons.setCellSpacing(new Extent(20));
	    		}
	    		consultInvitations();
	    	}
	    });
		
	    activate();
	    
		grid.setLayoutData(hld);
		grid.setColumnWidth(0, new Extent(50));
		grid.setColumnWidth(1, new Extent(50));
		grid.setColumnWidth(2, new Extent(350));
		
		htmlLayout.add(grid);
		
		return htmlLayout;		
	}
	
	private void activate(){
		inviteServerPush.beg();
		
	}
	
	private void consultInvitations(){
		
	    Session session = null;
	    try {
	      session = SessionHibernate.getInstance().getSession();
	      session.beginTransaction();
	      
	      usuario = (Usuario) session.load(Usuario.class, usuario.getId());

	      Invitation obj = new Invitation();
		  obj.setUserReceivesRef(usuario);

		  resultsI = //
		  session.createCriteria(Invitation.class).add(Example.create(obj)).list();
		  
	    } finally {

	      // ----------------------------------------
	      // whatever happens, always close
	      // ----------------------------------------

	      if (session != null) {
	        if (session.getTransaction() != null) {
	          session.getTransaction().commit();
	        }

	        session.close();
	      }
	    }
	    invitations.removeAll();
   	  	for( int i = 0; i < resultsI.size(); i++){
   	  		if(resultsI.get(i).getUserGeneratesRef().getLogin() != usuario.getLogin()){
	   	  		row = new Row();
	   	  		Label lbl = new Label("Usuario   : "+resultsI.get(i).getUserGeneratesRef().getLogin());
	   	  		lbl.setForeground(Color.WHITE);
	   	  		row.add(lbl);
	   	  		Button aceptar = new Button("Aceptar");
	   	  		aceptar.setStyle(Styles1.DEFAULT_STYLE);
	   	  		aceptar.setToolTipText("Aceptar la invitacion");
	   	  		aceptar.addActionListener(new ActionListener() {
	          	public void actionPerformed(ActionEvent evt) {
	          		inviteServerPush.end();
	          		removeAll();
	          		add(new ArenaDesktop(usuario));
	          		}
	
	   	  		});
	   	  		row.add(aceptar);
	   	  		invitations.add(row);
	   	  		invitations.setInsets(new Insets(new Extent(110)));
   	  		}
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
	
	private void UpdateInOfArena() {
			
		    Session session = null;

		    try {
		      session = SessionHibernate.getInstance().getSession();
		      session.beginTransaction();
		      
		      usuario = (Usuario) session.load(Usuario.class, usuario.getId());

		      usuario.setArena(1);

		      session.update(usuario);
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
	    Session session = null;

	    try {
	      session = SessionHibernate.getInstance().getSession();
	      session.beginTransaction();
	      
	      usuario = (Usuario) session.load(Usuario.class, usuario.getId());

	      usuario.setArena(0);
	      Invitation obj = new Invitation();
		  obj.setUserReceivesRef(usuario);

		  resultsI = //
		  session.createCriteria(Invitation.class).add(Example.create(obj)).list();
		  for(int i = 0; i < resultsI.size(); i++){
			  if(resultsI.get(i).getUserGeneratesRef() == usuario ){
				  session.delete(resultsI.get(i));
			  }
		  }
	      
	      session.update(usuario);	      
	    } finally {
	      if (session != null) {
	        if (session.getTransaction() != null) {
	          session.getTransaction().commit();
	        }
	        session.close();
	      }
	    }
}
	
	private void buttonExitClicked(ActionEvent e) {	
		UpdateOutOfArena();
		removeAll();
		add(new MapaDesktop(usuario));
	}


}
