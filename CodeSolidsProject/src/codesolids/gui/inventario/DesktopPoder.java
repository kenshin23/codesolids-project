package codesolids.gui.inventario;

import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.bd.clases.Poderes;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.perfil.Perfil;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.Styles1;
import codesolids.util.ImageReferenceCache;
import codesolids.util.MessageBox;
import codesolids.util.TestTableModel;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.ImageCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;

import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;

/**
 * 
 * @author Antonio López
 *
 */

public class DesktopPoder extends ContentPane{

	TestTableModel tableDtaModel;
	
	private Personaje personaje;
	
	private Column colPanel; 
	private Column colEquip;
	
	public DesktopPoder()
	{
		
		PrincipalApp app = (PrincipalApp) ApplicationInstance.getActive();
		
		personaje = app.getPersonaje();
		
		initGUI();
	}
	
	private void initGUI()
	{
		add(initInventario());
	}

	private Component initInventario() {
		
		setBackground(Color.BLACK);
		
		HtmlLayout retHtmlLayout;
		
		try{
			
			retHtmlLayout = new HtmlLayout(getClass().getResourceAsStream("templateinv.html"), "UTF-8");
			
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
				
		Column colP = new Column();
		colP.add(initPanel());
		
		HtmlLayoutData hld = new HtmlLayoutData("cartel");
		
		colP.setLayoutData(hld);
		retHtmlLayout.add(colP);

		loadTableBD();
		
		return retHtmlLayout;
	}
	
	private Column initPanel() {
		
		Panel panel = new Panel();

		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/cartel3.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(950));
		panel.setHeight(new Extent(400));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		colCartel.setInsets(new Insets(5, 20, 20, 20));
		colCartel.add(rowInventario());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.add(panel);
		
		return colCartel;
	}

	private Row rowInventario() {
		
		Row row = new Row();
		row.setCellSpacing(new Extent(30));
		row.setInsets(new Insets(5, 20, 5, 20));
		
		row.add(loadTable());
		row.add(loadEquip());
		
		return row;
	}

	private Column loadTable()
	{
		Column col = new Column();
				
		ETable table;
		
		TableColModel tableColModel = initTableColModel();
		TableSelModel tableSelModel = new TableSelModel();

		tableDtaModel = new TestTableModel();
	    tableDtaModel.setEditable(true);
	    tableDtaModel.setPageSize(6);

	    table = new ETable();
	    
	    table.setTableDtaModel(tableDtaModel);
	    table.setTableColModel(tableColModel);
	    table.setTableSelModel(tableSelModel);
		col.add(table);
		
		ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
	    tableNavigation.setForeground(Color.WHITE);
	    tableNavigation.setAlignment(Alignment.ALIGN_CENTER);
	    col.add(tableNavigation);
	    
		return col;
	}
	
