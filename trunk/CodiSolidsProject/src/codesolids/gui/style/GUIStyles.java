package codesolids.gui.style;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.MutableStyle;
import nextapp.echo.app.Style;

public class GUIStyles {

  public static final Style DEFAULT_STYLE;

  static {
    MutableStyle style = new MutableStyle();

    style.set(Button.PROPERTY_LINE_WRAP, false);

    style.set(Button.PROPERTY_BACKGROUND, new Color(0xF2, 0xF2, 0xED));
    style.set(Button.PROPERTY_BORDER, new Border(1, new Color(0xD6, 0xD3, 0xCE), Border.STYLE_SOLID));

    style.set(Button.PROPERTY_ROLLOVER_ENABLED, true);
    style.set(Button.PROPERTY_ROLLOVER_BACKGROUND, new Color(0xDE, 0xF3, 0xFF));
    style.set(Button.PROPERTY_ROLLOVER_BORDER, new Border(1, new Color(0x31, 0x69, 0xC6), Border.STYLE_SOLID));

    style.set(Button.PROPERTY_INSETS, new Insets(2));
    //style.set(Button.PROPERTY_OUTSETS, new Insets(1));

    style.set(Button.PROPERTY_TEXT_ALIGNMENT, new Alignment(Alignment.TRAILING, Alignment.DEFAULT));
    style.set(Button.PROPERTY_ICON_TEXT_MARGIN, new Extent(3));
    //style.set(Button.PROPERTY_MOUSE_CURSOR, CURSOR_POINTER);

    style.set(Button.PROPERTY_DISABLED_FOREGROUND, Color.LIGHTGRAY);

    DEFAULT_STYLE = style;
  }
}
