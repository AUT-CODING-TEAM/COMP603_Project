package view.main;

import controller.main.*;
import java.awt.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class CenterPanel extends MainViewViewTemplate {

    private int remainingDay;

    public CenterPanel(JFrame mainView, User user) {
        super(mainView, user);
    }

    public int getRemainingDay() {
        return remainingDay;
    }

    @Override
    public void setProperty() {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.decode("#F8F6F1"));
    }

    @Override
    public void addComponents() {
        StudyPlan sp = user.getCurrentStudyPlan();
        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(3).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));

        JLabel remainDayLabel = new JLabel("Remaining", SwingConstants.CENTER);
        add(remainDayLabel, new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));

        JLabel targetNum_1 = new JLabel("Today's", SwingConstants.CENTER);
        add(targetNum_1, new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));

        addRemainingDayRest();
        addTodayTargetNumberRest();

        JLabel currentPlanLabel = new JLabel("Current Plan: " + user.getCurrentStudyPlan().getStudyPlanName(), SwingConstants.CENTER);
        add(currentPlanLabel, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JButton editPlanBtn = new JButton("Change Plan");
        editPlanBtn.addActionListener(new ChangePlanController(mainView, user));
        add(editPlanBtn, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JLabel learntNumLabel = new JLabel("Learned: " + sp.getTotalMemorizedNumber() + "/" + user.getCurrentStudyPlan().getTotalNumber(), SwingConstants.CENTER);
        add(learntNumLabel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JButton wordListBtn = new JButton("Word List");
        wordListBtn.addActionListener(new ShowVocabularyListController(mainView, user));
        add(wordListBtn, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        addProgressBar();

    }

    private void addRemainingDayRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        remainingDay = user.getRemainingDay() < 0 ? 0 : user.getRemainingDay();
        JLabel remainDayLabel_2 = new JLabel(String.valueOf(remainingDay), SwingConstants.CENTER);
        remainDayLabel_2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(remainDayLabel_2);

        JLabel remainDayLabel_3 = new JLabel(remainingDay == 1 ? "day" : "days", SwingConstants.CENTER);
        jPanel.add(remainDayLabel_3);

        add(jPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }

    private void addTodayTargetNumberRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JLabel targetNum_2 = new JLabel(String.valueOf(user.getTodayTargetNumber()), SwingConstants.CENTER);
        targetNum_2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(targetNum_2);

        JLabel targetNum_3 = new JLabel("words", SwingConstants.CENTER);
        jPanel.add(targetNum_3);

        add(jPanel, new GridBagTool().setGridx(2).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }

    private void addProgressBar() {
        StudyPlan sp = user.getCurrentStudyPlan();
        int totalMemorizedNumber = sp.getTotalMemorizedNumber();
        int totalNumber = sp.getTotalNumber();

        JProgressBar progressBar = new JProgressBar(0, 100);
        int progress = totalMemorizedNumber * 100 / totalNumber;

        progressBar.setValue(progress);
        progressBar.setOpaque(true);
        progressBar.setBackground(Color.decode("#F8F6F1"));
        add(progressBar, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }
}
