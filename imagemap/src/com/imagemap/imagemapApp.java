package com.imagemap;


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;


public class imagemapApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    imagemap IM = new imagemap();
    window.setContent(IM);
    return window;
  }
}
