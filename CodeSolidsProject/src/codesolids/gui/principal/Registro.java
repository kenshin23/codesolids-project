package codesolids.gui.principal;


import java.awt.GridLayout;

import codesolids.gui.style.Styles1;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Border.Side;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;
import nextapp.echo.app.layout.GridLayoutData;


import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;
import echopoint.util.ExtentKit;

/**
* @author Karla Moreno
* 
*/

public class Registro extends WindowPane {
	
	private Column col;
	
	public Registro(){

		initGUI();
	}

	// --------------------------------------------------------------------------------

	public void initGUI() {

		add(initRegistro());

	}
	
	public Component initRegistro()
	{
		
		
		this.setTitle("Registro de Usuario");	
		this.setMinimumHeight(new Extent(400));
		this.setMinimumWidth(new Extent(200));
		this.setResizable(false);
		
		ResourceImageReference w = new ResourceImageReference("Images/fondo.jpg");		
		ImageReference image = w;
		FillImage imagep = new FillImage(image);
		
		this.setBackgroundImage(imagep);
		
		
		col = new Column();
		col.setCellSpacing(new Extent(50));
		col.setInsets(new Insets(new Extent(1), new Extent(20), new Extent(1), new Extent(1)));
		
		Row row = new Row();
		Label label = new Label("Nombre de Usuario");
		label.setForeground(Color.WHITE);
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(20));
		row.add(label);
		TextField fieldLogin = new TextField(); 
		row.add(fieldLogin);
		
		col.add(row);
		
	    row = new Row();
	    row.setAlignment(Alignment.ALIGN_CENTER);
	    row.setCellSpacing(new Extent(67));
	    label = new Label("Contraseña");
		label.setForeground(Color.WHITE);
	    row.add(label);
		PasswordField fieldPass = new PasswordField(); 
		row.add(fieldPass);
		
		col.add(row);

	    row = new Row();
	    row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(10));
		label = new Label("Confirme Contraseña");
		label.setForeground(Color.WHITE);
		row.add(label);
		PasswordField fieldPass2 = new PasswordField(); 
		row.add(fieldPass2);
		
		col.add(row);

	    row = new Row();
	    row.setAlignment(Alignment.ALIGN_CENTER);
		row.setCellSpacing(new Extent(90));
		label = new Label("E-mail:");
		label.setForeground(Color.WHITE);
		row.add(label);
		TextField fieldEmail = new TextField(); 
		row.add(fieldEmail);
		
		col.add(row);
		
		row = new Row();
		row.setAlignment(Alignment.ALIGN_CENTER);
		Button btnOk = new Button("Registrar");
		btnOk.setStyle(Styles1.DEFAULT_STYLE);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnOkClicked();
				
			}
		});
		
		row.add(btnOk);
		
		col.add(row);
		
		this.setModal(true);
		
		return col;
	}
	
	public void btnOkClicked(){
	
		col.add(new Label("Hola"));
	
	}

}
