package codesolids.gui.chat;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Chat;
import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.GUIStyles;
import codesolids.util.TimedServerPush;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author = Jose Luis Perez M.
 * 
 */

public class ChatGui extends ContentPane {

	private TextArea txtCharla;
	private TextArea txtMsg;
	private TextArea txtUsuarios;
	private Column col;
	private Column col2;
	private Usuario usuario;

	TaskQueueHandle taskQueue;
	TimedServerPush timedServerPush;
	private HtmlLayout htmllayaut;

	private Chat chatBean;

	public ChatGui() {
		taskQueue = ApplicationInstance.getActive().createTaskQueue();
		add(initGUI());
	}

	// --------------------------------------------------------------------------------

	public Component initGUI() {
		
		
		txtCharla = new TextArea();
		txtCharla.setEditable(false);
		txtCharla.setInsets(new Insets(15, 15, 15, 15));
		txtCharla.setWidth(new Extent(540));
		txtCharla.setHeight(new Extent(335));

		txtUsuarios = new TextArea();
		txtUsuarios.setEditable(false);
		txtUsuarios.setHeight(new Extent(360));
		txtUsuarios.setWidth(new Extent(155));

		col2 = new Column();

		this.setBackground(Color.BLACK);

		try {
			htmllayaut = new HtmlLayout(getClass().getResourceAsStream("index.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


		HtmlLayoutData hld;
		hld = new HtmlLayoutData("chat");


		ApplicationInstance app = ApplicationInstance.getActive();

		chatBean = new Chat();


		txtMsg = new TextArea();
		txtMsg.setMaximumLength(220);
		txtMsg.setWidth(new Extent(512));
		txtMsg.setHeight(new Extent(145));


		Row row = new Row();
		row.setCellSpacing(new Extent(20));

		txtMsg.setText(txtMsg.getText());

		row.add(txtMsg);

		row.add(txtMsg);

		col = new Column();

		mostrarMensajes();
		mostrarActivos();
		
		timedServerPush = new TimedServerPush(1000, app, taskQueue,
				new Runnable() {
			@Override
			public void run() {
				mostrarMensajes();
				mostrarActivos();

			}
		});

		timedServerPush.beg();

		//		addWindowPaneListener(new WindowPaneListener() {
		//
		//			@Override
		//			public void windowPaneClosing(WindowPaneEvent arg0) {
		//				timedServerPush.end();
		//
		//			}
		//		});

		Column col3 = new Column();

		col3.add(initPanel());


		//col.add(mostrarActivos());

		col3.setLayoutData(hld);
		htmllayaut.add(col3);

		hld = new HtmlLayoutData("banner2");

		Button btnRegresar = new Button("REGRESAR");
		btnRegresar.setStyle(GUIStyles.DEFAULT_STYLE);
		btnRegresar.setHeight(new Extent(20));
		btnRegresar.setAlignment(Alignment.ALIGN_BOTTOM);
		btnRegresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRegresarCliked();

			}
		});

		btnRegresar.setLayoutData(hld);
		htmllayaut.add(btnRegresar);

		return htmllayaut;

	}


	public Column initPanel(){

		Panel panel = new Panel();

		ResourceImageReference w = new ResourceImageReference("Images/Fondos/marco1.png");	
		ImageReference image = w;
		FillImage imagef = new FillImage(image);

		panel.setWidth(new Extent(801));
		panel.setHeight(new Extent(535));

		panel.setBackgroundImage(imagef);

		Column colp = new Column();
		colp.setCellSpacing(new Extent(90));

		Column colp2 = new Column();
		Row row = new Row();

		row.setCellSpacing(new Extent(30));
		row.add(panelMensajes());
		row.add(panelUsuarios());
		colp2.add(row);

		colp.add(colp2);

		row = new Row();
		row.setCellSpacing(new Extent(50));
		row.add(panelTexto());
		Button btnEnviar = new Button("Enviar");
		btnEnviar.setStyle(GUIStyles.DEFAULT_STYLE);
		btnEnviar.setHeight(new Extent(25));

		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnEnviarClicked();

			}
		});

		row.add(btnEnviar);

		colp.add(row);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;

	}

	
	public Component panelMensajes(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(360));
		panel.setWidth(new Extent(585));
		//panel.setBackground(Color.GREEN);
		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(5, 5, 5, 5));
		colp.add(txtCharla);

		panel.add(colp);

		colp = new Column();
		//colp.setBorder(new Border(5, Color.RED, 1));
		colp.add(panel);

		return colp;


	}

	public Component panelUsuarios(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(360));
		panel.setWidth(new Extent(786 - 585));
		//panel.setBackground(Color.GREEN);

		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(15, 10, 10, 10));
		colp.add(txtUsuarios);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;


	}

	public Component panelTexto(){

		Panel panel = new Panel();

		panel.setHeight(new Extent(512-450));
		panel.setWidth(new Extent(532));
		//panel.setBackground(Color.GREEN);

		panel.setAlignment(Alignment.ALIGN_LEFT);

		Column colp = new Column();
		colp.setInsets(new Insets(15, 0, 5, 5));
		colp.add(txtMsg);

		panel.add(colp);

		colp = new Column();
		colp.add(panel);

		return colp;


	}

	public void mostrarActivos() {

		txtUsuarios.setText("");

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		// --------------------------------------------------------------------------------


		List<Usuario> list = session.createCriteria(Usuario.class).list();
		for (Usuario obj : list) {


			if ( obj.isActivo() == true ){
			txtUsuarios.setText( obj.getLogin() + "\n" + txtUsuarios.getText());

			}

		}
		
		session.getTransaction().commit();
		session.close();

	}


	public void mostrarMensajes() {

		txtCharla.setText("");

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		Criteria criteria;


		//criteria = session.createCriteria(codesolids.bd.clases.Chat.class).addOrder(Order.desc("dateMsg")).list();
		List<codesolids.bd.clases.Chat> list =  session.createCriteria(codesolids.bd.clases.Chat.class).addOrder(Order.asc("id")).list();



		for (codesolids.bd.clases.Chat obj : list) {

			if ( obj.getDateMsg().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ){

				txtCharla.setText( obj.getLogin() + ">>" + obj.getMensaje() + "\n" + txtCharla.getText());

			}
		}

		session.getTransaction().commit();
		session.close();
	}

	private void btnEnviarClicked() {

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		// --------------------------------------------------------------------------------

		Chat chatBean = new Chat();


		PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
		Usuario usuario = pa.getUsuario();
		usuario = (Usuario) session.load(Usuario.class, usuario.getId());

		chatBean.setMensaje(txtMsg.getText());
		Calendar calendar = new GregorianCalendar();
		chatBean.setLogin(usuario.getLogin());
		chatBean.setDateMsg(calendar);


		session.save(chatBean);

		session.getTransaction().commit();
		session.close();

		txtMsg.setText("");

	}

	protected void btnRegresarCliked() {
		
		Session session = null;
		
		session = SessionHibernate.getInstance().getSession();
        session.beginTransaction();
        

		PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
		Usuario usuario = pa.getUsuario();
        
        
        Criteria criteria = session.createCriteria(Usuario.class).add(//
                                               Restrictions.eq("login", usuario.getLogin()));

        usuario = (Usuario) criteria.uniqueResult();
		
		usuario.setActivo(false);

		session.getTransaction().commit();
		session.close();

		timedServerPush.end();
		removeAll();
		add(new MapaDesktop());

	}

}