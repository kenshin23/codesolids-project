package com.tutorial.events;

import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;

public class Panel2 extends Row implements MyCustomListener {

  //  private EventListenerList eventListenerList = new EventListenerList();
  private TextField txtResultado;

  public Panel2() {
    initGUI();
  }

  private void initGUI() {
    txtResultado = new TextField();
    add(txtResultado);
  }

  //  public void setResultado(String resultado) {
  //    txtResultado.setText(resultado);
  //  }

  @Override
  public void disparadoEventoA(MyCustomEvent evt) {
    Panel1 pnl = (Panel1) evt.getSource();
    txtResultado.setText("A " + evt.getData());
  }

  @Override
  public void disparadoEventoBoC(MyCustomEvent evt) {
    Panel1 pnl = (Panel1) evt.getSource();
    txtResultado.setText("B o C " + pnl.getData());
  }
}
