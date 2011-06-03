/*
 * Created on 19/09/2008
 */
package com.minotauro.echo.table.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nextapp.echo.app.event.EventListenerList;

import com.minotauro.echo.table.event.TableSelModelEvent;
import com.minotauro.echo.table.event.TableSelModelEvtProxy;
import com.minotauro.echo.table.event.TableSelModelListener.TableSelModelListenerMethod;

/**
 * @author Demián Gutierrez
 */
public class TableSelModel {

  protected TableSelModelEvtProxy tableSelModelEvtProxy = //
  new TableSelModelEvtProxy(new EventListenerList());

  protected Set<Object> selectedSet = //
  new HashSet<Object>();

  protected boolean multiple = true;

  protected ETable table;

  // --------------------------------------------------------------------------------

  public TableSelModel() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public TableSelModelEvtProxy getTableSelModelEvtProxy() {
    return tableSelModelEvtProxy;
  }

  // --------------------------------------------------------------------------------

  public boolean isMultiple() {
    return multiple;
  }

  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

  // --------------------------------------------------------------------------------

  public ETable getTable() {
    return table;
  }

  public void setTable(ETable cledaTable) {
    if (cledaTable.getTableSelModel() != this) {
      throw new IllegalStateException();
    }

    this.table = cledaTable;
  }

  // --------------------------------------------------------------------------------

  public boolean getSelected(Object element) {
    return selectedSet.contains(element);
  }

  // --------------------------------------------------------------------------------

  public void/**/setSelected(Object element, //
      boolean selected) {

    if (!multiple && selected) {
      setSelected( //
          new HashSet<Object>(selectedSet), false);
    }

    if (element instanceof Collection<?>) {
      Collection<?> elements = (Collection<?>) element;

      for (Object obj : elements) {
        if (selected) {
          selectedSet.add/*   */(obj);
        } else {
          selectedSet.remove/**/(obj);
        }
      }
    } else {
      if (selected) {
        selectedSet.add/*   */(element);
      } else {
        selectedSet.remove/**/(element);
      }
    }

    tableSelModelEvtProxy.fireActionEvent( //
        new TableSelModelEvent(this, selected, element), //
        TableSelModelListenerMethod.TABLE_SEL);
  }

  // --------------------------------------------------------------------------------

  public Set<Object> getSelectedSet() {
    return selectedSet;
  }

  public void/*    */setSelectedSet( //
      Set<Object> selectedSet) {
    this.selectedSet = selectedSet;

    tableSelModelEvtProxy.fireActionEvent( //
        new TableSelModelEvent(this, false, null), //
        TableSelModelListenerMethod.TABLE_SET);
  }
}
