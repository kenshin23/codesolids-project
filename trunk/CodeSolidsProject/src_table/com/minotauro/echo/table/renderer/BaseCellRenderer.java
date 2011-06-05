/*
 * Created on 25/09/2008
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Insets;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;

/**
 * @author Demián Gutierrez
 */
public abstract class BaseCellRenderer implements CellRenderer {

  public BaseCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public GridLayoutData getGridLayoutData() {
    GridLayoutData gridLayoutData = new GridLayoutData();
    gridLayoutData.setInsets(new Insets(5, 5, 5, 5));
    return gridLayoutData;
  }
}
