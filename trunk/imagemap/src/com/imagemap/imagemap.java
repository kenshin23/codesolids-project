package com.imagemap;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.ResourceImageReference;
import echopoint.ImageMap;
import echopoint.model.RectangleSection;

public class imagemap extends ContentPane{
	
	public imagemap() {
		super();
		
		constructorComponentes();
	}

	private void constructorComponentes() {
	
	this.setInsets(new Insets(2, 2, 2, 2));
	
	ImageReference ir = new ResourceImageReference(  "/com/imagemap/mago_fuego1.jpg");
	
	Column col = new Column();
	add(col);
	
	ImageMap imageMap = new ImageMap(ir);
	imageMap.setWidth(new Extent(100, Extent.PERCENT));
	imageMap.setHeight(new Extent(100, Extent.PERCENT));
	imageMap.addSection(new RectangleSection(0, 0, 1, 1, ""));
	col.add(imageMap);


	
		}
}
