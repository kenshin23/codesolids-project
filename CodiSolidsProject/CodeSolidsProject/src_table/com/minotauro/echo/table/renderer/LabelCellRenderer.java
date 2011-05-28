/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class LabelCellRenderer implements CellRenderer {

	protected GridLayoutData gridLayoutData = new GridLayoutData();

	protected Color background;
	protected Color foreground;

	protected Alignment alignment = new Alignment( //
			Alignment.RIGHT, Alignment.DEFAULT);

	// --------------------------------------------------------------------------------

	public LabelCellRenderer() {
		// Empty
	}

	// --------------------------------------------------------------------------------

	@Override
	public Component getCellRenderer(ETable table, Object value, int col,
			int row) {
		Label ret = new Label();

		if (foreground != null) {
			ret.setForeground(foreground);
		}

		if (value == null) {
			ret.setText("-");
		} else {
			ret.setText(value.toString());
		}

		return ret;
	}

	// --------------------------------------------------------------------------------

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

	// --------------------------------------------------------------------------------

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	// --------------------------------------------------------------------------------

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	// --------------------------------------------------------------------------------

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

}
