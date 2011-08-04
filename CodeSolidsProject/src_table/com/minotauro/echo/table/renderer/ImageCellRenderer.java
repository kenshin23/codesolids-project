package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.layout.GridLayoutData;
import codesolids.util.ImageReferenceCache;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;

import echopoint.ImageIcon;

/**
 * @author Antonio López
 * Created on 22/05/2011
 */

public class ImageCellRenderer implements CellRenderer {

	protected GridLayoutData gridLayoutData = new GridLayoutData();

	protected Color background;
	protected Color foreground;
	protected Extent width;
	protected Extent height;

	protected Alignment alignment = new Alignment( //
			Alignment.RIGHT, Alignment.DEFAULT);

	public ImageCellRenderer() 
	{
	}
	
	@Override
	public Component getCellRenderer(ETable table, Object value, int col,
			int row) {
		
		ImageIcon retImg = new ImageIcon();
		retImg.setIcon(ImageReferenceCache.getInstance().getImageReference(value.toString()));
		
		if (foreground != null) {
			retImg.setForeground(foreground);
		}
		
		if (width != null){
			retImg.setWidth(width);
		}

		if (height != null){
			retImg.setWidth(height);
		}
		
		return retImg;
	}

	
	@Override
	public GridLayoutData getGridLayoutData() {
		GridLayoutData gridLayoutData = new GridLayoutData();
		gridLayoutData.setInsets(new Insets(5, 5, 5, 5));
		
		gridLayoutData.setAlignment(alignment);

		if (background != null) {
			gridLayoutData.setBackground(background);
		}

		return gridLayoutData;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	
	public Extent getWidth()
	{
		return width; 
	}
	
	public void setWidth(Extent width)
	{
		this.width = width;
	}
	
	public Extent getHeight()
	{
		return height;
	}
	
	public void setHeight(Extent heigth)
	{
		this.height = heigth;
	}

}
