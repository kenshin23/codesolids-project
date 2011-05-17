package com.tutorial.input2;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class InputTextApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Hello World");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
