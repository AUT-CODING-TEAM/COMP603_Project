/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.search;

import java.awt.*;
import javax.swing.*;
import model.SearchResult;
import view.GroundPanelTemplate;

/**
 *
 * @author ThinkPad
 */
public class SearchPanel extends GroundPanelTemplate {

    private SearchResult searchResult;

    public SearchPanel(SearchResult searchResult) {
        super(GroundPanelTemplate.BACK);
        this.searchResult = searchResult;
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
    
    public void addComponents(){
        
    }
}
