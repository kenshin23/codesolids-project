/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventListener;

import nextapp.echo.app.event.EventListenerList;

import com.minotauro.echo.table.event.TableSelModelListener.TableSelModelListenerMethod;

/**
 * @author Demián Gutierrez
 */
public class TableSelModelEvtProxy {

  protected EventListenerList eventListenerList;

  // --------------------------------------------------------------------------------

  public TableSelModelEvtProxy(EventListenerList eventListenerList) {
    this.eventListenerList = eventListenerList;
  }

  // --------------------------------------------------------------------------------

  public void addTableSelModelListener(TableSelModelListener listener) {
    eventListenerList.addListener(TableSelModelListener.class, listener);
  }

  public void delTableSelModelListener(TableSelModelListener listener) {
    eventListenerList.removeListener(TableSelModelListener.class, listener);
  }

  public EventListener[] getTableSelModelListener() {
    return eventListenerList.getListeners(TableSelModelListener.class);
  }

  public void fireActionEvent(TableSelModelEvent evt, TableSelModelListenerMethod method) {
    EventListener[] eventListeners = getTableSelModelListener();

    for (int i = 0; i < eventListeners.length; i++) {
      TableSelModelListener listener = //
      (TableSelModelListener) eventListeners[i];

      switch (method) {
        case TABLE_SEL :
          listener.tableSelChanged(evt);
          break;
        case TABLE_SET :
          listener.tableSetChanged(evt);
          break;
      }
    }
  }
}
