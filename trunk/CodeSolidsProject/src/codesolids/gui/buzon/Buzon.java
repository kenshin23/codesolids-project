package codesolids.gui.buzon;

import java.util.Calendar;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Clan;
import codesolids.bd.clases.Mensaje;
import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;

/**
 * 
 * @author Antonio López
 *
 */

public class Buzon extends WindowPane{

	public Buzon()
	{
		initGUI();
	}

	private void initGUI() {
		
		this.setTitle("Buzón de Mensajes");
		this.setStyle(StyleWindow.DEFAULT_STYLE);
		this.setWidth(new Extent(710));
		this.setHeight(new Extent(312));
		this.setMovable(false);
		this.setResizable(false);
		this.setModal(true);
		
		this.add(buzonMensaje());
	}

	private Column buzonMensaje() {
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(10, 10, 10, 10));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		Personaje personaje =  app.getPersonaje();
		
		Criteria criteria = session.createCriteria(Mensaje.class).add(Restrictions.eq("personajeReceivesRef", personaje));
		List<Mensaje> msgList = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		for( int i = 0; i < msgList.size(); i++ )
			col.add(rowsBuzonMensaje(msgList.get(i)));
		
		return col;
	}

	private Row rowsBuzonMensaje(Mensaje msgBuzon) {
		
		Row rowMensaje = new Row();
		rowMensaje.setCellSpacing(new Extent(300));
		
		rowMensaje.add(rowsAsunto(msgBuzon));
		rowMensaje.add(rowsBotonera(msgBuzon));
		
		Row row = new Row();
		row.setCellSpacing(new Extent(10));
		row.setInsets(new Insets(5, 5, 5, 5));
		row.setBorder(new Border(2,new Color(25, 54, 65),Border.STYLE_OUTSET));
		
		row.add(new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/sobre-carta.png")));
		row.add(rowMensaje);
		
		return row;
	}
	
	private Row rowsAsunto(Mensaje msgBuzon)
	{
		Row row = new Row();
		row.setCellSpacing(new Extent(20));
		
		row.add(new Label("" + msgBuzon.getDateSend().get(Calendar.DAY_OF_MONTH) + "/" + msgBuzon.getDateSend().get(Calendar.MONTH) + "/" + msgBuzon.getDateSend().get(Calendar.YEAR)));
		
		if ( msgBuzon.getPersonajeReceivesRef().getClanRef() == null )
			row.add(new Label("Asunto: Invitación Clan"));
		else
			row.add(new Label("Asunto: Solicitud Vacante"));
		
		return row;
	}
	
	private Row rowsBotonera(final Mensaje msgBuzon)
	{
		Row row = new Row();
		row.setCellSpacing(new Extent(3));
		
		Button btnLeer = new Button("Leer");
		btnLeer.setStyle(Styles1.DEFAULT_STYLE);
		btnLeer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnLeerClicked(msgBuzon);
			}
		});
		row.add(btnLeer);
		
		Button btnDelete = new Button("Borrar");
		btnDelete.setStyle(Styles1.DEFAULT_STYLE);
		btnDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnDeleteClicked(msgBuzon);
			}
		});
		row.add(btnDelete);
		
		return row;
	}
	
	private void btnLeerClicked(Mensaje msgBuzon) {
		
		this.removeAll();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		msgBuzon = (Mensaje) session.load(Mensaje.class, msgBuzon.getId());

		if( !msgBuzon.getLeido() )
			msgBuzon.setLeido(true);
			
		session.getTransaction().commit();
		session.close();
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(0, 60, 0, 40));
		
		col.add(mensajePersonaje(msgBuzon));
		col.add(botonesMensaje(msgBuzon));
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		this.add(col);
		
	}
	
	private void btnDeleteClicked(Mensaje msgBuzon) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		msgBuzon = (Mensaje) session.load(Mensaje.class, msgBuzon.getId());

		session.delete(msgBuzon);
		
		session.getTransaction().commit();
		session.close();
		
		this.removeAll();
		this.add(buzonMensaje());
	}
	
	private Column mensajePersonaje(Mensaje mensaje)
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		panel.setWidth(new Extent(360));
		panel.setHeight(new Extent(70));
		panel.setBorder(new Border(2, Color.BLACK, Border.STYLE_SOLID));
		
		TextArea msgBuzon = new TextArea();
		msgBuzon.setInsets(new Insets(3, 3, 3, 3));
		msgBuzon.setBorder(new Border(0, Color.BLACK, Border.STYLE_NONE));
		msgBuzon.setWidth(new Extent(360));
		msgBuzon.setHeight(new Extent(70));
		msgBuzon.setEditable(false);
		msgBuzon.setDisabledBackground(Color.WHITE);		
		
		String str;
		if( mensaje.getPersonajeReceivesRef().getClanRef() == null )
			str = "Hola " + "\"" + mensaje.getPersonajeReceivesRef().getUsuarioRef().getLogin() + "\"" + ", El lider " + "\"" + mensaje.getPersonajeSendRef().getUsuarioRef().getLogin() + "\"" + " del Clan " + mensaje.getPersonajeSendRef().getClanRef().getNameClan() + " te ha enviado una invitación para unirte a su clan.";
		else
			str = "Hola " + "\"" + mensaje.getPersonajeReceivesRef().getUsuarioRef().getLogin() + "\"" + ", El personaje " + "\"" + mensaje.getPersonajeSendRef().getUsuarioRef().getLogin() + "\"" + " te ha enviado una solicitud de vancante para pertenecer a tu clan " + mensaje.getPersonajeReceivesRef().getClanRef().getNameClan() + ".";
		
		msgBuzon.setText(str);
		panel.add(msgBuzon);
		
		Column col = new Column();
		col.setInsets(new Insets(10, 10, 10, 10));
		
		col.add(panel);
		
		return col;
	}
	
	private Row botonesMensaje(final Mensaje msgBuzon)
	{
		Row row = new Row();
		row.setCellSpacing(new Extent(3));
		
		Button btnAceptar = new Button("Aceptar");
		btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
		btnAceptar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnAceptarClicked(msgBuzon);
			}
		});
		row.add(btnAceptar);
		
		Button btnRegresar = new Button("Regresar");
		btnRegresar.setStyle(Styles1.DEFAULT_STYLE);
		btnRegresar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnRegresarClicked();
			}
		});
		row.add(btnRegresar);
		
		return row;
	}
	
	private void btnAceptarClicked(Mensaje msgBuzon) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		msgBuzon = (Mensaje) session.load(Mensaje.class, msgBuzon.getId());
		
		if( msgBuzon.getPersonajeSendRef().getClanRef() == null )
		{
			Clan clan = (Clan) session.load(Clan.class, msgBuzon.getPersonajeReceivesRef().getClanRef().getId());
			
			if( clan.getCantPersonaje() < clan.getLimite() )
			{
				clan.setCantPersonaje(clan.getCantPersonaje() + 1);
				
				Personaje personaje = (Personaje) session.load(Personaje.class, msgBuzon.getPersonajeSendRef().getId());
				personaje.setClanRef(clan);
				
				this.removeAll();
				this.add(buzonMensaje());
				
			}
			else
			{
				String str = "La cantidad de miembros del clan ya esta en su limite.";
				
				this.removeAll();
				this.add(warningMensaje(str));
			}
		}
		else
		{
			Clan clan = (Clan) session.load(Clan.class, msgBuzon.getPersonajeSendRef().getClanRef().getId());
			
			if( msgBuzon.getPersonajeReceivesRef().getClanRef() == null )
			{
				if( clan.getCantPersonaje() < clan.getLimite() )
				{
					clan.setCantPersonaje(clan.getCantPersonaje() + 1);
					
					Personaje personaje = (Personaje) session.load(Personaje.class, msgBuzon.getPersonajeReceivesRef().getId());
					personaje.setClanRef(clan);
					
					this.removeAll();
					this.add(buzonMensaje());
					
				}
				else
				{
					String str = "La cantidad de miembros del clan ya esta en su limite.";
					
					this.removeAll();
					this.add(warningMensaje(str));
				}
			}
			else
			{
				String str = "Este personaje ya pertenece a un clan.";
				
				this.removeAll();
				this.add(warningMensaje(str));				
			}
		}
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	private void btnRegresarClicked() {
		
		this.removeAll();
		this.add(buzonMensaje());
		
	}
	
	private Column warningMensaje(String texto)
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		panel.setInsets(new Insets(50, 80, 50, 50));
		
		Column col = new Column();
		col.setInsets(new Insets(10, 10, 10, 10));
		col.setCellSpacing(new Extent(25));
		
		col.add(new Label(texto));
		
		Button btnOk = new Button("Aceptar");
		btnOk.setStyle(Styles1.DEFAULT_STYLE);
		btnOk.setWidth(new Extent(50));
		
		btnOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnOkClicked();
			}
		});
		col.add(btnOk);
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private void btnOkClicked() {
		
		this.removeAll();
		this.add(buzonMensaje());
		
	}
	
}
