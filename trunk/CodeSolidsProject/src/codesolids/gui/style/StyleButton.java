package codesolids.gui.style;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.MutableStyle;
import nextapp.echo.app.Style;

public class StyleButton {

	public static final Style BATALLA_STYLE;

	static {
		
		MutableStyle style = new MutableStyle();

		style.set(Button.PROPERTY_BACKGROUND, new Color(182, 182, 182));
		style.set(Button.PROPERTY_BORDER, new Border(new Extent(3, Extent.PX), new Color(113,113,111), Border.STYLE_RIDGE));
		style.set(Button.PROPERTY_WIDTH, new Extent(32));
		style.set(Button.PROPERTY_HEIGHT, new Extent(32));
		style.set(Button.PROPERTY_ALIGNMENT, new Alignment(Alignment.CENTER, Alignment.CENTER));
		
		BATALLA_STYLE = style;
	}
	  
}
