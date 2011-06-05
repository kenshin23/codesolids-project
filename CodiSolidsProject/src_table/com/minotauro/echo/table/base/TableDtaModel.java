/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.event.EventListenerList;

import com.minotauro.echo.table.event.TableDtaModelEvent;
import com.minotauro.echo.table.event.TableDtaModelEvtProxy;

/**
 * @author Demián Gutierrez
 */
public abstract class TableDtaModel {

  protected TableDtaModelEvtProxy tableDtaModelEvtProxy = //
  new TableDtaModelEvtProxy(new EventListenerList());

  protected ETable table;

  // --------------------------------------------------------------------------------

  public TableDtaModel() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public TableDtaModelEvtProxy getTableDtaModelEvtProxy() {
    return tableDtaModelEvtProxy;
  }

  // --------------------------------------------------------------------------------

  public ETable getTable() {
    return table;
  }

  public void setTable(ETable table) {
    if (table.getTableDtaModel() != this) {
      throw new IllegalStateException();
    }

    this.table = table;
  }

  // --------------------------------------------------------------------------------

  public Object/**/getValue(int col, int row) {
    TableColModel tableColModel = table.getTableColModel();

    TableColumn tableColumn = //
    tableColModel.getTableColumnList().get(col);

    return tableColumn.getValue(table, getElementAt(row));
  }

  public void/*  */setValue(int col, int row, //
      Object value) {

    TableColModel tableColModel = table.getTableColModel();

    TableColumn tableColumn = //
    tableColModel.getTableColumnList().get(col);

    tableColumn.setValue(table, getElementAt(row), value);

    if (tableColumn.isEventOnSetValue()) {
      tableDtaModelEvtProxy.fireActionEvent( //
          new TableDtaModelEvent(this));
    }
  }

  // --------------------------------------------------------------------------------

  public int getColCount() {
    return table.getTableColModel(). //
        getTableColumnList().size();
  }

  // --------------------------------------------------------------------------------

  public abstract int getRowCount();

  // --------------------------------------------------------------------------------

  public abstract Object getElementAt(int row);

  // --------------------------------------------------------------------------------

  public abstract boolean getEditable();

  public abstract void setEditable(boolean editable);

}
