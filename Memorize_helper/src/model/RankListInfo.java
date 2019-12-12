/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.UserController;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class RankListInfo {

    public static final int NUMBERS = 0;
    public static final int NAMES = 1;
    public static final int WORDS = 2;

    private ArrayList<User> users;
    
    public RankListInfo(){
        users = new UserController().getMemorizeRank();
    }

    public String[] getRankListInfo(int col) {
        String[] s = new String[users.size() + 1];
        if (col == 0) {
            s[0] = String.format("%18s", "Rank");
        } else if (col == 1) {
            s[0] = String.format("%18s", "Username");
        } else if (col == 2) {
            s[0] = String.format("%18s", "Learned Words");
        }

        for (int i = 1; i < s.length; i++) {
            if (col == 0) {
                s[i] = String.format("%19d", i);
            } else if (col == 1) {
                s[i] = String.format("%22s", users.get(i - 1).getUsername());
            } else if (col == 2) {
                s[i] = String.format("%20d", users.get(i - 1).getFinishedNumberInTotal());
            }
        }

        return s;
    }
}
