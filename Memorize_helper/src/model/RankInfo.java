/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class RankInfo {

    public static final int NUMBERS = 0;
    public static final int NAMES = 1;
    public static final int WORDS = 2;

    private int listNumber;
    private ArrayList<User> users;

    public RankInfo() {// develop use only
        listNumber = 50;
        users = new ArrayList<>();

        users.add(new User().setUsername("admin").setFinishedNumberInTotal(-999));
        for (int i = 1; i <= listNumber; i++) {
            users.add(new User().setUsername("testUser" + i).setFinishedNumberInTotal(100 - i));
        }
    }

    public String[] getRankInfo(int col) {
        String[] s = new String[listNumber + 1];
        if (col == 0) {
            s[0] = String.format("%18s", "排名");
        } else if (col == 1) {
            s[0] = String.format("%18s", "用户名");
        } else if (col == 2) {
            s[0] = String.format("%18s", "总词数");
        }

        for (int i = 1; i < s.length; i++) {
            if (col == 0) {
                s[i] = String.format("%19d", i);
            } else if (col == 1) {
                s[i] = String.format("%22s", users.get(i).getUsername());
            } else if (col == 2) {
                s[i] = String.format("%20d", users.get(i).getFinishedNumberInTotal());
            }
        }

        return s;
    }
}
