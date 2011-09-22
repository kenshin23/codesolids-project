package codesolids.gui.crear;

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
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.seleccion.DesktopSelect;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Antonio López
 *
 */

public class DesktopCreate extends ContentPane{

	private Column colCartel;
	
	private Label lblTitulo;
	private Label lblDescripcion;
	private Label lblDescripcion1;
	private Label lblDescripcion2;
	private Label lblDescripcion3;
	
	public DesktopCreate()
	{
		initGUI();
	}

	private void initGUI() {
		add(initCreate());
	}

	private Component initCreate() {
		
		HtmlLayout retHtmlLayout;
		
		try{
			
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templatecreate.html"), "UTF-8");
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Column colP = new Column();
		colP.add(initPanel());
		
		HtmlLayoutData hld;
		
		hld = new HtmlLayoutData("cartel");
		
		colP.setLayoutData(hld);
		retHtmlLayout.add(colP);
		
		colP = new Column();
		colP.add(initFoot());
		
		hld = new HtmlLayoutData("descripcion");
		
		colP.setLayoutData(hld);
	    
		retHtmlLayout.add(colP);
		
		return retHtmlLayout;
	}

	private Column initPanel() {
		
		Panel panel = new Panel();

		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/cartel3.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(950));
		panel.setHeight(new Extent(400));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		colCartel.setInsets(new Insets(45, 20, 0, 20));
		colCartel.add(rowImage());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.add(panel);
		
		
		return colCartel;
	}

	private Column rowImage() {
		
		Column col = new Column();
		col.setInsets(new Insets(5, 20, 5, 20));
		
		col.add(initRowSelect());
		
		return col;
	}
	
	private Row initRowSelect()
	{
		Row row = new Row(); 
		row.setCellSpacing(new Extent(5));
		
//		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MagoT.png");
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGT.gif");	
		row.add(initColPersonaje(imgR,"Mago Tierra"));
		
//		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MagoF.png");
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGF.gif");
		row.add(initColPersonaje(imgR,"Mago Fuego"));		
		
//		imgR =  ImageReferenceCache.getInstance().getImageReference("Images/Personajes/MagoHH.png");
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGHR.gif");
		row.add(initColPersonaje(imgR,"Mago Hielo"));		
		
//		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/GuerreroGG.png");
		imgR = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGGR.gif");
		row.add(initColPersonaje(imgR,"Guerrero"));
		
		return row;
		
	}
	
	private Panel initColPersonaje(ImageReference imgR, final String type)
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column col = new Column();
		col.setInsets(new Insets(5, 5, 5, 5));
		col.setCellSpacing(new Extent(5));

		Label lblType = new Label();
		lblType.setFont(new Font(Font.SANS_SERIF,Font.BOLD,new Extent(12)));
		lblType.setText(type);
		col.add(lblType);
		
		Label lblI = new Label(imgR);
		col.add(lblI);
		
		Row row = new Row(); 
		row.setCellSpacing(new Extent(1));
		
