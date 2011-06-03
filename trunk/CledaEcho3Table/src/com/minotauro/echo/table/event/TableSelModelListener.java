/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventListener;

/**
 * @author Demián Gutierrez
 */
public interface TableSelModelListener extends EventListener {

  public enum TableSelModelListenerMethod {
    TABLE_SEL, TABLE_SET
  }

  // --------------------------------------------------------------------------------

  public void tableSelChanged(TableSelModelEvent evt);

  public void tableSetChanged(TableSelModelEvent evt);
}
