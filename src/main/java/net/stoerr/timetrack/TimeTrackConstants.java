/* created by hps on 01.10.2008
 * Copyright 2007 T-Systems MMS GmbH Dresden
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 */
package net.stoerr.timetrack;

/**
 * Allgemeine Konstanten für TimeTrack
 * 
 * @author hps
 * @since 01.10.2008
 */
public interface TimeTrackConstants {

    /** Die Zeitgranularität: Zeit, nach der TimeTrack aufgerufen wird. */
    float HOUR_GRANULARITY = 0.5f;

    /** Zeit für normalen Kill: 10 Minuten */
    long DELAY_KILL = 10 * 60 * 1000;

    /** Zeit für Emergency-Kill: 20 Minuten */
    long DELAY_EMERGENCYKILL = 20 * 60 * 1000;

    /** Countdown Zeit in Sekunden */
    int COUNTDOWN_SECONDS = 120;

}
