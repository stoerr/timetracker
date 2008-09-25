package net.stoerr.timetrack.entity;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;

public class ShortRandomIdGenerator extends AbstractUUIDGenerator {

    public Serializable generate(SessionImplementor arg0, Object arg1)
            throws HibernateException {
        return String.valueOf(Math.random()).substring(2);
    }

}