	private TableColModel initTableColModel() {
		
		TableColModel tableColModel = new TableColModel();
		
		TableColumn tableColumn;
	    LabelCellRenderer lcr;
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getLevel();
	    	}
	    };
		
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(20));
	    tableColumn.setHeadValue("Lv.");
	    
	    tableColumn = new TableColumn(){
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getDirImage();	    		
	    	}
	    };
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);
	    
	    ImageCellRenderer icr = new ImageCellRenderer();
	    icr.setBackground(new Color(226,211,161));
	    icr.setWidth(new Extent(15));
	    icr.setHeight(new Extent(15));
	    
	    tableColumn.setDataCellRenderer(icr);
	    tableColModel.getTableColumnList().add(tableColumn);
	    
	    tableColumn.setWidth(new Extent(15));
	    tableColumn.setHeadValue("");  
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Poderes poder = (Poderes) element;
	    		return poder.getName();
	    	}
	    };
	    
	    tableColumn.setWidth(new Extent(180));
	    tableColumn.setHeadValue("Nombre");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    tableColumn.setHeadCellRenderer(lcr);	    
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);
	    lcr.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
	    
	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);    	 
	    
	    tableColumn = new TableColumn();
	    tableColumn.setWidth(new Extent(30));
	    tableColumn.setHeadValue("");
	    
	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    tableColumn.setHeadCellRenderer(lcr);

	    tableColumn.setDataCellRenderer(initNestedCellRenderer());
		tableColModel.getTableColumnList().add(tableColumn);
	    
		return tableColModel;
	}

	private CellRenderer initNestedCellRenderer() 
	{

		NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();

		nestedCellRenderer.setBackground(new Color(226,211,161));

		nestedCellRenderer.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));

		nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
			@Override    
			public Component getCellRenderer( //
					final ETable table, final Object value, final int col, final int row) {


				Button btnVer = new Button();
				btnVer.setToolTipText("Ver");
				btnVer.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/ver2.png")));
				btnVer.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/ver2MouseOver.png"))));
				btnVer.setRolloverEnabled(true);
				btnVer.setHeight(new Extent(23));
				btnVer.setWidth(new Extent(35));

				btnVer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btnVerClicked(row);
					}
				});
				
				return btnVer;
			}
		});

		return nestedCellRenderer;
	}
	
	private Column panelDescription()
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/panel.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(300));
		panel.setHeight(new Extent(200));
		panel.setBackgroundImage(imgF);
		
		Column colPanel = new Column();
		colPanel.setInsets(new Insets(5, 5, 5, 5));
		colPanel.setCellSpacing(new Extent(5));
		colPanel.add(initDescription());

		panel.add(colPanel);

		colPanel = new Column();
		colPanel.add(panel);
		
		return colPanel;
	}
	
	private Column initDescription() {

		colPanel = new Column();

		return colPanel;
	}
	
	private Column loadEquip()
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(15));
		
		col.add(panelDescription());
		col.add(rowPowerEquip());
		col.add(loadButton());
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column powerEquip(final int posicion)
	{

		Column col = new Column();
		col.setCellSpacing(new Extent(2));

		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());

		String queryStr = "SELECT po FROM PersonajePoderes AS pp, Poderes AS po WHERE personajeref_id = :idPlayer AND pp.poderesRef = po.id AND equipado = true";
		Query query  = session.createQuery(queryStr);
		query.setInteger("idPlayer", personaje.getId());
		
		final List<Poderes> list = query.list();
				
		session.getTransaction().commit();
		session.close();
		
		if( posicion < list.size() )
		{
			if( list.get(posicion).getTipo().equals(personaje.getTipo()))
			{
				ImageReference imgR = ImageReferenceCache.getInstance().getImageReference(list.get(posicion).getDirImage());
				ImageIcon imgI = new ImageIcon(imgR);
				imgI.setWidth(new Extent(42));
				imgI.setHeight(new Extent(42));
				col.add(imgI);
			}
			else
			{
				ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/vacioInventario.png");
				ImageIcon imgI = new ImageIcon(imgR);
				imgI.setWidth(new Extent(42));
				imgI.setHeight(new Extent(42));
				col.add(imgI);
			}
		}	
		else
		{
			ImageReference imgR = ImageReferenceCache.getInstance().getImageReference("Images/Util/vacioInventario.png");
			ImageIcon imgI = new ImageIcon(imgR);
			imgI.setWidth(new Extent(42));
			imgI.setHeight(new Extent(42));
			col.add(imgI);
		}
		
		Button btnQuitar = new Button();
		btnQuitar.setToolTipText("Quitar el poder");
		btnQuitar.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/quitar.png")));
		btnQuitar.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/quitarMouseOver.png")));
		btnQuitar.setRolloverEnabled(true);
		btnQuitar.setHeight(new Extent(24));
		btnQuitar.setWidth(new Extent(42));
		
		if( posicion < list.size() )
		{
			btnQuitar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					btnQuitarClicked(list.get(posicion));
				}
			});
		}
		else
		{
			btnQuitar.setEnabled(false);
		}
		
		col.add(btnQuitar);
		
		return col;
	}
	
	private Column rowPowerEquip()
	{
		
		colEquip = new Column();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		for(int i = 0; i < 6; i++ )
		{
			row.add(powerEquip(i));
		}
		
		colEquip.add(row);	
		
		return colEquip;
	}
	
	private Column loadButton()
	{
		Column col = new Column();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(20));
		
		Button btnClear = new Button();
		btnClear.setToolTipText("Limpiar Poderes");
		btnClear.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/limpiar.png")));
		btnClear.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/limpiarMouseOver.png"))));
		btnClear.setRolloverEnabled(true);
		btnClear.setHeight(new Extent(27));
		btnClear.setWidth(new Extent(103));
		
		btnClear.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnClearClicked();
			}
		});
		row.add(btnClear);
		
		Button btnExit = new Button();
		btnExit.setToolTipText("Regresar Perfil");
		btnExit.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/regresar.png")));
		btnExit.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/regresarMouseOver.png"))));
		btnExit.setRolloverEnabled(true);
		btnExit.setHeight(new Extent(27));
		btnExit.setWidth(new Extent(103));
		
		btnExit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnExitClicked();
			}
		});
		row.add(btnExit);
		
		col.add(row);
		
		return col;
	}
	
	private void loadTableBD()
	{
		
	    Session session = SessionHibernate.getInstance().getSession();
	    session.beginTransaction();
			    
	    personaje = (Personaje) session.load(Personaje.class, personaje.getId());
	    
	    Criteria criteria = session.createCriteria(PersonajePoderes.class).add(Restrictions.and//
	    																		(Restrictions.eq("personajeRef", personaje), Restrictions.eq("learnProgreso", false))).addOrder(Order.asc("poderesRef"));
	    
	    List<PersonajePoderes> listP = criteria.list();
	    
	    session.getTransaction().commit();
	    session.close();
		
	    for(int j = 0; j < listP.size(); j++)
	    	tableDtaModel.add(listP.get(j).getPoderesRef());
	}


	private void btnVerClicked(int row) {
		
		Poderes poder = (Poderes) tableDtaModel.getElementAt(row);
		colPanel.removeAll();
		
		verInfo(poder);
		
	}

	private void verInfo(final Poderes poder)
	{

		Column col = new Column();
		col.setCellSpacing(new Extent(5));
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		ImageReference imgR = ImageReferenceCache.getInstance().getImageReference(poder.getDirImage().toString());
		ImageIcon imgI = new ImageIcon(imgR);
		imgI.setWidth(new Extent(42));
		imgI.setHeight(new Extent(42));		
		row.add(imgI);
		
		Column colInfo = new Column();
		colInfo.setCellSpacing(new Extent(2));
		
		colInfo.add(new Label("Lv. " + poder.getLevel()));

		colInfo.add(new Label(poder.getName()));
		
		Label lblDamage = new Label();
		lblDamage.setForeground(Color.RED);
		lblDamage.setText("Daño " + poder.getDamage());
		colInfo.add(lblDamage);
		
		Label lblMp = new Label();
		lblMp.setForeground(Color.BLUE);
		lblMp.setText("Psinergia " + poder.getPsinergia());
		colInfo.add(lblMp);
		
		Label lblCooldown = new Label();
		lblCooldown.setForeground(Color.ORANGE);
		lblCooldown.setText("Cooldown " + poder.getCooldown());
		colInfo.add(lblCooldown);
		row.add(colInfo);
		
		col.add(row);
		
		col.add(new Label(poder.getDescripcion()));

		Button btnEquipar = new Button();
		btnEquipar.setToolTipText("Equipar Poder");
		btnEquipar.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/equipar.png")));
		btnEquipar.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference(("Images/Botones/equiparMouseOver.png"))));
		btnEquipar.setRolloverEnabled(true);
		btnEquipar.setHeight(new Extent(27));
		btnEquipar.setWidth(new Extent(103));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Criteria criteria = session.createCriteria(PersonajePoderes.class).add(Restrictions.and(Restrictions.eq("personajeRef", personaje), Restrictions.eq("poderesRef", poder)));
		PersonajePoderes pPoderes = (PersonajePoderes) criteria.uniqueResult();
		
		session.getTransaction().commit();
		session.close();
		
		if( pPoderes.getEquipado() == false)
		{	
			btnEquipar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					btnEquiparClicked(poder);
				}
			});
			col.add(btnEquipar);
		}
		else
		{
			btnEquipar.setVisible(false);
		}
		
		colPanel.setInsets(new Insets(10, 20, 10, 20));
		colPanel.add(col);
		
	}
	
	private void btnEquiparClicked(Poderes poder) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		String queryStr = "SELECT max(orderEquipado) FROM PersonajePoderes AS pp WHERE pp.personajeRef = " + personaje.getId();
		Query query = session.createQuery(queryStr);
		
		List list = query.list();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Criteria criteria = session.createCriteria(PersonajePoderes.class).add(Restrictions.and(Restrictions.eq("personajeRef", personaje), Restrictions.eq("poderesRef", poder)));
		PersonajePoderes pPoderes = (PersonajePoderes) criteria.uniqueResult();
		
		int order = 0;
		
		if( pPoderes.getPersonajeRef().getId() == personaje.getId() )
		{
			order = ((Integer) list.get(0)) + 1;
		}

		if( order > 6 )
		{
			Label lblText = new Label();
			lblText.setText("Usted no puede equipar mas de 6 habilidades.");
			createWindow(lblText);
		}
		else
		{
			if( (pPoderes.getEquipado() == false) && (pPoderes.getPersonajeRef().getId() == personaje.getId()) )
			{
				pPoderes.setEquipado(true);
				pPoderes.setOrderEquipado(order);
			}
			else
			{
				Label lblText = new Label();
				lblText.setText("Usted tiene equipado esta habilidad todavia.");
				createWindow(lblText);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		
		colEquip.removeAll();
		colEquip.add(rowPowerEquip());
		
	}
	
	private void createWindow(Label lblText)
	{
		Column col = new Column();
		col.add(lblText);

		MessageBox messageBox  = new MessageBox("Inventario",// 
				col,// 
				400, 130,//
				MessageBox.ACCEPT_WINDOW);
		add(messageBox);
	}
	
	private void btnQuitarClicked(Poderes poder) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(PersonajePoderes.class).add(Restrictions.eq("equipado", true));
		List<PersonajePoderes> list = criteria.list();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		int pos = 0;
		
		for( int i = 0; i < list.size(); i++ )
		{
			if( (list.get(i).getPoderesRef().getId() == poder.getId()) && (list.get(i).getPersonajeRef().getId() == personaje.getId()) )
			{
				pos = list.get(i).getOrderEquipado();
				list.get(i).setEquipado(false);
				list.get(i).setOrderEquipado(0);
			}		
		}
		
		for( int j = 0; j < list.size(); j++ )
		{
			if( list.get(j).getOrderEquipado() > pos )
				list.get(j).setOrderEquipado(list.get(j).getOrderEquipado() - 1);
		}
		
		session.getTransaction().commit();
		session.close();
		
		colEquip.removeAll();
		colEquip.add(rowPowerEquip());
		
	}
	
	private void btnClearClicked() {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(PersonajePoderes.class).add(Restrictions.eq("equipado", true));
		List<PersonajePoderes> list = criteria.list();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		for( int i = 0; i < list.size(); i++ )
		{
			if( list.get(i).getPersonajeRef().getId() == personaje.getId() )
			{
				list.get(i).setEquipado(false);
				list.get(i).setOrderEquipado(0);
			}
		}
		
		session.getTransaction().commit();
		session.close();
		
		colEquip.removeAll();
		colEquip.add(rowPowerEquip());
	}
	
	private void btnExitClicked() {
		removeAll();
		add(new Perfil());
	}
	
}
