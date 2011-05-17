package com.tutorial.template;

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
import echopoint.TemplatePanel;
import echopoint.template.ResourceTemplateDataSource;

public class TemplateApp extends ContentPane {

  private TemplatePanel templatePanel;
  private TextArea field1;
  private TextArea field2;

  public TemplateApp(TemplateServlet servlet) {
    setBackground(new Color(249, 249, 249));

    initGUI();
  }

  protected void initGUI() {
    add(testSimple());
  }

  public Component testSimple() {
    ResourceTemplateDataSource stds = new ResourceTemplateDataSource("com/tutorial/template/template.html");
    templatePanel = new TemplatePanel();
    templatePanel.setTemplateDataSource(stds);

    field1 = new TextArea();
    field2 = new TextArea();

    showEditable();

    Column col = new Column();
    col.setCellSpacing(new Extent(10));
    col.add(templatePanel);

    return col;
  }

  private void showEditable() {
    templatePanel.removeAll();

    templatePanel.addNamedComponent(new Label("Campo 1"), "label1");
    templatePanel.addNamedComponent(new Label("Campo 2"), "label2");

    field1.setHeight(new Extent(20));
    templatePanel.addNamedComponent(field1, "field1");

    field2.setHeight(new Extent(20));
    templatePanel.addNamedComponent(field2, "field2");

    Button btnSend = new Button("Enviar");
    btnSend.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
    btnSend.setInsets(new Insets(3, 3, 3, 3));
    btnSend.setBackground(Color.LIGHTGRAY);
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        showReadonly();
      }
    });

    Row r = new Row();
    r.add(btnSend);
    templatePanel.addNamedComponent(r, "button");
  }

  private void showReadonly() {
    templatePanel.removeAll();

    templatePanel.addNamedComponent(new Label("Campo 1"), "label1");
    templatePanel.addNamedComponent(new Label("Campo 2"), "label2");

    templatePanel.addNamedComponent(new Label(field1.getText()), "field1");
    templatePanel.addNamedComponent(new Label(field2.getText()), "field2");

    Button btnSend = new Button("Enviar");
    btnSend.setBorder(new Border(new Extent(1), Color.BLACK, Border.STYLE_SOLID));
    btnSend.setInsets(new Insets(3, 3, 3, 3));
    btnSend.setBackground(Color.LIGHTGRAY);
    btnSend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        showEditable();
      }
    });

    Row r = new Row();
    r.add(btnSend);
    templatePanel.addNamedComponent(r, "button");
  }
}
