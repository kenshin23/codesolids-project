package codesolids.gui.clan;

import java.util.Calendar;
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
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Clan;
import codesolids.bd.clases.Mensaje;
import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageBox;
import codesolids.util.TestTableModel;

import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.LabelCellRenderer;

import echopoint.HtmlLayout;

/**
 * 
 * @author Antonio López
 *
 */

public class PerfilClan extends ContentPane{

	private Personaje personaje;
	
	private TestTableModel tableDtaModel;
	
	private Column colPerfil;
	
	public PerfilClan()
	{	
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		personaje = app.getPersonaje();
		
		initGUI();
	}
	
	private void initGUI()
	{
		add(initPerfilClan());
	}

	private Component initPerfilClan() {
		
		HtmlLayout retHtmlLayout;
		
		try{
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateclan.html"), "UTF-8");		
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		add(initVentana());
		
		return retHtmlLayout;
	}
	
	private WindowPane initVentana()
	{
		final WindowPane ventana = new WindowPane();
		ventana.setStyle(StyleWindow.DEFAULT_STYLE);
		ventana.setWidth(new Extent(750));
		ventana.setHeight(new Extent(390));
		ventana.setMovable(false);
		ventana.setResizable(false);
		ventana.setModal(false);
		ventana.setClosable(false);
		
		TabPane tabPane = new TabPane();
		ventana.add(tabPane);
		
		tabPane.setTabActiveBackground(new Color(226,211,161));
		tabPane.setTabActiveForeground(Color.BLACK);
		tabPane.setTabActiveFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		
		tabPane.setTabInactiveBackground(new Color(228,228,228));
		tabPane.setTabInactiveForeground(Color.BLACK);
		tabPane.setTabInactiveFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		
		tabPane.setBackground(new Color(226,211,161));
		tabPane.setBorder(new Border(1,Color.BLACK,Border.STYLE_SOLID));
		
		tabPane.setTabWidth(new Extent(180));
		tabPane.setTabHeight(new Extent(20));
		
		tabPane.setTabAlignment(Alignment.ALIGN_CENTER);

		TabPaneLayoutData tpld = new TabPaneLayoutData();
		tpld.setTitle("Perfil Clan");
		
		Column col = new Column();
		col.setLayoutData(tpld);
		col.add(initColumnPerfil());
		
		tabPane.add(col);
		
		tpld = new TabPaneLayoutData();
		tpld.setTitle("Listas de Miembro");
		
		col = new Column();
		col.setLayoutData(tpld);
		col.add(initMembers());
		
		tabPane.add(col);
		
		return ventana;
	}

	private Column initColumnPerfil()
	{
		colPerfil = new Column();
		colPerfil.add(initPerfil());
		
		return colPerfil;
	}
	
	private Column initPerfil() {
		
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Clan clan = (Clan) session.load(Clan.class, personaje.getClanRef().getId());
		
		session.getTransaction().commit();
		session.close();
		
		Column col = new Column();
		col.setInsets(new Insets(10, 10, 10, 10));
		col.setCellSpacing(new Extent(15));
		
		Grid grid = new Grid(2);
		grid.setInsets(new Insets(10, 10, 30, 10));
		
		Label lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		Label lblText = new Label("Nombre del Clan: ");
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label(clan.getNameClan());
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		lblText = new Label("Clan ID: ");
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label("" + clan.getId());
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		lblText = new Label("Lider del Clan: ");
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label(clan.getClanMasterRef().getUsuarioRef().getLogin());
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		lblText = new Label("Total Miembros: ");
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label("" + clan.getCantPersonaje() + " / " + clan.getLimite());
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		lblText = new Label("Reputación: ");
		lblText.setForeground(new Color(128,0,128));
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label("" + clan.getReputacion());
		lblText.setForeground(new Color(128,0,128));
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		lblImg = new Label(ImageReferenceCache.getInstance().getImageReference("Images/Util/pto.png"));
		row = new Row();
		row.setCellSpacing(new Extent(5));
		row.add(lblImg);
		
		lblText = new Label("Oro del Clan: ");
		lblText.setForeground(Color.ORANGE);
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		row.add(lblText);
		grid.add(row);
		
		lblText = new Label("" + clan.getGold());
		lblText.setForeground(Color.ORANGE);
		lblText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		grid.add(lblText);
		
		col.add(grid);
		
		col.add(createBotones());
		
		return col;
	}
	
	private Column createBotones() {
		
		Column col = new Column();
		
		if( personaje.getId() == personaje.getClanRef().getClanMasterRef().getId() )
			col.add(botoneLider());
		else
			col.add(botonesMember());
		
		return col;
	}

	private Grid botoneLider() {
		
		Grid grid = new Grid(5);
		grid.setInsets(new Insets(10, 10, 10, 10));
		
		GridLayoutData gld = new GridLayoutData();
		gld.setAlignment(Alignment.ALIGN_RIGHT);
		grid.setLayoutData(gld);
		
		Button btnCerrar = new Button("Cerrar Clan");
		btnCerrar.setStyle(Styles1.DEFAULT_STYLE);
		btnCerrar.setToolTipText("Cerrar el Clan");
		btnCerrar.setWidth(new Extent(80));
		btnCerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCerrarClicked();
			}
		});
		grid.add(btnCerrar);
		
		Button btnDespedir = new Button("Despedir Clan");
		btnDespedir.setStyle(Styles1.DEFAULT_STYLE);
		btnDespedir.setToolTipText("Despedir del Clan");
		btnDespedir.setWidth(new Extent(90));
		btnDespedir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnDespedirClicked();
			}
		});
		grid.add(btnDespedir);
		
		Button btnInvitar = new Button("Invitar Clan");
		btnInvitar.setStyle(Styles1.DEFAULT_STYLE);
		btnInvitar.setToolTipText("Invitar al Clan");
		btnInvitar.setWidth(new Extent(80));
		btnInvitar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnInvitarClicked();
			}
		});
		grid.add(btnInvitar);
		
		Button btnGold = new Button("Donar Oro");
		btnGold.setStyle(Styles1.DEFAULT_STYLE);
		btnGold.setToolTipText("Donar Oro al Clan");
		btnGold.setWidth(new Extent(70));
		btnGold.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnGoldClicked();
			}
		});
		grid.add(btnGold);
		
		Button btnRegresar = new Button("Regresar");
		btnRegresar.setStyle(Styles1.DEFAULT_STYLE);
		btnRegresar.setToolTipText("Regresar al mapa");
		btnRegresar.setWidth(new Extent(70));
		btnRegresar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnRegresarClicked();
			}
		});
		grid.add(btnRegresar);
		
		return grid;
	}

	private void btnCerrarClicked() {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Criteria criteria = session.createCriteria(Mensaje.class).add(Restrictions.eq("personajeSendRef", personaje));
		List<Mensaje> mailBox = criteria.list();
		
		for( int i = 0; i < mailBox.size(); i++ )
			session.delete(mailBox.get(i));
		
		Clan clan = (Clan) session.load(Clan.class, personaje.getClanRef().getId());
		
		int idClan = clan.getId();
		
		criteria = session.createCriteria(Personaje.class).add(Restrictions.eq("clanRef", clan));
		List<Personaje> list = criteria.list();
		
		for( int i = 0; i < list.size(); i++ )
			list.get(i).setClanRef(null);
		
		clan.setClanMasterRef(null);
			
		session.getTransaction().commit();
		session.close();
		
		removeAll();
		add(new MapaDesktop());
		
		session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		clan = (Clan) session.load(Clan.class, idClan);
		session.delete(clan);
		
		session.getTransaction().commit();
		session.close();
	}
	
	private void btnDespedirClicked() {
		
		final WindowPane ventana = new WindowPane();
		ventana.setTitle("Despedir del Clan");
		ventana.setStyle(StyleWindow.DEFAULT_STYLE);
		
		ventana.setModal(true);
		ventana.setResizable(false);
		ventana.setMovable(false);
		add(ventana);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(10, 10, 10, 10));
		ventana.add(col);
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		Grid grid = new Grid(2);
		grid.setInsets(new Insets(5, 5, 5, 5));
		grid.setLayoutData(cld);
		
		Label lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("ID del Miembro: ");
		grid.add(lbl);
		
		final TextField textField = new TextField();
		textField.setWidth(new Extent(120));
		textField.setMaximumLength(200);
		grid.add(textField);
		
		col.add(grid);
		
		Button btnDespedir = new Button("Despedir");
		btnDespedir.setStyle(Styles1.DEFAULT_STYLE);
		btnDespedir.setWidth(new Extent(60));
		btnDespedir.setToolTipText("Despedir al miembro del clan");
		btnDespedir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnDpdirClicked(textField,ventana,1);
			}	
		});
		btnDespedir.setLayoutData(cld);
		col.add(btnDespedir);
	}
	
	private void btnDpdirClicked(TextField textField,WindowPane ventana, int tipo) {

		if( textField.getText().equals("") )
		{
			Column col = new Column();
			col.add(new Label("Por favor rellene el dato, no puede esta vacio el campo."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);

			return;
		}
		else if( isNumeric(textField.getText()) )
		{

			Session session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Personaje.class).add(Restrictions.eq("id", Integer.parseInt(textField.getText())));

			if( criteria.list().size() == 0 )
			{
				textField.setText("");
				
				Column col = new Column();
				col.add(new Label("El personaje no existe en la aplicación."));

				MessageBox messageBox  = new MessageBox("Error",// 
						col,// 
						400, 130,//
						MessageBox.ACCEPT_WINDOW);
				add(messageBox);

				return;
			}
			else{

				personaje = (Personaje) session.load(Personaje.class, personaje.getId());
				Clan clan = (Clan) session.load(Clan.class, personaje.getClanRef().getId());

				Personaje pBean = (Personaje) criteria.uniqueResult();

				if( (tipo == 2) && (pBean.getId() != clan.getClanMasterRef().getId()) )
				{	
					Mensaje mailBox = new Mensaje();
					
					Calendar calSend = Calendar.getInstance();
					mailBox.setDateSend(calSend);
					
					mailBox.setPersonajeSendRef(personaje);
					mailBox.setPersonajeReceivesRef(pBean);
					
					mailBox.setLeido(false);
					
					session.save(mailBox);
					
				}
				else if( (tipo == 2) && (pBean.getId() == clan.getClanMasterRef().getId()) )
				{
					textField.setText("");
					
					Column col = new Column();
					col.add(new Label("Usted no puede ser invitado por usted mismo al Clan."));

					MessageBox messageBox  = new MessageBox("Error",// 
							col,// 
							400, 130,//
							MessageBox.ACCEPT_WINDOW);
					add(messageBox);

					return;
				}
				else if( (tipo == 1) && (pBean.getClanRef().getId() == clan.getId()) && (pBean.getId() != clan.getClanMasterRef().getId()) )
				{	

					pBean.setClanRef(null);
					clan.setCantPersonaje(clan.getCantPersonaje() - 1);

				}else if( (tipo == 1) && (pBean.getClanRef().getId() == clan.getId()) && (pBean.getId() == clan.getClanMasterRef().getId()) ){	

					textField.setText("");
					
					Column col = new Column();
					col.add(new Label("Usted no puede ser eliminado por ser el Lider del Clan."));

					MessageBox messageBox  = new MessageBox("Error",// 
							col,// 
							400, 130,//
							MessageBox.ACCEPT_WINDOW);
					add(messageBox);

					return;
				}
				else
				{
					textField.setText("");
					
					Column col = new Column();
					col.add(new Label("El personaje no pertenece al clan."));

					MessageBox messageBox  = new MessageBox("Error",// 
							col,// 
							400, 130,//
							MessageBox.ACCEPT_WINDOW);
					add(messageBox);

					return;							
				}
			}

			session.getTransaction().commit();
			session.close();

			ventana.userClose();
			
			tableDtaModel.clear();
			loadBD();
			
			colPerfil.removeAll();
			colPerfil.add(initPerfil());

		}else
		{
			textField.setText("");

			Column col = new Column();
			col.add(new Label("El Formato del id no es númerica. Por favor ingresar un formato correcto Ejemplo: 305"));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
		}
	}	
	
	private void btnInvitarClicked() {
		
		final WindowPane ventana = new WindowPane();
		ventana.setTitle("Invitación al clan");
		ventana.setStyle(StyleWindow.DEFAULT_STYLE);
		
		ventana.setModal(true);
		ventana.setResizable(false);
		ventana.setMovable(false);
		add(ventana);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(10, 10, 10, 10));
		ventana.add(col);
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		Grid grid = new Grid(2);
		grid.setInsets(new Insets(5, 5, 5, 5));
		grid.setLayoutData(cld);
		
		Label lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("ID del Miembro: ");
		grid.add(lbl);
		
		final TextField textField = new TextField();
		textField.setWidth(new Extent(120));
		textField.setMaximumLength(200);
		grid.add(textField);
		
		col.add(grid);
		
		Button btnInvitar = new Button("Invitar");
		btnInvitar.setStyle(Styles1.DEFAULT_STYLE);
		btnInvitar.setWidth(new Extent(60));
		btnInvitar.setToolTipText("Invitar un miembro al clan");
		btnInvitar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnDpdirClicked(textField, ventana, 2);
			}
		});
		btnInvitar.setLayoutData(cld);
		col.add(btnInvitar);
	
	}
		
	private void btnGoldClicked() {
		
		final WindowPane ventana = new WindowPane();
		ventana.setTitle("Donación Oro");
		ventana.setStyle(StyleWindow.DEFAULT_STYLE);
		
		ventana.setModal(true);
		ventana.setResizable(false);
		ventana.setMovable(false);
		add(ventana);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(10));
		col.setInsets(new Insets(10, 10, 10, 10));
		ventana.add(col);
		
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		
		Grid grid = new Grid(2);
		grid.setInsets(new Insets(5, 5, 5, 5));
		grid.setLayoutData(cld);
		
		Label lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("Oro del Clan: ");
		grid.add(lbl);
		
		lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("" + personaje.getClanRef().getGold());
		grid.add(lbl);
		
		lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("Tu Oro: ");
		grid.add(lbl);
		
		lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("" + personaje.getGold());
		grid.add(lbl);
		
		lbl = new Label();
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, null));
		lbl.setText("Oro para donar: ");
		grid.add(lbl);
		
		final TextField textField = new TextField();
		textField.setWidth(new Extent(120));
		textField.setMaximumLength(80);
		grid.add(textField);
		
		col.add(grid);
		
		Button btnDonar = new Button("Donar");
		btnDonar.setStyle(Styles1.DEFAULT_STYLE);
		btnDonar.setWidth(new Extent(50));
		btnDonar.setToolTipText("Donar Oro");
		btnDonar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnDonarClicked(textField,ventana);
			}
		});
		btnDonar.setLayoutData(cld);
		col.add(btnDonar);	
	}
	
	private void btnDonarClicked(TextField textField, WindowPane ventana) {
		
		if( textField.getText().equals("") )
		{
			Column col = new Column();
			col.add(new Label("Por favor rellene el dato, no puede estar vacio el campo."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
			
			return;
		}
		else if( isNumeric(textField.getText()) )
		{
			
			if( Integer.parseInt(textField.getText()) <= personaje.getGold() )
			{
				ventana.userClose();
				
				Session session = SessionHibernate.getInstance().getSession();
				session.beginTransaction();
				
				personaje = (Personaje) session.load(Personaje.class, personaje.getId());
				personaje.setGold(personaje.getGold() - Integer.parseInt(textField.getText()));
				
				Clan clan = (Clan) session.load(Clan.class, personaje.getClanRef().getId());
				clan.setGold(clan.getGold() + Integer.parseInt(textField.getText()));
				
				session.getTransaction().commit();
				session.close();
				
				colPerfil.removeAll();
				colPerfil.add(initPerfil());
				
			}
			else
			{
				textField.setText("");
				
				Column col = new Column();
				col.add(new Label("Usted no tiene esa cantidad de oro para donar."));

				MessageBox messageBox  = new MessageBox("Error",// 
						col,// 
						400, 130,//
						MessageBox.ACCEPT_WINDOW);
				add(messageBox);						
			}
		}else
		{
			textField.setText("");
			
			Column col = new Column();
			col.add(new Label("El Formato de la cantidad de oro no es númerica. Por favor ingresar un formato correcto Ejemplo: 100"));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
		}	
	}
	
	private boolean isNumeric(String cadena)
	{
		try
		{
			Integer.parseInt(cadena);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
	
	private void btnRegresarClicked() {
		removeAll();
		add(new MapaDesktop());
	}
	
	private Grid botonesMember() {
		
		Grid grid = new Grid(3);
		grid.setInsets(new Insets(10, 10, 10, 10));
		
		GridLayoutData gld = new GridLayoutData();
		gld.setAlignment(Alignment.ALIGN_RIGHT);
		grid.setLayoutData(gld);
		
		Button btnRetirar = new Button("Retirar Clan");
		btnRetirar.setStyle(Styles1.DEFAULT_STYLE);
		btnRetirar.setWidth(new Extent(90));
		btnRetirar.setToolTipText("Retirarse del Clan");
		btnRetirar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnRetirarClicked();
			}
		});
		grid.add(btnRetirar);
		
		Button btnGold = new Button("Donar Oro");
		btnGold.setStyle(Styles1.DEFAULT_STYLE);
		btnGold.setWidth(new Extent(70));
		btnGold.setToolTipText("Donar Oro al Clan");
		btnGold.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnGoldClicked();
			}
		});
		grid.add(btnGold);
		
		Button btnRegresar = new Button("Regresar");
		btnRegresar.setStyle(Styles1.DEFAULT_STYLE);
		btnRegresar.setToolTipText("Regresar al mapa");
		btnRegresar.setWidth(new Extent(70));
		btnRegresar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnRegresarClicked();
			}
		});
		grid.add(btnRegresar);
		
		return grid;
	}
	
	private void btnRetirarClicked() {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Clan clan = (Clan) session.load(Clan.class, personaje.getClanRef().getId());
		clan.setCantPersonaje(clan.getCantPersonaje() - 1);
		
		personaje.setClanRef(null);
		
		session.getTransaction().commit();
		session.close();
		
		removeAll();
		add(new MapaDesktop());
	}
	
	private Component initMembers() {
		
		Column col = new Column();
		col.setCellSpacing(new Extent(3));
		col.setInsets(new Insets(10,10,10,10));
		
		TableColModel tableColModel = initTableColModel();
	    TableSelModel tableSelModel = new TableSelModel();

	    tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(10);
	    
	    ETable table = new ETable();
	    
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);
	    
	    table.setBorder(new Border(new Extent(1), new Color(87, 205, 211), Border.STYLE_RIDGE));
		
	    col.add(table);
	    
		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    tableNavigation.setAlignment(Alignment.ALIGN_RIGHT);
	    tableNavigation.setForeground(Color.WHITE);
	    tableNavigation.setInsets(new Insets(0, 0, 20, 0));
	    
	    col.add(tableNavigation);
	    
	    loadBD();
	    
		return col;
	}

	private TableColModel initTableColModel() {		
		
		TableColModel tableColModel = new TableColModel();

	    TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
				return personaje.getId();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("ID");
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getUsuarioRef().getLogin();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(100));
	    tableColumn.setHeadValue("Nombre");
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getLevel();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Nivel");    
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getReputacionClan();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(80));
	    tableColumn.setHeadValue("Reputación");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(new Color(128,0,128));
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(new Color(128,0,128));
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Personaje personaje = (Personaje) element;
	    		return personaje.getDonateGold();
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(new Color(247,240,30));
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(50));
	    tableColumn.setHeadValue("Donación");
	    
	    return tableColModel;
	}
	
	private void loadBD()
	{
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Personaje.class).add(Restrictions.eq("clanRef", personaje.getClanRef())).addOrder(Order.desc("level"));
		List<Personaje> listPlayer = criteria.list();
		
		for(Personaje obj : listPlayer)
		{
			Personaje pBean = new Personaje();
			
			pBean.setId(obj.getId());
			pBean.setUsuarioRef(obj.getUsuarioRef());
			pBean.setLevel(obj.getLevel());
			pBean.setDonateGold(obj.getDonateGold());
			pBean.setReputacionClan(obj.getReputacionClan());
			
			tableDtaModel.add(pBean);
		}
		
		session.getTransaction().commit();
		session.close();		
	}
	
}
