package com.tutorial.htmllayout;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

public class HtmlLayoutApp extends ContentPane {

  private HtmlLayout htmlLayout;
  private TextArea field1;
  private TextArea field2;

  public HtmlLayoutApp(HtmlLayoutServlet servlet) {
    setBackground(new Color(249, 249, 249));

    initGUI();
  }

  protected void initGUI() {
    add(testSimple());
  }

  public Component testSimple() {
    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    field1 = new TextArea();
    field2 = new TextArea();

    showEditable();

    Column col = new Column();
    col.setCellSpacing(new Extent(10));
    col.add(htmlLayout);

    return col;
  }

  private void showEditable() {
    HtmlLayoutData hld;

    htmlLayout.removeAll();

    hld = new HtmlLayoutData("label1");
    Label lblLabel1 = new Label("Campo 1");
    lblLabel1.setLayoutData(hld);
    htmlLayout.add(lblLabel1);

    hld = new HtmlLayoutData("field1");
    field1.setLayoutData(hld);
    field1.setHeight(new Extent(20));
    htmlLayout.add(field1);

    hld = new HtmlLayoutData("label2");
    Label lblLabel2 = new Label("Campo 2");
    lblLabel2.setLayoutData(hld);
    htmlLayout.add(lblLabel2);

    hld = new HtmlLayoutData("field2");
    field2.setLayoutData(hld);
    field2.setHeight(new Extent(20));
    htmlLayout.add(field2);

    Button btnSend = new Button("Enviar");
    btnSend.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
    btnSend.setInsets(new Insets(3, 3, 3, 3));
    btnSend.setBackground(Color.LIGHTGRAY);
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        showReadonly();
      }
    });

    hld = new HtmlLayoutData("button");
    Row r = new Row();
    r.add(btnSend);
    r.setLayoutData(hld);
    htmlLayout.add(r);
  }

  private void showReadonly() {
    HtmlLayoutData hld;

    htmlLayout.removeAll();

    hld = new HtmlLayoutData("label1");
    Label lblLabel1 = new Label("Campo 1");
    lblLabel1.setLayoutData(hld);
    htmlLayout.add(lblLabel1);

    hld = new HtmlLayoutData("field1");
    Label lblLabelData1 = new Label(field1.getText());
    lblLabelData1.setLayoutData(hld);
    htmlLayout.add(lblLabelData1);

    hld = new HtmlLayoutData("label2");
    Label lblLabel2 = new Label("Campo 2");
    lblLabel2.setLayoutData(hld);
    htmlLayout.add(lblLabel2);

    hld = new HtmlLayoutData("field2");
    Label lblLabelData2 = new Label(field2.getText());
    lblLabelData2.setLayoutData(hld);
    htmlLayout.add(lblLabelData2);

    Button btnSend = new Button("Enviar");
    btnSend.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
    btnSend.setInsets(new Insets(3, 3, 3, 3));
    btnSend.setBackground(Color.LIGHTGRAY);
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        showEditable();
      }
    });

    hld = new HtmlLayoutData("button");
    Row r = new Row();
    r.add(btnSend);
    r.setLayoutData(hld);
    htmlLayout.add(r);
  }
}
