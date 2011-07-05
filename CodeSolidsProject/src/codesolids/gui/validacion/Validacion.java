package codesolids.gui.validacion;



/*
 * autor hector prada
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.Window;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.layout.RowLayoutData;
import codesolids.gui.mapa.Styles;

public class Validacion extends ContentPane {

	private String usuario = null;
	private String contrasena = null;
	private WindowPane msgError;
	private TextField login;
	private PasswordField contra;

	public Validacion() {
		super();

		constructorComponentes();
	}
	
	private void constructorComponentes() {

		this.setEnabled(true);
		this.setVisible(true);
		this.setFont(new Font(new Font.Typeface("arial"), Font.PLAIN,new Extent(10, Extent.PT)));
		msgError = new WindowPane();
		msgError.setStyleName("Default");
		msgError.setTitle("*********ERROR********");
		msgError.setVisible(false);
		msgError.setDefaultCloseOperation(WindowPane.HIDE_ON_CLOSE);
		msgError.setBackground(new Color(0x6363b6));
		msgError.setMovable(true);
		add(msgError);
	
		Label error = new Label();
		error.setText("ERROR, USUARIO Y/O CONTRASEÑA EQUIVOCADO. INTENTE DE NUEVO");
		error.setFont(new Font(new Font.Typeface("arial"), Font.ITALIC,new Extent(10, Extent.PT)));
		msgError.add(error);
		
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(1, Extent.PX)));
		add(grid1);
	
		Label log = new Label();
		log.setText("Usuario:");
		log.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		grid1.add(log);
		/*para los textfield y passwordfield no vale la pena usar stylesheet son muy pocos propiedades*/
		
		login = new TextField();
		login.setEnabled(true);
		login.setVisible(true);
		login.setFont(new Font(null, Font.PLAIN, new Extent(10, Extent.PT)));
		login.setBackground(new Color(0xe1eefa));
		ResourceImageReference espada = new ResourceImageReference("/Images/Mapa/128px-AoL_Sword.png");
		login.setBackgroundImage(new FillImage(espada));
		login.setBorder(new Border(new Extent(3, Extent.PX),new Color(0x002aff), Border.STYLE_RIDGE));
		login.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				setUsuario(e);
			}
		});
		
		grid1.add(login);
		
		Label log2 = new Label();
		log2.setText("Contraseña:");
		log2.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		grid1.add(log2);

		contra = new PasswordField();
		contra.setEnabled(true);
		contra.setVisible(true);
		contra.setFont(new Font(null, Font.PLAIN, new Extent(10, Extent.PT)));
		contra.setBackground(new Color(0xe1eefa));
		contra.setBackgroundImage(new FillImage(espada));
		contra.setBorder(new Border(new Extent(3, Extent.PX), new Color(0x002aff), Border.STYLE_RIDGE));
		contra.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				setContrasena(e);
			}
		});
		
		grid1.add(contra);
		
		Row row1 = new Row();
		GridLayoutData row1LayoutData = new GridLayoutData();
		row1LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),new Extent(10, Extent.PX), new Extent(0, Extent.PX),new Extent(0, Extent.PX)));
		row1LayoutData.setColumnSpan(2);
		row1.setLayoutData(row1LayoutData);
		grid1.add(row1);
		
		Button btnAceptar = new Button();
		btnAceptar.setEnabled(true);
		btnAceptar.setText("Acceder");
		btnAceptar.setVisible(true);
		btnAceptar.setStyle(Styles.BOTONES_LOGIN_SCREEN);
		RowLayoutData btnAceptarLayoutData = new RowLayoutData();
		btnAceptarLayoutData.setInsets(new Insets(new Extent(80, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnAceptar.setLayoutData(btnAceptarLayoutData);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validarLogin(e);
			}
		});
		
		row1.add(btnAceptar);
		
		Button btnCancelar = new Button();
		btnCancelar.setEnabled(true);
		btnCancelar.setText("Cancelar");
		btnCancelar.setVisible(true);
		btnCancelar.setStyle(Styles.BOTONES_LOGIN_SCREEN);
		RowLayoutData btnCancelarLayoutData = new RowLayoutData();
		btnCancelarLayoutData.setInsets(new Insets(new Extent(30, Extent.PX),new Extent(0, Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX)));
		btnCancelar.setLayoutData(btnCancelarLayoutData);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelarEvt(e);
			}
		});
		row1.add(btnCancelar);
	}

	private void setUsuario(PropertyChangeEvent e) {
		this.usuario = (String) e.getNewValue();

	}
	
	private void btnCancelarEvt(ActionEvent e) {
		contra.setText("");
		login.setText("");

	}

	private void validarLogin(ActionEvent e) {
		boolean valid = false;
		valid = (this.usuario.equals("code") && this.contrasena.equals("solids"));

		if (valid) {
			Window win = (Window) getParent();
			//win.setContent(new MapaDesktop());
		} else {
			login.setText("");
			contra.setText("");
			this.msgError.setVisible(true);

		}
	}

	private void setContrasena(PropertyChangeEvent e) {

		this.contrasena = (String) e.getNewValue();
	}
}
