package net.stoerr.functional;

/**
 * A runnable with a parameter. Some sort of of poor mans closure.
 * 
 * @author hps
 * @since 24.09.2008
 */
public abstract class Runnable1<T> extends ProtectedRunnable {

    public T val;

    public Runnable1() {
        // empty
    }

    public Runnable1(T val) {
        this.val = val;
    }

}
