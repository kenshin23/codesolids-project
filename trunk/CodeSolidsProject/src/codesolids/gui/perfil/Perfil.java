/*
 * Created on 14/06/2011
 */
package codesolids.gui.perfil;

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
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.informagen.echo.app.CapacityBar;

import codesolids.bd.clases.Nivel;
import codesolids.bd.clases.Personaje;
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
	private Panel descrip = new Panel();
	List<Personaje> results = new ArrayList<Personaje>();
	
	private HtmlLayout htmlLayout;
	private List<Number> listNumber;
	private CapacityBar barraHp;
	private CapacityBar barraMp;
	private CapacityBar barraXp;
	
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
  	    descrip.add(datos());
		rowCentral.add(descrip);
		rowCentral.setLayoutData(hld);
		htmlLayout.add(rowCentral);		
		
		return htmlLayout;
	}
	
	public Panel datos(){
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		session.getTransaction().commit();
	    session.close();
		
		Panel panel = new Panel();
		panel.setInsets(new Insets(10, 45, 10, 10));
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/cartel3.png");
		FillImage imgF = new FillImage(imgR);
		panel.setWidth(new Extent(950));
		panel.setHeight(new Extent(350));
		panel.setBackgroundImage(imgF);
		
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/pto.png");
		imgF = new FillImage(imgR);
		
		Column colCentral = new Column();
		Row rowCentral = new Row();

		Grid grid = new Grid(3);
		grid.setBackground(Color.WHITE);
		grid.setInsets(new Insets(0, 2, 90, 2));
		
		Label lbl = new Label("Datos Generales");		
		grid.add(lbl);
		lbl = new Label(" ");
		grid.add(lbl);
		lbl = new Label(" ");
		grid.add(lbl);
		lbl = new Label("Usuario  " +usuario.getLogin(), imgR);
		grid.add(lbl);
		lbl = new Label("Nivel  " +personaje.getLevel(), imgR);
		grid.add(lbl);
		lbl = new Label("Oro  " +personaje.getGold(), imgR);
		grid.add(lbl);
		lbl = new Label("Id  " +personaje.getId(), imgR);
		grid.add(lbl);
		lbl = new Label("Tipo  " +personaje.getTipo(), imgR);
		grid.add(lbl);
		
		colCentral.add(grid);
		
		Column colBar = new Column();
		colBar.setInsets(new Insets(0, 0, 0, 50));
		colBar.setCellSpacing(new Extent(4));
		Row rowBar = new Row();
		
		Panel panelImage = new Panel();
	    ImageReference ir = ImageReferenceCache.getInstance().getImageReference(personaje.getDirImage());
		
		Label lblImage = new Label(ir);
		panelImage.add(lblImage);
		panelImage.setHeight(new Extent(255));
		panelImage.setWidth(new Extent(200));
		
		rowCentral.add(panelImage);
		
		rowBar.setCellSpacing(new Extent(10));
		rowBar.add(new Label("XP"));
		barraXp = createBar(Color.GREEN,Color.WHITE,personaje.getXp(), consultXp(personaje.getLevel()) - personaje.getXp());
		rowBar.add(barraXp);
		rowBar.add(new Label(consultXp(personaje.getLevel())+"/"+personaje.getXp()));
		colBar.add(rowBar);
		
		rowBar = new Row();
		rowBar.setCellSpacing(new Extent(10));
		rowBar.add(new Label("HP"));
		barraHp = createBar(Color.RED,Color.WHITE,personaje.getHp(),0);
		rowBar.add(barraHp);
		rowBar.add(new Label(personaje.getHp()+"/"+barraHp.getValues().get(0).intValue()));
		colBar.add(rowBar);
		
		rowBar = new Row();
		rowBar.setCellSpacing(new Extent(10));
		rowBar.add(new Label("MP"));
		barraMp = createBar(Color.BLUE,Color.WHITE,personaje.getMp(),0);
		rowBar.add(barraMp);
		rowBar.add(new Label(personaje.getMp()+"/"+barraMp.getValues().get(0).intValue()));
		colBar.add(rowBar);
		
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
		rowBar = new Row();
		rowBar.add(btnItem);
		rowBar.setInsets(new Insets(25, 15, 0, 0));
		rowBar.setCellSpacing(new Extent(10));
		rowBar.add(btnPoderes);
		colBar.add(rowBar);
		
		Grid gridAt = new Grid(3);
		gridAt.setInsets(new Insets(0, 2, 10, 0));
		
		TextField txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/mas.png");
		
		lbl = new Label("Vida");
		gridAt.add(lbl);
		txtData.setText(""+personaje.getHp());
		gridAt.add(txtData);
		Button btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirHpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Psinergia");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setText(""+personaje.getMp());
		gridAt.add(txtData);
		btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirMpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Defensa");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setText(""+personaje.getDefensa());
		gridAt.add(txtData);
		btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirDpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Velocidad");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setText(""+personaje.getSpeed());
		gridAt.add(txtData);
		btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirSpClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Ataque Básico");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setMaximumLength(4);
		txtData.setText(""+personaje.getAtaqueBasico());
		gridAt.add(txtData);
		btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirAbClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Ataque Especial");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setMaximumLength(4);
		txtData.setText(""+personaje.getAtaqueEspecial());
		gridAt.add(txtData);
		btnS = new Button();
		btnS.setIcon(imgR);
		btnS.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnS.setWidth( new Extent(10));
		btnS.setToolTipText("Incrementar este atributo");
		btnS.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			subirAeClicked(e);				
			}
		});
		if(personaje.getPuntos() > 0)
			btnS.setEnabled(true);
		else if(personaje.getPuntos() == 0)
			btnS.setEnabled(false);

		gridAt.add(btnS);
		
		lbl = new Label("Puntos");
		gridAt.add(lbl);
		txtData = new TextField();
		txtData.setWidth(new Extent(50));
		txtData.setEditable(false);
		txtData.setBackground(Color.WHITE);
		txtData.setToolTipText("Puntos Disponibles");
		txtData.setText(""+personaje.getPuntos());
		gridAt.add(txtData);
		
		rowCentral.add(colBar);
		rowCentral.setCellSpacing(new Extent(20));
		rowCentral.add(gridAt);
		colCentral.add(rowCentral);
		panel.add(colCentral);
		
		return panel;
	}
	
	public CapacityBar createBar(Color color1, Color color2, int indice1,int indice2){

		CapacityBar barra = new CapacityBar(13, 120); 
		barra.setShowTicks(false);
		barra.setReflectivity(0.1);
		List<Color> listColor = new ArrayList<Color>();
		listColor.add(color1);
		listColor.add(color2);
		barra.setColors(listColor);
		listNumber = new ArrayList<Number>();
		listNumber.add(indice1);
		listNumber.add(indice2); 
		barra.setValues(listNumber);     

		return barra;
	}
	
	@SuppressWarnings("unchecked")
	private int consultXp(int num){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Nivel> list = session.createCriteria(Nivel.class).add(Restrictions.gt("level", num)).list();
		session.getTransaction().commit();
		session.close();
		
		return list.get(0).getCantidadExp();		
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
		descrip.add(datos());
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
		descrip.add(datos());
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
		descrip.add(datos());
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
		descrip.add(datos());
	}
	
	private void subirAbClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setAtaqueBasico(personaje.getAtaqueBasico() + 0.05);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(datos());
	}
	
	private void subirAeClicked(ActionEvent e){
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		personaje.setAtaqueEspecial(personaje.getAtaqueEspecial() + 0.05);
		personaje.setPuntos(personaje.getPuntos() - 1);
		
		session.getTransaction().commit();
	    session.close();
		
		descrip.remove(0);
		descrip.add(datos());
	}
	
	private void buttonExitClicked(ActionEvent e) {
		removeAll();
		add(new MapaDesktop());
	}
	
}