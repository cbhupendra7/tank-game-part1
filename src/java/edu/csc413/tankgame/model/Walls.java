package edu.csc413.tankgame.model;

public class Walls extends Entity {
    public Walls(String id, double x, double y, double angle){
    super(id, x, y, angle);
}


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public double getXBound() {
        return getX()+32.0;
    }

    @Override
    public double getYBound() {
        return getY()+32.0;
    }

    @Override
    public void move(GameState gameState){

    }

    @Override
    public void boundChecking(GameState gameState) {
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public void setY(double y) {

    }

//    @Override
//    public void Health(GameState gameState) {
//
//    }

}
