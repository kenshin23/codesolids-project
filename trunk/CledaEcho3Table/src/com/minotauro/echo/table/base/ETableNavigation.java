/*
 * Created on 09/05/2007
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.lang.StringUtils;

import com.minotauro.echo.table.event.PageableModelEvent;
import com.minotauro.echo.table.event.PageableModelListener;

/**
 * @author Demián Gutierrez
 */
public class ETableNavigation extends Row {

  protected PageableModel pageableModel;

  protected Button btnBeg;
  protected Button btnPre;
  protected Button btnNxt;
  protected Button btnLst;

  protected TextField txtPage;

  // --------------------------------------------------------------------------------

  public ETableNavigation(PageableModel pageableModel) {
    this.pageableModel = pageableModel;

    pageableModel.getPageableModelEvtProxy().addPageableModelListener( //
        new PageableModelListener() {
          public void pageChanged(PageableModelEvent evt) {
            updateState();
          }
        });

    initGUI();

    updateState();
  }

  // --------------------------------------------------------------------------------

  protected void initGUI() {
    setCellSpacing(new Extent(5));

    btnBeg = new Button("<<");
    //btnBeg.setStyle(ButtonEx.DEFAULT_STYLE);
    btnBeg.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnBegClicked();
      }
    });
    add(btnBeg);

    btnPre = new Button("<");
    //btnPre.setStyle(ButtonEx.DEFAULT_STYLE);
    btnPre.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnPreClicked();
      }
    });
    add(btnPre);

    txtPage = new TextField();
    txtPage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        txtPageChanged();
      }
    });
    txtPage.setAlignment(new Alignment( //
        Alignment.CENTER, Alignment.DEFAULT));
    txtPage.setWidth(new Extent(100));
    add(txtPage);

    btnNxt = new Button(">");
    //btnNxt.setStyle(ButtonEx.DEFAULT_STYLE);
    btnNxt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnNxtClicked();
      }
    });
    add(btnNxt);

    btnLst = new Button(">>");
    //btnLst.setStyle(ButtonEx.DEFAULT_STYLE);
    btnLst.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnLstClicked();
      }
    });
    add(btnLst);
  }

  // --------------------------------------------------------------------------------

  protected void btnBegClicked() {
    pageableModel.setCurrPage(0);
    pageableModel.currPageChanged();
    updateState();
  }

  protected void btnPreClicked() {
    pageableModel.setCurrPage(pageableModel.getCurrPage() - 1);
    pageableModel.currPageChanged();
    updateState();
  }

  protected void btnNxtClicked() {
    pageableModel.setCurrPage(pageableModel.getCurrPage() + 1);
    pageableModel.currPageChanged();
    updateState();
  }

  protected void btnLstClicked() {
    pageableModel.setCurrPage(pageableModel.getTotalPages() - 1);
    pageableModel.currPageChanged();
    updateState();
  }

  // --------------------------------------------------------------------------------

  protected void txtPageChanged() {
    String text = txtPage.getText();

    if (StringUtils.isBlank(text)) {
      updateState();
      return;
    }

    int slashIndex = text.indexOf('/');

    if (slashIndex >= 0) {
      text = text.substring(0, slashIndex);
    }

    int page;

    try {
      page = Integer.parseInt(text.trim()) - 1;
    } catch (NumberFormatException e) {
      updateState();
      return;
    }

    if (page < 0 || page > pageableModel.getTotalPages()) {
      updateState();
      return;
    }

    pageableModel.setCurrPage(page);
    pageableModel.currPageChanged();
    updateState();
  }

  // --------------------------------------------------------------------------------

  protected void updateState() {
    int page = pageableModel.getCurrPage() + 1;

    boolean beg = page == 1;
    boolean end = page == pageableModel.getTotalPages();

    btnBeg.setEnabled(!beg);
    btnPre.setEnabled(!beg);
    btnNxt.setEnabled(!end);
    btnLst.setEnabled(!end);

    txtPage.setText(page + "/" + pageableModel.getTotalPages());
  }
}
