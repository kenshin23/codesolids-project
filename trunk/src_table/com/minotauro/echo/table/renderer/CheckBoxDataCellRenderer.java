/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class CheckBoxDataCellRenderer implements CellRenderer {

  protected boolean editable;

  // --------------------------------------------------------------------------------

  public CheckBoxDataCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer( //
      final ETable table, final Object value, final int col, final int row) {

    CheckBox ret = new CheckBox();
    ret.setSelected(value == null ? false : (Boolean) value);
    ret.setEnabled(editable);

    ret.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        CheckBox src = (CheckBox) evt.getSource();
        table.getTableDtaModel().setValue(col, row, src.isSelected());
      }
    });

    return ret;
  }

  // --------------------------------------------------------------------------------

  @Override
  public GridLayoutData getGridLayoutData() {
    GridLayoutData gridLayoutData = new GridLayoutData();
    gridLayoutData.setInsets(new Insets(5, 5, 5, 5));
    return gridLayoutData;
  }

  // --------------------------------------------------------------------------------

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

}
