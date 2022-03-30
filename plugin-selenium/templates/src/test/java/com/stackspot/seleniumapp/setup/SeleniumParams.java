package com.stackspot.seleniumapp.setup;

public final class SeleniumParams {

    private static final String BROWSER = "CHROME";
    private static final int MONITOR_ID = 1;
    private static final long DEFAULT_ELEMENT_WAIT_TIME_IN_MILL = 6000;
    private static final long DEFAULT_PAGE_WAIT_TIME_IN_MILL = 30000;

    public static synchronized String BROWSER() { return BROWSER; }

    public static synchronized int MONITOR_ID() {
        return MONITOR_ID;
    }

    public static synchronized long DEFAULT_ELEMENT_WAIT_TIME_IN_MILL() {
        return DEFAULT_ELEMENT_WAIT_TIME_IN_MILL;
    }

    public static synchronized long DEFAULT_PAGE_WAIT_TIME_IN_MILL() {
        return DEFAULT_PAGE_WAIT_TIME_IN_MILL;
    }
}
