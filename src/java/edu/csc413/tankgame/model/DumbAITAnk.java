package edu.csc413.tankgame.model;

public class DumbAITAnk extends Tank {
    public DumbAITAnk(String id, double x, double y, double angle) {
        super(id, x, y, angle);

    }

    private int coolDown = 200;
    @Override
    public void move(GameState gameState) {
       // moveForward();
        turnRight();

        coolDown++;
        if (coolDown > 205) {
            shoot(gameState);

            coolDown = 0;
        }
    }




}
