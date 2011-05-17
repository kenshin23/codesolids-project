package com.tutorial.events;

import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;

public class Desktop extends ContentPane {

  private Panel2 p2;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
    setInsets(new Insets(5, 5, 5, 5));
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {

    // ----------------------------------------
    // Layout stuff
    // ----------------------------------------

    Row row = new Row();
    row.setCellSpacing(new Extent(5, Extent.PX));
    add(row);

    Panel1 p1 = new Panel1();
    row.add(p1);

    p2 = new Panel2();
    row.add(p2);

    //    p1.addPanel1Listener(new Panel1Listener() {
    //      @Override
    //      public void disparadoEventoA(Panel1Event evt) {
    //        p2.setResultado("A");
    //      }
    //
    //      @Override
    //      public void disparadoEventoBoC(Panel1Event evt) {
    //        p2.setResultado("A o C");
    //      }
    //    });

    //    p1.addPanel1Listener(p2);

  }
}
