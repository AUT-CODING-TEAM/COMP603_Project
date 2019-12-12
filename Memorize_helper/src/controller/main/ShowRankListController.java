/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import model.RankListInfo;
import model.User;
import view.rankList.RankListPanel;

/**
 *
 * @author ThinkPad
 */
public class ShowRankListController extends MainViewControllerTemplate{

    public ShowRankListController(JFrame mainView, User user) {
        super(mainView, user);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        new RankListPanel(mainView, new RankListInfo());
        mainView.setVisible(false);
    }
    
}
