package com.tutorial.table;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class TableApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Table");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
