package codesolids.gui.principal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;

import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.*;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;


/**
 * 
 * @author = Jose Luis Perez M.
 * 
 */


public class Desktop extends ContentPane {

	private HtmlLayout htmllayaut;

	private TextField textLogin; 
	private PasswordField textPassword;
	
	private Usuario usuario;
	
	// --------------------------------------------------------------------------------

	public Desktop(){
		initGUI();
	}

	// --------------------------------------------------------------------------------

	private void initGUI() {

		add(initPrincipal());

	}

	
	// --------------------------------------------------------------------------------
	
	
	private Component initPrincipal(){
		
		try {
			htmllayaut = new HtmlLayout(getClass().getResourceAsStream("index.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		//Tabla del Login
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("login");
		
		
		Column col = new Column();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(30));
		Label la = new Label("Login");
		la.setForeground(Color.WHITE);
		row.add(la);
		textLogin = new TextField();
		row.add(textLogin);
		col.add(row);
		
		row = new Row();
		la = new Label("Password");
		la.setForeground(Color.WHITE);
		row.add(la);
		textPassword = new PasswordField();
		row.add(textPassword);
		col.add(row);
		
		Button btnAcceder = new Button("Acceder");
		btnAcceder.setStyle(Styles1.DEFAULT_STYLE);
		btnAcceder.setHeight(new Extent(15));
		btnAcceder.setWidth(new Extent(50));
		col.add(btnAcceder);
		btnAcceder.addActionListener(new ActionListener() {
			
	
			public void actionPerformed(ActionEvent arg0) {
				btnAccederClicked();	
			}
		});
		
		col.setLayoutData(hld);
		htmllayaut.add(col);
		
		//Tabla del registro
		
		hld = new HtmlLayoutData("register");
		col = new Column();
		
		Button btnRegistro = new Button("Registrar");
		btnRegistro.setStyle(Styles1.DEFAULT_STYLE);
		btnRegistro.setHeight(new Extent(20));
		btnRegistro.setWidth(new Extent(50));
		
		btnRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRegistroClicked();
				
			}
		});
		
		row = new Row();
		row.add(btnRegistro);
		row.setAlignment(Alignment.ALIGN_CENTER);
		col.add(row);
		
		col.setLayoutData(hld);
		
		htmllayaut.add(col);
		
		//Tabla de la informacion
		
		hld = new HtmlLayoutData("info");
		
		row = new Row();
		row.setCellSpacing(new Extent(30)); 
		Button btnHistoria = new Button("Historia");
		btnHistoria.setStyle(Styles1.DEFAULT_STYLE);
		btnHistoria.setHeight(new Extent(15));
		btnHistoria.setWidth(new Extent(65));
		btnHistoria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnHistoriaClicked();
				
			}
		});
		
		row.add(btnHistoria);
		
		
		Button btnPersonajes = new Button("Personajes");
		btnPersonajes.setStyle(Styles1.DEFAULT_STYLE);
		btnPersonajes.setHeight(new Extent(15));
		btnPersonajes.setWidth(new Extent(65));
		row.add(btnPersonajes);
		
		Button btnObjetos = new Button("Objetos");
		btnObjetos.setStyle(Styles1.DEFAULT_STYLE);
		btnObjetos.setHeight(new Extent(15));
		btnObjetos.setWidth(new Extent(65));
		row.add(btnObjetos);
		
		row.setLayoutData(hld);
		
		htmllayaut.add(row);
		
		return htmllayaut;
		
	}
	
	

	// --------------------------------------------------------------------------------
	
	
	private void btnAccederClicked() {
		
		if(!validateCampo())
		{
			return;
		}
		
		Session session = null;
		
		try
		{
			session = SessionHibernate.getInstancia().getSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Usuario.class).add(//
								Restrictions.and(Restrictions.eq("login", textLogin.getText()),//
												 Restrictions.eq("password", textPassword.getText())));
		
			usuario = (Usuario) criteria.uniqueResult();
			
			if (usuario == null)
			{
				//Esto no se deberia hacer, hay que hacer una clases para ventanas de mensajes
				//Por lo rapido lo hice asi. XD
				WindowPane ventanaEmergente = new WindowPane();
				ventanaEmergente.setTitle("Error!");
				
				ventanaEmergente.add(new Label("Login o Contraseña incorrectos"));
				add(ventanaEmergente);

			}
			else
			{
				removeAll();
				add(new MapaDesktop());
			}
			
		}finally
		{
			if(session != null)
			{
				if(session.getTransaction() != null)
				{
					session.getTransaction().commit();
				}
				
				session.close();
			}
		}
		
	}
	
	private void btnHistoriaClicked() {
		Info inf = new Info();
		add(inf.initHistoria());
		
	}
	
	private void btnRegistroClicked(){

		add(new Registro());
		
	}
	
	private boolean validateCampo()
	{
		boolean ok = true;

		ok &= !textLogin.getText().equals("");
		ok &= !textPassword.getText().equals("");

		if(ok)
		{
			return true;
		}
		
		
		//Esto no se deberia hacer, hay que hacer una clases para ventanas de mensajes
		//Por lo rapido lo hice asi. XD
		WindowPane ventanaEmergente = new WindowPane();
		ventanaEmergente.setTitle("Campos Vacios!");
		
		ventanaEmergente.add(new Label("Por favor ingrese todos sus datos, faltan algunos campos por rellenar"));
		add(ventanaEmergente);
		
		return false;
	}
	
	
}

