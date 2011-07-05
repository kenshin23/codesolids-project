package codesolids.gui.principal;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.gui.style.Styles1;

/**
* @author Hector Prada
* 
*/

public class PreEditarDatos extends WindowPane{
	
	
	
	private String loginString = "";
	private String passwString = "";
	private TextField loginField;
	private PasswordField passwField;
	
	private String editUserString = "";
	private String editPasswString = "";
	private String editRePasswString = "";
	private PasswordField editPasswField;
	private PasswordField editRePasswField;
	private TextField editUserField;
	
	private WindowPane msgError;
	private WindowPane msgExito;



	Usuarios uno = new Usuarios();
	

	public PreEditarDatos() {
		super();

		initWindowPane_User();
	}
	
	private void initWindowPane_User(){
		
		this.setEnabled(true);
		this.setTitle("EDITAR DATOS DE USUARIO");
		this.setHeight(new Extent(400, Extent.PX));
		this.setVisible(true);
		this.setClosable(true);
		this.setTitleBackground(new Color(0x3a3aff));
		this.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		this.setWidth(new Extent(358, Extent.PX));
		this.setResizable(false);
		this.setMovable(false);
		uno.setUserName("rosa");
		uno.setPassw("meltroso");
		uno.setEmail("rosa@gmail.com");
		
		ResourceImageReference ir = new ResourceImageReference("/Images/fondoEditarDatos.jpg");
		this.setBackgroundImage(new FillImage(ir));
		add(new preEditDatos());
		
		
	}
	
	
	public class EditarDatos extends ContentPane{
	
		public EditarDatos() {
		super();

		initEditarDatos();
	}
	
