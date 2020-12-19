package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
        PrintListener printlistener = new PrintListener();
        EscapeListener escapelistener = new EscapeListener();
        mainView = new MainView(printlistener, escapelistener);
        runGameView = mainView.getRunGameView();
        gameState = new GameState();


    }



    public void start() {
        // TODO: Implement.
        // This should set the MainView's screen to the start menu screen.
        // u should have figured out how to transit the screen when the button is pressed

        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);
          runGame();

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
            String wall = "Wall-" + i;
            Walls walls = new Walls(
                      wall,
                      WallImageInfo.readWalls().get(i).getX(),
                      WallImageInfo.readWalls().get(i).getY(),
                      0);
            gameState.addEntity(walls);
            runGameView.addDrawableEntity(
                     wall,
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

        for (Entity ShellEntities : gameState.getShell()) {
            runGameView.addDrawableEntity(
                    ShellEntities.getId(),
                    RunGameView.SHELL_IMAGE_FILE,
                    ShellEntities.getX(),
                    ShellEntities.getY(),
                    ShellEntities.getAngle());
            gameState.addEntity(ShellEntities);
        }
        gameState.getShell().clear();

        for(Entity bullet : gameState.GetShellToRemove()){
            gameState.removeEntity(bullet);
            runGameView.removeDrawableEntity(bullet.getId());
        }
            gameState.GetShellToRemove().clear();

        for (Entity entity : gameState.getEntities()) {
            runGameView.setDrawableEntityLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }


        for(Entity entity1: gameState.getEntities() ){
                 for(Entity entity2:gameState.getEntities()){
                     if(entitiesOverlap(entity1, entity2)){
                       if  (!entity1.getId().equals(entity2.getId())) {
                             handleCollision(entity1, entity2);
                 }

                    }
                }
            }

        return true;
    }


    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }




    public class PrintListener implements ActionListener {
        public static final String START_BUTTON_ACTION_COMMAND = "start_ac";
        public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_ac";


        @Override
        public void actionPerformed(ActionEvent event) {
            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                System.out.println("Start button is pressed");


            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
                mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
                System.out.println(" exit is pressed");

            }

        }
    }

    public class EscapeListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e){

        }

        @Override
        public void keyPressed(KeyEvent event){
            int key = event.getKeyCode();
            if(key == KeyEvent.VK_ESCAPE){
                mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            }

        }
        @Override
        public void keyReleased(KeyEvent e){

        }
    }

    private boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    }


    private void handleCollision(Entity entity1, Entity entity2) {
        if (entity1 instanceof Tank && entity2 instanceof Tank) {
            double x1Distance = entity1.getXBound() - entity2.getX();
            double x2Distance = entity2.getXBound() - entity1.getX();
            double y1Distance = entity1.getYBound() - entity2.getY();
            double y2Distance = entity2.getYBound() - entity1.getY();

            double halfDistance;

            if (x1Distance < x2Distance && x1Distance < y1Distance && x1Distance < y2Distance) {
                halfDistance = x1Distance / 2;
                entity1.setX(entity1.getX() - halfDistance);
                entity2.setX(entity2.getX() + halfDistance);

            } else if (x2Distance < x1Distance && x2Distance < y1Distance && x2Distance < y2Distance) {
                halfDistance = x2Distance / 2;
                entity1.setX(entity1.getX() + halfDistance);
                entity2.setX(entity2.getX() - halfDistance);
            } else if (y1Distance < x2Distance && y1Distance < x1Distance && y1Distance < y2Distance) {
                halfDistance = y1Distance / 2;
                entity1.setY(entity1.getY() - halfDistance);
                entity2.setY(entity2.getY() + halfDistance);
            } else if (y2Distance < y1Distance && y2Distance < x1Distance && y2Distance < x2Distance) {
                halfDistance = y2Distance / 2;
                entity1.setY(entity1.getY() + halfDistance);
                entity2.setY(entity2.getY() - halfDistance);
            }


        } else if (entity1 instanceof Tank && entity2 instanceof Walls) {
//            // Handle tank shell collision

            double x1Distance = entity1.getXBound() - entity2.getX();
            double x2Distance = entity2.getXBound() - entity1.getX();
            double y1Distance = entity1.getYBound() - entity2.getY();
            double y2Distance = entity2.getYBound() - entity1.getY();
            double Distance;
            if (x1Distance < x2Distance && x1Distance < y1Distance && x1Distance < y2Distance) {
                Distance = x1Distance;
                entity1.setX(entity1.getX() - Distance);
            } else if (x2Distance < x1Distance && x2Distance < y1Distance && x2Distance < y2Distance) {
                Distance = x2Distance;
                entity1.setX(entity1.getX() + Distance);
            } else if (y1Distance < x2Distance && y1Distance < x1Distance && y1Distance < y2Distance) {
                Distance = y1Distance;
                entity1.setY(entity1.getY() - Distance);
            } else if (y2Distance < y1Distance && y2Distance < x1Distance && y2Distance < x2Distance) {
                Distance = y2Distance;
                entity1.setY(entity1.getY() + Distance);
            }


        } else if (entity1 instanceof Shell && entity2 instanceof Tank) {
            // Same as above]
            gameState.AddShellToRemove(entity1);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION),
                    RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    entity1.getX(), entity2.getY());



        } else if (entity1 instanceof Shell && entity2 instanceof Walls) {
            gameState.AddShellToRemove(entity1);
            gameState.AddShellToRemove(entity2);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION),
                    RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    entity1.getX(),
                    entity2.getY());



        } else if(entity1 instanceof Shell || entity2 instanceof Shell){
            gameState.AddShellToRemove(entity1);
            gameState.AddShellToRemove(entity2);
            runGameView.addAnimation((RunGameView.SHELL_EXPLOSION_ANIMATION),
                    RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                    entity1.getX(),
                    entity2.getY());

        }
    }
}