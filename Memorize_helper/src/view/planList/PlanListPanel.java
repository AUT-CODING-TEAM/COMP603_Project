/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.planList;

import view.OnePlanPanel;
import controller.planList.AddPlanController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.MyPlanInfo;
import model.PlanListInfo;
import model.RankListInfo;
import model.User;
import view.GridBagTool;
import view.GroundPanelTemplate;
import view.ListInScrollTemplate;
import view.main.MainView;
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

    private void setSize(JFrame jFrame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = 1280;
        int frameWidth = 720;
        int frameHeight = 720;
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void addComponents() {
        JFrame planListFrame = new JFrame("添加词书");
        setSize(planListFrame);
        planListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //better jump to changePlanPanel
        planListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                new MyPlanPanel(user, new MyPlanInfo());
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

        JLabel lbl_pLP_rankList = new JLabel("添加词书", SwingConstants.CENTER);
        add(lbl_pLP_rankList, new GridBagTool().setFill(GridBagConstraints.VERTICAL).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        JPanel planListPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        int col = 5;
        int row = planListInfo.getStudyPlans().size() / col + 1;

        planListPanel.setLayout(new GridLayout(row, col, 20, 20));
        
        AddPlanController addPlanController = new AddPlanController();

        for (int i = 0; i < planListInfo.getStudyPlans().size(); i++) {
//            JPanel jPanel = new JPanel(new GridBagLayout());
            OnePlanPanel jPanel = new OnePlanPanel();

            JLabel lbl_pLP_studyPlanName = new JLabel(planListInfo.getStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            lbl_pLP_studyPlanName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(lbl_pLP_studyPlanName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel lbl_pLP_totalNumber = new JLabel(String.valueOf(planListInfo.getStudyPlans().get(i).getTotalNumber()) + "词", SwingConstants.CENTER);
            lbl_pLP_totalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(lbl_pLP_totalNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));
            
            if (planListInfo.getStudyPlans().get(i).getAdded() == 0) {
                jPanel.setBackground(new Color(186, 187, 185));
                jPanel.addMouseListener(addPlanController);
            }
            else{
                jPanel.setBackground(new Color(91, 110, 125));
                jPanel.setBorder(new TitledBorder("已添加"));
            }
            
            planListPanel.add(jPanel);
        }

        JScrollPane jScrollPane = new JScrollPane(planListPanel);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.8));

        planListFrame.add(this);
        planListFrame.setVisible(true);
    }

}
