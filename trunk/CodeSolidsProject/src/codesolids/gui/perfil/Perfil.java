/*
 * Created on 14/06/2011
 */
package codesolids.gui.perfil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.inventario.DesktopItem;
import codesolids.gui.inventario.DesktopPoder;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Fernando Osuna
 *
 */

@SuppressWarnings("serial")
public class Perfil extends ContentPane{
	private Usuario usuario;
	private Personaje personaje;
	private Label lblData;
	Panel descrip = new Panel();
	List<Personaje> results = new ArrayList<Personaje>();
	
	private HtmlLayout htmlLayout;
	
	public Perfil() {
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
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		session.getTransaction().commit();
	    session.close();

		Panel panel = new Panel();
		panel.setInsets(new Insets(20, 35, 20, 20));
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/cartel3.png");
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
		
	    ImageReference ir = ImageReferenceCache.getInstance().getImageReference(personaje.getDirImage());
		
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
			buttonItemClicked(e);				
			}

		private void buttonItemClicked(ActionEvent e) {
			removeAll();
			add(new DesktopItem());
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
		
			private void buttonPoderesClicked(ActionEvent e) 
			{
				removeAll();
				add(new DesktopPoder());
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
		lblData = new Label("Defensa " + personaje.getDefensa());
		col.add(lblData);
		lblData = new Label("Velocidad " + personaje.getSpeed());
		col.add(lblData);
		lblData = new Label("Ataque Básico "+ personaje.getAtaqueBasico());
		col.add(lblData);
		lblData = new Label("Ataque Especial "+personaje.getAtaqueEspecial());
		col.add(lblData);
		rowTab.add(col);
		
		col = new Column();
		col.setCellSpacing(new Extent(4));

		lblData = new Label("Subir Ptos - Disponibles " +personaje.getPuntos());
		lblData.setFont(new Font(null, 1 , new Extent(12)));
		
		col.add(lblData);
		Button btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirHpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);
		btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirMpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);
		btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirDpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);
		btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirSpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);
		btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirAbClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);
		btnS = new Button("Subir");
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(40));
		btnS.setStyle(Styles1.DEFAULT_STYLE);
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirAeClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		col.add(btnS);

		rowTab.add(col);
		panel.add(rowTab);
		
		return panel;
	}
	
	private void subirHpClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setHp(personaje.getHp() + 10);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void subirMpClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setMp(personaje.getMp() + 5);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void subirDpClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setDefensa(personaje.getDefensa() + 1);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void subirSpClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setSpeed(personaje.getSpeed() + 10);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void subirAbClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
//		personaje.setAtaqueBasico(personaje.getAtaqueBasico() + 5/100);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void subirAeClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
//		personaje.setAtaqueEspecial(personaje.getAtaqueEspecial() + 5/100);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(btnSeeClicked());
	}
	
	private void buttonExitClicked(ActionEvent e) {
		removeAll();
		add(new MapaDesktop());
	}
	
}