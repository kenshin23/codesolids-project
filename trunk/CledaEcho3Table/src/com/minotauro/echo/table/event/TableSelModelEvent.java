/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventObject;

import com.minotauro.echo.table.base.TableSelModel;

/**
 * @author Demián Gutierrez
 */
public class TableSelModelEvent extends EventObject {

  protected boolean selected;

  protected Object element;

  // --------------------------------------------------------------------------------

  public TableSelModelEvent(TableSelModel source, //
      boolean selected, Object element) {

    super(source);

    this.selected/**/= selected;
    this.element/* */= element;
  }

  // --------------------------------------------------------------------------------

  public boolean isSelected() {
    return selected;
  }

  // --------------------------------------------------------------------------------

  public Object getElement() {
    return element;
  }
}
