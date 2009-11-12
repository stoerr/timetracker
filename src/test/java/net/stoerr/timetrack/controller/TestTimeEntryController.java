/* created by hps on 24.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.timetrack.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.stoerr.timetrack.TimeTrackConstants;
import net.stoerr.timetrack.entity.ShortRandomIdGenerator;
import net.stoerr.timetrack.entity.TimeEntry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author hps
 * @since 24.09.2008
 */
public class TestTimeEntryController implements TimeTrackConstants {

    static TimeEntryController c;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        c = new TimeEntryController();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        c.shutdown();
    }

    /**
     * @param taskName
     */
    private void createTask(final String taskName) {
        /**
         * Runnable2<String, TimeEntry> run = new Runnable2<String,
         * TimeEntry>(taskName) {
         * 
         * @Override public void run() { val2 = c.saveAction(val); } };
         *           c.wrapTransaction(run);
         */
        TimeEntry entry = c.saveAction(taskName);
        assertNotNull(entry.getId());
        /*
         * Same as c.getTransaction().begin(); c.createEntry(entry);
         * c.getTransaction().commit();
         */
    }

    @Test
    public void testCreate() {
        final String taskName = "Testtask " + new Date();
        createTask(taskName);
        List<TimeEntry> entries = c.getEntries();
        assertFalse(entries.isEmpty());
        boolean found = false;
        for (TimeEntry timeEntry : entries) {
            if (taskName.equals(timeEntry.getTask())) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testIDGen() {
        ShortRandomIdGenerator gen = new ShortRandomIdGenerator();
        for (int i = 0; i < 1000; ++i) {
            String val = (String) gen.generate(null, null);
            assertNotNull(val);
            assertEquals(22, val.length());
        }
    }

    @Test
    public void testRaiseTime() {
        String taskName = "Raisingtest " + new Date();
        createTask(taskName);
        createTask(taskName);
        createTask(taskName);
        List<TimeEntry> entries = c.getEntries();
        assertFalse(entries.isEmpty());
        boolean found = false;
        for (TimeEntry timeEntry : entries) {
            if (taskName.equals(timeEntry.getTask())) {
                found = true;
                System.out.println(timeEntry);
                assertEquals(timeEntry.toString(), 3 * HOUR_GRANULARITY, timeEntry.getHours().floatValue(), 1e-6);
            }
        }
        assertTrue(found);
    }

}
