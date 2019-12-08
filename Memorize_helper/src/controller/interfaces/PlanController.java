/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * @param pid the plan's id
     * @return an instance of StudyPlan whose ID is param.pid
     */
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
            int isFinish = res.getInt("FINISH");
            int total_num = db.count(book, "", "");
            plan = new StudyPlan(book, id, total_num, total_day, start, today_num, isFinish);

        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plan;
    }

    public StudyPlan getPlan(User user, String book) {
        Database db = Database.getInstance();
        String[] key = {"USER_ID", "BOOK"};
        String[] val = {String.valueOf(user.getID()), book};
        ResultSet res = db.get("PLAN", key, val);
        StudyPlan plan = null;
        try {
            if (res.next()) {
                int id = res.getInt("ID");
                int today_num = res.getInt("TODAY_TARGET_NUMBER");
                int total_day = res.getInt("TOTAL_DAY");
                long start = res.getLong("START_TIME");
                int isFinish = res.getInt("FINISH");
                int total_num = db.count(book, "", "");
                plan = new StudyPlan(book, id, total_num, total_day, start, today_num, isFinish);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plan;
    }

    public int getPlanIdByBook(User user, String book) {
        Database db = Database.getInstance();
        ResultSet res = db.get("PLAN", "USER_ID", user.getID());
        int pid = 0;
        try {
            while (res.next()) {
                if (res.getString("BOOK").equals(book)) {
                    pid = res.getInt("ID");
                    break;
                };
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pid;
    }

    /**
     * @param user this user's plan info will be got
     * @return an instance of StudyPlan
     *
     * this function equals to User.getStudyPlan()
     *
     */
    public StudyPlan getPlan(User user) {
        return user.getCurrentStudyPlan();
    }

    /**
     * @param user the user
     * @return all book which is user's plan
     */
    public ArrayList<StudyPlan> getAllPlanByUser(User user) {
        Database db = Database.getInstance();
        ResultSet res = db.get("PLAN", "USER_ID", user.getID());
        ArrayList<StudyPlan> s = new ArrayList<StudyPlan>();
        try {
            while (res.next()) {
                int id = res.getInt("ID");
                String book = res.getString("BOOK");
                int today_num = res.getInt("TODAY_TARGET_NUMBER");
                int total_day = res.getInt("TOTAL_DAY");
                long start = res.getLong("START_TIME");
                int isFinish = res.getInt("FINISH");
                int total_num = db.count(book, "", "");
                StudyPlan plan = new StudyPlan(book, id, total_num, total_day, start, today_num, isFinish);
                s.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    /**
     * @param user the user
     * @param book the book name
     * @return if the book is one of the user's plan
     */
    public boolean isPlan(User user, String book) {
        Database db = Database.getInstance();
        boolean res = false;
        ResultSet p = db.get("PLAN", "USER_ID", user.getID());
        try {
            while (p.next()) {
                if (p.getString("BOOK").equals(book)) {
                    res = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * @param book the name of book that user want to choose
     * @param everyday_num how many number of word user want to learn everyday
     * @param user_id user's id
     * @return new plan's ID. if no plan created, the return value is 0
     */
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
        bd.append(",\"FINISH\"");
        bd.append(") values (\'").append(user_id).append("\',");
        bd.append("\'").append(book.toUpperCase()).append("\',");
        bd.append(" ").append(total_day).append(",");
        bd.append(" ").append(start_time).append(",");
        bd.append(" ").append(everyday_num).append(",");
        bd.append(" \'0\')");

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

    /**
     * @param book the name of book that user want to choose
     * @param total_day how many days user want to spend learning all words
     * @param user_id user's id
     * @return new plan's ID. if no plan created, the return value is 0
     */
    public int addPlan(int total_day, String book, int user_id) {
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
        int everyday_num = total_word_num / total_day;
        long start_time = System.currentTimeMillis();

        StringBuilder bd = new StringBuilder("insert into \"PLAN\" (\"USER_ID");
        bd.append("\",\"BOOK\",\"TOTAL_DAY\",\"START_TIME\",\"TODAY_TARGET_NUMBER\"");
        bd.append(",\"FINISH\"");
        bd.append(") values (\'").append(user_id).append("\',");
        bd.append("\'").append(book.toUpperCase()).append("\',");
        bd.append(" ").append(total_day).append(",");
        bd.append(" ").append(start_time).append(",");
        bd.append(" ").append(everyday_num).append(",");
        bd.append(" \'0\')");

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
    
    public void setPlan(User user, int pid){
         Database db = Database.getInstance();
         db.set("USERS", "ID", user.getID(), "STUDY_PLAN", String.valueOf(pid));
    }
    /**
     * @param plan get this plan's total word number
     * @return total word number
     */
    public int getTotalWordNum(StudyPlan plan) {
        if (plan == null) {
            return 0;
        }
        Database db = Database.getInstance();
        int num = db.count(plan.getStudyPlanName(), "", "");
        return num;
    }

    /**
     * @param id an user's id
     * @param plan get word number according to this plan
     * @return the number of words that param.id memorized in param.plan. If
     * he/she is never set a plan, return value is 0
     */
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

    /**
     * @param user an instance of user
     * @return the number of words that param.user memorized in his plan. If
     * he/she is never set a plan, return value is 0
     */
    public int getFinishWordNum(User user) {
        int num = 0;
        StudyPlan plan = user.getCurrentStudyPlan();
        if (plan == null) {
            return num;
        }
        Database db = Database.getInstance();
        String[] key = {"user_id", "word_source"};
        String[] value = {String.valueOf(user.getID()), plan.getStudyPlanName()};
        num = db.count("memorize", key, value);
        return num;
    }

    /**
     * @param id user's id
     * @param plan get word number according to this plan
     * @return the number of word that param.id not memorized yet. If user is
     * never set a plan, return value is 0
     */
    public int getRemainWordNum(int id, StudyPlan plan) {
        int num = 0;
        if (plan == null) {
            return num;
        }
        int finish = this.getFinishWordNum(id, plan);
        num = this.getTotalWordNum(plan);
        return num - finish;
    }

    /**
     * @param plan an instance of StudyPlan
     * @return the plan name.If plan is not exist,return value will tell "no
     * activated plan"
     */
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
     * of class StudyPlan.database update is already done at correct/wrong
     * function in other controller)
     *
     */
    public void updateTodayPlanInfo(User user) {
        if (user != null) {
            StudyPlan p = user.getCurrentStudyPlan();
            if (p != null) {
                p.setTodayMemorized(this.getTodayMemorizedNum(user));
                p.setTodayReviewd(this.getTodayReviewedNum(user));
                MemorizeController mct = new MemorizeController();
                if (mct.countMemorizedWord(user) == p.getTotalNumber()) {
                    Database db = Database.getInstance();
                    db.set("PLAN", "ID", p.getID(), "FINISH", "1");
                    p.setFinished(1);
                }
            }

        }
    }

    /**
     * @param user get this user's today memorized number
     * @return memorized word number today
     */
    public int getTodayMemorizedNum(User user) {
        Database db = Database.getInstance();
        StudyPlan plan = user.getCurrentStudyPlan();
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
        StudyPlan plan = user.getCurrentStudyPlan();
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
