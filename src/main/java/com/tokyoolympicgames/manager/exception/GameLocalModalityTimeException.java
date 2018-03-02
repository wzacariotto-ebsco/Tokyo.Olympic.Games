package com.tokyoolympicgames.manager.exception;

public class GameLocalModalityTimeException extends RuntimeException {

    /**
     * This class is an runtime exception responsible for the validation of how many time the game will happen in some local
     */
    private static final long serialVersionUID = 2685347634907353702L;

    public GameLocalModalityTimeException() {

    }

    public GameLocalModalityTimeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {

        super(arg0, arg1, arg2, arg3);
    }

    public GameLocalModalityTimeException(String arg0, Throwable arg1) {

        super(arg0, arg1);
    }

    public GameLocalModalityTimeException(String arg0) {

        super(arg0);
    }

    public GameLocalModalityTimeException(Throwable arg0) {

        super(arg0);
    }
}
