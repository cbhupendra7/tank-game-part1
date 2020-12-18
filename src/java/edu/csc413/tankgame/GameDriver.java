package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * GameDriver is the primary controller class for the tank game. The game is launched from GameDriver.main, and
 * GameDriver is responsible for running the game loop while coordinating the views and the data models.
 */
public class GameDriver {
    // TODO: Implement.
    // Add the instance variables, constructors, and other methods needed for this class. GameDriver is the centerpiece
    // for the tank game, and should store and manage the other components (i.e. the views and the models). It also is
    // responsible for running the game loop.
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameState gameState;


    public GameDriver() {
        mainView = new MainView();
        runGameView = mainView.getRunGameView();
        gameState = new GameState();

    }

    public static boolean startPressed;
    public static boolean exitPressed;




    public void start() {
        // TODO: Implement.
        // This should set the MainView's screen to the start menu screen.
        // u should have figured out how to transit the screen when the button is pressed


        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);

        if (startPressed=true) {

            mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
            runGame();
        }
    }

    private void runGame() {
        Tank playerTank =
                new PlayerTank(
                        GameState.PLAYER_TANK_ID,
                        RunGameView.PLAYER_TANK_INITIAL_X,
                        RunGameView.PLAYER_TANK_INITIAL_Y,
                        RunGameView.PLAYER_TANK_INITIAL_ANGLE);


        Tank aiTank =
                new DumbAITAnk(
                        GameState.AI_TANK_ID,
                        RunGameView.AI_TANK_INITIAL_X,
                        RunGameView.AI_TANK_INITIAL_Y,
                        RunGameView.AI_TANK_INITIAL_ANGLE);

        // adding to the list
        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);

        runGameView.addDrawableEntity(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());

        runGameView.addDrawableEntity(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle());
        Runnable gameRunner = () -> {
            while (update()) {
                runGameView.repaint();
                try {
                    Thread.sleep(8L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };
        new Thread(gameRunner).start();
    }

    // TODO: Implement.
    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.
    private boolean update() {


        for (Entity entity : gameState.getEntities()) {
            entity.move(gameState);
        }


        //1.Ask all tanks,shells, etc. to move
        //2.Ask all tanks, shells, etc. to check bounds
        //3. For part 2: check collisions
        //4. for(every thing in the game that is drawn, need to update the location and angle)
        // runGameView.setDrawableEntityLocationAndAngle(GameState.PLAYER_TANK_ID,x,y, angle);


        // Ask gameState --- any new shells to draw?
        // if so, call addDrawableEntity

        //Ask gameState -- any shells to remove?
        // if so, cal removeDrawableEntity


        for (Entity entity : gameState.getEntities()) {
            runGameView.setDrawableEntityLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }
        return true;
    }


    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }

    public static void pressedStart(){
        startPressed = true;

    }
    public static void pressedExit(){
        exitPressed = true;

    }
}

    public static class startListener implements ActionListener {
        public static final String START_BUTTON_ACTION_COMMAND = "start_ac";
        public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_ac";


        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
               // mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                // MainView.Screen.RUN_GAME_SCREEN

                System.out.println("Start button is pressed");


            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
                //mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
                System.out.println(" exit is pressed");
            }

        }   }