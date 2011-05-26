/*
 * Created on 22/09/2008
 */
package com.minotauro.echo.table.renderer;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;

import com.minotauro.echo.table.base.CellRenderer;
import com.minotauro.echo.table.base.ETable;
import com.minotauro.echo.table.base.TableDtaModel;
import com.minotauro.echo.table.base.TableSelModel;
import com.minotauro.echo.table.event.TableSelModelEvent;
import com.minotauro.echo.table.event.TableSelModelListener;

/**
 * @author Demián Gutierrez
 */
public class CheckBoxHeadCellRenderer implements CellRenderer {

  protected GridLayoutData gridLayoutData = new GridLayoutData();

  protected boolean editable;

  // --------------------------------------------------------------------------------

  public CheckBoxHeadCellRenderer() {
    gridLayoutData.setInsets(new Insets(5, 5, 5, 5));
    gridLayoutData.setBackground(Color.PINK);
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer( //
      final ETable table, final Object value, final int col, final int row) {

    final CheckBox chkAll = new CheckBox();
    chkAll.setEnabled(editable);

    TableSelModel tableSelModel = //
    table.getTableSelModel();

    tableSelModel.getTableSelModelEvtProxy().addTableSelModelListener( //
        new TableSelModelListener() {
          public void tableSelChanged(TableSelModelEvent evt) {
            tableSelModelChanged(table, chkAll);
          }

          public void tableSetChanged(TableSelModelEvent evt) {
            // Empty
          }
        });

    tableSelModelChanged(table, chkAll);

    chkAll.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        chkAllClicked(table, evt);
      }
    });

    return chkAll;
  }

  // --------------------------------------------------------------------------------

  protected void chkAllClicked(ETable table, ActionEvent evt) {
    CheckBox src = (CheckBox) evt.getSource();

    TableDtaModel tableDtaModel = //
    table.getTableDtaModel();

    TableSelModel tableSelModel = //
    table.getTableSelModel();

    List<Object> elementList = new ArrayList<Object>();

    for (int i = 0; i < tableDtaModel.getRowCount(); i++) {
      Object element = tableDtaModel.getElementAt(i);

      if (tableSelModel.getSelected(element) != src.isSelected()) {
        elementList.add(element);
      }
    }

    tableSelModel.setSelected( //
        elementList, src.isSelected());
  }

  // --------------------------------------------------------------------------------

  protected void tableSelModelChanged(ETable table, CheckBox checkBox) {
    TableDtaModel tableDtaModel = //
    table.getTableDtaModel();

    TableSelModel tableSelModel = //
    table.getTableSelModel();

    boolean selected = true;

    for (int i = 0; i < tableDtaModel.getRowCount(); i++) {
      selected &= tableSelModel.getSelected( //
          tableDtaModel.getElementAt(i));
    }

    checkBox.setSelected(selected);
  }

  // --------------------------------------------------------------------------------

  @Override
  public GridLayoutData getGridLayoutData() {
    return gridLayoutData;
  }

  // --------------------------------------------------------------------------------

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

}
