/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.awt.event.ActionEvent;
import javax.swing.*;
import model.*;
import view.rankList.*;

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
        System.out.println("查看排行榜JFrame");
        new RankListPanel(mainView, new RankListInfo());
        mainView.setVisible(false);
    }
    
}
