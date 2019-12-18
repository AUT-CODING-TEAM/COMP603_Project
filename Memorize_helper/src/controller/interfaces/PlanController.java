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
 * @author Yun_c, Pingchuan
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

    public void setPlan(User user, int pid) {
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
     * @return the number of words that is put into memorize table. If he/she is
     * never set a plan, return value is 0
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
     * @return the number of word that not put into memorize tabel yet. If user
     * is never set a plan, return value is 0
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
                MemorizeController mct = new MemorizeController();
                long day_start
                        = (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset())
                        / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                        - TimeZone.getDefault().getRawOffset();
                
                long plan_start_time
                        = (p.getStartTime() + +TimeZone.getDefault().getRawOffset())
                        / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                        - TimeZone.getDefault().getRawOffset();
                
                long delta = (day_start - plan_start_time) / (1000 * 3600 * 24);

                int remain_day = p.getTotalDay() - (int) delta;

                p.setTodayMemorized(this.getTodayMemorizedNum(user));
                p.setTodayReviewd(this.getTodayReviewedNum(user));
                p.setTotalMemorizedNumber(mct.countMemorizedWordInPlan(user));
                p.setNeedReviewNumber(this.getNeedReviewNum(user));
                p.setReaminDay(remain_day);

                if (p.getTotalMemorizedNumber() == p.getTotalNumber()) {
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
        long day_start = (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset())
                / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();
        long day_end = day_start + 1000 * 24 * 60 * 60 - 1;
        //get today memorized word
        int mem_num = 0;
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from ");
        bd.append("\"MEMORIZE\" where \"WORD_SOURCE\" = \'").append(table.toUpperCase());
        bd.append("\' and CAST(\"LAST_MEM_TIME\" as bigint) >= ").append(day_start);
        bd.append(" and CAST(\"LAST_MEM_TIME\" as bigint) <= ").append(day_end);
        bd.append(" and \"AGING\" = 1 and \"USER_ID\" = \'").append(user.getID());
        bd.append("\'");
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
        long day_start
                = (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset())
                / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();
        long day_end = day_start + 1000 * 24 * 60 * 60 - 1;
        //get today reviewed word
        int review_num = 0;

        // LAST_MEM_TIME between day_start and day_end, that means newest operation 
        // on the word is today. AGING >= 2, that means the words who is 
        // reviewed. Above all, this can find out today's reviewed number
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from ");
        bd.append("\"MEMORIZE\" where \"WORD_SOURCE\" = \'").append(table.toUpperCase());
        bd.append("\' and CAST(\"LAST_MEM_TIME\" as bigint) >= ").append(day_start);
        bd.append(" and CAST(\"LAST_MEM_TIME\" as bigint) <= ").append(day_end);
        bd.append(" and \"AGING\" >= 2 and \"USER_ID\" = \'").append(user.getID());
        bd.append("\'");
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

    /**
     * @prarm user the instance of User
     * @return how many number of words this user have to review in total
     */
    public int getNeedReviewNum(User user) {
        Database db = Database.getInstance();
        StudyPlan plan = user.getCurrentStudyPlan();
        if (plan == null) {
            return 0;
        }

        String table = plan.getStudyPlanName();
        Long time = System.currentTimeMillis();
        long day_start
                = (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset())
                / (1000 * 3600 * 24) * (1000 * 3600 * 24)
                - TimeZone.getDefault().getRawOffset();
        long day_end = day_start + 1000 * 24 * 60 * 60 - 1;
        int ndRevNum = 0;
        // aging is 1, that means the words who is memorized today or other days 
        // without review. 
        StringBuilder bd = new StringBuilder("select count(*) as NUMBER from ");
        bd.append("\"MEMORIZE\" where \"WORD_SOURCE\" = \'").append(table.toUpperCase());
        bd.append("\' and \"AGING\" = 1 and \"USER_ID\" = \'").append(user.getID());
        bd.append("\'");
        ResultSet res = db.SQLqr(bd.toString());
        try {
            if (res.next()) {
                ndRevNum = res.getInt("NUMBER");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ndRevNum;
    }

    public void editPlan(User user, String book, int dailyNum, int days) {
        Database db = Database.getInstance();
        String sqlPrepared = "UPDATE PLAN SET TOTAL_DAY = ?, TODAY_TARGET_NUMBER = ? WHERE USER_ID = ? AND BOOK = ?";
        int totalNum = db.count(book, "", "");
        /**
         * if input daily numbers.
         */
        int dailyNumNew = dailyNum;
        int daysNew = totalNum / dailyNumNew;
        String userId = user.getID() + "";

        /**
         * If input days.
         */
        if (dailyNum < 1) {
            daysNew = days;
            dailyNumNew = totalNum / daysNew;

        }

        db.prepare(sqlPrepared, daysNew, dailyNumNew, userId, book);
    }

    public void removePlan(User user, String book) {
        Database db = Database.getInstance();
        String sqlPrepared = "DELETE FROM PLAN WHERE USER_ID = ? AND BOOK = ?";
        String userId = user.getID() + "";
        db.prepare(sqlPrepared, userId, book);
        String sqlPrepared2 = "DELETE FROM MEMORIZE WHERE USER_ID = ? AND WORD_SOURCE = ?";
        db.prepare(sqlPrepared2, userId, book);
    }
}
