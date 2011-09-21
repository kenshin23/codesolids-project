package codesolids.util;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;

import org.hibernate.Session;

import codesolids.bd.clases.Personaje;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;

public class MessageLevel extends WindowPane{

	private Personaje personaje;
	private int cantHp;
	private int cantMp;
	
	public MessageLevel(Personaje personaje, int cantHp, int cantMp)
	{
		
		setTitle("Felicitaciones");
		setStyle(StyleWindow.DEFAULT_STYLE);
		
		setResizable(false);
		setClosable(false);
		setMovable(false);
		setModal(true);
		
		setWidth(new Extent(400));
		setHeight(new Extent(200));
		
		this.personaje = personaje;
		this.cantHp = cantHp;
		this.cantMp = cantMp;
		
		add(messageLevel());
		
	}
	
	private Column messageLevel()
	{
		
	    ColumnLayoutData cld;
	    cld = new ColumnLayoutData();
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		
	    Label lbl = new Label();
	    lbl.setForeground(new Color(128,0,128));
	    lbl.setFont(new Font(null, 1, new Extent(18)));
	    lbl.setText("Eres Nivel: " + personaje.getLevel());
	    lbl.setLayoutData(cld);
	    
		Column col = new Column();
	    col.setCellSpacing(new Extent(15));
	    col.setInsets(new Insets(5, 5, 5, 5));
	    
	    col.add(lbl);
	    
		lbl = new Label();
		lbl.setForeground(Color.RED);
		lbl.setFont(new Font(null, 1, new Extent(16)));
		lbl.setText("Obtienes de HP: " + cantHp);
		lbl.setLayoutData(cld);
		col.add(lbl);

		lbl = new Label();
		lbl.setForeground(Color.BLUE);
		lbl.setFont(new Font(null, 1, new Extent(16)));
		lbl.setText("Obtienes de MP: " + cantMp);
		lbl.setLayoutData(cld);
		col.add(lbl);
	 
	    Button btnAceptar = new Button("Aceptar");
		btnAceptar.setLayoutData(cld);
	    btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
        btnAceptar.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
    	btnAceptar.setWidth(new Extent(50));
    	
    	btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				
				Session session = SessionHibernate.getInstance().getSession(); 
				session.beginTransaction();
				
				personaje = (Personaje) session.load(Personaje.class, personaje.getId());
				
				personaje.setHp(personaje.getHp() + cantHp);
				personaje.setMp(personaje.getMp() + cantMp);
				personaje.setPuntos(personaje.getPuntos() + 3);
				
				session.getTransaction().commit();
				session.close();
				
				userClose();
			}
		});
    	col.add(btnAceptar);
    	
    	return col;
	}
	
}
