package com.tutorial.panelchange;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class PanelChangeApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Panel Change");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
