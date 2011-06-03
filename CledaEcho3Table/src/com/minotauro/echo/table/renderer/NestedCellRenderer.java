/*
 * Created on 25/09/2008
 */
package com.minotauro.echo.table.renderer;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class NestedCellRenderer extends BaseCellRenderer {

  protected List<CellRenderer> cellRendererList = //
  new ArrayList<CellRenderer>();

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

  public List<CellRenderer> getCellRendererList() {
    return cellRendererList;
  }

  public void setCellRendererList(List<CellRenderer> cellRendererList) {
    this.cellRendererList = cellRendererList;
  }
}
