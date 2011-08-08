package codesolids.gui.mapa;

/*
 * @autor Hector PRada
 * 
 * */

import java.util.ArrayList;
import java.util.Calendar;
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
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.informagen.echo.app.CapacityBar;

import codesolids.bd.clases.Nivel;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.academia.AcademiaDesktop;
import codesolids.gui.arena.PreArena;
import codesolids.gui.chat.ChatGui;
import codesolids.gui.editar.EditarDatos;
import codesolids.gui.mision.Mision;
import codesolids.gui.perfil.PerfilDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.principal.PrincipalDesktop;
import codesolids.gui.ranking.Ranking;
import codesolids.gui.style.Styles1;
import codesolids.gui.tienda.Tienda;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageBox;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

@SuppressWarnings("serial")
public class MapaDesktop extends ContentPane {

	private Usuario usuario;

	private Personaje personaje;

	public MapaDesktop() {
		super();
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		usuario = app.getUsuario();
		
		personaje = app.getPersonaje();
		
		initGUI();
	}

	 private void initGUI() {
	    add(initMapa());
	 }

	 private Component initMapa(){
		
		 HtmlLayout htmlLayout; 

		 try {
			 htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateMapa.html"), "UTF-8");
		 } catch (Exception e) {
			 throw new RuntimeException(e);
		 }
		 
		 HtmlLayoutData hld;

		 ImageReference perfil_des = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/perfil_sel.png");
		 ImageReference perfil_sel = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/perfil_des.png");
		 ImageReference academia_sel = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/academia_sel.png");
		 ImageReference academia_des = ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/academia_des.png");
		 ImageReference ranking_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/ranking_sel.png");
		 ImageReference ranking_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/ranking_des.png");
		 ImageReference arena_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/arena_sel.png");
		 ImageReference arena_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/arena_des.png");
		 ImageReference clanes_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/clanes_sel.png");
		 ImageReference clanes_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/clanes_des.png");
		 ImageReference tienda_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/tienda_sel.png");
		 ImageReference tienda_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/tienda_des.png");
		 ImageReference chat_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/chat_sel.png");
		 ImageReference chat_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/chat_des.png");
		 ImageReference misiones_sel =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/misiones_sel.png");
		 ImageReference misiones_des =  ImageReferenceCache.getInstance().getImageReference("/Images/Mapa/misiones_des.png");


		 hld = new HtmlLayoutData("info");
		 Column col = new Column();
		 col.add(panelInfo());
		 col.setLayoutData(hld);
		 htmlLayout.add(col);
		  
		 hld = new HtmlLayoutData("botonPerfil");
		 Button perfilBtn = new Button();
		 perfilBtn.setEnabled(true);
		 perfilBtn.setVisible(true);
		 perfilBtn.setRolloverEnabled(true);
		 perfilBtn.setHeight(new Extent(80, Extent.PX));
		 perfilBtn.setWidth(new Extent(177, Extent.PX));
		 perfilBtn.setBackgroundImage(new FillImage(perfil_sel));
		 perfilBtn.setRolloverBackgroundImage(new FillImage(perfil_des));
		 perfilBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 perfilBtnClicked(e);
			 }
		 });
		 perfilBtn.setLayoutData(hld);
		 htmlLayout.add(perfilBtn);

		 hld = new HtmlLayoutData("botonAcademia");
		 Button academiaBtn = new Button();
		 academiaBtn.setEnabled(true);
		 academiaBtn.setVisible(true);
		 academiaBtn.setBackgroundImage(new FillImage(academia_des));
		 academiaBtn.setRolloverEnabled(true);
		 academiaBtn.setRolloverBackgroundImage(new FillImage(academia_sel));
		 academiaBtn.setHeight(new Extent(349, Extent.PX));
		 academiaBtn.setWidth(new Extent(329, Extent.PX));
		 academiaBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 academiaBtnClicked();
			 }
		 });
		 academiaBtn.setLayoutData(hld);
		 htmlLayout.add(academiaBtn);

		 hld = new HtmlLayoutData("botonRanking");
		 Button rankingBtn = new Button();
		 rankingBtn.setEnabled(true);
		 rankingBtn.setRolloverBackgroundImage(new FillImage(ranking_sel));
		 rankingBtn.setHeight(new Extent(124, Extent.PX));
		 rankingBtn.setVisible(true);
		 rankingBtn.setBackgroundImage(new FillImage(ranking_des));
		 rankingBtn.setRolloverEnabled(true);
		 rankingBtn.setWidth(new Extent(236, Extent.PX));
		 rankingBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 rankingBtnClicked(e);
			 }
		 });
		 rankingBtn.setLayoutData(hld);
		 htmlLayout.add(rankingBtn);

		 hld = new HtmlLayoutData("botonArena");
		 Button arenaBtn = new Button();
		 arenaBtn.setEnabled(true);
		 arenaBtn.setRolloverBackgroundImage(new FillImage(arena_sel));
		 arenaBtn.setBackgroundImage(new FillImage(arena_des));
		 arenaBtn.setRolloverEnabled(true);
		 arenaBtn.setWidth(new Extent(272, Extent.PX));
		 arenaBtn.setHeight(new Extent(205, Extent.PX));
		 arenaBtn.setVisible(true);
		 arenaBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 arenaBtnClicked(e);
			 }
		 });
		 arenaBtn.setLayoutData(hld);
		 htmlLayout.add(arenaBtn);

		 hld = new HtmlLayoutData("botonClanes");
		 Button clanesBtn = new Button();
		 clanesBtn.setEnabled(true);
		 clanesBtn.setRolloverBackgroundImage(new FillImage(clanes_sel));
		 clanesBtn.setBackgroundImage(new FillImage(clanes_des));
		 clanesBtn.setRolloverEnabled(true);
		 clanesBtn.setHeight(new Extent(80, Extent.PX));
		 clanesBtn.setVisible(true);
		 clanesBtn.setWidth(new Extent(149, Extent.PX));
		 clanesBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 clanesBtnClicked(e);
			 }
		 });
		 clanesBtn.setLayoutData(hld);
		 htmlLayout.add(clanesBtn);

		 hld = new HtmlLayoutData("botonTienda");
		 Button tiendaBtn = new Button();
		 tiendaBtn.setEnabled(true);
		 tiendaBtn.setRolloverBackgroundImage(new FillImage(tienda_sel));
		 tiendaBtn.setBackgroundImage(new FillImage(tienda_des));
		 tiendaBtn.setRolloverEnabled(true);
		 tiendaBtn.setHeight(new Extent(137, Extent.PX));
		 tiendaBtn.setWidth(new Extent(231, Extent.PX));
		 tiendaBtn.setVisible(true);
		 tiendaBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 tiendaBtnClicked(e);
			 }
		 });
		 tiendaBtn.setLayoutData(hld);
		 htmlLayout.add(tiendaBtn);

		 hld = new HtmlLayoutData("botonChat");
		 Button chatBtn = new Button();
		 chatBtn.setEnabled(true);
		 chatBtn.setVisible(true);
		 chatBtn.setBackgroundImage(new FillImage(chat_des));
		 chatBtn.setRolloverEnabled(true);
		 chatBtn.setRolloverBackgroundImage(new FillImage(chat_sel));
		 chatBtn.setHeight(new Extent(47, Extent.PX));
		 chatBtn.setWidth(new Extent(47, Extent.PX));
		 chatBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 chatBtnClicked(e);
			 }
		 });
		 chatBtn.setLayoutData(hld);	
		 htmlLayout.add(chatBtn);

		 hld = new HtmlLayoutData("botonMisiones");
		 Button misionesBtn = new Button();
		 misionesBtn.setEnabled(true);
		 misionesBtn.setVisible(true);
		 misionesBtn.setRolloverEnabled(true);
		 misionesBtn.setHeight(new Extent(205, Extent.PX));
		 misionesBtn.setWidth(new Extent(249, Extent.PX));
		 misionesBtn.setBackgroundImage(new FillImage(misiones_des));
		 misionesBtn.setRolloverBackgroundImage(new FillImage(misiones_sel));
		 misionesBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 misionesBtnClicked();
			 }
		 });
		 misionesBtn.setLayoutData(hld);
		 htmlLayout.add(misionesBtn);

		 hld = new HtmlLayoutData("botonLogout");

		 Row row = new Row();
		 row.setCellSpacing(new Extent(20));

		 Button btnEditarDatos = new Button("Editar Datos");
		 btnEditarDatos.setStyle(Styles1.DEFAULT_STYLE);
		 btnEditarDatos.setHeight(new Extent(15));
		 btnEditarDatos.setWidth(new Extent(73));    
		 btnEditarDatos.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent arg0) {
				 btnEditarDatosClicked();

			 }
		 });
		 row.add(btnEditarDatos);

		 Button logoutBtn = new Button("Cerrar Sesion");
		 logoutBtn.setEnabled(true);
		 logoutBtn.setVisible(true);
		 logoutBtn.setStyle(Styles1.DEFAULT_STYLE);
		 logoutBtn.setHeight(new Extent(15, Extent.PX));
		 logoutBtn.setWidth(new Extent(80, Extent.PX));
		 logoutBtn.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 logoutBtnClicked(e);
			 }
		 });
		 row.add(logoutBtn);

		 row.setLayoutData(hld);
		 htmlLayout.add(row);

		 validateHabilidad();
		 
		 return htmlLayout;
	 }

	 //funciones para los actionlistener
	 private void arenaBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new PreArena());
	 }

	 private void logoutBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new PrincipalDesktop());
	 }

	 private void btnEditarDatosClicked() {
		 add(new EditarDatos());
	 }

	 private void perfilBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new PerfilDesktop());
	 }

	 private void rankingBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new Ranking());
	 }

	 private void clanesBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new Clanes());
	 }

	 private void tiendaBtnClicked(ActionEvent e) {
		 removeAll();
		 add(new Tienda());
	 }

	 private void academiaBtnClicked() {
		 removeAll();
		 add(new AcademiaDesktop());
	 }

	 private void misionesBtnClicked() {
		 removeAll();
		 add(new Mision());
	 }

	 private void chatBtnClicked(ActionEvent e) {

		 Session session = null;

		 session = SessionHibernate.getInstance().getSession();
		 session.beginTransaction();


		 PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
		 Usuario usuario = pa.getUsuario();


		 Criteria criteria = session.createCriteria(Usuario.class).add(//
				 Restrictions.eq("login", usuario.getLogin()));

		 usuario = (Usuario) criteria.uniqueResult();

		 usuario.setActivo(true);

		 session.getTransaction().commit();
		 session.close();


		 removeAll();
		 add(new ChatGui());
	 }

	 private Column panelInfo()
	 {
 
		 Panel panel = new Panel();
		 panel.setAlignment(Alignment.ALIGN_CENTER);
		 
		 Column col = new Column();
		 col.setInsets(new Insets(2,2,2,2));

		 col.add(rowInfoPlayer());

		 panel.add(col);
		 
		 col = new Column();
		 col.add(panel);
		 return col;
	 }

	private Row rowInfoPlayer() {
		
		Row row = new Row();
		row.setCellSpacing(new Extent(2));
		
		Label lblImg = new Label();
		
		if( personaje.getTipo().equals("Fuego") )
		{
			lblImg.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MarcoCaraF.png"));
			row.add(lblImg);
		}
		else if( personaje.getTipo().equals("Tierra") )	
		{
			lblImg.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MarcoCaraT.png"));
			row.add(lblImg);
		}
		else if( personaje.getTipo().equals("Hielo") )	
		{
			lblImg.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MarcoCaraH.png"));
			row.add(lblImg);
		}
		else	
		{
			lblImg.setIcon(ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MarcoCaraG.png"));
			row.add(lblImg);
		}
		
		row.add(infoColumn());
		
		return row;
	}

	private Column infoColumn() {
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_LEFT);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/p_estadoInfo.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(230));
		panel.setHeight(new Extent(105));
		panel.setBackgroundImage(imgF);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(0));
		col.setInsets(new Insets(10,10,10,10));
		
		Label lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText(usuario.getLogin());
		col.add(lbl);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(50));
		
		lbl = new Label();
		lbl.setForeground(Color.YELLOW);
		lbl.setText("Lv. "+ personaje.getLevel());
		row.add(lbl);
		
        Panel panelGold = new Panel();
        panelGold.setWidth(new Extent(100));

        Row rowPanel = new Row();
        rowPanel.setCellSpacing(new Extent(10));

		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        rowPanel.add(imgI);
		
        lbl = new Label();
        lbl.setForeground(Color.YELLOW);
        lbl.setText(" " + personaje.getGold());
		rowPanel.add(lbl);		
        panelGold.add(rowPanel);
        
        row.add(panelGold);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(10));
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("XP");		
		row.add(lbl);
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Nivel nivelExp = (Nivel) session.load(Nivel.class, (personaje.getLevel() +1));
		
		session.getTransaction().commit();
		session.close();
		
		CapacityBar barraXp = createBarra(Color.GREEN,Color.WHITE,personaje.getXp(),(nivelExp.getCantidadExp() - personaje.getXp()));
		row.add(barraXp);
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);		
		lbl.setText(nivelExp.getCantidadExp() + "/" + personaje.getXp());
		row.add(lbl);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(10));
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("HP");		
		row.add(lbl);
		
		CapacityBar barraVida = createBarra(Color.RED,Color.WHITE,personaje.getHp(),0);
		row.add(barraVida);
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText(personaje.getHp() + "/" + barraVida.getValues().get(0).intValue());
		row.add(lbl);
		col.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));

		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText("MP");		
		row.add(lbl);
		
		CapacityBar barraPsinergia = createBarra(Color.BLUE,Color.WHITE,personaje.getMp(),0);
		row.add(barraPsinergia);
		
		lbl = new Label();
		lbl.setForeground(Color.WHITE);
		lbl.setText(personaje.getMp() + "/" + barraPsinergia.getValues().get(0).intValue());
		row.add(lbl);
		col.add(row);
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private CapacityBar createBarra(Color color1, Color color2, int indice1,int indice2){

		CapacityBar barra = new CapacityBar(10, 100); 
		barra.setShowTicks(false);
		barra.setReflectivity(0.1);
		
		List<Color> listColor = new ArrayList<Color>();
		listColor.add(color1);
		listColor.add(color2);
		barra.setColors(listColor);
		
		List<Number> listNumber = new ArrayList<Number>();
		listNumber.add(indice1);
		listNumber.add(indice2); 
		barra.setValues(listNumber);     

		return barra;
	}
	 
	private void validateHabilidad()
	{
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		session.getTransaction().commit();
		session.close();
		
		if( personaje.getLearning() )
		{
			Calendar calIni = Calendar.getInstance();
			Calendar calFin = personaje.getFechaFin();
			
			if( validateDate(calIni, calFin) == true )
			{
				String cad = "Termino su entrenamieto, has aprendido una nueva habilidad.";
				createWindow(cad);
				
				session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				String queryStr = "SELECT pp FROM Personaje AS pe, PersonajePoderes AS pp WHERE pe.id = pp.personajeRef AND pe.learning = pp.learnProgreso";
				PersonajePoderes personajePoderes = (PersonajePoderes) session.createQuery(queryStr).uniqueResult();
				
				personajePoderes.setLearnProgreso(false);
				
				personaje = (Personaje) session.load(Personaje.class, personaje.getId());
				
				personaje.setLearning(false);
				
				session.getTransaction().commit();
				session.close();

			}	
		}
	}
	
	private boolean validateDate(Calendar cal1, Calendar cal2)
	{
		if (cal1.compareTo(cal2) >= 0)
			return true;
		else
			return false;
	}
	
	private void createWindow(String texto)
	{

		Column col = new Column();
		col.add(new Label(texto));

		MessageBox messageBox  = new MessageBox("Academia - Compra",// 
				col,// 
				400, 130,//
				MessageBox.ACCEPT_WINDOW);
		add(messageBox);	  
		
	}
	
}
 