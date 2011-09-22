package codesolids.gui.clan;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Clan;
import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.ranking.RankingClan;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageBox;
import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Antonio López
 *
 */

public class CreateClan extends ContentPane {

	private TextField txtName;
	
	private Personaje personaje;
	
	public CreateClan()
	{
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		personaje = app.getPersonaje();
		
		initGUI();
	}
	
	private void initGUI()
	{
		if( validateIsClan() )
			add(new PerfilClan());
		else
			add(initClan());
	}

	private Component initClan() {
		
		HtmlLayout retHtmlLayout;
		
		try{
			
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateclan.html"), "UTF-8");
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Column colP = new Column();
		colP.add(initPanel());
			
		HtmlLayoutData hld = new HtmlLayoutData("cartel");
		
		colP.setLayoutData(hld);
		retHtmlLayout.add(colP);
		
		return retHtmlLayout;
	}
	
	private boolean validateIsClan()
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		String queryStr = "SELECT pe FROM Personaje AS pe, Clan AS c WHERE pe.clanRef = c.id AND pe.id = " + personaje.getId();
		Query query = session.createQuery(queryStr);
		List<Personaje> pList = query.list();
		
		session.getTransaction().commit();
		session.close();
	
		if( pList.isEmpty() )
			return false;
		else
			return true;
	}
	
	private Column initPanel() {
		
		Panel panel = new Panel();

		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Items/pergamino.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(460));
		panel.setHeight(new Extent(385));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		colCartel.setInsets(new Insets(30, 30, 30, 30));
		colCartel.add(colClanes());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.add(panel);
		
