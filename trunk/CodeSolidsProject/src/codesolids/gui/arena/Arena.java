package codesolids.gui.arena;

import codesolids.gui.principal.*;

import java.util.ArrayList;
import java.util.List;
import codesolids.gui.style.*;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextArea;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import java.lang.Number;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.informagen.echo.app.CapacityBar;

import com.thoughtworks.xstream.converters.reflection.FieldKey;

import sun.misc.Cache;


/**
 * 
 * @author = Jose Luis Perez M.
 * @author = Antonio Lopez. 
 * @Colaborador = Fernando Osuna.
 * 
 */


public class Arena extends ContentPane{

	private Personaje p1;
	private Personaje p2;
	private Column col;
	private Label labelCp;

	private Poder poder;

	private Button btnAttack1;
	private Button btnAttack2;
	private Button btnAttack3;
	private Button btnAttack4;
	private Button btnAttack5;
	private Button btnAttack6;
	
	private Button btnHit;
	private Button btnLoadCp;

	private List<Poder> listPoder,poderesPc;

	private HtmlLayout htmllayaut;

	private CapacityBar barraVida1;
	private CapacityBar barraVida2;
	private CapacityBar barraPsinergia;
	private CapacityBar barraXp;

	private List<Number> listNumber;

	private boolean flag=false;
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------

	public Arena(){

		initGUI();
	}

	// --------------------------------------------------------------------------------

	private void initGUI() {

		add(initArena());

	}

	// --------------------------------------------------------------------------------

