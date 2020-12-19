package edu.csc413.tankgame.model;

public class PlayerTank extends Tank {


    public PlayerTank(String id, double x, double y, double angle) {

        super(id, x, y, angle);
    }

    @Override
    public double getXBound() {
        return getX() + 55.0;
    }

    @Override
    public double getYBound() {
        return getY() + 55.0;
    }


    private int coolDown = 200;


    @Override
    public void move(GameState gameState) {
        coolDown++;
        if (GameState.upPressed) {
            moveForward();
        }
        if (GameState.downPressed) {
            moveBackward();
        }
        if (GameState.rightPressed) {
            turnRight();
        }
        if (GameState.leftPressed) {
            turnLeft();
        }
        if (GameState.shootPressed) {
            if (coolDown > 200) {

                shoot(gameState);

            }
            coolDown = 0;
        }
    }

}



