package net.stoerr.timetrack.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TimeTrackMain.class);

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
            }
        });
    }

    private JTabbedPane tabPane;

    private JTable lastEventsTable;

    private JLabel taskLabel;

    private JScrollPane eventScrollPane;

    private AbstractAction saveAction;

    private TimeEntryTableModel lastEventsTableModel;

    private TimeEntryController controller;

    private JButton newEntryButton;

    private JTextField taskField;

    private JPanel newEventTab;

    private JPanel newEventPanel;

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

    private AbstractAction getSaveAction() {
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
            {
                tabPane = new JTabbedPane();
                getContentPane().add(tabPane, BorderLayout.CENTER);
                {
                    newEventTab = new JPanel();
                    tabPane.addTab("new", null, newEventTab, null);
                    BorderLayout newEventTabLayout = new BorderLayout();
                    newEventTab.setLayout(newEventTabLayout);
                    newEventTab.setPreferredSize(new java.awt.Dimension(387, 225));
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
                            newEventPanel.add(getTaskField(), BorderLayout.CENTER);
                            taskField.setText("");
                        }
                        {
                            newEntryButton = new JButton();
                            newEventPanel.add(newEntryButton, BorderLayout.EAST);
                            newEntryButton.setText("Save");
                            newEntryButton.setAction(getSaveAction());
                        }
                    }
                    {
                        eventScrollPane = new JScrollPane();
                        newEventTab.add(eventScrollPane, BorderLayout.CENTER);
                        {
                            lastEventsTableModel = new TimeEntryTableModel();
                            lastEventsTable = new JTable();
                            eventScrollPane.setViewportView(lastEventsTable);
                            lastEventsTable.setModel(lastEventsTableModel);
                            lastEventsTable.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent evt) {
                                    lastEventsTableMouseClicked(evt);
                                }
                            });
                            TableColumn col = lastEventsTable.getColumnModel().getColumn(0);
                            col.setMinWidth(150);
                            col.setMaxWidth(150);
                        }
                    }
                }
            }
            setSize(400, 300);
            setupKill();
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    private void lastEventsTableMouseClicked(MouseEvent evt) {
        System.out.println("lastEventsTable.mouseClicked, event=" + evt);
        int row = lastEventsTable.getSelectedRow();
        getTaskField().setText(getLastEventsTableModel().getEntries().get(row).getTask());
    }

    public void refresh() {
        List<TimeEntry> entries = getController().getEntries();
        getLastEventsTableModel().setEntries(entries);
        getLastEventsTableModel().fireTableDataChanged();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                eventScrollPane.getVerticalScrollBar().setValue(10000);
            };
        });
    }

    public void save() {
        final String text = getTaskField().getText();
        if (null != text && !"".equals(text.trim())) {
            TimeEntry entry = new TimeEntry();
            entry.setTask(text.trim());
            entry.setTime(new Date());
            getController().getTransaction().begin();
            getController().createEntry(entry);
            getController().getTransaction().commit();
            getController().flushDB();
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

}
