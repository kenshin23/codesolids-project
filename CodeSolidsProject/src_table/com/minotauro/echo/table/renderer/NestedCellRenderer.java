/*
 * Created on 25/09/2008
 */
package com.minotauro.echo.table.renderer;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;

import echopoint.style.Background;

/**
 * @author Demián Gutierrez
 */
public class NestedCellRenderer implements CellRenderer {

  protected List<CellRenderer> cellRendererList = //
  new ArrayList<CellRenderer>();

  protected Color background;
  
  protected Alignment alignment = new Alignment( //
      Alignment.RIGHT, Alignment.DEFAULT);

  // --------------------------------------------------------------------------------

  public NestedCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer(ETable table, Object value, int col, int row) {
    Row ret = new Row();

    ret.setCellSpacing(new Extent(1));
    ret.setAlignment(alignment);
    
    for (CellRenderer cellRenderer : cellRendererList) {
      Component component = cellRenderer. //
          getCellRenderer(table, value, col, row);

      if (component != null) {
        ret.add(component);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  @Override
  public GridLayoutData getGridLayoutData() {
    GridLayoutData gridLayoutData = new GridLayoutData();
    gridLayoutData.setInsets(new Insets(5, 5, 5, 5));
    
	if (background != null) {
		gridLayoutData.setBackground(background);
	}
    
    return gridLayoutData;
  }

  // --------------------------------------------------------------------------------

  public List<CellRenderer> getCellRendererList() {
    return cellRendererList;
  }

  public void setCellRendererList(List<CellRenderer> cellRendererList) {
    this.cellRendererList = cellRendererList;
  }

  // --------------------------------------------------------------------------------

  public Alignment getAlignment() {
    return alignment;
  }

  public void setAlignment(Alignment alignment) {
    this.alignment = alignment;
  }
  
  public Color getBackground()
  {
	  return background;
  }

  public void setBackground(Color background)
  {
	  this.background = background;
  }
  
}
