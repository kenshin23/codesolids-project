/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.layout.GridLayoutData;

/**
 * @author Demián Gutierrez
 */
public interface CellRenderer {

  public Component getCellRenderer( //
      ETable table, Object value, int col, int row);

  // --------------------------------------------------------------------------------

  public GridLayoutData getGridLayoutData();

  // --------------------------------------------------------------------------------

  public Color getBackground();

  public void setBackground(Color background);

  // --------------------------------------------------------------------------------

  public Color getForeground();

  public void setForeground(Color foreground);

  // --------------------------------------------------------------------------------

  public Alignment getAlignment();

  public void setAlignment(Alignment alignment);
}
