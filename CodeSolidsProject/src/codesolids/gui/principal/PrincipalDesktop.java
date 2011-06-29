package codesolids.gui.principal;

import codesolids.gui.arena.*;
import codesolids.gui.validacion.*;
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
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;


/**
 * 
 * @author = Jose Luis Perez M.
 * 
 */


public class PrincipalDesktop extends ContentPane {

	private HtmlLayout htmllayaut;

	// --------------------------------------------------------------------------------

	public PrincipalDesktop(){
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
		TextField log = new TextField();
		row.add(log);
		col.add(row);
		
		row = new Row();
		la = new Label("Password");
		la.setForeground(Color.WHITE);
		row.add(la);
		PasswordField pass = new PasswordField();
		row.add(pass);
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
		removeAll();
		add(new MapaDesktop());
	}
	
	private void btnHistoriaClicked() {
		Info inf = new Info();
		add(inf.initHistoria());
		
	}
	
	private void btnRegistroClicked(){

		add(new Registro());
		
	}
	
}

