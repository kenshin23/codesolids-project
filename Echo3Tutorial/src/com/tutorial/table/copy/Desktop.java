package com.tutorial.table.copy;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.ETableNavigation;
import com.minotauro.echo.table.base.TableColModel;
import com.minotauro.echo.table.base.TableColumn;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.renderer.BaseCellRenderer;
import com.minotauro.echo.table.renderer.LabelCellRenderer;
import com.minotauro.echo.table.renderer.NestedCellRenderer;
import com.tutorial.table.GUIStyles;
import com.tutorial.table.PersonBean;
import com.tutorial.table.TestTableModel;

import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

public class Desktop extends ContentPane {

  private TestTableModel tableDtaModel;

  private HtmlLayout htmlLayout;
  private int editingRow;

  private TextField txtFrst;
  private TextField txtLast;

  private Button btnCreate;
  private Button btnUpdate;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    add(initTemplate1());
  }

  // --------------------------------------------------------------------------------

  private Component initTemplate1() {
    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template1.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    HtmlLayoutData hld;

    hld = new HtmlLayoutData("head");
    Label lblHead = new Label("¡...head...!");
    lblHead.setLayoutData(hld);
    htmlLayout.add(lblHead);

    hld = new HtmlLayoutData("cent");
    Button btnClickToEnter = new Button("Click me to enter!!!");
    btnClickToEnter.setStyle(GUIStyles.DEFAULT_STYLE);
    btnClickToEnter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        btnClickToEnterClicked();
      }
    });
    btnClickToEnter.setLayoutData(hld);
    htmlLayout.add(btnClickToEnter);

    hld = new HtmlLayoutData("foot");
    Label lblFoot = new Label("¡...foot...!");
    lblFoot.setLayoutData(hld);
    htmlLayout.add(lblFoot);

    return htmlLayout;
  }

  // --------------------------------------------------------------------------------

  private void btnClickToEnterClicked() {
    removeAll();
    add(initTemplate2());
  }

  // --------------------------------------------------------------------------------

  private Component initTemplate2() {
    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template2.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    HtmlLayoutData hld;

    hld = new HtmlLayoutData("menu");
    Component menu = initMenu();
    menu.setLayoutData(hld);
    htmlLayout.add(menu);

    hld = new HtmlLayoutData("head");
    //    Label lblHead = new Label("¡...head...!");
    //    lblHead.setLayoutData(hld);
    Component component = initTopRow();
    component.setLayoutData(hld);
    htmlLayout.add(component);

    hld = new HtmlLayoutData("main");

    // ----------------------------------------
    // The table
    // ----------------------------------------

    TableColModel tableColModel = initTableColModel();
    TableSelModel tableSelModel = new TableSelModel();

    tableDtaModel = new TestTableModel();
    tableDtaModel.setEditable(true);
    tableDtaModel.setPageSize(3);

    ETable table = new ETable();
    table.setTableDtaModel(tableDtaModel);
    table.setTableColModel(tableColModel);
    table.setTableSelModel(tableSelModel);

    table.setEasyview(true);

    table.setBorder(new Border(1, Color.BLACK, Border.STYLE_NONE));
    table.setInsets(new Insets(5, 2, 5, 2));
    //    col.add(table);

    //    Label lblMain = new Label("¡...main...!");
    table.setId("main"); // Para poder removerlo luego
    table.setLayoutData(hld);
    htmlLayout.add(table);

    hld = new HtmlLayoutData("foot");
    //    Label lblFoot = new Label("¡...foot...!");

    // ----------------------------------------
    // The navigation control
    // ----------------------------------------

    ETableNavigation tableNavigation = new ETableNavigation(tableDtaModel);
    tableNavigation.setLayoutData(hld);
    //col.add(tableNavigation);

    htmlLayout.add(tableNavigation);

    return htmlLayout;
  }

  private TableColModel initTableColModel() {
    TableColModel tableColModel = new TableColModel();

    TableColumn tableColumn;

    tableColumn = new TableColumn() {
      @Override
      public Object getValue(ETable table, Object element) {
        PersonBean personaBean = (PersonBean) element;
        return personaBean.getFrstName();
      }
    };
    tableColumn.setWidth(new Extent(150));
    tableColumn.setHeadValue("Frst Name");
    tableColumn.setHeadCellRenderer(new LabelCellRenderer());
    tableColumn.setDataCellRenderer(new LabelCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    tableColumn = new TableColumn() {
      @Override
      public Object getValue(ETable table, Object element) {
        PersonBean personaBean = (PersonBean) element;
        return personaBean.getLastName();
      }
    };
    tableColumn.setWidth(new Extent(150));
    tableColumn.setHeadValue("Last Name");
    tableColumn.setHeadCellRenderer(new LabelCellRenderer());
    tableColumn.setDataCellRenderer(new LabelCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    tableColumn = new TableColumn();
    tableColumn.setWidth(new Extent(50));
    tableColumn.setHeadValue("Actions");
    tableColumn.setHeadCellRenderer(new LabelCellRenderer());
    tableColumn.setDataCellRenderer(initNestedCellRenderer());
    tableColModel.getTableColumnList().add(tableColumn);

    return tableColModel;
  }

  private NestedCellRenderer initNestedCellRenderer() {
    NestedCellRenderer nestedCellRenderer = new NestedCellRenderer();
    nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
      @Override
      public Component getCellRenderer( //
          final ETable table, final Object value, final int col, final int row) {

        boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

        Button ret = new Button("Edt");
        ret.setStyle(GUIStyles.DEFAULT_STYLE);
        ret.setEnabled(editable);
        ret.setToolTipText("Edt");

        ret.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            btnEdtClicked(row);
          }
        });
        return ret;
      }
    });

    nestedCellRenderer.getCellRendererList().add(new BaseCellRenderer() {
      @Override
      public Component getCellRenderer( //
          final ETable table, final Object value, final int col, final int row) {

        boolean editable = ((TestTableModel) table.getTableDtaModel()).getEditable();

        Button ret = new Button("Del");
        ret.setStyle(GUIStyles.DEFAULT_STYLE);
        ret.setEnabled(editable);
        ret.setToolTipText("Del");
        ret.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            btnDelClicked(row);
          }
        });
        return ret;
      }
    });

    return nestedCellRenderer;
  }

  // --------------------------------------------------------------------------------

  private Component initMenu() {
    Column col = new Column();

    Button btnItem1 = new Button("Item 1");
    btnItem1.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem1Clicked();
      }
    });
    col.add(btnItem1);

    Button btnItem2 = new Button("Item 2");
    btnItem2.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem2Clicked();
      }
    });
    col.add(btnItem2);

    Button btnItem3 = new Button("Item 3");
    btnItem3.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem3Clicked();
      }
    });
    col.add(btnItem3);

    Button btnItem4 = new Button("Item 4");
    btnItem4.setStyle(GUIStyles.DEFAULT_STYLE);
    btnItem4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnItem4Clicked();
      }
    });
    col.add(btnItem4);

    Button btnExit = new Button("Exit");
    btnExit.setStyle(GUIStyles.DEFAULT_STYLE);
    btnExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        btnExitClicked();
      }
    });
    col.add(btnExit);

    return col;
  }

  // --------------------------------------------------------------------------------

  private void btnItem1Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem1 pnlMain = new PanelItem1();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem2Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem2 pnlMain = new PanelItem2();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem3Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem3 pnlMain = new PanelItem3();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnItem4Clicked() {
    HtmlLayoutData hld = new HtmlLayoutData("main");
    PanelItem4 pnlMain = new PanelItem4();
    pnlMain.setId("main");
    pnlMain.setLayoutData(hld);

    // Remueve componente anterior del htmlLayout 
    htmlLayout.remove(htmlLayout.getComponent("main"));
    htmlLayout.add(pnlMain);
  }

  // --------------------------------------------------------------------------------

  private void btnExitClicked() {
    removeAll();
    add(initTemplate1());
  }

  private Row initTopRow() {
    Row row = new Row();
    row.setCellSpacing(new Extent(5));

    row.add(new Label("Frst Name:"));
    txtFrst = new TextField();
    row.add(txtFrst);

    row.add(new Label("Last Name:"));
    txtLast = new TextField();
    row.add(txtLast);

    btnCreate = new Button("Create");
    btnCreate.setStyle(GUIStyles.DEFAULT_STYLE);
    btnCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCreateClicked();
      }
    });
    row.add(btnCreate);

    btnUpdate = new Button("Update");
    btnUpdate.setStyle(GUIStyles.DEFAULT_STYLE);
    btnUpdate.setEnabled(false);
    btnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnUpdateClicked();
      }
    });
    row.add(btnUpdate);

    return row;
  }

  // --------------------------------------------------------------------------------

  private void btnCreateClicked() {
    PersonBean personaBean = new PersonBean();

    personaBean.setFrstName(txtFrst.getText());
    personaBean.setLastName(txtLast.getText());

    tableDtaModel.add(personaBean);
  }

  // --------------------------------------------------------------------------------

  private void btnUpdateClicked() {
    PersonBean personaBean = (PersonBean) tableDtaModel.getElementAt(editingRow);
    personaBean.setFrstName(txtFrst.getText());
    personaBean.setLastName(txtLast.getText());

    tableDtaModel.setEditable(true);

    btnCreate.setEnabled(true);
    btnUpdate.setEnabled(false);

    tableDtaModel.currPageChanged();
  }

  private void btnEdtClicked(int row) {
    PersonBean personaBean = (PersonBean) tableDtaModel.getElementAt(row);

    txtFrst.setText(personaBean.getFrstName());
    txtLast.setText(personaBean.getLastName());

    tableDtaModel.setEditable(false);
    tableDtaModel.currPageChanged();

    btnCreate.setEnabled(false);
    btnUpdate.setEnabled(true);

    editingRow = row;
  }

  // --------------------------------------------------------------------------------

  private void btnDelClicked(int row) {
    tableDtaModel.del(tableDtaModel.getElementAt(row));
  }
}
