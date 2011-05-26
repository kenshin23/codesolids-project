/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.event;

import java.util.EventListener;

import nextapp.echo.app.event.EventListenerList;

/**
 * @author Demián Gutierrez
 */
public class PageableModelEvtProxy {

  protected EventListenerList eventListenerList;

  // --------------------------------------------------------------------------------

  public PageableModelEvtProxy(EventListenerList eventListenerList) {
    this.eventListenerList = eventListenerList;
  }

  // --------------------------------------------------------------------------------

  public void addPageableModelListener(PageableModelListener listener) {
    eventListenerList.addListener(PageableModelListener.class, listener);
  }

  public void delPageableModelListener(PageableModelListener listener) {
    eventListenerList.removeListener(PageableModelListener.class, listener);
  }

  public EventListener[] getPageableModelListener() {
    return eventListenerList.getListeners(PageableModelListener.class);
  }

  public void fireActionEvent(PageableModelEvent evt) {
    EventListener[] eventListeners = getPageableModelListener();

    for (int i = 0; i < eventListeners.length; i++) {
      PageableModelListener listener = //
      (PageableModelListener) eventListeners[i];

      listener.pageChanged(evt);
    }
  }
}
