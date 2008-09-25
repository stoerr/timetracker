package net.stoerr.timetrack.gui;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.stoerr.timetrack.entity.TimeEntry;

/**
 * Tablemodel for a list of {@link TimeEntry}.
 * 
 * @author hps
 * @since 25.09.2008
 */
public class TimeEntryTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 4427952859031059850L;

    private List<TimeEntry> entries = Collections.emptyList();

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
        case 0:
            return Date.class;
        case 1:
            return String.class;
        default:
            throw new ArrayIndexOutOfBoundsException(column);
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
        case 0:
            return "Time";
        case 1:
            return "Task";
        default:
            throw new ArrayIndexOutOfBoundsException(column);
        }
    }

    /**
     * The displayed list.
     * 
     * @return the entries
     */
    public List<TimeEntry> getEntries() {
        return entries;
    }

    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        TimeEntry entry = entries.get(row);
        switch (column) {
        case 0:
            return entry.getTime();
        case 1:
            return entry.getTask();
        default:
            throw new ArrayIndexOutOfBoundsException(column);
        }
    }

    public void setEntries(List<TimeEntry> entries2) {
        this.entries = entries2;
        if (null == entries) {
            entries = Collections.emptyList();
        }
    }

}
