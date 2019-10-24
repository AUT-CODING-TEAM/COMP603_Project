/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;
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

    public StudyPlan getPlan(User user) {
        return user.getStudyPlan();
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

    public int getTotalWordNum(StudyPlan plan) {
        if (plan == null) {
            return 0;
        }
        Database db = Database.getInstance();
        int num = db.count(plan.getStudyPlanName(), "", "");
        return num;
    }

    public int getFinishWordNum(int id, StudyPlan plan) {
        int num = 0;
        if (plan == null) {
            return num;
        }
        Database db = Database.getInstance();
        String[] key = {"user_id", "word_source"};
        String[] value = {String.valueOf(id), plan.getStudyPlanName()};
        num = db.count("memorize", key, value);
        return num;
    }

    public int getFinishWordNum(User user) {
        int num = 0;
        StudyPlan plan = user.getStudyPlan();
        if (plan == null) {
            return num;
        }
        Database db = Database.getInstance();
        String[] key = {"user_id", "word_source"};
        String[] value = {String.valueOf(user.getID()), plan.getStudyPlanName()};
        num = db.count("memorize", key, value);
        return num;
    }

    public int getRemainWordNum(int id, StudyPlan plan) {
        int num = 0;
        if (plan == null) {
            return num;
        }
        int finish = this.getFinishWordNum(id, plan);
        num = this.getTotalWordNum(plan);
        return num - finish;
    }

    public String getPlanName(StudyPlan plan) {
        String name = "No Activated Plan";
        if (plan == null) {
            return name;
        }
        name = plan.getStudyPlanName();
        return name;
    }

    /**
     * @param user which user's plan info(such as memorize number or review
     * number)should be update(not update in database but update at the instance
     * of class StudyPlan.database update is already done at memorize function
     * and review function)
     *
     */
    public void updateTodayPlanInfo(User user) {
        if (user != null) {
            StudyPlan p = user.getStudyPlan();
            if (p != null) {
                p.setTodayMemorized(this.getTodayMemorizedNum(user));
                p.setTodayReviewd(this.getTodayReviewedNum(user));
            }

        }
    }

    /**
     * @param user get this user's today memorized number
     * @return memorized word number today
     */
    public int getTodayMemorizedNum(User user) {
        Database db = Database.getInstance();
        StudyPlan plan = user.getStudyPlan();
        if (plan == null) {
            return 0;
        }

        String table = plan.getStudyPlanName();
        Long time = System.currentTimeMillis();
        long day_start = time / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();
        long day_end = day_start + 1000 * (24 * 60 * 60 - 1);
        //get today memorized word
        int mem_num = 0;
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from ");
        bd.append("\"MEMORIZE\" where \"WORD_SOURCE\" = \'").append(table.toUpperCase());
        bd.append("\' and CAST(\"LAST_MEM_TIME\" as bigint) >= ").append(day_start);
        bd.append(" and CAST(\"LAST_MEM_TIME\" as bigint) <= ").append(day_end);
        bd.append(" and \"AGING\" = 0");
        ResultSet res = db.SQLqr(bd.toString());
        try {
            if (res.next()) {
                mem_num = res.getInt("NUMBER");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mem_num;
    }

    /**
     * @param user get this user's today reviewed number
     * @return reviewed word number today
     */
    public int getTodayReviewedNum(User user) {
        Database db = Database.getInstance();
        StudyPlan plan = user.getStudyPlan();
        if (plan == null) {
            return 0;
        }

        String table = plan.getStudyPlanName();
        Long time = System.currentTimeMillis();
        long day_start = time / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();
        long day_end = day_start + 1000 * (24 * 60 * 60 - 1);
        //get today memorized word
        int review_num = 0;
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from ");
        bd.append("\"MEMORIZE\" where \"WORD_SOURCE\" = \'").append(table.toUpperCase());
        bd.append("\' and CAST(\"LAST_MEM_TIME\" as bigint) >= ").append(day_start);
        bd.append(" and CAST(\"LAST_MEM_TIME\" as bigint) <= ").append(day_end);
        bd.append(" and \"AGING\" > 0");
        ResultSet res = db.SQLqr(bd.toString());
        try {
            if (res.next()) {
                review_num = res.getInt("NUMBER");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return review_num;
    }
}
