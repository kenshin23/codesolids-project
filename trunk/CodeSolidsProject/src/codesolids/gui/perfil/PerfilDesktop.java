/*
 * Created on 14/06/2011
 */
package codesolids.gui.perfil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.bd.clases.Poderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Fernando Osuna
 *
 */

@SuppressWarnings("serial")
public class PerfilDesktop extends ContentPane{
	private Usuario usuario;
	private Personaje personaje;
	private Label lblData;
	Panel descrip = new Panel();
	List<Personaje> results = new ArrayList<Personaje>();
	
	private HtmlLayout htmlLayout;
	
	public PerfilDesktop() {
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		usuario = app.getUsuario();
		personaje = app.getPersonaje();
	    initGUI();
	}
	
	private void initGUI() {
		add(initPerfil());
	}

	private Component initPerfil(){
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//		Session session = SessionHibernate.getInstance().getSession();
//  	    session.beginTransaction();
//  		String queryStr = "FROM Personaje WHERE usuarioRef = :user";
//  		Query query  = session.createQuery(queryStr);
//  		query.setInteger("user", usuario.getId());
//  		
//  		personaje = (Personaje) query.list().get(0);
//  		
//  		session.getTransaction().commit();			  	        
//  	    session.close();
		
		
		
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("head");		
		Row menu = new Row();
		
		Button returnButton = new Button();		
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
		
		hld = new HtmlLayoutData("body");
		
		Row rowCentral = new Row();
  	    descrip.add(btnSeeClicked());
		rowCentral.add(descrip);
		rowCentral.setLayoutData(hld);
		htmlLayout.add(rowCentral);		
		
		return htmlLayout;
	}

