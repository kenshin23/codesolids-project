package com.tutorial.events;

import java.util.EventListener;

import nextapp.echo.app.Button;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

public class Panel1 extends Row {

  private EventListenerList eventListenerList = new EventListenerList();
  private TextField txtData;

  private ActionListenerProxy actionListenerProxy = new ActionListenerProxy(eventListenerList);

  public String getData() {
    return txtData.getText();
  }

  public Panel1() {
    initGUI();
  }

  private void initGUI() {
    txtData = new TextField();
    add(txtData);

    Button btnA = new Button("A");
    btnA.setStyle(GUIStyles.DEFAULT_STYLE);
    btnA.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        firePanel1Event(new MyCustomEvent(Panel1.this, txtData.getText()), MyCustomListener.METHOD_DISPARADO_EVENTO_A);
      }
    });
    add(btnA);

    Button btnB = new Button("B");
    btnB.setStyle(GUIStyles.DEFAULT_STYLE);
    btnB.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        firePanel1Event(new MyCustomEvent(Panel1.this, txtData.getText()), MyCustomListener.METHOD_DISPARADO_EVENTO_B_O_C);
      }
    });
    add(btnB);

    Button btnC = new Button("C");
    btnC.setStyle(GUIStyles.DEFAULT_STYLE);
    btnC.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        firePanel1Event(new MyCustomEvent(Panel1.this, txtData.getText()), MyCustomListener.METHOD_DISPARADO_EVENTO_B_O_C);
      }
    });
    add(btnC);
  }

  public void addPanel1Listener(MyCustomListener listener) {
    eventListenerList.addListener(MyCustomListener.class, listener);
  }

  public void delPanel1Listener(MyCustomListener listener) {
    eventListenerList.removeListener(MyCustomListener.class, listener);
  }

  public EventListener[] getPanel1Listener() {
    return eventListenerList.getListeners(MyCustomListener.class);
  }

  private void firePanel1Event(MyCustomEvent evt, int method) {
    EventListener[] eventListeners = getPanel1Listener();

    for (int i = 0; i < eventListeners.length; i++) {
      MyCustomListener listener = (MyCustomListener) eventListeners[i];

      switch (method) {
        case MyCustomListener.METHOD_DISPARADO_EVENTO_A :
          listener.disparadoEventoA(evt);
          break;
        case MyCustomListener.METHOD_DISPARADO_EVENTO_B_O_C :
          listener.disparadoEventoBoC(evt);
          break;
      }
    }
  }

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }
}
