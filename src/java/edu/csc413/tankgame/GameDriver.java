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
    private final PrintListener printlistener;



    public GameDriver() {
        mainView = new MainView();
        runGameView = mainView.getRunGameView();
        gameState = new GameState();
        printlistener = new PrintListener();

    }


    public static boolean startPressed;
    public static boolean exitPressed;

    public void start() {
        // TODO: Implement.
        // This should set the MainView's screen to the start menu screen.
        // u should have figured out how to transit the screen when the button is pressed

        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);

      if (startPressed = true) {
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
        Tank waiTank =
                new AwareAITank(
                        GameState.AI_TANK_2_ID,
                        RunGameView.AI_TANK_2_INITIAL_X,
                        RunGameView.AI_TANK_2_INITIAL_Y,
                        RunGameView.AI_TANK_2_INITIAL_ANGLE);


        WallImageInfo.readWalls();
        for(int i = 0; i <WallImageInfo.readWalls().size();i++ )
        {
            String wallz = "Wall-"+i;
            Walls walls = new Walls(
                     wallz,
                      WallImageInfo.readWalls().get(i).getX(),
                      WallImageInfo.readWalls().get(i).getY(),
                      0);
            gameState.addEntity(walls);
            runGameView.addDrawableEntity(
                     wallz,
                     WallImageInfo.readWalls().get(i).getImageFile(),
                     walls.getX(),
                     walls.getY(),
                    0
            );

        }


        // adding to the list
        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);
        gameState.addEntity(waiTank);


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
        runGameView.addDrawableEntity(
                GameState.AI_TANK_2_ID,
                RunGameView.AI_TANK_2_IMAGE_FILE,
                waiTank.getX(),
                waiTank.getY(),
                waiTank.getAngle());


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
       //     entity.add(shellEntities);
            entity.boundChecking(gameState);
        }
        //1.Ask all tanks,shells, etc. to move
        //2.Ask all tanks, shells, etc. to check bounds
        //3. For part 2: check collisions
        //4. for(every thing in the game that is drawn, need to update the location and angle)


        // Ask gameState --- any new shells to draw?
        // if so, call addDrawableEntity

        //Ask gameState -- any shells to remove?
        // if so, cal removeDrawableEntity

        for (Entity ShellEntities : gameState.getShellEntities()) {
            runGameView.addDrawableEntity(
                    ShellEntities.getId(),
                    RunGameView.SHELL_IMAGE_FILE,
                    ShellEntities.getX(),
                    ShellEntities.getY(),
                    ShellEntities.getAngle());
            if (!gameState.getShellEntities().contains(ShellEntities)) {
                runGameView.removeDrawableEntity(ShellEntities.getId());
            }
            ShellEntities.move(gameState);

        }


        for (Entity entity : gameState.getEntities()) {
            runGameView.setDrawableEntityLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }

        for(int i = 0; i < gameState.getEntities().size() - 1; i++){
            for(int j=i+1; j<gameState.getEntities().size()-1;j++) {


                Entity entity1 = gameState.getEntities().get(i);
                Entity entity2 = gameState.getEntities().get(j);

                if(entitiesOverlap(entity1, entity2)){
                    handleCollision(entity1,entity2);
                }
            }
        }

        return true;

    }


    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }

    public static void pressedStart() {
        startPressed = true;

    }

    public static void pressedExit() {
        exitPressed = true;

    }


    public class PrintListener implements ActionListener {
        public static final String START_BUTTON_ACTION_COMMAND = "start_ac";
        public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_ac";


        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
               pressedStart();
                System.out.println("Start button is pressed");
                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);


            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
                if(actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)){
                  mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
                  pressedExit();
                System.out.println(" exit is pressed");}
            }

        }
    }

//    for(int i = 0; i < gameState.getEntities().size()- 1; i++){
//        for(int j=i+1; j<gameState.getEntites().size()-1;j++){
//
//        }
//        Entity entity1 = gameState.getEntities().get(i);
//
//    }

    private boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    }


    private void handleCollision(Entity entity1, Entity entity2){
        if(entity1 instanceof Tank && entity2 instanceof Tank){
            double x1Distance = entity1.getXBound()-entity2.getX();
            double x2Distance = entity2.getXBound()-entity1.getX();
            double y1Distance = entity1.getYBound()-entity2.getY();
            double y2Distance = entity2.getYBound()-entity1.getY();

            double halfDistance;
            if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance){
                halfDistance = x1Distance/2;
                entity1.setX(entity1.getX() - halfDistance);
                entity2.setX(entity2.getX() + halfDistance);
            }

            else if(x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance) {
                halfDistance = x2Distance/2;
                entity1.setX(entity1.getX() + halfDistance);
                entity2.setX(entity1.getX() - halfDistance);
            }

            else if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance) {
                halfDistance = x1Distance/2;
                entity1.setY(entity1.getX() - halfDistance);
                entity2.setY(entity1.getX() + halfDistance);
            }

            else if (x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance){
                  halfDistance = x2Distance/2;
                  entity1.setY(entity1.getX() + halfDistance);
                  entity2.setY(entity1.getX() - halfDistance);
            }


        }else if (entity1 instanceof Tank && entity2 instanceof Walls) {
            // Handle tank shell collision
            double x1Distance = entity1.getXBound()-entity2.getX();
            double x2Distance = entity2.getXBound()-entity1.getX();
            double y1Distance = entity1.getYBound()-entity2.getY();
            double y2Distance = entity2.getYBound()-entity1.getY();

            double distance;

            if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance){
                distance = x1Distance;
                entity1.setX(entity1.getX() - distance);}

            else if(x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance){
                distance = x2Distance;
                entity2.setX(entity1.getX() - distance);}

            else if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance){
                distance = x1Distance;
                entity1.setY(entity1.getX() - distance);}

            else if (x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance){
                distance = x2Distance;
                entity2.setY(entity1.getX() - distance);}



        } else if (entity1 instanceof Shell && entity2 instanceof Tank) {
            // Same as above]
            double x1Distance = entity1.getXBound()-entity2.getX();
            double x2Distance = entity2.getXBound()-entity1.getX();
            double y1Distance = entity1.getYBound()-entity2.getY();
            double y2Distance = entity2.getYBound()-entity1.getY();

            double distance;

            if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance){
                distance = x1Distance;
                entity1.setX(entity1.getX() - distance);}

            else if(x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance){
                distance = x2Distance;
                entity2.setX(entity1.getX() - distance);}

            else if(x1Distance < x2Distance && x1Distance < y1Distance && x1Distance< y2Distance){
                distance = x1Distance;
                entity1.setY(entity1.getX() - distance);}

            else if (x2Distance < x1Distance && x2Distance < y1Distance && x2Distance< y2Distance){
                distance = x2Distance;
                entity2.setY(entity1.getX() - distance);}

        }
    }

}


