/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.renderer;

import java.text.DateFormat;
import java.util.Calendar;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;

import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class CalendarCellRenderer extends BaseCellRenderer {

  protected DateFormat dateFormat;

  // --------------------------------------------------------------------------------

  public CalendarCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public CalendarCellRenderer(DateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer(ETable table, Object value, int col, int row) {
    Label ret = new Label();

    if (value == null) {
      ret.setText("-");
    } else {
      ret.setText(dateFormat.format( //
          ((Calendar) value).getTime()));
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public DateFormat getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(DateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }
}
