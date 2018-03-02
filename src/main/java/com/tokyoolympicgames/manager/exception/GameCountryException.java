package com.tokyoolympicgames.manager.exception;

public class GameCountryException extends RuntimeException {

    /**
     * This class is an runtime exception responsible for the validation is it is the same country
     */
    private static final long serialVersionUID = 2685347634907353702L;

    public GameCountryException() {

    }

    public GameCountryException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {

        super(arg0, arg1, arg2, arg3);
    }

    public GameCountryException(String arg0, Throwable arg1) {

        super(arg0, arg1);
    }

    public GameCountryException(String arg0) {

        super(arg0);
    }

    public GameCountryException(Throwable arg0) {

        super(arg0);
    }
}
