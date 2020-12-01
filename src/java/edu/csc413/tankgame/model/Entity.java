package edu.csc413.tankgame.model;

public abstract class Entity {

    protected final String id;
    protected double x;
    protected double y;
    protected double angle;

    public Entity(String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }


    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public abstract void move(GameState gameState);
}