	public Component initArena(){

		try {
			htmllayaut = new HtmlLayout(getClass().getResourceAsStream("arena.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//-----------------------------------------------------------------


		HtmlLayoutData hld;
		hld = new HtmlLayoutData("batalla");

		col = new Column();
		col.setCellSpacing(new Extent(5));
		
		crearPersonaje();

		Column colEstado = new Column();
		colEstado.setInsets(new Insets(5,5,5,105));
		//colEstado.setBorder(new Border(new Extent(1),Color.BLACK,1));

		col.add(colEstado);
		colEstado.add(new Label(p1.getLogin()));

		Row row = new Row();
		row.setCellSpacing(new Extent(50));
		row.add(new Label("Lv. "+ p1.getLevel()));
		row.add(new Label("Gold "+ p1.getGold()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("XP"));
		barraXp = crearBarraVida1(Color.GREEN,Color.WHITE,p1.getXp(),160);
		row.add(barraXp);
		row.add(new Label("200/"+p1.getXp()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("HP"));
		barraVida1 = crearBarraVida1(Color.RED,Color.WHITE,p1.getHp(),0);
		row.add(barraVida1);
		row.add(new Label(p1.getHp()+"/"+barraVida1.getValues().get(0).intValue()));
		colEstado.add(row);

		row = new Row();
		row.setCellSpacing(new Extent(10));
		row.add(new Label("MP"));
		barraPsinergia = crearBarraVida1(Color.BLUE,Color.WHITE,p1.getCp(),0);
		row.add(barraPsinergia);
		labelCp = new Label();
		labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());
		row.add(labelCp);
		colEstado.add(row);

		ImageReference mA = new ResourceImageReference(p1.getImage());

		ImageReference mB = new ResourceImageReference(p2.getImage());

		Label magoa = new Label(mA);
		Label magob = new Label(mB);

		Row rowM = new Row();
		rowM.setCellSpacing(new Extent(200, Extent.PX));
		rowM.add(magoa);
		rowM.add(magob);

		col.add(rowM);

		Row rowA = new Row();
		rowA.setCellSpacing(new Extent(5));
		rowA.add(new Label("Lv "+ p1.getLevel()));
		rowA.add(barraVida1);

		Row rowB = new Row();
		rowB.setCellSpacing(new Extent(5));
		rowB.add(new Label("Lv "+ p2.getLevel()));
		barraVida2 = crearBarraVida1(Color.RED,Color.WHITE,p2.getHp(),0);
		rowB.add(barraVida2);

		row = new Row();
		row.setCellSpacing(new Extent(205));
		row.add(rowA);
		row.add(rowB);

		col.add(row);

		col.add(CreateButtonSpecial());

		col.add(CreateButtonBasic());
		

		Button exit = new Button("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnExitClicked();
				
			}
		});
		
		
		col.add(exit);
		
		col.setLayoutData(hld);
		htmllayaut.add(col);
		

		return htmllayaut;

	}

	public void crearPersonaje(){

		listPoder = new ArrayList<Poder>();
		poder = new Poder();
		poder.setDamage(150);
		poder.setPsinergia(30);
		poder.setName("Fuego");

		listPoder.add(poder);

		poder = new Poder();
		poder.setDamage(100);
		poder.setPsinergia(20);
		poder.setName("Llama");
		listPoder.add(poder);

		p1 = new Personaje("01",5,10000,40,1000,150,"Images/MagoA.png",listPoder);


		p2 = new Personaje("02",5,10000,40,1000,150,"Images/MagoB.png",poderesPc);

	}

	public CapacityBar crearBarraVida1(Color color1, Color color2, int indice1,int indice2){

		CapacityBar barra = new CapacityBar(10, 150); 
		barra.setShowTicks(false);
		barra.setReflectivity(0.1);
		List<Color> listColor = new ArrayList<Color>();
		listColor.add(color1);
		listColor.add(color2);
		barra.setColors(listColor);
		listNumber = new ArrayList<Number>();
		listNumber.add(indice1);
		listNumber.add(indice2); 
		barra.setValues(listNumber);     

		return barra;
	}

	public void FinalBattle(){

		if(barraVida1.getValues().get(0).intValue() <= 0){
			WindowPane windowPane = new WindowPane();
			add(windowPane);
			windowPane.setTitle("Se acabo");
			Label msg = new Label("El juego ha terminado.! J2 Win");
			windowPane.add(msg);
			windowPane.setModal(true);
			windowPane.setVisible(true);
			windowPane.setEnabled(true);

		}

		if(barraVida2.getValues().get(0).intValue() <= 0){

			WindowPane windowPane = new WindowPane();
			windowPane.setTitle("Se acabo");
			add(windowPane);
			Label msg = new Label("El juego ha terminado.! J1 Win");
			windowPane.add(msg);
			windowPane.setModal(true);
			windowPane.setVisible(true);

		}


	}

	private Row CreateButtonSpecial()
	{

		Row rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));

		btnAttack1 = new Button();
		btnAttack1.setText("1");
		btnAttack1.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnAttack1.setToolTipText("Damage: " + listPoder.get(0).getDamage() + //
				"\nCooldown: " + listPoder.get(0).getCooldown() +// 
				"\nPsinergia: " + listPoder.get(0).getPsinergia());
		btnAttack1.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack1.setHeight(new Extent(30));
		btnAttack1.setWidth(new Extent(30));
		btnAttack1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				b1Clicked();
			}
		});		
		rowBotonera.add(btnAttack1);

		btnAttack2 = new Button();
		btnAttack2.setText("2");
		btnAttack2.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnAttack2.setToolTipText("Damage: " + listPoder.get(1).getDamage() + //
				"\nCooldown: " + listPoder.get(1).getCooldown() +// 
				"\nPsinergia: " + listPoder.get(1).getPsinergia());
		btnAttack2.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack2.setHeight(new Extent(30));
		btnAttack2.setWidth(new Extent(30));
		btnAttack2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				b2Clicked();
			}
		});
		rowBotonera.add(btnAttack2);

		btnAttack3 = new Button();
		btnAttack3.setText("3");
		btnAttack3.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnAttack3.setToolTipText("Damage: " + listPoder.get(1).getDamage() + //
				"\nCooldown: " + listPoder.get(1).getCooldown() +// 
				"\nPsinergia: " + listPoder.get(1).getPsinergia());
		btnAttack3.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack3.setHeight(new Extent(30));
		btnAttack3.setWidth(new Extent(30));
		btnAttack3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//btnAttack3Clicked();
			}
		});
		rowBotonera.add(btnAttack3);

		btnAttack4 = new Button();
		btnAttack4.setText("4");
		btnAttack4.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		//btnAttack4.setToolTipText("Damage: 30\nCooldown: 2\nPsinergia: 45");
		btnAttack4.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack4.setHeight(new Extent(30));
		btnAttack4.setWidth(new Extent(30));
		btnAttack4.setEnabled(false);
		btnAttack4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//btnAttack4Clicked();
			}
		});
		rowBotonera.add(btnAttack4);

		btnAttack5 = new Button();
		btnAttack5.setText("5");
		btnAttack5.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		//btnAttack5.setToolTipText("Damage: 30\nCooldown: 2\nPsinergia: 45");
		btnAttack5.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack5.setHeight(new Extent(30));
		btnAttack5.setWidth(new Extent(30));
		btnAttack5.setEnabled(false);
		btnAttack5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//btnAttack5Clicked();
			}
		});
		rowBotonera.add(btnAttack5);

		btnAttack6 = new Button();
		btnAttack6.setText("6");
		btnAttack6.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		//btnAttack6.setToolTipText("Damage: 30\nCooldown: 2\nPsinergia: 45");
		btnAttack6.setStyle(Styles1.DEFAULT_STYLE);
		btnAttack6.setHeight(new Extent(30));
		btnAttack6.setWidth(new Extent(30));
		btnAttack6.setEnabled(false);		
		btnAttack6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//btnAttack6Clicked();
			}
		});
		rowBotonera.add(btnAttack6);

		rowBotonera.setInsets(new Insets(170,30,20,2));

		return rowBotonera;
	}
	
	
	private Row CreateButtonBasic()
	{
		Row rowBotonera = new Row();
		rowBotonera.setCellSpacing(new Extent(2));
	
		btnHit = new Button();
		btnHit.setText("A");
		btnHit.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnHit.setStyle(Styles1.DEFAULT_STYLE);
		btnHit.setHeight(new Extent(30));
		btnHit.setWidth(new Extent(30));
		btnHit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnHitClicked();
			}
		});		
		rowBotonera.add(btnHit);
		
		btnLoadCp = new Button();
		btnLoadCp.setText("P");
		btnLoadCp.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnLoadCp.setStyle(Styles1.DEFAULT_STYLE);
		btnLoadCp.setHeight(new Extent(30));
		btnLoadCp.setWidth(new Extent(30));
		btnLoadCp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnLoadCpClicked();
			}
		});		
		rowBotonera.add(btnLoadCp);		
		
		rowBotonera.setInsets(new Insets(255,2,20,20));
		
		return rowBotonera;
	}
	
	

	public void b1Clicked(){

		int dano = ((int)(Math.random()*(40))+90);

		if( (barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida2.setValues(listNumber);
			FinalBattle();

			flag=true;
		}

		else{


			if(barraPsinergia.getValues().get(0).intValue() < listPoder.get(0).getPsinergia() )
			{
				labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());
			}

			else{

				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()); 
				listNumber.add( p2.getHp() - (barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(barraPsinergia.getValues().get(0).intValue() - listPoder.get(0).getPsinergia());
				listNumber.add( p1.getCp() - (barraPsinergia.getValues().get(0).intValue() - listPoder.get(0).getPsinergia()) );
				barraPsinergia.setValues(listNumber);
				
				labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());

				simular(dano);

			}
		} 

		if ( (barraVida1.getValues().get(0).intValue() - dano <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida1.setValues(listNumber);
			FinalBattle();

			//			windowPane.addWindowPaneListener(new WindowPaneListener() {
			//
			//				@Override
			//				public void windowPaneClosing(WindowPaneEvent arg0) {
			//					col.removeAll();
			////					add(new Label ("Chao"));
			//
			//				}
			//			}); 

			flag=true;
		}

	}

	public void b2Clicked(){


		int dano = ((int)(Math.random()*(40))+90);

		if( (barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida2.setValues(listNumber);
			FinalBattle();

			flag=true;
		}

		else{


			if(barraPsinergia.getValues().get(0).intValue() < listPoder.get(1).getPsinergia() )
			{
				labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());
			}

			else{

				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()); 
				listNumber.add( p2.getHp() - (barraVida2.getValues().get(0).intValue() - listPoder.get(0).getDamage()) );

				barraVida2.setValues(listNumber);

				listNumber = new ArrayList<Number>();
				listNumber.add(barraPsinergia.getValues().get(0).intValue() - listPoder.get(1).getPsinergia());
				listNumber.add( p1.getCp() - (barraPsinergia.getValues().get(0).intValue() - listPoder.get(1).getPsinergia()) );
				barraPsinergia.setValues(listNumber);
				
				labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());

				simular(dano);

			}
		} 

		if ( (barraVida1.getValues().get(0).intValue() - dano <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida1.setValues(listNumber);
			FinalBattle();

			//			windowPane.addWindowPaneListener(new WindowPaneListener() {
			//
			//				@Override
			//				public void windowPaneClosing(WindowPaneEvent arg0) {
			//					col.removeAll();
			////					add(new Label ("Chao"));
			//
			//				}
			//			}); 

			flag=true;
		}
	}

	private void simular(int dano){



		listNumber = new ArrayList<Number>();
		listNumber.add(barraVida1.getValues().get(0).intValue() - dano); 
		listNumber.add( p1.getHp() - (barraVida1.getValues().get(0).intValue() - dano) );

		barraVida1.setValues(listNumber);


	}
	
	private void btnHitClicked()
	{
		
		int dano = ((int)(Math.random()*(50))+150);

		if( (barraVida2.getValues().get(0).intValue() - 60<=0) && flag==false ){
			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida2.setValues(listNumber);
			FinalBattle();

			flag=true;
		}

		else{


				listNumber = new ArrayList<Number>();
				listNumber.add(barraVida2.getValues().get(0).intValue() - 60); 
				listNumber.add( p2.getHp() - (barraVida2.getValues().get(0).intValue() - 60) );

				barraVida2.setValues(listNumber);
				simular(dano);

		} 

		if ( (barraVida1.getValues().get(0).intValue() - dano <=0) && flag==false) {

			listNumber = new ArrayList<Number>();
			listNumber.add(0);
			listNumber.add(150);
			barraVida1.setValues(listNumber);
			FinalBattle();

			flag=true;
		}		
	}
	
	
	
	private void btnLoadCpClicked()
	{
		int dano = ((int)(Math.random()*(50))+150);

		
		if((barraPsinergia.getValues().get(0).intValue() + 30) >= 100)
		{	
			labelCp.setText(p1.getCp()+"/"+p1.getCp());	
			
			listNumber = new ArrayList<Number>();
			listNumber.add(p1.getCp());
			listNumber.add(0);
			barraPsinergia.setValues(listNumber);
			
			simular(dano);
			FinalBattle();

		}
		else
		{
			int aux = (barraPsinergia.getValues().get(0).intValue()+30);
			
			labelCp.setText(  aux+ "/" + p1.getCp());
			
			listNumber = new ArrayList<Number>();
			listNumber.add(aux);
			listNumber.add( p1.getCp() - aux );
			barraPsinergia.setValues(listNumber);
			
			labelCp.setText(p1.getCp()+"/"+barraPsinergia.getValues().get(0).intValue());
			
			simular(dano);
			FinalBattle();


		}
	}
	
	private void btnExitClicked(){
		
		removeAll();
		add(new PrincipalDesktop());
		
	}
	
}
