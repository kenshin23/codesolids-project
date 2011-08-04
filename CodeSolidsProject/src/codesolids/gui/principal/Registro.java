package codesolids.gui.principal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
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
import codesolids.gui.style.Styles1;

/**
* @author Karla Moreno
* 
* 
*/

public class Registro extends WindowPane{
	
	private TextField fieldLogin;
	private TextField fieldEmail;
	private PasswordField fieldPass;
	private PasswordField fieldPass2;
	
	public Registro(){
		
		this.setTitle("Registro de Usuario");	
		this.setMinimumHeight(new Extent(400));
		this.setMinimumWidth(new Extent(200));
		this.setResizable(false);
		
		ResourceImageReference w = new ResourceImageReference("Images/fondo2.jpg");		
		ImageReference image = w;
		FillImage imagep = new FillImage(image);
		
		this.setBackgroundImage(imagep);
		
		this.setModal(true);
		
		initGUI();
	}

	public void initGUI() {

		add(initRegistro());
	}
	
	public Component initRegistro(){
			
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
		row.setCellSpacing(new Extent(90));
		label = new Label("E-mail:");
		label.setForeground(Color.WHITE);
		row.add(label);
		fieldEmail = new TextField(); 
		row.add(fieldEmail);
		
		col.add(row);
		
		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		Button btnOk = new Button("Registrar");
		btnOk.setStyle(Styles1.DEFAULT_STYLE);
		btnOk.setHeight(new Extent(20));
		btnOk.setWidth(new Extent(50));
		
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
	
//************************ METODO PARA VALIDAR CORREO ****************************************************
	public boolean isEmail(String input) {
		
		Pattern pat = null;
		Matcher mat = null;        
		pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		mat = pat.matcher(input);
		if (mat.find()) {
			return true;
		}else{
			return false;
		}        
	}

//*****************************************************************************************************************		  

	private boolean isPassEquals()
	{
		if(fieldPass.getText().equals(fieldPass2.getText()))
			return true;
		else
			return false;
	}
	
	private void btnRegistrarClicked() {
		
		if (!validateCampo())
		{
					
			return;
		}
		else if( !isPassEquals() )
		{
//			CrearVentana2();
			return;
		}
		else if( !isEmail(fieldEmail.getText()) )
		{
//			CrearVentana2();
			return;
		}
		
		Session session = null;
		
		try{
			
			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();
			
			if(!checkUser(session))
			{				
				//Aqui debe ir Mensaje de Advertencia
				return;
			}	
			
			Usuario userBean = new Usuario();
			
			userBean.setLogin(fieldLogin.getText());
			userBean.setPassword(fieldPass.getText());
			userBean.setEmail(fieldEmail.getText());
			
			Calendar fechaRegister = new GregorianCalendar();
			userBean.setDateJoin(fechaRegister);
			
			session.save(userBean);
			
			this.userClose();
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
	
	
	private boolean validateCampo()
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
			
}
