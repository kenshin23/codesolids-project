/*
 * Created on 27/08/2008
 */
package com.tutorial.events;

import java.util.EventListener;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

/**
 * @author Demián Gutierrez
 */
public class ActionListenerProxy {

  protected EventListenerList eventListenerList;

  // --------------------------------------------------------------------------------

  public ActionListenerProxy(EventListenerList eventListenerList) {
    this.eventListenerList = eventListenerList;
  }

  // --------------------------------------------------------------------------------

  public void addActionListener(ActionListener listener) {
    eventListenerList.addListener(ActionListener.class, listener);
  }

  public void delActionListener(ActionListener listener) {
    eventListenerList.removeListener(ActionListener.class, listener);
  }

  public EventListener[] getActionListener() {
    return eventListenerList.getListeners(ActionListener.class);
  }

  public void fireActionEvent(ActionEvent evt) {
    EventListener[] eventListeners = getActionListener();

    for (int i = 0; i < eventListeners.length; i++) {
      ActionListener listener = (ActionListener) eventListeners[i];
      listener.actionPerformed(evt);
    }
  }
}
