/* created by hps on 01.10.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.functional;

/**
 * A runnable with two parameters. Some sort of of poor mans closure.
 * 
 * @author hps
 * @since 01.10.2008
 */
public class Runnable2<T1, T2> extends Runnable1<T1> {

    public T2 val2;

    public Runnable2() {
        // empty
    }

    public Runnable2(T1 theVal) {
        super(theVal);
    }

    public Runnable2(T1 theVal, T2 theVal2) {
        super(theVal);
        val2 = theVal2;
    }

}
