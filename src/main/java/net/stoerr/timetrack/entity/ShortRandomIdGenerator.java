package net.stoerr.timetrack.entity;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;

/**
 * A very
 * 
 * @author hps
 * @since 25.09.2008
 */
public class ShortRandomIdGenerator extends AbstractUUIDGenerator {

    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    private static long mult = 15137;
    private static long div = 2147482763;

    public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
        StringBuilder buf = new StringBuilder();
        double d = Math.random();
        short count = this.getCount();
        short hitime = this.getHiTime();
        int ip = this.getIP();
        int jvm = this.getJVM();
        int lotime = this.getLoTime();
        long val = count;
        val *= mult;
        val %= div;
        val += hitime;
        val *= mult;
        val %= div;
        d = d + val / ((double) div);
        d = Math.abs(d);
        while (d > 1) {
            d = d / 2;
        }
        for (int i = 0; i < 11; ++i) {
            d = d * chars.length();
            int n = (int) Math.floor(d);
            d -= n;
            buf.append(chars.charAt(n));
        }
        val += ip;
        val *= mult;
        val %= div;
        val += jvm;
        val *= mult;
        val %= div;
        val += lotime;
        val *= mult;
        val %= div;
        d += val / ((double) div) + Math.random();
        d = Math.abs(d);
        while (d > 1) {
            d = d / 2;
        }
        for (int i = 0; i < 11; ++i) {
            d = d * chars.length();
            int n = (int) Math.floor(d);
            d -= n;
            buf.append(chars.charAt(n));
        }
        return buf.toString();
    }

}
