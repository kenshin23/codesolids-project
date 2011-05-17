package com.tutorial.imagemap;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.ImageMap;
import echopoint.model.CircleSection;
import echopoint.model.PolygonSection;
import echopoint.model.RectangleSection;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ImageMapApp extends ApplicationInstance {

  Label lblSelected = new Label();

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    ImageReference ir = new ResourceImageReference( //
        "com/tutorial/imagemap/bar-circle.gif");

    Column col = new Column();
    contentPane.add(col);

    ImageMap imageMap = new ImageMap(ir);

    imageMap.setWidth(new Extent(520));
    imageMap.setHeight(new Extent(300));

    imageMap.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        lblSelected.setText(evt.getActionCommand());
      }
    });

    imageMap.addSection(new CircleSection(70, 70, 50, "circle"));
    imageMap.addSection(new RectangleSection(90, 220, 290, 270, "rectangle"));
    imageMap.addSection(new PolygonSection(new int[]{275, 120, 450, 120, 500, 75, 425, 35, 330, 100, 275, 50},
        "polygon"));

    col.add(imageMap);

    lblSelected = new Label("nothing selected");
    col.add(lblSelected);

    return window;
  }
}