		return colCartel;
	}
	
	private Column colClanes()
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_RIGHT);
		
		Button btnSalir = new Button();
        btnSalir.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/regresar.png")));
        btnSalir.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/regresarMouseOver.png"))));
        btnSalir.setRolloverEnabled(true);
        btnSalir.setHeight(new Extent(27));
        btnSalir.setWidth(new Extent(103));
		btnSalir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				removeAll();
				add(new MapaDesktop());
			}
		});
		btnSalir.setLayoutData(cld);
		col.add(btnSalir);
		
		col.add(createClan());
		col.add(requestClan());
		col.add(rankingClan());
		
		return col;
	}
	
	private Column createClan()
	{
		Column colOption = new Column();
		colOption.setInsets(new Insets(2, 2, 2, 2));
		colOption.setBorder(new Border(2,new Color(25, 54, 65),Border.STYLE_OUTSET));
		colOption.setCellSpacing(new Extent(5));
		
		Label lblTitulo = new Label();
		lblTitulo.setForeground(new Color(25, 54, 65));
		lblTitulo.setText("Crear un nuevo Clan: ");
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_LEFT);
		lblTitulo.setLayoutData(cld);
		
		colOption.add(lblTitulo);
		
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		row.add(new Label("Nombre del Clan: "));
		
		txtName = new TextField();
		txtName.setWidth(new Extent(200));
		txtName.setMaximumLength(120);
		row.add(txtName);
		
		row.setLayoutData(cld);
		colOption.add(row);
		
		Button btnCrear = new Button();
        btnCrear.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/crear3.png")));
        btnCrear.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/crear3MouseOver.png"))));
        btnCrear.setRolloverEnabled(true);
        btnCrear.setHeight(new Extent(27));
        btnCrear.setWidth(new Extent(103));
		btnCrear.setToolTipText("Crear un Clan");
		btnCrear.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnCrearClicked();
			}
		});
		btnCrear.setLayoutData(cld);
		colOption.add(btnCrear);
		        
        row = new Row();
        row.setCellSpacing(new Extent(5));
        row.setLayoutData(cld);
        
		Label lblGold = new Label();
		lblGold.setForeground(Color.YELLOW);
		lblGold.setText("Necesitas: 100000 ");
        row.add(lblGold);
		
        Panel panel = new Panel();
        panel.setWidth(new Extent(100));

        ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/sacomoneda.png");
        ImageIcon imgI = new ImageIcon(imgR);
        imgI.setWidth(new Extent(25));
        imgI.setHeight(new Extent(25));
        row.add(imgI);
		
		panel.add(row);
		row.add(panel);
		
		colOption.add(row);
		
		return colOption;
	}
	
	private Column requestClan()
	{
		Column colOption = new Column();
		colOption.setInsets(new Insets(2, 2, 2, 2));
		colOption.setBorder(new Border(2,new Color(25, 54, 65),Border.STYLE_OUTSET));
		colOption.setCellSpacing(new Extent(5));
		
		Label lblTitulo = new Label();
		lblTitulo.setForeground(new Color(25, 54, 65));
		lblTitulo.setText("Solicitud de Clan: ");
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_LEFT);
		lblTitulo.setLayoutData(cld);
		
		colOption.add(lblTitulo);
		
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		lblTitulo = new Label();
		lblTitulo.setText("Encuentra un clan con vacantes.");
		lblTitulo.setLayoutData(cld);
		
		colOption.add(lblTitulo);
		
		Button btnRequest = new Button();
		btnRequest.setToolTipText("Solicitar Vacante");
        btnRequest.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/solicitar.png")));
        btnRequest.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/solicitarMouseOver.png"))));
        btnRequest.setRolloverEnabled(true);
        btnRequest.setHeight(new Extent(27));
        btnRequest.setWidth(new Extent(103));

		btnRequest.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnRequestClicked();
			}
		});
		btnRequest.setLayoutData(cld);
		colOption.add(btnRequest);
		
		return colOption;
	}
	
	private Column rankingClan()
	{
		Column colOption = new Column();
		colOption.setInsets(new Insets(2, 2, 2, 2));
		colOption.setBorder(new Border(2,new Color(25, 54, 65),Border.STYLE_OUTSET));
		colOption.setCellSpacing(new Extent(5));
		
		Label lblTitulo = new Label();
		lblTitulo.setForeground(new Color(25, 54, 65));
		lblTitulo.setText("Ranking de Clan: ");
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_LEFT);
		lblTitulo.setLayoutData(cld);
		
		colOption.add(lblTitulo);
		
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		lblTitulo = new Label();
		lblTitulo.setText("Da un vistazo a la clasificación más reciente de los clanes!");
		lblTitulo.setLayoutData(cld);
		
		colOption.add(lblTitulo);
		
		Button btnRanking = new Button();
        btnRanking.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/ranking.png")));
        btnRanking.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/rankingMouseOver.png"))));
        btnRanking.setRolloverEnabled(true);
        btnRanking.setHeight(new Extent(27));
        btnRanking.setWidth(new Extent(103));
		btnRanking.setToolTipText("Ver Ranking Clan");
		
		btnRanking.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnRankingClicked();
			}
		});
		btnRanking.setLayoutData(cld);
		colOption.add(btnRanking);
		
		return colOption;
	}

	private void btnCrearClicked() 
	{		

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		personaje = (Personaje) session.load(Personaje.class, personaje.getId());

		if(!checkNameClan(session))
		{				
			Column col = new Column();
			col.add(new Label("El nombre de clan ya existe, intente con otro."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);

			txtName.setText("");

			return;
		}
		
		if( personaje.getGold() < 100000 )
		{
			
			Column col = new Column();
			col.add(new Label("Usted no tiene oro suficiente para crear un clan."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);

			txtName.setText("");
			
			return;
		}
		
		if( txtName.getText().equals("") )
		{
			
			Column col = new Column();
			col.add(new Label("Por favor ingrese el nombre del clan, el campo esta vacio."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);

			txtName.setText("");
			
			return;
		}
		
		Clan clan = new Clan();

		clan.setNameClan(txtName.getText());
		clan.setGold(0);
		clan.setReputacion(0);
		clan.setLimite(20);
		clan.setCantPersonaje(1);

		Calendar fechaRegister = new GregorianCalendar();
		clan.setDateJoin(fechaRegister);

		clan.setClanMasterRef(personaje);
		
		personaje.setGold(personaje.getGold() - 100000);
		
		personaje.setClanRef(clan);

		clan.getListPersonaje().add(personaje);

		session.save(clan);

		session.getTransaction().commit();
		session.close();
		
		removeAll();
		add(new PerfilClan());
		
	}
	
	private boolean checkNameClan(Session session)
	{
		
		Criteria criteria = session.createCriteria(Clan.class).add(Restrictions.eq("nameClan", txtName.getText()));

		if (criteria.list().size() != 0)
			return false;
		else
			return true;
	}

	private void btnRequestClicked() {
		
		WindowPane ventanaRequest = new WindowPane();
		ventanaRequest.setTitle("Solicitud de Afiliación");
		ventanaRequest.setStyle(StyleWindow.DEFAULT_STYLE);
		ventanaRequest.setWidth(new Extent(820));
		ventanaRequest.setHeight(new Extent(430));
		ventanaRequest.setMaximumWidth(new Extent(820));
		ventanaRequest.setMaximumHeight(new Extent(430));
		ventanaRequest.setResizable(false);
		ventanaRequest.setMovable(false);
		ventanaRequest.setModal(true);
		
		Column col = new Column();
		col.setInsets(new Insets(10, 10, 10, 10));
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		col.setLayoutData(cld);
		
		RequestClan requestClan = new RequestClan(ventanaRequest);
		col.add(requestClan);
		
		ventanaRequest.add(col);
		
		add(ventanaRequest);
	}
	
	private void btnRankingClicked() {
		
		removeAll();
		add(new RankingClan());
	}
}
