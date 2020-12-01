package edu.csc413.tankgame.model;

public class PlayerTank extends Tank {

    public PlayerTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }


    @Override
    public void move(GameState gameState) {
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

    }

}



