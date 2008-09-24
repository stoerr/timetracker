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

    /** The emfactory is only created when emfactory is first accessed. */
    @SuppressWarnings("synthetic-access")
    private static class EMFHelper {
        static final EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("timetrack");
        static final EntityManager emanager = emfactory.createEntityManager();
        static {
            initialized = true;
        }
    }

    private static EntityManager getEntityManager() {
        return EMFHelper.emanager;
    }

    @SuppressWarnings("unchecked")
    public List<TimeEntry> getEntries() {
        return getEntityManager().createQuery("from TimeEntry order by time").getResultList();
    }

    public void createEntry(TimeEntry entry) {
        getEntityManager().persist(entry);
    }

    public void shutdown() {
        if (initialized) {
            EMFHelper.emfactory.close();
        }
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
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
