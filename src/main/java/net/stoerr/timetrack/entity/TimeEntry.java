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
import org.hibernate.annotations.Index;

/**
 * Ein Zeiteintrag
 * 
 * @author hps
 * @since 24.09.2008
 */
@Entity
public class TimeEntry implements Serializable, Cloneable {

    private static final long serialVersionUID = -5717515642220659858L;

    private String id;

    private Date time;

    private String task;

    private Float hours;

    private Timestamp version;

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) { // impossible
            throw new RuntimeException(e);
        }
    }

    /**
     * Hours we worked on the task
     * 
     * @return the hours
     */
    public Float getHours() {
        return hours;
    }

    @Id
    @AccessType("field")
    @GeneratedValue(generator = "shortrandom")
    @GenericGenerator(name = "shortrandom", strategy = "net.stoerr.timetrack.entity.ShortRandomIdGenerator")
    public String getId() {
        return id;
    }

    /**
     * @return the task
     */
    @Index(name = "entrytaskind")
    public String getTask() {
        return task;
    }

    /**
     * @return the time
     */
    @Index(name = "entrytimeind")
    public Date getTime() {
        return time;
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

    /**
     * @param hours
     *            the hours to set
     */
    public void setHours(Float hours) {
        this.hours = hours;
    }

    /**
     * @param task
     *            the task to set
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(final Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimeEntry{" + id + ":" + task + "," + hours + "," + time + "@" + version + "}";
    }

}
