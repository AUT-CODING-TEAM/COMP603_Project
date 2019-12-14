/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.searchResultList;

import controller.WordDetailController;
import controller.main.SearchController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 */
public class SearchResultListPanel extends GroundPanelTemplate {

    private JFrame searchResultListFrame;
    private JList list_sP_searchResultList;
    private String inputKeyWord;
    private User user;
    private JTextField tf_sRLP_keyword;

    public SearchResultListPanel(String inputKeyWord, User user) {
        super(GroundPanelTemplate.BACK);
        this.inputKeyWord = inputKeyWord;
        this.user = user;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        searchResultListFrame = new JFrame("Look Up");
        setSize(searchResultListFrame, 720, 720);
        searchResultListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchResultListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                backToMain();
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        addSearchPanel();

        addSearchResultListPanel();

        searchResultListFrame.add(this);

        searchResultListFrame.setVisible(true);
    }

    private void addSearchPanel() {
        JPanel searchPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        searchPanel.setLayout(new GridLayout(3, 1));
        
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        JLabel lbl_sRLP_searchTip = new JLabel("Look Up");
        jPanel.add(lbl_sRLP_searchTip);

        tf_sRLP_keyword = new JTextField(20);
        this.tf_sRLP_keyword.setText(inputKeyWord);
        tf_sRLP_keyword.getDocument().addDocumentListener(new SearchController(this));
        jPanel.add(tf_sRLP_keyword);
        
        this.searchResultListFrame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                SearchResultListPanel.this.tf_sRLP_keyword.requestFocus();
            }
        });

        searchPanel.add(new JLabel());
        searchPanel.add(jPanel);
        searchPanel.add(new JLabel());

        add(searchPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.01));
    }

    public void addSearchResultListPanel() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        jPanel.setLayout(new GridLayout(1, 1));

        list_sP_searchResultList = new ListInScrollTemplate(new SearchResultInfo(inputKeyWord).getSearchResultListInfo());
        list_sP_searchResultList.setEnabled(true);
        list_sP_searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list_sP_searchResultList.addListSelectionListener(new WordDetailController(user, list_sP_searchResultList, searchResultListFrame));
        jPanel.add(list_sP_searchResultList);

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.89));
    }

    public JFrame getSearchResultListFrame() {
        return searchResultListFrame;
    }

    public JList getList_sP_searchResultList() {
        return list_sP_searchResultList;
    }

    public JTextField getTf_sRLP_keyword() {
        return tf_sRLP_keyword;
    }

    public void backToMain() {
        new MainView(user);
    }
}
