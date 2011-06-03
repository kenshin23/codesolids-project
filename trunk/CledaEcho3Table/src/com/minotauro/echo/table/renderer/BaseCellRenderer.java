/*
 * Created on 25/09/2008
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Insets;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;

/**
 * @author Demián Gutierrez
 */
public abstract class BaseCellRenderer implements CellRenderer {

  protected Color background;
  protected Color foreground;

  protected Alignment alignment = new Alignment( //
      Alignment.RIGHT, Alignment.DEFAULT);

  // --------------------------------------------------------------------------------

  public BaseCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public GridLayoutData getGridLayoutData() {
    GridLayoutData gridLayoutData = new GridLayoutData();
    gridLayoutData.setInsets(new Insets(5, 5, 5, 5));

    if (background != null) {
      gridLayoutData.setBackground(background);
    }

    gridLayoutData.setAlignment(alignment);

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