	public Panel btnSeeClicked() {

		Panel panel = new Panel();
		panel.setInsets(new Insets(20, 35, 20, 20));
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = new ResourceImageReference("Images/cartel3.png");
		FillImage imgF = new FillImage(imgR);
		panel.setWidth(new Extent(950));
		panel.setHeight(new Extent(350));
		panel.setBackgroundImage(imgF);

		
		Panel panelImage= new Panel();
		Row rowTab = new Row();
		rowTab.setInsets(new Insets(20, 35, 20, 20));
		Column colTab = new Column();
		colTab.setCellSpacing(new Extent(4));
		Column col = new Column();
		col.setCellSpacing(new Extent(9));
		
	    ResourceImageReference ir = new ResourceImageReference(personaje.getDirImage());
		
		Label lblImage = new Label(ir);
		panelImage.add(lblImage);
		panelImage.setHeight(new Extent(255));
		panelImage.setWidth(new Extent(200));

		lblData = new Label("Datos Generales ");
		lblData.setFont(new Font(null, 1 , new Extent(12)));
		colTab.add(lblData);
		lblData = new Label("Usuario " + usuario.getLogin());
		colTab.add(lblData);
		lblData = new Label("Tipo " + personaje.getTipo());
		colTab.add(lblData);
		lblData = new Label("Nivel " + personaje.getLevel());
		colTab.add(lblData);
		lblData = new Label("XP " + personaje.getXp());
		colTab.add(lblData);
		lblData = new Label("Oro " + personaje.getGold());
		colTab.add(lblData);
		Button btnItem = new Button();
		btnItem.setText("Objetos");
		btnItem.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnItem.setHeight(new Extent(15));
		btnItem.setWidth( new Extent(60));
		btnItem.setToolTipText("Ver sus Objetos");
		btnItem.setStyle(Styles1.DEFAULT_STYLE);
		btnItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
//			buttonItemClicked(e);				
			}
		});
		colTab.add(btnItem);
		Button btnPoderes = new Button();
		btnPoderes.setText("Poderes");
		btnPoderes.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnPoderes.setHeight(new Extent(15));
		btnPoderes.setWidth( new Extent(60));
		btnPoderes.setToolTipText("Ver sus Poderes");
		btnPoderes.setStyle(Styles1.DEFAULT_STYLE);
		btnPoderes.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buttonPoderesClicked(e);				
			}

		private void buttonPoderesClicked(ActionEvent e) {
			final WindowPane win = new WindowPane();
			win.setTitle("Poderes que Posee");
			win.setWidth(new Extent(300));
			win.setMaximumWidth(new Extent(200));
			win.setMaximumHeight(new Extent(300));
			win.setMovable(false);
			win.setResizable(false);
			win.setPositionX(new Extent(1000));
			Column col = new Column();
			
			Session session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();
			String queryStr = "SELECT personajePoderesList AS pp FROM Personaje WHERE personajeref_id = :idPlayer";
			Query query  = session.createQuery(queryStr);
			query.setInteger("idPlayer", personaje.getId());
			List<Object> listQuery = query.list();
			createListPoderes(listQuery, col);			
			
			session.getTransaction().commit();			  	        
			session.close();
			
			Button button = new Button();
			button.setText("Aceptar");
			button.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			button.setHeight(new Extent(15));
			button.setWidth( new Extent(60));
			button.setToolTipText("Regresar al perfil");
			button.setStyle(Styles1.DEFAULT_STYLE);
			button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				win.userClose();
				}
			});
			col.add(button);
			col.setInsets(new Insets(25));
			win.add(col);
			add(win);
			
		}
		});
		colTab.add(btnPoderes);
		rowTab.add(panelImage);
		rowTab.setCellSpacing(new Extent(15));
		rowTab.add(colTab);
		
		lblData = new Label("Atributos Generales");
		lblData.setFont(new Font(null, 1 , new Extent(12)));
		col.add(lblData);
		lblData = new Label("Vida "+ personaje.getHp());
		col.add(lblData);
		lblData = new Label("Psinergia "+ personaje.getMp());
		col.add(lblData);
		lblData = new Label("Defensa ");
		col.add(lblData);
		lblData = new Label("Velocidad ");
		col.add(lblData);
		lblData = new Label("Ataque Básico ");
		col.add(lblData);
		lblData = new Label("Ataque Especial ");
		col.add(lblData);
		rowTab.add(col);
		
		col = new Column();
		col.setCellSpacing(new Extent(4));
		Row row = new Row();
		lblData = new Label("Subir Ptos - Disponibles 0");
		lblData.setFont(new Font(null, 1 , new Extent(12)));
		
		col.add(lblData);
		Button btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		Button btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row.add(btnS);
		row.add(btnB);
		col.add(row);
		btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row = new Row();
		row.add(btnS);
		row.add(btnB);
		col.add(row);
		btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row = new Row();
		row.add(btnS);
		row.add(btnB);
		col.add(row);
		btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row = new Row();
		row.add(btnS);
		row.add(btnB);
		col.add(row);
		btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row = new Row();
		row.add(btnS);
		row.add(btnB);
		col.add(row);
		btnS = new Button("Subir");
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnB = new Button("Bajar");
		btnB.setStyle(Styles1.DEFAULT_STYLE);
		row = new Row();
		row.add(btnS);
		row.add(btnB);
		col.add(row);

		rowTab.add(col);
		panel.add(rowTab);
		
		return panel;
	}
	
	private void createListPoderes( List<Object> listQuery, Column col){
		Iterator<Object> iter = listQuery.iterator();
  	    if (!iter.hasNext()) {
  	    	lblData = new Label("Aun no tiene poderes");
			col.add(lblData); 
  	    	return;
  	    }
  	    while (iter.hasNext()) {  
  	    	PersonajePoderes p = (PersonajePoderes) iter.next();
  	    	lblData = new Label(p.getPoderesRef().getName());
			col.add(lblData);  	            
  	    }
  	    
	}
	
	private void buttonExitClicked(ActionEvent e) {
		removeAll();
		add(new MapaDesktop());
	}
	
}