package codesolids.gui.principal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.FillImageBorder;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.seleccion.DesktopSelect;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageBox;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;


/**
 * 
 * @author = Jose Luis Perez M.
 * @Colaborador = Antonio López
 * 
 */


public class PrincipalDesktop extends ContentPane {

	private TextField textLogin; 
	private PasswordField textPassword;

	private TextField fieldLogin;
	private TextField fieldEmail;
	private PasswordField fieldPass;
	private PasswordField fieldPass2;
	private WindowPane ventana;
	
	private Usuario usuario;

	public PrincipalDesktop(){
		initGUI();
	}

	private void initGUI() {
		add(initPrincipal());
	}

	private Component initPrincipal(){

		HtmlLayout htmllayaut;

		try {
			htmllayaut = new HtmlLayout(getClass().getResourceAsStream("index.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		ResourceImageReference w;
		ImageReference image;
		FillImage imageb;
		
		
		//Tabla del Login

		HtmlLayoutData hld;
		hld = new HtmlLayoutData("login");


		Column col = new Column();

		Row row = new Row();
		row.setCellSpacing(new Extent(30));
		Label la = new Label("Login:");
		la.setForeground(Color.WHITE);
		row.add(la);
		textLogin = new TextField();
		row.add(textLogin);
		col.add(row);

		row = new Row();
		la = new Label("Password:");
		la.setForeground(Color.WHITE);
		row.add(la);
		textPassword = new PasswordField();
		row.add(textPassword);
		col.add(row);

		Button btnAcceder = new Button();
		imageb = new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/acceder.png"));
		btnAcceder.setBackgroundImage(imageb);
		btnAcceder.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/accederMouseOver.png"))));
		btnAcceder.setRolloverEnabled(true);
		btnAcceder.setHeight(new Extent(30));
		btnAcceder.setWidth(new Extent(95));
		Column colAcceder = new Column();
		colAcceder.setInsets(new Insets(new Extent(60),new Extent(10)));
		colAcceder.add(btnAcceder);
		col.add(colAcceder);
		btnAcceder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnAccederClicked();    
			}
		});

		col.setLayoutData(hld);
		htmllayaut.add(col);

		//Tabla del registro

		hld = new HtmlLayoutData("register");
		col = new Column();

		Button btnRegistro = new Button();
		btnRegistro.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/registrar.png")));
		btnRegistro.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/registrarMouseOver.png"))));
		btnRegistro.setRolloverEnabled(true);
		btnRegistro.setHeight(new Extent(30));
		btnRegistro.setWidth(new Extent(95));

		btnRegistro.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
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
		row.setCellSpacing(new Extent(15));
		
		Button btnHistoria = new Button();

		btnHistoria.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/historia.png")));
		btnHistoria.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/historiaMouseOver.png")));
		btnHistoria.setRolloverEnabled(true);
		btnHistoria.setHeight(new Extent(30));
		btnHistoria.setWidth(new Extent(95));
		btnHistoria.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnHistoriaClicked();

			}
		});
		row.add(btnHistoria);

		Button btnPersonajes = new Button();
		btnPersonajes.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/personajes4.png")));
		btnPersonajes.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/personajes4MouseOver.png")));
		btnPersonajes.setRolloverEnabled(true);

		btnPersonajes.setHeight(new Extent(30));
		btnPersonajes.setWidth(new Extent(100));
		btnPersonajes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnPersonajesClicked();

			}
		});
		row.add(btnPersonajes);

		Button btnObjetos = new Button();
		btnObjetos.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/objetos.png")));
		btnObjetos.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/objetosMouseOver.png")));
		btnObjetos.setRolloverEnabled(true);
		
		btnObjetos.setHeight(new Extent(30));
		btnObjetos.setWidth(new Extent(100));
		row.add(btnObjetos);

		row.setLayoutData(hld);

		htmllayaut.add(row);

		return htmllayaut;

	}

	private void btnAccederClicked() {

		if(!validateCampoLogin())
		{

			Column col = new Column();
			col.add(new Label("Por favor ingrese todos sus datos, faltan algunos campos por rellenar."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);

			return;
		}

		Session session = null;

		try
		{
			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Usuario.class).add(//
					Restrictions.and(Restrictions.eq("login", textLogin.getText()),//
							Restrictions.eq("password", textPassword.getText())));

			usuario = (Usuario) criteria.uniqueResult();

			if (usuario == null)
			{
				Column col = new Column();
				col.add(new Label("Login o Contraseña incorrectos"));

				MessageBox messageBox  = new MessageBox("Error",// 
						col,// 
						400, 130,//
						MessageBox.ACCEPT_WINDOW);
				add(messageBox);
			}
			else
			{

				PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
				pa.setUsuario(usuario);

				removeAll();
				add(new DesktopSelect());
			}

		}finally
		{
			if(session != null)
			{
				if(session.getTransaction() != null)
				{
					session.getTransaction().commit();
				}

				session.close();
			}
		}

	}
	
	private boolean validateCampoLogin()
	{
		boolean ok = true;

		ok &= !textLogin.getText().equals("");
		ok &= !textPassword.getText().equals("");

		if(ok)
		{
			return true;
		}

		return false;
	}

	private void btnHistoriaClicked() {
		Info inf = new Info();
		add(inf.initHistoria());
	}
	
	private void btnPersonajesClicked() {
		Personajes inf = new Personajes();
		add(inf.initPersonaje());
	}

	private void btnRegistroClicked(){

		ventana = new WindowPane();
	
		ventana.setMinimumHeight(new Extent(400));
		ventana.setMinimumWidth(new Extent(200));
		ventana.setResizable(false);


		ventana.setBorder(new FillImageBorder(Color.BLACK, new Insets(new Extent(2)), new Insets(new Extent(2))));
		ventana.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Fondos/castillo2.jpg")));
		ventana.setTitleBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/borde3.png")));		
		
		ventana.setModal(true);
		
		ventana.add(initRegistro());
		
		add(ventana);
	}

	private Column initRegistro()
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(50));
		col.setInsets(new Insets(new Extent(1), new Extent(20), new Extent(1), new Extent(1)));

		Row row = new Row();
		Label label = new Label("Nombre de Usuario:");
		label.setForeground(Color.WHITE);
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(20));
		row.add(label);
		fieldLogin = new TextField();
		fieldLogin.setMaximumLength(20);
		row.add(fieldLogin);

		col.add(row);

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(67));
		label = new Label("Contraseña:");
		label.setForeground(Color.WHITE);
		row.add(label);
		fieldPass = new PasswordField(); 

		row.add(fieldPass);

		col.add(row);

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(10));
		label = new Label("Confirme Contraseña:");
		label.setForeground(Color.WHITE);
		row.add(label);
		fieldPass2 = new PasswordField(); 

		row.add(fieldPass2);

		col.add(row);

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(95));
		label = new Label("E-mail:");
		label.setForeground(Color.WHITE);
		row.add(label);
		fieldEmail = new TextField(); 
		row.add(fieldEmail);

		col.add(row);

		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		Button btnOk = new Button();
		
		FillImage imageb = new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/registrar.png")));
		btnOk.setBackgroundImage(imageb);
		btnOk.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/registrarMouseOver.png"))));
		btnOk.setRolloverEnabled(true);
		
		btnOk.setHeight(new Extent(30));
		btnOk.setWidth(new Extent(95));

		btnOk.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnRegistrarClicked();
			}
		});

		row.add(btnOk);

		col.add(row);

		return col;
	}
	
	private void btnRegistrarClicked() {
		
		if (!validateCampoRegister())
		{
			Column col = new Column();
			col.add(new Label("Por favor ingrese todos sus datos, faltan algunos campos por rellenar."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
			
			return;
		}
		else if( !isPassEquals() )
		{
			Column col = new Column();
			col.add(new Label("Las claves no son iguales."));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
			
			fieldPass.setText("");
			fieldPass2.setText("");
			
			return;			
			
		}
		else if( !isEmail(fieldEmail.getText()) )
		{
			Column col = new Column();
			col.add(new Label("El formato de la dirección electronica no es correcta, el formato es: weyard@example.com"));

			MessageBox messageBox  = new MessageBox("Error",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
			
			fieldEmail.setText("");
			
			return;
		}

		Session session = null;

		try{

			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();

			if(!checkUser(session))
			{				
				Column col = new Column();
				col.add(new Label("El nombre de usuario ya existe, intente con otro."));

				MessageBox messageBox  = new MessageBox("Error",// 
						col,// 
						400, 130,//
						MessageBox.ACCEPT_WINDOW);
				add(messageBox);
				
				fieldLogin.setText("");
				
				return;
			}	

			Usuario userBean = new Usuario();

			userBean.setLogin(fieldLogin.getText());
			userBean.setPassword(fieldPass.getText());
			userBean.setEmail(fieldEmail.getText());

			Calendar fechaRegister = new GregorianCalendar();
			userBean.setDateJoin(fechaRegister);

			session.save(userBean);

			Column col = new Column();
			col.add(new Label("Su cuenta ha sido registrada."));

			MessageBox messageBox  = new MessageBox("Felicitaciones",// 
					col,// 
					400, 130,//
					MessageBox.ACCEPT_WINDOW);
			add(messageBox);
			
			ventana.userClose();
		}

		finally
		{
			if(session != null)
			{
				if(session.getTransaction() != null)
				{
					session.getTransaction().commit();
				}
				session.close();
			}
		}

	}
	
	private boolean checkUser(Session session)
	{
		Criteria criteria = session.createCriteria(Usuario.class).add(Restrictions.eq("login", fieldLogin.getText()));

		if (criteria.list().size() != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampoRegister()
	{
		if (fieldLogin.getText().equals("")) {
			return false;
		}

		if (fieldPass.getText().equals("")) {
			return false;
		}

		if (fieldPass2.getText().equals("")) {
			return false;
		}
		
		if (fieldEmail.getText().equals("")) {
			return false;
		}
		
		return true;
	}
	
	private boolean isPassEquals()
	{
		if(fieldPass.getText().equals(fieldPass2.getText()))
			return true;
		else
			return false;
	}
	
	public boolean isEmail(String input) {
		
		Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*.)*([a-zA-Z].)+([a-zA-Z]{2,3}))$");
		Matcher mat = pat.matcher(input);
		
		if (mat.find())
			return true;
		else
			return false;        
	}
}

