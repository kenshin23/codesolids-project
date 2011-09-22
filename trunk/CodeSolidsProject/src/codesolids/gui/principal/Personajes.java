package codesolids.gui.principal;

import codesolids.util.ImageReferenceCache;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.FillImageBorder;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;

public class Personajes {
	
	public Component initPersonaje(){
		
		WindowPane windowPane = new WindowPane();
		windowPane.setMaximumHeight(new Extent(550));
		windowPane.setMinimumWidth(new Extent(440));
		windowPane.setResizable(false);
		windowPane.setPositionX(new Extent(400));
		windowPane.setPositionY(new Extent(50));
		windowPane.setModal(true);
				
		windowPane.setBorder(new FillImageBorder(Color.BLACK, new Insets(new Extent(1)), new Insets(new Extent(1))));
			
		FillImage imagep = new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Fondos/FondoP.jpg"));
		windowPane.setBackgroundImage(imagep);

		imagep = new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/personajesBar.png"));
		windowPane.setTitleBackgroundImage(imagep);
		
		
	    Grid grid = new Grid(2);
	    //grid.setBorder(new Border(3, Color.BLACK, 1));
	    Label  label;
	    
	    //Mago de Fuego
		ImageReference image = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGF.gif");
		label = new Label(image);
		grid.add(label);
		
		label = new Label("El mago de fuego tiene la habilidad de hacer que sus enemigos ardan "+
"en las temibles llamas de Weyard, sus ataques pueden ser de medio y "+
"largo alcance. Es capaz de renacer desde las cenizas para vencer a "+
"los enemigos, sus daños son de tipo totalmente ofensivo.");
		label.setForeground(Color.LIGHTGRAY);
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD, null));
		grid.add(label);
		
		//Mago de Hielo
		label = new Label("El mago de hielo es capaz de controlar letales hechizos de escarcha y "+
				"frío. Su habilidad de hielo es una forma nativa de las regiones del "+
				"Norte de Weyard. Su capacidad de alcance es muy alta, maneja muchos "+
				"PP (Psinergia Point)");
		label.setForeground(Color.LIGHTGRAY);
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD, null));
		grid.add(label);
		
		
		image = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGH.gif");
		label = new Label(image);
		grid.add(label);
		
		//Mago de Tierra
		image = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGT.gif");
		label = new Label(image);
		grid.add(label);
		
		label = new Label("El mago de tierra tiene la habilidad de utilizar la tierra de Weyard, "+
				"con sus ataques elementales es casi seguro que el enemigo no podrá "+
				"resistir. Sus poderes tienen la capacidad de cambiar la fuerza y la "+
				"composición de la tierra, maneja muchos HP (Healt Point).");
		label.setForeground(Color.LIGHTGRAY);
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD, null));
		grid.add(label);
		
		//Guerrero
		label = new Label("El guerrero es determinado en sus ataques, son muy fuertes y resistentes "+
						"en la batalla. Combinan sus puños, espadas y patadas para realizar "+
						"ataques contudentes. Su capacidad es de corto alcance, luchan fuera "+
						"de la ley Weyard en busca de la gloria.");
		label.setForeground(Color.LIGHTGRAY);
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD, null));
		grid.add(label);
		
		image = ImageReferenceCache.getInstance().getImageReference("Images/Personajes/Gifs/MGG.gif");
		label = new Label(image);
		grid.add(label);	

		windowPane.add(grid);
		return windowPane;
	}	

}
