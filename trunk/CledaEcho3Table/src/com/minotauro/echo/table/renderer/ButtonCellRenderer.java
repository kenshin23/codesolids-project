/*
 * Created on 03/05/2011
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Style;
import nextapp.echo.app.event.ActionListener;

import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class ButtonCellRenderer extends BaseCellRenderer {

  protected ActionListener actionListener;

  protected ImageReference icon;

  protected String text;

  protected Style style;

  // --------------------------------------------------------------------------------

  public ButtonCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer( //
      final ETable table, final Object value, final int col, final int row) {

    boolean editable = table.getTableDtaModel().getEditable();

    Button ret = new Button(text, icon);
    ret.setStyle(style);
    ret.setEnabled(editable);
    ret.setToolTipText(text);

    // ----------------------------------------
    // The action command identifies the row
    // ----------------------------------------

    ret.setActionCommand(Integer.toString(row));

    ret.addActionListener(actionListener);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public ActionListener getActionListener() {
    return actionListener;
  }

  public void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }

  // --------------------------------------------------------------------------------

  public ImageReference getIcon() {
    return icon;
  }

  public void setIcon(ImageReference icon) {
    this.icon = icon;
  }

  // --------------------------------------------------------------------------------

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  // --------------------------------------------------------------------------------

  public Style getStyle() {
    return style;
  }

  public void setStyle(Style style) {
    this.style = style;
  }
}
