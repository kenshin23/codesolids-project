package codesolids.gui.arena;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class ArenaApp extends ApplicationInstance {
	
	public Window init(){
		Window window = new Window();
		window.setTitle("Arena");
		
	    Desktop desktop = new Desktop();
	    window.setContent(desktop);
	    
	    return window;
	}

}
