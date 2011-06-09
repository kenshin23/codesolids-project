package codesolids.gui.registrodeusuario;

import java.awt.GridLayout;

import codesolids.gui.style.GUIStyles;

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

public class Desktop extends ContentPane {
	private HtmlLayout htmlLayout;
		
	private Label info;
	private Label espacio;
	private TextField nombre;
	private TextField correo;
	private TextField contrasena;
	private TextField check_contrasena;
	
	private Grid grid;
	
	//private GridLayout gridLayaout;
	//private Component comp;
	//hacerlo para GridLAyaout 
	WindowPane ventana;
	
	
		
	public Desktop() {
		initGUI();
	
	}

	private void initGUI() {
		add(initFormulario());
		
	}
	
	private Component initFormulario()
	{
		
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
			
		ResourceImageReference ir = new ResourceImageReference("Images/Registro/art.jpg");
		//imagenes que me gustan de fondo: art, fantasy, city , drak, hd, land,op
		ResourceImageReference w = new ResourceImageReference("Images/Registro/clean.png");
		htmlLayout.setBackgroundImage(ir);
		
		ImageReference image = w;
	
		grid = new Grid(4);
		grid.setInsets(new Insets(2, 2, 2, 2));
		HtmlLayoutData hld;
		//GridLayoutData grid;
		
		
		hld = new HtmlLayoutData("central");
		//grid = new GridLayoutData();
		
		nombre = new TextField();  		
		correo = new TextField();
		contrasena = new TextField();
		check_contrasena = new TextField();
		
		Row row = new Row();
		Column col = new Column();
		//Column col2 = new Column();
		
		
	    //grid.setBackground(Color.LIGHTGRAY);
	    
		info = new Label("Registro de Usuario");
		//col.add(info);
		grid.add(info);
		espacio = new Label("                 ");
		grid.add(espacio);
		espacio = new Label("                 ");
		grid.add(espacio);
		espacio = new Label("                 ");
		grid.add(espacio);
		
		
		info = new Label("Ingrese Nombre de usuario");
		grid.add(info);
		col.setLayoutData(hld);
		htmlLayout.add(col);
		//col2.add(nombre);
		grid.add(nombre);
		espacio = new Label("                 ");
		grid.add(espacio);
		espacio = new Label("                 ");
		grid.add(espacio);
		
		Panel panel = new Panel();
		
		col.setCellSpacing(new Extent(10));
		info = new Label("Ingrese Email:");
		grid.add(info);
		//col2.add(correo);
		grid.add(correo);
		espacio = new Label("                 ");
		grid.add(espacio);
		espacio = new Label("                 ");
		grid.add(espacio);
		
		info = new Label("Ingrese Contraseña");
		col.add(info);
		//col2.add(contrasena);
		grid.add(info);
		grid.add(contrasena);
		espacio = new Label("                 ");
		grid.add(espacio);
		espacio = new Label("                 ");
		grid.add(espacio);
		
		info = new Label("Confirme Contraseña");
		grid.add(info);
		//col2.add(check_contrasena);
		grid.add(check_contrasena);
		
		row.add(grid);
		//row.add(col2);
		
		FillImage imagep = new FillImage(image);
		
		panel.add(row);
		panel.setLayoutData(hld);
		panel.setBackgroundImage(imagep);
		panel.setHeight(new Extent(385));
		panel.setWidth(new Extent(460));
		panel.setBorder(new Border(new Extent(10, Extent.PX), new Color(0xd4630c), Border.STYLE_NONE));
		htmlLayout.add(panel);
		
		return htmlLayout;
	}

	private java.awt.Component Label() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

	

