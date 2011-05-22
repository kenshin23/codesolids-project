package MapaJuego.Codesolids;


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class ArenaServlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new ArenaApp();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new ArenaApp();
  }
}
