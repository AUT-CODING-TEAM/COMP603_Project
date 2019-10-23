/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.search;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class SearchResultListPanel extends GroundPanelTemplate {

    private JFrame mainView;
    private SearchResultInfo searchResultInfo;
    //get existed searchPanel in view/main/TopPanel
    private JPanel mainViewSearchPanel;
    private JFrame searchResultListFrame;
    private JList list_sP_searchResultListPart1;

    public SearchResultListPanel(JFrame mainView, SearchResultInfo searchResultInfo, JPanel mainViewSearchPanel) {
        super(GroundPanelTemplate.BACK);
        this.mainView = mainView;
        this.searchResultInfo = searchResultInfo;
        this.mainViewSearchPanel = mainViewSearchPanel;
        setProperty();
        addComponents();
    }

    private void setSize(JFrame jFrame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = 1280;
        int frameWidth = 720;
        int frameHeight = 720;
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        searchResultListFrame = new JFrame("搜索");
        setSize(searchResultListFrame);
        searchResultListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchResultListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                mainView.setVisible(true);
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
        
        add(mainViewSearchPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
        
        addSearchResultListPanel();
        
        searchResultListFrame.add(this);
        
        //visibility should be controlled
//        searchResultListFrame.setVisible(true);
    }
    
    public void addSearchResultListPanel(){
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        jPanel.setLayout(new GridLayout(1, 1));
        
        list_sP_searchResultListPart1 = new ListInScrollTemplate(searchResultInfo.getSearchResultListInfo(SearchResultInfo.WORD));
        jPanel.add(list_sP_searchResultListPart1);
        
//        JList list_sP_searchResultListPart2 = new ListInScrollTemplate(searchResultInfo.getSearchResultListInfo(SearchResultInfo.PHONETIC_SYMBOL));
//        jPanel.add(list_sP_searchResultListPart2);
//        
//        JList list_sP_searchResultListPart3 = new ListInScrollTemplate(searchResultInfo.getSearchResultListInfo(SearchResultInfo.PARAPHRASE));
//        jPanel.add(list_sP_searchResultListPart3);

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.8));
    }

    public JFrame getSearchResultListFrame() {
        return searchResultListFrame;
    }

    public JList getList_sP_searchResultListPart1() {
        return list_sP_searchResultListPart1;
    }
}
