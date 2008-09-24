package net.stoerr.functional;

/**
 * A runnable with a parameter.
 * 
 * @author hps
 * @since 24.09.2008
 */
public abstract class ParameterizedRunnable<T> implements Runnable {

    public T val;

    public ParameterizedRunnable() {
        // empty
    }

    public ParameterizedRunnable(T val) {
        this.val = val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    abstract public void run();

}
