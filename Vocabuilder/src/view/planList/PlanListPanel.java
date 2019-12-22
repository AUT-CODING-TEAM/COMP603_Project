package view.planList;

import view.OnePlanPanel;
import controller.planList.AddPlanController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.*;
import view.*;
import view.myPlan.MyPlanPanel;

/**
 *
 * @author ThinkPad
 */
public class PlanListPanel extends GroundPanelTemplate {

    private User user;
    private PlanListInfo planListInfo;

    public PlanListPanel(User user, PlanListInfo planListInfo) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.planListInfo = planListInfo;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        JFrame planListFrame = new JFrame("Add a Plan");
        setSize(planListFrame, 720, 720);
        planListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        planListFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (user.getCurrentStudyPlan() == null) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Do you want to leave without selecting a plan?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    new PlanListPanel(user, new PlanListInfo(user));
                } else {
                    new MyPlanPanel(user, new MyPlanInfo(user));
                }
            }
        });

        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel selectionTipLabel = new JLabel("Select Vocabulary List", SwingConstants.CENTER);
        add(selectionTipLabel, new GridBagTool().setFill(GridBagConstraints.VERTICAL).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        JPanel planListPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        int col = 5;
        int row = planListInfo.getStudyPlans().size() / col + 1;

        planListPanel.setLayout(new GridLayout(row, col, 20, 20));

        AddPlanController addPlanController = new AddPlanController(user, planListFrame);

        for (int i = 0; i < planListInfo.getStudyPlans().size(); i++) {
            OnePlanPanel jPanel = new OnePlanPanel();
            jPanel.setStudyPlan(planListInfo.getStudyPlans().get(i));

            JLabel planNameLabel = new JLabel(planListInfo.getStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            planNameLabel.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(planNameLabel, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel totalNumLabel = new JLabel(String.valueOf(planListInfo.getStudyPlans().get(i).getTotalNumber()) + "words", SwingConstants.CENTER);
            totalNumLabel.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(totalNumLabel, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

            if (planListInfo.getStudyPlans().get(i).getStudyPlanName().equals("fill")) {
                jPanel.setBackground(new Color(248, 246, 241));
                planNameLabel.setText("");
                totalNumLabel.setText("");
            } else if (planListInfo.getStudyPlans().get(i).getAdded() == 0) {
                jPanel.setBackground(new Color(186, 187, 185));
                jPanel.addMouseListener(addPlanController);
            } else {
                jPanel.setBackground(new Color(91, 110, 125));
                jPanel.setBorder(new TitledBorder("Added"));
            }

            planListPanel.add(jPanel);
        }

        JScrollPane jScrollPane = new JScrollPane(planListPanel);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.8));

        planListFrame.add(this);
        planListFrame.setVisible(true);
    }
}
