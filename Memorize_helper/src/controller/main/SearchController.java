/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import model.SearchResultInfo;
import view.search.*;

/**
 *
 * @author ThinkPad
 */
public class SearchController implements DocumentListener {

    private JFrame mainView;
    private JTextField tf_tP_keyword;
    private String inputKeyWord;
    //get existed searchPanel in view/main/TopPanel
    private JPanel mainViewSearchPanel;
    private SearchResultListPanel searchResultListPanel;

    public SearchController(JFrame mainView, JTextField tf_tP_keyword, JPanel mainViewSearchPanel) {
        this.mainView = mainView;
        this.tf_tP_keyword = tf_tP_keyword;
        this.mainViewSearchPanel = mainViewSearchPanel;
    }

    private void response() {
        try {
            Document doc = tf_tP_keyword.getDocument();
            this.inputKeyWord = doc.getText(0, doc.getLength());
        } catch (Exception e) {

        }

        if (searchResultListPanel == null) {
            System.out.println("word is not null");
            mainView.setVisible(false);
            searchResultListPanel = new SearchResultListPanel(mainView, new SearchResultInfo(inputKeyWord), mainViewSearchPanel);
            searchResultListPanel.getSearchResultListFrame().setVisible(true);
        } else {
            if ("".equals(inputKeyWord)) {
                searchResultListPanel.getSearchResultListFrame().dispose();
                mainView.setVisible(true);
            } else {
                searchResultListPanel.getList_sP_searchResultListPart1().setListData(new SearchResultInfo(inputKeyWord).getSearchResultListInfo(SearchResultInfo.WORD));
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        response();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        response();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
