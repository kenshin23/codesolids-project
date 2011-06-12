package codesolids.gui.style;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.MutableStyle;
import nextapp.echo.app.Style;
import nextapp.echo.app.WindowPane;

/**
 * @author: Antonio López
 * 
 */

public class StyleWindow {

	public static final Style DEFAULT_STYLE;
	public static final Style ACADEMY_STYLE;
	
	static {
		MutableStyle style = new MutableStyle();

		style.set(WindowPane.PROPERTY_TITLE_BACKGROUND, new Color(87, 205, 211));
		style.set(WindowPane.PROPERTY_TITLE_FOREGROUND, Color.WHITE);

		style.set(WindowPane.PROPERTY_BACKGROUND, new Color(226,211,161));
		
		DEFAULT_STYLE = style;
	}
	
	static {
		MutableStyle style = new MutableStyle();
		
		style.set(WindowPane.PROPERTY_TITLE_BACKGROUND, new Color(87, 205, 211));
		style.set(WindowPane.PROPERTY_TITLE_FOREGROUND, Color.WHITE);

		style.set(WindowPane.PROPERTY_BACKGROUND, new Color(226,211,161));
		
		ACADEMY_STYLE = style;
	}
}
