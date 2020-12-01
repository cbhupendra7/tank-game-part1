package edu.csc413.tankgame;

import edu.csc413.tankgame.model.GameState;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControlListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            GameState.setupPressed();

            System.out.println("w is pressed");
        }
        if (keyCode == KeyEvent.VK_S) {
            GameState.setdownPressed();
            System.out.println("A is pressed");
        }
        if (keyCode == KeyEvent.VK_A) {
            GameState.setleftPressed();
            System.out.println("S is pressed");
        }
        if (keyCode == KeyEvent.VK_D) {
            GameState.setrightPressed();
            System.out.println("D is pressed");
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.out.println("esc is pressed");
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            GameState.setshootPressed();
            System.out.println(" space is pressed");
        }
    }

        @Override
        public void keyReleased (KeyEvent event){
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
                GameState.releaseupPressed();
                System.out.println("w is released");
            }
            if (keyCode == KeyEvent.VK_S) {
                GameState.releasedownPressed();
                System.out.println("S is released");

            }
            if (keyCode == KeyEvent.VK_A) {
                GameState.releaseleftPressed();
                System.out.println("A is released");

            }
            if (keyCode == KeyEvent.VK_D) {
                GameState.releaserightPressed();
                System.out.println("D is released");

            }
            if (keyCode == KeyEvent.VK_ESCAPE) {
                System.out.println("esc is released");
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                GameState.releaseshootPressed();
                System.out.println(" space is released");
            }
        }
    }




