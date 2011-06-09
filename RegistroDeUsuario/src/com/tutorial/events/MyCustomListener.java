package com.tutorial.events;

import java.util.EventListener;

public interface MyCustomListener extends EventListener {

  public static final int METHOD_DISPARADO_EVENTO_A = 0;
  public static final int METHOD_DISPARADO_EVENTO_B_O_C = 1;
  
  public void disparadoEventoA(MyCustomEvent evt);

  public void disparadoEventoBoC(MyCustomEvent evt);
}
