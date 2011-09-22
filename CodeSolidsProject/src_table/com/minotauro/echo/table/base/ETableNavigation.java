/*
 * Created on 09/05/2007
 */
package com.minotauro.echo.table.base;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.apache.commons.lang.StringUtils;

import codesolids.util.ImageReferenceCache;

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

    btnBeg = new Button();
    //btnBeg.setStyle(ButtonEx.DEFAULT_STYLE);
    btnBeg.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/menormenor.png")));
    btnBeg.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/menormenorMouseOver.png")));
    btnBeg.setRolloverEnabled(true);
    btnBeg.setWidth(new Extent(25));
    btnBeg.setHeight(new Extent(25));
    btnBeg.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnBegClicked();
      }
    });
    add(btnBeg);

    btnPre = new Button();
    //btnPre.setStyle(ButtonEx.DEFAULT_STYLE);
    btnPre.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/menor.png")));
    btnPre.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/menorMouseOver.png")));
    btnPre.setRolloverEnabled(true);
    btnPre.setWidth(new Extent(25));
    btnPre.setHeight(new Extent(25));
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

    btnNxt = new Button();
    //btnNxt.setStyle(ButtonEx.DEFAULT_STYLE);
    btnNxt.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/mayor.png")));
    btnNxt.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/mayorMouseOver.png")));
    btnNxt.setRolloverEnabled(true);
    btnNxt.setWidth(new Extent(25));
    btnNxt.setHeight(new Extent(25));
    btnNxt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnNxtClicked();
      }
    });
    add(btnNxt);

    btnLst = new Button();
    //btnLst.setStyle(ButtonEx.DEFAULT_STYLE);
    btnLst.setBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/mayormayor.png")));
    btnLst.setRolloverBackgroundImage(new FillImage(ImageReferenceCache.getInstance().getImageReference("Images/Botones/mayormayorMouseOver.png")));
    btnLst.setRolloverEnabled(true);
    btnLst.setWidth(new Extent(25));
    btnLst.setHeight(new Extent(25));
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
    txtPage.setBorder(new Border(3, new Color(87, 205, 211), Border.STYLE_SOLID));
  }
}
