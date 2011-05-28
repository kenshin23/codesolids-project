/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventListener;

import nextapp.echo.app.event.EventListenerList;

/**
 * @author Demián Gutierrez
 */
public class TableDtaModelEvtProxy {

  protected EventListenerList eventListenerList;

  // --------------------------------------------------------------------------------

  public TableDtaModelEvtProxy(EventListenerList eventListenerList) {
    this.eventListenerList = eventListenerList;
  }

  // --------------------------------------------------------------------------------

  public void addTableDtaModelListener(TableDtaModelListener listener) {
    eventListenerList.addListener(TableDtaModelListener.class, listener);
  }

  public void delTableDtaModelListener(TableDtaModelListener listener) {
    eventListenerList.removeListener(TableDtaModelListener.class, listener);
  }

  public EventListener[] getTableDtaModelListener() {
    return eventListenerList.getListeners(TableDtaModelListener.class);
  }

  public void fireActionEvent(TableDtaModelEvent evt) {
    EventListener[] eventListeners = getTableDtaModelListener();

    for (int i = 0; i < eventListeners.length; i++) {
      TableDtaModelListener listener = //
      (TableDtaModelListener) eventListeners[i];

      listener.tableDtaChanged(evt);
    }
  }
}
