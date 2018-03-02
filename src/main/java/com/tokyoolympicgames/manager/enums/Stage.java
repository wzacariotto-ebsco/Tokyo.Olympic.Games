package com.tokyoolympicgames.manager.enums;

/**
 * Enum with the game gameStage.
 *
 * @author Wendler
 */
public enum Stage {
    FINAL("F"), EIGHTFINAL("EF"), QUARTERFINAL("QF"), SEMIFINAL("SF"), ELIMINATION("E");

    private String gameStage;

    Stage(String gameStage) {

        this.gameStage = gameStage;
    }

    public String getStage() {

        return gameStage;
    }
}
