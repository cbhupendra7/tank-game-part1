package edu.csc413.tankgame.model;

public class Walls extends Entity {
    public Walls(String id, double x, double y, double angle){
    super(id, x, y, angle);
}

    @Override
    public double getXBound() {
        return 0;
    }

    @Override
    public double getYBound() {
        return 0;
    }

    @Override
    public void move(GameState gameState){


    }

    @Override
    public void boundChecking(GameState gameState) {

    }

    @Override
    public void setX(double v) {

    }

    @Override
    public void setY(double v) {

    }

}
