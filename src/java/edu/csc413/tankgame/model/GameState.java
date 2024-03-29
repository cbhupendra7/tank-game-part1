package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

import java.util.ArrayList;
import java.util.List;



/**
 * GameState represents the state of the game "world." The GameState object tracks all of the moving entities like tanks
 * and shells, and provides the controller of the program (i.e. the GameDriver) access to whatever information it needs
 * to run the game. Essentially, GameState is the "data context" needed for the rest of the program.
 */
public class GameState {
    public static final double TANK_X_LOWER_BOUND = 30.0;
    public static final double TANK_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width - 100.0;
    public static final double TANK_Y_LOWER_BOUND = 30.0;
    public static final double TANK_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height - 120.0;

    public static final double SHELL_X_LOWER_BOUND = -10.0;
    public static final double SHELL_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width;
    public static final double SHELL_Y_LOWER_BOUND = -10.0;
    public static final double SHELL_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height;

    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    public static final String AI_TANK_2_ID = "ai-tank2";
    // TODO: Feel free to add more tank IDs if you want to support multiple AI tanks! Just make sure they're unique.

    // TODO: Implement.
    // There's a lot of information the GameState will need to store to provide contextual information. Add whatever
    // instance variables, constructors, and methods are needed.


    public static boolean upPressed;
    public static boolean downPressed;
    public static boolean leftPressed;
    public static boolean rightPressed;
    public static boolean shootPressed;
    public static boolean escPressed;

    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity tank) {
        entities.add(tank);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }


    private final List<Entity> shell = new ArrayList<>();

    public void addShell(Entity shells) {
        shell.add(shells);
    }

    public List<Entity> getShell() {
        return shell;
    }

    public void removeShell(Entity shell) {
        entities.remove(shell);
    }



    private final List<Entity>  shellToRemove = new ArrayList<>();

    public void AddShellToRemove(Entity shell) {
        shellToRemove.add(shell);
    }

    public List<Entity> GetShellToRemove() {
        return shell;
    }

    public void RemoveShellToRemove(Entity shell) {
        entities.remove(shell);
    }




    public static void setupPressed() {
        upPressed = true;
    }

    public static void setdownPressed() {
        downPressed = true;
    }

    public static void setleftPressed() {
        leftPressed = true;
    }

    public static void setrightPressed() {
        rightPressed = true;
    }

    public static void setshootPressed() {
        shootPressed = true;
    }

    public static void setEscPressed() {escPressed = true;}

    public static void releaseupPressed() {
        upPressed = false;
    }

    public static void releasedownPressed() {
        downPressed = false;
    }

    public static void releaseleftPressed() {
        leftPressed = false;
    }

    public static void releaserightPressed() {
        rightPressed = false;
    }

    public static void releaseshootPressed() {
        shootPressed = false;
    }
    public static void releaseEscPressed(){
        escPressed = false;
    }


      public Entity getEntity(String playerTankId) {

        for (Entity entity: getEntities()){
            if(entity.getId().equals(playerTankId)){
                return entity;
            }
        }
        return null;
      }
}
