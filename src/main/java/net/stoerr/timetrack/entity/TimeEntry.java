package net.stoerr.timetrack.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Ein Zeiteintrag
 * 
 * @author hps
 * @since 24.09.2008
 */
@Entity
public class TimeEntry implements Serializable, Cloneable {

    private static final long serialVersionUID = -4477574737181050048L;

    private String id;

    private Date time;

    private String task;

    private Timestamp version;

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the task
     */
    public String getTask() {
        return task;
    }

    /**
     * @param task
     *            the task to set
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * @return the id
     */
    @Id
    @AccessType("field")
    @GeneratedValue(generator = "shortrandom")
    @GenericGenerator(name = "shortrandom", strategy = "net.stoerr.timetrack.entity.ShortRandomIdGenerator")
    public String getId() {
        return id;
    }

    /**
     * Interne Version fuer optimistic concurrency.
     * 
     * @return the version
     */
    @Version
    @AccessType("field")
    protected Timestamp getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return id.hashCode(); // (int) (id % 2147482763);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) { // impossible
            throw new RuntimeException(e);
        }
    }

}
