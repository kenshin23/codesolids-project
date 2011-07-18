package codesolids.gui.principal;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.Usuario;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class PrincipalApp extends ApplicationInstance {
	
	private Usuario usuario;
	private Personaje personaje;
	
	public Window init(){
		Window window = new Window();
		window.setTitle("CodeSolids Project");
		
	    PrincipalDesktop principalDesktop = new PrincipalDesktop();
	    window.setContent(principalDesktop);
	    
	    return window;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Personaje getPersonaje()
	{
		return personaje;
	}
	
	public void setPersonaje(Personaje personaje)
	{
		this.personaje = personaje;
	}
	
}
