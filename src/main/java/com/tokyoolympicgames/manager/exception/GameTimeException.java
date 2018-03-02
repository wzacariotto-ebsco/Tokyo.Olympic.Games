package com.tokyoolympicgames.manager.exception;

public class GameTimeException extends RuntimeException {

    /**
     * This class is an runtime exception responsible for the validation game begin and end date and duration
     */
    private static final long serialVersionUID = 2685347634907353702L;

    public GameTimeException() {

    }

    public GameTimeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {

        super(arg0, arg1, arg2, arg3);
    }

    public GameTimeException(String arg0, Throwable arg1) {

        super(arg0, arg1);
    }

    public GameTimeException(String arg0) {

        super(arg0);
    }

    public GameTimeException(Throwable arg0) {

        super(arg0);
    }
}
