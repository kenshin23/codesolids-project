/*
 * Created on 14/06/2011
 */
package codesolids.gui.perfil;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
//import nextapp.echo.app.Font;
//import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import codesolids.gui.mapa.MapaDesktop;
import codesolids.gui.style.Styles1;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

import codesolids.gui.perfil.User;
import codesolids.bd.clases.Usuario;

/**
 * 
 * @author Fernando Osuna
 *
 */

@SuppressWarnings("serial")
public class PerfilDesktop extends ContentPane {
	
	private Usuario usuario;
	
	private HtmlLayout htmlLayout;
	
	private Label lblImage;
	private Label lblData;
	
	private User user;
	private int indexPlayer = 0;
	
	public PerfilDesktop(Usuario usuario) {
		this.usuario = usuario;
		initGUI();
	
	}

	private void initGUI() {
		add(initPerfil());
		
	}

	private Component initPerfil() {
		try {
			htmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateiu.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		ResourceImageReference w = new ResourceImageReference("Images/pergamino.jpg");		
		ImageReference image = w;
	
		HtmlLayoutData hld;
		hld = new HtmlLayoutData("button");
		
		Row menu = new Row();
		Button selectButton = new Button();
		selectButton.setText("SELECCIONE");
		selectButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		selectButton.setHeight(new Extent(15));
		selectButton.setToolTipText("Seleccionar el perfil a ver");
		selectButton.setStyle(Styles1.DEFAULT_STYLE);
		selectButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button2Clicked(e);				
			}
		});
		menu.add(selectButton);
		menu.setCellSpacing(new Extent(50));
		
		Button returnButton = new Button();
		returnButton.setText("REGRESAR");
		returnButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		returnButton.setHeight(new Extent(15));
		returnButton.setToolTipText("Regresar al Mapa Principal");
		returnButton.setStyle(Styles1.DEFAULT_STYLE);
		returnButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button1Clicked(e);				
			}
		});
		menu.add(returnButton);
		menu.setLayoutData(hld);
		htmlLayout.add(menu);		
		
		hld = new HtmlLayoutData("body");
		
		user = new User();
		user.setLogin("USUARIO");
				
		Panel panelImage= new Panel();
		Row rowTab = new Row();
		Column colTab = new Column();
		
	    ResourceImageReference ir = new ResourceImageReference(user.getPlayers().get(indexPlayer).getImage());
		
		lblImage = new Label(ir);
		panelImage.add(lblImage);
		FillImage imagep = new FillImage(image);
		panelImage.setBackgroundImage(imagep);
		panelImage.setHeight(new Extent(213));
		panelImage.setWidth(new Extent(200));
		panelImage.setBorder(new Border(new Extent(5, Extent.PX), Color.LIGHTGRAY, Border.STYLE_SOLID));

		lblData = new Label("Datos Generales ");
		lblData.setBackground(Color.LIGHTGRAY);
		colTab.add(lblData);
		lblData = new Label("" + user.getLogin());
		colTab.add(lblData);
		lblData = new Label("Nombre " + user.getPlayers().get(indexPlayer).getLogin());
		colTab.add(lblData);
		lblData = new Label("Tipo " + user.getPlayers().get(indexPlayer).getType());
		colTab.add(lblData);
		lblData = new Label("Nivel " + user.getPlayers().get(indexPlayer).getLevel());
		colTab.add(lblData);
		lblData = new Label("XP " + user.getPlayers().get(indexPlayer).getXp());
		colTab.add(lblData);
		lblData = new Label("Oro " + user.getPlayers().get(indexPlayer).getGold());
		colTab.add(lblData);
		lblData = new Label("Atributos Generales");
		lblData.setBackground(Color.LIGHTGRAY);
		colTab.add(lblData);
		lblData = new Label("Vida "+ user.getPlayers().get(indexPlayer).getLife());
		colTab.add(lblData);
		lblData = new Label("Psinergia " + user.getPlayers().get(indexPlayer).getEnergy());
		colTab.add(lblData);
		lblData = new Label("Fuerza " + user.getPlayers().get(indexPlayer).getForce());
		colTab.add(lblData);
		lblData = new Label("Defenza " + user.getPlayers().get(indexPlayer).getDefense());
		colTab.add(lblData);
		lblData = new Label("Velocidad " + user.getPlayers().get(indexPlayer).getSpeed());
		colTab.add(lblData);
		lblData = new Label("Atributos Especiales");
		lblData.setBackground(Color.LIGHTGRAY);
		colTab.add(lblData);
		Button btnItem = new Button();
		btnItem.setText("Objetos");
		btnItem.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnItem.setHeight(new Extent(15));
		btnItem.setWidth( new Extent(60));
		btnItem.setToolTipText("Ver sus Objetos");
		btnItem.setStyle(Styles1.DEFAULT_STYLE);
		btnItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button3Clicked(e);				
			}
		});
		colTab.add(btnItem);
		Button btnPoderes = new Button();
		btnPoderes.setText("Poderes");
		btnPoderes.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnPoderes.setHeight(new Extent(15));
		btnPoderes.setWidth( new Extent(60));
		btnPoderes.setToolTipText("Ver sus Poderes");
		btnPoderes.setStyle(Styles1.DEFAULT_STYLE);
		btnPoderes.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			button4Clicked(e);				
			}
		});
		colTab.add(btnPoderes);
		colTab.setBackground(Color.WHITE);
		colTab.setBorder( new Border(new Extent(2), Color.LIGHTGRAY, Border.STYLE_SOLID));
		rowTab.add(panelImage);
		rowTab.setCellSpacing(new Extent(15));
		rowTab.add(colTab);
		
		rowTab.setLayoutData(hld);
		htmlLayout.add(rowTab);
		
		
		return htmlLayout;
	}
	
	private void button1Clicked(ActionEvent e) {		
		removeAll();
		add(new MapaDesktop(usuario));
	}
	
	private void button2Clicked(ActionEvent e) {
		final WindowPane win = new WindowPane();
		win.setTitle("Seleccione el perfil");
		win.setWidth(new Extent(300));
		win.setMaximumWidth(new Extent(300));
		win.setMaximumHeight(new Extent(100));
		win.setMovable(false);
		win.setResizable(false);
		Row row = new Row();
		for( int i = 0; i < user.getPlayers().size(); i++){
			Button button = new Button();
			button.setText(user.getPlayers().get(i).getLogin());
			button.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
			button.setHeight(new Extent(15));
			button.setToolTipText("Seleccione " + user.getPlayers().get(i).getLogin()+ " Tipo " +user.getPlayers().get(i).getType());
			button.setStyle(Styles1.DEFAULT_STYLE);
			final int j = i;
			button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indexPlayer = j;				
				removeAll();
				add(initPerfil());
				}
			});
			row.add(button);
			row.setCellSpacing(new Extent(10));
		}
		row.setAlignment(Alignment.ALIGN_CENTER);
		row.setInsets(new Insets(15));
		win.add(row);
		add(win);
	}
	
	private void button3Clicked(ActionEvent e) {
		final WindowPane win = new WindowPane();
		win.setTitle("Objetos que Posee");
		win.setWidth(new Extent(300));
		win.setMaximumWidth(new Extent(200));
		win.setMaximumHeight(new Extent(300));
		win.setMovable(false);
		win.setResizable(false);
		Column col = new Column();
		for( int i = 0; i < user.getPlayers().get(indexPlayer).getItems().size(); i++){
			lblData = new Label(user.getPlayers().get(indexPlayer).getItems().get(i).getName());
			col.add(lblData);			
		}
		Button button = new Button();
		button.setText("Aceptar");
		button.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		button.setHeight(new Extent(15));
		button.setWidth( new Extent(60));
		button.setToolTipText("Regresar al perfil");
		button.setStyle(Styles1.DEFAULT_STYLE);
		button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			win.userClose();
			}
		});
		col.add(button);
		col.setInsets(new Insets(25));
		win.add(col);
		add(win);
	}
	
	private void button4Clicked(ActionEvent e) {
		final WindowPane win = new WindowPane();
		win.setTitle("Poderes que Posee");
		win.setWidth(new Extent(300));
		win.setMaximumWidth(new Extent(200));
		win.setMaximumHeight(new Extent(300));
		win.setMovable(false);
		win.setResizable(false);
		Column col = new Column();
		for( int i = 0; i < user.getPlayers().get(indexPlayer).getPowers().size(); i++){
			lblData = new Label(user.getPlayers().get(indexPlayer).getPowers().get(i).getName());
			col.add(lblData);			
		}
		Button button = new Button();
		button.setText("Aceptar");
		button.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		button.setHeight(new Extent(15));
		button.setWidth( new Extent(60));
		button.setToolTipText("Regresar al perfil");
		button.setStyle(Styles1.DEFAULT_STYLE);
		button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			win.userClose();
			}
		});
		col.add(button);
		col.setInsets(new Insets(25));
		win.add(col);
		add(win);
	}

}
