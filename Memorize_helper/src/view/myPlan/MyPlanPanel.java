/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editBtnor.
 */
package view.myPlan;

import controller.interfaces.PlanController;
import controller.myPlan.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad, Pingchuan, Yizhao
 */
public class MyPlanPanel extends GroundPanelTemplate implements ActionListener, MouseListener {

    private User user;
    private MyPlanInfo myPlanInfo;
    private JFrame myPlanFrame;

    private String quantity;

    //Buttons
    private JButton switchBtn;
    private JButton editBtn;
    private JButton removeBtn;
    private JButton addBtn;

    //Plan
    private String selectedPlan;

    public String getSelectedPlan() {
        return selectedPlan;
    }
    private ArrayList<PlanPanelUnit> panelList;

    //Controllers
    private static final PlanController PLAN_CONTROLLER = new PlanController();

    public JFrame getMyPlanFrame() {
        return myPlanFrame;
    }

    public String getQuantity() {
        return quantity;
    }

    public MyPlanPanel(User user, MyPlanInfo myPlanInfo) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.myPlanInfo = myPlanInfo;
        this.panelList = new ArrayList<>();
        this.setProperty();
        this.addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        this.myPlanFrame = new JFrame("My Plan(s)");
        super.setSize(myPlanFrame, 720, 360);
        myPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myPlanFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new MainView(user);
            }
        });

        this.fillBlank();
        this.addTextLabel();
        this.addMyBookPanel();

        switchBtn = new JButton("Switch");
        switchBtn.setEnabled(false);
        add(switchBtn, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        switchBtn.addActionListener(new MakePlanController(user, this));

        this.editBtn = new JButton("Edit");
        this.editBtn.setEnabled(false);
        add(editBtn, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        this.editBtn.addActionListener(this);
        
        this.removeBtn = new JButton("Remove");
        this.removeBtn.setEnabled(false);
        add(removeBtn, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        this.removeBtn.addActionListener(this);

        myPlanFrame.add(this);
        myPlanFrame.setVisible(true);
    }

    private void fillBlank() {
        //left fill label
        this.add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        this.add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //bottom fill label
        this.add(new JLabel(), new GridBagTool().setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
    }

    private void addTextLabel() {
        JLabel lbl_myPP_myPlan = new JLabel("My Plan(s)", SwingConstants.CENTER);
        this.add(lbl_myPP_myPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_chooseBook = new JLabel("Select a Plan");
        this.add(lbl_myPP_chooseBook, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
    }

    private void addMyBookPanel() {
        JPanel myBookPanel = new JPanel(new GridLayout(1, myPlanInfo.getMyStudyPlans().size() + 1, 20, 20));

        for (int i = 0; i < myPlanInfo.getMyStudyPlans().size(); i++) {
            PlanPanelUnit plan = new PlanPanelUnit(user, myPlanInfo.getMyStudyPlans().get(i));
            this.panelList.add(plan);

            myBookPanel.add(plan);
        }

        addBtn = new JButton("Add a Plan");
        addBtn.addActionListener(new ShowPlanListController(myPlanFrame, user));
        myBookPanel.add(addBtn);
        
        JScrollPane jScrollPane = new JScrollPane(myBookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.15));

        myBookPanel.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * To control buttons of plan management.
         */
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            switch (btn.getText()) {

                case "Edit":
                    if (this.selectedPlan != null) {
                        //Edit
                        break;
                    }
                    break;

                case "Remove":
                    if (this.selectedPlan != null) {
                        int choice = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure to remove this plan?",
                                "Remove check",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                        if (choice == 0) {
                            PLAN_CONTROLLER.removePlan(user, selectedPlan);
                        }
                        break;
                    }
                    break;

                case "Switch":
                    if (this.selectedPlan != null) {
                        //Edit
                        break;
                    }
                    break;
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof PlanPanelUnit) {
            PlanPanelUnit clicked = (PlanPanelUnit) e.getSource();

            /**
             * Fix border of plan units
             */
            for (PlanPanelUnit p : this.panelList) {
                if (clicked != p) {
                    p.unSelect();
                }
            }

            /**
             * Set selected plan name.
             */
            this.selectedPlan = clicked.getPlanName();
            /**
             * Set buttons condition
             */
            this.removeBtn.setEnabled(true);
            this.editBtn.setEnabled(true);
            this.switchBtn.setEnabled(false);
            if (!clicked.isActivated()) {
                this.switchBtn.setEnabled(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
