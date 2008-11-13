package net.stoerr.timetrack.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import net.stoerr.timetrack.TimeTrackConstants;
import net.stoerr.timetrack.entity.TimeEntry;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.ejb.HibernateEntityManagerFactory;

/**
 * Controller für die Operationen an TimeEntries.
 * 
 * @see http 
 *      ://www.hibernate.org/hib_docs/entitymanager/reference/en/html/queryhql
 *      .html
 * 
 * @author hps
 * @since 24.09.2008
 */
public class TimeEntryController implements TimeTrackConstants {

    /** Logger for TimeEntryController */
    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory
            .getLog(TimeEntryController.class);

    private static boolean initialized = false;

    static final EntityManagerFactory emfactory;

    static final EntityManager emanager;
    static {
        try {
            emfactory = Persistence.createEntityManagerFactory("timetrack");
            emanager = emfactory.createEntityManager();
            initialized = true;
        } catch (Exception e) {
            LOG.error(e, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private static EntityManager getEntityManager() {
        return emanager;
    }

    public TimeEntryController() {
        // empty
    }

    public void createEntry(TimeEntry entry) {
        getEntityManager().persist(entry);
    }

    /**
     * Flushes the DB to disk.
     */
    public void flushDB() {
        LOG.info("Flushing");
        Session session = ((HibernateEntityManagerFactory) emfactory).getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        session.createSQLQuery("CHECKPOINT").executeUpdate();
        trans.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<TimeEntry> getEntries() {
        final List<TimeEntry> resultList = getEntityManager().createQuery("from TimeEntry order by time desc")
                .getResultList();
        return resultList;
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    /**
     * Speichert eine halbe Stinde
     * 
     * @param task
     */
    @SuppressWarnings("unchecked")
    public TimeEntry saveAction(String task) {
        TimeEntry entry;
        getTransaction().begin();
        Date time = new Date();
        Query query = getEntityManager().createQuery(
                "from TimeEntry t where t.task=:task and t.time>:daystart order by time desc");
        query.setParameter("task", task);
        long timeDaystart = (time.getTime() / 86400000) * 86400000;
        Date daystart = new Date(timeDaystart);
        query.setParameter("daystart", daystart);
        List<TimeEntry> haveEntries = query.getResultList();
        if (haveEntries.isEmpty()) {
            entry = new TimeEntry();
            entry.setTask(task.trim());
            entry.setTime(time);
            entry.setHours(HOUR_GRANULARITY);
            createEntry(entry);
        } else {
            entry = haveEntries.get(0);
            Float hours = entry.getHours();
            if (null == hours || 0 == hours) {
                hours = HOUR_GRANULARITY;
            }
            entry.setHours(hours + HOUR_GRANULARITY);
        }
        getTransaction().commit();
        flushDB();
        return entry;
    }

    public void shutdown() {
        if (initialized) {
            emfactory.close();
        }
    }

    public void startup() {
        getEntries();
    }

    @SuppressWarnings("null")
    public void wrapTransaction(Runnable run) {
        EntityTransaction trans = null;
        try {
            trans = getTransaction();
            trans.begin();
            run.run();
            trans.commit();
        } catch (Exception e) {
            LOG.error(e, e);
            try {
                trans.rollback();
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }
    }

}
