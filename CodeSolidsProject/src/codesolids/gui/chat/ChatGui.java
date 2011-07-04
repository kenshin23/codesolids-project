package codesolids.gui.chat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.FillImageBorder;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.Window;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.bd.clases.Chat;
import codesolids.gui.academia.TimedServerPush;

import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.GUIStyles;

/**
 * 
 * @author = Jose Luis Perez M.
 * 
 */

public class ChatGui extends WindowPane {

	private TextArea txtCharla;
	private TextArea txtMsg;
	private SplitPane splitPane2;
	private Column col;

	TaskQueueHandle taskQueue;
	TimedServerPush timedServerPush;

	// private Session session;

	private Chat chatBean;

	public ChatGui() {
		taskQueue = ApplicationInstance.getActive().createTaskQueue();
		initGUI();
	}

	// --------------------------------------------------------------------------------

	public void initGUI() {

		ApplicationInstance app = ApplicationInstance.getActive();
		
		chatBean = new Chat();

		ResourceImageReference w = new ResourceImageReference("Images/Fondos/usuario.jpg");	
		ImageReference image = w;
		FillImage imagef = new FillImage(image);
		
		
		this.setTitle("Chat");
		this.setMinimumHeight(new Extent(300));
		this.setMinimumWidth(new Extent(650));
		this.setResizable(false);
		this.setBackgroundImage(imagef);
		
		txtMsg = new TextArea();
		txtMsg.setWidth(new Extent(400));

		SplitPane splitPane = new SplitPane();
		splitPane.setSeparatorWidth(new Extent(5));
		splitPane.setSeparatorColor(Color.BLUE);
		splitPane.setSeparatorPosition(new Extent(150));
		//splitPane.set
		splitPane.setResizable(true);

		splitPane.add(mostrarActivos());

		splitPane2 = new SplitPane();
		splitPane2.setSeparatorWidth(new Extent(5));
		splitPane2.setOrientation(splitPane2.ORIENTATION_VERTICAL);
		splitPane2.setSeparatorPosition(new Extent(210));
		splitPane2.setResizable(true);
		splitPane2.setSeparatorColor(Color.BLUE);
				
		col = new Column();
		
		splitPane2.add(col);
		
		Row row = new Row();

		txtMsg.setText(txtMsg.getText());

		row.add(txtMsg);

		Button btnEnviar = new Button("Enviar");
		btnEnviar.setStyle(GUIStyles.DEFAULT_STYLE);

		row.add(txtMsg);
		row.add(btnEnviar);

		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnEnviarClicked();

			}
		});

		splitPane2.add(row);
		
		
		splitPane.add(splitPane2);

		// Configuration configuration = new Configuration();
		// configuration.configure("hibernate.cfg.xml");
		// SessionFactory sessionFactory = configuration.buildSessionFactory();

		// --------------------------------------------------------------------------------

		// session = sessionFactory.openSession();
		// session.beginTransaction();

		timedServerPush = new TimedServerPush(1000, app, taskQueue,
				new Runnable() {
					@Override
					public void run() {
						mostrarMensajes();

					}
				});

		timedServerPush.beg();
		
		addWindowPaneListener(new WindowPaneListener() {
			
			@Override
			public void windowPaneClosing(WindowPaneEvent arg0) {
				timedServerPush.end();
				
			}
		});
		
		
		add(splitPane);

	}

	
public Component mostrarActivos() {

		Column col = new Column();

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		// --------------------------------------------------------------------------------


		List<Usuario> list = session.createCriteria(Usuario.class).list();
		for (Usuario obj : list) {
			
			
			System.out.println(obj.getLogin());
			col.add(new Label(obj.getLogin()));
		}
		session.getTransaction().commit();
		session.close();

		return col;
	}

	@SuppressWarnings("deprecation")
	public void mostrarMensajes() {

		col.removeAll();
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		List<codesolids.bd.clases.Chat> list = session.createCriteria(
				codesolids.bd.clases.Chat.class).list();
		for (codesolids.bd.clases.Chat obj : list) {
			
			System.out.println(obj.getMensaje());
			col.add(new Label( obj.getLogin() + ">>" + obj.getMensaje() ));
			
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
		Calendar fechaRegister = new GregorianCalendar();
		chatBean.setLogin(usuario.getLogin());
		chatBean.setDateMsg(fechaRegister);
		
		session.save(chatBean);

		session.getTransaction().commit();
		session.close();

		txtMsg.setText("");

	}

}
