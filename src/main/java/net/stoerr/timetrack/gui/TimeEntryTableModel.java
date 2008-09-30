package net.stoerr.timetrack.gui;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

    private final SimpleDateFormat format = new SimpleDateFormat("EE dd.MM.yy HH:mm", Locale.GERMANY);

    @Override
    public int getColumnCount() {
        return 3;
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
            return "Hours";
        case 2:
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
            if (null != entry.getTime()) {
                return format.format(entry.getTime());
            } else {
                return null;
            }
        case 1:
            if (null != entry.getHours()) {
                return entry.getHours();
            } else {
                return "";
            }
        case 2:
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
