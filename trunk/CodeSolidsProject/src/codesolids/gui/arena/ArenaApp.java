package codesolids.gui.arena;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class ArenaApp extends ApplicationInstance {
	
	public Window init(){
		Window window = new Window();
		window.setTitle("Arena");
		
	    ArenaDesktop arenaDesktop = new ArenaDesktop();
	    window.setContent(arenaDesktop);
	    
	    return window;
	}

}
