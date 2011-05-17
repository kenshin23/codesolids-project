package com.tutorial.htmllayoutchange;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

public class Desktop extends ContentPane {

  private HtmlLayout htmlLayout;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    add(initTemplate1());
  }

  // --------------------------------------------------------------------------------

  private Component initTemplate1() {
    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template1.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    HtmlLayoutData hld;

    hld = new HtmlLayoutData("head");
    Label lblHead = new Label("¡...head...!");
    lblHead.setLayoutData(hld);
    htmlLayout.add(lblHead);

    hld = new HtmlLayoutData("cent");
    Button btnClickToEnter = new Button("Click me to enter!!!");
    btnClickToEnter.setStyle(GUIStyles.DEFAULT_STYLE);
    btnClickToEnter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        btnClickToEnterClicked();
      }
    });
    btnClickToEnter.setLayoutData(hld);
    htmlLayout.add(btnClickToEnter);

    hld = new HtmlLayoutData("foot");
    Label lblFoot = new Label("¡...foot...!");
    lblFoot.setLayoutData(hld);
    htmlLayout.add(lblFoot);

    return htmlLayout;
  }

  // --------------------------------------------------------------------------------

  private void btnClickToEnterClicked() {
    removeAll();
    add(initTemplate2());
  }

  // --------------------------------------------------------------------------------

  private Component initTemplate2() {
    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template2.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    HtmlLayoutData hld;

    hld = new HtmlLayoutData("menu");
    Component menu = initMenu();
    menu.setLayoutData(hld);
    htmlLayout.add(menu);

    hld = new HtmlLayoutData("head");
    Label lblHead = new Label("¡...head...!");
    lblHead.setLayoutData(hld);
    htmlLayout.add(lblHead);

    hld = new HtmlLayoutData("main");
    Label lblMain = new Label("¡...main...!");
    lblMain.setId("main"); // Para poder removerlo luego
    lblMain.setLayoutData(hld);
    htmlLayout.add(lblMain);

    hld = new HtmlLayoutData("foot");
    Label lblFoot = new Label("¡...foot...!");
    lblFoot.setLayoutData(hld);
    htmlLayout.add(lblFoot);

    return htmlLayout;
  }

  // --------------------------------------------------------------------------------

  private Component initMenu() {
    Column col = new Column();

    Button btnItem1 = new Button("Item 1");
    btnItem1.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem1Clicked();
      }
    });
    col.add(btnItem1);

    Button btnItem2 = new Button("Item 2");
    btnItem2.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem2Clicked();
      }
    });
    col.add(btnItem2);

    Button btnItem3 = new Button("Item 3");
    btnItem3.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem3Clicked();
      }
    });
    col.add(btnItem3);

    Button btnItem4 = new Button("Item 4");
    btnItem4.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem4Clicked();
      }
    });
    col.add(btnItem4);

    Button btnExit = new Button("Exit");
    btnExit.setStyle(GUIStyles.DEFAULT_STYLE);
    btnExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnExitClicked();
      }
    });
    col.add(btnExit);

    return col;
  }

  // --------------------------------------------------------------------------------

  private void btnItem1Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem1 pnlMain = new PanelItem1();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem2Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem2 pnlMain = new PanelItem2();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem3Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem3 pnlMain = new PanelItem3();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem4Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem4 pnlMain = new PanelItem4();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnExitClicked() {
    removeAll();
    add(initTemplate1());
  }
}
