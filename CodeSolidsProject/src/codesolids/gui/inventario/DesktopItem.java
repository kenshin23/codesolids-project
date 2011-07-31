package codesolids.gui.inventario;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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

import codesolids.bd.clases.Item;
import codesolids.bd.clases.Personaje;
import codesolids.bd.clases.PersonajeItem;
import codesolids.bd.clases.PersonajePoderes;
import codesolids.bd.clases.Poderes;
import codesolids.bd.hibernate.SessionHibernate;
import codesolids.gui.perfil.PerfilDesktop;
import codesolids.gui.principal.PrincipalApp;
import codesolids.gui.style.StyleWindow;
import codesolids.gui.style.Styles1;
import codesolids.util.TestTableModel;

import echopoint.HtmlLayout;
import echopoint.ImageIcon;
import echopoint.layout.HtmlLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Border;
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
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * 
 * @author Antonio López
 *
 */

public class DesktopItem extends ContentPane {

	TestTableModel tableDtaModel;
	TestTableModel tableDtaModelItems;
	
	private Column colPanel;
	private Column colEquip;
	
	private Personaje personaje;
	
	public DesktopItem()
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

		loadItemPlayerType("Espada");
		
		return retHtmlLayout;
	}
	
	private Column initPanel() {
		
		Panel panel = new Panel();

		ImageReference imgR = new ResourceImageReference("Images/cartel3.png");
		FillImage imgF = new FillImage(imgR);
		
		panel.setWidth(new Extent(950));
		panel.setHeight(new Extent(400));
		panel.setBackgroundImage(imgF);
		
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column colCartel = new Column();
		colCartel.setInsets(new Insets(5, 20, 20, 20));
		colCartel.add(rowBotonera());
		colCartel.add(rowInventario());
		
		panel.add(colCartel);
		
		colCartel = new Column();
		colCartel.add(panel);
		

		return colCartel;
	}
	
	private Row rowBotonera()
	{
		
		Row row = new Row();
		row.setCellSpacing(new Extent(15));
		row.setInsets(new Insets(5, 30, 5, 20));
		
		Button btnArmas = new Button("Armas");
		btnArmas.setToolTipText("Armas");
		btnArmas.setStyle(Styles1.DEFAULT_STYLE);
		
		btnArmas.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnArmasClicked();
			}
		});
		row.add(btnArmas);
		
		Button btnArmaduras = new Button("Armaduras");
		btnArmaduras.setToolTipText("Armaduras");
		btnArmaduras.setStyle(Styles1.DEFAULT_STYLE);
		
		btnArmaduras.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnArmadurasClicked();
			}
		});
		row.add(btnArmaduras);
		
		Button btnItems = new Button("Items");
		btnItems.setToolTipText("Items");
		btnItems.setStyle(Styles1.DEFAULT_STYLE);
		
		btnItems.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnItemsClicked();
			}
		});
		row.add(btnItems);
		
		Button btnExit = new Button("Salir");
		btnExit.setToolTipText("Regresar a Perfil");
		btnExit.setStyle(Styles1.DEFAULT_STYLE);
		
		btnExit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				btnExitClicked();
			}
		});
		row.add(btnExit);
		
		return row;
	}
	
	private Row rowInventario() {
		
		Row row = new Row();
		row.setCellSpacing(new Extent(30));
		
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
	    		Item item = (Item) element;
	    		return item.getLevel();
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
	    		Item item = (Item) element;
	    		return item.getDirImage();	    		
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
	    		Item item = (Item) element;
	    		return item.getName();
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
	    
	    tableColumn = new TableColumn(){      
	    	@Override
	    	public Object getValue(ETable table, Object element) {
	    		Item item = (Item) element;
	    		Session session = SessionHibernate.getInstance().getSession();
	    		session.beginTransaction();
	    		String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt";
	    		Query query  = session.createQuery(queryStr);
	    		query.setInteger("idPlayer", personaje.getId());
	    		query.setString("nameIt", item.getName());
	    		List<PersonajeItem> list = query.list();
	    		session.getTransaction().commit();			  	        
	    		session.close();
	    		return list.size();
	    	}
	    };

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(87, 205, 211));
	    lcr.setForeground(Color.WHITE);
	    tableColumn.setHeadCellRenderer(lcr);

	    lcr = new LabelCellRenderer();
	    lcr.setBackground(new Color(226,211,161));
	    lcr.setForeground(Color.BLACK);

	    tableColumn.setDataCellRenderer(lcr);
	    tableColModel.getTableColumnList().add(tableColumn);

	    tableColumn.setWidth(new Extent(100));
	    tableColumn.setHeadValue("Cantidad");

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

				boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

				Button btnVer = new Button("Ver");
				btnVer.setStyle(Styles1.DEFAULT_STYLE);
				btnVer.setEnabled(editable);
				btnVer.setToolTipText("Ver");

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
		
		ImageReference imgR = new ResourceImageReference("Images/Util/panel.png");
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
	
	private void btnVerClicked(int row) {
		
		Item item = (Item) tableDtaModel.getElementAt(row);
		colPanel.removeAll();
		
		verInfo(item,row);
		
	}

	private void verInfo(final Item item,final int fila)
	{

		Column col = new Column();
		col.setCellSpacing(new Extent(5));
		
		Row row = new Row();
		row.setCellSpacing(new Extent(5));
		
		ImageReference imgR = new ResourceImageReference(item.getDirImage().toString());
		ImageIcon imgI = new ImageIcon(imgR);
		imgI.setWidth(new Extent(42));
		imgI.setHeight(new Extent(42));		
		row.add(imgI);
		
		Column colInfo = new Column();
		colInfo.setCellSpacing(new Extent(2));
		
		colInfo.add(new Label("Lv. " + item.getLevel()));

		colInfo.add(new Label(item.getName()));
		
		if( item.getTipo().equals("Espada") )
		{
			Label lblDamage = new Label();
			lblDamage.setForeground(Color.RED);
			lblDamage.setText("Daño " + item.getIndex());
			colInfo.add(lblDamage);
		}
		else if( item.getTipo().equals("Armadura"))
		{
			Label lblDefensa = new Label();
			lblDefensa.setForeground(Color.GREEN);
			lblDefensa.setText("Defensa " + item.getIndex());
			colInfo.add(lblDefensa);
		}
		else if( item.getTipo().equals("Bomba") )
		{
			Label lblDamage = new Label();
			lblDamage.setForeground(Color.RED);
			lblDamage.setText("Daño " + item.getIndex());
			colInfo.add(lblDamage);			
		}
		else if( item.getTipo().equals("Pocion") )
		{
			Label lblEffect = new Label();
			lblEffect.setForeground(Color.BLUE);
			lblEffect.setText("Efecto " + item.getIndex());
			colInfo.add(lblEffect);
		}
		
		row.add(colInfo);
		
		col.add(row);
		
		col.add(new Label(item.getDescripcion()));

		Button btnEquipar = new Button("Equipar");
		btnEquipar.setToolTipText("Equipar Items");
		btnEquipar.setStyle(Styles1.DEFAULT_STYLE);
		btnEquipar.setWidth(new Extent(50));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("itemRef", item),Restrictions.eq("personajeRef", personaje)));
		
		List<PersonajeItem> pItem = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		if( (pItem.get(0).isEquipado() == false) )
		{	
			btnEquipar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					btnEquiparClicked(item,fila);
				}
			});
			col.add(btnEquipar);
		}
		else if((pItem.get(0).isEquipado() == false) || (pItem.get(0).getItemRef().getTipo().equals("Espada")) || (pItem.get(0).getItemRef().getTipo().equals("Armadura")) )
		{
			btnEquipar.setVisible(false);
		}
		
		colPanel.setInsets(new Insets(10, 20, 10, 20));
		colPanel.add(col);
		
	}
	
	private void btnEquiparClicked(Item item,int row) {
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();

		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.and(Restrictions.eq("itemRef", item),Restrictions.eq("personajeRef", personaje)));
		
		List<PersonajeItem> pItem = criteria.list();
		
		criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
		
		List<PersonajeItem> listI = criteria.list();
		
		if( (pItem.get(0).isEquipado() == false) && (pItem.get(0).getPersonajeRef().getId() == personaje.getId()) && (pItem.get(0).getItemRef().getTipo().equals("Espada")) )
		{
			pItem.get(0).setEquipado(true);
			
			for( int i = 0; i < listI.size(); i++ )
			{
				if( (listI.get(i).getItemRef().getTipo().equals("Espada")) && (listI.get(i).getPersonajeRef().getId() == personaje.getId()) )
				{
					listI.get(i).setEquipado(false);
				}
			}
		}
		else if( (pItem.get(0).isEquipado() == true) && (pItem.get(0).getItemRef().getTipo().equals("Espada")) )
		{
			Label lblText = new Label();
			lblText.setText("Usted ya tiene equipado esta Arma.");
			CreateWindow(lblText);
		}

		if( (pItem.get(0).isEquipado() == false) && (pItem.get(0).getPersonajeRef().getId() == personaje.getId()) && (pItem.get(0).getItemRef().getTipo().equals("Armadura")) )
		{
			pItem.get(0).setEquipado(true);
			for( int i = 0; i < listI.size(); i++ )
			{
				if ((listI.get(i).getItemRef().getTipo().equals("Armadura")) && (listI.get(i).getPersonajeRef().getId() == personaje.getId()) )
				{
					listI.get(i).setEquipado(false);
				}
			}
		}
		else if( (pItem.get(0).isEquipado() == true) && (pItem.get(0).getItemRef().getTipo().equals("Armadura")) )
		{
			Label lblText = new Label();
			lblText.setText("Usted ya tiene equipado esta Armadura.");
			CreateWindow(lblText);
		}
		
		session.getTransaction().commit();
		session.close();
		
		colEquip.removeAll();
		colEquip.add(rowItemEquip());
		
	}
	
	private void CreateWindow(Label lblText)
	{
		final WindowPane ventanaCompra = new WindowPane();
		ventanaCompra.setTitle("Inventario");
		ventanaCompra.setWidth(new Extent(300));
		ventanaCompra.setMaximumWidth(new Extent(300));
		ventanaCompra.setMaximumHeight(new Extent(150));
		ventanaCompra.setMovable(false);
		ventanaCompra.setResizable(false);
		ventanaCompra.setModal(true);
		ventanaCompra.setStyle(StyleWindow.ACADEMY_STYLE);

		Button btnAceptar = new Button("Aceptar");
		btnAceptar.setTextAlignment((new Alignment(Alignment.CENTER,Alignment.CENTER)));
		btnAceptar.setToolTipText("Aceptar");
		btnAceptar.setStyle(Styles1.DEFAULT_STYLE);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ventanaCompra.userClose();
			}
		});

		Row rowBtn = new Row();
		rowBtn.setInsets(new Insets(125, 10, 0, 20));
		rowBtn.add(btnAceptar);

		Column colPane = new Column();
		colPane.setInsets(new Insets(5, 5, 5, 0));
		colPane.setCellSpacing(new Extent(10));
		colPane.add(lblText);

		colPane.add(rowBtn);
		
		ventanaCompra.add(colPane);

		add(ventanaCompra);
		  
	}
	
	private Column loadEquip()
	{
		
		Panel panel = new Panel();
		panel.setAlignment(Alignment.ALIGN_CENTER);
		
		Column col = new Column();
		col.setCellSpacing(new Extent(15));

		col.add(panelDescription());
		col.add(rowItemEquip());
		
		panel.add(col);
		
		col = new Column();
		col.add(panel);
		
		return col;
	}
	
	private Column rowItemEquip()
	{
		colEquip = new Column();
		
		Row row = new Row();
		row.setCellSpacing(new Extent(25));
		
		row.add(colItemsEquip());
		row.add(colObjectEquip());
		
		colEquip.add(row);
		
		return colEquip;
	}
	
	private Column colItemsEquip()
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(5));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(PersonajeItem.class).add(Restrictions.eq("equipado", true));
		
		List<PersonajeItem> list = criteria.list();
		
		session.getTransaction().commit();
		session.close();
		
		Label lblPocion = new Label();
		lblPocion.setText("Pociones: " + 0);
		
		Label lblBomba = new Label();
		lblBomba.setText("Bombas: " + 0);
		
		col.add(lblPocion);
		col.add(lblBomba);
		
		return col;
	}
	
	private Column colObjectEquip()
	{
		Column col = new Column();
		col.setCellSpacing(new Extent(5));
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());

		String queryStr = "SELECT i FROM PersonajeItem AS pi, Item AS i WHERE pi.itemRef = i.id AND pi.equipado = true AND pi.personajeRef = " + personaje.getId() + " ORDER BY pi.itemRef ASC";
		Query query = session.createQuery(queryStr);
		
		List<Item> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		for(int i = 0; i < list.size(); i++)
		{
			if( list.get(i).getTipo().equals("Espada") )
			{
				Label lblArma = new Label();
				lblArma.setText(list.get(i).getName() + " <-> Equipada");
				col.add(lblArma);
			}
			else if ( list.get(i).getTipo().equals("Armadura") )
			{
				Label lblArmor = new Label();
				lblArmor.setText(list.get(i).getName() + " <-> Equipada");
				col.add(lblArmor);
			}
		}
		
		return col;
	}
		
	private void loadItemPlayerType(String tipo)
	{
		
		Session session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		List<Item> listBD = session.createCriteria(Item.class).addOrder(Order.asc("id")).list();
		session.getTransaction().commit();
		session.close();
		
		session = SessionHibernate.getInstance().getSession();
		session.beginTransaction();
		
		personaje = (Personaje) session.load(Personaje.class, personaje.getId());
		
		for(Item item: listBD){
			
			if( tipo.equals("Items") )
			{
				String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt AND tipo= :tipoIt1 OR tipo= :tipoIt2";				
				Query query  = session.createQuery(queryStr);
				query.setInteger("idPlayer", personaje.getId());
				query.setString("nameIt", item.getName());
				query.setString("tipoIt1", "Pocion");
				query.setString("tipoIt2", "Bomba");
				List<Object> list = query.list();
				
				addItemTableType(list,tipo);
			}
			else
			{
				String queryStr = "SELECT personajeItemList FROM Item WHERE personajeref_id = :idPlayer AND name= :nameIt AND tipo= :tipoIt";
				Query query  = session.createQuery(queryStr);
				query.setInteger("idPlayer", personaje.getId());
				query.setString("nameIt", item.getName());
				query.setString("tipoIt", tipo);
				List<Object> list = query.list();
				
				addItemTableType(list,tipo);
			}
		}		
			
		session.getTransaction().commit();
		session.close();		
	}
	
	public void addItemTableType(List<Object> resultQuery,String tipo) {
		Iterator<Object> iter = resultQuery.iterator();
  	    if (!iter.hasNext()) {
  	    	return;
  	    }
  	    while (iter.hasNext()) {
			PersonajeItem it = (PersonajeItem) iter.next();
			cargarType(it,tipo);
			return;
  	    }    		
	}
	
	private void cargarType(PersonajeItem obj,String tipo)
	{
		Item item = new Item();
		item.setId(obj.getItemRef().getId());
		item.setLevel(obj.getItemRef().getLevel());
		item.setName(obj.getItemRef().getName());
		item.setPrice(obj.getItemRef().getPrice());
		item.setIndex(obj.getItemRef().getIndex());
		item.setTipo(obj.getItemRef().getTipo());
		item.setDescripcion(obj.getItemRef().getDescripcion());
		item.setUso(obj.getItemRef().isUso());
		item.setDirImage(obj.getItemRef().getDirImage());
		item.setInshop(obj.getItemRef().isInshop());

		if( item.getTipo().equals("Pocion") || item.getTipo().equals("Bomba") )
			tableDtaModel.add(item);
		else if( item.getTipo().equals(tipo) )
			tableDtaModel.add(item);
	}
	
	private void btnArmasClicked() {
		
		colPanel.removeAll();
		tableDtaModel.clear();
		
		loadItemPlayerType("Espada");
		
	}
	
	private void btnArmadurasClicked() {
		
		colPanel.removeAll();
		tableDtaModel.clear();

		loadItemPlayerType("Armadura");
		
	}
	
	private void btnItemsClicked() {
		
		colPanel.removeAll();
		tableDtaModel.clear();
		
		loadItemPlayerType("Items");
				
	}
	
	private void btnExitClicked() {
		removeAll();
		add(new PerfilDesktop());
	}
}