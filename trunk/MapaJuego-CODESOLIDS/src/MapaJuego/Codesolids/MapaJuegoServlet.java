package MapaJuego.Codesolids;


import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class MapaJuegoServlet extends WebContainerServlet {

  public ApplicationInstance newInstance() {
    return new InitWindowApp();
  }

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new InitWindowApp();
  }
}
