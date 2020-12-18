package edu.csc413.tankgame.model;

/**
 * Model class representing a shell that has been fired by a tank. A shell has a position and an angle, as well as a
 * speed. Shells by default should be unable to turn and only move forward.
 */
// TODO: Notice that Shell has a lot in common with Tank. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.
public class Shell extends Entity {
    private static final String SHELL_ID_PREFIX = "shell-";
    private static final double MOVEMENT_SPEED = 4.0;

    private static long uniqueId = 0L;

    public Shell(double x, double y, double angle) {
        super(getUniqueId(), x, y, angle);
    }
    protected void moveForward() {
        x += MOVEMENT_SPEED * Math.cos(angle);
        y += MOVEMENT_SPEED * Math.sin(angle);
    }


    @Override
    public void move(GameState gamestate){
        moveForward();
    }

    @Override
    public void boundChecking(GameState gameState) {
        if (getX() < GameState.SHELL_X_LOWER_BOUND) {
            x = GameState.SHELL_X_LOWER_BOUND;
        }
        if (getX() > GameState.SHELL_X_UPPER_BOUND) {
            x = GameState.SHELL_X_UPPER_BOUND;
        }
        if (getY() < GameState.SHELL_Y_LOWER_BOUND) {
            y = GameState.SHELL_Y_LOWER_BOUND;
        }
        if (getY() > GameState.SHELL_Y_UPPER_BOUND) {
            y = GameState.SHELL_Y_UPPER_BOUND;

        }
    }

    @Override
    public void setX(double v) {

    }

    @Override
    public void setY(double v) {

    }

    private static String getUniqueId () {
            return SHELL_ID_PREFIX + uniqueId++;
        }


    @Override
    public double getXBound() {
        return getX()+24.0;
    }

    public double getYBound() {
        return getY() + 24.0;


    }

    }
