/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.planList;

import controller.myPlan.MakePlanController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.*;
import view.*;
import view.myPlan.MyPlanPanel;

/**
 *
 * @author ThinkPad
 */
public class CreatePlanPanel extends GroundPanelTemplate {

    private User user;
    private StudyPlan selectedPlan;
    private JFrame selectedPlanFrame;
    private JLabel selectedBookName;
    private JLabel selectedBookTotalNumber;
    private JTabbedPane optionTabs;
    private JButton btn_confirm;
    private JList makePlanListPart1;
    private JList makePlanListPart2;
    private String quantity;

    public String getQuantity() {
        return quantity;
    }

    public StudyPlan getSelectedPlan() {
        return selectedPlan;
    }

    public CreatePlanPanel(User user, StudyPlan selectedPlan) {
        super(GroundPanelTemplate.BACK);
        this.user = user;
        this.selectedPlan = selectedPlan;
        setProperty();
        addComponents();
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        selectedPlanFrame = new JFrame("Schedule the Plan");
        setSize(selectedPlanFrame, 720, 720);
        selectedPlanFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectedPlanFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (e.getID() == WindowEvent.WINDOW_CLOSING && user.getCurrentStudyPlan() == null) {
                    JOptionPane.showMessageDialog(null, "Please schedule the plan!", "informaiton ", JOptionPane.INFORMATION_MESSAGE);
                    new CreatePlanPanel(user, selectedPlan);
                }
                else{
                    new MyPlanPanel(user, new MyPlanInfo(user));
                }
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));

        addMakePlanPanel();

        btn_confirm = new JButton();
        btn_confirm.setText("OK");

        btn_confirm.addActionListener(new MakePlanController(user, this, selectedPlanFrame));
        add(btn_confirm, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        selectedPlanFrame.add(this);
        selectedPlanFrame.setVisible(true);
    }

    public void addMakePlanPanel() {
        JPanel topJPanel = new JPanel(new GridLayout(2, 1));
        topJPanel.setOpaque(true);
        topJPanel.setBackground(new Color(238, 236, 232));

        selectedBookName = new JLabel(selectedPlan.getStudyPlanName(), SwingConstants.CENTER);
        selectedBookName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        topJPanel.add(selectedBookName);

        selectedBookTotalNumber = new JLabel(String.valueOf(selectedPlan.getTotalNumber()) + " words", SwingConstants.CENTER);
        selectedBookTotalNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));
        topJPanel.add(selectedBookTotalNumber);

        add(topJPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        optionTabs = new JTabbedPane();

        MakePlanTabPanel makePlanTabPanelPart1 = new MakePlanTabPanel(0);
        MakePlanTabPanel makePlanTabPanelPart2 = new MakePlanTabPanel(1);

        optionTabs.addTab("based on DAILY TASK", makePlanTabPanelPart1);
        optionTabs.addTab("based on LEARNING DURATION", makePlanTabPanelPart2);

        add(optionTabs, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.7));
    }

    class MakePlanTabPanel extends JPanel {

        private int option;

        public MakePlanTabPanel(int option) {
            this.option = option;
            setProperty();
            addComponents();
        }

        public void setProperty() {
            setLayout(new GridLayout(1, 1));
        }

        public void addComponents() {
            if (option == 0) {
                String s[] = null;
                if (selectedPlan.getTotalNumber() % 5 == 0) {
                    s = new String[selectedPlan.getTotalNumber() / 5];
                    for (int i = 0; i < s.length; i++) {
                        s[i] = String.format("%50s", String.valueOf(5 * (i + 1) + " words"));
                    }
                } else {
                    s = new String[selectedPlan.getTotalNumber() / 5 + 1];
                    for (int i = 0; i < s.length - 1; i++) {
                        s[i] = String.format("%50s", String.valueOf(5 * (i + 1) + " words"));
                    }
                    s[s.length - 1] = String.format("%50s", String.valueOf(selectedPlan.getTotalNumber() + " words"));
                }
                makePlanListPart1 = new ListInScrollTemplate(s);
                makePlanListPart1.setEnabled(true);
                makePlanListPart1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                makePlanListPart1.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (makePlanListPart1.getValueIsAdjusting()) {
                            quantity = makePlanListPart1.getSelectedValue().toString().trim();
                        }
                    }
                });
                JScrollPane jScrollPane = new JScrollPane(makePlanListPart1);
                add(jScrollPane);
            } else if (option == 1) {
                String s[] = new String[selectedPlan.getTotalNumber()];
                for (int i = 0; i < s.length; i++) {
                    s[i] = String.format("%50s", (i + 1) + (i == 0? " day": " days"));
                }
                makePlanListPart2 = new ListInScrollTemplate(s);
                makePlanListPart2.setEnabled(true);
                makePlanListPart2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                makePlanListPart2.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (makePlanListPart2.getValueIsAdjusting()) {
                            quantity = makePlanListPart2.getSelectedValue().toString().trim();
                        }
                    }
                });
                JScrollPane jScrollPane = new JScrollPane(makePlanListPart2);
                add(jScrollPane);
            }
        }
    }
}
