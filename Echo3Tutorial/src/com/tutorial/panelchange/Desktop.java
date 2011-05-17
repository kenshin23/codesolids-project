package com.tutorial.panelchange;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

public class Desktop extends ContentPane {

  private Row rowCnt;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    SplitPane splitPane = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL);
    splitPane.setSeparatorWidth(new Extent(10));
    splitPane.setSeparatorColor(Color.RED);
    splitPane.setSeparatorPosition(new Extent(100));
    splitPane.setResizable(true);
    add(splitPane);

    splitPane.add(initMenu());

    Column colMain = new Column();
    splitPane.add(colMain);

    Row rowTop = new Row();
    colMain.add(rowTop);
    Label lblHead = new Label("¡...head...!");
    rowTop.add(lblHead);

    rowCnt = new Row();
    colMain.add(rowCnt);
    Label lblMain = new Label("¡...main...!");
    rowCnt.add(lblMain);

    Row rowBot = new Row();
    colMain.add(rowBot);
    Label lblFoot = new Label("¡...foot...!");
    rowBot.add(lblFoot);
  }

  // --------------------------------------------------------------------------------

  public Component initMenu() {
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

    return col;
  }

  // --------------------------------------------------------------------------------

  private void btnItem1Clicked() {
    rowCnt.removeAll();
    rowCnt.add(new PanelItem1());
  }

  // --------------------------------------------------------------------------------

  private void btnItem2Clicked() {
    rowCnt.removeAll();
    rowCnt.add(new PanelItem2());
  }

  // --------------------------------------------------------------------------------

  private void btnItem3Clicked() {
    rowCnt.removeAll();
    rowCnt.add(new PanelItem3());
  }

  // --------------------------------------------------------------------------------

  private void btnItem4Clicked() {
    rowCnt.removeAll();
    rowCnt.add(new PanelItem4());
  }
}