		Button btnVer = new Button();
		btnVer.setToolTipText("Ver");
		FillImage imageb = new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/ver.png")));
		btnVer.setBackgroundImage(imageb);
		btnVer.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/verMouseOver.png"))));
		btnVer.setRolloverEnabled(true);
		btnVer.setAlignment(Alignment.ALIGN_CENTER);
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnVerClicked(type);
			}
		});
		btnVer.setHeight(new Extent(29));
		btnVer.setWidth(new Extent(49));
		row.add(btnVer);
		
		Button btnSelect = new Button();
		btnSelect.setToolTipText("Seleccionar");
		imageb = new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/seleccionar.png")));
		btnSelect.setBackgroundImage(imageb);
		btnSelect.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/seleccionarMouseOver.png"))));
		btnSelect.setRolloverEnabled(true);
		btnSelect.setAlignment(Alignment.ALIGN_CENTER);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSelectClicked(type);
			}
		});
		btnSelect.setHeight(new Extent(29));
		btnSelect.setWidth(new Extent(103));
		row.add(btnSelect);
		
		col.add(row);
		
		panel.add(col);

		return panel;
	}
	
	private Column initFoot() {
		
		Panel panel = new Panel();

		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Fondos/cartel.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(505));
		panel.setHeight(new Extent(152));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
	
		colCartel.add(initDescripcion());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.setInsets(new Insets(0,0,0,20));
		colCartel.add(panel);
		
		return colCartel;
	}

	private Column initDescripcion() {
		
		colCartel = new Column();
		
		return colCartel;
	}

	private Column descripcionTipo() {
		
		Column col = new Column();
		colCartel.setInsets(new Insets(30, 20, 30, 10));
		col.setCellSpacing(new Extent(10));
		
		lblTitulo = new Label();
		lblTitulo.setFont(new Font(Font.SANS_SERIF,Font.BOLD,new Extent(11)));
		col.add(lblTitulo);
		
		lblDescripcion = new Label();
		lblDescripcion.setFont(new Font(Font.SANS_SERIF,Font.BOLD, new Extent(11)));
		col.add(lblDescripcion);
		
		lblDescripcion1 = new Label();
		lblDescripcion1.setFont(new Font(Font.SANS_SERIF,Font.BOLD, new Extent(11)));
		col.add(lblDescripcion1);
		
		lblDescripcion2 = new Label();
		lblDescripcion2.setFont(new Font(Font.SANS_SERIF,Font.BOLD, new Extent(11)));
		col.add(lblDescripcion2);
		
		lblDescripcion3 = new Label();
		lblDescripcion3.setFont(new Font(Font.SANS_SERIF,Font.BOLD, new Extent(11)));
		col.add(lblDescripcion3);
		
		return col;
		
	}	
	
	private void btnSelectClicked(String type) {
				
		Personaje personaje = new Personaje();
		
		personaje.setLevel(1);
		personaje.setHp(100);
		personaje.setMp(100);
		personaje.setGold(300);
		personaje.setXp(0);
		personaje.setAtaqueBasico(0.0);
		personaje.setAtaqueEspecial(0.0);
		personaje.setSpeed(0);
		personaje.setDefensa(0);
		personaje.setPuntos(0);
		personaje.setReputacionClan(0);
		personaje.setDonateGold(0);
		
		if( type == "Mago Tierra"){
			personaje.setTipo("Tierra");
			//personaje.setDirImage("Images/Personajes/MagoT.png");
			personaje.setDirImage("Images/Personajes/Gifs/MGT.gif");
		}
		else if( type == "Mago Fuego" ){
			personaje.setTipo("Fuego");
			//personaje.setDirImage("Images/Personajes/MagoF.png");
			personaje.setDirImage("Images/Personajes/Gifs/MGF.gif");
		}
		else if( type == "Mago Hielo" ){
			personaje.setTipo("Hielo");
			//personaje.setDirImage("Images/Personajes/MagoH.png");
			personaje.setDirImage("Images/Personajes/Gifs/MGH.gif");
		}
		else{
			personaje.setTipo("Guerrero");
			//personaje.setDirImage("Images/Personajes/GuerreroGG.png");
			personaje.setDirImage("Images/Personajes/Gifs/MGG.gif");
		}
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		Usuario usuario = app.getUsuario();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		usuario = (Usuario) session.load(Usuario.class, usuario.getId());
		
		personaje.setUsuarioRef(usuario);
		usuario.getPersonajeList().add(personaje);
		
		session.save(usuario);
		
		session.getTransaction().commit();
		session.close();
		
		removeAll();
		add(new DesktopSelect());
		
	}	
	
	private void btnVerClicked(String type)
	{
		
		if( type == "Mago Tierra" )
			infoMagoTierra();
		else if( type == "Mago Fuego" )
			infoMagoFuego();
		else if( type == "Mago Hielo" )
			infoMagoHielo();
		else
			infoGuerrero();
	}

	private void infoMagoTierra() {
		
		colCartel.removeAll();
		
		colCartel.add(descripcionTipo());
		
		lblTitulo.setText("Mago Tierra");
		
		lblDescripcion.setText("El mago de tierra tiene la habilidad de utilizar la tierra de Weyard, ");
		lblDescripcion1.setText("con sus ataques elementales es casi seguro que el enemigo no podrá ");
		lblDescripcion2.setText("resistir. Sus poderes tienen la capacidad de cambiar la fuerza y la ");
		lblDescripcion3.setText("composición de la tierra, maneja muchos HP (Healt Point).");
	
	}
	
	private void infoMagoFuego() {
		
		colCartel.removeAll();
		
		colCartel.add(descripcionTipo());
		
		lblTitulo.setText("Mago Fuego");

		lblDescripcion.setText("El mago de fuego tiene la habilidad de hacer que sus enemigos arden ");
		lblDescripcion1.setText("en las temibles llamas de Weyard, sus ataques pueden ser de medio y ");
		lblDescripcion2.setText("largo alcance. Es capaz de renacer desde las cenizas para vencer a ");
		lblDescripcion3.setText("los enemigos, sus daños son de tipo totalmente ofensivo.");
		
	}	
	
	private void infoMagoHielo() {

		colCartel.removeAll();
		
		colCartel.add(descripcionTipo());
		
		lblTitulo.setText("Mago Hielo");

		lblDescripcion.setText("El mago de hielo es capaz de controlar letales hechizos de escarcha y "); 
		lblDescripcion1.setText("frío. Su habilidad de hielo es una forma nativa de las regiones del ");
		lblDescripcion2.setText("Norte de Weyard. Su capacidad de alcance es muy alta, maneja muchos PP (Psinergia Point).");
		
	}
	
	private void infoGuerrero() {
		
		colCartel.removeAll();
		
		colCartel.add(descripcionTipo());
		
		lblTitulo.setText("Guerrero");

		lblDescripcion.setText("El guerrero es determinado en sus ataques, son muy fuertes ");
		lblDescripcion1.setText("y resistentes en la batalla. Combinan sus puños, espadas y patadas ");
		lblDescripcion2.setText("para realizar ataques contudentes. Su capacidad es de corto alcance, ");
		lblDescripcion3.setText("luchan fuera de la ley Weyard en busca de la gloria.");
		
	}	
}
