package edu.csc413.tankgame.model;

public class DumbAITAnk extends Tank {
    public DumbAITAnk(String id, double x, double y, double angle){
        super(id,x, y, angle );

    }

    @Override
    public void move(GameState gameState){
        moveForward();
        turnRight();

    }
}

