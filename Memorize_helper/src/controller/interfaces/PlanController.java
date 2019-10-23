/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.StudyPlan;
import model.User;

/**
 *
 * @author Yun_c
 */
public class PlanController {

    public StudyPlan getPlan(int pid) {
        Database db = Database.getInstance();
        StudyPlan plan = null;
        if (pid == 0) {
            return plan;
        }
        ResultSet res = db.get("PLAN", "ID", pid);
        try {
            if (!res.next()) {
                return plan;
            }
            int id = res.getInt("ID");
            String book = res.getString("BOOK");
            int today_num = res.getInt("TODAY_TARGET_NUMBER");
            int total_day = res.getInt("TOTAL_DAY");
            long start = res.getLong("START_TIME");
            int total_num = db.count(book, "", "");
            plan = new StudyPlan(book, id, total_num, total_day, start, today_num);

        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plan;
    }

    public int addPlan(String book, int everyday_num, int user_id) {
        Database db = Database.getInstance();
        int pid = 0;
        String[] col = {"user_id", "book"};
        String[] val = {String.valueOf(user_id), book};
        ResultSet p = db.get("PLAN", col, val);
        try {
            if (p.next()) {
                return pid;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int total_word_num = db.count(book, "", "");
        int total_day = total_word_num / everyday_num;
        if (total_word_num % everyday_num != 0) {
            total_day += 1;
        }
        long start_time = System.currentTimeMillis();
        StringBuilder bd = new StringBuilder("insert into \"PLAN\" (\"USER_ID");
        bd.append("\",\"BOOK\",\"TOTAL_DAY\",\"START_TIME\",\"TODAY_TARGET_NUMBER\"");
        bd.append(") values (\'").append(user_id).append("\',");
        bd.append("\'").append(book.toUpperCase()).append("\',");
        bd.append(" ").append(total_day).append(",");
        bd.append(" ").append(start_time).append(",");
        bd.append(" ").append(everyday_num).append(")");

        boolean res = db.SQL(bd.toString());
        if (res) {
            p = db.get("PLAN", col, val);
            try {
                if (p.next()) {
                    pid = p.getInt("ID");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pid;
    }
}
