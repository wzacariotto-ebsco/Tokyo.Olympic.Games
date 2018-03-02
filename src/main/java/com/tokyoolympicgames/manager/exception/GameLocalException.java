package com.tokyoolympicgames.manager.exception;

public class GameLocalException extends RuntimeException {

    /**
     * This class is an runtime exception responsible for the validation local where end time is before the begin time
     */
    private static final long serialVersionUID = 2685347634907353702L;

    public GameLocalException() {

    }

    public GameLocalException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {

        super(arg0, arg1, arg2, arg3);
    }

    public GameLocalException(String arg0, Throwable arg1) {

        super(arg0, arg1);
    }

    public GameLocalException(String arg0) {

        super(arg0);
    }

    public GameLocalException(Throwable arg0) {

        super(arg0);
    }
}
