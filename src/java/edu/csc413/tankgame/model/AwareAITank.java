package edu.csc413.tankgame.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class AwareAITank extends Tank {
    public AwareAITank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    private int coolDown = 10;

    @Override
  public void move(GameState gameState){
        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);
        double dx = playerTank.getX()-getX();
        double dy = playerTank.getY()-getY();

        double angleToPlayer = Math.atan2(dy,dx);
        double angleDifference = getAngle()-angleToPlayer;
        angleDifference -= Math.floor(angleDifference / Math.toRadians(360.0)+0.5)*Math.toRadians(360.0);

        if(angleDifference <-Math.toRadians(3.0)){
            turnRight();

        }else if(angleDifference >Math.toRadians(3.0)){
            turnLeft();
       }
         double distance = Math.sqrt(dx*dx+dy*dy);
        if(distance > 400.0){
            moveForward();
        }
        else if(distance <200.0){
            moveBackward();
        }






        coolDown++;
        if(coolDown > 10 ){
            shoot(gameState);

        };
        coolDown = 0;
    }

}