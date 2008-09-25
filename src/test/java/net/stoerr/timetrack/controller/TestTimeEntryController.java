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

import net.stoerr.functional.Runnable1;
import net.stoerr.timetrack.entity.ShortRandomIdGenerator;
import net.stoerr.timetrack.entity.TimeEntry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author hps
 * @since 24.09.2008
 */
public class TestTimeEntryController {

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

    @Test
    public void testCreate() {
        TimeEntry entry = new TimeEntry();
        final String taskName = "Testtask " + new Date();
        entry.setTask(taskName);
        Runnable1<TimeEntry> run = new Runnable1<TimeEntry>(entry) {
            @Override
            public void run() {
                c.createEntry(val);
            }
        };
        c.wrapTransaction(run);
        /*
         * Same as c.getTransaction().begin(); c.createEntry(entry);
         * c.getTransaction().commit();
         */
        assertNotNull(entry.getId());
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

}
