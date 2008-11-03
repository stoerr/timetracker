package net.stoerr.timetrack.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

import net.stoerr.timetrack.controller.TimeEntryController;
import net.stoerr.timetrack.entity.TimeEntry;

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

    /** SUID */
    private static final long serialVersionUID = 8552907221005431441L;

    /** Logger for TimeTrackMain */
    static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(TimeTrackMain.class);

    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TimeEntryController controller = new TimeEntryController();
                controller.startup(); // start hibernate now.
                TimeTrackMain inst = new TimeTrackMain(controller);
                inst.setLocationRelativeTo(null);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                inst.setTitle("Time tracker HPS");
                inst.setExtendedState(Frame.MAXIMIZED_BOTH);
                inst.setVisible(true);
                inst.setAlwaysOnTop(true);
            }
        });
    }

    private AbstractAction saveAction;

    private JButton countdownStop;

    private JButton startClosing;

    private JPanel countdownActions;

    private JLabel countdownLabel;

    private JPanel countDownPane;

    private JTable lastEventsTable;

    private JScrollPane eventScrollPane;

    private JButton saveandcloseButton;

    private JTextField taskField;

    private JButton newEntryButton;

    private JPanel newEventPanel;

    private JPanel newEventTab;

    private JTabbedPane tabPane;

    private AbstractAction stopCountdownAction;

    private AbstractAction countdownAction;

    private TimeEntryTableModel lastEventsTableModel;

    private TimeEntryController controller;

    public TimeTrackMain() {
        super();
        initGUI();
    }

    public TimeTrackMain(TimeEntryController theController) {
        super();
        controller = theController;
        initGUI();
    }

    public TimeEntryController getController() {
        if (controller == null) {
            controller = new TimeEntryController();
        }
        return controller;
    }

    public TimeEntryTableModel getLastEventsTableModel() {
        return lastEventsTableModel;
    }

    @SuppressWarnings("serial")
    public AbstractAction getSaveAction() {
        if (saveAction == null) {
            saveAction = new AbstractAction("Save", null) {
                public void actionPerformed(ActionEvent evt) {
                    save();
                }
            };
        }
        return saveAction;
    }

    public JTextField getTaskField() {
        return taskField;
    }

    private void initGUI() {
        try {
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
                        newEventPanel = new JPanel();
                        newEventTab.add(newEventPanel, BorderLayout.SOUTH);
                        BorderLayout newEventPanelLayout = new BorderLayout();
                        newEventPanel.setLayout(newEventPanelLayout);
                        {
                            newEntryButton = new JButton();
                            newEventPanel
                                    .add(newEntryButton, BorderLayout.WEST);
                            newEntryButton.setText("Save");
                            newEntryButton.setAction(getSaveAction());
                        }
                        {
                            taskField = new JTextField();
                            newEventPanel.add(taskField, BorderLayout.CENTER);
                            taskField.setText("");
                        }
                        {
                            saveandcloseButton = new JButton();
                            newEventPanel.add(saveandcloseButton,
                                    BorderLayout.EAST);
                            saveandcloseButton.setText("Save and Closing");
                            saveandcloseButton
                                    .addActionListener(new ActionListener() {
                                        public void actionPerformed(
                                                ActionEvent evt) {
                                            System.out
                                                    .println("saveandcloseButton.actionPerformed, event="
                                                            + evt);
                                            getSaveAction()
                                                    .actionPerformed(evt);
                                            getCountdownAction()
                                                    .actionPerformed(evt);
                                        }
                                    });
                        }
                    }
                    {
                        eventScrollPane = new JScrollPane();
                        eventScrollPane.getVerticalScrollBar().setValue(10000);
                        newEventTab.add(eventScrollPane, BorderLayout.CENTER);
                        {
                            lastEventsTableModel = new TimeEntryTableModel();
                            lastEventsTable = new JTable();
                            eventScrollPane.setViewportView(lastEventsTable);
                            lastEventsTable.setModel(lastEventsTableModel);
                            lastEventsTable
                                    .addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent evt) {
                                            lastEventsTableMouseClicked(evt);
                                        }
                                    });
                            TableColumn col = lastEventsTable.getColumnModel()
                                    .getColumn(0);
                            col.setMinWidth(150);
                            col.setMaxWidth(150);
                            col = lastEventsTable.getColumnModel().getColumn(1);
                            col.setMinWidth(100);
                            col.setMaxWidth(100);
                        }
                    }
                }
                {
                    countDownPane = new JPanel();
                    tabPane.addTab("stop", null, countDownPane, null);
                    BorderLayout countDownPaneLayout = new BorderLayout();
                    countDownPane.setLayout(countDownPaneLayout);
                    {
                        countdownLabel = new JLabel();
                        countDownPane.add(countdownLabel, BorderLayout.CENTER);
                        countdownLabel.setText("Countdown");
                        countdownLabel
                                .setHorizontalAlignment(SwingConstants.CENTER);
                        countdownLabel
                                .setHorizontalTextPosition(SwingConstants.CENTER);
                    }
                    {
                        countdownActions = new JPanel();
                        countDownPane.add(countdownActions, BorderLayout.SOUTH);
                        BorderLayout countdownActionsLayout = new BorderLayout();
                        countdownActions.setLayout(countdownActionsLayout);
                        countdownActions.setBounds(223, 284, 10, 10);
                        {
                            startClosing = new JButton();
                            countdownActions.add(startClosing,
                                    BorderLayout.WEST);
                            startClosing.setAction(getCountdownAction());
                            startClosing.addActionListener(countdownAction);
                        }
                        {
                            countdownStop = new JButton();
                            countdownActions.add(countdownStop,
                                    BorderLayout.EAST);
                            countdownStop.setAction(getStopCountdownAction());
                            countdownStop
                                    .addActionListener(stopCountdownAction);
                        }
                    }
                }
            }
            {
                this.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowActivated(WindowEvent evt) {
                        thisWindowActivated(evt);
                    }

                    @Override
                    public void windowClosed(WindowEvent evt) {
                        LOG.info("windowClosed");
                        thisWindowClosed(evt);
                    }

                    @Override
                    public void windowClosing(WindowEvent evt) {
                        LOG.info("windowClosing");
                        thisWindowClosed(evt);
                    }
                });
            }
            setSize(400, 300);
            setupKill();
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    void lastEventsTableMouseClicked(MouseEvent evt) {
        System.out.println("lastEventsTable.mouseClicked, event=" + evt);
        int row = lastEventsTable.getSelectedRow();
        getTaskField().setText(
                getLastEventsTableModel().getEntries().get(row).getTask());
    }

    public void refresh() {
        List<TimeEntry> entries = getController().getEntries();
        getLastEventsTableModel().setEntries(entries);
        getLastEventsTableModel().fireTableDataChanged();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    public void save() {
        final String text = getTaskField().getText();
        if (null != text && !"".equals(text.trim())) {
            getController().saveAction(text);
        }
        refresh();
    }

    private void setupKill() {
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(15 * 1000 * 1000);
                return null;
            }

            @Override
            protected void done() {
                setVisible(false);
                dispose();
            }

        }.execute();
    }

    void thisWindowActivated(WindowEvent evt) {
        LOG.info("this.windowActivated, event=" + evt);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    void thisWindowClosed(WindowEvent evt) {
        LOG.info("Shutdown. " + evt);
        getController().shutdown();
    }

    public AbstractAction getCountdownAction() {
        if (countdownAction == null) {
            countdownAction = new AbstractAction("Countdown and Close", null) {
                public void actionPerformed(ActionEvent evt) {
                }
            };
        }
        return countdownAction;
    }

    public AbstractAction getStopCountdownAction() {
        if (stopCountdownAction == null) {
            stopCountdownAction = new AbstractAction("Stop Countdown", null) {
                public void actionPerformed(ActionEvent evt) {
                }
            };
        }
        return stopCountdownAction;
    }

}
