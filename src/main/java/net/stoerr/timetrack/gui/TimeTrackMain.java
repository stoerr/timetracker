package net.stoerr.timetrack.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.stoerr.timetrack.controller.TimeEntryController;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TimeTrackMain extends javax.swing.JFrame {
    static private JTabbedPane tabPane;

    static private JTable lastEventsTable;

    static private JLabel taskLabel;
    private DefaultTableModel lastEventsTableModel;
    private TimeEntryController controller;

    static private JButton newEntryButton;

    static private JTextField taskField;

    static private JPanel newEventTab;

    static private JPanel newEventPanel;

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TimeTrackMain inst = new TimeTrackMain();
                inst.setLocationRelativeTo(null);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                inst.setTitle("Time tracker HPS");
                inst.setExtendedState(Frame.MAXIMIZED_BOTH);
                inst.setVisible(true);
            }
        });
    }

    public TimeTrackMain() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            {
                this.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent evt) {
                        thisWindowClosed(evt);
                    }
                });
            }
            {
                tabPane = new JTabbedPane();
                getContentPane().add(tabPane, BorderLayout.CENTER);
                {
                    newEventTab = new JPanel();
                    tabPane.addTab("new", null, newEventTab, null);
                    BorderLayout newEventTabLayout = new BorderLayout();
                    newEventTab.setLayout(newEventTabLayout);
                    newEventTab.setPreferredSize(new java.awt.Dimension(387,
                            225));
                    {
                        lastEventsTableModel = new DefaultTableModel(
                                new String[][] { { "One", "Two" },
                                        { "Three", "Four" } }, new String[] {
                                        "Column 1", "Column 2" });
                        lastEventsTable = new JTable();
                        newEventTab.add(lastEventsTable, BorderLayout.CENTER);
                        lastEventsTable.setModel(lastEventsTableModel);
                    }
                    {
                        newEventPanel = new JPanel();
                        BorderLayout newEventPanelLayout = new BorderLayout();
                        newEventPanel.setLayout(newEventPanelLayout);
                        newEventTab.add(newEventPanel, BorderLayout.SOUTH);
                        {
                            taskLabel = new JLabel();
                            newEventPanel.add(taskLabel, BorderLayout.WEST);
                            taskLabel.setText("Aufgabe");
                        }
                        {
                            taskField = new JTextField();
                            newEventPanel.add(getTaskField(),
                                    BorderLayout.CENTER);
                            taskField.setText("Task");
                        }
                        {
                            newEntryButton = new JButton();
                            newEventPanel
                                    .add(newEntryButton, BorderLayout.EAST);
                            newEntryButton.setText("Save");
                        }
                    }
                }
            }
            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JTextField getTaskField() {
        return taskField;
    }

    private void thisWindowClosed(WindowEvent evt) {
        getController().shutdown();
    }
    
    public TimeEntryController getController() {
        if(controller == null) {
            controller = new TimeEntryController();
        }
        return controller;
    }

}
