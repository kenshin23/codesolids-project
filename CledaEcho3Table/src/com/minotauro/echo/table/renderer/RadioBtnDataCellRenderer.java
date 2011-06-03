/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.RadioButton;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class RadioBtnDataCellRenderer extends BaseCellRenderer {

  protected boolean editable;

  // --------------------------------------------------------------------------------

  public RadioBtnDataCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer( //
      final ETable table, final Object value, final int col, final int row) {

    RadioButton ret = new RadioButton();
    ret.setSelected(value == null ? false : (Boolean) value);
    ret.setEnabled(editable);

    ret.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        //        Object[] selected = //
        //        table.getTableSelModel().getSelectedSet().toArray();
        //
        //        for (Object object : selected) {
        //          table.getTableSelModel().setSelected(object, false);
        //        }

        //        table.getTableSelModel().getSelectedSet().clear();
        table.getTableDtaModel().setValue(col, row, true);
      }
    });

    return ret;
  }

  // --------------------------------------------------------------------------------

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }
}
