/* created by hps on 25.09.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.functional;

/**
 * A {@link Runnable} that wraps checked exceptions into
 * {@link RuntimeException}s. Some sort of of poor mans closure.
 * 
 * @author hps
 * @since 25.09.2008
 */
public class ProtectedRunnable implements Runnable {

    /**
     * Override to implement the code to be executed, if it can throw checked
     * exceptions. These are wrapped into a {@link RuntimeException} by
     * {@link #run()}.
     */
    protected void protectedRun() {
        // empty
    }

    /**
     * Override to implement the code to be executed if it only throws unchecked
     * Exceptions. If not overriden it forwards to {@link #protectedRun()} but
     * wraps checked exceptions into {@link RuntimeException}s.
     */
    public void run() {
        try {
            protectedRun();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
