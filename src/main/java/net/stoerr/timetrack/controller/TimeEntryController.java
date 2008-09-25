package net.stoerr.timetrack.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.stoerr.timetrack.entity.TimeEntry;

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
public class TimeEntryController {

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

    public void TimeEntryController() {
        // empty
    }

    public void createEntry(TimeEntry entry) {
        getEntityManager().persist(entry);
    }

    @SuppressWarnings("unchecked")
    public List<TimeEntry> getEntries() {
        final List resultList = getEntityManager().createQuery("from TimeEntry order by time")
                        .getResultList();
        return resultList;
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
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
