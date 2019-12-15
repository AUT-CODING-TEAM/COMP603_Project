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
//                super.windowClosing(e);
                new MainView(user);
            }
        });

        this.fillBlank();
        this.addTextLabel();
        this.addMyBookPanel();

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

        for (int i = 0; i < myPlanInfo.getMyStudyPlans().size(); i++) {;
            PlanPanelUnit plan = new PlanPanelUnit(user, myPlanInfo.getMyStudyPlans().get(i));
            this.panelList.add(plan);
            myBookPanel.add(plan);
        }

        JButton btn_myBP_addBook = new JButton("Add a Plan");
        btn_myBP_addBook.addActionListener(new ShowPlanListController(myPlanFrame, user));
        myBookPanel.add(btn_myBP_addBook);
        JScrollPane jScrollPane = new JScrollPane(myBookPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jScrollPane, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.15));

        myBookPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() instanceof PlanPanelUnit) {
                    PlanPanelUnit pn = (PlanPanelUnit) e.getSource();
                    for (JPanel p : MyPlanPanel.this.panelList) {
                        PlanPanelUnit pn_inlist = (PlanPanelUnit) p;
                        if (pn == pn_inlist) {
                            continue;
                        }
                        pn_inlist.unSelect();
                    }
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

}