		private void initEditarDatos(){

		
		this.setEnabled(true);
		this.setVisible(true);
		
		Column col = new Column();
		col.setEnabled(true);
		col.setVisible(true);
		col.setInsets(new Insets(new Extent(10, Extent.PX), new Extent(0,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		col.setCellSpacing(new Extent(40, Extent.PX));
		this.add(col);
		
		Label msgInstruc = new Label();
		msgInstruc.setEnabled(true);
		msgInstruc.setText("Si no desea cambiar su login deje el primer campo en blanco. Si no desea cambiar su contraseña deje en blanco los dos ultimos campos.");
		msgInstruc.setVisible(true);
		msgInstruc.setFont(new Font(null, Font.ITALIC | Font.UNDERLINE,new Extent(10, Extent.PT)));
		msgInstruc.setForeground(Color.WHITE);
		col.add(msgInstruc);
		
		Row filaLogin = new Row();
		filaLogin.setEnabled(true);
		filaLogin.setVisible(true);
		filaLogin.setCellSpacing(new Extent(53, Extent.PX));
		col.add(filaLogin);
		
		Label loginLbl = new Label();
		loginLbl.setEnabled(true);
		loginLbl.setText("Ingrese Nuevo Login");
		loginLbl.setVisible(true);
		loginLbl.setForeground(Color.WHITE);
		filaLogin.add(loginLbl);
		
		editUserField = new TextField();
		editUserField.setEnabled(true);
		editUserField.setVisible(true);
		editUserField.setWidth(new Extent(150, Extent.PX));
		editUserField.setEditable(true);
		editUserField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				editUser(e);
			}
		});
		filaLogin.add(editUserField);
		
		Row filaPassw = new Row();
		filaPassw.setEnabled(true);
		filaPassw.setVisible(true);
		filaPassw.setCellSpacing(new Extent(18, Extent.PX));
		col.add(filaPassw);
		
		Label passwLbl = new Label();
		passwLbl.setEnabled(true);
		passwLbl.setText("Ingrese Nueva Contraseña");
		passwLbl.setVisible(true);
		passwLbl.setForeground(Color.WHITE);
		filaPassw.add(passwLbl);
		
		editPasswField = new PasswordField();
		editPasswField.setEnabled(true);
		editPasswField.setVisible(true);
		editPasswField.setWidth(new Extent(150, Extent.PX));
		editPasswField.setEditable(true);
		editPasswField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				editPassw(e);
			}
		});
		filaPassw.add(editPasswField);
		
		Row filaReInsertPassw = new Row();
		filaReInsertPassw.setEnabled(true);
		filaReInsertPassw.setVisible(true);
		filaReInsertPassw.setCellSpacing(new Extent(5, Extent.PX));
		col.add(filaReInsertPassw);
		
		Label rePasswLbl = new Label();
		rePasswLbl.setEnabled(true);
		rePasswLbl.setText("Reingrese Nueva Contraseña");
		rePasswLbl.setVisible(true);
		rePasswLbl.setForeground(Color.WHITE);
		filaReInsertPassw.add(rePasswLbl);
		
		editRePasswField = new PasswordField();
		editRePasswField.setEnabled(true);
		editRePasswField.setVisible(true);
		editRePasswField.setWidth(new Extent(150, Extent.PX));
		editRePasswField.setEditable(true);
		editRePasswField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				editRePassw(e);
			}
		});
		filaReInsertPassw.add(editRePasswField);
		
		Row filaBtn = new Row();
		filaBtn.setEnabled(true);
		filaBtn.setVisible(true);
		//filaBtn.setInsets(new Insets(new Extent(70, Extent.PX), new Extent(0,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		filaBtn.setCellSpacing(new Extent(30, Extent.PX));
		col.add(filaBtn);
		
		Button btnAceptar = new Button();
		btnAceptar.setEnabled(true);
		btnAceptar.setText("Aceptar Cambios");
		btnAceptar.setVisible(true);
		btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAceptarCambiosClicked(e);
			}
		});
		filaBtn.add(btnAceptar);
		
		Button btnCancelar = new Button();
		btnCancelar.setEnabled(true);
		btnCancelar.setText("Cancelar");
		btnCancelar.setVisible(true);
		btnCancelar.setStyle(Styles1.DEFAULT_STYLE);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCancelEditClicked(e);
			}
		});
		filaBtn.add(btnCancelar);
		
		Button btnRegresar = new Button();
		btnRegresar.setEnabled(true);
		btnRegresar.setText("Regresar");
		btnRegresar.setVisible(true);
		btnRegresar.setStyle(Styles1.DEFAULT_STYLE);
		btnRegresar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRegresarClicked(e);
			}
		});
		
		filaBtn.add(btnRegresar);
		
		msgExito = new WindowPane();
		msgExito.setHeight(new Extent(100, Extent.PX));
		msgExito.setWidth(new Extent(200, Extent.PX));
		msgExito.setStyleName("Default");
		msgExito.setTitle("*********EXITO********");
		msgExito.setVisible(false);
		msgExito.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		msgExito.setBackground(new Color(0x6363b6));
		msgExito.setMovable(true);
		add(msgError);
	
		Label exito = new Label();
		exito.setText("EXITO, SU INFORMACION HA SIDO ACTUALIZADA");
		msgExito.add(exito);
		
	}
		
	
	}
	
	public class preEditDatos extends ContentPane{
		
		public preEditDatos() {
			super();

			initPreEditarDatos();
		}
	
		private void initPreEditarDatos(){

		Column col = new Column();
		col.setInsets(new Insets(new Extent(15, Extent.PX), new Extent(80,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		col.setCellSpacing(new Extent(50, Extent.PX));
		add(col);
		
		Row filalogin = new Row();
		filalogin.setCellSpacing(new Extent(55, Extent.PX));
		col.add(filalogin);
		
		Label loginLbl = new Label();
		loginLbl.setText("Login");
		loginLbl.setFont(new Font(null, Font.PLAIN, new Extent(10, Extent.PT)));
		loginLbl.setForeground(Color.WHITE);
		filalogin.add(loginLbl);
		
		loginField = new TextField();
		loginField.setEnabled(true);
		loginField.setVisible(true);
		loginField.setWidth(new Extent(150, Extent.PX));
		loginField.setEditable(true);
		loginField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				askLogin(e);
			}
		});
		filalogin.add(loginField);
		
		Row filaPassw = new Row();
		filaPassw.setCellSpacing(new Extent(18, Extent.PX));
		col.add(filaPassw);
		
		Label passwLbl = new Label();
		passwLbl.setText("Contraseña");
		passwLbl.setForeground(Color.WHITE);
		filaPassw.add(passwLbl);
		
		passwField = new PasswordField();
		passwField.setEnabled(true);
		passwField.setVisible(true);
		passwField.setWidth(new Extent(150, Extent.PX));
		passwField.setEditable(true);
		passwField.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				askPassw(e);
			}
		});
		filaPassw.add(passwField);
		
		Row filaBtn = new Row();
		filaBtn.setCellSpacing(new Extent(50, Extent.PX));
		filaBtn.setInsets(new Insets(new Extent(40, Extent.PX), new Extent(0,Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		col.add(filaBtn);
		
		Button btnAcceder = new Button();
		btnAcceder.setText("Acceder");
		btnAcceder.setStyle(Styles1.DEFAULT_STYLE);
		btnAcceder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAccederClicked(e);
			}

		
		});
		filaBtn.add(btnAcceder);
		
		Button btnCancelar = new Button();
		btnCancelar.setText("Cancelar");
		btnCancelar.setStyle(Styles1.DEFAULT_STYLE);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCancelarClicked(e);
			}

		
		});
		filaBtn.add(btnCancelar);
		
		msgError = new WindowPane();
		msgError.setHeight(new Extent(100, Extent.PX));
		msgError.setWidth(new Extent(200, Extent.PX));
		msgError.setStyleName("Default");
		msgError.setTitle("*********ERROR********");
		msgError.setVisible(false);
		msgError.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		msgError.setBackground(new Color(0x6363b6));
		msgError.setMovable(true);
		add(msgError);
	
		Label error = new Label();
		error.setText("ERROR, PORFAVOR REINGRESE SU INFORMACION");
		msgError.add(error);
		

		

	}
		}
	
	
	//metodos para el PreditDatos
	
	
	private void askLogin(PropertyChangeEvent e) {
		this.loginString = (String) e.getNewValue();

		
	}
	
	private void askPassw(PropertyChangeEvent e) {

		this.passwString = (String) e.getNewValue();

	}
	
	private void btnCancelarClicked(ActionEvent e) {
		passwField.setText("");
		loginField.setText("");

	}
	
	private void btnAccederClicked(ActionEvent e) {
		boolean valid = false;
		if (this.loginString.equals(uno.getUserName()) && this.passwString.equals(uno.getPassw()) )
		{
			valid = true;
		}


		if (valid) {
			this.removeAll();
			add(new EditarDatos());
			loginField.setText("");
			passwField.setText("");
			
			
		} else {
			loginField.setText("");
			passwField.setText("");
			this.msgError.setVisible(true);

		}
	}
	
	
	//metodos para el EditDatos
	
	private void editUser(PropertyChangeEvent e) {
		this.editUserString = (String) e.getNewValue();

	}
	
	private void editPassw(PropertyChangeEvent e) {
		this.editPasswString = (String) e.getNewValue();

	}
	
	
	
	
	private void editRePassw(PropertyChangeEvent e) {

		this.editRePasswString = (String) e.getNewValue();

	}
	
	private void btnRegresarClicked(ActionEvent e) {
		this.removeAll();
		this.add(new preEditDatos());
	//para que cuando regrese al contentpane de PREEDITARDATOS, alguien no pueda entrar con solo darle acceder.
		//esta misma validacion esta en btnAccederClicked
		loginField.setText("");
		passwField.setText("");
	}
	
	private void btnCancelEditClicked(ActionEvent e) {
		editPasswField.setText("");
		editUserField.setText("");
		editRePasswField.setText("");

	}
	
	private void btnAceptarCambiosClicked(ActionEvent e) {
		boolean valid = false;
		
		if( (this.editUserString.equals("") && this.editPasswString.equals("") && this.editRePasswString.equals("")) )
		{
			valid=false;
		}
		
		if (this.editUserString == "" && this.editPasswString != "" && this.editRePasswString != "" )
		{
			if(this.editPasswString.equals(this.editRePasswString) )
			{
				uno.setPassw(this.editPasswString);
				valid=true;
			}
		}
		
		if (this.editUserString != "" && this.editPasswString != "" && this.editRePasswString != "" )  
		{
			if (this.editPasswString.equals(this.editRePasswString) )
			{
				uno.setUserName(this.editUserString);
				uno.setPassw(this.editPasswString);
				valid=true;
		
			}
		}
		
		if (this.editUserString != "" && this.editPasswString == "" && this.editRePasswString == "" )
		{
			uno.setUserName(this.editUserString);
			valid=true;
		}
		
		if (valid) {
			
			//this.msgError.setVisible(false);
			this.msgExito.setVisible(true);
			editUserField.setText("");
			editPasswField.setText("");
			editRePasswField.setText("");
			
		} else {
			editUserField.setText("");
			editPasswField.setText("");
			editRePasswField.setText("");
			this.msgError.setVisible(true);

		}
	}
	
	
	//creacion de una clase usuario para mantener en memoria.
	
	public class Usuarios{
		private String userName;
		private String passw;
		private String email;
		
		public String getUserName(){
			return userName;
		}
		
		public String getPassw(){
			return passw;
		}
		
		public String getEmail(){
			return email;
		}
		
		public void setUserName(String userName){
			this.userName=userName;
		}
		
		public void setPassw(String passw){
			this.passw=passw;
		}
		
		public void setEmail(String email){
			this.email=email;
		}
	};
	
}




