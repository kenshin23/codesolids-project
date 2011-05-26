/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

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
}
