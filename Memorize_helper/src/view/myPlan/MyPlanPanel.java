/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.myPlan;

import controller.myPlan.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 */
public class MyPlanPanel extends GroundPanelTemplate {

    private User user;
    private MyPlanInfo myPlanInfo;
    private JFrame myPlanFrame;
    private JButton btn_myPP_handleByBookSituation;
    private String bookName;
    private String quantity;

    public JFrame getMyPlanFrame() {
        return myPlanFrame;
    }

    public String getBookName() {
        return bookName;
    }

    public String getQuantity() {
        return quantity;
    }

    public MyPlanPanel(User user, MyPlanInfo myPlanInfo) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.myPlanInfo = myPlanInfo;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        myPlanFrame = new JFrame("我的计划");
        setSize(myPlanFrame, 720, 360);
        myPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myPlanFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                new MainView(user);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_myPlan = new JLabel("我的计划", SwingConstants.CENTER);
        add(lbl_myPP_myPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_chooseBook = new JLabel("选择学习词书");
        add(lbl_myPP_chooseBook, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        addMyBookPanel();

        btn_myPP_handleByBookSituation = new JButton("继续学习该计划");
        btn_myPP_handleByBookSituation.setEnabled(false);
        btn_myPP_handleByBookSituation.addActionListener(new MakePlanController(user, this));
        add(btn_myPP_handleByBookSituation, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        myPlanFrame.add(this);
        myPlanFrame.setVisible(true);
    }

    public void addMyBookPanel() {
        JPanel myBookPanel = new JPanel(new GridLayout(1, myPlanInfo.getMyStudyPlans().size() + 1, 20, 20));

        for (int i = 0; i < myPlanInfo.getMyStudyPlans().size(); i++) {
            OnePlanPanel jPanel = new OnePlanPanel();
            jPanel.setName(String.valueOf(i));

            JLabel lbl_myPP_studyPlanName = new JLabel(myPlanInfo.getMyStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            lbl_myPP_studyPlanName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(lbl_myPP_studyPlanName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel lbl_myPP_totalNumber = new JLabel(String.valueOf(myPlanInfo.getMyStudyPlans().get(i).getTotalNumber()) + "词", SwingConstants.CENTER);
            lbl_myPP_totalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(lbl_myPP_totalNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

            jPanel.setBackground(new Color(91, 110, 125));
            jPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    OnePlanPanel selectedPanel = (OnePlanPanel) e.getSource();
                    int index = Integer.parseInt(selectedPanel.getName());
                    bookName = myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName();
                    if (myPlanInfo.getMyStudyPlans().get(index).getFinished() == 0) {
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("学习该计划");
                    } else if (myPlanInfo.getMyStudyPlans().get(index).getFinished() == 1) {
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        btn_myPP_handleByBookSituation.setText("复习该计划");
                    }
                    if (user.getCurrentStudyPlan().getStudyPlanName().equals(myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName())) {
                        btn_myPP_handleByBookSituation.setEnabled(false);
                        btn_myPP_handleByBookSituation.setText("继续学习该计划");
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
            });
            if (myPlanInfo.getMyStudyPlans().get(i).getFinished() == 1) {
                jPanel.setBorder(new TitledBorder("已学完"));
            }

            myBookPanel.add(jPanel);
        }

        JButton btn_myBP_addBook = new JButton("添加词书");
        btn_myBP_addBook.addActionListener(new ShowPlanListController(myPlanFrame, user));
        myBookPanel.add(btn_myBP_addBook);

        JScrollPane jScrollPane = new JScrollPane(myBookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }
}
