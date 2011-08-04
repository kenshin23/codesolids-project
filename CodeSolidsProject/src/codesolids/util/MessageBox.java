package codesolids.util;

import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;

/**
 * 
 * @author Antonio López
 *
 */

public class MessageBox extends WindowPane{

	public static int ACCEPT_WINDOW = 1;
	public static int ACCEPT_CANCEL_WINDOW = 2;
	public static int OK_WINDOW = 4;
	
	private Column column;
	
	private int option;
	
	public MessageBox(String titulo, Column column, int width, int height, int option)
	{
		setTitle(titulo);
		setStyle(StyleWindow.DEFAULT_STYLE);
		
		this.column = column;
		
		this.option = option;
		
		setWidth(new Extent(width));
		setHeight(new Extent(height));
		
		setResizable(false);
		setClosable(false);
		setModal(true);
		
		initGUI();
	}

	private void initGUI() {
		
	    Column col = new Column();
	    col.setCellSpacing(new Extent(15));
	    col.setInsets(new Insets(5, 5, 5, 5));
	    add(col);
		
	    ColumnLayoutData cld;
	    cld = new ColumnLayoutData();
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    Column colComponent = new Column();
	    colComponent.add(column);
	    colComponent.setLayoutData(cld);
	    col.add(colComponent);
	    
	    Row row = new Row();
	    row.setCellSpacing(new Extent(5));
	    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    row.setLayoutData(cld);
	    col.add(row);
	    
	    if( (option & ACCEPT_WINDOW) != 0 )
	    {
	    	
	    	Button btnAccept = new Button("Aceptar");
	    	btnAccept.setStyle(Styles1.DEFAULT_STYLE);
	        btnAccept.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    	btnAccept.setWidth(new Extent(50));
	    	
	    	btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					userClose();
				}
			});
	    	row.add(btnAccept);
	    }
	
	    if( (option & ACCEPT_CANCEL_WINDOW) != 0 )
	    {
	    	
	    	Button btnAccept = new Button("Aceptar");
	    	btnAccept.setStyle(Styles1.DEFAULT_STYLE);
	        btnAccept.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    	btnAccept.setWidth(new Extent(50));
	    	
	    	btnAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					userClose();
				}
			});
	    	row.add(btnAccept);
	    	
	    	Button btnCancel = new Button("Cancelar");
	    	btnCancel.setStyle(Styles1.DEFAULT_STYLE);
	        btnCancel.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    	btnCancel.setWidth(new Extent(50));
	    	
	    	btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					userClose();
				}
			});
	    	row.add(btnAccept);
	    }
	    
	    if( (option & OK_WINDOW) != 0 )
	    {
	    	
	    	Button btnOk = new Button("Ok");
	    	btnOk.setStyle(Styles1.DEFAULT_STYLE);
	        btnOk.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    	btnOk.setWidth(new Extent(50));
	    	
	    	btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					userClose();
				}
			});
	    	row.add(btnOk);
	    }
	    
	}
	
}
