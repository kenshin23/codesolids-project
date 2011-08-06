package codesolids.gui.editar;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;

import org.hibernate.Session;

import codesolids.bd.clases.Usuario;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.mapa.Styles;
import codesolids.gui.principal.PrincipalApp;
import codesolids.util.ImageReferenceCache;

/**
* @author Hector Prada
* 
*/

public class EditarDatos extends WindowPane{
	
	private Label labelPassword1;
	private Label labelPassword2;
	private Label labelEmail;
	
	private PasswordField txtPassword1;
	private PasswordField txtPassword2;
	private TextField txtEmail;
	
	private Usuario usuario;
	
	public EditarDatos() {
		super();

		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		usuario = app.getUsuario();
		
		initWindowPane_User();
	}	

	private void initWindowPane_User(){
		
		this.setEnabled(true);
		this.setTitle("EDITAR DATOS DE USUARIO");
		this.setHeight(new Extent(300, Extent.PX));
		this.setVisible(true);
		this.setClosable(true);
		this.setTitleBackground(new Color(0x3a3aff));
		this.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		this.setWidth(new Extent(358, Extent.PX));
		this.setResizable(false);
		this.setMovable(false);
		this.setModal(true);
		
		ImageReference ir = ImageReferenceCache.getInstance().getImageReference("/Images/fondoEditarDatos.jpg");
		this.setBackgroundImage(new FillImage(ir));
		this.add(initData());
	}
	
	private Column initData()
	{		
		Column col = new Column();
		col.setCellSpacing(new Extent(15));
		col.setInsets(new Insets(10, 20, 10, 20));
		
		Row row = new Row();
		row.setCellSpacing(new Extent(12));
		
		labelPassword1 = new Label();
		labelPassword1.setForeground(Color.WHITE);
		labelPassword1.setText("Nueva Contraseña: ");
		row.add(labelPassword1);
		
		txtPassword1 = new PasswordField();
		row.add(txtPassword1);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(9));
		
		labelPassword2 = new Label();
		labelPassword2.setForeground(Color.WHITE);
		labelPassword2.setText("Repetir Contraseña: ");
		row.add(labelPassword2);
		
		txtPassword2 = new PasswordField();
		row.add(txtPassword2);
		col.add(row);
		
		row = new Row();
		row.setCellSpacing(new Extent(45));
		
		labelEmail = new Label();
		labelEmail.setForeground(Color.WHITE);
		labelEmail.setText("Nuevo Email: ");
		row.add(labelEmail);
		
		txtEmail = new TextField();
		txtEmail.setText(usuario.getEmail());
		row.add(txtEmail);
		col.add(row);
		
		row = new Row();
		row.setInsets(new Insets(15, 0, 15, 0));
		row.setCellSpacing(new Extent(10));
		
		Button btnAceptar = new Button();
		btnAceptar.setEnabled(true);
		btnAceptar.setText("Aceptar");
		btnAceptar.setVisible(true);
		btnAceptar.setStyle(Styles.BOTONES_LOGIN_SCREEN);
		RowLayoutData btnAceptarLayoutData = new RowLayoutData();
		btnAceptarLayoutData.setInsets(new Insets(new Extent(80, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnAceptar.setLayoutData(btnAceptarLayoutData);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAceptarClicked();
			}
		});		
		row.add(btnAceptar);
		
		Button btnCancelar = new Button();
		btnCancelar.setEnabled(true);
		btnCancelar.setText("Cancelar");
		btnCancelar.setVisible(true);
		btnCancelar.setStyle(Styles.BOTONES_LOGIN_SCREEN);
		RowLayoutData btnCancelarLayoutData = new RowLayoutData();
		btnCancelarLayoutData.setInsets(new Insets(new Extent(30, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnCancelar.setLayoutData(btnCancelarLayoutData);
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelarClicked();
			}
		});
		row.add(btnCancelar);
		
		col.add(row);
		
		return col;
	}
	
	private void btnAceptarClicked() 
	{
		
		if(!validateCampo())
		{
			//Aqui debe ir una Advertencia
			return;
		}
		else if( !isPassEquals() )
		{
			//Aqui debe ir una Advertencia
			return;
		}
		else if(!isEmail(txtEmail.getText()))
		{
			//Aqui debe ir una Advertencia
			return;
		}

		Session session = null;

		try{

			session = SessionHibernate.getInstance().getSession();
			session.beginTransaction();
						
			usuario = (Usuario) session.load(Usuario.class, usuario.getId());
			
			usuario.setEmail(txtEmail.getText());
			usuario.setPassword(txtPassword1.getText());
			
			PrincipalApp pa = (PrincipalApp) ApplicationInstance.getActive();
			pa.setUsuario(usuario);
			
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
	
	private void btnCancelarClicked() {
		this.userClose();
	}
	
	private boolean validateCampo()
	{
		if (txtPassword1.getText().equals("")) {
			return false;
		}

		if (txtPassword2.getText().equals("")) {
			return false;
		}

		if (txtEmail.getText().equals("")) {
			return false;
		}
		
		return true;
	}
	
	public boolean isEmail(String input) {
    
		Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*.)*([a-zA-Z].)+([a-zA-Z]{2,3}))$");
		Matcher mat = pat.matcher(input);
		
		if (mat.find())
			return true;
		else
			return false;    
	}
	
	private boolean isPassEquals()
	{
		if(txtPassword1.getText().equals(txtPassword2.getText()))
			return true;
		else
			return false;
	}
}
