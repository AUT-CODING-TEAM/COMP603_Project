/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.myPlan;

import controller.myPlan.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 */
public class MyPlanPanel extends GroundPanelTemplate {

//    public static Border defaultBorder = BorderFactory.createLineBorder(new Color(91, 110, 125));
//    public static Border currentBorder = BorderFactory.createLineBorder(new Color(23, 35, 61));
    public static Border selectedBorder = BorderFactory.createLineBorder(new Color(45, 140, 240), 5);
    public static Color currentColor = new Color(23, 35, 61);
    private User user;
    private MyPlanInfo myPlanInfo;
    private JFrame myPlanFrame;
    private JButton btn_myPP_handleByBookSituation;
    private String bookName;
    private String quantity;
    private JPanel selected;

    private ArrayList<JPanel> panelList;
    private JButton edit;
    private JButton remove;

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
        this.panelList = new ArrayList<>();
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        myPlanFrame = new JFrame("My Plan(s)");
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

        JLabel lbl_myPP_myPlan = new JLabel("My Plan(s)", SwingConstants.CENTER);
        add(lbl_myPP_myPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        JLabel lbl_myPP_chooseBook = new JLabel("Select a Plan");
        add(lbl_myPP_chooseBook, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        addMyBookPanel();

        btn_myPP_handleByBookSituation = new JButton("Switch");
        btn_myPP_handleByBookSituation.setEnabled(false);
        btn_myPP_handleByBookSituation.addActionListener(new MakePlanController(user, this));
        add(btn_myPP_handleByBookSituation, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        this.edit = new JButton("Edit");
        this.remove = new JButton("Remove");
        this.edit.setEnabled(false);
        this.remove.setEnabled(false);
        add(edit, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        add(remove, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        myPlanFrame.add(this);
        myPlanFrame.setVisible(true);
    }

    public void addMyBookPanel() {
        JPanel myBookPanel = new JPanel(new GridLayout(1, myPlanInfo.getMyStudyPlans().size() + 1, 20, 20));

        for (int i = 0; i < myPlanInfo.getMyStudyPlans().size(); i++) {
            OnePlanPanel jPanel = new OnePlanPanel();
            this.panelList.add(jPanel);
            jPanel.setName(String.valueOf(i));

            JLabel lbl_myPP_studyPlanName = new JLabel(myPlanInfo.getMyStudyPlans().get(i).getStudyPlanName(), SwingConstants.CENTER);
            lbl_myPP_studyPlanName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
            jPanel.addStudyPlanName(lbl_myPP_studyPlanName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));

            JLabel lbl_myPP_totalNumber = new JLabel(String.valueOf(myPlanInfo.getMyStudyPlans().get(i).getTotalNumber()) + "words", SwingConstants.CENTER);
            lbl_myPP_totalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
            jPanel.addTotalNumber(lbl_myPP_totalNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

            jPanel.setBackground(new Color(91, 110, 125));
//            jPanel.setBorder(defaultBorder);
            if (this.user.getCurrentStudyPlan().getStudyPlanName().equals(lbl_myPP_studyPlanName.getText())) {
                jPanel.setBackground(currentColor);
//                jPanel.setBorder(currentBorder);
                StringBuilder sb = new StringBuilder();
                sb.append("<html>&nbsp;(Current)<br>");
                sb.append(lbl_myPP_totalNumber.getText() + "</html>");
                lbl_myPP_totalNumber.setText(sb.toString());
                lbl_myPP_studyPlanName.setForeground(Color.white);
                lbl_myPP_totalNumber.setForeground(Color.white);

            }

            jPanel.addMouseListener(new MouseListener() {
                MyPlanPanel that = MyPlanPanel.this;

                @Override
                public void mouseClicked(MouseEvent e) {
                    OnePlanPanel selectedPanel = (OnePlanPanel) e.getSource();
                    that.selected = selectedPanel;
                    int index = Integer.parseInt(selectedPanel.getName());
                    bookName = myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName();
                    if (myPlanInfo.getMyStudyPlans().get(index).getFinished() == 0) {
                        jPanel.setBorder(selectedBorder);
                        for (JPanel jp : that.panelList) {
                            if (jp == jPanel) {
                                continue;
                            }

                            jp.setBorder(null);
                        }
                        btn_myPP_handleByBookSituation.setEnabled(true);
                        remove.setEnabled(true);
                        edit.setEnabled(true);
                    }
                    if (user.getCurrentStudyPlan().getStudyPlanName().equals(myPlanInfo.getMyStudyPlans().get(index).getStudyPlanName())) {
                        jPanel.setBorder(selectedBorder);
                        for (JPanel jp : that.panelList) {
                            if (jp == jPanel) {
                                continue;
                            }
                            jp.setBorder(null);
                        }
                        btn_myPP_handleByBookSituation.setEnabled(false);
                        remove.setEnabled(true);
                        edit.setEnabled(true);
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
                jPanel.setBorder(new TitledBorder("Completed"));
            }

            myBookPanel.add(jPanel);
        }

        JButton btn_myBP_addBook = new JButton("Add a Plan");
        btn_myBP_addBook.addActionListener(new ShowPlanListController(myPlanFrame, user));
        myBookPanel.add(btn_myBP_addBook);

        JScrollPane jScrollPane = new JScrollPane(myBookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }
}
