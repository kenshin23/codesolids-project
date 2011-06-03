/*
 * Created on 23/09/2008
 */
package com.minotauro.echo.table.base;

import com.minotauro.echo.table.event.PageableModelEvtProxy;

/**
 * @author Demián Gutierrez
 */
public interface PageableModel {

  public PageableModelEvtProxy getPageableModelEvtProxy();

  // --------------------------------------------------------------------------------

  public int getRealFromPagedRow(int row);

  public int getPagedFromRealRow(int row);

  // --------------------------------------------------------------------------------

  public int/* */getPageSize();

  public void/**/setPageSize( //
      int pageSize);

  // --------------------------------------------------------------------------------

  public int/* */getCurrPage();

  public void/**/setCurrPage( //
      int currPage);

  // --------------------------------------------------------------------------------

  public int getTotalPages();

  // --------------------------------------------------------------------------------

  public void currPageChanged();
}
