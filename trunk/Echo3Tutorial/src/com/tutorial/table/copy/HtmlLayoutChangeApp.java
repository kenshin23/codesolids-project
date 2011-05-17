package com.tutorial.table.copy;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class HtmlLayoutChangeApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Panel Change");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
